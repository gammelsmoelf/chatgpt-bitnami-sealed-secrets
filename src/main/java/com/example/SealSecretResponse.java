package com.example;

public class SealSecretResponse {
    String data;

    public SealSecretResponse(String data) {
        this.data = data;
    }

    public SealSecretResponse() {

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
