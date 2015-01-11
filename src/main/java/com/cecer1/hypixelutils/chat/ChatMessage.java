package com.cecer1.hypixelutils.chat;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

import java.util.EnumSet;

public class ChatMessage {
    private final PlayerName _playerName;
    private final IChatComponent _message;
    public EnumSet<ChatType> chatTypes;

    public ChatMessage(PlayerName playerName, ChatStyle messageStyle, String message) {
        _playerName = playerName;
        _message = new ChatComponentText(message).setChatStyle(messageStyle);
        chatTypes = EnumSet.noneOf(ChatType.class);
    }

    public PlayerName getPlayerName() {
        return _playerName;
    }

    public IChatComponent getMessage() {
        return _message;
    }
}
