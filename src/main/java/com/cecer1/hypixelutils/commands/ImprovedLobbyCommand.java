package com.cecer1.hypixelutils.commands;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.UtilityMethods;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImprovedLobbyCommand extends AbstractedCommand {

    public ImprovedLobbyCommand(String commandName) {
        super(commandName);
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
            HypixelUtilsCore.improvedLobbyCommandProcessor.setDesiredLobbyNumber(Integer.parseInt(strings[1]));
        }
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/lobby " + strings[0]);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender)
    {
        if(!UtilityMethods.isCurrentServerHypixel())
            return false;
        return true;
    }

    private static final List<String> _lobbyTypes = Arrays.asList("arcade", "arena", "blitz", "cops", "main", "megawalls", "paintball", "quake", "tnt", "vampirez", "walls");

    @Override
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] strings, BlockPos pos)
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
}
