package com.example.oscar.goapp;

/**
 * Created by Oscar on 2017-01-16.
 */

import com.loopj.android.http.*;

import java.util.Map;

public class RestClient {
    private static final String BASE_URL = "https://api.vasttrafik.se/";

    private final AsyncHttpClient client;

    private static RestClient instance = null;

    public static RestClient getInstance(){
        if(instance == null){
            instance = new RestClient();
        }
        return instance;

    }

    public RestClient(){
        client = new AsyncHttpClient();
    }


    public void addHeader(String header, String value){
        client.addHeader(header, value);
    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, Map<String, String> paramsMap, AsyncHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams(paramsMap);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public AsyncHttpClient getClient(){
        return client;
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
