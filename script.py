import os
import json

article_map = set()
duplicate_count = 0
count = 0
exception_count = 0
for filename in os.listdir("C:/UCR/IR Project/NewsArticleSearchEngine/Crawler/data"):
    print ('Processing : {0}'.format(filename))
    file = "C:/UCR/IR Project/NewsArticleSearchEngine/Crawler/data2/"+filename
    json_data = []
    with open(file, 'r') as data:
        articles = json.load(data)
        for article in articles:
            count = count + 1
            try:
                title = article['articleTitle']
                if title not in article_map:
                    article_map.add(title)
                    json_data.append(article)
            except:
                exception_count = exception_count + 1
                pass
    file = "C:/UCR/IR Project/NewsArticleSearchEngine/Crawler/data2/"+filename
    with open(file, "w") as outfile:
        json.dump(json_data, outfile)
        outfile.close()
        json_data = []
print('done')
#print(count)
#print(len(article_map))
#print(exception_count)