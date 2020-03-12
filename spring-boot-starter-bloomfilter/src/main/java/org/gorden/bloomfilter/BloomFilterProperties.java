package org.gorden.bloomfilter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GordenTam
 **/
@ConfigurationProperties(prefix = "bloomfilter")
public class BloomFilterProperties {

    private BloomFilterType type;

    private List<String> names = new ArrayList<>();

    private long expectedInsertions;

    private double fpp;

    private Class<?> serializationClass;

    private Class<?> hashFunction;

    public BloomFilterProperties() {
    }

    public BloomFilterType getType() {
        return this.type;
    }

    public void setType(BloomFilterType mode) {
        this.type = mode;
    }

    public List<String> getNames() {
        return this.names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public long getExpectedInsertions() {
        return this.expectedInsertions;
    }

    public void setExpectedInsertions(long expectedInsertions) {
        this.expectedInsertions = expectedInsertions;
    }

    public double getFpp() {
        return fpp;
    }

    public void setFpp(double fpp) {
        this.fpp = fpp;
    }

    public Class<?> getSerializationClass() {
        return serializationClass;
    }

    public void setSerializationClass(Class<?> serializationClass) {
        this.serializationClass = serializationClass;
    }

    public enum BloomFilterType {
        JDK,
        REDIS;

        private BloomFilterType() {
        }
    }

    private class Redis {
        private RedisOperationType SYN;
    }

    public enum RedisOperationType {
        SYN,
        REACTOR;

        private RedisOperationType() {
        }
    }
}
