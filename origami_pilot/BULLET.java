package com.noncom.origami_pilot;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.*;

public class BULLET implements FlyObject
{

	@Override
	public void setDamage(int t)
	{
	}


	@Override
	public boolean isPlain()
	{
		return false;
	}


	private Sprite bullet;
	private Vector2 forw , pos;
	private float pow = 700 ,rot = MathUtils.random(-300, 300);
	private SpriteBatch batch;
	private boolean destroy;
	private OrthographicCamera camera;
	private SceneInterface scene;
	private Sound damage_01;
	private float timer_ = 0;
	private final float TIMER = 10;
	private final int queue = 50;

	
	BULLET(OrthographicCamera camera, SpriteBatch batch ,Vector2 forw , Vector2 pos,SceneInterface scene)
	{
		this.scene = scene;
		this.camera = camera;
		this.batch = batch;
		this.pos = new Vector2(pos);
		this.forw = new Vector2(forw);
		bullet = SpriteLoader.getLoadSprite(1);
		bullet.setScale(0.09f);
		damage_01 = SpriteLoader.getLoadSound(1);
	}
	
	@Override
	public void render(float dt) {
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		bullet.setPosition(pos.x, pos.y);
		bullet.draw(batch);
		batch.end();
	}

	@Override
	public void update(float dt) {
		pos.x += forw.x*pow*dt;
		pos.y += forw.y*pow*dt;
		bullet.setRotation(bullet.getRotation()+rot*dt);
		if(pos.y>scene.getWight()||pos.y<0||timer_>TIMER)
			destroy = true;
		
		timer_ += dt;
		
		Array<FlyObject> arr = scene.getFlyObject();
		for(int i = 0 ; i < arr.size ; i++)
		{
			if(arr.get(i).isPlain())
			{
				if(arr.get(i).getPositon().dst(pos) < 30f)
				{
					damage_01.play(0.3f);
					arr.get(i).setDamage(30);
					destroy = true;
					break;
				}
			}
		}
	}

	@Override
	public void dispose() {
		//bullet.getTexture().dispose();
	}

	@Override
	public boolean getDestroy() {
		return destroy;
	}

	@Override
	public Vector2 getPositon() {
		return null;
	}


	@Override
	public int getQueue() {
		return queue;
	}

}
