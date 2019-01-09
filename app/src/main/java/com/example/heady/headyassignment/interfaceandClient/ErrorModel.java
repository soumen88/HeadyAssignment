package com.example.heady.headyassignment.interfaceandClient;


import android.support.annotation.Keep;

import java.io.Serializable;


@Keep
public class ErrorModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer errorCode;

    private String errorMessage;

    private String humanizedMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getHumanizedMessage() {
        return humanizedMessage;
    }

    public void setHumanizedMessage(String humanizedMessage) {
        this.humanizedMessage = humanizedMessage;
    }
}

