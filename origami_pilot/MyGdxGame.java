package com.noncom.origami_pilot;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;


public class MyGdxGame implements ApplicationListener
{
	SceneInterface scene;
	int SCENE_NUM = 0 ;
	public final int SCENE1 = 1 , MENU = 0;
	boolean ISCLICKED = false , ISPAUSE = false;

	@Override
	public void create () {
		SpriteLoader.Load();
		scene = new Menu_01(this);
		
	}

	@Override
	public void render () {	
		float dt = 0;
		if(!ISPAUSE)
			dt = Gdx.graphics.getDeltaTime();
	

		scene.update(dt);

		if(ISCLICKED)
		{
			switch (SCENE_NUM) {
				case MENU:
					scene.dispose();
					scene = new Menu_01(this);
					ISCLICKED = false;
					break;
				case SCENE1:
					scene.dispose();
					scene = new Scene_01(this);
					ISCLICKED = false;
					break;
			}
		}
	}

	public void setPause()
	{
		ISPAUSE = !ISPAUSE;
	}
	
	@Override
	public void dispose () {
		scene.dispose();
		SpriteLoader.dispose();
	}

	public void setSceneNum(int num) {
		this.SCENE_NUM = num;
		this.ISCLICKED = true;
	}

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}
}
