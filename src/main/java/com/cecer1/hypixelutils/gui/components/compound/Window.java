package com.cecer1.hypixelutils.gui.components.compound;

import com.cecer1.hypixelutils.gui.CanRenderValue;
import com.cecer1.hypixelutils.gui.components.core.*;

public class Window extends SizableComponent {
    private RectangleComponent _titleRect;
    private TextLabelComponent _titleText;
    private RectangleComponent _bodyRect;
    private RectangleComponent _closeRect;

    public Window(ComponentParent parent) {
        super(parent);
        setCanRenderUnfocused(CanRenderValue.FALSE);
        
        final Window window = this;


        this.setClickThrough(false);
        
        _titleRect = new RectangleComponent(this){
            @Override
            public void onMouseDown(int x, int y) {
                window.setCursorLocked(true);
            }
            @Override
            public void onMouseUp(int x, int y) {
                window.setCursorLocked(false);
            }
        };        
        _titleRect.setColour(0x20, 0x20, 0x20, 0xB0);
        _titleRect.setOffset(0, 0);
        _titleRect.setVisible(true);
        
        _titleText = new TextLabelComponent(this);
        _titleText.setColour(0xFF, 0xFF, 0xFF, 0xFF);
        _titleText.setOffset(0, 0);
        _titleText.setVisible(true);
        _titleText.setHorizontalAlignment(HorizontalTextAlignment.CENTRE);
        _titleText.setVerticalAlignment(VerticalTextAlignment.CENTRE);
        _titleText.setOverflowMode(TextOverflowMode.TRIM);
        
        _bodyRect = new RectangleComponent(this);
        _bodyRect.setColour(0x80, 0x80, 0x80, 0xB0);
        _bodyRect.setOffset(0, 11);
        _bodyRect.setVisible(true);
        
        final Window w = this;
        _closeRect = new RectangleComponent(this) {
            @Override
            public void onMouseUp(int x, int y) {
                w.setVisible(false);
                super.onMouseUp(x, y);
            }

            @Override
            public void onHoverStatusChanged(int x, int y, boolean newStatus) {
                super.onHoverStatusChanged(x, y, newStatus);

                if(newStatus) {
                    this.setColour(0xFF, 0x80, 0x80, 0xFF);
                } else {
                    this.setColour(0xB0, 0xB0, 0xB0, 0xFF);
                }
            }
        };
        _closeRect.setColour(0xE0, 0xE0, 0xE0, 0xFF);
        _closeRect.setSize(9, 9);
        _closeRect.setVisible(true);
        _closeRect.setCanRenderUnfocused(CanRenderValue.FALSE);
    }

    @Override
    public void onMouseDown(int x, int y) {
        getParent().bringToFront(this);
        super.onMouseDown(x, y);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean hasFocus) {
        ComponentParent parent = getParent();
        
        int newX = getRelativeX();
        int newY = getRelativeY();

        boolean needsMoving = false;
        if(getAbsoluteX() < parent.getAbsoluteX()) {
            newX = 0;
            needsMoving = true;
        } else if(getAbsoluteX() + getWidth() > parent.getAbsoluteX() + parent.getWidth()) {
            newX = Math.max(0, parent.getWidth() - getWidth());
            needsMoving = true;
        }
        if(getAbsoluteY() < getParent().getAbsoluteY()) {
            newY = 0;
            needsMoving = true;
        } else if(getAbsoluteY() + getHeight() > parent.getAbsoluteY() + parent.getHeight()) {
            newY = Math.max(0, parent.getHeight() - getHeight());
            needsMoving = true;
        }
        
        if(needsMoving) {
            setOffset(newX, newY);
        }

        super.render(mouseX, mouseY, hasFocus);
    }

    public String getTitle() {
        return _titleText.getText();
    }
    public void setTitle(String title) {
        _titleText.setText(title);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        _titleRect.setSize(width, 11);
        _titleText.setSize(width, 11);
        _bodyRect.setSize(width, Math.max(0, height - 11));
        
        _closeRect.setOffset(width - 10, 1);
    }

    @Override
    public void setVisible(boolean visible) {
        if(!visible)
            setCursorLocked(false);
        super.setVisible(visible);
    }
}
