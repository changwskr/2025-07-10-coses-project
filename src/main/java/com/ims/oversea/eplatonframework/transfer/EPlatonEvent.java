package com.ims.oversea.eplatonframework.transfer;

import java.io.Serializable;
import java.util.Date;

/**
 * EPlaton Event Data Transfer Object
 */
public class EPlatonEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String eventId;
    private Date eventTime;
    private String userId;
    private Object eventData;
    private TPSVCINFODTO tpsvcinfoDTO;
    private EPlatonCommonDTO common;

    public EPlatonEvent() {
        this.eventTime = new Date();
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getEventData() {
        return eventData;
    }

    public void setEventData(Object eventData) {
        this.eventData = eventData;
    }

    public TPSVCINFODTO getTPSVCINFODTO() {
        return tpsvcinfoDTO;
    }

    public void setTPSVCINFODTO(TPSVCINFODTO tpsvcinfoDTO) {
        this.tpsvcinfoDTO = tpsvcinfoDTO;
    }

    public EPlatonCommonDTO getCommon() {
        return common;
    }

    public void setCommon(EPlatonCommonDTO common) {
        this.common = common;
    }
}
