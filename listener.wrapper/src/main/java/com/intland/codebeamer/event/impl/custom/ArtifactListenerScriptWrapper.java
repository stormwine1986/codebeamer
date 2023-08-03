package com.intland.codebeamer.event.impl.custom;

import java.io.File;
import java.io.FileReader;

import javax.script.Bindings;
import javax.script.ScriptEngine;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.intland.codebeamer.event.ArtifactListener;
import com.intland.codebeamer.event.BaseEvent;
import com.intland.codebeamer.event.util.VetoException;
import com.intland.codebeamer.persistence.dto.ArtifactApprovalHistoryEntryDto;
import com.intland.codebeamer.persistence.dto.ArtifactDto;
import com.intland.codebeamer.utils.scripting.ScriptingCache;

@Component("artifactListenerScriptWrapper")
public class ArtifactListenerScriptWrapper implements ArtifactListener {

    protected static final Logger logger = Logger.getLogger(ArtifactListenerScriptWrapper.class);

    private static final String SCRIPT_TYPE = "groovy";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void artifactCreated(BaseEvent<ArtifactDto, Void, Boolean> event) throws VetoException {
        try {
            execute(event, "artifact_created.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        } 
    }

    @Override
    public void artifactUpdated(BaseEvent<ArtifactDto, ArtifactDto, Boolean> event) throws VetoException {
        try {
            execute(event, "artifact_updated.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    @Override
    public void artifactDeleted(BaseEvent<ArtifactDto, Void, Void> event) throws VetoException {
        try {
            execute(event, "artifact_deleted.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    @Override
    public void artifactOpened(BaseEvent<ArtifactDto, Void, Void> event) throws VetoException {
        try {
            execute(event, "artifact_opened.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    @Override
    public void artifactApproved(BaseEvent<ArtifactApprovalHistoryEntryDto, Void, Void> event) throws VetoException {
        try {
            execute(event, "artifact_approved.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }
    
    protected void execute(BaseEvent<?, ?, ?> event, String scriptPath) throws Exception {
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
