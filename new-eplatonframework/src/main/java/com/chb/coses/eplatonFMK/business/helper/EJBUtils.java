package com.chb.coses.eplatonFMK.business.helper;

import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * EJB Utils
 * Spring 기반으로 전환된 EJB 유틸리티 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class EJBUtils {

    private static final Logger logger = LoggerFactory.getLogger(EJBUtils.class);

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Spring Bean 가져오기
     */
    public <T> T getBean(Class<T> beanClass) {
        try {
            return applicationContext.getBean(beanClass);
        } catch (Exception e) {
            logger.error("getBean() 에러", e);
            return null;
        }
    }

    /**
     * Spring Bean 가져오기 (이름으로)
     */
    public Object getBean(String beanName) {
        try {
            return applicationContext.getBean(beanName);
        } catch (Exception e) {
            logger.error("getBean() 에러", e);
            return null;
        }
    }

    /**
     * Spring Bean 가져오기 (이름과 타입으로)
     */
    public <T> T getBean(String beanName, Class<T> beanClass) {
        try {
            return applicationContext.getBean(beanName, beanClass);
        } catch (Exception e) {
            logger.error("getBean() 에러", e);
            return null;
        }
    }

    /**
     * Bean 존재 여부 확인
     */
    public boolean containsBean(String beanName) {
        try {
            return applicationContext.containsBean(beanName);
        } catch (Exception e) {
            logger.error("containsBean() 에러", e);
            return false;
        }
    }

    /**
     * Bean 타입 확인
     */
    public boolean isTypeMatch(String beanName, Class<?> targetType) {
        try {
            return applicationContext.isTypeMatch(beanName, targetType);
        } catch (Exception e) {
            logger.error("isTypeMatch() 에러", e);
            return false;
        }
    }

    /**
     * Bean 타입 가져오기
     */
    public Class<?> getType(String beanName) {
        try {
            return applicationContext.getType(beanName);
        } catch (Exception e) {
            logger.error("getType() 에러", e);
            return null;
        }
    }

    /**
     * Bean 별칭 가져오기
     */
    public String[] getAliases(String beanName) {
        try {
            return applicationContext.getAliases(beanName);
        } catch (Exception e) {
            logger.error("getAliases() 에러", e);
            return new String[0];
        }
    }

    /**
     * 특정 타입의 모든 Bean 가져오기
     */
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        try {
            return applicationContext.getBeansOfType(type);
        } catch (Exception e) {
            logger.error("getBeansOfType() 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 특정 어노테이션이 있는 Bean 가져오기
     */
    public Map<String, Object> getBeansWithAnnotation(Class<?> annotationType) {
        try {
            return applicationContext.getBeansWithAnnotation(annotationType);
        } catch (Exception e) {
            logger.error("getBeansWithAnnotation() 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * Bean 이름 가져오기
     */
    public String[] getBeanDefinitionNames() {
        try {
            return applicationContext.getBeanDefinitionNames();
        } catch (Exception e) {
            logger.error("getBeanDefinitionNames() 에러", e);
            return new String[0];
        }
    }

    /**
     * Bean 개수 가져오기
     */
    public int getBeanDefinitionCount() {
        try {
            return applicationContext.getBeanDefinitionCount();
        } catch (Exception e) {
            logger.error("getBeanDefinitionCount() 에러", e);
            return 0;
        }
    }

    /**
     * 프로파일 확인
     */
    public boolean isProfileActive(String profile) {
        try {
            return applicationContext.getEnvironment().acceptsProfiles(profile);
        } catch (Exception e) {
            logger.error("isProfileActive() 에러", e);
            return false;
        }
    }

    /**
     * 활성 프로파일 가져오기
     */
    public String[] getActiveProfiles() {
        try {
            return applicationContext.getEnvironment().getActiveProfiles();
        } catch (Exception e) {
            logger.error("getActiveProfiles() 에러", e);
            return new String[0];
        }
    }

    /**
     * 기본 프로파일 가져오기
     */
    public String[] getDefaultProfiles() {
        try {
            return applicationContext.getEnvironment().getDefaultProfiles();
        } catch (Exception e) {
            logger.error("getDefaultProfiles() 에러", e);
            return new String[0];
        }
    }
}