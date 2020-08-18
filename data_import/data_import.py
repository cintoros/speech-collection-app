import logging
import os
import wave

import mysql.connector
from bs4 import BeautifulSoup
from pydub import AudioSegment

from config import *

connection = mysql.connector.connect(
    host=HOST,
    database=DATABASE,
    user=USER,
    password=PASSWORD,
)
connection.autocommit = False
cursor = connection.cursor(dictionary=True)

logging.basicConfig(level=logging.INFO)


def get_last_insert_id(dict_cursor):
    """
    return the last inserted id by this client/connection.
    see also https://dev.mysql.com/doc/refman/5.7/en/mysql-insert-id.html
    """
    dict_cursor.execute('select last_insert_id() as id')
    return dict_cursor.fetchone()['id']


def replace_extension(file, new_extension):
    root, _ = os.path.splitext(file)
    if not new_extension.startswith('.'):
        new_extension = '.' + new_extension
    return root + new_extension


def search_directories():
    try:
        # init directories
        os.makedirs(os.path.join(BASE_DIR, "text_audio"))
        os.makedirs(os.path.join(BASE_DIR, "original_text"))
        os.makedirs(os.path.join(BASE_DIR, "recording"))
    except FileExistsError:
        # directory already exists
        pass
    logging.info('Loading...')
    entries = os.scandir(os.path.join(BASE_DIR, SOURCE_DIR))
    for entry in entries:
        for fileData in os.listdir(os.path.join(BASE_DIR, SOURCE_DIR, entry.name)):
            if fileData.endswith(".xml"):
                extract_data_to_db(entry.name)
    logging.info('Done!')


def extract_data_to_db(folderNumber: str):
    try:
        logging.info('Loading ' + folderNumber)
        text_path = os.path.join(SOURCE_DIR, folderNumber, 'indexes.xml')
        audio_path = os.path.join(SOURCE_DIR, folderNumber, 'audio.wav')
        cursor.execute('insert into source(user_id,user_group_id,path_to_raw_file,name,licence) VALUE(%s,%s,%s,%s,%s)',
                       [1, 1, audio_path, SOURCE_NAME, SOURCE_LICENCE])
        audio_source_id = get_last_insert_id(cursor)
        cursor.execute('insert into source(user_id,user_group_id,path_to_raw_file,name,licence) VALUE(%s,%s,%s,%s,%s)',
                       [1, 1, text_path, SOURCE_NAME, SOURCE_LICENCE])
        text_source_id = get_last_insert_id(cursor)
        text_path = os.path.join(BASE_DIR, text_path)
        audio_path = os.path.join(BASE_DIR, audio_path)

        with open(text_path, encoding='utf-8') as file:
            soup = BeautifulSoup(file.read(), 'html.parser')
        with wave.open(audio_path, 'rb') as f_wave:
            times = {tli['id']: float(tli['time']) for tli in soup.find_all('tli')}
            for tier in soup.find_all('tier'):
                for event in tier.find_all('event'):
                    start_time = times[event['start']]
                    end_time = times[event['end']]
                    duration_seconds = end_time - start_time
                    if duration_seconds > 0.0:
                        transcript_text = event.get_text()
                        cursor.execute(
                            "insert into data_element ( source_id,  user_group_id, finished)values (%s, %s, %s)",
                            [text_source_id, 1, True]
                        )
                        element_id_1 = get_last_insert_id(cursor)
                        cursor.execute(
                            "insert into data_element ( source_id,  user_group_id, finished)values (%s, %s, %s)",
                            [audio_source_id, 1, True]
                        )
                        element_id_2 = get_last_insert_id(cursor)
                        cursor.execute(
                            "insert into data_tuple (data_element_id_1,data_element_id_2,type)values (%s,%s,%s)",
                            [element_id_1, element_id_2, "TEXT_AUDIO"]
                        )
                        # TODO for now we just insert everything as standard german
                        cursor.execute(
                            "insert into text ( dialect_id,  data_element_id, text)values (%s, %s, %s)",
                            [27, element_id_1, transcript_text]
                        )
                        cursor.execute(
                            "insert into audio (dialect_id,data_element_id,audio_start,audio_end,path)values (%s,%s,%s,%s,%s)",
                            [27, element_id_2, start_time, end_time, 'PLACEHOLDER']
                        )

                        text_audio_id = get_last_insert_id(cursor)
                        audio_path_to_file = os.path.join("text_audio", f'{text_audio_id}.flac')
                        cursor.execute('update audio set path = %s where id = %s',
                                       [audio_path_to_file, text_audio_id])
                        f_wave.setpos(int(start_time * f_wave.getframerate()))
                        audio_bytes = f_wave.readframes(int(duration_seconds * f_wave.getframerate()))
                        audio_segment = AudioSegment(
                            data=audio_bytes,
                            sample_width=f_wave.getsampwidth(),
                            frame_rate=f_wave.getframerate(),
                            channels=f_wave.getnchannels(),
                        )
                        audio_segment = audio_segment.set_channels(1)
                        audio_segment.export(os.path.join(BASE_DIR, audio_path_to_file),
                                             format='flac')
                    else:
                        logging.warning(
                            f"skipping event with start={event['start']} because its duration of {duration_seconds} is <= 0.0.")
        connection.commit()
    except Exception as e:
        connection.rollback()
        raise e
    finally:
        cursor.close()
        connection.close()


if __name__ == '__main__':
    search_directories()
