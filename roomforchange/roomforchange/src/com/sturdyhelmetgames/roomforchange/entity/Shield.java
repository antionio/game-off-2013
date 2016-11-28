package com.sturdyhelmetgames.roomforchange.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sturdyhelmetgames.roomforchange.assets.Assets;
import com.sturdyhelmetgames.roomforchange.level.Level;

public class Shield extends UsableItem {

	public Shield(float x, float y, Level level) {
		super(x, y, 0.5f, 0.5f, level);
	}
	
	public void renderGui(int charge, SpriteBatch batch, float posX, float posY){
		final float scale = getScale();
		batch.draw(Assets.getGameObject("perga3"), posX, posY, 0f, 0f, 1f, 1f, scale*0.75f, scale*0.75f, 0f);
		
	}
	
	public void useItem(Player player, Level level){
		player.invulnerableTick = 3f;
	}

	@Override
	public void render(float delta, SpriteBatch batch) {
		super.render(delta, batch);

		// calculate scale
		final float scale = getScale();
			batch.draw(Assets.getGameObject("perga3"), bounds.x, bounds.y - 0.65f
					+ zz, 0f, 0f, 1f, 1f, scale, scale, 0f);
	}

	@Override
	public void collectItem() {
		super.collectItem();
		level.player.pickupItem(this);
		this.aliveTick = ALIVE_TIME_MAX;
	}

}
