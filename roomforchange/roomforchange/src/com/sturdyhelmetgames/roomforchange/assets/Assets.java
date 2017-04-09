/*    Copyright 2013 Antti Kolehmainen

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */
package com.sturdyhelmetgames.roomforchange.assets;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.sturdyhelmetgames.roomforchange.level.PieceTemplate;
import com.sturdyhelmetgames.roomforchange.level.RoomTemplate;

public class Assets {

	private static final AssetManager assetManager = new AssetManager();

	public static final String ATLAS_FILE_OBJECTS_ALL = "objects_all";
	private static final String FOLDER_DATA = "data/";
	private static final String FOLDER_SOUNDS = "data/sounds/";
	private static final String FOLDER_PARTICLE = "data/particles/";
	private static final String ATLAS_FILEPATH_OBJECTS_ALL = FOLDER_DATA
			+ ATLAS_FILE_OBJECTS_ALL + ".atlas";

	public static final String SOUND_STONEDOOR = FOLDER_SOUNDS
			+ "stonedoor.mp3";
	public static final String SOUND_ENEMYDIE = FOLDER_SOUNDS + "enemydie.wav";
	public static final String SOUND_COLLECT = FOLDER_SOUNDS + "collect.wav";
	public static final String SOUND_DEATH = FOLDER_SOUNDS + "death.wav";
	public static final String SOUND_HIT = FOLDER_SOUNDS + "hit.wav";
	public static final String SOUND_EXPLOSION = FOLDER_SOUNDS
			+ "explosion.wav";
	public static final String SOUND_MUSIC = FOLDER_SOUNDS
			+ "dungeon_music.mp3";

	public static final String PARTICLE_SANDSTREAM = FOLDER_PARTICLE
			+ "sandstream.p";
	public static final String PARTICLE_SANDSMOKE_RIGHT = FOLDER_PARTICLE
			+ "sandsmoke_right.p";
	public static final String PARTICLE_SANDSMOKE_LEFT = FOLDER_PARTICLE
			+ "sandsmoke_left.p";
	public static final String PARTICLE_SANDSMOKE_UP = FOLDER_PARTICLE
			+ "sandsmoke_up.p";
	public static final String PARTICLE_SANDSMOKE_DOWN = FOLDER_PARTICLE
			+ "sandsmoke_down.p";
	public static final String PARTICLE_ENEMY_DIE = FOLDER_PARTICLE
			+ "enemydie.p";
	public static final String PARTICLE_EXPLOSION = FOLDER_PARTICLE
			+ "explosion.p";

	public static final String TEXTURE_FONT_BIG_BLACK = FOLDER_DATA
			+ "font-big-black.png";
	public static final String TEXTURE_FONT_BIG_WHITE = FOLDER_DATA
			+ "font-big-white.png";
	public static final String TEXTURE_FONT_SMALL_BLACK = FOLDER_DATA
			+ "font-small-black.png";
	public static final String TEXTURE_FONT_SMALL_WHITE = FOLDER_DATA
			+ "font-small-white.png";

	public static TextureRegion[] fontBigBlack;
	public static TextureRegion[] fontBigWhite;
	public static TextureRegion[] fontSmallBlack;
	public static TextureRegion[] fontSmallWhite;
	

	public static Animation batWalkFront;
	public static Animation batWalkBack;
	public static Animation batWalkLeft;
	public static Animation batWalkRight;
	
	public static Animation zombieWalkFront;
	public static Animation zombieWalkBack;
	public static Animation zombieWalkLeft;
	public static Animation zombieWalkRight;
	
	public static Animation skeletonWalkFront;
	public static Animation skeletonWalkBack;
	public static Animation skeletonWalkLeft;
	public static Animation skeletonWalkRight;
	
	public static Animation ghostWalkFront;
	public static Animation ghostWalkBack;
	public static Animation ghostWalkLeft;
	public static Animation ghostWalkRight;

	
	public static Animation mummyWalkFront;
	public static Animation mummyWalkBack;
	public static Animation mummyWalkLeft;
	public static Animation mummyWalkRight;

	public static Animation snakeWalkFront;
	public static Animation snakeWalkBack;
	public static Animation snakeWalkRight;
	public static Animation snakeWalkLeft;

	public static Animation playerWalkFront;
	public static Animation playerWalkBack;
	public static Animation playerWalkRight;
	public static Animation playerWalkLeft;
	public static Animation playerDying;
	public static Animation playerFalling;


	public static Animation thiefWalkFront;
	public static Animation thiefWalkBack;
	public static Animation thiefWalkRight;
	public static Animation thiefWalkLeft;
	public static Animation thiefDying;
	public static Animation thiefFalling;
	public static Animation thiefKnifeLeft;
	public static Animation thiefKnifeUp;
	public static Animation thiefKnifeDown;
	public static Animation thiefKnifeRight;
	
	public static Animation pharaoWalkFront;
	public static Animation pharaoWalkBack;
	public static Animation pharaoWalkRight;
	public static Animation pharaoWalkLeft;
	public static Animation pharaoDying;
	public static Animation pharaoFalling;
	public static Animation pharaoFireUp;
	public static Animation pharaoFireDown;
	public static Animation pharaoFireRight;
	public static Animation pharaoFireLeft;
	
	public static Animation keyLeft;
	public static Animation keyRight;

	
	public static Animation spiderFront;
	public static Animation kingSpiderFront;

	public static Animation hitTarget;
	public static Animation bomb;

	public static ParticleEffectPool sandStreamPool;
	public static ParticleEffectPool sandSmokeRightPool;
	public static ParticleEffectPool sandSmokeLeftPool;
	public static ParticleEffectPool sandSmokeUpPool;
	public static ParticleEffectPool sandSmokeDownPool;
	public static ParticleEffectPool enemydiePool;
	public static ParticleEffectPool explosionPool;

	public static final Array<PieceTemplate> pieceTemplates = new Array<PieceTemplate>();
	public static final Array<RoomTemplate> roomTemplates = new Array<RoomTemplate>();

	public static void loadGameData() {
		assetManager.load(ATLAS_FILEPATH_OBJECTS_ALL, TextureAtlas.class);

		assetManager.load(TEXTURE_FONT_BIG_BLACK, Texture.class);
		assetManager.load(TEXTURE_FONT_BIG_WHITE, Texture.class);
		assetManager.load(TEXTURE_FONT_SMALL_BLACK, Texture.class);
		assetManager.load(TEXTURE_FONT_SMALL_WHITE, Texture.class);
		assetManager.load(PARTICLE_SANDSTREAM, ParticleEffect.class);
		assetManager.load(PARTICLE_SANDSMOKE_RIGHT, ParticleEffect.class);
		assetManager.load(PARTICLE_SANDSMOKE_LEFT, ParticleEffect.class);
		assetManager.load(PARTICLE_SANDSMOKE_UP, ParticleEffect.class);
		assetManager.load(PARTICLE_SANDSMOKE_DOWN, ParticleEffect.class);
		assetManager.load(PARTICLE_ENEMY_DIE, ParticleEffect.class);
		assetManager.load(PARTICLE_EXPLOSION, ParticleEffect.class);
		assetManager.load(SOUND_STONEDOOR, Sound.class);
		assetManager.load(SOUND_ENEMYDIE, Sound.class);
		assetManager.load(SOUND_COLLECT, Sound.class);
		assetManager.load(SOUND_HIT, Sound.class);
		assetManager.load(SOUND_DEATH, Sound.class);
		assetManager.load(SOUND_EXPLOSION, Sound.class);
		assetManager.load(SOUND_MUSIC, Sound.class);

		finishLoading();
		setupAssets();
		setupPieceTemplates();
	}

	private static void setupPieceTemplates() {
		final FileHandle[] pieceTemplateHandles = new FileHandle[15];
		for (int i = 1; i <= pieceTemplateHandles.length; i++) {
			pieceTemplateHandles[i - 1] = Gdx.files.internal(FOLDER_DATA
					+ "piecetemplates" + File.separator + i + "_piece.png");
		}

		addPieceTemplate(pieceTemplateHandles[0], new boolean[] { false, true,
				false, false });
		addPieceTemplate(pieceTemplateHandles[1], new boolean[] { false, true,
				false, true });
		addPieceTemplate(pieceTemplateHandles[2], new boolean[] { false, false,
				false, true });
		addPieceTemplate(pieceTemplateHandles[3], new boolean[] { false, false,
				true, false });
		addPieceTemplate(pieceTemplateHandles[4], new boolean[] { true, false,
				true, false });
		addPieceTemplate(pieceTemplateHandles[5], new boolean[] { true, false,
				false, false });
		addPieceTemplate(pieceTemplateHandles[6], new boolean[] { true, true,
				false, false });
		addPieceTemplate(pieceTemplateHandles[7], new boolean[] { false, true,
				true, false });
		addPieceTemplate(pieceTemplateHandles[8], new boolean[] { false, false,
				true, true });
		addPieceTemplate(pieceTemplateHandles[9], new boolean[] { true, false,
				false, true });
		addPieceTemplate(pieceTemplateHandles[10], new boolean[] { true, true,
				false, true });
		addPieceTemplate(pieceTemplateHandles[11], new boolean[] { true, true,
				true, false });
		addPieceTemplate(pieceTemplateHandles[12], new boolean[] { false, true,
				true, true });
		addPieceTemplate(pieceTemplateHandles[13], new boolean[] { true, false,
				true, true });
		addPieceTemplate(pieceTemplateHandles[14], new boolean[] { true, true,
				true, true });

		final FileHandle[] roomObjectHandles = new FileHandle[11];
		for (int i = 1; i <= roomObjectHandles.length; i++) {
			roomObjectHandles[i - 1] = Gdx.files.internal(FOLDER_DATA
					+ "roomtemplates" + File.separator + i + "_piece.png");
		}
		for (FileHandle handle : roomObjectHandles) {
			final Pixmap pixmap = new Pixmap(handle);
			roomTemplates.add(new RoomTemplate(pixmap));
		}
	}

	private static void addPieceTemplate(FileHandle fileHandle,
			boolean[] doorsOpen) {
		final Pixmap pixmap = new Pixmap(fileHandle);
		pieceTemplates.add(new PieceTemplate(pixmap, doorsOpen));
	}

	private static void setupAssets() {
		fontBigBlack = new TextureRegion(get(TEXTURE_FONT_BIG_BLACK,
				Texture.class)).split(8, 8)[0];
		fontBigWhite = new TextureRegion(get(TEXTURE_FONT_BIG_WHITE,
				Texture.class)).split(8, 8)[0];
		fontSmallBlack = new TextureRegion(get(TEXTURE_FONT_SMALL_BLACK,
				Texture.class)).split(4, 4)[0];
		fontSmallWhite = new TextureRegion(get(TEXTURE_FONT_SMALL_WHITE,
				Texture.class)).split(4, 4)[0];
		

		batWalkFront = new Animation(0.1f, new TextureRegion[] {
				getGameObject("bat1"), getGameObject("bat2"),
				getGameObject("bat3") });
		batWalkBack = new Animation(0.1f, new TextureRegion[] {
				getGameObject("bat7"), getGameObject("bat8"),
				getGameObject("bat9") });

		final TextureRegion[] batLeftRegions = new TextureRegion[] {
				getGameObject("bat4"), getGameObject("bat5"),
				getGameObject("bat6") };
		batWalkLeft = new Animation(0.1f, batLeftRegions);
		batWalkRight = new Animation(0.1f,
				flipRegionsHorizontally(batLeftRegions));
		
		zombieWalkFront = new Animation(0.1f, new TextureRegion[] {
				getGameObject("zombie3"), getGameObject("zombie4")});
		zombieWalkBack = new Animation(0.1f, new TextureRegion[] {
				getGameObject("zombie1"), getGameObject("zombie2")});

		final TextureRegion[] zombieLeftRegions = new TextureRegion[] {
				getGameObject("zombie5"), getGameObject("zombie6")};
		zombieWalkRight = new Animation(0.1f, zombieLeftRegions);
		zombieWalkLeft = new Animation(0.1f,
				flipRegionsHorizontally(zombieLeftRegions));
		
		skeletonWalkFront = new Animation(0.1f, new TextureRegion[] {
				getGameObject("skeleton3"), getGameObject("skeleton4")});
		skeletonWalkBack = new Animation(0.1f, new TextureRegion[] {
				getGameObject("skeleton1"), getGameObject("skeleton2")});

		final TextureRegion[] skeletonLeftRegions = new TextureRegion[] {
				getGameObject("skeleton5"), getGameObject("skeleton6")};
		skeletonWalkRight = new Animation(0.1f, skeletonLeftRegions);
		skeletonWalkLeft = new Animation(0.1f,
				flipRegionsHorizontally(skeletonLeftRegions));
		
		ghostWalkFront = new Animation(0.1f, new TextureRegion[] {
				getGameObject("ghost3"), getGameObject("ghost4")});
		ghostWalkBack = new Animation(0.1f, new TextureRegion[] {
				getGameObject("ghost1"), getGameObject("ghost2")});

		final TextureRegion[] ghostLeftRegions = new TextureRegion[] {
				getGameObject("ghost5"), getGameObject("ghost6")};
		ghostWalkRight = new Animation(0.1f, ghostLeftRegions);
		ghostWalkLeft = new Animation(0.1f,
				flipRegionsHorizontally(ghostLeftRegions));
		
		
		

		mummyWalkFront = new Animation(0.2f,
				new TextureRegion[] { getGameObject("mummy-front-1"),
						getGameObject("mummy-front-2"),
						getGameObject("mummy-front-3"),
						getGameObject("mummy-front-4"), });

		snakeWalkFront = new Animation(0.1f, new TextureRegion[] {
				getGameObject("snake-front-1"), getGameObject("snake-front-2"),
				getGameObject("snake-front-3") });
		snakeWalkBack = new Animation(0.1f, new TextureRegion[] {
				getGameObject("snake-back-1"), getGameObject("snake-back-2"),
				getGameObject("snake-back-3") });

		final TextureRegion[] snakeLeftRegions = new TextureRegion[] {
				getGameObject("snake-left-1"), getGameObject("snake-left-2"),
				getGameObject("snake-left-3") };
		snakeWalkLeft = new Animation(0.1f, snakeLeftRegions);
		snakeWalkRight = new Animation(0.1f,
				flipRegionsHorizontally(snakeLeftRegions));

		mummyWalkBack = new Animation(0.2f, new TextureRegion[] {
				getGameObject("mummy-back-1"), getGameObject("mummy-back-2"),
				getGameObject("mummy-back-3"), getGameObject("mummy-back-4"), });

		TextureRegion[] mummyLeftRegions = new TextureRegion[] {
				getGameObject("mummy-left-1"), getGameObject("mummy-left-2"),
				getGameObject("mummy-left-3"), getGameObject("mummy-left-4"), };
		mummyWalkLeft = new Animation(0.2f, mummyLeftRegions);
		mummyWalkRight = new Animation(0.2f,
				flipRegionsHorizontally(mummyLeftRegions));

		playerWalkFront = new Animation(0.15f, new TextureRegion[] {
				getGameObject("player-front-1"),
				getGameObject("player-front-idle"),
				getGameObject("player-front-2"),
				getGameObject("player-front-idle"), });
		playerWalkBack = new Animation(0.15f, new TextureRegion[] {
				getGameObject("player-back-1"),
				getGameObject("player-back-idle"),
				getGameObject("player-back-2"),
				getGameObject("player-back-idle"), });
		playerWalkRight = new Animation(0.2f, new TextureRegion[] {
				getGameObject("player-right-1"),
				getGameObject("player-right-2"), });
		playerWalkLeft = new Animation(0.2f,
				new TextureRegion[] { getGameObject("player-left-1"),
						getGameObject("player-left-2"), });
		playerDying = new Animation(0.3f, new TextureRegion[] {
				getGameObject("player-dying-1"),
				getGameObject("player-dying-2"),
				getGameObject("player-dying-3"),
				getGameObject("player-dying-4"),
				getGameObject("player-dying-5"), });
		playerDying.setPlayMode(Animation.NORMAL);

		playerFalling = new Animation(0.3f, new TextureRegion[] {
				getGameObject("player-falling-1"),
				getGameObject("player-falling-2"),
				getGameObject("player-falling-3"), getGameObject("empty") });
		playerFalling.setPlayMode(Animation.NORMAL);
		
		
		
		thiefWalkFront = new Animation(0.15f, new TextureRegion[] {
				getGameObject("player-f1"),
				getGameObject("player-f2"),});
		thiefWalkBack = new Animation(0.15f, new TextureRegion[] {
				getGameObject("player-b1"),
				getGameObject("player-b2"), });
		thiefWalkRight = new Animation(0.2f, new TextureRegion[] {
				getGameObject("player-r1"),
				getGameObject("player-r2"), });
		thiefWalkLeft = new Animation(0.2f,
				flipRegionsHorizontally(new TextureRegion[] { getGameObject("player-r1"),
						getGameObject("player-r2"), }));
		thiefDying = new Animation(0.3f, new TextureRegion[] {
				getGameObject("player-dead"), });
		thiefDying.setPlayMode(Animation.NORMAL);

		thiefFalling = new Animation(0.3f, new TextureRegion[] { getGameObject("empty") });
		thiefFalling.setPlayMode(Animation.NORMAL);
		
		thiefKnifeLeft = new Animation(1f, new TextureRegion[] {getGameObject("player-knife")});
		thiefKnifeRight = new Animation(1f, flipRegionsHorizontally(
				new TextureRegion[] {getGameObject("player-knife")}));
		thiefKnifeUp = new Animation(1f, new TextureRegion[] {getGameObject("player-knife-u")});
		thiefKnifeDown = new Animation(1f, new TextureRegion[] {getGameObject("player-knife-d")});
		
		
		pharaoWalkFront = new Animation(0.15f, new TextureRegion[] {
				getGameObject("pharao-f1"),
				getGameObject("pharao-f2"), });
		pharaoWalkBack = new Animation(0.15f, new TextureRegion[] {
				getGameObject("pharao-b1"),
				getGameObject("pharao-b2"), });
		pharaoWalkLeft = new Animation(0.2f, flipRegionsHorizontally(new TextureRegion[] {
				getGameObject("pharao-r1"),
				getGameObject("pharao-r2"), }));
		pharaoWalkRight = new Animation(0.2f,
				new TextureRegion[] { getGameObject("pharao-r1"),
						getGameObject("pharao-r2"), });
		pharaoDying = new Animation(0.3f, new TextureRegion[] {
				getGameObject("pharao-dead"),});
		pharaoDying.setPlayMode(Animation.NORMAL);

		pharaoFalling = new Animation(0.3f, new TextureRegion[] {
				 getGameObject("empty") });
		pharaoFalling.setPlayMode(Animation.NORMAL);
		
		pharaoFireLeft = new Animation(0.1f, new TextureRegion[] {getGameObject("fire-l1"), getGameObject("fire-l2")});
		pharaoFireLeft.setPlayMode(Animation.LOOP);
		pharaoFireRight = new Animation(0.1f, flipRegionsHorizontally(
				new TextureRegion[] {getGameObject("fire-l1"), getGameObject("fire-l2")}));
		pharaoFireRight.setPlayMode(Animation.LOOP);
		pharaoFireDown = new Animation(0.1f, new TextureRegion[] {getGameObject("fire-f1"), getGameObject("fire-f2")});
		pharaoFireDown.setPlayMode(Animation.LOOP);
		pharaoFireUp = new Animation(0.1f, new TextureRegion[] {getGameObject("fire-b1"), getGameObject("fire-b2")});
		pharaoFireUp.setPlayMode(Animation.LOOP);
		
		keyRight = new Animation(0.1f, new TextureRegion[] {getGameObject("key-right")});
		keyLeft = new Animation(0.1f,new TextureRegion[] {getGameObject("key-left")});
		

		spiderFront = new Animation(0.2f, new TextureRegion[] {
				getGameObject("spider-front-1"),
				getGameObject("spider-front-2") });
		kingSpiderFront = new Animation(0.2f, new TextureRegion[] {
				getGameObject("king-spider-front-1"),
				getGameObject("king-spider-front-2") });

		hitTarget = new Animation(0.1f, new TextureRegion[] {
				getGameObject("hit-1"), getGameObject("hit-2"),
				getGameObject("hit-3"), });

		bomb = new Animation(0.3f, new TextureRegion[] {
				getGameObject("bomb-1"), getGameObject("bomb-2") });
		bomb.setPlayMode(Animation.LOOP);

		sandStreamPool = new ParticleEffectPool(get(PARTICLE_SANDSTREAM,
				ParticleEffect.class), 5, 10);
		sandSmokeRightPool = new ParticleEffectPool(get(
				PARTICLE_SANDSMOKE_RIGHT, ParticleEffect.class), 5, 10);
		sandSmokeLeftPool = new ParticleEffectPool(get(PARTICLE_SANDSMOKE_LEFT,
				ParticleEffect.class), 5, 10);
		sandSmokeUpPool = new ParticleEffectPool(get(PARTICLE_SANDSMOKE_UP,
				ParticleEffect.class), 5, 10);
		sandSmokeDownPool = new ParticleEffectPool(get(PARTICLE_SANDSMOKE_DOWN,
				ParticleEffect.class), 5, 10);
		enemydiePool = new ParticleEffectPool(get(PARTICLE_ENEMY_DIE,
				ParticleEffect.class), 5, 10);
		explosionPool = new ParticleEffectPool(get(PARTICLE_EXPLOSION,
				ParticleEffect.class), 5, 10);
	}

	private static TextureRegion[] flipRegionsHorizontally(
			final TextureRegion[] origRegions) {
		final TextureRegion[] flippedRegions = new TextureRegion[origRegions.length];
		for (int i = 0; i < origRegions.length; i++) {
			flippedRegions[i] = new TextureRegion(origRegions[i]);
			flippedRegions[i].flip(true, false);
		}
		return flippedRegions;
	}

	public static TextureRegion getFullGameObject(String objectName) {
		return get(ATLAS_FILEPATH_OBJECTS_ALL, TextureAtlas.class).findRegion(
				objectName);
	}

	public static TextureRegion getGameObject(String objectName) {
		return new TextureRegion(get(ATLAS_FILEPATH_OBJECTS_ALL,
				TextureAtlas.class).findRegion(objectName), 1, 1, 16, 16);
	}

	public static Sound getGameSound(String soundName) {
		return assetManager.get(soundName, Sound.class);
	}

	public static boolean update() {
		boolean result = assetManager.update();
		System.out.println("AssetManager progress: "
				+ assetManager.getProgress() + " result " + result);
		return result;
	}

	public static <T> T get(String assetFileName, Class<T> type) {
		return assetManager.get(assetFileName, type);
	}

	public static void finishLoading() {
		assetManager.finishLoading();
	}

	public static void clear() {
		assetManager.clear();

		for (int i = 0; i < pieceTemplates.size; i++) {
			pieceTemplates.get(i).getPixmap().dispose();
		}
		pieceTemplates.clear();

		for (int i = 0; i < roomTemplates.size; i++) {
			roomTemplates.get(i).getPixmap().dispose();
		}
		roomTemplates.clear();
	}

	public static PieceTemplate getRandomPieceTemplate() {
		return pieceTemplates.get(MathUtils.random(pieceTemplates.size - 1));
	}

	public static RoomTemplate getRandomRoomTemplate() {
		return roomTemplates.get(MathUtils.random(roomTemplates.size - 1))
				.clone();
	}

}
