import os
import re
import sys

import mysql.connector
import spacy

from config import *

NLP = spacy.load('de_core_news_sm', disable=['ner'])
WHITESPACE_REGEX = re.compile(r'[ \t]+')

connection = mysql.connector.connect(
    host=host,
    database=database,
    user=user,
    password=password,
)
connection.autocommit = False
cursor = connection.cursor(dictionary=True)


def preprocess_transcript_for_sentence_split(transcript):
    transcript = transcript.replace('-\n', '')
    transcript = transcript.replace(' \n', ' ')
    transcript = transcript.replace('\n', ' ')
    transcript = transcript.replace('\t', ' ')

    transcript = WHITESPACE_REGEX.sub(' ', transcript)
    transcript = transcript.strip()

    return transcript


def split_to_sentences(transcript):
    doc = NLP(preprocess_transcript_for_sentence_split(transcript))

    return [sent.text for sent in doc.sents]


if __name__ == '__main__':
    fileIds = sys.argv[1]
    for fileId in fileIds.split(','):
        with open(os.path.join(base_dir, 'extracted_text', fileId + ".txt")) as file:
            p = preprocess_transcript_for_sentence_split(file)
            sentences = split_to_sentences(p)
            # TODO insert sentences into database
        print(fileId)
