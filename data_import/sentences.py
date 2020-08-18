import os
import re
import sys
import wave

import mysql.connector
import spacy
from pydub import AudioSegment

from config import *

NLP = spacy.load('de_core_news_sm', disable=['ner'])
WHITESPACE_REGEX = re.compile(r'[ \t]+')


def split_to_sentences(transcript):
    transcript = transcript.replace('-\n', '')
    transcript = transcript.replace(' \n', ' ')
    transcript = transcript.replace('\n', ' ')
    transcript = transcript.replace('\t', ' ')
    transcript = WHITESPACE_REGEX.sub(' ', transcript)
    transcript = transcript.strip()

    doc = NLP(transcript)

    return [sent.text for sent in doc.sents]


def get_last_insert_id(cursor):
    """
    return the last inserted id by this client/connection.
    see also https://dev.mysql.com/doc/refman/5.7/en/mysql-insert-id.html
    """
    cursor.execute('select last_insert_id() as id')
    return cursor.fetchone()['id']


if __name__ == '__main__':
    command = sys.argv[1]
    connection = mysql.connector.connect(
        host=HOST,
        database=DATABASE,
        user=USER,
        password=PASSWORD,
    )
    connection.autocommit = False
    cursor = connection.cursor(dictionary=True)
    try:
        # extract sentences from a text file.
        if command == "1":
            fileIds = sys.argv[2].split(',')
            for fileId in fileIds:
                fp = os.path.join(BASE_DIR, 'extracted_text', fileId + ".txt")
                sentences = split_to_sentences(open(fp, encoding="utf-8").read())
                for excerpt in sentences:
                    cursor.execute(
                        "insert into data_element(source_id, user_group_id) value(%s, (select (user_group_id) from source where id=%s))",
                        [fileId, fileId])
                    data_element_id = get_last_insert_id(cursor)
                    cursor.execute("insert into text(dialect_id,data_element_id,text) values(%s,%s,%s)",
                                   [27, data_element_id, excerpt])
                print(fileId + " done.")
                os.remove(fp)
                connection.commit()
        # re generate text-audio audio segment
        elif (command == "2"):
            text_audio_id = sys.argv[2]
            cursor.execute(
                'select path_to_raw_file,audio_start,audio_end from audio join data_element on '
                'audio.data_element_id=data_element.id join source on source.id=data_element.source_id where audio.id = %s',
                [text_audio_id])
            text_audio = cursor.fetchone()
            audio = text_audio['path_to_raw_file']
            audio = os.path.join(BASE_DIR, audio)
            audio_start = text_audio['audio_start']
            audio_end = text_audio['audio_end']
            duration_seconds = audio_end - audio_start
            with wave.open(audio, 'rb') as f_wave:
                audio_path_to_file = f'{text_audio_id}.flac'

                f_wave.setpos(int(audio_start * f_wave.getframerate()))
                audio_bytes = f_wave.readframes(int(duration_seconds * f_wave.getframerate()))
                audio_segment = AudioSegment(
                    data=audio_bytes,
                    sample_width=f_wave.getsampwidth(),
                    frame_rate=f_wave.getframerate(),
                    channels=f_wave.getnchannels(),
                )
                audio_segment = audio_segment.set_channels(1)
                audio_segment.export(os.path.join(BASE_DIR, "text_audio", audio_path_to_file),
                                     format='flac')
            connection.commit()
        # calculate the duration of all recordings that are not calculated already. this is needed because:
        # 1. there is no java library that supports webm (vorbis).
        # 2. chrome does not set the duration header of the webm/vorbis (firefox 77.0 does set it).
        # see https://bugs.chromium.org/p/chromium/issues/detail?id=642012
        elif (command == "3"):
            cursor.execute("select id,path from audio where duration=0")
            paths = cursor.fetchall()
            for path in paths:
                seconds = len(AudioSegment.from_file(os.path.join(BASE_DIR, path['path']))) / 1000
                cursor.execute('update audio set duration=%s where audio.id = %s', [seconds, path['id']])
            connection.commit()
    finally:
        cursor.close()
        connection.close()
