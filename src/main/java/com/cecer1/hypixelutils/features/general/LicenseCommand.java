package com.cecer1.hypixelutils.features.general;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.gui.frames.GuiLicenseFrame;
import com.cecer1.hypixelutils.gui.frames.HypixelUtilsLicenseFrame;
import com.cecer1.hypixelutils.gui.frames.UnirestLicenseFrame;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class LicenseCommand extends AbstractedCommand {

    public LicenseCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        GuiLicenseFrame.makeVisible();
        HypixelUtilsLicenseFrame.makeVisible();
        UnirestLicenseFrame.makeVisible();
        HypixelUtilsCore.userInterface.toggleScreenDelayed(true);
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
