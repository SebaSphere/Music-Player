package info.u_team.music_player.gui.settings;

import static info.u_team.music_player.init.MusicPlayerLocalization.*;

import info.u_team.music_player.gui.*;
import info.u_team.music_player.gui.controls.GuiControls;
import info.u_team.music_player.init.MusicPlayerResources;
import info.u_team.music_player.musicplayer.MusicPlayerManager;
import info.u_team.music_player.musicplayer.settings.*;
import info.u_team.u_team_core.gui.elements.*;
import info.u_team.u_team_core.gui.render.ScrollingTextRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

public class GuiMusicPlayerSettings extends BetterScreen {
	
	private final Screen previousGui;
	
	private GuiMusicPlayerSettingsMixerDeviceList mixerDeviceList;
	
	private GuiControls controls;
	
	public GuiMusicPlayerSettings(Screen previousGui) {
		super(new StringTextComponent("musicplayersettings"));
		this.previousGui = previousGui;
	}
	
	@Override
	protected void init() {
		addButton(new ImageButton(1, 1, 15, 15, MusicPlayerResources.TEXTURE_BACK, button -> minecraft.displayGuiScreen(previousGui)));
		
		final Settings settings = MusicPlayerManager.getSettingsManager().getSettings();
		
		final ActiveButton toggleKeyWorkInGuiButton = addButton(new ActiveButton(12, 60, width / 2 - 24, 20, getTranslation(GUI_SETTINGS_TOGGLE_KEY_IN_GUI), 0x80FF00FF));
		toggleKeyWorkInGuiButton.setActive(settings.isKeyWorkInGui());
		toggleKeyWorkInGuiButton.setPressable(() -> {
			settings.setKeyWorkInGui(!settings.isKeyWorkInGui());
			toggleKeyWorkInGuiButton.setActive(settings.isKeyWorkInGui());
		});
		
		final ActiveButton toggleIngameMenueDisplayButton = addButton(new ActiveButton(width / 2 + 14, 60, width / 2 - 24, 20, getTranslation(GUI_SETTINGS_TOGGLE_MENUE_OVERLAY), 0x80FF00FF));
		toggleIngameMenueDisplayButton.setActive(settings.isShowIngameMenueOverlay());
		toggleIngameMenueDisplayButton.setPressable(() -> {
			settings.setShowIngameMenueOverlay(!settings.isShowIngameMenueOverlay());
			toggleIngameMenueDisplayButton.setActive(settings.isShowIngameMenueOverlay());
		});
		
		final ActiveButton toggleIngameDisplayButton = addButton(new ActiveButton(12, 90, width / 2 - 24, 20, getTranslation(GUI_SETTINGS_TOGGLE_INGAME_OVERLAY), 0x80FF00FF));
		toggleIngameDisplayButton.setActive(settings.isShowIngameOverlay());
		toggleIngameDisplayButton.setPressable(() -> {
			settings.setShowIngameOverlay(!settings.isShowIngameOverlay());
			toggleIngameDisplayButton.setActive(settings.isShowIngameOverlay());
		});
		
		final UButton ingameOverlayPositionButton = addButton(new UButton(width / 2 + 14, 90, width / 2 - 24, 20, getTranslation(GUI_SETTINGS_POSITION_OVERLAY) + ": " + getTranslation(settings.getIngameOverlayPosition().getLocalization())));
		ingameOverlayPositionButton.setPressable(() -> {
			settings.setIngameOverlayPosition(IngameOverlayPosition.forwardCycle(settings.getIngameOverlayPosition()));
			ingameOverlayPositionButton.setMessage(getTranslation(GUI_SETTINGS_POSITION_OVERLAY) + ": " + getTranslation(settings.getIngameOverlayPosition().getLocalization()));
		});
		
		mixerDeviceList = new GuiMusicPlayerSettingsMixerDeviceList(width - 24, height, 133, 183, 12, width - 12);
		children.add(mixerDeviceList);
		
		controls = new GuiControls(this, 5, width);
		children.add(controls);
	}
	
	@Override
	public void tick() {
		controls.tick();
	}
	
	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		final ScrollingTextRender titleRender = controls.getTitleRender();
		final ScrollingTextRender authorRender = controls.getAuthorRender();
		this.init(minecraft, width, height);
		controls.setTitleRender(titleRender);
		controls.setAuthorRender(authorRender);
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderDirtBackground(0);
		mixerDeviceList.render(mouseX, mouseY, partialTicks);
		font.drawString(getTranslation(GUI_SETTINGS_MIXER_DEVICE_SELECTION), 13, 117, 0xFFFFFF);
		controls.drawScreen(mouseX, mouseY, partialTicks);
		super.render(mouseX, mouseY, partialTicks);
	}
	
}
