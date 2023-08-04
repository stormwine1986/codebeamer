package com.intland.codebeamer.event.impl.custom;

import java.io.File;
import java.io.FileReader;
import java.util.Date;

import javax.script.Bindings;
import javax.script.ScriptEngine;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.intland.codebeamer.event.BaseEvent;
import com.intland.codebeamer.event.ProjectListener;
import com.intland.codebeamer.event.util.VetoException;
import com.intland.codebeamer.persistence.dto.DailyProjectStatsDto;
import com.intland.codebeamer.persistence.dto.ProjectDto;
import com.intland.codebeamer.persistence.dto.UserDto;
import com.intland.codebeamer.utils.scripting.ScriptingCache;

@Component("projectListenerScriptWrapper")
public class ProjectListenerScriptWrapper implements ProjectListener {

    protected static final Logger logger = Logger.getLogger(ProjectListenerScriptWrapper.class);

    private static final String SCRIPT_TYPE = "groovy";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void projectCreated(BaseEvent<ProjectDto, Void, Void> event) throws VetoException {
        try {
            execute(event, "project_created.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    @Override
    public void projectUpdated(BaseEvent<ProjectDto, ProjectDto, Void> event) throws VetoException {
        try {
            execute(event, "project_updated.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    @Override
    public void projectDeleted(BaseEvent<ProjectDto, Void, Void> event) throws VetoException {
        try {
            execute(event, "project_deleted.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    @Override
    public void projectDailyStatistics(BaseEvent<ProjectDto, Date, DailyProjectStatsDto> event) throws VetoException {
        try {
            execute(event, "project_dailyStatistics.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    @Override
    public void userJoinRequested(BaseEvent<ProjectDto, String, Void> event) throws VetoException {
        try {
            execute(event, "user_join_requested.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    @Override
    public void userJoinAccepted(BaseEvent<ProjectDto, String, UserDto> event) throws VetoException {
        try {
            execute(event, "user_join_accepted.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    @Override
    public void userJoinRejected(BaseEvent<ProjectDto, String, UserDto> event) throws VetoException {
        try {
            execute(event, "user_join_rejected.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    protected void execute(BaseEvent<?,?,?> event, String scriptPath) throws Exception {
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
