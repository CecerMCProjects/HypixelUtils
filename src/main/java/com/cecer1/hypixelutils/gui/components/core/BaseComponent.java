package com.cecer1.hypixelutils.gui.components.core;

import com.cecer1.hypixelutils.HypixelUtilsCore;

import java.util.List;

public abstract class BaseComponent extends ComponentParent {
    protected int _offsetX;
    protected int _offsetY;

    @Override
    public int getAbsoluteX() {
        return getParent().getAbsoluteX() + _offsetX;
    }
    @Override
    public int getAbsoluteY() {
        return getParent().getAbsoluteY() + _offsetY;
    }
    @Override
    public int getRelativeX() {
        return _offsetX;
    }
    @Override
    public int getRelativeY() {
        return _offsetY;
    }
    
    public void setOffset(int x, int y) {
        boolean changed = x != _offsetX || y != _offsetY;
            
        _offsetX = x;
        _offsetY = y;
        
        if(changed)
            onOffsetChanged();
    }
    
    public List<String> hoverText;
    
    private ComponentParent _parent;
    private Canvas _canvas;
    protected int _zOrder = 0;
    
    private boolean _cursorLocked = false;
    private int _cursorOffsetX;
    private int _cursorOffsetY;
    public void setCursorLocked(boolean locked) {
        if(locked) {
            _cursorOffsetX = Integer.MIN_VALUE;
            _cursorOffsetY = Integer.MIN_VALUE;
        }
        _cursorLocked = locked;
    }
    public void setCursorLocked(boolean locked, int offsetX, int offsetY) {
        if(locked) {
            _cursorOffsetX = offsetX;
            _cursorOffsetY = offsetY;
        }
        _cursorLocked = locked;
    }
    public boolean isCursorLocked() {
        return _cursorLocked;
    }
    

    public BaseComponent(ComponentParent parent) {
        if(parent == null)
            throw new IllegalArgumentException("parent cannot be null.");

        _parent = parent;

        setClickThrough(true);

        ComponentParent canvas = getParent();
        while (canvas instanceof BaseComponent) {
            canvas = canvas.getParent();
        }
        _canvas = (Canvas)canvas;
        _canvas.registerComponent(this, parent);
    }

    public Canvas getCanvas() {
        return _canvas;
    }

    @Override
    public ComponentParent getParent() {
        return _parent;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean hasFocus) {
        _currentHoverStatus = false;
        
        if(_cursorLocked) {
            if(_cursorOffsetX == Integer.MIN_VALUE) {
                _cursorOffsetX = getAbsoluteX() - mouseX;
                _cursorOffsetY = getAbsoluteY() - mouseY;
            }
            
            setOffset(mouseX + _cursorOffsetX, mouseY + _cursorOffsetY);
        }

        for (BaseComponent component : getChildren()) {
            if (component.isRenderable(hasFocus))
                component.render(mouseX, mouseY, hasFocus);
        }
    }
    @Override
    public void postRender(int mouseX, int mouseY, boolean hasFocus) {
        updateHoverStatus(mouseX, mouseY, _currentHoverStatus);

        for (BaseComponent component : getChildren()) {
            if (component.isRenderable(hasFocus))
                component.postRender(mouseX, mouseY, hasFocus);
        }
    }
    public void updateHoverStatus(int mouseX, int mouseY, boolean newStatus) {
        if(_currentHoverStatus != _lastHoverStatus) {
            onHoverStatusChanged(mouseX, mouseY, _currentHoverStatus);
            _lastHoverStatus = _currentHoverStatus;
        }
    }
    
    public void remove() {
        for(BaseComponent child : this.getChildren()) {
            child.remove();
        }
        _children.clear();
        _canvas.deregisterComponent(this);
    }

    @Override
    public List<BaseComponent> getChildren() {
        return _children;
    }

    public void onMouseDown(int x, int y) {
        BaseComponent component = Canvas.getTopComponent(this, x, y, Canvas.RESTRICT_CLICKS_TO_PARENT_BOUNDS, true, true);
        if(component != null)
            component.onMouseDown(x, y);
    }

    public void onMouseUp(int x, int y) {
    }

    private boolean _currentHoverStatus = false;
    private boolean _lastHoverStatus = false;
    public void onHover(int x, int y) {
        if(this.hoverText != null) {
            HypixelUtilsCore.userInterface.drawHoverText(this.hoverText, x, y);
        }
        _currentHoverStatus = true;
    }
    public void onHoverStatusChanged(int x, int y, boolean newStatus) {
    }
    
    private boolean _clickThrough;
    public boolean isClickThrough() {
        return _clickThrough;
    }
    public void setClickThrough(boolean clickThrough) {
        _clickThrough = clickThrough;
    }



    public void onOffsetChanged() {
                
    }
}
