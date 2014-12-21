package com.cecer1.modframework.common.commands;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.List;

public abstract class AbstractedCommand implements ICommand {

    private String _commandName;

    public AbstractedCommand(String commandName) {
        _commandName = commandName;
    }

    @Override
    public String getCommandName() {
        return _commandName;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public List getCommandAliases() {
        return null;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
