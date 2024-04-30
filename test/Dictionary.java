package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Dictionary {
    String[] fileNames;
    private CacheManager existWords;
    private CacheManager notexistWords;
    private BloomFilter bloomfilter;

    public Dictionary(String... fileNames) {

        existWords = new CacheManager(400, new LRU());
        notexistWords = new CacheManager(100, new LFU());
        bloomfilter = new BloomFilter(256, "MD5", "SHA1");

        this.fileNames = fileNames.clone();
        for (String fileName : fileNames) {

            try {
                Scanner scan = new Scanner(new BufferedReader(new FileReader(fileName)));
                while (scan.hasNext()) {
                    bloomfilter.add(scan.next().toLowerCase());
                }
            } catch (FileNotFoundException ex) {

            }
        }

    }

    public boolean query(String word) {

        if (existWords.query(word)) {
            return true;
        } else if (notexistWords.query(word)) {

            return false;
        } else if (bloomfilter.contains(word)) {

            existWords.add(word);
            return true;
        }
        notexistWords.add(word);
        return false;
    }

    public boolean challenge(String word) {

        if (IOSearcher.search(word, fileNames)) {

            existWords.add(word);
            return true;
        }
        notexistWords.add(word);
        return false;
    }
}
