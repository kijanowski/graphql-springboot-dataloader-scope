package graphqlscope.graphql;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.dataloader.CacheMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class CustomGuavaBasedCache<U, V> implements CacheMap<U, V> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomGuavaBasedCache.class);

    private Cache<U, V> cache;

    public CustomGuavaBasedCache(long maxCacheSize, long expiryInSeconds) {
        this.cache = CacheBuilder
          .newBuilder()
          .maximumSize(maxCacheSize)
          .expireAfterWrite(expiryInSeconds, TimeUnit.SECONDS)
          .removalListener(notification -> LOG.info("Key {} got removed, because: {}", notification.getKey(), notification.getCause()))
          .build();

    }

    @Override
    public boolean containsKey(U key) {
        return cache.getIfPresent(key) != null;
    }

    @Override
    public V get(U key) {
        return cache.getIfPresent(key);
    }

    @Override
    public CacheMap<U, V> set(U key, V value) {
        cache.put(key, value);
        return this;
    }

    @Override
    public CacheMap<U, V> delete(U key) {
        cache.invalidate(key);
        return this;
    }

    @Override
    public CacheMap<U, V> clear() {
        cache.invalidateAll();
        return this;
    }
}
