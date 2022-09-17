package com.project.common;

import java.util.List;

public class ResponseForm {
    String message;
    List<FieldError> fieldErrorList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public ResponseForm(String message) {
        this.message = message;
    }

    public List<FieldError> getFieldErrorList() {
        return fieldErrorList;
    }

    public void setFieldErrorList(List<FieldError> filedErrorList) {
        this.fieldErrorList = filedErrorList;
    }

    public static class FieldError{
        String message;
        String value;
        String name;

        public FieldError(String message, String value, String name) {
            this.message = message;
            this.value = value;
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
