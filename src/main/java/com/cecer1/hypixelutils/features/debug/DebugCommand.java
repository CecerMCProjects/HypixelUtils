package com.cecer1.hypixelutils.features.debug;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class DebugCommand extends AbstractedCommand {

    public DebugCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(HypixelUtilsCore.config.isDebugModeEnabled())
        {
            ChatOutputs.printDebugDebugModeEnabledStatus(false);
            HypixelUtilsCore.config.setDebugModeEnabled(false);
        }
        else
        {
            ChatOutputs.printDebugDebugModeEnabledStatus(true);
            HypixelUtilsCore.config.setDebugModeEnabled(true);
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
