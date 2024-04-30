package test;

import java.util.HashSet;
import java.util.Set;
import java.security.MessageDigest;
import java.util.BitSet;
import java.math.BigInteger;

import java.security.NoSuchAlgorithmException;

public class BloomFilter {
    private int size;
    BitSet bitsarray;
    public String[] hashfunctions;
    MessageDigest md;

    public BloomFilter(int size, String... algs) {
        bitsarray = new BitSet(size);
        this.size = size;
        hashfunctions = new String[algs.length];
        System.arraycopy(algs, 0, hashfunctions, 0, algs.length);

    }

    public void add(String word) {

        byte[] bts = null;
        for (String function : hashfunctions) {

            try {
                md = MessageDigest.getInstance(function);
            } catch (NoSuchAlgorithmException e) {

            }
            bts = md.digest(word.getBytes());
            BigInteger bigInt = new BigInteger(bts);
            bitsarray.set(Math.abs(bigInt.intValue()) % size);

        }

    }

    public boolean contains(String word) {
        byte[] bts = null;
        for (String function : hashfunctions) {
            try {
                md = MessageDigest.getInstance(function);
            } catch (NoSuchAlgorithmException e) {

            }
            bts = md.digest(word.getBytes());
            BigInteger bigInt = new BigInteger(bts);
            if (bitsarray.get(Math.abs(bigInt.intValue()) % size)) {
                return true;
            }

        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(bitsarray.length());
        for (int i = 0; i < bitsarray.length(); i++) {
            if (bitsarray.get(i)) {
                sb.append("1");
            } else {
                sb.append("0");
            }
        }
        return sb.toString();
    }

}
