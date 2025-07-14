package com.skcc.oversea.framework.transfer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SystemParameterCDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String parameterId;
    private String parameterName;
    private String parameterValue;
    private String description;
    private String status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public SystemParameterCDTO() {
    }

    public SystemParameterCDTO(String parameterId, String parameterName, String parameterValue) {
        this.parameterId = parameterId;
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
        this.createDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getParameterId() {
        return parameterId;
    }

    public void setParameterId(String parameterId) {
        this.parameterId = parameterId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}