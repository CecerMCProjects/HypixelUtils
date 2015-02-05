package com.cecer1.hypixelutils.features.debug;

import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatMessageEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import com.cecer1.hypixelutils.events.handlers.IOnChatMessageEventHandler;
import com.cecer1.hypixelutils.utils.ChatUtilities;

public class ChatPrinterProcessor implements IOnChatMessageEventHandler, IOnChatEventHandler
{
    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatMessageEventData)
            onEvent((OnChatMessageEventData)data);
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData)data);
    }

    @Override
    public void onEvent(OnChatMessageEventData data) {
        System.out.println("                 " + data.getPlayerName().getFullName().getUnformattedText() + ": " + data.getMessage().getUnformattedText());
    }

    @Override
    public void onEvent(OnChatEventData data) {
        System.out.println("    >>> " + ChatUtilities.convertChatComponentToJson(data.getMessage()));
    }
}