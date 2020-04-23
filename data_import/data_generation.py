import os

import pyttsx3
from espeakng import ESpeakNG
from gtts import gTTS

import abox
from config import *


# first make google and bing official api as they seem to be better supported than acapella -> and have no rate limit etc.


def ttsGTTS(text: str):
    """
     current limit can be seen here https://cloud.google.com/text-to-speech/quotas
     see also https://github.com/pndurette/gTTS/issues/117#issuecomment-458634045
    :param text:
    """
    tts = gTTS(text, lang='de')
    tts.save(os.path.join(base_dir, 'data_generation', "gTTS.mp3"))


# TODO not sure about the quality of espeak/espeak-ng
#  could be usefully to generate data with different pitch,speed volume etc.

# requires espeak(all) , nsss(mac) or sapi5(windows) to be installed locally
def ttsPyttsx3(text: str):
    engine = pyttsx3.init()
    engine.setProperty('voice', 'german')
    # rate: int = engine.getProperty('rate')
    # engine.setProperty('rate', rate - 0)
    # volume: int = engine.getProperty('volume')
    # engine.setProperty('volume', volume - 0.0)
    print("save_to_file")
    engine.save_to_file(text, os.path.join(base_dir, 'data_generation', "pyttsx3.mp3"))
    print("runAndWait")
    engine.runAndWait()
    print("finished?")


# requires espeak-ng  to be installed locally
def ttsEsng(text: str):
    esng = ESpeakNG()
    esng.voice = 'german'
    esng.voice = 'German'
    # esng.pitch = 80
    # esng.speed = 120
    # esng.volume = 200
    esng.say(text)
    # esng.runAndWait()
    for v in esng.voices:
        print(v)
    wavs = esng.synth_wav(text)
    if wavs:
        print("present")
        with open(os.path.join(base_dir, 'data_generation', "esng.wav"), 'wb') as output:
            output.write(wavs)
    else:
        print("nope")
    # wav = wave.open(StringIO.StringIO(wavs))
    # print(wav.getnchannels(), wav.getframerate(), wav.getnframes())


# TODO https://acapela-box.com/AcaBox/index.php does not serve an api.
#  For a cloud base api see https://www.acapela-group.com/solutions/acapela-vaas/
# TODO not sure about this implementation -> needs python-pympv or other library that provides codecs
def ttsAbox(text: str):
    box = abox.Abox(output=os.path.join(base_dir, 'data_generation', "abox.ogg"), voice="willbadguy22k")
    url = box.query(text)
    ret = abox.ab_download(url, box.option_l)
    if not ret:
        print("Aborting")


def googleApi():
    # TODO implement
    print("not implemented")


def bingApi():
    # TODO implement
    print("not implemented")


def mozillaTss():
    # see https://github.com/mozilla/TTS
    # TODO maybe some of the data of the open source datasets might be useful instead?
    #  https://www.caito.de/2019/01/the-m-ailabs-speech-dataset/
    print("not implemented")


def run(text: str):
    # ttsGTTS(text)
    # ttsPyttsx3(text)
    # ttsEsng(text)
    # acapela(text)
    ttsAbox(text)


if __name__ == '__main__':
    run("Hallo Welt das hier ist ein deutscher Text gesprochen von einem Programm.")