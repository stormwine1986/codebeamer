package com.intland.codebeamer.script;

import org.json.JSONObject;

/**
 * Api调用异常
 * 
 */
public class ApiException extends Exception {

    private JSONObject body = null;
    private Integer responseCode = null;

    public ApiException(int responseCode, JSONObject body){
        this.body = body;
        this.responseCode = responseCode;
    }

    public ApiException(Throwable cause){
        super(cause);
    }

    public JSONObject getBody(){
        return body;
    }

    public Integer getResponseCode(){
        return responseCode;
    }

    @Override
    public String toString(){
        return "responseCode = " + responseCode + ", body = " + body + ", cause = " + super.getCause();
    }
}
