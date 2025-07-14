package com.skcc.oversea.framework.transfer;

import com.skcc.oversea.foundation.constant.Constants;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseDTO {

    private String id;
    private String status;
    private String message;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public BaseDTO() {
        this.createDate = LocalDateTime.now();
    }

    public String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT_YYYY_MM_DD_HH_MM_SS);
        return dateTime.format(formatter);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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