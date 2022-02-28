package com.index.hadoop;

import java.util.*;

import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;


public class TermFrequency {
    private final int TOTAL_DOCUMENTS = 138200;
    private Map<String, ArrayList<String[]>> docsScanned;
    List<String> docIDs;

    public TermFrequency(Map<String, ArrayList<String[]>> docsScanned) {
        this.docsScanned = docsScanned;
    }

    public String[] CalculateTFIDF(String query, int pageNo) throws IOException {
        HashMap<String, Float> hm = new HashMap<>();
        for (String word : getTokenizedWords(query)) {
            if (docsScanned.containsKey(word)) {
                ArrayList<String[]> docsArray = docsScanned.get(word);
                for (String[] doc : docsArray) {
                    float score = CalculateTF(Integer.parseInt(doc[1]), Integer.parseInt(doc[2])) * CalculateIDF(docsArray.size());
                    if (!hm.containsKey(doc[0])) hm.put(doc[0], score);
                    else {
                        float newScore = hm.get(doc[0]) + score;
                        hm.put(doc[0], newScore);
                    }
                }
            }
        }
        HashMap<String, Float> tfIdf = sortByValue(hm);
        docIDs = new ArrayList<>(tfIdf.keySet());

        return PrintData(pageNo);
    }

    private float CalculateTF(int wordCount, int docLength) {
        return wordCount * 1f / docLength;
    }

    private float CalculateIDF(int docsCount) {
        return (float) Math.log(TOTAL_DOCUMENTS * 1f / docsCount);
    }

    public static HashMap<String, Float> sortByValue(HashMap<String, Float> hm) {
        List<Map.Entry<String, Float>> list = new LinkedList<>(hm.entrySet());
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        HashMap<String, Float> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Float> aa : list)
            temp.put(aa.getKey(), aa.getValue());

        return temp;
    }

    private String[] PrintData(int pageNo) {
        String[] output = new String[10];

        for (int i = 10 * pageNo; i < (10 * pageNo) + 10; i++) {
            output[i % 10] = docIDs.get(i);
        }

        return output;
    }


    public List<String> getTokenizedWords(String query) throws IOException {

        query = query.toLowerCase();
        query = query.replaceAll("\\.", " ");
        StringReader reader = new StringReader(query);
        Tokenizer tokenizer = new StandardTokenizer();
        tokenizer.setReader(reader);

        TokenStream tokenStream = new StopFilter(new ASCIIFoldingFilter(new ClassicFilter(new LowerCaseFilter(tokenizer))), EnglishAnalyzer.getDefaultStopSet());

        //tokenStream = new PorterStemFilter(tokenStream);
        final CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();

        List<String> tokens = new ArrayList<>();
        while (tokenStream.incrementToken()) {
            tokens.add(charTermAttribute.toString());
        }

        tokenStream.end();
        tokenStream.close();

        return tokens;
    }

}
