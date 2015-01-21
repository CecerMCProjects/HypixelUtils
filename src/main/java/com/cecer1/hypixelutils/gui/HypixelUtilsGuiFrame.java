package com.cecer1.hypixelutils.gui;

import org.darkstorm.minecraft.gui.component.basic.BasicFrame;

public abstract class HypixelUtilsGuiFrame extends BasicFrame {

    public HypixelUtilsGuiFrame() {
        super();
    }

    public HypixelUtilsGuiFrame(String title) {
        super(title);
    }
    
    public abstract void init();
}