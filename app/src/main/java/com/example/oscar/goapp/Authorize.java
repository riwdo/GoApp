package com.example.oscar.goapp;


import android.preference.PreferenceActivity;
import android.util.Base64;
import android.util.Log;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Oscar on 2017-01-16.
 */

public class Authorize {


    private static final String key = "PcrqHApo1THJt1VB4GJ3C6REh1ca";
    private static final String secret_key = "_fH6ngMNfQSv0wuEbl8HL9rhAsIa";

    private static String access_token = null;

    private Token token = null;

    private static int expires = 0;

    public Authorize() {
        token = new Token();
    }

    public String getLocation() {
        if (access_token != null) {
            RestClient.getInstance().addHeader("Authorization", "Bearer " + access_token);

            RestClient.getInstance().get("bin/rest.exe/v2/location.name?input=ols&format=json", null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String str = new String(responseBody, "UTF-8");
                        Log.d("Location", str);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("Failed to get location", error.toString());
                }
            });

        }
        return null;
    }


    public void newAsyncHTTP() throws ExecutionException, InterruptedException {


        String base64 = Base64.encodeToString((key + ":" + secret_key).getBytes(), Base64.DEFAULT);
        RestClient.getInstance().addHeader("Authorization", "Basic " + base64);

        HashMap<String, String> p = new HashMap<>();

        p.put("grant_type_fake", "fake");
        p.put("grant_type", "client_credentials");
        p.put("f_grant_type_fake2", "fake");


        RestClient.getInstance().post("token", p, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                RestClient.getInstance().getClient().removeAllHeaders();

                Log.d("POST_SUCCESS", "" + statusCode);
                Log.d("POST_SUCCESS", headers.toString());

                try {
                    String str = new String(responseBody, "UTF-8");
                    JSONObject json = new JSONObject(new String(responseBody));
                    token.setAccess_token(json.getString("access_token"));
                    token.setExpires_in(json.getInt("expires_in"));
                    token.setScope(json.getString("scope"));
                    token.setToken_type(json.getString("token_type"));
                    token.calculateExpireTime();
                    Log.d("Valid", String.valueOf(token.isValid()));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Fail", "Failed to retrieve token");
            }
        });


    }


}
