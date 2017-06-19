package com.noncom.origami_pilot;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class BONUS implements FlyObject {

	private Sprite[] sprite_bonus_array;
	private Sprite sprite_bonus;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private float x , y , scaleXY = 0.3f , acc = 10;
	private SceneInterface scene;
	private FlyObject plain;
	private boolean destroy = false;
	private int bonus_int = 0;
	private final int queue = 50;

	
	public BONUS(OrthographicCamera camera , SpriteBatch batch , float x , float y , SceneInterface scene , FlyObject plain ) {
		this.sprite_bonus_array = new Sprite[2];
		this.sprite_bonus_array[0] = SpriteLoader.getLoadSprite(23);
		this.sprite_bonus_array[1] = SpriteLoader.getLoadSprite(26);
		int rand = (int)MathUtils.random(0, 2);
		switch (rand){
		case 0:
			this.sprite_bonus = this.sprite_bonus_array[0];
			bonus_int = -60;
			break;
		case 1:
			this.sprite_bonus = this.sprite_bonus_array[1];
			bonus_int = 60;
			break;
		case 2:
			bonus_int = 0;
			break;
		}
		if(bonus_int!=0)
		{
			this.sprite_bonus.setScale(scaleXY);
		}
		this.plain = plain;
		this.scene = scene;
		this.camera = camera;
		this.batch = batch;
		this.x = x;
		this.y = y;

	}
	
	@Override
	public void render(float dt) {

		if(bonus_int!=0)
		{
			batch.begin();
			sprite_bonus.draw(batch);
			batch.end();
		}
		
	}

	@Override
	public void update(float dt) {
		
		if(bonus_int==0)
			destroy = true;
		
		
		if(bonus_int!=0)
		{
			if(y<0||y>scene.getWight())
				destroy = true;
			
			sprite_bonus.setPosition(x, y);
			y -= dt * acc;
			
			if(plain.getPositon().dst(new Vector2(x, y)) < 30f)
			{
				//damage_01.play(0.3f);
				plain.setDamage(bonus_int);
				destroy = true;
			}
		}
		
		
	}

	@Override
	public void dispose() {		
	}

	@Override
	public boolean getDestroy() {
		return destroy;
	}

	@Override
	public Vector2 getPositon() {
		return new Vector2(x,y);
	}

	@Override
	public boolean isPlain() {
		return false;
	}

	@Override
	public void setDamage(int t) {		
	}

	@Override
	public int getQueue() {
		return queue;
	}

}
