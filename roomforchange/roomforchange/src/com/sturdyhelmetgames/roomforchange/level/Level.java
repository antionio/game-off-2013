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
package com.sturdyhelmetgames.roomforchange.level;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.sturdyhelmetgames.roomforchange.assets.Assets;
import com.sturdyhelmetgames.roomforchange.entity.Bat;
import com.sturdyhelmetgames.roomforchange.entity.Entity;
import com.sturdyhelmetgames.roomforchange.entity.Exit;
import com.sturdyhelmetgames.roomforchange.entity.Gem;
import com.sturdyhelmetgames.roomforchange.entity.Ghost;
import com.sturdyhelmetgames.roomforchange.entity.KingSpider;
import com.sturdyhelmetgames.roomforchange.entity.Mummy;
import com.sturdyhelmetgames.roomforchange.entity.Player;
import com.sturdyhelmetgames.roomforchange.entity.Scroll;
import com.sturdyhelmetgames.roomforchange.entity.Skeleton;
import com.sturdyhelmetgames.roomforchange.entity.Snake;
import com.sturdyhelmetgames.roomforchange.entity.Spider;
import com.sturdyhelmetgames.roomforchange.entity.Talisman;
import com.sturdyhelmetgames.roomforchange.entity.Zombie;
import com.sturdyhelmetgames.roomforchange.level.LabyrinthPiece.LabyrinthPieceState;
import com.sturdyhelmetgames.roomforchange.screen.GameScreen;
import com.sturdyhelmetgames.roomforchange.util.LabyrinthUtil;

import aurelienribon.tweenengine.TweenManager;

public class Level {

	public final TweenManager entityTweenManager = new TweenManager();
	public final GameScreen gameScreen;
	private LabyrinthPiece[][] labyrinth;
	private LevelTile[][] tiles;

	private final Vector2 currentPiecePos = new Vector2();
	private final Vector2 piecePos = new Vector2();

	public Player player;
	public final Array<Entity> entities = new Array<Entity>();
	public final Array<PooledEffect> particleEffects = new Array<PooledEffect>();

	public boolean pauseEntities = false;

	public Level(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	public Vector2 findPiecePos(LabyrinthPiece pieceToFind) {
		for (int x = 0; x < labyrinth.length; x++) {
			final LabyrinthPiece[] pieces = labyrinth[x];
			for (int y = 0; y < pieces.length; y++) {
				if (pieceToFind == pieces[y]) {
					return piecePos.set(x, y);
				}
			}
		}
		throw new RuntimeException("Labyrinth piece not found!");
	}

	public Vector2 findCurrentPiecePos() {
		for (int x = 0; x < labyrinth[0].length; x++) {
			final LabyrinthPiece[] pieceList = labyrinth[x];
			for (int y = 0; y < pieceList.length; y++) {
				final LabyrinthPiece piece = pieceList[y];
				if (player.bounds.overlaps(piece.getBounds())) {
					currentPiecePos.set(x, y);
					piece.state = LabyrinthPieceState.LIGHTS_ON;
				} else if (piece.state == LabyrinthPieceState.LIGHTS_ON) {
					piece.state = LabyrinthPieceState.LIGHTS_DIMMED;
				}
			}
		}
		return currentPiecePos;
	}

	public Vector2 findCurrentPieceRelativeToMapPosition() {
		final Vector2 currentPiecePos = findCurrentPiecePos();
		currentPiecePos.x *= LabyrinthPiece.WIDTH;
		currentPiecePos.y *= LabyrinthPiece.HEIGHT;
		return currentPiecePos;
	}

	public LabyrinthPiece[][] getLabyrinth() {
		return labyrinth;
	}

	public void setLabyrinth(LabyrinthPiece[][] labyrinth) {
		this.labyrinth = labyrinth;
	}

	public LevelTile[][] getTiles() {
		return tiles;
	}

	public void setTiles(LevelTile[][] tiles) {
		this.tiles = tiles;
	}

	public static enum LevelTileType {
		GROUND(false, "ground-1"), WALL_CORNER(true, "brick-corner"), WALL_LEFT(
				true, "brick-left"), WALL_RIGHT(true, "brick-right"), WALL_FRONT(
				true, "brick-front"), WALL_BACK(true, "brick-back"), DOOR(true,
				"door"), EXIT(false, "exit"), HOLE_1(false, "hole-1"), HOLE_2(
				false, "hole-2"), HOLE_3(false, "hole-3"), HOLE_4(false,
				"hole-4"), HOLE_5(false, "hole-5"), HOLE_6(false, "hole-6"), HOLE_7(
				false, "hole-7"), ROCK(true, "rock"), LEVER(true, "lever");

		private final boolean collidable;
		private final String objectName;

		private LevelTileType(boolean collidable, String objectName) {
			this.collidable = collidable;
			this.objectName = objectName;
		}

		public String getObjectName() {
			return objectName;
		}

		public boolean isExit() {
			return this == EXIT;
		}

		public boolean isNotWall() {
			return this != WALL_CORNER && this != WALL_BACK
					&& this != WALL_FRONT && this != WALL_LEFT
					&& this != WALL_RIGHT;
		}

		public boolean isCollidable() {
			return collidable;
		}

		public boolean isHole() {
			return this == HOLE_1;
		}
	}

	public static class LevelTile {
		public final LabyrinthPiece parent;
		public final LevelTileType type;

		public LevelTile(LabyrinthPiece parent, LevelTileType type) {
			if (parent == null || type == null) {
				throw new RuntimeException(
						"Cannot create LevelTile without parent Level or type!");
			}
			this.parent = parent;
			this.type = type;
		}

		public void render(float delta, SpriteBatch batch, float x, float y,
				boolean minimap) {
			if (parent.state == LabyrinthPieceState.LIGHTS_ON) {
				if (!minimap) {
					batch.draw(Assets.getGameObject(type.getObjectName()), x,
							y, 1f, 1f);
				} else {
					batch.draw(Assets.getGameObject(type.getObjectName()), x,
							y, 1f, 1f);
				}
			} else {
				if (parent.state != LabyrinthPieceState.LIGHTS_OFF) {
					batch.draw(Assets.getGameObject(type.getObjectName()), x,
							y, 1f, 1f);
				}
				if (minimap
						&& parent.state == LabyrinthPieceState.LIGHTS_DIMMED) {
					final com.badlogic.gdx.graphics.Color origColor = batch
							.getColor();
					batch.setColor(1f, 1f, 1f, 0.3f);
					batch.draw(Assets.getFullGameObject("black"), x, y, 1f, 1f);
					batch.setColor(origColor);
				}
			}
		}

		public boolean isCollidable() {
			return type.isCollidable();
		}

		public boolean isHole() {
			return type.isHole();
		}
	}

	public void update(float fixedStep) {
		for (int i = 0; i < particleEffects.size; i++) {
			final PooledEffect pooledEffect = particleEffects.get(i);
			pooledEffect.update(fixedStep);
			if (pooledEffect.isComplete()) {
				particleEffects.removeIndex(i);
				pooledEffect.free();
			}
		}
		if (!pauseEntities) {
			entityTweenManager.update(fixedStep);
			for (int i = 0; i < entities.size; i++) {
				final Entity entity = entities.get(i);
				entity.update(fixedStep);
				if (!entity.isAlive() && entity != player) {
					entities.removeIndex(i);
				}
			}
		}
	}

	public void render(float delta, SpriteBatch batch, boolean minimap) {
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
				final LevelTile tile = tiles[x][y];
				tile.render(delta, batch, x, y, minimap);
			}
		}
		for (int i = 0; i < entities.size; i++) {
			final Entity entity = entities.get(i);
			if (entity != player && !minimap)
				entity.render(delta, batch);
		}
		player.render(delta, batch);

		for (int i = 0; i < particleEffects.size; i++) {
			particleEffects.get(i).draw(batch);
		}
	}

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;

	private void moveEntitiesAndCamera(float xOffset, float yOffset) {
		for (int i = 0; i < particleEffects.size; i++) {
			final PooledEffect effect = particleEffects.get(i);
			float x = effect.getEmitters().get(0).getX();
			float y = effect.getEmitters().get(0).getY();
			effect.setPosition(x + xOffset, y + yOffset);
		}
		for (int i = 0; i < entities.size; i++) {
			final Entity entity = entities.get(i);
			entity.bounds.x += xOffset;
			entity.bounds.y += yOffset;
		}
		gameScreen.updateCameraPos(xOffset, yOffset);
	}

	public void moveLabyrinthPiece(int dir) {

		final Vector2 currentPiecePos = findCurrentPiecePos();
		LabyrinthPiece nextPiece = labyrinth[(int) currentPiecePos.x][(int) currentPiecePos.y];

		switch (dir) {
		case (LEFT): {
			int width = labyrinth[0].length;
			int xPos = (int) currentPiecePos.x - 1;
			int yPos = (int) currentPiecePos.y;

			boolean switchOver = false;
			for (int i = 0; i < width; i++) {
				if (xPos < 0) {
					xPos += width;
					switchOver = i == 0;
				}
				LabyrinthPiece currentPiece = nextPiece;
				nextPiece = labyrinth[xPos][yPos];
				labyrinth[xPos][yPos] = currentPiece;
				xPos -= 1;
			}
			if (switchOver) {
				moveEntitiesAndCamera(LabyrinthPiece.WIDTH * (width - 1), 0f);
			} else {
				moveEntitiesAndCamera(-LabyrinthPiece.WIDTH, 0f);
			}
		}
			break;
		case (RIGHT): {
			int width = labyrinth[0].length;
			int xPos = (int) currentPiecePos.x + 1;
			int yPos = (int) currentPiecePos.y;
			boolean switchOver = false;
			for (int i = 0; i < width; i++) {
				if (xPos >= width) {
					xPos -= width;
					switchOver = i == 0;
				}
				LabyrinthPiece currentPiece = nextPiece;
				nextPiece = labyrinth[xPos][yPos];
				labyrinth[xPos][yPos] = currentPiece;
				xPos += 1;
			}
			if (switchOver) {
				moveEntitiesAndCamera(-LabyrinthPiece.WIDTH * (width - 1), 0f);
			} else {
				moveEntitiesAndCamera(LabyrinthPiece.WIDTH, 0f);
			}
		}
			break;
		case (UP): {
			int height = labyrinth.length;
			int xPos = (int) currentPiecePos.x;
			int yPos = (int) currentPiecePos.y + 1;
			boolean switchOver = false;
			for (int i = 0; i < height; i++) {
				if (yPos >= height) {
					yPos -= height;
					if (i == 0) {
						switchOver = true;
					}
				}
				LabyrinthPiece currentPiece = nextPiece;
				nextPiece = labyrinth[xPos][yPos];
				labyrinth[xPos][yPos] = currentPiece;
				yPos += 1;
			}
			if (switchOver) {
				moveEntitiesAndCamera(0f, -LabyrinthPiece.HEIGHT * (height - 1));
			} else {
				moveEntitiesAndCamera(0f, LabyrinthPiece.HEIGHT);
			}
		}
			break;
		case (DOWN): {
			int height = labyrinth.length;
			int xPos = (int) currentPiecePos.x;
			int yPos = (int) currentPiecePos.y - 1;
			boolean switchOver = false;
			for (int i = 0; i < height; i++) {
				if (yPos < 0) {
					yPos += height;
					switchOver = i == 0;
				}
				LabyrinthPiece currentPiece = nextPiece;
				nextPiece = labyrinth[xPos][yPos];
				labyrinth[xPos][yPos] = currentPiece;
				yPos -= 1;
			}
			if (switchOver) {
				moveEntitiesAndCamera(0f, LabyrinthPiece.HEIGHT * (height - 1));
			} else {
				moveEntitiesAndCamera(0f, -LabyrinthPiece.HEIGHT);
			}
		}
			break;
		}
		LabyrinthUtil.updateLabyrinthTiles(this);
		gameScreen.currentCamPosition
				.set(findCurrentPieceRelativeToMapPosition());
	}

	public void pauseEntities() {
		pauseEntities = true;
	}

	public void resumeEntities() {
		pauseEntities = false;
	}

	private static final Array<Entity> toBeRemoved = new Array<Entity>();

	/**
	 * Do stuff that happens when the player moves to another piece.
	 */
	public void moveToAnotherPieceHook() {
		// clean up old entities old-school style

		for (int i = 0; i < entities.size; i++) {
			final Entity entity = entities.get(i);
			if (entity != player) {
				toBeRemoved.add(entity);
			}
		}
		entities.removeAll(toBeRemoved, true);
		toBeRemoved.clear();

		final Vector2 currentPiecePos = findCurrentPiecePos();
		final LabyrinthPiece currentPiece = labyrinth[(int) currentPiecePos.x][(int) currentPiecePos.y];
		final Vector2 currentPieceRelativePos = findCurrentPieceRelativeToMapPosition();
		spawnNewEnemiesAround(currentPiece, currentPieceRelativePos);
	}

	private void spawnNewEnemiesAround(LabyrinthPiece piece,
			Vector2 currentPieceRelativePos) {
		final RoomTemplate template = piece.roomTemplate;
		for (int i = 0; i < template.getEntityTypes().size; i++) {

			final float randomX = currentPieceRelativePos.x + 1
					+ MathUtils.random(9);
			final float randomY = currentPieceRelativePos.y + 1
					+ MathUtils.random(5);
			final Class<?> entityType = template.getEntityTypes().get(i);
						
			if (entityType == Mummy.class) {
				entities.add(new Mummy(randomX, randomY, this));
			} else if (entityType == Snake.class) {
				entities.add(new Snake(randomX, randomY, this));
			} else if (entityType == Spider.class) {
				entities.add(new Spider(randomX, randomY, this));
			} else if (entityType == KingSpider.class) {
				entities.add(new KingSpider(randomX, randomY, this));
			} else if (entityType == Bat.class) {
				entities.add(new Bat(randomX, randomY, this));
			} else if (entityType == Zombie.class) {
				entities.add(new Zombie(randomX, randomY, this));
			} else if (entityType == Skeleton.class) {
				entities.add(new Skeleton(randomX, randomY, this));
			} else if (entityType == Ghost.class) {
				entities.add(new Ghost(randomX, randomY, this));
			}
				
		}

		final float randomX = currentPieceRelativePos.x + 2
				+ MathUtils.random(7);
		final float randomY = currentPieceRelativePos.y + 2
				+ MathUtils.random(4);
		if (template.treasureType != null) {
			if (template.treasureType == Scroll.class && !player.gotScroll) {
				entities.add(new Scroll(randomX, randomY, this));
			} else if (template.treasureType == Talisman.class
					&& !player.gotTalisman) {
				entities.add(new Talisman(randomX, randomY, this));
			} else if (template.treasureType == Gem.class && !player.gotGem) {
				entities.add(new Gem(randomX, randomY, this));
			}
		}
		if (template.hasExit) {
			entities.add(new Exit(currentPieceRelativePos.x + 3,
					currentPieceRelativePos.y + 1, this));
		}
	}

	public void addParticleEffect(String name, float x, float y) {
		PooledEffect effect = null;
		if (name.equals(Assets.PARTICLE_SANDSMOKE_RIGHT)) {
			effect = Assets.sandSmokeRightPool.obtain();
		} else if (name.equals(Assets.PARTICLE_SANDSMOKE_LEFT)) {
			effect = Assets.sandSmokeLeftPool.obtain();
		} else if (name.equals(Assets.PARTICLE_SANDSMOKE_UP)) {
			effect = Assets.sandSmokeUpPool.obtain();
		} else if (name.equals(Assets.PARTICLE_SANDSMOKE_DOWN)) {
			effect = Assets.sandSmokeDownPool.obtain();
		} else if (name.equals(Assets.PARTICLE_SANDSTREAM)) {
			effect = Assets.sandStreamPool.obtain();
		} else if (name.equals(Assets.PARTICLE_ENEMY_DIE)) {
			effect = Assets.enemydiePool.obtain();
		} else if (name.equals(Assets.PARTICLE_EXPLOSION)) {
			effect = Assets.explosionPool.obtain();
		}
		if (effect != null) {
			effect.setPosition(x, y);
			particleEffects.add(effect);
		}
	}
}
