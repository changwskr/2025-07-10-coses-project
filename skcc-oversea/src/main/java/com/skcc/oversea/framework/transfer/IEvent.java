package com.skcc.oversea.framework.transfer;

public interface IEvent {
    String getSource();

    void setSource(String source);

    String getType();

    void setType(String type);

    Object getData();

    void setData(Object data);
}