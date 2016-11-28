package com.sturdyhelmetgames.roomforchange.entity;

import junit.framework.Assert;

import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sturdyhelmetgames.roomforchange.entity.Entity.Direction;
import com.sturdyhelmetgames.roomforchange.entity.Entity.EntityState;
import com.sturdyhelmetgames.roomforchange.level.Level;
import com.sturdyhelmetgames.roomforchange.screen.GameScreen;

import static org.junit.Assert.*;

public class PlayerTest {
	GameScreen gameScreen = new GameScreen();
	Level level = new Level(gameScreen);
	SpriteBatch spriteBatch = new SpriteBatch();
	
	@Test
    public void drawAttackUpTest() {
		Player player = new Player(0, 0, level);
		player.direction = Direction.UP;
		player.setTryHitTime(0.2f);
		player.bounds.x = 0;
		player.bounds.y = 0;
		player.drawAttack(spriteBatch);
				
		Assert.assertTrue(player.bounds.x == .8f);
    }
	
	@Test
    public void drawAttackDownTest() {
		Player player = new Player(0, 0, level);
		player.direction = Direction.DOWN;
		player.setTryHitTime(0.2f);
		player.bounds.x = 0;
		player.bounds.y = 0;
		player.drawAttack(spriteBatch);
				
		Assert.assertTrue(player.bounds.x == 90f);
    }
	
	@Test
    public void drawAttackRightTest() {
		Player player = new Player(0, 0, level);
		player.direction = Direction.Right;
		player.setTryHitTime(0.2f);
		player.bounds.x = 0;
		player.bounds.y = 0;
		player.drawAttack(spriteBatch);
				
		Assert.assertTrue(player.bounds.x == 180f);
    }
	
	@Test
    public void drawAttackLeftTest() {
		Player player = new Player(0, 0, level);
		player.direction = Direction.LEFT;
		player.setTryHitTime(0.2f);
		player.bounds.x = 0;
		player.bounds.y = 0;
		player.drawAttack(spriteBatch);
				
		Assert.assertTrue(player.bounds.x == .8f);
    }
	
	@Test
    public void drawPlayerFalling() {
		Player player = new Player(0, 0, level);
		player.state = EntityState.FALLING;
		player.drawPlayer(spriteBatch);
		
		Assert.assertTrue(player.state == EntityState.FALLING);
    }
	
	@Test
    public void drawPlayerDying() {
		Player player = new Player(0, 0, level);
		player.state = EntityState.DYING;
		player.drawPlayer(spriteBatch);
		
		Assert.assertTrue(player.state == EntityState.DYING);
    }
    
    @Test
    public void drawPlayerWalking() {
		Player player = new Player(0, 0, level);
		player.state = EntityState.WALKING;
		player.drawPlayer(spriteBatch);
		
		Assert.assertTrue(player.state == EntityState.WALKING);
    }
}
