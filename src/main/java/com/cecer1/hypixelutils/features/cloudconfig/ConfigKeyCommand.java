package com.cecer1.hypixelutils.features.cloudconfig;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.UtilityMethods;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.gui.GuiConfigManagerWrapper;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.IChatComponent;

import java.util.UUID;

public class ConfigKeyCommand extends AbstractedCommand {

    public ConfigKeyCommand(String commandName) {
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
            if(args.length > 0 && args[0] != null) {
                UUID newConfigKey;
                try {
                    if(args[0].equalsIgnoreCase("random")) {
                        newConfigKey = UUID.randomUUID();
                    } else {
                        newConfigKey = UUID.fromString(args[0]);
                    }
                } catch (IllegalArgumentException e) {
                    ChatOutputs.printErrorConfigKeyNotUuid();
                    return;
                }

                typedConfig.deleteRemoteConfig();
                typedConfig.setConfigKey(newConfigKey);
                HypixelUtilsCore.setSavedConfigKey(newConfigKey);
                ChatOutputs.printNewConfigKey(newConfigKey);
                ChatOutputs.printForcingConfigSave();
                typedConfig.save();
                return;
            } else {
                ChatOutputs.printCurrentConfigKey(typedConfig.getConfigKey());
                return;
            }
        } else {
            ChatOutputs.printErrorConfigNotCloudConfig();
            return;
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
        return 1;
    }
}
