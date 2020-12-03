package com.chrisjaunes.communication.client.utils;

public class UniApiResult<T> {
    public String status;
    public T data;
    public UniApiResult(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static class Fail extends UniApiResult<String> {
        public String error;
        public Fail(String status, String data) {
            super(status, data);
        }
        public Fail(String status, String data, String error) {
            super(status, data);
            this.error = error;
        }
    }
}
