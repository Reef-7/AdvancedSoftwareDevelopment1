package test;

import java.util.HashSet;

public class CacheManager {
    private final int maxcachesize;
    private CacheReplacementPolicy policy;
    private HashSet<String> cachewords;

    public CacheManager(int size, CacheReplacementPolicy crp) {

        this.maxcachesize = size;
        this.policy = crp;
        cachewords = new HashSet<>();
    }

    public boolean query(String word) {
        return cachewords.contains(word);
    }

    public void add(String word) {

        if (!query(word)) {
            cachewords.add(word);
            policy.add(word);
            if (cachewords.size() > maxcachesize) {

                cachewords.remove(policy.remove());
            }
        } else {
            policy.add(word);
        }
    }
}
