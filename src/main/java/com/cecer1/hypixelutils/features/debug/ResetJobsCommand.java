package com.cecer1.hypixelutils.features.debug;

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

public class ResetJobsCommand extends AbstractedCommand {

    public ResetJobsCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        HypixelUtilsCore.commandJobManager.reset();
        IChatComponent commandReply = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
                .appendSibling(new ChatComponentText("Job queue reset!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        Minecraft.getMinecraft().thePlayer.addChatMessage(commandReply);
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
