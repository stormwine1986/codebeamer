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
import com.intland.codebeamer.event.WorkflowListener;
import com.intland.codebeamer.event.util.VetoException;
import com.intland.codebeamer.manager.util.ActionData;
import com.intland.codebeamer.persistence.dto.TrackerItemDto;
import com.intland.codebeamer.persistence.dto.WorkflowTransitionDto;
import com.intland.codebeamer.utils.scripting.ScriptingCache;

@Component("workflowListenerScriptWrapper")
public class WorkflowListenerScriptWrapper implements WorkflowListener {

    protected static final Logger logger = Logger.getLogger(WorkflowListenerScriptWrapper.class);

    private static final String SCRIPT_TYPE = "groovy";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void transitionTaken(BaseEvent<WorkflowTransitionDto, TrackerItemDto, ActionData> event) throws VetoException {
        try {
            execute(event, "transition_taken.groovy");
        } catch (Exception e) {
            logger.error("", e);
        }
    }
    
    protected void execute(BaseEvent<WorkflowTransitionDto, TrackerItemDto, ActionData> event, String scriptPath) throws Exception {
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
