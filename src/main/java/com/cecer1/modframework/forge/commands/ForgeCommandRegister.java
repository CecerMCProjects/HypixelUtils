package com.cecer1.modframework.forge.commands;

import com.cecer1.modframework.common.commands.ICommandRegister;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;

public class ForgeCommandRegister implements ICommandRegister {

    @Override
    public ICommandRegister registerCommand(ICommand command) {
        ClientCommandHandler.instance.registerCommand(command);
        return this;
    }
}
