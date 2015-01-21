package com.cecer1.hypixelutils.features.cloudconfig;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.config.IConfigManager;
import com.cecer1.hypixelutils.gui.GuiConfigManagerWrapper;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class ConfigGuiCommand extends AbstractedCommand {

    public ConfigGuiCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        IConfigManager configManager = HypixelUtilsCore.config;
        if(!(configManager instanceof GuiConfigManagerWrapper)) {
            ChatOutputs.printErrorConfigNotSupportGui();
            return;
        }
        
        GuiConfigManagerWrapper wrappedConfig = (GuiConfigManagerWrapper)configManager;
        wrappedConfig.getTogglesFrame().setVisible(true);
        HypixelUtilsCore.userInterface.toggleScreenDelayed(true);
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
