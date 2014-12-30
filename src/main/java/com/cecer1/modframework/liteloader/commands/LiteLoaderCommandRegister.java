package com.cecer1.modframework.liteloader.commands;

import com.cecer1.modframework.common.commands.ICommandRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiteLoaderCommandRegister implements ICommandRegister {
    private Map<String, ICecerCommand> _commands;

    public LiteLoaderCommandRegister() {
        _commands = new HashMap<String, ICecerCommand>();
        configureRegex(0);
    }

    @Override
    public ICommandRegister registerCommand(ICecerCommand command) {
        _commands.put(command.getCommandName(), command);
        if(command.getMaximumArgumentCount() > _currentMaxSupportedArgs) {
            configureRegex(command.getMaximumArgumentCount());
        }
        return this;
    }

    private int _currentMaxSupportedArgs = 0;
    private Pattern _commandPattern;
    private void configureRegex(int maxSupportedCommandArgs) {
        StringBuilder regexBuilder = new StringBuilder("^\\/(\\S+?)");
        for(int i = 0; i < maxSupportedCommandArgs; i++) {
            regexBuilder.append("(?:\\s+(\\S+?)|)");
        }
        regexBuilder.append("(?:\\s*|\\s.*)$");
        _commandPattern = Pattern.compile(regexBuilder.toString());
        _currentMaxSupportedArgs = maxSupportedCommandArgs;
    }

    public boolean trigger(String message) {
        Matcher matcher = _commandPattern.matcher(message);
        if(!matcher.matches()) // If it is not a command like syntax.
            return true;

        String commandName = matcher.group(1);
        if(!_commands.containsKey(commandName)) // If it is not a registered command name.
            return true;

        int commandArgsCount = matcher.groupCount()-1;
        String[] commandArgs = new String[commandArgsCount];
        for(int i = 0; i < commandArgsCount; i++) {
            commandArgs[i] = matcher.group(i + 2);
        }

        ICommand command = _commands.get(commandName);
        try {
            command.processCommand(Minecraft.getMinecraft().thePlayer, commandArgs);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        return false; // Prevent the chat from being processed further.
    }
}
