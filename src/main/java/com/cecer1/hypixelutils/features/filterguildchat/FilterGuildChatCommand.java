package com.cecer1.hypixelutils.features.filterguildchat;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.clientcommands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class FilterGuildChatCommand extends AbstractedCommand {

    public FilterGuildChatCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(HypixelUtilsCore.configHelper.filterGuildChatEnabled.getValue(false))
        {
            HypixelUtilsCore.configHelper.filterGuildChatEnabled.setValue(false);
        }
        else
        {
            HypixelUtilsCore.configHelper.filterGuildChatEnabled.setValue(true);
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        if(!HypixelUtilsCore.currentState.isConnected())
            return false;
        return true;
    }

    @Override
    public int getMaximumArgumentCount() {
        return 0;
    }
}
