package com.cecer1.hypixelutils.gui;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.modframework.common.events.IOnRenderEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.darkstorm.minecraft.gui.GuiManager;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.theme.Theme;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;
import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;

public class HypixelUtilsGuiManager extends GuiScreen implements IOnRenderEventHandler {
    private GuiManager _guiEngine;
    private GuiManagerDisplayScreen _screen;
    
    public HypixelUtilsGuiManager() {
        _guiEngine = new HypixelUtilsGuiEngine();
        _guiEngine.setTheme(new SimpleTheme());
        _guiEngine.setup();
        
        _screen = new GuiManagerDisplayScreen(_guiEngine);
    }
    
    public HypixelUtilsGuiFrame initAndAddFrame(HypixelUtilsGuiFrame frame) {
        frame.init();
        addFrame(frame);
        return frame;
    }
    
    public void draw() {
        for(Frame frame : _guiEngine.getFrames()) {
            if(frame.isPinned()) {
                frame.render();
            }
            frame.update();
        }
    }

    public HypixelUtilsGuiManager addFrame(Frame frame) {
        _guiEngine.addFrame(frame);
        return this;
    }
    public HypixelUtilsGuiManager removeFrame(Frame frame) {
        _guiEngine.removeFrame(frame);
        return this;
    }

    @Override
    public void onRender(IOnRenderEventData event) {
        if(event.isIngame()) {
            draw();
        }
    }
    
    public void closeScreenIfNoFrames() {
        if(!areAnyFramesVisible())
            toggleScreen(false);
    }
    
    public boolean areAnyFramesVisible() {
        for(Frame frame : _guiEngine.getFrames()) {
            if(frame.isVisible())
                return true;
        }
        return false;
    }

    /**
     * Toggles the display of the GuiScreen of the GuiEngine.
     * Use toggleScreenDelayed instead if doing from a command or the chat closing will close the Gui.
     * @param active If true then the screen will be displayed, otherwise it will be hidden.
     */
    public void toggleScreen(boolean active) {
        if(active && Minecraft.getMinecraft().currentScreen == _screen)
            return;
        if(!active && Minecraft.getMinecraft().currentScreen != _screen)
            return;
        
        final GuiScreen screenToDisplay;
        if(active)
            screenToDisplay = _screen;
        else
            screenToDisplay = null;
        
        Minecraft.getMinecraft().displayGuiScreen(screenToDisplay);
    }

    /**
     * Toggles the display of the GuiScreen of the GuiEngine.
     * The action will be delayed by 1 tick. Use this if doing from a command or the chat closing will close the Gui.
     * @param active If true then the screen will be displayed, otherwise it will be hidden.
     */
    public void toggleScreenDelayed(final boolean active) {
        // We wait 1 tick otherwise the chat closing will close our screen.
        HypixelUtilsCore.scheduler.scheduleTask(new Runnable() {
            @Override
            public void run() {
                toggleScreen(active);
            }
        }, 1);
    }
    
    public Theme getTheme() {
        return _guiEngine.getTheme();
    }
}
