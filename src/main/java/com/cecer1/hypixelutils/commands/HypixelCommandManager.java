package com.cecer1.hypixelutils.commands;

import com.cecer1.hypixelutils.chat.ChatMessage;
import com.cecer1.hypixelutils.chat.IChatMessageSubscriber;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.events.IOnTickEventHandler;

import java.util.ArrayDeque;
import java.util.Queue;

public class HypixelCommandManager implements IChatMessageSubscriber, IOnChatEventHandler, IOnTickEventHandler {
    private static final int BOOSTER_COMMAND_COOLDOWN = 1000;
    private static final int MSG_COMMAND_COOLDOWN = 600;

    
    public Cooldown boosterCommandCooldown;
    public Cooldown msgCommandCooldown;

    
    private HypixelCommandJob _currentJob;
    private Queue<HypixelCommandJob> _jobQueue;

    public HypixelCommandManager() {
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

            Cooldown jobCooldown = _currentJob.getCooldown();
            if (jobCooldown != null && jobCooldown.isCooling())
                return;

            _currentJob.trigger();
            
            if (jobCooldown != null)
                jobCooldown.reset();
        } else if(_currentJob.isDone()) {
            _currentJob.complete();
            _currentJob = null;
        }
    }

    @Override
    public boolean processChatMessage(ChatMessage message) {
        if(_currentJob == null)
            return true;

        boolean result = _currentJob.processChatMessage(message);
        return result;
    }

    @Override
    public void onChat(IOnChatEventData event) {
        if(_currentJob == null)
            return;

        _currentJob.onChat(event);
    }
    @Override
    public void onTick(IOnTickEventData event) {
        boosterCommandCooldown.onTick(event);

        processJobs();
    }
}
