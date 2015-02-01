package com.cecer1.hypixelutils.features.boosters;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.backgroundcommands.HypixelCommandJobThank;
import com.cecer1.hypixelutils.backgroundcommands.HypixelCommandJobTip;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.clientcommands.AbstractedCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class TipAndThankCommand extends AbstractedCommand {

    public TipAndThankCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(args.length == 0 || args[0] == null || args[0].length() == 0)
        {
            ChatOutputs.printErrorTipAndThankCommandUsage();
            return;
        }
        String playerName = args[0];
        
        if(playerName.equalsIgnoreCase("all")) {
            ChatOutputs.printErrorTipAndThankAllCommandDisabled();
            BoosterQueue latestQueue = HypixelUtilsCore.boosterQueueChatModifier.latestQueue;
            if(latestQueue != null) {
                String[] playerNames = latestQueue.getActiveBoosterPlayerName();
                for(String name : playerNames) {
                    queueTipAndThank(name);
                }
            }
        } else {
            queueTipAndThank(playerName);
        }
    }
    
    private void queueTipAndThank(String playerName) {
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
