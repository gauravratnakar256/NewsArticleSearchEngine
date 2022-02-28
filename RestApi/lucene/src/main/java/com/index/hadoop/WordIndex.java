package com.index.hadoop;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("WordIndex")
public class WordIndex {

    String word;
    List<IndexData> index;

    public WordIndex(String word, List<IndexData> index) {
        this.word = word;
        this.index = index;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<IndexData> getIndex() {
        return index;
    }

    public void setIndex(List<IndexData> index) {
        this.index = index;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WordIndex{");
        sb.append("word='").append(word).append('\'');
        sb.append(", index=").append(index);
        sb.append('}');
        return sb.toString();
    }
}
