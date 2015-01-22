package com.cecer1.hypixelutils.features.soundfx;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import net.minecraft.client.Minecraft;

public class SoundPlayer {
    private static Runnable _playKarmaGainSoundRunnable = new Runnable() {
        @Override
        public void run() {
            Minecraft.getMinecraft().thePlayer.playSound("random.orb", 1.0f, 2.0f);
        }
    };
    public static void PlayKarmaGainSound() {
        
        HypixelUtilsCore.scheduler.scheduleTask(_playExpGainSoundRunnable, 0);
        HypixelUtilsCore.scheduler.scheduleTask(_playExpGainSoundRunnable, 1);
        HypixelUtilsCore.scheduler.scheduleTask(_playExpGainSoundRunnable, 2);
    }
    
    private static Runnable _playExpGainSoundRunnable = new Runnable() {
        @Override
        public void run() {
            Minecraft.getMinecraft().thePlayer.playSound("mob.chicken.plop", 1.0f, 2.0f);
        }
    };
    public static void PlayExpGainSound() {
        
        HypixelUtilsCore.scheduler.scheduleTask(_playExpGainSoundRunnable, 0);
        HypixelUtilsCore.scheduler.scheduleTask(_playExpGainSoundRunnable, 1);
        HypixelUtilsCore.scheduler.scheduleTask(_playExpGainSoundRunnable, 2);
        HypixelUtilsCore.scheduler.scheduleTask(_playExpGainSoundRunnable, 3);
    }

    private static Runnable _playCoinGainSoundRunnable = new Runnable() {
        @Override
        public void run() {
            Minecraft.getMinecraft().thePlayer.playSound("random.orb", 1.0f, 1.0f);
        }
    };
    public static void PlayCoinGainSound() {

        HypixelUtilsCore.scheduler.scheduleTask(_playCoinGainSoundRunnable, 0);
        HypixelUtilsCore.scheduler.scheduleTask(_playCoinGainSoundRunnable, 2);
        HypixelUtilsCore.scheduler.scheduleTask(_playCoinGainSoundRunnable, 3);
        HypixelUtilsCore.scheduler.scheduleTask(_playCoinGainSoundRunnable, 5);
    }
}
