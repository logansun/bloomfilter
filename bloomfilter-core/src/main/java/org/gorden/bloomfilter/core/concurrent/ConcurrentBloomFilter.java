package org.gorden.bloomfilter.core.concurrent;

import org.gorden.bloomfilter.core.AbstractBloomFilter;
import org.gorden.bloomfilter.core.bitset.BitSet;
import org.gorden.bloomfilter.core.bitset.LockFreeBitSet;
import org.gorden.bloomfilter.core.hash.HashFunction;
import org.gorden.bloomfilter.core.hash.Murmur3_128HashFunction;
import org.gorden.bloomfilter.core.serializer.BloomFilterSerializer;
import org.gorden.bloomfilter.core.serializer.JdkSerializationBloomFilterSerializer;

/**
 * In memory bloom filter
 * @author GordenTam
 **/

public class ConcurrentBloomFilter<T> extends AbstractBloomFilter<T> {

    private ConcurrentBloomFilter(String name, int numHashFunctions, BitSet bitSet, BloomFilterSerializer bloomFilterSerializer, HashFunction hashFunction) {
        super(name, numHashFunctions, bitSet, bloomFilterSerializer, hashFunction);
    }

    public static <T> ConcurrentBloomFilter<T> create(String name, long expectedInsertions, double fpp) {
        return create(name, expectedInsertions, fpp, new JdkSerializationBloomFilterSerializer(), new Murmur3_128HashFunction(0));
    }

    public static <T> ConcurrentBloomFilter<T> create(String name, long expectedInsertions, double fpp, BloomFilterSerializer bloomFilterSerializer) {
        return create(name, expectedInsertions, fpp, bloomFilterSerializer, new Murmur3_128HashFunction(0));
    }

    public static <T> ConcurrentBloomFilter<T> create(String name, long expectedInsertions, double fpp, HashFunction hashFunction) {
        return create(name, expectedInsertions, fpp, new JdkSerializationBloomFilterSerializer(), hashFunction);
    }

    public static <T> ConcurrentBloomFilter<T> create(String name, long expectedInsertions, double fpp, BloomFilterSerializer bloomFilterSerializer, HashFunction hashFunction) {
        if (expectedInsertions <= 0) {
            throw new IllegalArgumentException(String.format("expectedInsertions (%s) must be > 0", expectedInsertions));
        }
        if (fpp >= 1.0) {
            throw new IllegalArgumentException(String.format("numHashFunctions (%s) must be < 1.0", fpp));
        }
        if (fpp <= 0.0) {
            throw new IllegalArgumentException(String.format("numHashFunctions (%s) must be > 0.0", fpp));
        }
        long numBits = optimalNumOfBits(expectedInsertions, fpp);
        int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);
        return new ConcurrentBloomFilter<T>(name, numHashFunctions, new LockFreeBitSet(numBits), bloomFilterSerializer, hashFunction);
    }

}