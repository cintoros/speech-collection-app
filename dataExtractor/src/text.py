import nltk.data
import mysql.connector
from mutagen.mp3 import MP3

#download the punkt package
#nltk.download()
tokenizer = nltk.data.load('tokenizers/punkt/english.pickle')
file = open('C:/Users/Jonas/Desktop/DeutschAndreaErzaehlt/87/transcript.txt')
data = file.read()
fileLength = len(data)


class Snippet:
    id = 0
    length = 0
    sentence = ''
    startPos = 0
    endPos = 0
    result = 0
    alength = 0
    aStartPos = 0
    aEndPos = 0


lengthArray = []
index = 0

# Add Snippet Object to LengthArray
for te in tokenizer.tokenize(data):
    index = index + 1
    snippet = Snippet()

    snippet.id = index
    snippet.length = len(te)
    snippet.sentence = te
    snippet.result = snippet.length / fileLength

    lengthArray.append(snippet)

for element in lengthArray:
    element.startPos = data.find(element.sentence)
    element.endPos = data.find(element.sentence) + len(element.sentence)

# ---------------------

audio = MP3('C:/Users/Jonas/Desktop/DeutschAndreaErzaehlt/87/audio.mp3')
audioFileLength = audio.info.length
print(audioFileLength)

pos = 0

for u in lengthArray:
    u.alength = u.result * audioFileLength
    pos = pos + u.alength
    u.aStartPos = pos
    u.aEndPos = pos + u.alength

# ----------------------

mydb = mysql.connector.connect(
    host='localhost',
    user='root',
    passwd='password',
    database='labeling-tool'
)

mycursor = mydb.cursor()

for file in lengthArray:
    sql = 'insert into textAudioIndex (id, samplingRate, textStartPos, textEndPos, audioStartPos, audioEndPos, speakerKey, labeled, correct, wrong, transcript_file_id) values (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)'
    val = (file.id , '44100', file.startPos, file.endPos, file.aStartPos, file.aEndPos, 1, 0, 0, 0, 87)
    mycursor.execute(sql, val)
    mydb.commit()

