package com.search.nyt;

import com.index.hadoop.Articles;
import com.index.hadoop.ParseReducerOutput;
import com.index.hadoop.TermFrequency;
import com.index.hadoop.WordIndex;
import com.index.lucene.LuceneIndexReader;
import com.mongodb.BasicDBObject;
import org.apache.lucene.queryparser.classic.ParseException;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
public class QueryController {

    @Value("${index.lucene.path}")
    String luceneIndexPath;

    @Value("${index.hadoop.path}")
    String hadoopIndexPath;

    private final MongoTemplate mongoTemplate;

    public QueryController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/lucene/fetch")
    public JSONArray getArticlesUsingLucene(@RequestParam(name = "query") String query, @RequestParam(name = "skip") int skip) throws ParseException, IOException {

        String luceneIndexPath = "C:\\IRProject\\Index";

        LuceneIndexReader reader = new LuceneIndexReader(luceneIndexPath);
        JSONArray articles = reader.search(query, skip);

        return articles;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/hadoop/fetch")
    public List<WordIndex> getArticlesUsingHadoop(@RequestParam(name = "query") String query, @RequestParam(name = "skip") int skip) throws ParseException, IOException {

        Map<String, ArrayList<String[]>> map = new HashMap<>();
        TermFrequency termFrequency = new TermFrequency(map);
        List<String> words = termFrequency.getTokenizedWords(query);

        Query q = new Query();
        q.addCriteria(Criteria.where("word").in(words));
        List<WordIndex> wordIndex = mongoTemplate.find(q, WordIndex.class);

        System.out.println(wordIndex.size());
        System.out.println(wordIndex.get(0).getIndex());
        return wordIndex;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/hadoop/buildInvertedIndex")
    public void buildInvertedIndex() throws ParseException, IOException {
        System.out.println("Initiating inverted index building process...");
        ParseReducerOutput parseReducerOutput = new ParseReducerOutput(hadoopIndexPath, mongoTemplate);
        System.out.println("Inverted index build completed!");
    }

}
