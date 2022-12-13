package com.api.parkingcontrol.exception;

public class BusinessException extends Exception {

    private String businessMessage;

    public BusinessException(String message) {
        super(message);
        this.businessMessage = message;
    }

    public String getBusinessMessage() {
        return businessMessage;
    }

    public void setBusinessMessage(String businessMessage) {
        this.businessMessage = businessMessage;
    }
}
