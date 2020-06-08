import logging

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
    # init directories
    # TODO select all data
    # NOTE checked recording etc, can be selected directly (not sure how the from_date needs to be formatted?)
    # select DATE(created_time) as date, COUNT(*) from checked_data_tuple where type!='SKIPPED' AND created_time > "2020-05-01" GROUP BY date
    # select DATE(created_time) as date, COUNT(*) from checked_data_element where type!='SKIPPED' AND created_time > "2020-05-01" GROUP BY date
    seconds = 0.0
    # TODO implement llop
    miliseconds = len(AudioSegment.from_file("1.webm")) / 1000
    # TODO for the audio minutes per day we need to fetch loop over all the data for the specific day and then lookup the audio on the file system
    # select path from data_element inner join audio on audio.data_element_id=data_element.id where DATE(created_time)="2020-06-04"
    logging.info('Done!')


if __name__ == '__main__':
    data_export_1()
