package com.sturdyhelmetgames.roomforchange.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sturdyhelmetgames.roomforchange.level.Level;

public class UsableItem extends Item {

	public UsableItem(float x, float y, float width, float height, Level level) {
		super(x, y, width, height, level);
	}
	
	/**
	 * Method called when the player uses the item, before consume.
	 */
	public void useItem(Player player, Level level){
		
	}
	
	/**
	 * Method called during GUI rendering.
	 * Used to draw the collected item in the interface.
	 */
	public void renderGui(int charge, SpriteBatch batch, float posX, float posY){
		
	}

}
