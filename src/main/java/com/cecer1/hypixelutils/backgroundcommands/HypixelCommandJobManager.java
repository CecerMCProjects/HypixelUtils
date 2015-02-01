package com.cecer1.hypixelutils.backgroundcommands;

import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatMessageEventData;
import com.cecer1.hypixelutils.events.eventdata.OnTickEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import com.cecer1.hypixelutils.events.handlers.IOnChatMessageEventHandler;
import com.cecer1.hypixelutils.events.handlers.IOnTickEventHandler;

import java.util.ArrayDeque;
import java.util.Queue;

public class HypixelCommandJobManager implements IOnChatEventHandler, IOnChatMessageEventHandler, IOnTickEventHandler {
    private static final int BOOSTER_COMMAND_COOLDOWN = 20;
    private static final int MSG_COMMAND_COOLDOWN = 12;

    
    public Cooldown boosterCommandCooldown;
    public Cooldown msgCommandCooldown;

    
    private HypixelCommandJob _currentJob;
    private Queue<HypixelCommandJob> _jobQueue;

    public HypixelCommandJobManager() {
        reset();
    }
    
    public void reset() {
        _currentJob = null;
        _jobQueue = new ArrayDeque<HypixelCommandJob>();
        boosterCommandCooldown = new Cooldown(BOOSTER_COMMAND_COOLDOWN);
        msgCommandCooldown = new Cooldown(MSG_COMMAND_COOLDOWN);
    }
    
    public void queueJob(HypixelCommandJob job) {
        _jobQueue.add(job);
    }

    // Having no locking should be fine as it should only be used in one thread anyway.
    private void processJobs() {
        if(_currentJob == null) {
            if (_jobQueue.size() == 0)
                return;
            _currentJob = _jobQueue.remove();
        }
        if(!_currentJob.isStarted()) {
            Cooldown jobCooldown = _currentJob.getCooldown();
            if (jobCooldown != null && jobCooldown.isCooling())
                return;

            _currentJob.trigger();
            
            if (jobCooldown != null)
                jobCooldown.reset();
            return;
        }
        if(_currentJob.isDone()) {
            _currentJob.complete();
            _currentJob = null;
            return;
        }
        if(_currentJob.isTimedOut()) {
            _currentJob.timedOut();
        }
    }

    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatMessageEventData)
            onEvent((OnChatMessageEventData) data);
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData) data);
        if(data instanceof OnTickEventData)
            onEvent((OnTickEventData) data);
    }

    @Override
    public void onEvent(OnChatEventData data) {
        if(_currentJob == null)
            return;
        _currentJob.onEvent(data);
    }

    @Override
    public void onEvent(OnChatMessageEventData data) {
        if(_currentJob == null)
            return;
        _currentJob.onEvent(data);
    }

    @Override
    public void onEvent(OnTickEventData data) {
        processJobs();
    }

}
