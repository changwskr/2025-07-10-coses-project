package com.skcc.oversea.eplatonframework.business.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skcc.oversea.eplatonframework.business.service.CommonService;
import com.skcc.oversea.eplatonframework.business.service.ReferenceService;
import com.skcc.oversea.eplatonframework.business.service.UserService;

/**
 * Spring Service Utility for SKCC Oversea
 * 
 * Provides Spring-based service access replacing EJB utilities
 * using dependency injection instead of JNDI lookup.
 */
@Component
public class SpringServiceUtils {

    private static final Logger logger = LoggerFactory.getLogger(SpringServiceUtils.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private UserService userService;

    /**
     * Get Common Service
     */
    public CommonService getCommonService() {
        return commonService;
    }

    /**
     * Get Reference Service
     */
    public ReferenceService getReferenceService() {
        return referenceService;
    }

    /**
     * Get User Service
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Get service by name from Spring context
     */
    public <T> T getService(String serviceName, Class<T> serviceType) {
        try {
            return applicationContext.getBean(serviceName, serviceType);
        } catch (Exception e) {
            logger.error("Error getting service: {}", serviceName, e);
            throw new RuntimeException("Service not found: " + serviceName, e);
        }
    }

    /**
     * Get service by type from Spring context
     */
    public <T> T getService(Class<T> serviceType) {
        try {
            return applicationContext.getBean(serviceType);
        } catch (Exception e) {
            logger.error("Error getting service of type: {}", serviceType.getSimpleName(), e);
            throw new RuntimeException("Service not found: " + serviceType.getSimpleName(), e);
        }
    }
}