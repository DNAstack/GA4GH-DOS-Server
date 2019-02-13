package com.dnastack.dos.server.response;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String msg;
    private int status;

    public ErrorResponse(String msg, int status, Throwable ex) {
        this.status = status;
        this.msg = msg + ": " + ex;
    }

}