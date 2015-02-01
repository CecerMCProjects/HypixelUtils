package com.cecer1.hypixelutils.features.bypasslobbyprotection;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.clientcommands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class BypassLobbyProtectionCommand extends AbstractedCommand {

    public BypassLobbyProtectionCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(HypixelUtilsCore.configHelper.bypassLobbyProtectionEnabled.getValue(false))
        {
            HypixelUtilsCore.configHelper.bypassLobbyProtectionEnabled.setValue(false);
        }
        else
        {
            HypixelUtilsCore.configHelper.bypassLobbyProtectionEnabled.setValue(true);
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
