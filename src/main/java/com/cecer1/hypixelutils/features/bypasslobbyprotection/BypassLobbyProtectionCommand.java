package com.cecer1.hypixelutils.features.bypasslobbyprotection;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class BypassLobbyProtectionCommand extends AbstractedCommand {

    public BypassLobbyProtectionCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(HypixelUtilsCore.config.isBypassLobbyProtectionEnabled())
        {
            HypixelUtilsCore.config.setBypassLobbyProtectionEnabled(false);

            // ChatOutput disabled because the GUI does it for us.
            // ChatOutputs.printBypassLobbyProtectionEnabledStatus(false);
        }
        else
        {
            HypixelUtilsCore.config.setBypassLobbyProtectionEnabled(true);

            // ChatOutput disabled because the GUI does it for us.
            // ChatOutputs.printBypassLobbyProtectionEnabledStatus(true);
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
