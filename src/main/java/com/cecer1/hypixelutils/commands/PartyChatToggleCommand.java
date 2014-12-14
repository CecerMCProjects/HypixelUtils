package com.cecer1.hypixelutils.commands;

import com.cecer1.hypixelutils.HypixelUtils;
import com.cecer1.hypixelutils.UtilityMethods;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.List;

public class PartyChatToggleCommand implements ICommand {
    private List<String> _aliases;

    public PartyChatToggleCommand()
    {
        _aliases = new ArrayList<String>();
        _aliases.add("partychattoggle");
        _aliases.add("pchattoggle");
        _aliases.add("ptoggle");
    }

    @Override
    public String getCommandName()
    {
        return "hypixelutils:partychattoggle";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "<PARTY_CHAT_TOGGLE_USAGE>";
    }

    @Override
    public List getCommandAliases()
    {
        return _aliases;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException
    {
        IChatComponent commandReply = UtilityMethods.getHypixelUtilsChatComponentPrefix()
                .appendSibling(new ChatComponentText("Party Chat is now ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));

        if(HypixelUtils.instance.filterPartyChatProcessor.isEnabled())
        {
			HypixelUtils.instance.filterPartyChatProcessor.setEnabled(false);
            commandReply.appendSibling(new ChatComponentText("SHOWN").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
        }
        else
        {
			HypixelUtils.instance.filterPartyChatProcessor.setEnabled(true);
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
