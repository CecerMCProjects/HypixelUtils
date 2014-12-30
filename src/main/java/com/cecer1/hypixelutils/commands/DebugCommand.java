package com.cecer1.hypixelutils.commands;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.UtilityMethods;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class DebugCommand extends AbstractedCommand {

    public DebugCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        IChatComponent commandReply = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
                .appendSibling(new ChatComponentText("Debug mode ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));

        if(HypixelUtilsCore.config.isDebugModeEnabled())
        {
            commandReply.appendSibling(new ChatComponentText("DISABLED").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            HypixelUtilsCore.config.setDebugModeEnabled(false);
        }
        else
        {
            commandReply.appendSibling(new ChatComponentText("ENABLED").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
            HypixelUtilsCore.config.setDebugModeEnabled(true);
        }
        commandReply.appendSibling(new ChatComponentText(".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));

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
