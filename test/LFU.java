package test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class LFU implements CacheReplacementPolicy {

    private LinkedHashMap<String, Integer> cache;
    private PriorityQueue<Map.Entry<String, Integer>> minHeap;

    public LFU() {

        cache = new LinkedHashMap<>();
        minHeap = new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
    }

    public void add(String word) {
        if (cache.containsKey(word)) {
            int freq = cache.get(word);
            cache.put(word, freq + 1);
        } else {
            cache.put(word, 1);
        }
        updateHeap();
    }

    public String remove() {
        String leastFrequent = minHeap.poll().getKey();
        cache.remove(leastFrequent);
        return leastFrequent;
    }

    private void updateHeap() {
        minHeap.clear();
        for (Map.Entry<String, Integer> entry : cache.entrySet()) {
            minHeap.offer(entry);
        }
    }
}
