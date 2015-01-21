package com.cecer1.hypixelutils.features.instantbed;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class InstantBedCommand extends AbstractedCommand {

    public InstantBedCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException
    {
        if(HypixelUtilsCore.config.isInstantBedEnabled())
        {
			HypixelUtilsCore.config.setInstantBedEnabled(false);

            // ChatOutput disabled because the GUI does it for us.
            // ChatOutputs.printInstantBedEnabledStatus(false);
        }
        else
        {
            HypixelUtilsCore.config.setInstantBedEnabled(true);

            // ChatOutput disabled because the GUI does it for us.
            // ChatOutputs.printInstantBedEnabledStatus(true);
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
