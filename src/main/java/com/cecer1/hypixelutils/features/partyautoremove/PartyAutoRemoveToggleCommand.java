package com.cecer1.hypixelutils.features.partyautoremove;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.modframework.common.commands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class PartyAutoRemoveToggleCommand extends AbstractedCommand {

    public PartyAutoRemoveToggleCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException
    {
        if(HypixelUtilsCore.config.isPartyAutoRemoveOfflineEnabled())
        {
            HypixelUtilsCore.config.setPartyAutoRemoveOfflineEnabled(false);
            
            // ChatOutput disabled because the GUI does it for us.
            // ChatOutputs.printPartyAutoRemoveEnabledStatus(false);
        }
        else
        {
            HypixelUtilsCore.config.setPartyAutoRemoveOfflineEnabled(true);
            
            // ChatOutput disabled because the GUI does it for us.
            // ChatOutputs.printPartyAutoRemoveEnabledStatus(true);
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender)
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
