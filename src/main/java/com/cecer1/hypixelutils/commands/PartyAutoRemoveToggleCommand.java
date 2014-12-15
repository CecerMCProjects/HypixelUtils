package com.cecer1.hypixelutils.commands;

import com.cecer1.hypixelutils.HypixelUtils;
import com.cecer1.hypixelutils.Utility;
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

public class PartyAutoRemoveToggleCommand implements ICommand {
    private List<String> _aliases;

    public PartyAutoRemoveToggleCommand()
    {
        _aliases = new ArrayList<String>();
        _aliases.add("partyautoremovetoggle");
        _aliases.add("pautoremovetoggle");
        _aliases.add("ptoggle");
    }

    @Override
    public String getCommandName()
    {
        return "hypixelutils:partyautoremovetoggle";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "<PARTY_AUTO_REMOVE_TOGGLE_USAGE>";
    }

    @Override
    public List getCommandAliases()
    {
        return _aliases;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException
    {
        IChatComponent commandReply = Utility.getHypixelUtilsChatComponentPrefix()
                .appendSibling(new ChatComponentText("Offline party members will ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));

        if(HypixelUtils.instance.partyAutoRemoveChatProcessor.isEnabled())
        {
			HypixelUtils.instance.partyAutoRemoveChatProcessor.setEnabled(false);
            commandReply.appendSibling(new ChatComponentText("now").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
        }
        else
        {
			HypixelUtils.instance.partyAutoRemoveChatProcessor.setEnabled(true);
            commandReply.appendSibling(new ChatComponentText("NOT").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        }
        commandReply.appendSibling(new ChatComponentText(" be automatically removed if they stop the party from joining a game.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));

        Minecraft.getMinecraft().thePlayer.addChatMessage(commandReply);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender)
    {
        if(!Utility.isCurrentServerHypixel())
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
