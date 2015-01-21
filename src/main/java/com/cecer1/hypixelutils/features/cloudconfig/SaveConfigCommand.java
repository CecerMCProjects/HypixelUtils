package com.cecer1.hypixelutils.features.cloudconfig;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.UtilityMethods;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.gui.GuiConfigManagerWrapper;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import com.cecer1.modframework.common.utils.ChatUtilities;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.IChatComponent;

public class SaveConfigCommand extends AbstractedCommand {

    public SaveConfigCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        IChatComponent commandReply = UtilityMethods.getHypixelUtilsChatComponentPrefix();
        
        CloudConfigManager typedConfig;
        if(!(HypixelUtilsCore.config instanceof CloudConfigManager)) {
            if(!(HypixelUtilsCore.config instanceof GuiConfigManagerWrapper)) {
                if(((GuiConfigManagerWrapper)HypixelUtilsCore.config).getBackingConfig() instanceof CloudConfigManager) {
                    typedConfig = (CloudConfigManager) ((GuiConfigManagerWrapper) HypixelUtilsCore.config).getBackingConfig();
                } else {
                    typedConfig = null;
                }
            } else {
                typedConfig = null;
            }
        } else {
            typedConfig = (CloudConfigManager) HypixelUtilsCore.config;
        }
        
        if(typedConfig != null) {
            ChatOutputs.printForcingConfigSave();
            typedConfig.save();
        } else {
            ChatOutputs.printErrorConfigNotSupportForcedSave();
        }
        ChatUtilities.printChatComponent(commandReply);
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
