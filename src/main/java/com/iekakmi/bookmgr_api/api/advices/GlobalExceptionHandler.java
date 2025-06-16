package com.iekakmi.bookmgr_api.api.advices;

import com.iekakmi.bookmgr_api.business.exceptions.BusinessLayerException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessLayerException.class)
    public ResponseEntity<?> handleBusinessLayerException(BusinessLayerException x) {
        return ResponseEntity
                .badRequest()
                .body(x.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception x) {
        return ResponseEntity
                .internalServerError()
                .body(x);
    }
}
