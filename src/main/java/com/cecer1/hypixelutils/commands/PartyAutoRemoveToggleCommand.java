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

public class PartyAutoRemoveToggleCommand extends AbstractedCommand {

    public PartyAutoRemoveToggleCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException
    {
        IChatComponent commandReply = UtilityMethods.getHypixelUtilsChatComponentPrefix()
                .appendSibling(new ChatComponentText("Offline party members will ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));

        if(HypixelUtilsCore.config.isPartyAutoRemoveOfflineEnabled())
        {
            HypixelUtilsCore.config.setPartyAutoRemoveOfflineEnabled(false);
            commandReply.appendSibling(new ChatComponentText("now").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
        }
        else
        {
            HypixelUtilsCore.config.setPartyAutoRemoveOfflineEnabled(true);
            commandReply.appendSibling(new ChatComponentText("not").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        }
        commandReply.appendSibling(new ChatComponentText(" be automatically removed if they stop the party from joining a game.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));

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
