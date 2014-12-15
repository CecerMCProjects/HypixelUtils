package com.cecer1.hypixelutils.chatprocessors;

import com.cecer1.hypixelutils.AbstractedSrgMethods;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.world.WorldEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class DebugChatProcessor extends BaseChatProcessor
{
    public DebugChatProcessor()
    {
        super();
    }

    public DebugChatProcessor(Property configProperty, boolean enabledByDefault)
    {
        super(configProperty, enabledByDefault);
    }

    @Override
    public void onChat(ClientChatReceivedEvent event)
    {
        System.out.println(AbstractedSrgMethods.convertChatComponentToJson(event.message));
    }
}
