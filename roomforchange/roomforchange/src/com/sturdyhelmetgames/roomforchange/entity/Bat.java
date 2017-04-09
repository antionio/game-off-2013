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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.sturdyhelmetgames.roomforchange.assets.Assets;
import com.sturdyhelmetgames.roomforchange.entity.Entity.Direction;
import com.sturdyhelmetgames.roomforchange.level.Level;
import com.sturdyhelmetgames.roomforchange.level.Level.LevelTile;

public class Bat extends Enemy {

	public Bat(float x, float y, Level level) {
		super(x, y, 1f, 1f, level);
		health = 1;
	}

	@Override
	public float getMaxVelocity() {
		return 5f;
	}

	@Override
	public void render(float delta, SpriteBatch batch) {
		if (blinkTick < BLINK_TICK_MAX) {
			super.render(delta, batch);
			Animation animation = null;

			if (direction == Direction.UP) {
				animation = Assets.batWalkFront;
			} else if (direction == Direction.DOWN) {
				animation = Assets.batWalkBack;
			} else if (direction == Direction.RIGHT) {
				animation = Assets.batWalkRight;
			} else if (direction == Direction.LEFT) {
				animation = Assets.batWalkLeft;
			}

			batch.draw(animation.getKeyFrame(stateTime, true), bounds.x - 0.1f,
					bounds.y - 0.1f, width, height);
		}
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

			if (tile1 != null)
				holes[0].set(p1x + 0.4f, p1y + 0.5f, 0.2f, 0.05f);
			else
				holes[0].unset();
			if (tile1 != null && !tile1.type.isNotWall()) {
				r[0].set(p1x, p1y, 1, 1);
			} else {
				r[0].set(-1, -1, 0, 0);
			}

			if (tile2 != null)
				holes[1].set(p2x + 0.4f, p2y + 0.5f, 0.2f, 0.05f);
			else
				holes[1].unset();
			if (tile2 != null && !tile2.type.isNotWall()) {
				r[1].set(p2x, p2y, 1, 1);
			} else {
				r[1].set(-1, -1, 0, 0);
			}

			if (tile3 != null)
				holes[2].set(p3x + 0.4f, p3y + 0.5f, 0.2f, 0.05f);
			else
				holes[2].unset();
			if (tile3 != null && !tile3.type.isNotWall()) {
				r[2].set(p3x, p3y, 1, 1);

			} else {
				r[2].set(-1, -1, 0, 0);
			}

			if (tile4 != null)
				holes[3].set(p4x + 0.4f, p4y + 0.5f, 0.2f, 0.05f);
			else
				holes[3].unset();
			if (tile4 != null && !tile4.type.isNotWall()) {
				r[3].set(p4x, p4y, 1, 1);
			} else {
				r[3].set(-1, -1, 0, 0);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			Gdx.app.log("Creature", "Creature went off screen");
		}
	}
	
	private float ACCEL_MAX = 1f;
	private float MAX_WALK_TIME = 3f;
	private float walkTime;
	private final Vector2 constantAccel = new Vector2();

	@Override
	public void update(float fixedStep) {
		super.update(fixedStep);



			if (walkTime > MAX_WALK_TIME) {
				walkTime = 0;
			}
			if (walkTime == 0) {
				float randomX = MathUtils.random(300);
				if (randomX < 100) {
					constantAccel.x = ACCEL_MAX;
				} else if (randomX > 100 && randomX < 200) {
					constantAccel.x = -ACCEL_MAX;
				}

				float randomY = MathUtils.random(300);
				if (randomY < 100) {
					constantAccel.y = ACCEL_MAX;
				} else if (randomY > 100 && randomY < 200) {
					constantAccel.y = -ACCEL_MAX;
				}
			} else {
				accel.set(constantAccel);
			}

			if (walkTime == 0) {

			}
		walkTime += fixedStep;

		float absVelX = Math.abs(vel.x);
		float absVelY = Math.abs(vel.y);

		if (absVelX >= absVelY) {
			if (vel.x <= 0f) {
				direction = Direction.LEFT;
			} else if (vel.x >= 0f) {
				direction = Direction.RIGHT;
			}
		} else {
			if (vel.y <= 0f) {
				direction = Direction.DOWN;
			} else if (vel.y >= 0f) {
				direction = Direction.UP;
			}
		}
	}

	@Override
	public void hitWallHook() {
		direction = MathUtils.randomBoolean() ? direction.turnLeft()
				: direction.turnRight();
	}

	@Override
	public float getInertia() {
		return 20f;
	}
	
}
