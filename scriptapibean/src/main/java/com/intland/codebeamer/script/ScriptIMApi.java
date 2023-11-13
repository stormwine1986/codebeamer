package com.intland.codebeamer.script;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.intland.codebeamer.script.api.IScriptIMApi;

public class ScriptIMApi implements IScriptIMApi {

    private ScriptApiBeanConfig config;

    protected ScriptIMApi(ScriptApiBeanConfig config){
        this.config = config;
    }

    @Override
    public JSONObject get(Integer id) throws ApiException {
        HttpURLConnection conn = null;
        try{
            URL url = new URL(config.root + "/rest/item/" + id);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(config.timeout);
            conn.setReadTimeout(config.timeout);
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Authorization","Basic " + new String(Base64.getEncoder().encode((config.user + ":" + config.password).getBytes())));

            int responseCode = conn.getResponseCode();
            if(responseCode >= 200 && responseCode < 300){
                InputStream inputStream = conn.getInputStream();
                return new JSONObject(IOUtils.toString(inputStream, "UTF-8"));
            }else{
                InputStream errorStream = conn.getErrorStream();
                throw new ApiException(responseCode, new JSONObject(IOUtils.toString(errorStream, "UTF-8")));
            }
            
        }catch(IOException e){
            throw new ApiException(e);
        }finally{
            if(conn != null){
                conn.disconnect();
            }
        }
    }

    @Override
    public JSONObject create(JSONObject data) throws ApiException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void delete(Integer id) throws ApiException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void update(JSONObject data) throws ApiException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public JSONArray query(int page, int pagesize, String queryString) throws ApiException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'query'");
    }
    
}
