package com.cecer1.hypixelutils.gui.components.core;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.gui.CanRenderValue;
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
        setCanRenderFocused(CanRenderValue.TRUE);
        setCanRenderUnfocused(CanRenderValue.TRUE);
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
            return Integer.MAX_VALUE;
        return screen.width;
    }

    @Override
    public int getHeight() {
        GuiScreen screen = Minecraft.getMinecraft().currentScreen;
        if(screen == null)
            return Integer.MAX_VALUE;
        return screen.height;
    }

    @Override
    public List<BaseComponent> getChildren() {
        return _children;
    }

    private boolean _anyRendered = false;
    
    @Override
    public void render(int mouseX, int mouseY, boolean hasFocus) {
        _anyRendered = false;
        
        if(!hasFocus)
            onMouseUp(mouseX, mouseY);
        
        for (BaseComponent component : getChildren()) {
            if (component.isRenderable(hasFocus)) {
                component.render(mouseX, mouseY, hasFocus);
                _anyRendered = true;
            }
        }
    }

    @Override
    public void postRender(int mouseX, int mouseY, boolean hasFocus) {
        for (BaseComponent component : getChildren()) {
            if (component.isRenderable(hasFocus)) {
                component.postRender(mouseX, mouseY, hasFocus);
            }
        }
        if(hasFocus) {
            if (!_anyRendered)
                HypixelUtilsCore.userInterface.setFocused(false);
        }
    }

    public static BaseComponent getTopComponent(ComponentParent component, int x, int y, boolean restrictToParentBounds, boolean respectClickThrough, boolean hasFocus) {
        Stack<BaseComponent> hitStack = getComponentsAt(component, x, y, restrictToParentBounds, hasFocus);

        for(BaseComponent c : hitStack) {
            if(respectClickThrough && c.isClickThrough()) {
                continue;
            }
            return c;
        }
        return null;
    }

    public static Stack<BaseComponent> getComponentsAt(ComponentParent component, int x, int y, boolean restrictToParentBounds, boolean hasFocus) {
        Stack<BaseComponent> hitStack = new Stack<BaseComponent>();
        
        for(BaseComponent child : component.getChildren()) {
            getComponentsAt(child, x, y, restrictToParentBounds, hitStack, hasFocus);
        }

        return hitStack;
    }
    private static void getComponentsAt(BaseComponent component, int x, int y, boolean restrictToParentBounds, Stack<BaseComponent> hitStack, boolean hasFocus) {
        if(!component.isRenderable(hasFocus))
            return;
        
        boolean hit = isHit(component, x, y, hasFocus);

        if(hit) {
            hitStack.add(component);
        }

        if(hit || !restrictToParentBounds) {
            for (BaseComponent child : component.getChildren()) {
                getComponentsAt(child, x, y, restrictToParentBounds, hitStack, hasFocus);
            }
        }
    }
    
    private static boolean isHit(BaseComponent component, int x, int y, boolean hasFocus) {
        if(!component.isRenderable(hasFocus))
            return false;
        
        if(component.getAbsoluteX() > x || component.getAbsoluteY() > y)
            return false;
        if(component.getAbsoluteX() + component.getWidth() < x || component.getAbsoluteY() + component.getHeight() < y)
            return false;
        return true;
    }
    
    public static final boolean RESTRICT_CLICKS_TO_PARENT_BOUNDS = false;
    
    private boolean isMouseDown = false;
    public void onMouseDown(int x, int y) {
        isMouseDown = true;
        
        List<BaseComponent> hitStack = Lists.reverse(getComponentsAt(this, x, y, RESTRICT_CLICKS_TO_PARENT_BOUNDS, true));

        for(BaseComponent component : hitStack) {
            component.onMouseDown(x, y);
            
            if(!component.isClickThrough()) {
                return;
            }
        }
    }
    public void onMouseUp(int x, int y) {
        if(!isMouseDown)
            return;
        isMouseDown = false;

        List<BaseComponent> hitStack = Lists.reverse(getComponentsAt(this, x, y, RESTRICT_CLICKS_TO_PARENT_BOUNDS, true));


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
    
    public BaseComponent[] getAllComponents() {
        return _allComponents.toArray(new BaseComponent[_allComponents.size()]);
    }

    @Override
    public ComponentParent getParent() {
        return null;
    }

    @Override
    public boolean aisVisible() {
        return true;
    }

    @Override
    public boolean getCanRenderFocused() {
        if(canRenderFocused == CanRenderValue.TRUE)
            return true;
        return false;
    }

    @Override
    public boolean getCanRenderUnfocused() {
        if(canRenderUnfocused == CanRenderValue.TRUE)
            return true;
        return false;
    }
}
