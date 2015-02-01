package com.cecer1.hypixelutils.gui.components.core;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Canvas extends ComponentParent {
    private int _offsetX;
    private int _offsetY;
    private int _width;
    private int _height;
    private Set<BaseComponent> _allComponents;
    
    public Canvas() {
        super();
        _allComponents = new HashSet<BaseComponent>();
    }

    @Override
    public boolean isVisible() {
        return _isVisible;
    }

    @Override
    public void setVisible(boolean visible) {
        _isVisible = visible;
    }

    @Override
    public int getAbsoluteX() {
        return _offsetX;
    }

    @Override
    public int getAbsoluteY() {
        return _offsetY;
    }

    @Override
    public int getRelativeX() {
        return _offsetX;
    }

    @Override
    public int getRelativeY() {
        return _offsetY;
    }

    @Override
    public int getWidth() {
        GuiScreen screen = Minecraft.getMinecraft().currentScreen;
        if(screen == null)
            return -1;
        return screen.width;
    }

    @Override
    public int getHeight() {
        GuiScreen screen = Minecraft.getMinecraft().currentScreen;
        if(screen == null)
            return -1;
        return screen.height;
    }

    @Override
    public List<BaseComponent> getChildren() {
        return _children;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if(isVisible()) {
            boolean anyRendered = false;
            for (BaseComponent component : getChildren()) {
                if(component.isVisible()) {
                    component.render(mouseX, mouseY);
                    anyRendered = true;
                }
            }
            if(!anyRendered)
                HypixelUtilsCore.userInterface.setVisible(false);
        }
    }
    public static BaseComponent getTopComponent(ComponentParent component, int x, int y, boolean restrictToParentBounds, boolean respectClickThrough) {
        Stack<BaseComponent> hitStack = getComponentsAt(component, x, y, restrictToParentBounds);

        for(BaseComponent c : hitStack) {
            if(respectClickThrough && c.isClickThrough()) {
                continue;
            }
            return c;
        }
        return null;
    }

    public static Stack<BaseComponent> getComponentsAt(ComponentParent component, int x, int y, boolean restrictToParentBounds) {
        Stack<BaseComponent> hitStack = new Stack<BaseComponent>();
        
        for(BaseComponent child : component.getChildren()) {
            getComponentsAt(child, x, y, restrictToParentBounds, hitStack);
        }

        return hitStack;
    }
    private static void getComponentsAt(BaseComponent component, int x, int y, boolean restrictToParentBounds, Stack<BaseComponent> hitStack) {
        if(!component.isVisible())
            return;
        
        boolean hit = isHit(component, x, y);

        if(hit) {
            hitStack.add(component);
        }

        if(hit || !restrictToParentBounds) {
            for (BaseComponent child : component.getChildren()) {
                getComponentsAt(child, x, y, restrictToParentBounds, hitStack);
            }
        }
    }
    
    private static boolean isHit(BaseComponent component, int x, int y) {
        if(!component.isVisible())
            return false;
        
        if(component.getAbsoluteX() > x || component.getAbsoluteY() > y)
            return false;
        if(component.getAbsoluteX() + component.getWidth() < x || component.getAbsoluteY() + component.getHeight() < y)
            return false;
        return true;
    }
    
    public static final boolean RESTRICT_CLICKS_TO_PARENT_BOUNDS = false;

    public void onMouseDown(int x, int y) {
        List<BaseComponent> hitStack = Lists.reverse(getComponentsAt(this, x, y, RESTRICT_CLICKS_TO_PARENT_BOUNDS));

        for(BaseComponent component : hitStack) {
            component.onMouseDown(x, y);
            
            if(!component.isClickThrough()) {
                return;
            }
        }
    }
    public void onMouseUp(int x, int y) {
        List<BaseComponent> hitStack = Lists.reverse(getComponentsAt(this, x, y, RESTRICT_CLICKS_TO_PARENT_BOUNDS));

        for(BaseComponent component : hitStack) {
            component.onMouseUp(x, y);

            if(!component.isClickThrough()) {
                return;
            }
        }
    }
    
    protected void registerComponent(BaseComponent component, ComponentParent parent) {
        if(parent == this) {
            _children.add(component);
            sortChildren();
        } else {
            BaseComponent parentComponent = (BaseComponent)component.getParent();
            parentComponent._children.add(component);
            parentComponent.sortChildren();
        }
        _allComponents.add(component);
    }

    protected void deregisterComponent(BaseComponent component) {
        _allComponents.remove(component);
    }

    @Override
    public ComponentParent getParent() {
        return null;
    }
}
