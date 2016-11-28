package com.sturdyhelmetgames.roomforchange.tools;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;
import com.sturdyhelmetgames.roomforchange.assets.Assets;

public class PackTextures {

	private static final String UNPROCESSED_FOLDER = "C:\\Users\\Antti\\workspace\\game-assets\\roomforchange\\unprocessed";
	private static final String PROCESSED_FOLDER = "C:\\Users\\Antti\\Documents\\GitHub\\game-off-2013\\roomforchange\\roomforchange-android\\assets\\data";

	public static void main(String[] args) {
		final Settings settings = new Settings();
		settings.maxWidth = 1024;
		settings.maxHeight = 1024;
		settings.paddingX = 4;
		settings.paddingY = 4;
		//settings.bleed = true;
		settings.edgePadding = true;
		settings.pot = true;
		TexturePacker2.process(settings, UNPROCESSED_FOLDER, PROCESSED_FOLDER,
				Assets.ATLAS_FILE_OBJECTS_ALL);
	}
}
