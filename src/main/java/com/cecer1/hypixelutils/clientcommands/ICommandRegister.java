package com.cecer1.hypixelutils.clientcommands;

public interface ICommandRegister {
    public ICommandRegister registerCommand(ICecerCommand command);
    public boolean trigger(String message);
}
