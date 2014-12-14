package com.cecer1.hypixelutils;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;


public class HypixelUtilsConfigGui extends GuiConfig {

    public HypixelUtilsConfigGui(GuiScreen parent) {
        super(parent,
            new ConfigElement(HypixelUtils.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
            HypixelUtils.MODID,
            false,
            false,
            GuiConfig.getAbridgedConfigPath(HypixelUtils.config.toString()));
    }
}
