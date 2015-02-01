package com.cecer1.hypixelutils.features.cloudconfig;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.clientcommands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.UUID;

public class ConfigKeyCommand extends AbstractedCommand {

    public ConfigKeyCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
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

            HypixelUtilsCore.cloudConfigServerGateway.deleteConfig(null);
            HypixelUtilsCore.cloudConfigServerGateway.configKey = newConfigKey;
            ChatOutputs.printNewConfigKey(newConfigKey);
            HypixelUtilsCore.configHelper.forceSave();
            return;
        } else {
            ChatOutputs.printCurrentConfigKey(HypixelUtilsCore.cloudConfigServerGateway.configKey);
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
