package com.sturdyhelmetgames.roomforchange.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sturdyhelmetgames.roomforchange.level.Level;

public class UsableItem extends Item {

	public UsableItem(float x, float y, float width, float height, Level level) {
		super(x, y, width, height, level);
	}
	
	public void useItem(Player player, Level level){
		
	}
	
	public void renderGui(int charge, SpriteBatch batch, float posX, float posY){
		
	}

}
