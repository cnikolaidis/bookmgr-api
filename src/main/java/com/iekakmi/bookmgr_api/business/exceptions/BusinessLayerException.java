package com.iekakmi.bookmgr_api.business.exceptions;

public class BusinessLayerException extends RuntimeException {
    
    public BusinessLayerException(String message) {
        super(message);
    }
}