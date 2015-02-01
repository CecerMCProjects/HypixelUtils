package com.cecer1.hypixelutils.features.improvedlobby;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.clientcommands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class ImprovedLobbyCommand extends AbstractedCommand {

    public ImprovedLobbyCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(args.length == 0 || args[0] == null)
        {
            HypixelUtilsCore.sendChatMessage("/lobby");
            return;
        }

        if(args.length >= 2)
        {
            try {
                HypixelUtilsCore.improvedLobbyCommandProcessor.setDesiredLobbyNumber(Integer.parseInt(args[1]));
            } catch (NumberFormatException e) {
                HypixelUtilsCore.improvedLobbyCommandProcessor.setDesiredLobbyNumber(0);
            } // Swallow
        }
        HypixelUtilsCore.sendChatMessage("/lobby " + args[0]);
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
        return 2;
    }
}
