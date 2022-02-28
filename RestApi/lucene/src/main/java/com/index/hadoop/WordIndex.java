package com.index.hadoop;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("WordIndex")
public class WordIndex {

    String word;
    String index;

    public WordIndex(String word, String index) {
        this.word = word;
        this.index = index;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WordIndex{");
        sb.append("word='").append(word).append('\'');
        sb.append(", index='").append(index).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
