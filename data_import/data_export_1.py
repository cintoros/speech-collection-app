import logging
import os

import mysql.connector
from pydub import AudioSegment

from config import *

connection = mysql.connector.connect(
    host=host,
    database=database,
    user=user,
    password=password,
)
connection.autocommit = False
cursor = connection.cursor(dictionary=True)
logging.basicConfig(level=logging.INFO)

FROM_DATE = "2020-05-01"


def data_export_1():
    logging.info('Loading...')

    from collections import defaultdict
    stats = defaultdict(lambda: {'checkedTexts': 0, 'checkedRecordings': 0, 'recordings': 0, 'recordingsMinutes': 0})
    cursor.execute(
        "select DATE(created_time) as date, COUNT(*) from checked_data_tuple where type!='SKIPPED' AND created_time > " + FROM_DATE + " GROUP BY date")
    results = cursor.fetchall()
    for result in results:
        if result['date']:
            stats[result['date']]['checkedTexts'] = result['COUNT(*)']
    cursor.execute(
        "select DATE(created_time) as date, COUNT(*) from checked_data_element where type!='SKIPPED' AND created_time > " + FROM_DATE + " GROUP BY date")
    results = cursor.fetchall()
    for result in results:
        if result['date']:
            stats[result['date']]['checkedTexts'] = result['COUNT(*)']

    cursor.execute(
        "select DATE(created_time) as date, COUNT(*) from data_element inner join audio on audio.data_element_id=data_element.id where created_time > " + FROM_DATE + " GROUP BY date")
    results = cursor.fetchall()
    for result in results:
        if result['date']:
            logging.info("counting recordings of: " + str(result['date']))
            stats[result['date']]['checkedTexts'] = result['COUNT(*)']
            seconds = 0.0
            cursor.execute(
                "select path from data_element inner join audio on audio.data_element_id=data_element.id where DATE(created_time)= '" + str(
                    result['date']) + "'")
            paths = cursor.fetchall()
            for path in paths:
                seconds += len(AudioSegment.from_file(os.path.join(base_dir, path['path']))) / 1000
            stats[result['date']]['recordingsMinutes'] = seconds / 60
    logging.info(stats)
    # TODO save dict as csv
    logging.info('Done!')


if __name__ == '__main__':
    data_export_1()
