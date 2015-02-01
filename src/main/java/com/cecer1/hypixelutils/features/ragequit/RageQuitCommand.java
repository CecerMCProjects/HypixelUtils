package com.cecer1.hypixelutils.features.ragequit;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.clientcommands.AbstractedCommand;
import com.cecer1.hypixelutils.utils.ChatUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class RageQuitCommand extends AbstractedCommand {

    public RageQuitCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        ChatUtilities.sendChat("*RAGEQUIT!*");
        HypixelUtilsCore.scheduler.scheduleTask(new Runnable() {
            @Override
            public void run() {
                Minecraft.getMinecraft().shutdown();
            }
        }, 5);
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
