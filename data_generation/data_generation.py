from xml.etree import ElementTree

import logging
import mysql.connector
import os
import pandas
import requests
import time
from google.cloud import texttospeech
from google.oauth2 import service_account
from typing import Union

from config import *

logging.basicConfig(format='%(asctime)s %(message)s', datefmt='%Y-%m-%d %H:%M:%S')
logger = logging.getLogger('data_generations')
logger.setLevel(logging.INFO)


# stdout_handler = logging.StreamHandler(sys.stdout)
# logger.addHandler(stdout_handler)


# based on https://azure.microsoft.com/en-us/pricing/details/cognitive-services/speech-services/
# and https://docs.microsoft.com/en-us/azure/cognitive-services/speech-service/quickstart-python-text-to-speech
# for pricing see https://azure.microsoft.com/en-us/pricing/details/cognitive-services/speech-services/
# for voices see https://docs.microsoft.com/en-us/azure/cognitive-services/speech-service/language-support#standard-voices
# 5 request per 1sec see https://docs.microsoft.com/en-us/azure/cognitive-services/speech-service/faq-text-to-speech
class MicrosoftApi:
    def __init__(self):
        self.base_path = os.path.join(BASE_DIR, "data_generation", "sequentual")
        os.makedirs(name=self.base_path, exist_ok=True)
        self.voices = ["de-CH-Karsten", "de-DE-Hedda", "de-DE-Stefan-Apollo", "de-AT-Michael"]
        self.voice_index = 0
        self.constructed_url = 'https://' + AZURE_APPLICATION_REGION + '.tts.speech.microsoft.com/' + 'cognitiveservices/v1'
        self.headers = {
            'Authorization': 'Bearer ' + self.get_token(),
            'Content-Type': 'application/ssml+xml',
            # see https://docs.microsoft.com/en-us/azure/cognitive-services/speech-service/rest-text-to-speech#audio-outputs
            'X-Microsoft-OutputFormat': 'audio-24khz-160kbitrate-mono-mp3',
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
            file_name = str(id) + "_" + voice_name + ".mp3"
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
        file_name = str(id) + "_" + voice_name + ".mp3"
        if response.status_code == 200:
            with open(os.path.join(self.base_path, file_name), 'wb') as audio:
                audio.write(response.content)
        else:
            logger.warning(
                f"\nStatus code: {str(response.status_code)}\nSomething went wrong. Check your subscription key and headers.\n")
            logger.warning(response)
            raise Exception('status code ', str(response.status_code))
        self.voice_index += 1
        if self.voice_index >= len(self.voices):
            self.voice_index = 0
        return voice_name


# based on https://cloud.google.com/text-to-speech/docs/reference/libraries#cloud-console
# for pricing see https://cloud.google.com/text-to-speech/pricing
# for languages/voices see https://cloud.google.com/text-to-speech/docs/voices
class GoogleApi:
    def __init__(self):
        self.base_path = os.path.join(BASE_DIR, "data_generation", "sequentual")
        os.makedirs(name=self.base_path, exist_ok=True)
        self.voices = ["de-DE-Standard-A", "de-DE-Standard-B", "de-DE-Standard-E", "de-DE-Standard-F"]
        self.voice_index = 0
        # Instantiates a client
        self.credentials = service_account.Credentials.from_service_account_file(GOOGLE_APPLICATION_CREDENTIALS)
        self.client = texttospeech.TextToSpeechClient(credentials=self.credentials)

    def get_next_voice(self, id: int) -> Union[None, str]:
        l = len(self.voices)
        for i in range(0, l):
            self.voice_index = (self.voice_index + i) % l
            voice_name = self.voices[self.voice_index]
            file_name = str(id) + "_" + voice_name + ".mp3"
            if not os.path.exists(os.path.join(self.base_path, file_name)):
                return voice_name
        return None

    def request_next(self, text: str, id: int) -> Union[None, str]:
        # Set the text input to be synthesized
        synthesis_input = texttospeech.types.SynthesisInput(text=text)
        voice_name = self.get_next_voice(id)
        if voice_name is None:
            return None
        # Build the voice request, select the language and voice
        voice = texttospeech.types.VoiceSelectionParams(language_code="de-DE", name=voice_name)
        # Select the type of audio file you want returned
        audio_config = texttospeech.types.AudioConfig(audio_encoding=texttospeech.enums.AudioEncoding.MP3)
        # Perform the text-to-speech request on the text input with the selected voice parameters and audio file type
        response = self.client.synthesize_speech(synthesis_input, voice, audio_config)
        file_name = str(id) + "_" + voice_name + ".mp3"
        # The response's audio_content is binary.
        with open(os.path.join(self.base_path, file_name), 'wb') as out:
            # Write the response to the output file.
            out.write(response.audio_content)
        self.voice_index += 1
        if self.voice_index >= len(self.voices):
            self.voice_index = 0
        return voice_name


class SequentualApiFetcher:
    def __init__(self):
        self.connection = mysql.connector.connect(host=HOST, database=DATABASE, user=USER, password=PASSWORD)
        self.cursor = self.connection.cursor(dictionary=True)
        self.cursor.execute(
            "CREATE TABLE IF NOT EXISTS generated_audio(id BIGINT NOT NULL AUTO_INCREMENT,text_id BIGINT NOT NULL,"
            "voice TEXT NOT NULL,PRIMARY KEY (id))ENGINE = INNODB DEFAULT CHARSET = UTF8MB4;")
        self.dataset = pandas.read_csv(os.path.join(BASE_DIR, "common_voice", "sentences.csv"))
        self.apis = [GoogleApi(), MicrosoftApi()]
        self.connection.autocommit = True
        self.api_index = 0
        self.cursor.execute("select IFNULL(text_id, 0) as last_id from generated_audio order by id DESC LIMIT 1;")
        result = self.cursor.fetchone()
        self.last_id = result['last_id']

    def request_next(self, text: str, id: int):
        voice = self.apis[self.api_index].request_next(text, id)
        self.api_index += 1
        if self.api_index >= len(self.apis):
            self.api_index = 0
        if voice is not None:
            self.cursor.execute("INSERT INTO generated_audio(text_id,voice) VALUE (%s,%s)", [id, voice])

    def request_all(self, ):
        try:
            for i in range(self.last_id, len(self.dataset)):
                self.request_next(self.dataset.sentence[i], i + 1)
                # NOTE: the azure api seems has a relative low request limit per time(1m,10m,1h) that is not documented...
                #  0.6-0.7 seems to be mostly good
                # NOTE: after about 1 hour googe/azure may run into timeouts etc.
                # NOTE google does not like special charachters like " in the sentence. "_InactiveRpcError"
                self.last_id = i
                time.sleep(0.6)
                if (i % 100 == 0):
                    logger.info("fetched with lastId(+1): " + str(i))
        finally:
            logger.info("finished with lastId(+1): " + str(self.last_id))
                


if __name__ == '__main__':
    api_fetcher = SequentualApiFetcher()
    api_fetcher.request_all()
