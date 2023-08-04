package com.intland.codebeamer.event.impl.custom;

import java.io.File;
import java.io.FileReader;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptEngine;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.intland.codebeamer.event.BaseEvent;
import com.intland.codebeamer.event.TrackerSynchronizationListener;
import com.intland.codebeamer.event.util.VetoException;
import com.intland.codebeamer.manager.util.SyncStatistics;
import com.intland.codebeamer.manager.util.TrackerSyncConfigurationDto;
import com.intland.codebeamer.persistence.dto.AssociationDto;
import com.intland.codebeamer.persistence.dto.TrackerDto;
import com.intland.codebeamer.persistence.dto.base.VersionReferenceDto;
import com.intland.codebeamer.utils.scripting.ScriptingCache;

@Component("trackerSynchronizationListenerScriptWrapper")
public class TrackerSynchronizationListenerScriptWrapper implements TrackerSynchronizationListener {

    protected static final Logger logger = Logger.getLogger(TrackerSynchronizationListenerScriptWrapper.class);

    private static final String SCRIPT_TYPE = "groovy";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void trackerSynchronizationConfigured(BaseEvent<TrackerSyncConfigurationDto, AssociationDto<?, TrackerDto>, ObjectNode> event) throws VetoException {
        try {
            execute(event, "tracker_synchronization_configured.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    @Override
    public void trackerSynchronizationRemoved(BaseEvent<AssociationDto<?, TrackerDto>, VersionReferenceDto<?>, Map<String, Object>> event) throws VetoException {
        try {
            execute(event, "tracker_synchronization_removed.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }

    @Override
    public void trackerSynchronized(BaseEvent<TrackerSyncConfigurationDto, TransactionStatus, SyncStatistics> event) {
        try {
            execute(event, "tracker_synchronized.groovy");
        } catch (Exception e) {
            logger.error("", e);
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
