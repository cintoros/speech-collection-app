import os
from typing import Iterable

import pyttsx3
from gtts import gTTS
from config import *


def ttsGTTS(text: str):
    tts = gTTS(text, lang='de')
    tts.save(os.path.join(base_dir, 'data_generation', "gTTS.mp3"))


# requires espeak(all) , nsss(mac) or sapi5(windows) to be installed locally
# TODO not sure about the quality
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


# https://acapela-box.com/AcaBox/index.php does not serve an api for a cloud base api see https://www.acapela-group.com/solutions/acapela-vaas/
def acapela(text: str):
    print("not implemented")


def run(text: str):
    # ttsGTTS(text)
    # ttsPyttsx3(text)
    acapela(text)


if __name__ == '__main__':
    run("Hallo Welt das hier ist ein deutscher Text gesprochen von einem Programm.")
