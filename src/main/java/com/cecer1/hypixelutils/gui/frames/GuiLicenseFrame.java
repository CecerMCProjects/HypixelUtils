package com.cecer1.hypixelutils.gui.frames;

import com.cecer1.hypixelutils.gui.HypixelUtilsGuiFrame;
import org.darkstorm.minecraft.gui.component.basic.BasicLabel;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;

import java.awt.*;

public class GuiLicenseFrame extends HypixelUtilsGuiFrame {

    public GuiLicenseFrame() {
        super("LICENSE for Minecraft GUI API");
    }

    @Override
    public void init() {
        this.setTheme(new SimpleTheme());
        
        this.add(new BasicLabel("HypixelUtils uses the Minecraft GUI API by DarkStorm652."));
        this.add(new BasicLabel("The following is the license it was released under."));
        this.add(new BasicLabel(" --- --- --- --- --- --- "));
        this.add(new BasicLabel(""));
        this.add(new BasicLabel("Copyright (c) 2013, DarkStorm (darkstorm@evilminecraft.net)"));
        this.add(new BasicLabel("All rights reserved."));
        this.add(new BasicLabel(""));
        this.add(new BasicLabel("Redistribution and use in source and binary forms, with or without"));
        this.add(new BasicLabel("modification, are permitted provided that the following conditions are met:"));
        this.add(new BasicLabel(""));
        this.add(new BasicLabel("1. Redistributions of source code must retain the above copyright notice, this"));
        this.add(new BasicLabel("   list of conditions and the following disclaimer."));
        this.add(new BasicLabel("2. Redistributions in binary form must reproduce the above copyright notice,"));
        this.add(new BasicLabel("   this list of conditions and the following disclaimer in the documentation"));
        this.add(new BasicLabel("   and/or other materials provided with the distribution."));
        this.add(new BasicLabel(""));
        this.add(new BasicLabel("THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND"));
        this.add(new BasicLabel("ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED"));
        this.add(new BasicLabel("WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE"));
        this.add(new BasicLabel("DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR"));
        this.add(new BasicLabel("ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES"));
        this.add(new BasicLabel("(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;"));
        this.add(new BasicLabel("LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND"));
        this.add(new BasicLabel("ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT"));
        this.add(new BasicLabel("(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS"));
        this.add(new BasicLabel("SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE."));

        this.setX(200);
        this.setY(50);
        Dimension defaultDimension = getTheme().getUIForComponent(this).getDefaultSize(this);
        this.setWidth(defaultDimension.width);
        this.setHeight(defaultDimension.height);
        this.layoutChildren();
        this.setVisible(false);
        this.setMinimized(false);

        this.setPinned(false);
        this.setPinnable(false);
        
        _instance = this;
    }
    
    private static GuiLicenseFrame _instance;
    public static void makeVisible() {
        _instance.setVisible(true);
    }
}
