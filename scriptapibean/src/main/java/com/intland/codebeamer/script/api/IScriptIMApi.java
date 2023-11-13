package com.intland.codebeamer.script.api;

import org.json.JSONArray;
import org.json.JSONObject;

import com.intland.codebeamer.script.ApiException;

/**
 * 跟踪项条目相关的API
 * 
 */
public interface IScriptIMApi {

    /**
     * 创建条目
     * 
     * @param data item 的属性, 可用属性清单见：GET {trackerURI}/newItem 
     * @return {"id":"","uri":"","name":""}
     * @throws ApiException
     */
    JSONObject create(JSONObject data) throws ApiException;

    /**
     * 删除指定id的条目
     * 
     * @param id
     * @return void
     * @throws ApiException
     */
    void delete(Integer id) throws ApiException;

    /**
     * 更新条目
     * 
     * @param data 
     * @return
     * @throws ApiException
     */
    void update(JSONObject data) throws ApiException;

    /**
     * 获取指定id的条目
     * 
     * @param id 条目ID
     * @return 条目
     * @throws ApiException
     */
    JSONObject get(Integer id) throws ApiException;

    /**
     * 分页查询条目
     * 
     * @param page 页码
     * @param pagesize 页大小
     * @param queryString 查询条件
     * @return 条目列表
     * @throws ApiException
     */
    JSONArray query(int page, int pagesize, String queryString) throws ApiException;
}
