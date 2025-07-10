package com.chb.coses.cosesFramework.transfer;

import com.chb.coses.framework.transfer.DTO;

/**
 * Modify DTO for handling modification operations in the Coses Framework
 */
public class ModifyDTO extends DTO {

    private String operationType; // INSERT, UPDATE, DELETE
    private String tableName;
    private String primaryKey;
    private String primaryKeyValue;
    private String modifiedBy;
    private long modifiedTime;
    private String status;
    private String errorMessage;

    public ModifyDTO() {
        super();
        this.modifiedTime = System.currentTimeMillis();
    }

    public ModifyDTO(String operationType, String tableName) {
        this();
        this.operationType = operationType;
        this.tableName = tableName;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getPrimaryKeyValue() {
        return primaryKeyValue;
    }

    public void setPrimaryKeyValue(String primaryKeyValue) {
        this.primaryKeyValue = primaryKeyValue;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Check if operation is insert
     * 
     * @return true if insert, false otherwise
     */
    public boolean isInsert() {
        return "INSERT".equalsIgnoreCase(operationType);
    }

    /**
     * Check if operation is update
     * 
     * @return true if update, false otherwise
     */
    public boolean isUpdate() {
        return "UPDATE".equalsIgnoreCase(operationType);
    }

    /**
     * Check if operation is delete
     * 
     * @return true if delete, false otherwise
     */
    public boolean isDelete() {
        return "DELETE".equalsIgnoreCase(operationType);
    }

    /**
     * Check if operation was successful
     * 
     * @return true if successful, false otherwise
     */
    public boolean isSuccess() {
        return "SUCCESS".equalsIgnoreCase(status);
    }
}