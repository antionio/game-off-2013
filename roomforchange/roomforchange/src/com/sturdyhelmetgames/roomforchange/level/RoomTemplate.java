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

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.sturdyhelmetgames.roomforchange.entity.Bat;
import com.sturdyhelmetgames.roomforchange.entity.Ghost;
import com.sturdyhelmetgames.roomforchange.entity.KingSpider;
import com.sturdyhelmetgames.roomforchange.entity.Mummy;
import com.sturdyhelmetgames.roomforchange.entity.Skeleton;
import com.sturdyhelmetgames.roomforchange.entity.Snake;
import com.sturdyhelmetgames.roomforchange.entity.Spider;
import com.sturdyhelmetgames.roomforchange.entity.Zombie;
import com.sturdyhelmetgames.roomforchange.level.Level.LevelTileType;

public class RoomTemplate {

	public static final int WIDTH = 10;
	public static final int HEIGHT = 6;
	private final LevelTileType[][] tileTypes;
	private final Pixmap pixmap;

	private final Array<Class<?>> entityTypes = new Array<Class<?>>();
	public Class<?> treasureType;
	public boolean hasExit = false;

	public Pixmap getPixmap() {
		return pixmap;
	}

	public LevelTileType[][] getTileTypes() {
		return tileTypes;
	}

	public Array<Class<?>> getEntityTypes() {
		return entityTypes;
	}

	public RoomTemplate(Pixmap pixmap) {
		this.pixmap = pixmap;
		tileTypes = new LevelTileType[WIDTH][HEIGHT];
		if (pixmap.getWidth() > WIDTH || pixmap.getHeight() > HEIGHT) {
			throw new IllegalArgumentException(
					"Template is too big! Should be 10x6px.");
		}

		int yFlip = 0;
		for (int x = 0; x < pixmap.getWidth(); x++) {
			for (int y = pixmap.getHeight() - 1; y >= 0; y--) {
				int pixel = pixmap.getPixel(x, yFlip);
				if (pixel == Color.BLACK) {
					tileTypes[x][y] = LevelTileType.WALL_FRONT;
				} else if (pixel == Color.RED) {
					tileTypes[x][y] = LevelTileType.HOLE_1;
				} else if (pixel == Color.BLUE) {
					tileTypes[x][y] = LevelTileType.ROCK;
				} else if (pixel == Color.GREEN) {
					tileTypes[x][y] = LevelTileType.LEVER;
				} else {
					tileTypes[x][y] = LevelTileType.GROUND;
				}
				yFlip++;
			}
			yFlip = 0;
		}

		// randomize room difficulty level
		int difficultyLevel = MathUtils.random(3)+2;
		if (difficultyLevel == 1) {
			difficultyLevel = 1;
		}
		for (int i = 0; i < difficultyLevel; i++) {
			final int enemyType = MathUtils.random(100);

			if (enemyType < 15) {
				entityTypes.add(Snake.class);
			} else if (enemyType < 25) {
				entityTypes.add(Mummy.class);
			} else if (enemyType < 40) {
				entityTypes.add(Spider.class);
			} else if (enemyType < 60) {
				entityTypes.add(Bat.class);
			} else if (enemyType < 75) {
				entityTypes.add(Zombie.class);
			} else if (enemyType < 90) {
				entityTypes.add(Skeleton.class);
			} else if (enemyType < 97) {
				entityTypes.add(Ghost.class);
			} else {
				entityTypes.add(KingSpider.class);
			}
		}
	}

	public RoomTemplate clone() {
		RoomTemplate template = new RoomTemplate(pixmap);
		return template;
	}

}
