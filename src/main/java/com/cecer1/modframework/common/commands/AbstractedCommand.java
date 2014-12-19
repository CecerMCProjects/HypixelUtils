package com.cecer1.modframework.common.commands;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

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
}
