from contextlib import closing
from multiprocessing import Pool
from xml.etree import ElementTree

import io
import logging
import mysql.connector
import os
import pandas
import requests
import threading
import time
from boto3 import Session
from google.cloud import texttospeech
from google.oauth2 import service_account
from pydub import AudioSegment
from typing import Union

from config import *

logging.basicConfig(format='%(asctime)s %(message)s', datefmt='%Y-%m-%d %H:%M:%S')
logger = logging.getLogger('data_generations')
logger.setLevel(logging.INFO)
# NOTE: we use a randomized file so we do not have similar sentences each time
data_file = os.path.join(BASE_DIR, "common_voice", "data_de", "sentences2.csv")


class MicrosoftApi:
    """
    based on https://azure.microsoft.com/en-us/pricing/details/cognitive-services/speech-services/
    and https://docs.microsoft.com/en-us/azure/cognitive-services/speech-service/quickstart-python-text-to-speech
    for pricing see https://azure.microsoft.com/en-us/pricing/details/cognitive-services/speech-services/
    for voices see https://docs.microsoft.com/en-us/azure/cognitive-services/speech-service/language-support#standard-voices
    5 request per 1sec see https://docs.microsoft.com/en-us/azure/cognitive-services/speech-service/faq-text-to-speech
    """

    def __init__(self):
        self.base_path = os.path.join(BASE_DIR, "data_generation", "threaded")
        os.makedirs(name=self.base_path, exist_ok=True)
        self.voices = ["de-CH-Karsten", "de-DE-Hedda", "de-DE-Stefan-Apollo", "de-AT-Michael"]
        self.voice_index = 0
        self.constructed_url = 'https://' + AZURE_APPLICATION_REGION + '.tts.speech.microsoft.com/' + 'cognitiveservices/v1'
        self.headers = {
            'Authorization': 'Bearer ' + self.get_token(),
            'Content-Type': 'application/ssml+xml',
            # see https://docs.microsoft.com/en-us/azure/cognitive-services/speech-service/rest-text-to-speech#audio-outputs
            'X-Microsoft-OutputFormat': 'riff-16khz-16bit-mono-pcm',
            'User-Agent': 'YOUR_RESOURCE_NAME'
        }
        self.xml_body = ElementTree.Element('speak', version='1.0')
        self.xml_body.set('{http://www.w3.org/XML/1998/namespace}lang', 'en-US')
        self.voice = ElementTree.SubElement(self.xml_body, 'voice')
        self.voice.set('{http://www.w3.org/XML/1998/namespace}lang', 'en-US')

    def get_token(self):
        self.last_token_time = time.time()
        fetch_token_url = 'https://' + AZURE_APPLICATION_REGION + '.api.cognitive.microsoft.com/sts/v1.0/issueToken'
        headers = {
            'Ocp-Apim-Subscription-Key': AZURE_APPLICATION_KEY
        }
        response = requests.post(fetch_token_url, headers=headers)
        return str(response.text)

    def get_next_voice(self, id: int) -> Union[None, str]:
        l = len(self.voices)
        for i in range(0, l):
            self.voice_index = (self.voice_index + i) % l
            voice_name = self.voices[self.voice_index]
            file_name = str(id) + "_" + voice_name + ".flac"
            if not os.path.exists(os.path.join(self.base_path, file_name)):
                return voice_name
        return None

    def request_next(self, text: str, id: int) -> Union[None, str]:
        # refresh token every 9 minutes (token is valid for 10 minutes)
        if ((time.time() - self.last_token_time) > (60 * 9)):
            logger.info("updated old token")
            self.headers['Authorization'] = 'Bearer ' + self.get_token()

        voice_name = self.get_next_voice(id)
        if voice_name is None:
            return None
        self.voice.set('name', voice_name)
        self.voice.text = text
        response = requests.post(self.constructed_url, headers=self.headers, data=ElementTree.tostring(self.xml_body))
        if response.status_code == 200:
            # save the result.
            file_name = os.path.join(self.base_path, str(id) + "_" + voice_name + ".flac")
            audio = AudioSegment.from_wav(io.BytesIO(response.content))
            audio = audio.set_channels(1)
            audio.export(file_name, format="flac")
        else:
            logger.warning(
                f"\nStatus code: {str(response.status_code)}\nSomething went wrong. Check your subscription key and headers.\n")
            logger.warning(response)
            raise Exception('status code ', str(response.status_code))
        self.voice_index += 1
        if self.voice_index >= len(self.voices):
            self.voice_index = 0
        return voice_name


class GoogleApi:
    """
    based on https://cloud.google.com/text-to-speech/docs/reference/libraries#cloud-console
    for pricing see https://cloud.google.com/text-to-speech/pricing
    for languages/voices see https://cloud.google.com/text-to-speech/docs/voices
    """

    def __init__(self):
        self.base_path = os.path.join(BASE_DIR, "data_generation", "threaded")
        os.makedirs(name=self.base_path, exist_ok=True)
        self.voices = ["de-DE-Standard-A", "de-DE-Standard-B", "de-DE-Standard-E", "de-DE-Standard-F"]
        self.voice_index = 0
        # Instantiates a client
        self.credentials = service_account.Credentials.from_service_account_file(GOOGLE_APPLICATION_CREDENTIALS)
        self.client = texttospeech.TextToSpeechClient(credentials=self.credentials)
        # Select the type of audio file you want returned
        self.audio_config = texttospeech.types.AudioConfig(audio_encoding=texttospeech.enums.AudioEncoding.LINEAR16)

    def get_next_voice(self, id: int) -> Union[None, str]:
        l = len(self.voices)
        for i in range(0, l):
            self.voice_index = (self.voice_index + i) % l
            voice_name = self.voices[self.voice_index]
            file_name = str(id) + "_" + voice_name + ".flac"
            if not os.path.exists(os.path.join(self.base_path, file_name)):
                return voice_name
        return None

    def request_next(self, text: str, id: int) -> Union[None, str]:
        # Set the text input to be synthesized
        # NOTE: google api does not like some special characters.
        synthesis_input = texttospeech.types.SynthesisInput(text=text.replace('"', ''))
        voice_name = self.get_next_voice(id)
        if voice_name is None:
            return None
        # Build the voice request, select the language and voice
        voice = texttospeech.types.VoiceSelectionParams(language_code="de-DE", name=voice_name)
        # Perform the text-to-speech request on the text input with the selected voice parameters and audio file type
        response = self.client.synthesize_speech(synthesis_input, voice, self.audio_config)

        # save the result.
        file_name = os.path.join(self.base_path, str(id) + "_" + voice_name + ".flac")
        audio = AudioSegment.from_wav(io.BytesIO(response.audio_content))
        audio = audio.set_channels(1)
        audio.export(file_name, format="flac")

        self.voice_index += 1
        if self.voice_index >= len(self.voices):
            self.voice_index = 0
        return voice_name


class AwsApi:
    """
    see https://docs.aws.amazon.com/polly/latest/
    for voices see see https://docs.aws.amazon.com/polly/latest/dg/voicelist.html
    """

    def __init__(self):
        self.base_path = os.path.join(BASE_DIR, "data_generation", "threaded")
        os.makedirs(name=self.base_path, exist_ok=True)
        self.voices = ["Marlene", "Vicki", "Hans"]
        self.voice_index = 0
        # Instantiates a client
        # Create a client using the credentials and region defined in the [adminuser]
        # section of the AWS credentials file (~/.aws/credentials).
        self.session = Session(profile_name="adminuser")
        self.polly = self.session.client("polly")

    def get_next_voice(self, id: int) -> Union[None, str]:
        l = len(self.voices)
        for i in range(0, l):
            self.voice_index = (self.voice_index + i) % l
            voice_name = self.voices[self.voice_index]
            file_name = str(id) + "_" + voice_name + ".flac"
            if not os.path.exists(os.path.join(self.base_path, file_name)):
                return voice_name
        return None

    def request_next(self, text: str, id: int) -> Union[None, str]:
        voice_name = self.get_next_voice(id)
        if voice_name is None:
            return None
        response = self.polly.synthesize_speech(Text=text, OutputFormat="pcm", SampleRate='16000', VoiceId=voice_name)
        # Access the audio stream from the response
        if "AudioStream" in response:
            # Note: Closing the stream is important because the service throttles on the
            # number of parallel connections. Here we are using contextlib.closing to
            # ensure the close method of the stream object will be called automatically
            # at the end of the with statement's scope.m:
            with closing(response["AudioStream"]) as stream:
                result = stream.read()
                # save the result.
                file_name = os.path.join(self.base_path, str(id) + "_" + voice_name + ".flac")
                # convert aws pcm see https://jun711.github.io/aws/convert-aws-polly-synthesized-speech-from-pcm-to-wav-format/
                audio = AudioSegment(result, sample_width=2, frame_rate=16000, channels=1)
                audio.export(file_name, format="flac")
        self.voice_index += 1
        if self.voice_index >= len(self.voices):
            self.voice_index = 0
        return voice_name


class ThreadedApiFetcher:
    def __init__(self):
        self.dataset = pandas.read_csv(data_file)

    def request_all(self):
        threads = list()
        # TODO manually add batch ids
        apis = [[GoogleApi(), 13], [MicrosoftApi(), 14], [AwsApi(), 15]]
        for api_fetcher, batch_id in apis:
            thread = threading.Thread(target=self.request_all_batch, args=[api_fetcher, batch_id])
            threads.append(thread)
            thread.start()
            logger.info("thread started")
        for thread in threads:
            thread.join()
            logger.info("thread done")
        logger.info("all threads done")

    def request_all_batch(self, api_fetcher, batch_id):
        connection = mysql.connector.connect(host=HOST, database=DATABASE, user=USER, password=PASSWORD)
        cursor = connection.cursor(dictionary=True)
        connection.autocommit = True
        cursor.execute("select * from batch where id=%s", [batch_id])
        result = cursor.fetchone()
        current_text_id = result["current_text_id"]
        end_text_id = result["end_text_id"]
        current_id = current_text_id
        try:
            for i in range(current_text_id, end_text_id):
                try:
                    voice = api_fetcher.request_next(self.dataset.sentences[i], i + 1)
                except:
                    logger.info(str(api_fetcher.__class__.__name__) + ": except caught")
                    voice = api_fetcher.request_next(self.dataset.sentences[i], i + 1)
                if voice is not None:
                    cursor.execute("INSERT INTO generated_audio_2(text_id,voice,batch_id) VALUE (%s,%s,%s)",
                                   [i + 1, voice, batch_id])
                cursor.execute("UPDATE batch SET current_text_id=%s WHERE id=%s", [i + 1, batch_id])
                current_id = i
                # sleep 3s to prevent over-fetching apis especially azure.
                time.sleep(3.0)
                if (i % 10 == 0):
                    logger.info(str(api_fetcher.__class__.__name__) + ": fetched with current_id(+1): " + str(i))
        finally:
            logger.info(str(api_fetcher.__class__.__name__) + ": finished with  current_id(+1): " + str(current_id))


class DataGeneration:
    @staticmethod
    def batch_prepare() -> None:
        """
        used to prepare the batches for the different apis.
        """
        connection = mysql.connector.connect(host=HOST, database=DATABASE, user=USER, password=PASSWORD)
        cursor = connection.cursor(dictionary=True)
        cursor.execute(
            "CREATE TABLE IF NOT EXISTS batch( id BIGINT NOT NULL AUTO_INCREMENT, api TEXT NOT NULL, start_text_id BIGINT NOT NULL, end_text_id BIGINT NOT NULL, current_text_id BIGINT NOT NULL, characters BIGINT NOT NULL, created_time DATETIME DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (id) );")
        cursor.execute(
            "CREATE TABLE IF NOT EXISTS generated_audio_2 ( id BIGINT NOT NULL AUTO_INCREMENT, text_id BIGINT NOT NULL, batch_id BIGINT NOT NULL, voice TEXT NOT NULL, PRIMARY KEY (id), FOREIGN KEY (batch_id)REFERENCES batch(id)) ENGINE = INNODB DEFAULT CHARSET = UTF8MB4;")
        dataset = pandas.read_csv(data_file)
        connection.autocommit = True
        cursor.execute("SELECT IFNULL(MAX(end_text_id), 0) AS last_id FROM batch;")
        result = cursor.fetchone()
        last_id = result['last_id']
        apis = [["GOOGLE", 4000000], ["AZURE", 5000000], ["AWS", 5000000]]
        # use lower limits when testing
        # apis = [["GOOGLE", 4000000 - 10000], ["AZURE", 5000000 - 10000], ["AWS", 5000000 - 10000]]
        for api, limit in apis:
            start_id = last_id
            characters = 0
            while True:
                if last_id >= len(dataset):
                    logger.info("no more text")
                    break
                text = dataset.sentences[last_id]
                characters += len(text)
                if (characters > limit):
                    characters -= len(text)
                    break
                last_id += 1
            cursor.execute(
                "INSERT INTO batch(api,start_text_id,end_text_id,current_text_id,characters) VALUE (%s,%s,%s,%s,%s)",
                [api, start_id, last_id, start_id, characters])
            logger.info("finished batch calculation for api: " + api)
        logger.info("finished batch calculation for all apis")

    @staticmethod
    def batch_run() -> None:
        fetcher = ThreadedApiFetcher()
        fetcher.request_all()

    @staticmethod
    def calc_stuff(file_data):
        try:
            audio = AudioSegment.from_file(
                os.path.join(os.path.join(BASE_DIR, "data_generation", "threaded", file_data)))
            return audio.duration_seconds
        except:
            return 0

    @staticmethod
    def batch_statistics() -> None:

        seconds = 0
        count = 0
        errors = 0
        files = os.listdir(os.path.join(BASE_DIR, "data_generation", "threaded"))
        agents = 16
        chunksize = 1000
        with Pool(processes=agents) as pool:
            result = pool.map(DataGeneration.calc_stuff, files, chunksize)
            errors += result.count(0)
            seconds += sum(result)
            count += len(result)
            print("sec: ", seconds, "h: ", seconds / 60 / 60, "count: ", count, "errors: ", errors)
        print("sec: ", seconds, "h: ", seconds / 60 / 60, "count: ", count, "errors: ", errors)


def batch_prepare():
    DataGeneration.batch_prepare()


if __name__ == '__main__':
    # TODO manually switch between modes
    #DataGeneration.batch_prepare()
    #DataGeneration.batch_run()
    DataGeneration.batch_statistics()
