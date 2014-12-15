package com.cecer1.hypixelutils.commands;

import com.cecer1.hypixelutils.HypixelUtils;
import com.cecer1.hypixelutils.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.List;

public class GuildChatToggleCommand implements ICommand {
    private List<String> _aliases;

    public GuildChatToggleCommand()
    {
        _aliases = new ArrayList<String>();
        _aliases.add("guildchattoggle");
        _aliases.add("gchattoggle");
        _aliases.add("gtoggle");
    }

    @Override
    public String getCommandName()
    {
        return "hypixelutils:guildchattoggle";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "<GUILD_CHAT_TOGGLE_USAGE>";
    }

    @Override
    public List getCommandAliases()
    {
        return _aliases;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException
    {
        IChatComponent commandReply = Utility.getHypixelUtilsChatComponentPrefix()
                .appendSibling(new ChatComponentText("Guild Chat is now ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));

        if(HypixelUtils.instance.filterGuildChatProcessor.isEnabled())
        {
			HypixelUtils.instance.filterGuildChatProcessor.setEnabled(false);
            commandReply.appendSibling(new ChatComponentText("SHOWN").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
        }
        else
        {
			HypixelUtils.instance.filterGuildChatProcessor.setEnabled(true);
            commandReply.appendSibling(new ChatComponentText("HIDDEN").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        }
        commandReply.appendSibling(new ChatComponentText(".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));

        Minecraft.getMinecraft().thePlayer.addChatMessage(commandReply);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender)
    {
        if(!Utility.isCurrentServerHypixel())
            return false;
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] strings)
    {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int i)
    {
        return false;
    }

    @Override
    public int compareTo(Object o)
    {
        return 0;
    }
}
