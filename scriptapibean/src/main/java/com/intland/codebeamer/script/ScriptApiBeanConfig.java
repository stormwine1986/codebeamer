package com.intland.codebeamer.script;

/**
 * ScriptApiBean的配置信息
 * 
 */
public class ScriptApiBeanConfig {

    protected ScriptApiBeanConfig(){}

    /**
     * codebeamer服务的 root
     */
    protected String root;
    /**
     * 执行用户
     */
    protected String user = "none";
    /**
     * 执行用户的密码
     */
    protected String password = "none";
    /**
     * 执行超时时间 ms
     */
    protected Integer timeout = 5000;
}
