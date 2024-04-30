package test;

import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.LinkedHashSet;

public class LRU implements CacheReplacementPolicy {

    private HashSet<String> cache;

    public LRU() {
        cache = new LinkedHashSet<String>();
    }

    public void add(String word) {
        if (cache.contains(word)) {
            cache.remove(word);
        }
        cache.add(word);
    }

    public String remove() {
        String leastRecentlyUsed = cache.iterator().next();
        cache.remove(leastRecentlyUsed);
        return leastRecentlyUsed;
    }

}
