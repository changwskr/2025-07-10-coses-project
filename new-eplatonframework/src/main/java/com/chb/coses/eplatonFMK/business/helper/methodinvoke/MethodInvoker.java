package com.chb.coses.eplatonFMK.business.helper.methodinvoke;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Method Invoker
 * Spring 기반으로 전환된 메소드 호출 유틸리티 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class MethodInvoker {

    private static final Logger logger = LoggerFactory.getLogger(MethodInvoker.class);

    /**
     * 메소드 호출
     */
    public static Object invokeMethod(Object target, String methodName, Object... args) {
        try {
            if (target == null) {
                logger.error("target이 null입니다.");
                return null;
            }

            Class<?>[] paramTypes = null;
            if (args != null) {
                paramTypes = new Class<?>[args.length];
                for (int i = 0; i < args.length; i++) {
                    paramTypes[i] = args[i] != null ? args[i].getClass() : Object.class;
                }
            }

            Method method = target.getClass().getMethod(methodName, paramTypes);
            return method.invoke(target, args);

        } catch (NoSuchMethodException e) {
            logger.error("메소드를 찾을 수 없습니다: {}", methodName, e);
            return null;
        } catch (IllegalAccessException e) {
            logger.error("메소드 접근 권한이 없습니다: {}", methodName, e);
            return null;
        } catch (InvocationTargetException e) {
            logger.error("메소드 호출 중 예외 발생: {}", methodName, e);
            return null;
        } catch (Exception e) {
            logger.error("메소드 호출 중 오류 발생: {}", methodName, e);
            return null;
        }
    }

    /**
     * 메소드 호출 (파라미터 타입 지정)
     */
    public static Object invokeMethod(Object target, String methodName, Class<?>[] paramTypes, Object... args) {
        try {
            if (target == null) {
                logger.error("target이 null입니다.");
                return null;
            }

            Method method = target.getClass().getMethod(methodName, paramTypes);
            return method.invoke(target, args);

        } catch (NoSuchMethodException e) {
            logger.error("메소드를 찾을 수 없습니다: {}", methodName, e);
            return null;
        } catch (IllegalAccessException e) {
            logger.error("메소드 접근 권한이 없습니다: {}", methodName, e);
            return null;
        } catch (InvocationTargetException e) {
            logger.error("메소드 호출 중 예외 발생: {}", methodName, e);
            return null;
        } catch (Exception e) {
            logger.error("메소드 호출 중 오류 발생: {}", methodName, e);
            return null;
        }
    }

    /**
     * 메소드 존재 여부 확인
     */
    public static boolean hasMethod(Object target, String methodName, Class<?>... paramTypes) {
        try {
            if (target == null) {
                return false;
            }

            target.getClass().getMethod(methodName, paramTypes);
            return true;

        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    /**
     * 메소드 목록 가져오기
     */
    public static Method[] getMethods(Object target) {
        try {
            if (target == null) {
                return new Method[0];
            }

            return target.getClass().getMethods();

        } catch (Exception e) {
            logger.error("메소드 목록 가져오기 에러", e);
            return new Method[0];
        }
    }

    /**
     * 메소드 정보 가져오기
     */
    public static Method getMethod(Object target, String methodName, Class<?>... paramTypes) {
        try {
            if (target == null) {
                return null;
            }

            return target.getClass().getMethod(methodName, paramTypes);

        } catch (NoSuchMethodException e) {
            logger.error("메소드를 찾을 수 없습니다: {}", methodName, e);
            return null;
        } catch (Exception e) {
            logger.error("메소드 정보 가져오기 에러", e);
            return null;
        }
    }

    /**
     * 메소드 시그니처 가져오기
     */
    public static String getMethodSignature(Method method) {
        try {
            if (method == null) {
                return null;
            }

            StringBuilder signature = new StringBuilder();
            signature.append(method.getReturnType().getSimpleName());
            signature.append(" ");
            signature.append(method.getName());
            signature.append("(");

            Class<?>[] paramTypes = method.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0) {
                    signature.append(", ");
                }
                signature.append(paramTypes[i].getSimpleName());
            }
            signature.append(")");

            return signature.toString();

        } catch (Exception e) {
            logger.error("메소드 시그니처 가져오기 에러", e);
            return null;
        }
    }

    /**
     * 메소드 파라미터 정보 가져오기
     */
    public static Class<?>[] getMethodParameterTypes(Method method) {
        try {
            if (method == null) {
                return new Class<?>[0];
            }

            return method.getParameterTypes();

        } catch (Exception e) {
            logger.error("메소드 파라미터 정보 가져오기 에러", e);
            return new Class<?>[0];
        }
    }

    /**
     * 메소드 반환 타입 가져오기
     */
    public static Class<?> getMethodReturnType(Method method) {
        try {
            if (method == null) {
                return null;
            }

            return method.getReturnType();

        } catch (Exception e) {
            logger.error("메소드 반환 타입 가져오기 에러", e);
            return null;
        }
    }

    /**
     * 메소드 접근 제어자 확인
     */
    public static boolean isPublicMethod(Method method) {
        try {
            if (method == null) {
                return false;
            }

            return java.lang.reflect.Modifier.isPublic(method.getModifiers());

        } catch (Exception e) {
            logger.error("메소드 접근 제어자 확인 에러", e);
            return false;
        }
    }

    /**
     * 메소드 접근 제어자 확인
     */
    public static boolean isPrivateMethod(Method method) {
        try {
            if (method == null) {
                return false;
            }

            return java.lang.reflect.Modifier.isPrivate(method.getModifiers());

        } catch (Exception e) {
            logger.error("메소드 접근 제어자 확인 에러", e);
            return false;
        }
    }

    /**
     * 메소드 접근 제어자 확인
     */
    public static boolean isProtectedMethod(Method method) {
        try {
            if (method == null) {
                return false;
            }

            return java.lang.reflect.Modifier.isProtected(method.getModifiers());

        } catch (Exception e) {
            logger.error("메소드 접근 제어자 확인 에러", e);
            return false;
        }
    }

    /**
     * 메소드 접근 제어자 확인
     */
    public static boolean isStaticMethod(Method method) {
        try {
            if (method == null) {
                return false;
            }

            return java.lang.reflect.Modifier.isStatic(method.getModifiers());

        } catch (Exception e) {
            logger.error("메소드 접근 제어자 확인 에러", e);
            return false;
        }
    }

    /**
     * 메소드 접근 제어자 확인
     */
    public static boolean isFinalMethod(Method method) {
        try {
            if (method == null) {
                return false;
            }

            return java.lang.reflect.Modifier.isFinal(method.getModifiers());

        } catch (Exception e) {
            logger.error("메소드 접근 제어자 확인 에러", e);
            return false;
        }
    }

    /**
     * 메소드 접근 제어자 확인
     */
    public static boolean isAbstractMethod(Method method) {
        try {
            if (method == null) {
                return false;
            }

            return java.lang.reflect.Modifier.isAbstract(method.getModifiers());

        } catch (Exception e) {
            logger.error("메소드 접근 제어자 확인 에러", e);
            return false;
        }
    }

    /**
     * 메소드 접근 제어자 확인
     */
    public static boolean isSynchronizedMethod(Method method) {
        try {
            if (method == null) {
                return false;
            }

            return java.lang.reflect.Modifier.isSynchronized(method.getModifiers());

        } catch (Exception e) {
            logger.error("메소드 접근 제어자 확인 에러", e);
            return false;
        }
    }

    /**
     * 메소드 접근 제어자 확인
     */
    public static boolean isNativeMethod(Method method) {
        try {
            if (method == null) {
                return false;
            }

            return java.lang.reflect.Modifier.isNative(method.getModifiers());

        } catch (Exception e) {
            logger.error("메소드 접근 제어자 확인 에러", e);
            return false;
        }
    }

    /**
     * 메소드 접근 제어자 확인
     */
    public static boolean isStrictMethod(Method method) {
        try {
            if (method == null) {
                return false;
            }

            return java.lang.reflect.Modifier.isStrict(method.getModifiers());

        } catch (Exception e) {
            logger.error("메소드 접근 제어자 확인 에러", e);
            return false;
        }
    }
}