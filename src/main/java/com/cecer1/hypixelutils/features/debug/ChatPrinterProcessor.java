package com.cecer1.hypixelutils.features.debug;

import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatMessageEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatMessageEventHandler;

public class ChatPrinterProcessor implements IOnChatMessageEventHandler
{
    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatMessageEventData)
            onEvent((OnChatMessageEventData)data);
    }

    @Override
    public void onEvent(OnChatMessageEventData data) {
        System.out.println("                 " + data.getPlayerName().getFullName().getUnformattedText() + ": " + data.getMessage().getUnformattedText());
    }
}