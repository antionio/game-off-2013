package com.sturdyhelmetgames.roomforchange.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.sturdyhelmetgames.roomforchange.assets.Assets;
import com.sturdyhelmetgames.roomforchange.level.Level;

public class Ray extends UsableItem {

	public Ray(float x, float y, Level level) {
		super(x, y, 0.5f, 0.5f, level);
	}
	
	public void renderGui(int charge, SpriteBatch batch, float posX, float posY){
		final float scale = getScale();
		batch.draw(Assets.getGameObject("perga2"), posX, posY, 0f, 0f, 1f, 1f, scale*0.75f, scale*0.75f, 0f);
		
	}
	
	public void useItem(Player player, Level level){
		float HIT_DISTANCE = 15f;
		Rectangle hitBounds = new Rectangle(0f, 0f, 0.8f, 0.8f);
		
		hitBounds.x = player.bounds.x;
		hitBounds.y = player.bounds.y;
		if (player.direction == Direction.LEFT) {
			hitBounds.x -= HIT_DISTANCE;
		} else if (player.direction == Direction.RIGHT) {
			hitBounds.x += HIT_DISTANCE;
		} else if (player.direction == Direction.UP) {
			hitBounds.y += HIT_DISTANCE;
		} else if (player.direction == Direction.DOWN) {
			hitBounds.y -= HIT_DISTANCE;
		}

		for (int i = 0; i < level.entities.size; i++) {
			final Entity entity = level.entities.get(i);
			if (!(entity instanceof Player))
			entity.state = EntityState.DEAD;
		}
	}

	@Override
	public void render(float delta, SpriteBatch batch) {
		super.render(delta, batch);

		// calculate scale
		final float scale = getScale();
			batch.draw(Assets.getGameObject("perga2"), bounds.x, bounds.y - 0.65f
					+ zz, 0f, 0f, 1f, 1f, scale, scale, 0f);
	}

	@Override
	public void collectItem() {
		super.collectItem();
		level.player.pickupItem(this);
		this.aliveTick = ALIVE_TIME_MAX;
	}

}
