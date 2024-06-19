package az.practice.rediscashing.util;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

@Component
@RequiredArgsConstructor
public class CacheUtil {
    private final RedissonClient redissonClient;

    public <T> T getBucket(String key) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket == null ? null : bucket.get();
    }

    public <T> void saveToCache(String key, T value, Long expirationDate, TemporalUnit temporalUnit) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        bucket.set(value);
        bucket.expire(Duration.of(expirationDate, temporalUnit));
    }

    public void deleteCache(String key) {
        var bucket = redissonClient.getBucket(key);
        bucket.delete();
    }
}
