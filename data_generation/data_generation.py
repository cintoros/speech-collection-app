from xml.etree import ElementTree

import os
import requests
from google.cloud import texttospeech
from google.oauth2 import service_account

from config import *


# based on https://azure.microsoft.com/en-us/pricing/details/cognitive-services/speech-services/
# and https://docs.microsoft.com/en-us/azure/cognitive-services/speech-service/quickstart-python-text-to-speech
# for pricing see https://azure.microsoft.com/en-us/pricing/details/cognitive-services/speech-services/
# for voices see https://docs.microsoft.com/en-us/azure/cognitive-services/speech-service/language-support#standard-voices
class MicrosoftApi:
    voices = ["de-CH-Karsten", "de-DE-Hedda", "de-DE-Stefan-Apollo", "de-DE-HeddaRUS"]
    voiceIndex = 0;

    def get_token(self):
        fetch_token_url = 'https://' + AZURE_APPLICATION_REGION + '.api.cognitive.microsoft.com/sts/v1.0/issueToken'
        headers = {
            'Ocp-Apim-Subscription-Key': AZURE_APPLICATION_KEY
        }
        response = requests.post(fetch_token_url, headers=headers)
        access_token = str(response.text)
        return access_token

    def request_next(self):
        basePath = os.path.join(BASE_DIR, "data_generation", "azure_cloud")
        os.makedirs(name=basePath, exist_ok=True)

        base_url = 'https://' + AZURE_APPLICATION_REGION + '.tts.speech.microsoft.com/'
        path = 'cognitiveservices/v1'
        constructed_url = base_url + path
        # TODO this token is only valid for 10 minutes? -> refresh?
        headers = {
            'Authorization': 'Bearer ' + self.get_token(),
            'Content-Type': 'application/ssml+xml',
            # see https://docs.microsoft.com/en-us/azure/cognitive-services/speech-service/rest-text-to-speech#audio-outputs
            'X-Microsoft-OutputFormat': 'audio-24khz-160kbitrate-mono-mp3',
            'User-Agent': 'YOUR_RESOURCE_NAME'
        }
        voiceName = self.voices[self.voiceIndex]

        xml_body = ElementTree.Element('speak', version='1.0')
        xml_body.set('{http://www.w3.org/XML/1998/namespace}lang', 'en-US')
        voice = ElementTree.SubElement(xml_body, 'voice')
        voice.set('{http://www.w3.org/XML/1998/namespace}lang', 'en-US')
        voice.set('name', voiceName)
        voice.text = "Hallo Welt das hier ist ein deutscher Text gesprochen von einem Programm."
        body = ElementTree.tostring(xml_body)

        response = requests.post(constructed_url, headers=headers, data=body)
        if response.status_code == 200:
            with open(os.path.join(basePath, "sample-" + voiceName + ".mp3"), 'wb') as audio:
                audio.write(response.content)
                print("\nStatus code: " + str(response.status_code) +
                      "\nYour TTS is ready for playback.\n")
        else:
            print("\nStatus code: " + str(response.status_code) +
                  "\nSomething went wrong. Check your subscription key and headers.\n")


# based on https://cloud.google.com/text-to-speech/docs/reference/libraries#cloud-console
# for pricing see https://cloud.google.com/text-to-speech/pricing
# for languages/voices see https://cloud.google.com/text-to-speech/docs/voices
class GoogleApi:
    voices = ["de-DE-Standard-A", "de-DE-Standard-B", "de-DE-Standard-E", "de-DE-Standard-F"]
    voiceIndex = 0
    os.makedirs(name=os.path.join(BASE_DIR, "data_generation", "google_cloud"), exist_ok=True)
    # Instantiates a client
    credentials = service_account.Credentials.from_service_account_file(GOOGLE_APPLICATION_CREDENTIALS)
    client = texttospeech.TextToSpeechClient(credentials=credentials)

    def request_next(self):
        # Set the text input to be synthesized
        text = "Hallo Welt das hier ist ein deutscher Text gesprochen von einem Programm."
        synthesis_input = texttospeech.types.SynthesisInput(text=text)

        # Build the voice request, select the language and voice
        voice = texttospeech.types.VoiceSelectionParams(language_code="de-DE", name=self.voices[self.voiceIndex])
        # Select the type of audio file you want returned
        audio_config = texttospeech.types.AudioConfig(audio_encoding=texttospeech.enums.AudioEncoding.MP3)
        # Perform the text-to-speech request on the text input with the selected voice parameters and audio file type
        response = self.client.synthesize_speech(synthesis_input, voice, audio_config)

        # The response's audio_content is binary.
        with open(os.path.join(BASE_DIR, "data_generation", "google_cloud", "google_cloud_n.mp3"), 'wb') as out:
            # Write the response to the output file.
            out.write(response.audio_content)
            print('Audio content written to file')


class SequentualApiFetcher:
    apis: [GoogleApi(), MicrosoftApi()]
    apiIndex = 0

    def request_next(self, text: str):
        # TODO finish implementeation
        self.apis[self.apiIndex].request_next();
