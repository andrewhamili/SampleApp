package com.hamiliserver.sampleapp.model;

public class Response {

    private int httpResponseCode;

    private String responseJson;


    public Response() {
    }

    public Response(int httpResponseCode, String responseJson) {
        this.httpResponseCode = httpResponseCode;
        this.responseJson = responseJson;
    }

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }
}