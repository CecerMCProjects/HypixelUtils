package com.cecer1.hypixelutils.chat;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class PlayerName {
    private final IChatComponent _tag;
    private final IChatComponent _name;
    private final IChatComponent _fullName;

    public PlayerName(IChatComponent tag, String name) {
        this(tag, tag.getChatStyle(), name);
    }
    public PlayerName(IChatComponent tag, ChatStyle nameStyle, String name) {
        _tag = tag;
        _name = new ChatComponentText(name.trim()).setChatStyle(nameStyle);
        _fullName = tag.createCopy().appendText(" ").appendSibling(_name);
    }
    public PlayerName(String name) {
        this(new ChatStyle().setColor(EnumChatFormatting.GRAY), name);
    }
    public PlayerName(ChatStyle nameStyle, String name) {
        _tag = null;
        _name = new ChatComponentText(name.trim()).setChatStyle(nameStyle);
        _fullName = _name.createCopy();
    }

    public IChatComponent getTag() {
        return _tag;
    }

    public IChatComponent getName() {
        return _name;
    }

    public IChatComponent getFullName() {
        return _fullName;
    }
}
