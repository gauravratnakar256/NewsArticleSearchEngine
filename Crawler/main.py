import crawler

if __name__ == '__main__':
    first_article_link = "https://www.nytimes.com/2022/02/04/us/politics/government-national-security-announcements.html"
    crawler.Crawler().crawl(first_article_link, 120000)
