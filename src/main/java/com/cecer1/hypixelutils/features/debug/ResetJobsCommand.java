package com.cecer1.hypixelutils.features.debug;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class ResetJobsCommand extends AbstractedCommand {

    public ResetJobsCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        HypixelUtilsCore.commandJobManager.reset();
        ChatOutputs.printDebugJobsQueueReset();
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
