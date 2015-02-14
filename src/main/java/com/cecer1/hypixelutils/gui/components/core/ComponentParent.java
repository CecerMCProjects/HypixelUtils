package com.cecer1.hypixelutils.gui.components.core;

import com.cecer1.hypixelutils.gui.CanRenderValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class ComponentParent {
    private boolean isVisible = false;
    protected CanRenderValue canRenderFocused = CanRenderValue.INHERIT;
    protected CanRenderValue canRenderUnfocused = CanRenderValue.INHERIT;
    protected List<BaseComponent> _children;
    protected int _maxZOrder = 0;
    private static Comparator<BaseComponent> _zComparator = new Comparator<BaseComponent>() {
        @Override
        public int compare(BaseComponent c1, BaseComponent c2) {
            if(c1._zOrder == c2._zOrder)
                return 0;
            if(c1._zOrder > c2._zOrder)
                return 1;
            return -1;
        }
    };
    
    public ComponentParent() {
        _children = new ArrayList<BaseComponent>();
    }


    public boolean aisVisible() {
        return isVisible;
    }
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    
    public boolean isRenderable(boolean hasFocus) {
        if(!isVisible)
            return false;
        
        if(hasFocus)
            return getCanRenderFocused();
        else 
            return getCanRenderUnfocused();
    }
    
    public boolean getCanRenderFocused() {
        if(canRenderFocused == CanRenderValue.TRUE)
            return true;
        if(canRenderFocused == CanRenderValue.FALSE)
            return true;
        return getParent().getCanRenderFocused();
    }
    public void setCanRenderFocused(CanRenderValue canRender) {
        canRenderFocused = canRender;
    }

    public boolean getCanRenderUnfocused() {
        if(canRenderUnfocused == CanRenderValue.TRUE)
            return true;
        if(canRenderUnfocused == CanRenderValue.FALSE)
            return false;
        return getParent().getCanRenderUnfocused();
    }
    public void setCanRenderUnfocused(CanRenderValue canRender) {
        canRenderUnfocused = canRender;
    }
    
    public abstract int getAbsoluteX();
    public abstract int getAbsoluteY();
    
    public abstract int getRelativeX();
    public abstract int getRelativeY();
    
    public abstract int getWidth();
    public abstract int getHeight();

    public abstract void render(int mouseX, int mouseY, boolean hasFocus);
    public abstract void postRender(int mouseX, int mouseY, boolean hasFocus);
    
    public abstract ComponentParent getParent();
    public List<BaseComponent> getChildren() {
        return _children;
    }

    public void sortChildren() {
        Collections.sort(_children, _zComparator);
    }

    public void compactZOrder() {
        sortChildren();
        int newZ = 0;
        for(BaseComponent component : _children) {
            component._zOrder = newZ;
            newZ++;
        }
        _maxZOrder = newZ;
    }

    public void setZOrder(BaseComponent component, int z) {
        if(!_children.contains(component))
            return;

        if(_maxZOrder < z)
            _maxZOrder = z;

        component._zOrder = z;
        compactZOrder();
    }

    public void bringToFront(BaseComponent component) {
        if(!_children.contains(component))
            return;

        setZOrder(component, _maxZOrder + 1);
    }
}
