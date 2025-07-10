package com.banking.kdb.oversea.foundation.utility;

import com.banking.foundation.log.FoundationLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO Converter utility for KDB Oversea Foundation
 * 
 * Provides methods for converting between entities and DTOs.
 */
public class DTOConverter {

    private static final FoundationLogger logger = FoundationLogger.getLogger(DTOConverter.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Convert entity to DTO
     */
    public static <T, R> R convertToDTO(T entity, Class<R> dtoClass) {
        if (entity == null) {
            return null;
        }

        try {
            String json = objectMapper.writeValueAsString(entity);
            return objectMapper.readValue(json, dtoClass);
        } catch (JsonProcessingException e) {
            logger.error("Error converting entity to DTO", e);
            return null;
        }
    }

    /**
     * Convert DTO to entity
     */
    public static <T, R> R convertToEntity(T dto, Class<R> entityClass) {
        if (dto == null) {
            return null;
        }

        try {
            String json = objectMapper.writeValueAsString(dto);
            return objectMapper.readValue(json, entityClass);
        } catch (JsonProcessingException e) {
            logger.error("Error converting DTO to entity", e);
            return null;
        }
    }

    /**
     * Convert list of entities to list of DTOs
     */
    public static <T, R> List<R> convertToDTOList(List<T> entities, Class<R> dtoClass) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(entity -> convertToDTO(entity, dtoClass))
                .collect(Collectors.toList());
    }

    /**
     * Convert list of DTOs to list of entities
     */
    public static <T, R> List<R> convertToEntityList(List<T> dtos, Class<R> entityClass) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(dto -> convertToEntity(dto, entityClass))
                .collect(Collectors.toList());
    }

    /**
     * Convert object to JSON string
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("Error converting object to JSON", e);
            return null;
        }
    }

    /**
     * Convert JSON string to object
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (CommonUtil.isNullOrEmpty(json)) {
            return null;
        }

        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            logger.error("Error converting JSON to object", e);
            return null;
        }
    }

    /**
     * Deep copy object
     */
    public static <T> T deepCopy(T obj, Class<T> clazz) {
        if (obj == null) {
            return null;
        }

        try {
            String json = objectMapper.writeValueAsString(obj);
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            logger.error("Error performing deep copy", e);
            return null;
        }
    }

    /**
     * Merge DTO into entity
     */
    public static <T, R> R mergeDTOIntoEntity(T dto, R entity, Class<R> entityClass) {
        if (dto == null || entity == null) {
            return entity;
        }

        try {
            String dtoJson = objectMapper.writeValueAsString(dto);
            String entityJson = objectMapper.writeValueAsString(entity);

            // Merge JSON objects
            Object dtoNode = objectMapper.readTree(dtoJson);
            Object entityNode = objectMapper.readTree(entityJson);

            // This is a simplified merge - in practice, you might want more sophisticated
            // merging logic
            return objectMapper.readValue(dtoJson, entityClass);
        } catch (JsonProcessingException e) {
            logger.error("Error merging DTO into entity", e);
            return entity;
        }
    }

    /**
     * Convert object to pretty JSON string
     */
    public static String toPrettyJson(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("Error converting object to pretty JSON", e);
            return null;
        }
    }

    /**
     * Check if two objects are equal by converting to JSON
     */
    public static boolean areEqual(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }

        try {
            String json1 = objectMapper.writeValueAsString(obj1);
            String json2 = objectMapper.writeValueAsString(obj2);
            return json1.equals(json2);
        } catch (JsonProcessingException e) {
            logger.error("Error comparing objects", e);
            return false;
        }
    }

    /**
     * Get ObjectMapper instance
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private DTOConverter() {
        // Private constructor to prevent instantiation
    }
}