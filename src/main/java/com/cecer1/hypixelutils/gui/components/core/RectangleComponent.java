package com.cecer1.hypixelutils.gui.components.core;

import net.minecraft.client.gui.Gui;

public class RectangleComponent extends SizableComponent {
    private int _colour;

    public RectangleComponent(ComponentParent parent) {
        super(parent);
    }

    public void setColour(int argb) {
        _colour = argb;
    }
    public void setColour(int red, int green, int blue, int alpha) {
        int argb = (alpha << 24) | (red << 16) | (green << 8) | blue;
        setColour(argb);
    }
    public int getColour() {
        return _colour;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean hasFocus) {
        int left = getAbsoluteX();
        int top = getAbsoluteY();
        
        int right = left + getWidth();
        int bottom = top + getHeight();
        
        Gui.drawRect(left, top, right, bottom, getColour());
        
        super.render(mouseX, mouseY, hasFocus);
    }
}
