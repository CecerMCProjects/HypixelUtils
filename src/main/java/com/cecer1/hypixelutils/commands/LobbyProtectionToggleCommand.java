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

public class LobbyProtectionToggleCommand implements ICommand {
    private List<String> _aliases;

    public LobbyProtectionToggleCommand()
    {
        _aliases = new ArrayList<String>();
        _aliases.add("lobbyprotectiontoggle");
        _aliases.add("lobbylobbytoggle");
        _aliases.add("lobbyprotection");
        _aliases.add("lobbylobby");
    }

    @Override
    public String getCommandName()
    {
        return "hypixelutils:lobbyprotectiontoggle";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "<LOBBY_PROTECTION_TOGGLE_USAGE>";
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
                .appendSibling(new ChatComponentText("/lobby protection has been ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));

        if(HypixelUtils.instance.antiLobbyCommandProtectionProcessor.isEnabled())
        {
			HypixelUtils.instance.antiLobbyCommandProtectionProcessor.setEnabled(false);
            commandReply.appendSibling(new ChatComponentText("ENABLED").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
        }
        else
        {
			HypixelUtils.instance.antiLobbyCommandProtectionProcessor.setEnabled(true);
            commandReply.appendSibling(new ChatComponentText("DISABLED").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        }
        commandReply.appendSibling(new ChatComponentText(".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));

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
