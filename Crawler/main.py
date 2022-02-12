import crawler
import sys
import os

if __name__ == '__main__':
    first_article_link = "https://www.nytimes.com/"
    url_per_file = 1000
    url_limit = 150000

    if len(sys.argv) < 4:
        print("Too few arguments!")
        print("python main.py <start-url> <number-of-urls-per-file> <number-of-articles-to-crawl>")
    else:
        first_article_link = sys.argv[1]
        url_per_file = sys.argv[2]
        url_limit = sys.argv[3]
        jsonPath = "data"

        if not os.path.isdir(jsonPath):
            os.mkdir(jsonPath)
        crawler.Crawler().crawl(first_article_link, int(url_limit), int(url_per_file))
