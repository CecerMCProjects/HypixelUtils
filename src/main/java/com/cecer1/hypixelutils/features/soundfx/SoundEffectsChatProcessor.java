package com.cecer1.hypixelutils.features.soundfx;

import com.cecer1.modframework.common.events.IOnChatEventHandler;
import net.minecraft.util.EnumChatFormatting;

import java.util.regex.Pattern;

public class SoundEffectsChatProcessor implements IOnChatEventHandler {

    private static Pattern _expGainPattern = Pattern.compile("^\\+\\d+ experience");
    private static Pattern _karmaGainPattern = Pattern.compile("^\\+\\d+ Karma!$");
    private static Pattern _coinGainPattern = Pattern.compile("^\\+\\d+ coins");
    @Override
    public void onChat(IOnChatEventData event) {
        if(event.getMessage().getChatStyle().getColor() == EnumChatFormatting.BLUE) {
            if(_expGainPattern.matcher(event.getMessage().getUnformattedText()).matches()) {
                SoundPlayer.PlayExpGainSound();
            }
        } else if(event.getMessage().getChatStyle().getColor() == EnumChatFormatting.LIGHT_PURPLE) {
            if(_karmaGainPattern.matcher(event.getMessage().getUnformattedText()).matches()) {
                SoundPlayer.PlayKarmaGainSound();
            }
        } else if(event.getMessage().getChatStyle().getColor() == EnumChatFormatting.GOLD) {
            if(_coinGainPattern.matcher(event.getMessage().getUnformattedText()).matches()) {
                SoundPlayer.PlayCoinGainSound();
            }
        }
    }
}
