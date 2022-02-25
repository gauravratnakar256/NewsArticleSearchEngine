import os
import re
from urllib.request import urlopen
import json

class InvertedIndex:

    def createInvertedIndex(self):
        self.mapper()
    
    def mapper(self):
        #Path getter
        blockPath = os.getcwd() + "/Crawler/data"
        #Create list of all block file names
        blocks = os.listdir(path=blockPath)
        #Initializing inverted index dictionary
        invertedIndex = dict()

        #Block traversing
        for block in blocks:
            res = open("Crawler/data/"+block)
            articles = json.load(res)
        
        #Article traversing
        for article in articles:
            articleBody = article["articleBody"].lower()
            articleTitle = article["articleTitle"].lower()
            articleID = str(article["articleID"])

            #Punctuation removal
            cleanBody = re.sub(r'[^\w\s]',' ', articleBody)
            cleanTitle = re.sub(r'[^\w\s]',' ', articleTitle)

            words = cleanBody.split() + cleanTitle.split()

            invertedIndex = self.reducer(block,articleID,words,invertedIndex)
            print(invertedIndex)

        res.close()
    
    def reducer(self,blockNumber,article,allWords,invertedIndexDict):
        for word in allWords:
                if word not in invertedIndexDict.keys():
                    invertedIndexDict[word] = {blockNumber:{str(article):1}}
                else:
                    invertedIndexDict[word][blockNumber][article] += 1
        return invertedIndexDict




invertedIndex = InvertedIndex()
invertedIndex.createInvertedIndex()



