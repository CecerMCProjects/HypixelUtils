package com.cecer1.hypixelutils.features.bypasslobbyprotection;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;

public class BypassLobbyProtectionProcessor implements IOnChatEventHandler
{
    private int _enableBypasses;

    private boolean isEnabled()
    {
        if(!HypixelUtilsCore.currentState.isConnected())
            return false;

        if(_enableBypasses > 0)
        {
            _enableBypasses--;
            return true;
        }
        return HypixelUtilsCore.config.isBypassLobbyProtectionEnabled();
    }
    public BypassLobbyProtectionProcessor enableOnce()
    {
        _enableBypasses++;
        return this;
    }

    @Override
    public void onChat(IOnChatEventData event) {
        if(!isEnabled())
            return;

        if (ChatUtilities.compareChatComponent(event.getMessage(), "{\"color\":\"green\",\"text\":\"Are you sure? Type /lobby again if you really want to quit.\"}"))
        {
            HypixelUtilsCore.sendChatMessage("/lobby");
            event.setCanceled(true);
        }
    }
}