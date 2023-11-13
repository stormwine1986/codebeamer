package com.intland.codebeamer.script;

import org.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import com.intland.codebeamer.manager.configuration.DaoBasedConfigurationManager;
import com.intland.codebeamer.persistence.dao.configuration.ApplicationConfiguration;

@Component
public class ScriprApiBean implements ApplicationContextAware {

    private ApplicationContext ctx;
    private ScriptApiBeanConfig config = new ScriptApiBeanConfig();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
        DaoBasedConfigurationManager manager = ctx.getBean(DaoBasedConfigurationManager.class);
        ApplicationConfiguration ac = manager.getConfiguration().orElse(new ApplicationConfiguration("{}"));
        JSONObject data = new JSONObject(ac.getConfiguration());
        if(data.has("script-api-bean")){
            if(data.getJSONObject("script-api-bean").has("user")) config.user = data.getJSONObject("script-api-bean").getString("user");
            if(data.getJSONObject("script-api-bean").has("password")) config.password = data.getJSONObject("script-api-bean").getString("password");
            if(data.getJSONObject("script-api-bean").has("timeout")) config.timeout = data.getJSONObject("script-api-bean").getInt("timeout");
            if(data.getJSONObject("script-api-bean").has("root")) config.root = data.getJSONObject("script-api-bean").getString("root");
        }

        im = new ScriptIMApi(config);
    }

    @Override
    public String toString(){
        return String.format("ScriprApiBean {user=%s, timeout=%s, root=%s}", config.user, config.timeout, config.root);
    }

    /**
     * 与条目有关的API
     */
    public ScriptIMApi im;
}
