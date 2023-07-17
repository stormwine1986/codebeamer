package com.intland.codebeamer.event.impl.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.intland.codebeamer.event.BaseEvent;
import com.intland.codebeamer.event.TrackerItemListener;
import com.intland.codebeamer.event.impl.CustomScriptExecutor;
import com.intland.codebeamer.event.util.VetoException;
import com.intland.codebeamer.manager.util.ActionData;
import com.intland.codebeamer.persistence.dto.ArtifactDto;
import com.intland.codebeamer.persistence.dto.TrackerItemDto;

import java.net.URI;
import java.net.URL;

import org.apache.log4j.Logger;

@Component("reviewhubListener")
public class ReviewhubListener implements TrackerItemListener {

    protected static final Logger log = Logger.getLogger(ReviewhubListener.class);

    private static final String TRACKER_KEY_REVIEW = "REVIEW";

    private static final String SCRIPT_TYPE = "groovy";

    private static final String SCRIPT_PATH = "review_created.groovy";

    @Autowired
    @Qualifier("customScriptExecutor")
    private CustomScriptExecutor scriptExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void trackerItemCreated(BaseEvent<TrackerItemDto, TrackerItemDto, ActionData> event) throws VetoException {
        if(TRACKER_KEY_REVIEW.equals(event.getSource().getTracker().getKeyName())){
            log.info(event.getSource());
            
            try{
                // file:/home/appuser/codebeamer/tomcat/webapps/ROOT/
                boolean exists = applicationContext.getResource("config/scripts/workflow/" + SCRIPT_PATH).exists();
                log.info(SCRIPT_PATH + " exists = " + exists);
                if(!exists){
                    return;
                }
                if(event.isPreEvent()){
                    BaseEvent<ArtifactDto, TrackerItemDto, ActionData> event2 = new BaseEvent<>(true, new ArtifactDto(), event.getSource(), event.getUser(), event.getRequest(), event.getData()); 
                    scriptExecutor.preExecute(event2, event.getUser(), event.getSource(), SCRIPT_TYPE, SCRIPT_PATH, true);
                } else {
                    BaseEvent<ArtifactDto, TrackerItemDto, ActionData> event2 = new BaseEvent<>(false, new ArtifactDto(), event.getSource(), event.getUser(), event.getRequest(), event.getData());
                    scriptExecutor.postExecute(event2, event.getUser(), event.getSource(), SCRIPT_TYPE, SCRIPT_PATH, true);
                }
            } catch(Exception e){
                throw new VetoException(e.getMessage(), e);
            }
        }
    }
}
