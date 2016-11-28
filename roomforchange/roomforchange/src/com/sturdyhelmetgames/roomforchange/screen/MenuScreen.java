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
package com.sturdyhelmetgames.roomforchange.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.sturdyhelmetgames.roomforchange.RoomForChangeGame;
import com.sturdyhelmetgames.roomforchange.assets.Assets;

public class MenuScreen extends Basic2DScreen {
	
	private int maxChar = 3;
	private int selChar = 0;
	private float stateTime = 0f;
	public static int character = 0;

	public MenuScreen(RoomForChangeGame game) {
		super(game, 12, 8);
	}

	@Override
	protected void updateScreen(float fixedStep) {
		stateTime += 0.01f;
	}

	@Override
	public void renderScreen(float delta) {
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		spriteBatch
				.draw(Assets.getFullGameObject("pyramid"), -6f, -4f, 12f, 8f);
		
		Animation animation = null;
		if (selChar == 0)
			animation = Assets.playerWalkFront;
		else if (selChar == 1)
			animation = Assets.thiefWalkFront;
		else if (selChar == 2)
			animation = Assets.pharaoWalkFront;
		
		if (animation != null)
			spriteBatch.draw(animation.getKeyFrame(stateTime, true), 2.5f, -0.8f, 0.5f, 0.5f);
		spriteBatch.draw(Assets.keyLeft.getKeyFrame(0f), 1.8f, -0.8f, 0.5f, 0.5f, 0.5f, 0.5f, 1f, 1f, 0f);
		spriteBatch.draw(Assets.keyRight.getKeyFrame(0f), 3.2f, -0.8f, 0.5f, 0.5f, 0.5f, 0.5f, 1f, 1f, 0f);
		
		spriteBatch.end();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.SPACE) {
			character = this.selChar;
			game.setScreen(new HelpScreen(game, new GameScreen(game)));
			return true;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)){
			selChar++;
			if (selChar >= maxChar)
				selChar = 0;
			return true;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT)){
			selChar--;
			if (selChar < 0)
				selChar = maxChar - 1;
			return true;
		}
		return super.keyDown(keycode);
	}

}
