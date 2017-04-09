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
package com.sturdyhelmetgames.roomforchange.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.sturdyhelmetgames.roomforchange.RandomUtil;
import com.sturdyhelmetgames.roomforchange.assets.Assets;
import com.sturdyhelmetgames.roomforchange.level.Level;
import com.sturdyhelmetgames.roomforchange.level.Level.LevelTile;

public class Enemy extends Entity {

	protected int health;

	public Enemy(float x, float y, float width, float height, Level level) {
		super(x, y, width, height, level);
	}

	@Override
	public void update(float fixedStep) {
		super.update(fixedStep);

		if (pause <= 0f) {
			if (state == EntityState.DYING) {
				level.addParticleEffect(Assets.PARTICLE_ENEMY_DIE, bounds.x
						+ width / 2, bounds.y + height / 2);
				state = EntityState.DEAD;
				final int random = RandomUtil.random(100);
				if (random < 40) {
					level.entities.add(new Heart(bounds.x, bounds.y, level));
				} else if (random < 50) {
					level.entities.add(new Bomb(bounds.x, bounds.y, level));
				} else if (random < 100) {
					final int rand = RandomUtil.random(15);
					if (rand == 1 || rand == 2|| rand == 3){
						level.entities.add(new Shield(bounds.x, bounds.y, level));
					} else if (rand == 4 || rand == 5 || rand == 6) {
						level.entities.add(new Aura(bounds.x, bounds.y, level));
					} else if (rand == 7) {
						level.entities.add(new Ray(bounds.x, bounds.y, level));
					}
				}
			}

			if (bounds.overlaps(level.player.bounds)) {
				level.player.takeDamage();
			}
		}
	}

	public void takeDamage() {
		if (pause <= 0f) {
			health--;
			Assets.getGameSound(Assets.SOUND_HIT).play(0.5f);
			if (health <= 0 && !isDying() && !isDead()) {
				state = EntityState.DYING;
				Assets.getGameSound(Assets.SOUND_ENEMYDIE).play(0.5f);
			} else {
				pause = INVULNERABLE_TIME_MIN;
				invulnerableTick = INVULNERABLE_TIME_MIN;
			}
		}
	}

	@Override
	public void hit(Rectangle hitBounds) {
		if (hitBounds.overlaps(bounds)) {
			takeDamage();
		}
	}

	@Override
	protected void fall() {
		// do nothing
	}

	@Override
	protected void fetchCollidableRects() {
		int p1x = (int) bounds.x;
		int p1y = (int) Math.floor(bounds.y);
		int p2x = (int) (bounds.x + bounds.width);
		int p2y = (int) Math.floor(bounds.y);
		int p3x = (int) (bounds.x + bounds.width);
		int p3y = (int) (bounds.y + bounds.height);
		int p4x = (int) bounds.x;
		int p4y = (int) (bounds.y + bounds.height);

		try {
			LevelTile tile1 = null;
			if (level.getTiles().length >= p1x && p1x >= 0
					&& level.getTiles()[p1x].length >= p1y && p1y >= 0)
				tile1 = level.getTiles()[p1x][p1y];
			LevelTile tile2 = null;
			if (level.getTiles().length >= p2x && p1x >= 0
					&& level.getTiles()[p2x].length >= p2y && p2y >= 0)
				tile2 = level.getTiles()[p2x][p2y];
			LevelTile tile3 = null;
			if (level.getTiles().length >= p3x && p3x >= 0
					&& level.getTiles()[p3x].length >= p3y && p3y >= 0)
				tile3 = level.getTiles()[p3x][p3y];
			LevelTile tile4 = null;
			if (level.getTiles().length >= p4x && p4x >= 0
					&& level.getTiles()[p4x].length >= p4y && p4y >= 0)
				tile4 = level.getTiles()[p4x][p4y];

			holes[0].unset();
			if (tile1 != null && (tile1.isCollidable() || tile1.isHole())) {
				r[0].set(p1x, p1y, 1, 1);
			} else {
				r[0].set(-1, -1, 0, 0);
			}

			holes[1].unset();
			if (tile2 != null && (tile2.isCollidable() || tile2.isHole())) {
				r[1].set(p2x, p2y, 1, 1);
			} else {
				r[1].set(-1, -1, 0, 0);
			}

			holes[2].unset();
			if (tile3 != null && (tile3.isCollidable() || tile3.isHole())) {
				r[2].set(p3x, p3y, 1, 1);

			} else {
				r[2].set(-1, -1, 0, 0);
			}

			holes[3].unset();
			if (tile4 != null && (tile4.isCollidable() || tile4.isHole())) {
				r[3].set(p4x, p4y, 1, 1);
			} else {
				r[3].set(-1, -1, 0, 0);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			Gdx.app.log("Creature", "Creature went off screen");
		}
	}

}
