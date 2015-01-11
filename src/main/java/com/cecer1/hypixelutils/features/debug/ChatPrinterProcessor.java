package com.cecer1.hypixelutils.features.debug;

import com.cecer1.hypixelutils.chat.ChatMessage;
import com.cecer1.hypixelutils.chat.IChatMessageSubscriber;

public class ChatPrinterProcessor implements IChatMessageSubscriber
{
    @Override
    public boolean processChatMessage(ChatMessage message) {
        System.out.println("                 " + message.getPlayerName().getFullName().getUnformattedText() + ": " + message.getMessage().getUnformattedText());
        return true;
    }
}