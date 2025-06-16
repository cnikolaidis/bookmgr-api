package com.iekakmi.bookmgr_api.business.exceptions;

public class BusinessLayerException extends Exception {
    
    public BusinessLayerException(String message) {
        super(message);
    }

    public BusinessLayerException(String message, Throwable cause) {
        super(message, cause);
    }
}