package com.cecer1.hypixelutils.gui;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnPostRenderHUDEventData;
import com.cecer1.hypixelutils.events.handlers.IOnPostRenderHUDEventHandler;
import com.cecer1.hypixelutils.gui.components.core.BaseComponent;
import com.cecer1.hypixelutils.gui.components.core.Canvas;
import com.cecer1.hypixelutils.gui.windows.ConfigWindow;
import com.cecer1.hypixelutils.gui.windows.MitLicenseWindow;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.List;

public class HypixelUtilsGuiManager extends GuiScreen implements IOnPostRenderHUDEventHandler {
    private Canvas _screenCanvas;

    public ConfigWindow configWindow;
    public MitLicenseWindow unirestLicenseWindow;
    public MitLicenseWindow hypixelUtilsLicenseWindow;

    public HypixelUtilsGuiManager() {
        _screenCanvas = new Canvas();

        configWindow = new ConfigWindow(_screenCanvas);
        configWindow.setVisible(true);
        configWindow.setOffset(HypixelUtilsCore.configHelper.configWindowPositionX.getValue(10), HypixelUtilsCore.configHelper.configWindowPositionY.getValue(10));

        unirestLicenseWindow = new MitLicenseWindow(_screenCanvas, "HypixelUtils uses Unirest by Mashape.", "Unirest was released under the following license.", 2013, "Mashape (http://mashape.com)");
        unirestLicenseWindow.setTitle("Unirest License");
        unirestLicenseWindow.setOffset(10, 10);
        
        hypixelUtilsLicenseWindow = new MitLicenseWindow(_screenCanvas, "HypixelUtils was released under the following license.", "", 2015, "Cecer (cecer1.com)");
        hypixelUtilsLicenseWindow.setTitle("HypixelUtils License");
        hypixelUtilsLicenseWindow.setOffset(20, 20);
    }

    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnPostRenderHUDEventData)
            onEvent((OnPostRenderHUDEventData)data);
    }

    
    private boolean _alreadyRendered = false;
    private int _lastMouseX = 0;
    private int _lastMouseY = 0;
    
    @Override
    public void onEvent(OnPostRenderHUDEventData data) {
        render(0, 0, false);
        _alreadyRendered = false;
    }
    
    private void render(int mouseX, int mouseY, boolean hasFocus) {
        if(_alreadyRendered)
            return;
        _alreadyRendered = true;
        if(hasFocus) {
            _lastMouseX = mouseX;
            _lastMouseY = mouseY;
        } else {
            mouseX = _lastMouseX;
            mouseY = _lastMouseY;
        }
        
        _screenCanvas.render(mouseX, mouseY, hasFocus);
        
        if(hasFocus) {
            List<BaseComponent> components = Lists.reverse(Canvas.getComponentsAt(_screenCanvas, mouseX, mouseY, false, true));
            for (BaseComponent component : components) {
                component.onHover(mouseX, mouseY);
                if (!component.isClickThrough()) {
                    break;
                }
            }
        }

        _screenCanvas.postRender(mouseX, mouseY, hasFocus);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        render(mouseX, mouseY, true);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        _screenCanvas.onMouseDown(mouseX, mouseY);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        _screenCanvas.onMouseUp(mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {
        // Unlock every component from the cursor.
        for(BaseComponent component : _screenCanvas.getAllComponents()) {
            component.setCursorLocked(false);
        }
    }

    /**
     * Toggles the display of the GuiScreen of the GuiEngine.
     * Use toggleScreenDelayed instead if doing from a command or the chat closing will close the Gui.
     * @param focused If true then the screen will be displayed, otherwise it will be hidden.
     */
    public void setFocused(boolean focused) {
        if(focused && Minecraft.getMinecraft().currentScreen == this)
            return;
        if(!focused && Minecraft.getMinecraft().currentScreen != this)
            return;

        final GuiScreen screenToDisplay;
        if(focused) {
            screenToDisplay = this;
        } else {
            screenToDisplay = null;
        }

        Minecraft.getMinecraft().displayGuiScreen(screenToDisplay);
        HypixelUtilsCore.configHelper.save();
    }

    /**
     * Toggles the display of the GuiScreen of the GuiEngine.
     * The action will be delayed by 1 tick. Use this if doing from a command or the chat closing will close the Gui.
     * @param visible If true then the screen will be displayed, otherwise it will be hidden.
     */
    public void setFocusedDelayed(final boolean visible) {
        // We wait 1 tick otherwise the chat closing will close our screen.
        HypixelUtilsCore.scheduler.scheduleTask(new Runnable() {
            @Override
            public void run() {
                setFocused(visible);
            }
        }, 1);
    }

    public void drawHoverText(List<String> hoverText, int x, int y) {
        this.drawHoveringText(hoverText, x, y);
    }
}