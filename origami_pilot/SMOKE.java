package com.noncom.origami_pilot;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.*;

public class SMOKE implements FlyObject
{
	private Sprite smke;
	private Vector2 forw , pos;
	private float pow = 100 ,rot = MathUtils.random(-2f, 2f),rotat = MathUtils.random(-20f, 20f), timer = 5 , shaderTimer = 0 , shaderCoef = MathUtils.random(-15f, 15f);
	private final int TIM = 5;
	private SpriteBatch batch;
	private boolean destroy;
	private OrthographicCamera camera;
	private final int queue = 50;
	private ShaderProgram smokeShader;

	
	public SMOKE(OrthographicCamera camera,SpriteBatch batch ,Vector2 forw , Vector2 pos) {
		this.camera = camera;
		this.batch = batch;
		this.pos = new Vector2(pos.x,pos.y);
		this.forw = new Vector2(0,1);
		this.forw.setAngle(forw.angle()+rot*5);
		smke = SpriteLoader.getLoadSprite(3);
		smke.setScale(0.05f);
		timer = TIM;
		smokeShader = SpriteLoader.getShader(0);
	}
	
	@Override
	public void render(float dt) {
		
		
		shaderTimer +=dt*shaderCoef;
		if(shaderTimer>1000){ shaderTimer = 0;}
		
		smokeShader.begin();
		smokeShader.setUniformf("timedelta", shaderTimer);
		smokeShader.end();
		
		batch.setShader(smokeShader);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		smke.setPosition(pos.x, pos.y);
		smke.draw(batch);
		batch.end();
		
		batch.setShader(null);
	}
	@Override
	public void update(float dt) {
		pos.x -= forw.x*pow*dt;
		pos.y -= forw.y*pow*dt;
		smke.setRotation(smke.getRotation()+rotat*dt);
		smke.setAlpha(Math.abs(timer/TIM));
		smke.setScale(smke.getScaleX()+dt/12);
		if(timer <= 0)
			destroy = true;
		timer-=dt;
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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setDamage(int t){}


	@Override
	public boolean isPlain()
	{
		return false;
	}

	@Override
	public int getQueue() {
		return queue;
	}

}
