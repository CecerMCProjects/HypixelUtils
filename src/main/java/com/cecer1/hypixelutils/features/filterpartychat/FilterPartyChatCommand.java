package com.cecer1.hypixelutils.features.filterpartychat;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class FilterPartyChatCommand extends AbstractedCommand {

    public FilterPartyChatCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(HypixelUtilsCore.config.isFilterPartyChatEnabled())
        {
            HypixelUtilsCore.config.setFilterPartyChatEnabled(false);

            // ChatOutput disabled because the GUI does it for us.
            // ChatOutputs.printFilterPartyChatEnabledStatus(false);
        }
        else
        {
            HypixelUtilsCore.config.setFilterPartyChatEnabled(true);

            // ChatOutput disabled because the GUI does it for us.
            // ChatOutputs.printFilterPartyChatEnabledStatus(true);
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
