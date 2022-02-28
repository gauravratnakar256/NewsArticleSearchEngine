package com.index.hadoop;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document("test")
public class Articles {

    int articleID;
    String articleUrl;
    String articleDate;
    String articleTitle;
    String articleBody;
    String articleSummary;

    public Articles(int articleID, String articleUrl, String articleDate, String articleTitle, String articleBody, String articleSummary) {
        this.articleID = articleID;
        this.articleUrl = articleUrl;
        this.articleDate = articleDate;
        this.articleTitle = articleTitle;
        this.articleBody = articleBody;
        this.articleSummary = articleSummary;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleBody() {
        return articleBody;
    }

    public void setArticleBody(String articleBody) {
        this.articleBody = articleBody;
    }

    public String getArticleSummary() {
        return articleSummary;
    }

    public void setArticleSummary(String articleSummary) {
        this.articleSummary = articleSummary;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Articles{");
        sb.append("articleID='").append(articleID).append('\'');
        sb.append(", articleUrl='").append(articleUrl).append('\'');
        sb.append(", articleDate='").append(articleDate).append('\'');
        sb.append(", articleTitle='").append(articleTitle).append('\'');
        sb.append(", articleBody='").append(articleBody).append('\'');
        sb.append(", articleSummary='").append(articleSummary).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
