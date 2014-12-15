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
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImprovedLobbyCommand implements ICommand {
    private List<String> _aliases;

    private final String _commandName;

    public ImprovedLobbyCommand(String commandName)
    {
        _commandName = commandName; // Can't use aliases because it breaks tab complete
        _aliases = new ArrayList<String>();
        //_aliases.add("hub");
        //_aliases.add("leave");

        Collections.sort(_lobbyTypes);
    }

    @Override
    public String getCommandName()
    {
        //return "hypixelutils:lobby";
        return _commandName;
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "<IMPROVED_LOBBY_USAGE>";
    }

    @Override
    public List getCommandAliases()
    {
        return _aliases;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException
    {
        if(strings.length == 0)
        {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/lobby");
            return;
        }

        if(strings.length == 2)
        {
            HypixelUtils.instance.lobbyAutoSwapProcessor.setDesiredLobbyNumber(Integer.parseInt(strings[1]));
        }
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/lobby " + strings[0]);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender)
    {
        if(!Utility.isCurrentServerHypixel())
            return false;
        return true;
    }

    private static final List<String> _lobbyTypes = Arrays.asList(new String[] {"arcade","arena","blitz","cops","main","megawalls","paintball","quake","tnt","vampirez","walls"});

    @Override
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] strings)
    {
        List<String> results = new ArrayList<String>();
        if(strings.length == 1)
        {
            for(String lobbyType : _lobbyTypes)
            {
                if(lobbyType.startsWith(strings[0]))
                    results.add(lobbyType);
            }
        }
        if(results.isEmpty())
            return null;
        return results;
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
