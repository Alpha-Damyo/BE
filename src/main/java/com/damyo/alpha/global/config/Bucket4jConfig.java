package com.damyo.alpha.global.config;

import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.redis.lettuce.Bucket4jLettuce;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class Bucket4jConfig {

    @Value("${bucket.capacity}")
    private int capacity;
    @Value("${bucket.refill}")
    private int refillAmount;
    private static final Duration REFILL_DURATION = Duration.ofMinutes(1);
    private static final Duration KEEP_AFTER_FULL_BUCKET = Duration.ofSeconds(20);
    private final RedisClient lettuceRedisClient;

    @Bean
    public LettuceBasedProxyManager<String> proxyManager() {
        StatefulRedisConnection<String, byte[]> connection = lettuceRedisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));
        return Bucket4jLettuce.casBasedBuilder(connection)
                .expirationAfterWrite(ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(KEEP_AFTER_FULL_BUCKET))
                .build();
    }

    @Bean
    public BucketConfiguration bucketConfiguration() {
        return BucketConfiguration.builder()
                .addLimit(limit -> limit.capacity(capacity).refillGreedy(refillAmount, REFILL_DURATION))
                .build();
    }
}
