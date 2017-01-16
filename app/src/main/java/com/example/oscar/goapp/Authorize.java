package com.example.oscar.goapp;


import android.preference.PreferenceActivity;
import android.util.Base64;
import android.util.Log;

import com.loopj.android.http.*;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Oscar on 2017-01-16.
 */

public class Authorize  {


    private static final String key = "PcrqHApo1THJt1VB4GJ3C6REh1ca";
    private static final String secret_key = "_fH6ngMNfQSv0wuEbl8HL9rhAsIa";



    public Authorize(){
    }

    public void newAsyncHTTP() throws ExecutionException, InterruptedException {


        String base64 = Base64.encodeToString((key+":"+secret_key).getBytes(), Base64.DEFAULT);
        RestClient.getInstance().addHeader("Authorization", "Basic " + base64);

        HashMap<String,String> p = new HashMap<>();

        p.put("grant_type_fake", "fake");
        p.put("grant_type", "client_credentials");
        p.put("f_grant_type_fake2", "fake");

        RestClient.getInstance().post("token", p, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                RestClient.getInstance().getClient().removeAllHeaders();

                RestClient.getInstance().addHeader("Authorization", "Bearer "+responseBody.toString());

                RestClient.getInstance().get("bin/rest.exe/v2/location.name?input=ols&format=json", null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("Get", "Successfully get");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e("FailGet", "Failed get");
                    }
                });

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Fail","Failed to retrieve token");
            }
        });


    }

}
