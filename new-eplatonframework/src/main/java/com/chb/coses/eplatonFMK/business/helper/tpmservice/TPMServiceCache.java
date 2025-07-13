package com.chb.coses.eplatonFMK.business.helper.tpmservice;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * TPM Service Cache
 * Spring 기반으로 전환된 TPM 서비스 캐시 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class TPMServiceCache {

    private static final Logger logger = LoggerFactory.getLogger(TPMServiceCache.class);

    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static final long DEFAULT_TTL = 300000; // 5분
    private static final int MAX_CACHE_SIZE = 10000;

    /**
     * 캐시 엔트리 클래스
     */
    public static class CacheEntry {
        private String key;
        private Object value;
        private long timestamp;
        private long ttl;
        private int accessCount;

        public CacheEntry(String key, Object value, long ttl) {
            this.key = key;
            this.value = value;
            this.timestamp = System.currentTimeMillis();
            this.ttl = ttl;
            this.accessCount = 0;
        }

        // Getters and Setters
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public long getTtl() {
            return ttl;
        }

        public void setTtl(long ttl) {
            this.ttl = ttl;
        }

        public int getAccessCount() {
            return accessCount;
        }

        public void setAccessCount(int accessCount) {
            this.accessCount = accessCount;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > ttl;
        }
    }

    /**
     * 생성자 - 캐시 정리 스케줄러 시작
     */
    public TPMServiceCache() {
        // 1분마다 만료된 캐시 항목 정리
        scheduler.scheduleAtFixedRate(this::cleanupExpiredEntries, 1, 1, TimeUnit.MINUTES);
        logger.info("TPM 서비스 캐시 초기화 완료");
    }

    /**
     * 캐시에 데이터 저장
     */
    public void put(String key, Object value) {
        put(key, value, DEFAULT_TTL);
    }

    /**
     * 캐시에 데이터 저장 (TTL 지정)
     */
    public void put(String key, Object value, long ttl) {
        try {
            // 캐시 크기 제한 확인
            if (cache.size() >= MAX_CACHE_SIZE) {
                cleanupOldestEntries();
            }

            CacheEntry entry = new CacheEntry(key, value, ttl);
            cache.put(key, entry);

            logger.debug("캐시 저장: {} (TTL: {}ms)", key, ttl);

        } catch (Exception e) {
            logger.error("캐시 저장 에러", e);
        }
    }

    /**
     * 캐시에서 데이터 가져오기
     */
    public Object get(String key) {
        try {
            CacheEntry entry = cache.get(key);
            if (entry == null) {
                logger.debug("캐시 미스: {}", key);
                return null;
            }

            // 만료 확인
            if (entry.isExpired()) {
                cache.remove(key);
                logger.debug("캐시 만료: {}", key);
                return null;
            }

            // 접근 횟수 증가
            entry.setAccessCount(entry.getAccessCount() + 1);

            logger.debug("캐시 히트: {} (접근횟수: {})", key, entry.getAccessCount());
            return entry.getValue();

        } catch (Exception e) {
            logger.error("캐시 가져오기 에러", e);
            return null;
        }
    }

    /**
     * 캐시에서 데이터 제거
     */
    public void remove(String key) {
        try {
            cache.remove(key);
            logger.debug("캐시 제거: {}", key);
        } catch (Exception e) {
            logger.error("캐시 제거 에러", e);
        }
    }

    /**
     * 캐시 전체 비우기
     */
    public void clear() {
        try {
            cache.clear();
            logger.info("캐시 전체 비우기 완료");
        } catch (Exception e) {
            logger.error("캐시 전체 비우기 에러", e);
        }
    }

    /**
     * 캐시에 키 존재 여부 확인
     */
    public boolean containsKey(String key) {
        try {
            CacheEntry entry = cache.get(key);
            if (entry == null) {
                return false;
            }

            if (entry.isExpired()) {
                cache.remove(key);
                return false;
            }

            return true;

        } catch (Exception e) {
            logger.error("캐시 키 존재 확인 에러", e);
            return false;
        }
    }

    /**
     * 캐시 크기 가져오기
     */
    public int size() {
        try {
            return cache.size();
        } catch (Exception e) {
            logger.error("캐시 크기 가져오기 에러", e);
            return 0;
        }
    }

    /**
     * 만료된 캐시 항목 정리
     */
    private void cleanupExpiredEntries() {
        try {
            int removedCount = 0;
            Iterator<Map.Entry<String, CacheEntry>> iterator = cache.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, CacheEntry> entry = iterator.next();
                if (entry.getValue().isExpired()) {
                    iterator.remove();
                    removedCount++;
                }
            }

            if (removedCount > 0) {
                logger.debug("만료된 캐시 항목 정리: {}개 제거", removedCount);
            }

        } catch (Exception e) {
            logger.error("만료된 캐시 항목 정리 에러", e);
        }
    }

    /**
     * 가장 오래된 캐시 항목 정리
     */
    private void cleanupOldestEntries() {
        try {
            // 접근 횟수가 가장 적은 항목들을 제거
            List<CacheEntry> entries = new ArrayList<>(cache.values());
            entries.sort(Comparator.comparingInt(CacheEntry::getAccessCount));

            int removeCount = Math.min(100, entries.size() / 10); // 10% 또는 100개 제거
            for (int i = 0; i < removeCount; i++) {
                cache.remove(entries.get(i).getKey());
            }

            logger.debug("오래된 캐시 항목 정리: {}개 제거", removeCount);

        } catch (Exception e) {
            logger.error("오래된 캐시 항목 정리 에러", e);
        }
    }

    /**
     * 캐시 통계 가져오기
     */
    public Map<String, Object> getCacheStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();

            statistics.put("totalEntries", cache.size());
            statistics.put("maxCacheSize", MAX_CACHE_SIZE);
            statistics.put("defaultTTL", DEFAULT_TTL);

            // 만료된 항목 수 계산
            int expiredCount = 0;
            long totalAccessCount = 0;
            long oldestTimestamp = Long.MAX_VALUE;
            long newestTimestamp = 0;

            for (CacheEntry entry : cache.values()) {
                if (entry.isExpired()) {
                    expiredCount++;
                }
                totalAccessCount += entry.getAccessCount();
                oldestTimestamp = Math.min(oldestTimestamp, entry.getTimestamp());
                newestTimestamp = Math.max(newestTimestamp, entry.getTimestamp());
            }

            statistics.put("expiredEntries", expiredCount);
            statistics.put("totalAccessCount", totalAccessCount);
            statistics.put("oldestEntry", oldestTimestamp == Long.MAX_VALUE ? 0 : oldestTimestamp);
            statistics.put("newestEntry", newestTimestamp);

            // 평균 접근 횟수
            if (cache.size() > 0) {
                double avgAccessCount = (double) totalAccessCount / cache.size();
                statistics.put("avgAccessCount", avgAccessCount);
            }

            return statistics;

        } catch (Exception e) {
            logger.error("캐시 통계 가져오기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 캐시 설정 가져오기
     */
    public Map<String, Object> getCacheConfiguration() {
        try {
            Map<String, Object> config = new HashMap<>();

            config.put("defaultTTL", DEFAULT_TTL);
            config.put("maxCacheSize", MAX_CACHE_SIZE);
            config.put("cleanupInterval", "1분");
            config.put("cleanupStrategy", "LRU");

            return config;

        } catch (Exception e) {
            logger.error("캐시 설정 가져오기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 캐시 내용 가져오기 (디버깅용)
     */
    public Map<String, Object> getCacheContents() {
        try {
            Map<String, Object> contents = new HashMap<>();

            for (Map.Entry<String, CacheEntry> entry : cache.entrySet()) {
                CacheEntry cacheEntry = entry.getValue();
                Map<String, Object> entryInfo = new HashMap<>();

                entryInfo.put("value", cacheEntry.getValue());
                entryInfo.put("timestamp", cacheEntry.getTimestamp());
                entryInfo.put("ttl", cacheEntry.getTtl());
                entryInfo.put("accessCount", cacheEntry.getAccessCount());
                entryInfo.put("expired", cacheEntry.isExpired());

                contents.put(entry.getKey(), entryInfo);
            }

            return contents;

        } catch (Exception e) {
            logger.error("캐시 내용 가져오기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 캐시 종료
     */
    public void shutdown() {
        try {
            scheduler.shutdown();
            cache.clear();
            logger.info("TPM 서비스 캐시 종료 완료");
        } catch (Exception e) {
            logger.error("TPM 서비스 캐시 종료 에러", e);
        }
    }
}