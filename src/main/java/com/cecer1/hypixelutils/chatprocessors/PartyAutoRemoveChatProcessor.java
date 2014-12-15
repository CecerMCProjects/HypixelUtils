package com.cecer1.hypixelutils.chatprocessors;

import com.cecer1.hypixelutils.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.config.Property;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartyAutoRemoveChatProcessor extends BaseChatProcessor {

    public PartyAutoRemoveChatProcessor()
    {
        super();
    }

    public PartyAutoRemoveChatProcessor(Property configProperty, boolean enabledByDefault)
    {
        super(configProperty, enabledByDefault);
    }

    private static Pattern _errorMessagePattern = Pattern.compile("^Your party can't queue for (.*): (.*) because (?:|\\[.+\\] )(\\S+) isn't online\\!$");

    @Override
    public void onChat(ClientChatReceivedEvent event) {
        IChatComponent rootComponent = Utility.getRootChatComponent(event.message);

        Matcher matcher = _errorMessagePattern.matcher(event.message.getUnformattedText());
        if(matcher.matches())
        {
            event.setCanceled(true);
            String offlinePlayerName = matcher.group(3);

            Minecraft.getMinecraft().thePlayer.sendChatMessage("/party remove " + offlinePlayerName);
            Minecraft.getMinecraft().thePlayer.addChatMessage(Utility.getHypixelUtilsChatComponentPrefix().appendSibling(new ChatComponentText(offlinePlayerName + " has been automatically removed from the party because they were offline!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW))));
        }
    }
}
