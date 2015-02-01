package com.cecer1.hypixelutils.backgroundcommands;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.backgroundcommands.callbacks.IHypixelCommandCallbackWtfmap;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatMessageEventData;
import com.cecer1.hypixelutils.utils.ChatUtilities;
import net.minecraft.util.IChatComponent;

public class HypixelCommandJobWtfmap extends HypixelCommandJob {
    private boolean _started = false;
    private boolean _done = false;
    private String _result = null;
    private IHypixelCommandCallbackWtfmap _callback;

    public HypixelCommandJobWtfmap(IHypixelCommandCallbackWtfmap callback) {
        _callback = callback;
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
        IChatComponent message = data.getMessage();
        if(message.getSiblings().size() == 0)
            return;
        
        IChatComponent firstSibling = (IChatComponent) message.getSiblings().get(0);
        if (ChatUtilities.compareChatComponent(firstSibling, "{\"color\":\"green\",\"text\":\"You are currently playing on \"}")) {
            IChatComponent locationComponent = (IChatComponent) message.getSiblings().get(0);
            _result = locationComponent.getUnformattedText();
            _done = true;
            data.setCanceled(true);
        } else if (firstSibling.getUnformattedText().equals("This command is not available on this server!")) {
            _result = null;
            _done = true;
            data.setCanceled(true);
        }
    }
    
    @Override
    public void onEvent(OnChatMessageEventData data) {

    }
    
    @Override
    public void trigger() {
        HypixelUtilsCore.sendChatMessage("/wtfmap");
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
        _callback.result(_result);
    }

    @Override
    public Cooldown getCooldown() {
        return null;
    }
    
    private Cooldown _timeout = new Cooldown(60); // 60 tick timeout
    @Override
    public boolean isTimedOut() {
        return !_timeout.isCooling();
    }
    public void timedOut() {
        _callback.result(null);
    }
}
