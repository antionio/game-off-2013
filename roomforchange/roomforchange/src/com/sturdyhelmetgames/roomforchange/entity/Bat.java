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
import com.sturdyhelmetgames.roomforchange.assets.Assets;
import com.sturdyhelmetgames.roomforchange.level.Level;
import com.sturdyhelmetgames.roomforchange.level.Level.LevelTile;

public class Bat extends Enemy {

	public Bat(float x, float y, Level level) {
		super(x, y, 1f, 1f, level);
		health = 1;
	}

	@Override
	public float getMaxVelocity() {
		return 0.05f;
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
	public void update(float fixedStep) {
		super.update(fixedStep);

		if (!isDying() && !isDead() && !isFalling() && isNotWalking()) {
			direction = direction.turnRight();
		}
		if (!isDying() && !isDead() && !isFalling()) {
			moveWithAccel(direction);
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
