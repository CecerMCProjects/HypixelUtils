package com.cecer1.hypixelutils.features.boosters;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.UtilityMethods;
import com.cecer1.hypixelutils.commands.HypixelCommandJobThank;
import com.cecer1.hypixelutils.commands.HypixelCommandJobTip;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class TipAndThankCommand extends AbstractedCommand {

    public TipAndThankCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(args.length == 0 || args[0] == null || args[0].length() == 0)
        {
            IChatComponent commandReply = UtilityMethods.getHypixelUtilsChatComponentPrefix()
                    .appendSibling(new ChatComponentText("Usage: /<command> <playername>").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_RED)));
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(commandReply);
            return;
        }
        String playerName = args[0];

        HypixelUtilsCore.commandJobManager.queueJob(new HypixelCommandJobTip(playerName));
        HypixelUtilsCore.commandJobManager.queueJob(new HypixelCommandJobThank(playerName));
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
