package com.cecer1.hypixelutils.features.cloudconfig;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.clientcommands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class ConfigGuiCommand extends AbstractedCommand {

    public ConfigGuiCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        HypixelUtilsCore.userInterface.configWindow.setVisible(true);
        HypixelUtilsCore.userInterface.setVisibleDelayed(true);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        return HypixelUtilsCore.currentState.isConnected();
    }

    @Override
    public int getMaximumArgumentCount() {
        return 0;
    }
}
