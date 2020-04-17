import os
from typing import Iterable

import pyttsx3
from gtts import gTTS
from config import *


def ttsGTTS(text: str):
    tts = gTTS(text, lang='de')
    tts.save(os.path.join(base_dir, 'data_generation', "gTTS.mp3"))


# requires espeak , nsss or sapi5 to be installed locally
def ttsPyttsx3(text: str):
    engine = pyttsx3.init()
    voices: Iterable = engine.getProperty('voices')
    for voice in voices:
        print(voice)
    # TODO speech rate/volume can be changed for additional data
    engine.setProperty('voice', 'german')
    rate: int = engine.getProperty('rate')
    engine.setProperty('rate', rate - 0)
    volume: int = engine.getProperty('volume')
    engine.setProperty('volume', volume - 0.0)
    print("save_to_file")
    engine.save_to_file(text, os.path.join(base_dir, 'data_generation', "pyttsx3.mp3"))
    print("runAndWait")
    engine.runAndWait()
    print("finished?")


def run(text: str):
    # ttsGTTS(text)
    # ttsPyttsx3(text)


if __name__ == '__main__':
    run("Hallo Welt das hier ist ein deutscher Text gesprochen von einem Text to Speech Programm.")
