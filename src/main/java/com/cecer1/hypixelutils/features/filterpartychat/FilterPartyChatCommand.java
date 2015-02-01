package com.cecer1.hypixelutils.features.filterpartychat;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.clientcommands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class FilterPartyChatCommand extends AbstractedCommand {

    public FilterPartyChatCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(HypixelUtilsCore.configHelper.filterPartyChatEnabled.getValue(false))
        {
            HypixelUtilsCore.configHelper.filterPartyChatEnabled.setValue(false);
        }
        else
        {
            HypixelUtilsCore.configHelper.filterPartyChatEnabled.setValue(true);
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
