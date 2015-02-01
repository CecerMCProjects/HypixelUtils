package com.cecer1.hypixelutils.gui;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnRenderEventData;
import com.cecer1.hypixelutils.events.handlers.IOnRenderEventHandler;
import com.cecer1.hypixelutils.gui.components.core.BaseComponent;
import com.cecer1.hypixelutils.gui.components.core.Canvas;
import com.cecer1.hypixelutils.gui.windows.ConfigWindow;
import com.cecer1.hypixelutils.gui.windows.MitLicenseWindow;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.List;

public class HypixelUtilsGuiManager extends GuiScreen implements IOnRenderEventHandler {
    private Canvas _screenCanvas;
    private Canvas _permCanvas;

    public ConfigWindow configWindow;
    public MitLicenseWindow unirestLicenseWindow;
    public MitLicenseWindow hypixelUtilsLicenseWindow;

    public HypixelUtilsGuiManager() {
        _permCanvas = new Canvas();

        _screenCanvas = new Canvas();
        _screenCanvas.setVisible(true);

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
        if(data instanceof OnRenderEventData)
            onEvent((OnRenderEventData)data);
    }

    @Override
    public void onEvent(OnRenderEventData data) {
        if(data.isIngame() && _permCanvas.isVisible()) {
            _screenCanvas.render(Integer.MIN_VALUE, Integer.MIN_VALUE);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        _screenCanvas.render(mouseX, mouseY);

        List<BaseComponent> components = Lists.reverse(Canvas.getComponentsAt(_screenCanvas, mouseX, mouseY, false));
        for(BaseComponent component : components) {
            if(component.hoverText != null) {
                drawHoveringText(component.hoverText, mouseX, mouseY);
            }
            if(!component.isClickThrough()) {
                return;
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        _screenCanvas.onMouseDown(mouseX, mouseY);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        _screenCanvas.onMouseUp(mouseX, mouseY);
    }


    /**
     * Toggles the display of the GuiScreen of the GuiEngine.
     * Use toggleScreenDelayed instead if doing from a command or the chat closing will close the Gui.
     * @param visible If true then the screen will be displayed, otherwise it will be hidden.
     */
    public void setVisible(boolean visible) {
        if(visible && Minecraft.getMinecraft().currentScreen == this)
            return;
        if(!visible && Minecraft.getMinecraft().currentScreen != this)
            return;

        final GuiScreen screenToDisplay;
        if(visible)
            screenToDisplay = this;
        else {
            screenToDisplay = null;

            HypixelUtilsCore.userInterface.configWindow.setVisible(false);
            HypixelUtilsCore.userInterface.unirestLicenseWindow.setVisible(false);
            HypixelUtilsCore.userInterface.hypixelUtilsLicenseWindow.setVisible(false);
        }

        Minecraft.getMinecraft().displayGuiScreen(screenToDisplay);
        HypixelUtilsCore.configHelper.save();
    }

    /**
     * Toggles the display of the GuiScreen of the GuiEngine.
     * The action will be delayed by 1 tick. Use this if doing from a command or the chat closing will close the Gui.
     * @param visible If true then the screen will be displayed, otherwise it will be hidden.
     */
    public void setVisibleDelayed(final boolean visible) {
        // We wait 1 tick otherwise the chat closing will close our screen.
        HypixelUtilsCore.scheduler.scheduleTask(new Runnable() {
            @Override
            public void run() {
                setVisible(visible);
            }
        }, 1);
    }
}