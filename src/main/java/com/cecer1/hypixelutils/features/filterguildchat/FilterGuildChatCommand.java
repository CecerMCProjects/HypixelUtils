package com.cecer1.hypixelutils.features.filterguildchat;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class FilterGuildChatCommand extends AbstractedCommand {

    public FilterGuildChatCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(HypixelUtilsCore.config.isFilterGuildChatEnabled())
        {
            HypixelUtilsCore.config.setFilterGuildChatEnabled(false);

            // ChatOutput disabled because the GUI does it for us.
            // ChatOutputs.printFilterGuildChatEnabledStatus(false);
        }
        else
        {
            HypixelUtilsCore.config.setFilterGuildChatEnabled(true);

            // ChatOutput disabled because the GUI does it for us.
            // ChatOutputs.printFilterGuildChatEnabledStatus(true);
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
