package com.cecer1.hypixelutils.gui.windows;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.gui.components.compound.Window;
import com.cecer1.hypixelutils.gui.components.core.ComponentParent;
import com.cecer1.hypixelutils.gui.components.core.TextLabelComponent;

public class MitLicenseWindow extends Window {
    private TextLabelComponent _line1;
    private TextLabelComponent _line2;
    
    private TextLabelComponent[] _lines;
    
    public MitLicenseWindow(ComponentParent parent, String line1, String line2, int year, String name) {
        super(parent);
        _lines = new TextLabelComponent[23];
        
        int windowWidth = 430;

        this.setSize(windowWidth, 244);

        _line1 = new TextLabelComponent(this);
        _line1.setText(line1);
        _line1.setColour(0xFF, 0xFF, 0xFF, 0xFF);
        _line1.setOffset(3, 15);
        _line1.setVisible(true);

        _line2 = new TextLabelComponent(this);
        _line2.setText(line2);
        _line2.setColour(0xFF, 0xFF, 0xFF, 0xFF);
        _line2.setOffset(3, TextLabelComponent.getFontHeight() + 15);
        _line2.setVisible(true);

        String[] lines = new String[] {
                "--- --- --- --- --- --- ",
                "",
                "The MIT License (MIT)",
                "",
                "Copyright (c) " + year + " " + name,
                "",
                "Permission is hereby granted, free of charge, to any person obtaining a copy",
                "of this software and associated documentation files (the \"Software\"), to deal",
                "in the Software without restriction, including without limitation the rights",
                "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell",
                "copies of the Software, and to permit persons to whom the Software is",
                "furnished to do so, subject to the following conditions:",
                "",
                "The above copyright notice and this permission notice shall be included in all",
                "copies or substantial portions of the Software.",
                "",
                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR",
                "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,",
                "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE",
                "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER",
                "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,",
                "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE",
                "SOFTWARE."
        };
        
        for(int i = 0; i < 23; i++) {
            _lines[i] = new TextLabelComponent(this);
            _lines[i].setText(lines[i]);
            _lines[i].setColour(0xFF, 0xFF, 0xFF, 0xFF);
            _lines[i].setOffset(3, (TextLabelComponent.getFontHeight() * (i+2)) + 15);
            _lines[i].setVisible(true);
        }
        
        final MitLicenseWindow window = this;
    }

    @Override
    public void onOffsetChanged() {
        HypixelUtilsCore.configHelper.configWindowPositionX.setValueNoSave(_offsetX);
        HypixelUtilsCore.configHelper.configWindowPositionY.setValueNoSave(_offsetY);
    }
}
