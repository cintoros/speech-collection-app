import logging
import os

import mysql.connector

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


def data_import_2():
    logging.info('Loading...')
    # init directories
    os.makedirs(os.path.join(BASE_DIR, "text_audio"), exist_ok=True)
    os.makedirs(os.path.join(BASE_DIR, "original_text"), exist_ok=True)
    os.makedirs(os.path.join(BASE_DIR, "recording"), exist_ok=True)
    sp = os.path.join("common_voice", "data_de", "sentences.txt")
    cursor.execute('insert into source(user_id,user_group_id,path_to_raw_file,name,licence) VALUE(%s,%s,%s,%s,%s)',
                   [1, 1, sp, "CommonVoice", "CC-0"])
    source_id = get_last_insert_id(cursor)
    file1 = open(os.path.join(BASE_DIR, sp), "r")
    lines = file1.readlines()
    # NOTE mysql/mariadb do not support tablesample so we need to shuffle the data before inserting.
    import random
    random.shuffle(lines)
    for idx, line in enumerate(lines):
        cursor.execute("insert into data_element ( source_id,  user_group_id, finished)values (%s, %s, %s)",
                       [source_id, 1, False])
        element_id = get_last_insert_id(cursor)
        cursor.execute("insert into text ( dialect_id,  data_element_id, text)values (%s, %s, %s)",
                       [27, element_id, line])
        if idx % 10000 == 0:
            logging.info('Loaded %s values', idx)
            connection.commit()
    connection.commit()
    logging.info('Done!')


if __name__ == '__main__':
    data_import_2()
