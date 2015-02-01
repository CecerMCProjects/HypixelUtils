package com.cecer1.hypixelutils.features.debug;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.clientcommands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class DebugCommand extends AbstractedCommand {

    public DebugCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(HypixelUtilsCore.configHelper.debugModeEnabled.getValue(false))
        {
            HypixelUtilsCore.configHelper.debugModeEnabled.setValue(false);
        }
        else
        {
            HypixelUtilsCore.configHelper.debugModeEnabled.setValue(true);
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
