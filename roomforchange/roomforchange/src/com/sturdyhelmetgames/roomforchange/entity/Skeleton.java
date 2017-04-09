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

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.sturdyhelmetgames.roomforchange.assets.Assets;
import com.sturdyhelmetgames.roomforchange.level.Level;

public class Skeleton extends Enemy {

	private float ACCEL_MAX = 1f;
	private float MAX_WALK_TIME = 3f;
	private float walkTime;
	private final Vector2 constantAccel = new Vector2();

	public Skeleton(float x, float y, Level level) {
		super(x, y, 1f, 0.6f, level);
		state = EntityState.WALKING;
		health = 5;
	}

	@Override
	public float getMaxVelocity() {
		return 0.01f;
	}

	@Override
	public void render(float delta, SpriteBatch batch) {
		if (blinkTick < BLINK_TICK_MAX) {
			super.render(delta, batch);
			Animation animation = null;

			if (direction == Direction.UP) {
				animation = Assets.skeletonWalkFront;
			} else if (direction == Direction.DOWN) {
				animation = Assets.skeletonWalkBack;
			} else if (direction == Direction.RIGHT) {
				animation = Assets.skeletonWalkRight;
			} else if (direction == Direction.LEFT) {
				animation = Assets.skeletonWalkLeft;
			}

			batch.draw(animation.getKeyFrame(stateTime, true), bounds.x - 0.1f,
					bounds.y - 0.1f, width, height + 0.4f);
		}
	}

	@Override
	public float getInertia() {
		return 20f;
	}

	private final Vector2 playerPos = new Vector2();
	private final Vector2 mummyPos = new Vector2();

	@Override
	public void update(float fixedStep) {
		super.update(fixedStep);

		level.player.bounds.getPosition(playerPos);
		bounds.getPosition(mummyPos);

		boolean mummyAttacking = playerPos.dst2(mummyPos) < 10f;
		if (mummyAttacking) {
			accel.set(playerPos.sub(mummyPos).scl(0.6f));
		} else {
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
		}
		walkTime += fixedStep;

		float absVelX = Math.abs(vel.x);
		float absVelY = Math.abs(vel.y);

		if (absVelX > absVelY) {
			if (vel.x <= 0f) {
				direction = Direction.LEFT;
			} else if (vel.x >= 0f) {
				direction = Direction.RIGHT;
			}
		} else if (absVelX < absVelY) {
			if (vel.y <= 0f) {
				direction = Direction.DOWN;
			} else if (vel.y >= 0f) {
				direction = Direction.UP;
			}
		} else {
			if (direction == Direction.LEFT || direction == Direction.RIGHT) {
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
	}

}
