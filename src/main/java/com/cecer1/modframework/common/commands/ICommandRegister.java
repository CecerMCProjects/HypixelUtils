package com.cecer1.modframework.common.commands;

import com.cecer1.modframework.liteloader.commands.ICecerCommand;

public interface ICommandRegister {
    public ICommandRegister registerCommand(ICecerCommand command);
}
