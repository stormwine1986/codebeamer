package com.intland.codebeamer.event.impl.custom;

import java.io.File;
import java.io.FileReader;

import javax.script.Bindings;
import javax.script.ScriptEngine;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.intland.codebeamer.event.BaseEvent;
import com.intland.codebeamer.event.UserListener;
import com.intland.codebeamer.event.util.VetoException;
import com.intland.codebeamer.utils.scripting.ScriptingCache;

@Component("userListenerScriptWrapper")
public class UserListenerScriptWrapper implements UserListener {

    protected static final Logger logger = Logger.getLogger(UserListenerScriptWrapper.class);

    private static final String SCRIPT_TYPE = "groovy";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void userCreated(BaseEvent event) throws VetoException {
        try {
            execute(event, "user_created.groovy");
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public void userUpdated(BaseEvent event) throws VetoException {
        try {
            execute(event, "user_updated.groovy");
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public void userDeleted(BaseEvent event) throws VetoException {
        try {
            execute(event, "user_deleted.groovy");
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    protected void execute(BaseEvent event, String scriptPath) throws Exception {
        File scriptFile = applicationContext.getResource("config/scripts/listener/" + scriptPath).getFile();
        
        // Not found listener script, do nothing.
        if(!scriptFile.exists()) return;

        ScriptEngine engine = ScriptingCache.get().getEngineByName(SCRIPT_TYPE);
        Bindings bindings = engine.getBindings(100);
        bindings.put("applicationContext", this.applicationContext);
        bindings.put("logger", logger);
        bindings.put("listener", this);
        bindings.put("event", event);
        bindings.put("user", event.getUser());
        bindings.put("beforeEvent", event.isPreEvent());
        bindings.put("script", scriptFile);
        engine.eval(new FileReader(scriptFile), bindings);
        return;
    }
    
}
