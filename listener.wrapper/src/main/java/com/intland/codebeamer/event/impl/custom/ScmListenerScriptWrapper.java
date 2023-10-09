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
import com.intland.codebeamer.event.ScmListener;
import com.intland.codebeamer.event.util.VetoException;
import com.intland.codebeamer.manager.scm.jobs.SCMSynchResult;
import com.intland.codebeamer.persistence.dto.ProjectDto;
import com.intland.codebeamer.persistence.dto.ScmChangeSetDto;
import com.intland.codebeamer.persistence.dto.ScmRepositoryDto;
import com.intland.codebeamer.utils.scripting.ScriptingCache;

@Component("scmListenerScriptWrapper")
public class ScmListenerScriptWrapper implements ScmListener {

    protected static final Logger logger = Logger.getLogger(ScmListenerScriptWrapper.class);

    private static final String SCRIPT_TYPE = "groovy";

    @Autowired
    private ApplicationContext applicationContext;

    public void repositoryCreated(BaseEvent<ScmRepositoryDto, Void, Void> event) throws VetoException {
        try {
            execute(event, "repository_created.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    public void repositoryUpdated(BaseEvent<ScmRepositoryDto, ScmRepositoryDto, Void> event) throws VetoException {
        try {
            execute(event, "repository_updated.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    public void repositoryDeleted(BaseEvent<ScmRepositoryDto, Void, Void> event) throws VetoException {
        try {
            execute(event, "repository_deleted.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    public void repositorySynchronized(BaseEvent<ScmRepositoryDto, ProjectDto, SCMSynchResult> event) throws VetoException {
        try {
            execute(event, "repository_synchronized.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    public void changeSetCommitted(BaseEvent<ScmChangeSetDto, Void, ScmChangeData> event) throws VetoException {
        try {
            execute(event, "change_set_committed.groovy");
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
