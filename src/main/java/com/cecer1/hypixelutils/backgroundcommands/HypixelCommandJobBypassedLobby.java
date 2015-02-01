package com.cecer1.hypixelutils.backgroundcommands;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatMessageEventData;
import com.cecer1.hypixelutils.utils.ChatUtilities;

public class HypixelCommandJobBypassedLobby extends HypixelCommandJob {
    private boolean _started = false;
    private boolean _done = false;

    public HypixelCommandJobBypassedLobby() {
    }
    
    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData)data);
        if(data instanceof OnChatMessageEventData)
            onEvent((OnChatMessageEventData)data);
    }

    @Override
    public void onEvent(OnChatEventData data) {
        if (ChatUtilities.compareChatComponent(data.getMessage(), "{\"color\":\"green\",\"text\":\"Are you sure? Type /lobby again if you really want to quit.\"}"))
        {
            HypixelUtilsCore.sendChatMessage("/lobby");
            data.setCanceled(true);
            _done = true;
        }
    }

    @Override
    public void onEvent(OnChatMessageEventData data) {

    }
    @Override
    public void trigger() {
        HypixelUtilsCore.sendChatMessage("/lobby");
        _started = true;
    }

    @Override
    public boolean isStarted() {
        return _started;
    }

    @Override
    public boolean isDone() {
        return _done;
    }

    @Override
    public void complete() {
    }

    @Override
    public Cooldown getCooldown() {
        return null;
    }
}
