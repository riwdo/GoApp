package com.example.oscar.goapp;

import android.util.Log;

import java.util.Date;

/**
 * Created by Oscar on 2017-01-16.
 */

public class Token {
    private String scope;
    private Date expiration;
    private String refresh_token;
    private String access_token;
    private String token_type;
    private int expires_in;

    private String TAG = "TOKEN";


    public String getToken() {
        return access_token;
    }


    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }


    public boolean isValid() {
        Date date = new Date();
        return date.before(expiration);
    }

    public void calculateExpireTime() {
        Date date = new Date();
        date.setTime(date.getTime() + expires_in * 1000);
        expiration = date;
    }


}
