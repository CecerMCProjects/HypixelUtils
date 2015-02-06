package com.cecer1.hypixelutils.gui.components.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class ComponentParent {
    protected boolean _isVisible = false;
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
    
    public abstract boolean isVisible();
    public abstract void setVisible(boolean visible);
    
    public abstract int getAbsoluteX();
    public abstract int getAbsoluteY();
    
    public abstract int getRelativeX();
    public abstract int getRelativeY();
    
    public abstract int getWidth();
    public abstract int getHeight();

    public abstract void render(int mouseX, int mouseY);
    public abstract void postRender(int mouseX, int mouseY);
    
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
