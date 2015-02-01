package com.cecer1.hypixelutils.features.instantbed;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.clientcommands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class InstantBedCommand extends AbstractedCommand {

    public InstantBedCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException
    {
        if(HypixelUtilsCore.configHelper.instantBedEnabled.getValue(false))
        {
			HypixelUtilsCore.configHelper.instantBedEnabled.setValue(false);
        }
        else
        {
            HypixelUtilsCore.configHelper.instantBedEnabled.setValue(true);
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender)
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
