package com.skcc.oversea.framework.business.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractDAOFactory {

    @Autowired
    protected ApplicationContext applicationContext;

    public abstract <T extends IDAO<?, ?>> T getDAO(Class<T> daoClass);

    protected <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }
}