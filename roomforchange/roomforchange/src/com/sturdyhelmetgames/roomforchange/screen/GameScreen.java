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
package com.sturdyhelmetgames.roomforchange.screen;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Quad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.sturdyhelmetgames.roomforchange.RandomUtil;
import com.sturdyhelmetgames.roomforchange.RoomForChangeGame;
import com.sturdyhelmetgames.roomforchange.assets.Assets;
import com.sturdyhelmetgames.roomforchange.assets.FontBig;
import com.sturdyhelmetgames.roomforchange.entity.Entity;
import com.sturdyhelmetgames.roomforchange.entity.Entity.Direction;
import com.sturdyhelmetgames.roomforchange.entity.Entity.HoleFallWrapper;
import com.sturdyhelmetgames.roomforchange.entity.Player;
import com.sturdyhelmetgames.roomforchange.level.Level;
import com.sturdyhelmetgames.roomforchange.tween.Vector3Accessor;
import com.sturdyhelmetgames.roomforchange.util.LabyrinthUtil;

public class GameScreen extends Basic2DScreen {

	public static final int SCALE = 20;
	private OrthographicCamera cameraMiniMap;
	private SpriteBatch batchMiniMap;
	public ScreenQuake screenQuake;
	public final Vector2 currentCamPosition = new Vector2();
	public final TweenManager cameraTweenManager = new TweenManager();
	public final FontBig font = new FontBig(FontBig.FONT_COLOR_BLACK);

	private ShapeRenderer shapeRenderer = new ShapeRenderer();

	private Level level;

	public GameScreen() {
		super();
		screenQuake = new ScreenQuake(camera);
	}

	public GameScreen(RoomForChangeGame game) {
		super(game, 12, 8);
		screenQuake = new ScreenQuake(camera);

		camera.position.set(6f, 4f, 0f);
		camera.update();

		// setup our mini map camera
		cameraMiniMap = new OrthographicCamera(12, 8);
		cameraMiniMap.zoom = SCALE;
		cameraMiniMap.position.set(-70f, 80f, 0f);
		cameraMiniMap.update();
		batchMiniMap = new SpriteBatch();

		level = LabyrinthUtil.generateLabyrinth(4, 4, this);
	}

	@Override
	protected void updateScreen(float fixedStep) {
		processKeys();

		level.update(fixedStep);

		final Vector2 currentPiecePos = level
				.findCurrentPieceRelativeToMapPosition();
		if (!currentPiecePos.epsilonEquals(currentCamPosition, 0.1f)) {
			Tween.to(camera.position, Vector3Accessor.POSITION_XY, 0.7f)
					.target(currentPiecePos.x + 6f, currentPiecePos.y + 4f, 0f)
					.ease(Quad.INOUT).start(cameraTweenManager);
			currentCamPosition.set(currentPiecePos);
			level.moveToAnotherPieceHook();
		}
		camera.update();

		screenQuake.update(fixedStep);
		cameraTweenManager.update(fixedStep);
	}

	@Override
	public void renderScreen(float delta) {
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		level.render(delta, spriteBatch, false);

		final Color originalColor = spriteBatch.getColor();
		spriteBatch.setColor(1f, 1f, 1f, 1f);
		spriteBatch.draw(Assets.getFullGameObject("darkness"),
				camera.position.x - 7f, camera.position.y - 5f, 14f, 10f);
		spriteBatch.setColor(originalColor);

		// calculate heart positions
		final float heartPositionX = camera.position.x - 5.9f;
		final float heartPositionY = camera.position.y + 2.9f;
		// draw player health
		final Player player = level.player;
		if (player != null) {
			for (int i = 0; i < player.maxHealth; i++) {
				final float x = heartPositionX + i * 0.55f;
				if (i < player.health) {
					spriteBatch.draw(Assets.getGameObject("heart-full"), x,
							heartPositionY, 1f, 1f);
				} else {
					spriteBatch.draw(Assets.getGameObject("heart-empty"), x,
							heartPositionY, 1f, 1f);
				}
			}
		}

		final float bombPosX = camera.position.x - 5.9f;
		final float bombPosY = camera.position.y + 3f;
		spriteBatch.draw(Assets.getGameObject("bomb-3"), bombPosX, bombPosY,
				0.5f, 0.5f);
		font.draw(spriteBatch, "x" + level.player.bombs, bombPosX + 0.6f,
				bombPosY);

		final float gemPosX = camera.position.x + 4f;
		final float gemPosY = camera.position.y + 3.4f;

		spriteBatch.setColor(1f, 1f, 1f, 0.5f);
		spriteBatch.draw(Assets.getGameObject("black"), gemPosX - 0.05f,
				gemPosY - 0.05f, 3f, 1f);
		spriteBatch.setColor(originalColor);

		if (player.gotGem) {
			spriteBatch.draw(Assets.getGameObject("gem"), gemPosX, gemPosY,
					0.5f, 0.5f);
		} else {
			spriteBatch.setColor(0f, 0f, 0f, 1f);
			spriteBatch.draw(Assets.getGameObject("gem"), gemPosX, gemPosY,
					0.5f, 0.5f);
			spriteBatch.setColor(originalColor);
		}
		final float scrollPosX = gemPosX + 0.75f;
		final float scrollPosY = gemPosY;
		if (player.gotScroll) {
			spriteBatch.draw(Assets.getGameObject("scroll"), scrollPosX,
					scrollPosY, 0.5f, 0.5f);
		} else {
			spriteBatch.setColor(0f, 0f, 0f, 1f);
			spriteBatch.draw(Assets.getGameObject("scroll"), scrollPosX,
					scrollPosY, 0.5f, 0.5f);
			spriteBatch.setColor(originalColor);
		}
		final float talismanPosX = scrollPosX + 0.75f;
		final float talismanPosY = scrollPosY;
		if (player.gotTalisman) {
			spriteBatch.draw(Assets.getGameObject("talisman"), talismanPosX,
					talismanPosY, 0.5f, 0.5f);
		} else {
			spriteBatch.setColor(0f, 0f, 0f, 1f);
			spriteBatch.draw(Assets.getGameObject("talisman"), talismanPosX,
					talismanPosY, 0.5f, 0.5f);
			spriteBatch.setColor(originalColor);
		}
		
		if (player.usableItem != null){
			final float itemPositionX = camera.position.x + 0.25f - 6f;
			final float itemPositionY = camera.position.y + 0.25f - 4f;
			player.usableItem.renderGui(0, spriteBatch, itemPositionX, itemPositionY);
		}

		spriteBatch.end();

		batchMiniMap.setProjectionMatrix(cameraMiniMap.combined);
		batchMiniMap.begin();
		final Color origColor = batchMiniMap.getColor();
		batchMiniMap.setColor(1f, 1f, 1f, 0.5f);
		batchMiniMap.draw(Assets.getGameObject("black"), 0f, 0f, 48f, 32f);
		batchMiniMap.setColor(origColor);
		level.render(delta, batchMiniMap, true);
		batchMiniMap.end();

		if (game.isDebug()) {
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);

			for (int i = 0; i < level.entities.size; i++) {
				shapeRenderer.setColor(Color.WHITE);
				final Entity entity = level.entities.get(i);
				shapeRenderer.rect(entity.bounds.x, entity.bounds.y,
						entity.bounds.width, entity.bounds.height);

				shapeRenderer.setColor(Color.RED);
				for (int i2 = 0; i2 < entity.holes.length; i2++) {
					HoleFallWrapper hole = entity.holes[i2];
					shapeRenderer.rect(hole.bounds.x, hole.bounds.y,
							hole.bounds.width, hole.bounds.height);
				}
			}

			shapeRenderer.rect(level.player.hitBounds.x,
					level.player.hitBounds.y, level.player.hitBounds.width,
					level.player.hitBounds.height);

			shapeRenderer.end();
		}
	}

	protected void processKeys() {
		if (!paused && level.player.isAlive()) {
			// process player movement keys
			if (Gdx.input.isKeyPressed(Keys.UP)) {
				level.player.moveWithAccel(Direction.UP);
			}
			if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				level.player.moveWithAccel(Direction.DOWN);
			}
			if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				level.player.moveWithAccel(Direction.RIGHT);
			}
			if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				level.player.moveWithAccel(Direction.LEFT);
			}

			if (Gdx.input.isKeyPressed(Keys.MINUS)) {
				camera.zoom += 0.1f;
				camera.update();
			}
			if (Gdx.input.isKeyPressed(Keys.PLUS) || Gdx.input.isKeyPressed(Keys.EQUALS)) {
				camera.zoom -= 0.1f;
				camera.update();
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// if (keycode == Keys.W) {
		// startScreenQuake(Level.UP);
		// }
		// if (keycode == Keys.S) {
		// startScreenQuake(Level.DOWN);
		// }
		// if (keycode == Keys.A) {
		// startScreenQuake(Level.LEFT);
		// }
		// if (keycode == Keys.D) {
		// startScreenQuake(Level.RIGHT);
		// }
		if (keycode == Keys.Z) {
			level.player.tryHit();
		}
		if (keycode == Keys.X) {
			level.player.dropBomb();
		}
		if (keycode == Keys.SHIFT_LEFT){
			level.player.useItem();
		}
		// if (keycode == Keys.K) {
		// level.entities.add(new ExplodingBomb(level.player.bounds.x + 2f,
		// level.player.bounds.y, level));
		// level.addParticleEffect(Assets.PARTICLE_ENEMY_DIE,
		// level.player.bounds.x + 2f, level.player.bounds.y);
		// }
		return super.keyDown(keycode);
	}

	@Override
	public void dispose() {
		super.dispose();
		batchMiniMap.dispose();
	}

	public void updateCameraPos(float xOffset, float yOffset) {
		camera.position.set(camera.position.x + xOffset, camera.position.y
				+ yOffset, 0f);
		camera.update();
	}

	public void startScreenQuake(final int dir) {
		screenQuake.activate(2.8f, new Runnable() {
			@Override
			public void run() {
				level.resumeEntities();
				level.moveLabyrinthPiece(dir);
			}
		});
		Assets.getGameSound(Assets.SOUND_STONEDOOR).play(0.5f, 1.5f, 0f);
		for (int i = 0; i < 5; i++) {
			level.addParticleEffect(Assets.PARTICLE_SANDSTREAM,
					camera.position.x + RandomUtil.bigRangeRandom(6f),
					camera.position.y + 4f);
		}

		if (dir == Level.RIGHT) {
			level.addParticleEffect(Assets.PARTICLE_SANDSMOKE_RIGHT,
					camera.position.x - 6f, camera.position.y + 3f);
			level.addParticleEffect(Assets.PARTICLE_SANDSMOKE_RIGHT,
					camera.position.x - 6f, camera.position.y - 4f);
		} else if (dir == Level.LEFT) {
			level.addParticleEffect(Assets.PARTICLE_SANDSMOKE_LEFT,
					camera.position.x - 6f, camera.position.y + 3f);
			level.addParticleEffect(Assets.PARTICLE_SANDSMOKE_LEFT,
					camera.position.x - 6f, camera.position.y - 4f);
		} else if (dir == Level.UP) {
			level.addParticleEffect(Assets.PARTICLE_SANDSMOKE_UP,
					camera.position.x - 6f, camera.position.y - 3f);
			level.addParticleEffect(Assets.PARTICLE_SANDSMOKE_UP,
					camera.position.x + 6f, camera.position.y - 3f);
		} else if (dir == Level.DOWN) {
			level.addParticleEffect(Assets.PARTICLE_SANDSMOKE_DOWN,
					camera.position.x - 6f, camera.position.y - 3f);
			level.addParticleEffect(Assets.PARTICLE_SANDSMOKE_DOWN,
					camera.position.x + 6f, camera.position.y - 3f);
		}
		level.pauseEntities();
	}

	public void openLeverScreen() {
		game.setScreen(new LeverScreen(game, this));
	}

	public void gameOver() {
		game.setScreen(new GameOverScreen(game, this));
	}

	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(this);
	}

	public void finishGame() {
		game.setScreen(new WinTheGameScreen(game, this));
	}

}
