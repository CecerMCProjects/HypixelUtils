package com.cecer1.hypixelutils.gui.components.core;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class TextLabelComponent extends SizableComponent {
    private HorizontalTextAlignment _halign;
    private VerticalTextAlignment _valign;
    private TextOverflowMode _overflow;
    private String _text;
    private int _colour;
    private boolean _shadow;
    private static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    private static int getCentredFontHeight() {
        return fontRenderer.FONT_HEIGHT-2;
    }
    public static int getFontHeight() {
        return fontRenderer.FONT_HEIGHT;
    }

    public TextLabelComponent(ComponentParent parent) {
        super(parent);
        _text = "";
        _shadow = true;
        _halign = HorizontalTextAlignment.LEFT;
        _valign = VerticalTextAlignment.TOP;
        _overflow = TextOverflowMode.FULL_OVERFLOW;
    }

    @Override
    public void setSize(int width, int height) {
        if(height < getCentredFontHeight())
            height = getCentredFontHeight();
        super.setSize(width, height);
    }

    public HorizontalTextAlignment getHorizontalAlignment() {
        return _halign;
    }
    public void setHorizontalAlignment(HorizontalTextAlignment alignment) {
        _halign = alignment;
    }
    public VerticalTextAlignment getVerticalAlignment() {
        return _valign;
    }
    public void setVerticalAlignment(VerticalTextAlignment alignment) {
        _valign = alignment;
    }
    public TextOverflowMode getOverflowMode() {
        return _overflow;
    }
    public void setOverflowMode(TextOverflowMode mode) {
        _overflow = mode;
    }
    

    private static void drawStringWithShadow(String text, int x, int y, int colour, boolean shadow) {
        fontRenderer.func_175065_a(text, x, y, colour, shadow);
    }
    
    
    /*
Not all combinations of rendering mode are implemented. Here is a table showing the progress.
    			TRIM	BOF		FOF		WRAP
LeftTop			Done	NI		Done	BU
LeftCentre		Done	NI		Done	NI
LeftBottom		Done	NI		Done	NI
CentreTop		Done	NI		Done	NI
CentreCentre	Done	NI		Done	NI
CentreBottom	Done	NI		Done	NI
RightTop		Done	NI		Done	NI
RightCentre		Done	NI		Done	NI
RightBottom		Done	NI		Done	NI
     */
    @Override
    public void render(int mouseX, int mouseY, boolean hasFocus) {
        if(_halign == HorizontalTextAlignment.LEFT) {
            if(_valign == VerticalTextAlignment.TOP) {
                if(_overflow == TextOverflowMode.TRIM) {
                    String textToDraw = fontRenderer.trimStringToWidth(_text, getWidth());
                    drawStringWithShadow(textToDraw, getAbsoluteX(), getAbsoluteY(), _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.BOUND_OVERFLOW) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
                else if(_overflow == TextOverflowMode.FULL_OVERFLOW) {
                    drawStringWithShadow(_text, getAbsoluteX(), getAbsoluteY(), _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.WRAP) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
            } else if(_valign == VerticalTextAlignment.CENTRE) {
                if(_overflow == TextOverflowMode.TRIM) {
                    String textToDraw = fontRenderer.trimStringToWidth(_text, getWidth());

                    int extraY = getHeight()-getCentredFontHeight();
                    int offsetY = extraY/2;
                    drawStringWithShadow(textToDraw, getAbsoluteX(), getAbsoluteY()+offsetY, _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.BOUND_OVERFLOW) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
                else if(_overflow == TextOverflowMode.FULL_OVERFLOW) {
                    int extraY = getHeight()-getCentredFontHeight();
                    int offsetY = extraY/2;
                    drawStringWithShadow(_text, getAbsoluteX(), getAbsoluteY()+offsetY, _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.WRAP) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
            } else if(_valign == VerticalTextAlignment.BOTTOM) {
                if(_overflow == TextOverflowMode.TRIM) {
                    String textToDraw = fontRenderer.trimStringToWidth(_text, getWidth());
                    int offsetY = getHeight()-getCentredFontHeight();
                    drawStringWithShadow(textToDraw, getAbsoluteX(), getAbsoluteY()+offsetY, _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.BOUND_OVERFLOW) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
                else if(_overflow == TextOverflowMode.FULL_OVERFLOW) {
                    int offsetY = getHeight()-getCentredFontHeight();
                    drawStringWithShadow(_text, getAbsoluteX(), getAbsoluteY()+offsetY, _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.WRAP) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
            }
        } else if(_halign == HorizontalTextAlignment.CENTRE) {
            if(_valign == VerticalTextAlignment.TOP) {
                if(_overflow == TextOverflowMode.TRIM) {
                    String textToDraw = fontRenderer.trimStringToWidth(_text, getWidth());
                    int textWidth = fontRenderer.getStringWidth(textToDraw);
                    int extraWidth = Math.max(0, getWidth()-textWidth);
                    int offsetX = extraWidth/2;
                    
                    drawStringWithShadow(textToDraw, getAbsoluteX()+offsetX, getAbsoluteY(), _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.BOUND_OVERFLOW) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
                else if(_overflow == TextOverflowMode.FULL_OVERFLOW) {
                    int textWidth = fontRenderer.getStringWidth(_text);
                    int extraWidth = Math.max(0, getWidth()-textWidth);
                    int offsetX = extraWidth/2;

                    drawStringWithShadow(_text, getAbsoluteX()+offsetX, getAbsoluteY(), _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.WRAP) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
            } else if(_valign == VerticalTextAlignment.CENTRE) {
                if(_overflow == TextOverflowMode.TRIM) {
                    String textToDraw = fontRenderer.trimStringToWidth(_text, getWidth());
                    int textWidth = fontRenderer.getStringWidth(textToDraw);
                    int extraWidth = Math.max(0, getWidth()-textWidth);
                    int offsetX = extraWidth/2;
                    
                    int extraY = getHeight()-getCentredFontHeight();
                    int offsetY = (extraY/2);

                    drawStringWithShadow(textToDraw, getAbsoluteX()+offsetX, getAbsoluteY()+offsetY, _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.BOUND_OVERFLOW) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
                else if(_overflow == TextOverflowMode.FULL_OVERFLOW) {
                    int textWidth = fontRenderer.getStringWidth(_text);
                    int extraWidth = Math.max(0, getWidth()-textWidth);
                    int offsetX = extraWidth/2;

                    int extraY = getHeight()-getCentredFontHeight();
                    int offsetY = (extraY/2);

                    drawStringWithShadow(_text, getAbsoluteX()+offsetX, getAbsoluteY()+offsetY, _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.WRAP) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
            } else if(_valign == VerticalTextAlignment.BOTTOM) {
                if(_overflow == TextOverflowMode.TRIM) {
                    String textToDraw = fontRenderer.trimStringToWidth(_text, getWidth());
                    int textWidth = fontRenderer.getStringWidth(textToDraw);
                    int extraWidth = Math.max(0, getWidth()-textWidth);
                    int offsetX = extraWidth/2;

                    int offsetY = getHeight()-getCentredFontHeight();

                    drawStringWithShadow(textToDraw, getAbsoluteX()+offsetX, getAbsoluteY()+offsetY, _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.BOUND_OVERFLOW) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
                else if(_overflow == TextOverflowMode.FULL_OVERFLOW) {
                    int textWidth = fontRenderer.getStringWidth(_text);
                    int extraWidth = Math.max(0, getWidth()-textWidth);
                    int offsetX = extraWidth/2;

                    int offsetY = getHeight()-getCentredFontHeight();

                    drawStringWithShadow(_text, getAbsoluteX()+offsetX, getAbsoluteY()+offsetY, _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.WRAP) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
            }
        } else if(_halign == HorizontalTextAlignment.RIGHT) {
            if(_valign == VerticalTextAlignment.TOP) {
                if(_overflow == TextOverflowMode.TRIM) {
                    String textToDraw = fontRenderer.trimStringToWidth(_text, getWidth());
                    int textWidth = fontRenderer.getStringWidth(textToDraw);
                    int offsetX = getWidth()-textWidth;

                    drawStringWithShadow(textToDraw, getAbsoluteX()+offsetX, getAbsoluteY(), _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.BOUND_OVERFLOW) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
                else if(_overflow == TextOverflowMode.FULL_OVERFLOW) {
                    int textWidth = fontRenderer.getStringWidth(_text);
                    int offsetX = getWidth() - textWidth;

                    drawStringWithShadow(_text, getAbsoluteX()+offsetX, getAbsoluteY(), _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.WRAP) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
            } else if(_valign == VerticalTextAlignment.CENTRE) {
                if(_overflow == TextOverflowMode.TRIM) {
                    String textToDraw = fontRenderer.trimStringToWidth(_text, getWidth());
                    int textWidth = fontRenderer.getStringWidth(textToDraw);
                    int offsetX = getWidth() - textWidth;

                    int extraY = getHeight()-getCentredFontHeight();
                    int offsetY = (extraY/2);

                    drawStringWithShadow(textToDraw, getAbsoluteX()+offsetX, getAbsoluteY()+offsetY, _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.BOUND_OVERFLOW) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
                else if(_overflow == TextOverflowMode.FULL_OVERFLOW) {
                    int textWidth = fontRenderer.getStringWidth(_text);
                    int offsetX = getWidth() - textWidth;

                    int extraY = getHeight()-getCentredFontHeight();
                    int offsetY = (extraY/2);

                    drawStringWithShadow(_text, getAbsoluteX()+offsetX, getAbsoluteY()+offsetY, _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.WRAP) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
            } else if(_valign == VerticalTextAlignment.BOTTOM) {
                if(_overflow == TextOverflowMode.TRIM) {
                    String textToDraw = fontRenderer.trimStringToWidth(_text, getWidth());
                    int textWidth = fontRenderer.getStringWidth(textToDraw);
                    int offsetX = getWidth() - textWidth;

                    int offsetY = getHeight()-getCentredFontHeight();

                    drawStringWithShadow(textToDraw, getAbsoluteX()+offsetX, getAbsoluteY()+offsetY, _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.BOUND_OVERFLOW) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
                else if(_overflow == TextOverflowMode.FULL_OVERFLOW) {
                    int textWidth = fontRenderer.getStringWidth(_text);
                    int offsetX = getWidth() - textWidth;

                    int offsetY = getHeight()-getCentredFontHeight();

                    drawStringWithShadow(_text, getAbsoluteX()+offsetX, getAbsoluteY()+offsetY, _colour, _shadow);
                }
                else if(_overflow == TextOverflowMode.WRAP) {
                    throw new RuntimeException("Text rendering mode is not implemented.");
                }
            }
        }
        
        super.render(mouseX, mouseY, hasFocus);
    }

    public void setColour(int argb) {
        _colour = argb;
    }
    public void setColour(int red, int green, int blue, int alpha) {
        setColour((alpha << 24) | (red << 16) | (green << 8) | blue);
    }
    public int getColour() {
        return _colour;
    }
    
    public void setText(String text) {
        _text = text;
    }
    public String getText() {
        return _text;
    }
}