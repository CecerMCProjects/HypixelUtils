package com.cecer1.hypixelutils.gui.frames;

import com.cecer1.hypixelutils.gui.HypixelUtilsGuiFrame;
import org.darkstorm.minecraft.gui.component.basic.BasicLabel;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;

import java.awt.*;

public class HypixelUtilsLicenseFrame extends HypixelUtilsGuiFrame {

    public HypixelUtilsLicenseFrame() {
        super("LICENSE for HypixelUtils");
    }

    @Override
    public void init() {
        this.setTheme(new SimpleTheme());

        this.add(new BasicLabel("The following is the license HypixelUtils was released under."));
        this.add(new BasicLabel(" --- --- --- --- --- --- "));
        this.add(new BasicLabel(""));
        this.add(new BasicLabel("The MIT License (MIT)"));
        this.add(new BasicLabel(""));
        this.add(new BasicLabel("Copyright (c) 2015 Cecer (cecer1.com)"));
        this.add(new BasicLabel(""));
        this.add(new BasicLabel("Permission is hereby granted, free of charge, to any person obtaining a copy"));
        this.add(new BasicLabel("of this software and associated documentation files (the \"Software\"), to deal"));
        this.add(new BasicLabel("in the Software without restriction, including without limitation the rights"));
        this.add(new BasicLabel("to use, copy, modify, merge, publish, distribute, sublicense, and/or sell"));
        this.add(new BasicLabel("copies of the Software, and to permit persons to whom the Software is"));
        this.add(new BasicLabel("furnished to do so, subject to the following conditions:"));
        this.add(new BasicLabel(""));
        this.add(new BasicLabel("The above copyright notice and this permission notice shall be included in all"));
        this.add(new BasicLabel("copies or substantial portions of the Software."));
        this.add(new BasicLabel(""));
        this.add(new BasicLabel("THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR"));
        this.add(new BasicLabel("IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,"));
        this.add(new BasicLabel("FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE"));
        this.add(new BasicLabel("AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER"));
        this.add(new BasicLabel("LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,"));
        this.add(new BasicLabel("OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE"));
        this.add(new BasicLabel("SOFTWARE."));

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

    private static HypixelUtilsLicenseFrame _instance;
    public static void makeVisible() {
        _instance.setVisible(true);
    }
}
