package com.cecer1.hypixelutils.features.license;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.clientcommands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class LicenseCommand extends AbstractedCommand {

    public LicenseCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        HypixelUtilsCore.userInterface.unirestLicenseWindow.setVisible(true);
        HypixelUtilsCore.userInterface.hypixelUtilsLicenseWindow.setVisible(true);

        HypixelUtilsCore.userInterface.setVisibleDelayed(true);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        return true;
    }

    @Override
    public int getMaximumArgumentCount() {
        return 0;
    }
}
