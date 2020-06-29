package com.sample.validationdemo.controller.advice;

import com.sample.validationdemo.error.X2ApiErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class InternalExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException re) {
        Logger logger = LoggerFactory.getLogger(InternalExceptionAdvice.class);
        logger.error(re.getMessage());
        X2ApiErrors errors = new X2ApiErrors();
        errors.setMessage(re.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }

}
