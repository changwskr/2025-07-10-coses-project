package com.chb.coses.eplatonFMK.business.dao;

import java.io.Serializable;

/**
 * 추상 엔티티 클래스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 기본 생성자
     */
    public AbstractEntity() {
        super();
    }

    /**
     * 엔티티 ID 반환
     * 
     * @return 엔티티 ID
     */
    public abstract Object getId();

    /**
     * 엔티티 ID 설정
     * 
     * @param id 엔티티 ID
     */
    public abstract void setId(Object id);

    /**
     * 엔티티가 새로운지 확인
     * 
     * @return 새로운 엔티티 여부
     */
    public boolean isNew() {
        return getId() == null;
    }

    /**
     * 엔티티가 지속적인지 확인
     * 
     * @return 지속적인 엔티티 여부
     */
    public boolean isPersistent() {
        return !isNew();
    }
}