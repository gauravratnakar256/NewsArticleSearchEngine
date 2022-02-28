package com.index.hadoop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ParseReducerOutput {

    private static final Map<String, ArrayList<String[]>> map = new HashMap<>();

    public ParseReducerOutput(String invertedIndexPath) {
        readInvertedTextFile(invertedIndexPath);
    }

    public static Map<String, ArrayList<String[]>> getMap() {
        return map;
    }

    private static void readInvertedTextFile(String invertedIndexPath) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(invertedIndexPath));
            Stream<String> lines = bufferedReader.lines();
            lines.parallel().forEach((line -> addDataToMap(line)));
            bufferedReader.close();
        } catch (Exception ex) {
            System.out.println("Error while reading the file!");
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
    }

    private static void addDataToMap(String line) {
        if (line != null) {
            String[] docs = line.split("\\s+");
            ArrayList<String[]> list;

            if (!map.containsKey(docs[0])) list = new ArrayList<>();
            else list = map.get(docs[0]);

            for (int i = 1; i < docs.length - 1; i++) {
                String doc = docs[i];
                String[] data = doc.split(":");
                list.add(data);
            }

            map.put(docs[0], list);
        }
    }
}
