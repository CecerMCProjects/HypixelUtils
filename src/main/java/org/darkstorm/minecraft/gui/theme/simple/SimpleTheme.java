package org.darkstorm.minecraft.gui.theme.simple;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.theme.AbstractTheme;

public class SimpleTheme extends AbstractTheme {
	private final FontRenderer fontRenderer;

	public SimpleTheme() {
		fontRenderer = Minecraft.getMinecraft().fontRendererObj;

		installUI(new SimpleFrameUI(this));
		installUI(new SimplePanelUI(this));
		installUI(new SimpleLabelUI(this));
		installUI(new SimpleButtonUI(this));
		installUI(new SimpleCheckButtonUI(this));
		installUI(new SimpleComboBoxUI(this));
		installUI(new SimpleSliderUI(this));
		installUI(new SimpleProgressBarUI(this));
	}

	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
}
