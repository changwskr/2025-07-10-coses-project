package com.chb.coses.eplatonFMK.business.helper;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.*;

/**
 * DTO Converter
 * Spring 기반으로 전환된 DTO 변환 유틸리티 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class DTOConverter {

    private static final Logger logger = LoggerFactory.getLogger(DTOConverter.class);

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    /**
     * Object를 JSON 문자열로 변환
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("toJson() 에러", e);
            return null;
        }
    }

    /**
     * JSON 문자열을 Object로 변환
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("fromJson() 에러", e);
            return null;
        }
    }

    /**
     * Object를 Map으로 변환
     */
    public static Map<String, Object> toMap(Object obj) {
        try {
            return objectMapper.convertValue(obj, Map.class);
        } catch (Exception e) {
            logger.error("toMap() 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * Map을 Object로 변환
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> clazz) {
        try {
            return objectMapper.convertValue(map, clazz);
        } catch (Exception e) {
            logger.error("fromMap() 에러", e);
            return null;
        }
    }

    /**
     * Object 복사
     */
    public static <T> T copy(T obj, Class<T> clazz) {
        try {
            String json = toJson(obj);
            return fromJson(json, clazz);
        } catch (Exception e) {
            logger.error("copy() 에러", e);
            return null;
        }
    }

    /**
     * List 복사
     */
    public static <T> List<T> copyList(List<T> list, Class<T> clazz) {
        try {
            List<T> result = new ArrayList<>();
            for (T item : list) {
                result.add(copy(item, clazz));
            }
            return result;
        } catch (Exception e) {
            logger.error("copyList() 에러", e);
            return new ArrayList<>();
        }
    }

    /**
     * Object를 다른 타입으로 변환
     */
    public static <T> T convert(Object obj, Class<T> targetClass) {
        try {
            return objectMapper.convertValue(obj, targetClass);
        } catch (Exception e) {
            logger.error("convert() 에러", e);
            return null;
        }
    }

    /**
     * List를 다른 타입으로 변환
     */
    public static <T> List<T> convertList(List<?> list, Class<T> targetClass) {
        try {
            List<T> result = new ArrayList<>();
            for (Object item : list) {
                result.add(convert(item, targetClass));
            }
            return result;
        } catch (Exception e) {
            logger.error("convertList() 에러", e);
            return new ArrayList<>();
        }
    }

    /**
     * Object를 Map으로 변환 (null 값 제외)
     */
    public static Map<String, Object> toMapExcludeNull(Object obj) {
        try {
            Map<String, Object> map = toMap(obj);
            Map<String, Object> result = new HashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    result.put(entry.getKey(), entry.getValue());
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("toMapExcludeNull() 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * Object를 Map으로 변환 (특정 필드만 포함)
     */
    public static Map<String, Object> toMapIncludeFields(Object obj, String... fields) {
        try {
            Map<String, Object> map = toMap(obj);
            Map<String, Object> result = new HashMap<>();
            for (String field : fields) {
                if (map.containsKey(field)) {
                    result.put(field, map.get(field));
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("toMapIncludeFields() 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * Object를 Map으로 변환 (특정 필드 제외)
     */
    public static Map<String, Object> toMapExcludeFields(Object obj, String... fields) {
        try {
            Map<String, Object> map = toMap(obj);
            Set<String> excludeFields = new HashSet<>(Arrays.asList(fields));
            Map<String, Object> result = new HashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (!excludeFields.contains(entry.getKey())) {
                    result.put(entry.getKey(), entry.getValue());
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("toMapExcludeFields() 에러", e);
            return new HashMap<>();
        }
    }
}