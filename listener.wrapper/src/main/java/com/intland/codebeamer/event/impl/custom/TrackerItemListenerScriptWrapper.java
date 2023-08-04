package com.intland.codebeamer.event.impl.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.intland.codebeamer.event.BaseEvent;
import com.intland.codebeamer.event.TrackerItemListener;
import com.intland.codebeamer.event.util.VetoException;
import com.intland.codebeamer.manager.util.ActionData;
import com.intland.codebeamer.persistence.dto.AccessPermissionDto;
import com.intland.codebeamer.persistence.dto.ArtifactDto;
import com.intland.codebeamer.persistence.dto.TrackerItemDto;
import com.intland.codebeamer.persistence.dto.TrackerItemEscalationScheduleDto;
import com.intland.codebeamer.persistence.util.TrackerItemAttachmentGroup;
import com.intland.codebeamer.utils.scripting.ScriptingCache;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptEngine;

import org.apache.log4j.Logger;

@Component("trackerItemListenerScriptWrapper")
public class TrackerItemListenerScriptWrapper implements TrackerItemListener {

    protected static final Logger logger = Logger.getLogger(TrackerItemListenerScriptWrapper.class);

    private static final String SCRIPT_TYPE = "groovy";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void trackerItemCreated(BaseEvent<TrackerItemDto, TrackerItemDto, ActionData> event) throws VetoException {
        try {
            execute(event, "tracker_item_created.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        } 
    }

    @Override
    public void trackerItemUpdated(BaseEvent<TrackerItemDto, TrackerItemDto, ActionData> event) throws VetoException {
        try {
            execute(event, "tracker_item_updated.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }
    
    @Override
    public void trackerItemRemoved(BaseEvent<TrackerItemDto, TrackerItemDto, ActionData> event) throws VetoException {
        try {
            execute(event, "tracker_item_removed.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }     
    }
    
    @Override
    public void trackerItemRestored(BaseEvent<TrackerItemDto, TrackerItemDto, ActionData> event) throws VetoException {
        try {
            execute(event, "tracker_item_restored.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }      
    }
    
    @Override
    public void trackerItemDeleted(BaseEvent<TrackerItemDto, TrackerItemDto, ActionData> event) throws VetoException {
        try {
            execute(event, "tracker_item_deleted.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }       
    }
    
    @Override
    public void trackerItemEscalated(BaseEvent<TrackerItemDto, TrackerItemEscalationScheduleDto, ActionData> event) throws VetoException {
        try {
            execute(event, "tracker_item_escalated.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }       
    }
    
    @Override
    public void attachmentAdded(BaseEvent<TrackerItemAttachmentGroup, List<AccessPermissionDto>, ActionData> event) throws VetoException {
        try {
            execute(event, "attachment_added.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }
    }
    
    @Override
    public void attachmentUpdated(BaseEvent<TrackerItemAttachmentGroup, List<AccessPermissionDto>, ActionData> event) throws VetoException {
        try {
            execute(event, "attachment_updated.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }       
    }
    
    @Override
    public void attachmentRemoved(BaseEvent<TrackerItemDto, List<ArtifactDto>, ActionData> event) throws VetoException {
        try {
            execute(event, "attachment_removed.groovy");
        } catch (Exception e) {
            logger.error("", e);
            throw new VetoException(e);
        }       
    }
    
    protected void execute(BaseEvent<?, ?, ActionData> event, String scriptPath) throws Exception {
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
