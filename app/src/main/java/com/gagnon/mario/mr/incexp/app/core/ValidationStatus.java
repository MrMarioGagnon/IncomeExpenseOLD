package com.gagnon.mario.mr.incexp.app.core;

public class ValidationStatus {

    private String mMessage = "";

    private ValidationStatus(String message) {
        mMessage = message;
    }

    public static ValidationStatus create(String message) {
        return new ValidationStatus(message);
    }

    public String getMessage() {
        return mMessage;
    }

    public boolean isValid() {
        return mMessage.length() == 0;
    }

}
