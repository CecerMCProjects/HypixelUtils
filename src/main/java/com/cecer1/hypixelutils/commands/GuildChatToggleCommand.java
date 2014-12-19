package com.cecer1.hypixelutils.commands;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.UtilityMethods;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.List;

public class GuildChatToggleCommand extends AbstractedCommand {

    public GuildChatToggleCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException
    {
        IChatComponent commandReply = UtilityMethods.getHypixelUtilsChatComponentPrefix()
                .appendSibling(new ChatComponentText("Guild Chat is now ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));

        if(HypixelUtilsCore.config.isFilterGuildChatEnabled())
        {
            HypixelUtilsCore.config.setFilterGuildChatEnabled(false);
            commandReply.appendSibling(new ChatComponentText("SHOWN").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
        }
        else
        {
            HypixelUtilsCore.config.setFilterGuildChatEnabled(true);
            commandReply.appendSibling(new ChatComponentText("HIDDEN").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        }
        commandReply.appendSibling(new ChatComponentText(".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));

        Minecraft.getMinecraft().thePlayer.addChatMessage(commandReply);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender)
    {
        if(!UtilityMethods.isCurrentServerHypixel())
            return false;
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] strings)
    {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int i)
    {
        return false;
    }

    @Override
    public int compareTo(Object o)
    {
        return 0;
    }
}
