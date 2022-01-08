package com.maciejBigos.poAwarii.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<org.springframework.validation.ObjectError> objectErrors = result.getAllErrors();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(objectErrors);
    }

    private Error processFieldErrors(List<org.springframework.validation.ObjectError> fieldErrors) {
        Error error = new Error(BAD_REQUEST.value(), "Validation error");
        for (org.springframework.validation.ObjectError fieldError: fieldErrors) {
           // error.addFieldError(fieldError.getObjectName(),fieldError.getField(), fieldError.getDefaultMessage());
            error.addFieldError(fieldError.getObjectName(),fieldError.getDefaultMessage());
        }
        return error;
    }

    static class Error {
        private final int status;
        private final String message;
        private List<ObjectError> fieldErrors = new ArrayList<>();

        Error(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public void addFieldError(String objName, String message) {
            ObjectError error = new ObjectError(objName, message);
            fieldErrors.add(error);
        }

        public List<ObjectError> getFieldErrors() {
            return fieldErrors;
        }
    }
}