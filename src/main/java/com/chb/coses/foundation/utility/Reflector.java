package com.chb.coses.foundation.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Reflection utility helper class
 */
public class Reflector {

    /**
     * Get all fields of a class including inherited ones
     */
    public static Field[] getAllFields(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<>();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!fields.containsKey(field.getName())) {
                    fields.put(field.getName(), field);
                }
            }
            clazz = clazz.getSuperclass();
        }

        return fields.values().toArray(new Field[0]);
    }

    /**
     * Get all methods of a class including inherited ones
     */
    public static Method[] getAllMethods(Class<?> clazz) {
        Map<String, Method> methods = new HashMap<>();

        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                String key = method.getName() + "(" + getParameterTypesString(method.getParameterTypes()) + ")";
                if (!methods.containsKey(key)) {
                    methods.put(key, method);
                }
            }
            clazz = clazz.getSuperclass();
        }

        return methods.values().toArray(new Method[0]);
    }

    /**
     * Get field value using reflection
     */
    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = getField(obj.getClass(), fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(obj);
        }
        return null;
    }

    /**
     * Set field value using reflection
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = getField(obj.getClass(), fieldName);
        if (field != null) {
            field.setAccessible(true);
            field.set(obj, value);
        }
    }

    /**
     * Get field by name from class hierarchy
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    /**
     * Get method by name and parameter types
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    /**
     * Invoke method using reflection
     */
    public static Object invokeMethod(Object obj, String methodName, Object... args) throws Exception {
        Class<?>[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i] != null ? args[i].getClass() : Object.class;
        }

        Method method = getMethod(obj.getClass(), methodName, parameterTypes);
        if (method != null) {
            method.setAccessible(true);
            return method.invoke(obj, args);
        }
        return null;
    }

    /**
     * Check if class has method
     */
    public static boolean hasMethod(Class<?> clazz, String methodName) {
        return getMethod(clazz, methodName) != null;
    }

    /**
     * Check if class has field
     */
    public static boolean hasField(Class<?> clazz, String fieldName) {
        return getField(clazz, fieldName) != null;
    }

    /**
     * Get parameter types as string
     */
    private static String getParameterTypesString(Class<?>[] parameterTypes) {
        if (parameterTypes.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(parameterTypes[i].getSimpleName());
        }
        return sb.toString();
    }

    /**
     * Convert object to string representation
     * 
     * @param obj object to convert
     * @return string representation
     */
    public static String objectToString(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.toString();
    }
}