package com.cecer1.hypixelutils.gui.components.core;

public class SizableComponent extends BaseComponent {
    private int _width;
    private int _height;

    public SizableComponent(ComponentParent parent) {
        super(parent);
    }

    @Override
    public int getHeight() {
        return _height;
    }
    @Override
    public int getWidth() {
        return _width;
    }
    public void setSize(int width, int height) {
        _width = width;
        _height = height;
    }
}
