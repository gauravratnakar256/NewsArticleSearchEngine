from bs4 import BeautifulSoup

try:
    import urllib.request as urllib2
except ImportError:
    import urllib2
import re
from collections import deque
import json
import os.path


class Crawler:
    def __init__(self, threshold_words=150):
        self.block_number = 0
        self.global_index = 0
        self.url_queue = deque()
        self.url_map = {}
        self.title_map = {}
        self.threshold_words = threshold_words
        self.json_data = []

    def get_visited_urls(self, url_per_file):
        file_exists = os.path.exists('article_map.json')
        if file_exists:
            with open('article_map.json', 'r') as article_map:
                self.url_map = json.load(article_map)
                self.global_index = len(self.url_map)
                print(self.global_index)
                last_url = list(self.url_map.keys())[-1]
                print('last visited url {0}'.format(last_url))
                self.block_number = int(self.global_index / url_per_file) + 1
                return last_url
        else:
            return None

    def write_json(self):
        filename = "data/block_" + str(self.block_number) + ".json"
        self.block_number += 1
        with open(filename, "w") as outfile:
            json.dump(self.json_data, outfile)
            outfile.close()
            self.json_data = []

    def write_urls(self):
        filename = "article_map.json"
        with open(filename, "w") as outfile:
            json.dump(self.url_map, outfile)
            outfile.close()

    def remove_excess_url_content(self, url_list):
        final_list = []

        for url in url_list:
            index = url.find(".html")
            parsed_url = url[:index + 5]
            if index == -1:
                continue
            else:
                final_list.append(parsed_url)

        del url_list[:]
        url_list = []

        for url in final_list:
            if url.startswith("http://www.nytimes.com") or url.startswith("https://www.nytimes.com"):
                url_list.append(url)
        return url_list

    def extract_href(self, data_received):
        href_links = []
        soup = BeautifulSoup(data_received, "html.parser")
        for itHref in soup.findAll('a', href=True):
            href_links.append(itHref.get('href'))
        return href_links

    def parse_article_data(self, data_received, url, current_index):
        if current_index == 0:
            return True

        soup = BeautifulSoup(data_received, "html.parser")

        if soup.find('html').get('lang') != "en":
            return False

        body = soup.find('div', class_="StoryBodyCompanionColumn")

        article_body = ""
        number_of_words = 0

        if body is not None:
            for para in soup.findAll('p'):
                if para is None:
                    continue
                content = para.getText()
                content = re.sub(r"\n+", " ", content)
                number_of_words += len(content.split())
                article_body += content
        else:
            return False

        if number_of_words >= self.threshold_words:
            article_date = ""
            article_title = ""
            article_summary = ""

            time = soup.find('time')
            if time is not None:
                article_date = time.get('datetime')[0:19] + "Z"

            h1 = soup.find('h1')
            if h1 is not None:
                article_title = h1.get_text()

            if article_title not in self.title_map:
                self.title_map[article_title] = True
            else:
                return False

            p = soup.find("p", id="article-summary")
            if p is not None:
                article_summary = p.get_text()

            self.json_data.append(
                {
                    "articleID": current_index,
                    "articleUrl": url,
                    "articleDate": article_date,
                    "articleTitle": article_title,
                    "articleSummary": article_summary,
                    "articleBody": article_body
                }
            )
            return True
        else:
            return False

    def extract_href_for_last_visited_url(self, last_visited_url):
        href_links = []
        try:
            request = urllib2.urlopen(last_visited_url)
            data_received = request.read()
            href_links = self.extract_href(data_received)
            href_links = self.remove_excess_url_content(href_links)
        except Exception as e:
            print("Exception extracting href for last visited urls: " + str(e))
            pass
        return href_links

    def crawl_website(self, url, current_index):
        href_links = []
        flag = False

        try:
            request = urllib2.urlopen(url)
            data_received = request.read()
            flag = self.parse_article_data(data_received, url, current_index)
            href_links = self.extract_href(data_received)
            href_links = self.remove_excess_url_content(href_links)
        except Exception as e:
            print("Exception Crawl Website: " + str(e))
            pass
        return href_links, flag

    def crawl(self, start, url_limit, url_per_file):
        self.url_queue = deque()
        start_url = self.get_visited_urls(url_per_file)
        if start_url is not None:
            start = start_url
            href_list = self.extract_href_for_last_visited_url(start_url)
            self.url_queue.extend(href_list)

        try:
            global_crawl_index = self.global_index + 1
            self.url_queue.append(start)
            url_limit = url_limit - len(self.url_map)
            while global_crawl_index < url_limit:
                if len(self.url_queue) == 0:
                    break
                url = self.url_queue.popleft()
                if url not in self.url_map:
                    self.url_map[url] = True

                    current_index = global_crawl_index
                    href_list, should_increment_crawler_index = self.crawl_website(url, current_index)
                    self.url_queue.extend(href_list)

                    if should_increment_crawler_index:
                        global_crawl_index += 1
                        if global_crawl_index % url_per_file == 0:
                            self.write_json()
            self.write_json()
            self.write_urls()
        except Exception as e:
            print('Error Crawl: ' + str(e))
            self.write_json()
            self.write_urls()
