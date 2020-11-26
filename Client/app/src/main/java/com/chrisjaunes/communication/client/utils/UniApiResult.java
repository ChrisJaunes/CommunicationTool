package com.chrisjaunes.communication.client.utils;

public class UniApiResult<T> {
    public String status;
    public T data;
    UniApiResult(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static class Fail extends UniApiResult<String> {
        public Fail(String status, String data) {
            super(status, data);
        }
    }
}
