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

public class LoadConfigCommand extends AbstractedCommand {

    public LoadConfigCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        IChatComponent commandReply = UtilityMethods.getHypixelUtilsChatComponentPrefix();
        if(HypixelUtilsCore.config instanceof CloudConfigManager) {
            commandReply.appendSibling(new ChatComponentText("Forcing config reload...").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
            ((CloudConfigManager)HypixelUtilsCore.config).load();
        } else {
            commandReply.appendSibling(new ChatComponentText("ERROR: Config backend does not support forced reloading!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        }
        Minecraft.getMinecraft().thePlayer.addChatMessage(commandReply);
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
        return 0;
    }
}
