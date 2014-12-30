package com.cecer1.hypixelutils.commands;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.UtilityMethods;
import com.cecer1.hypixelutils.config.CloudConfigManager;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
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
        if(HypixelUtilsCore.config instanceof CloudConfigManager) {
            CloudConfigManager cloudConfig = (CloudConfigManager)HypixelUtilsCore.config;

            if(args.length > 0 && args[0] != null) {
                UUID newConfigKey;
                try {
                    if(args[0].equalsIgnoreCase("random")) {
                        newConfigKey = UUID.randomUUID();
                    } else {
                        newConfigKey = UUID.fromString(args[0]);
                    }
                } catch (IllegalArgumentException e) {
                    commandReply.appendSibling(new ChatComponentText("ERROR: Invalid config key! Must be a valid UUID!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(commandReply);
                    return;
                }

                cloudConfig.deleteRemoteConfig();
                cloudConfig.setConfigKey(newConfigKey);
                HypixelUtilsCore.setSavedConfigKey(newConfigKey);
                commandReply.appendSibling(new ChatComponentText("Your new config key is ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)))
                        .appendSibling(new ChatComponentText(newConfigKey.toString()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)))
                        .appendSibling(new ChatComponentText(".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
                Minecraft.getMinecraft().thePlayer.addChatMessage(commandReply);

                Minecraft.getMinecraft().thePlayer.addChatMessage(UtilityMethods.getHypixelUtilsChatComponentPrefix().appendSibling(new ChatComponentText("Forcing config save...").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW))));
                cloudConfig.save();
                return;
            } else {
                commandReply.appendSibling(new ChatComponentText("Your current config key is ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)))
                        .appendSibling(new ChatComponentText(HypixelUtilsCore.getSavedConfigKey().toString()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)))
                        .appendSibling(new ChatComponentText(".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
                Minecraft.getMinecraft().thePlayer.addChatMessage(commandReply);
                return;
            }
        } else {
            commandReply.appendSibling(new ChatComponentText("ERROR: Config backend is not using Cloud Config!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            Minecraft.getMinecraft().thePlayer.addChatMessage(commandReply);
            return;
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        if(!UtilityMethods.isHypixel())
            return false;
        return true;
    }

    @Override
    public int getMaximumArgumentCount() {
        return 1;
    }
}
