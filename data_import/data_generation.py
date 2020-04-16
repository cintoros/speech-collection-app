from gtts import gTTS


def googleTextToSpeech(text: str):
    tts = gTTS(text, lang='de')
    tts.save('hello.mp3')


def run():
    googleTextToSpeech("Hallo Welt.")


if __name__ == '__main__':
    run()
