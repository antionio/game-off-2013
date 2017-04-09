package com.sturdyhelmetgames.roomforchange.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.sturdyhelmetgames.roomforchange.assets.Assets;
import com.sturdyhelmetgames.roomforchange.level.Level;

public class Knife extends Entity {
	
	protected Direction direction;

	public Knife(float x, float y, Direction dir, Level level) {
		super(x, y, 0.5f, 0.5f, level);
		this.direction = dir;
	}
	
	@Override
	public float getMaxVelocity() {
		return 0.15f;
	}
	
	@Override
	public void update(float fixedStep) {
		super.update(fixedStep);
		
		if (blinkTick == BLINK_TICK_MAX) {
			this.state = EntityState.DEAD;
		}
		
		if (!isDying() && !isDead() && !isFalling()) {
			moveWithAccel(direction);
		}
		
		for (int i = 0; i < level.entities.size; i++) {
			final Entity entity = level.entities.get(i);
			
			if (entity instanceof Enemy && entity.isAlive())
			{
				entity.hit(this.bounds);
				//this.state = EntityState.DEAD;
				
			}
		}
	}
	
	@Override
	public void hitWallHook() {
		invulnerableTick = 500f;
	}
	
	@Override
	public void render(float delta, SpriteBatch batch) {

		if (blinkTick < BLINK_TICK_MAX) {
			super.render(delta, batch);
			
			Animation animation = null;
			
			if (direction == Direction.UP) {
				animation = Assets.thiefKnifeUp;
			} else if (direction == Direction.DOWN) {
				animation = Assets.thiefKnifeDown;
			} else if (direction == Direction.RIGHT) {
				animation = Assets.thiefKnifeRight;
			} else if (direction == Direction.LEFT) {
				animation = Assets.thiefKnifeLeft;
			}
		
			if (animation != null)
				batch.draw(animation.getKeyFrame(0f), bounds.x, bounds.y, width, height);
		}

	}
	
	@Override
	protected void tryMove() {
		bounds.y += vel.y;
		fetchCollidableRects();

		for (int i = 0; i < r.length; i++) {
			Rectangle rect = r[i];
			if (bounds.overlaps(rect)) {
				if (vel.y < 0) {
					bounds.y = rect.y + rect.height + 0.01f;
				} else
					bounds.y = rect.y - bounds.height - 0.01f;
				vel.y = 0;
				hitWallHook();
			}
		}

		bounds.x += vel.x;
		fetchCollidableRects();
		
		for (int i = 0; i < r.length; i++) {
			Rectangle rect = r[i];
			if (bounds.overlaps(rect)) {
				if (vel.x < 0)
					bounds.x = rect.x + rect.width + 0.01f;
				else
					bounds.x = rect.x - bounds.width - 0.01f;
				vel.x = 0;
				hitWallHook();
			}
		}
	}

}
