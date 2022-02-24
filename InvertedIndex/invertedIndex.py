import os
import re
from urllib.request import urlopen
import json

class InvertedIndex:
    
    def createInvertedIndex(self):
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

            #Punctuation removal
            cleanBody = re.sub(r'[^\w\s]','', articleBody)
            cleanTitle = re.sub(r'[^\w\s]','', articleTitle)

            words = cleanBody.split() + cleanTitle.split()
            
            for word in words:
                if word not in invertedIndex.keys():
                    invertedIndex[word] = {block:1}
                else:
                    invertedIndex[word][block] += 1
            print(invertedIndex)

    
        res.close()


invertedIndex = InvertedIndex()
invertedIndex.createInvertedIndex()

# res = open("Crawler/data/block_94.json")
# dataJSON = json.load(res)
# print(dataJSON[0].keys())


