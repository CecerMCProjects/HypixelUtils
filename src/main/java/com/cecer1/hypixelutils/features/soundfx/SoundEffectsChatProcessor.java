package com.cecer1.hypixelutils.features.soundfx;

import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import net.minecraft.util.EnumChatFormatting;

import java.util.regex.Pattern;

public class SoundEffectsChatProcessor implements IOnChatEventHandler {

    private static Pattern _expGainPattern = Pattern.compile("^\\+\\d+ experience");
    private static Pattern _karmaGainPattern = Pattern.compile("^\\+\\d+ Karma!$");
    private static Pattern _coinGainPattern = Pattern.compile("^\\+\\d+ coins");
    
    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData)data);
    }

    @Override
    public void onEvent(OnChatEventData data) {
        if(data.getMessage().getChatStyle().getColor() == EnumChatFormatting.BLUE) {
            if(_expGainPattern.matcher(data.getMessage().getUnformattedText()).matches()) {
                SoundPlayer.PlayExpGainSound();
            }
        } else if(data.getMessage().getChatStyle().getColor() == EnumChatFormatting.LIGHT_PURPLE) {
            if(_karmaGainPattern.matcher(data.getMessage().getUnformattedText()).matches()) {
                SoundPlayer.PlayKarmaGainSound();
            }
        } else if(data.getMessage().getChatStyle().getColor() == EnumChatFormatting.GOLD) {
            if(_coinGainPattern.matcher(data.getMessage().getUnformattedText()).matches()) {
                SoundPlayer.PlayCoinGainSound();
            }
        }
    }
}
