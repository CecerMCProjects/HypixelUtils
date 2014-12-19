package com.cecer1.hypixelutils.events;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;

public class DebugProcessor implements IOnChatEventHandler
{
    @Override
    public void onChat(IOnChatEventData event) {
        if(!HypixelUtilsCore.config.isDebugModeEnabled())
            return;

        System.out.println(ChatUtilities.convertChatComponentToJson(event.getMessage()));
    }
}