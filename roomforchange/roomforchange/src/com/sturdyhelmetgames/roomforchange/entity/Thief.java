package com.sturdyhelmetgames.roomforchange.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sturdyhelmetgames.roomforchange.assets.Assets;
import com.sturdyhelmetgames.roomforchange.level.Level;

public class Thief extends Player {

	public Thief(float x, float y, Level level) {
		super(x, y, level);

		this.health = 2;
		this.maxHealth = 2;
	}
	
	public float getMaxVelocity() {
		return 0.06f;
	}
	
	@Override
	public void drawPlayer(SpriteBatch batch)
	{
		Animation animation = null;
		
		if (isFalling()) {
			animation = Assets.thiefFalling;
			batch.draw(animation.getKeyFrame(dyingAnimState),
					bounds.x - 0.1f, bounds.y, width, height + 0.4f);
		} else if (isDying() || isDead()) {
			animation = Assets.thiefDying;
			batch.draw(animation.getKeyFrame(dyingAnimState),
					bounds.x - 0.1f, bounds.y, width, height + 0.4f);
		} else {
			if (direction == Direction.UP) {
				animation = Assets.thiefWalkBack;
			} else if (direction == Direction.DOWN) {
				animation = Assets.thiefWalkFront;
			} else if (direction == Direction.RIGHT) {
				animation = Assets.thiefWalkRight;
			} else if (direction == Direction.LEFT) {
				animation = Assets.thiefWalkLeft;
			}
			if (isNotWalking()) {
				batch.draw(animation.getKeyFrame(0f), bounds.x - 0.1f,
						bounds.y, width, height + 0.4f);
			} else {
				batch.draw(animation.getKeyFrame(stateTime, true),
						bounds.x - 0.1f, bounds.y, width, height + 0.4f);
			}
		}
	}
	
	@Override
	public void drawAttack(SpriteBatch batch) {
	}
	
	private static final float HIT_DISTANCE = 0.5f;
	
	@Override
	public void tryHit() {
		if (!isDying() && !isDead() && !isFalling()) {
			//tryHitTime = 0f;
			hitBounds.x = bounds.x;
			hitBounds.y = bounds.y;
			if (direction == Direction.LEFT) {
				this.level.entities.add(new Knife(bounds.x, bounds.y, Direction.LEFT, level));
			} else if (direction == Direction.RIGHT) {
				this.level.entities.add(new Knife(bounds.x, bounds.y, Direction.RIGHT, level));
			} else if (direction == Direction.UP) {
				this.level.entities.add(new Knife(bounds.x, bounds.y, Direction.UP, level));
			} else if (direction == Direction.DOWN) {
				this.level.entities.add(new Knife(bounds.x, bounds.y, Direction.DOWN, level));
			}

			// double the hit distance for tiles
			if (direction == Direction.LEFT) {
				hitBounds.x -= HIT_DISTANCE;
			} else if (direction == Direction.RIGHT) {
				hitBounds.x += HIT_DISTANCE;
			} else if (direction == Direction.UP) {
				hitBounds.y += HIT_DISTANCE;
			} else if (direction == Direction.DOWN) {
				hitBounds.y -= HIT_DISTANCE;
			}

			tryHitLever((int) hitBounds.x, (int) hitBounds.y);
			tryHitLever((int) hitBounds.x + 1, (int) hitBounds.y);
			tryHitLever((int) hitBounds.x, (int) hitBounds.y + 1);
		}
	}

}
