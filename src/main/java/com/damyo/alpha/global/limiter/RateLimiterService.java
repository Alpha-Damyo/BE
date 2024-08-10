package com.damyo.alpha.global.limiter;

import com.damyo.alpha.global.exception.error.BaseException;
import com.damyo.alpha.global.exception.error.CommonErrorCode;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class RateLimiterService {
    private final LettuceBasedProxyManager<String> proxyManager;
    private final BucketConfiguration bucketConfig;
    public boolean tryConsume(String remoteAddKey) {
        Bucket bucket = getOrCreateBucket(remoteAddKey);

        ConsumptionProbe probe = consumeToken(bucket);

        log.info("API Key: {}, RemoteAddress: {}, tryConsume: {}, remainToken: {}, tryTime: {}",
                remoteAddKey, remoteAddKey, probe.isConsumed(), probe.getRemainingTokens(), LocalDateTime.now());

        handleNotConsumed(probe);

        return probe.isConsumed();
    }

    private Bucket getOrCreateBucket(String apiKey) {
        return proxyManager.getProxy(apiKey, () -> bucketConfig);
    }

    private ConsumptionProbe consumeToken(Bucket bucket) {
        return bucket.tryConsumeAndReturnRemaining(1);
    }

    private void handleNotConsumed(ConsumptionProbe probe) {
        if (!probe.isConsumed()) {
            throw new BaseException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
    }
}
