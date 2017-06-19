package com.noncom.origami_pilot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class BOT implements FlyObject
{
	private Sprite plane , plane_damage , plane_left , plane_right ,  health_bar_s , health_bar;;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Vector2 forw , plainforw , posit;
	private float x = 0, y = 0 , timer = 0 , g = 400 , bulltimer = 0 ,  SCAIL =0.2f;
	private float BULL_TIM = 1 , BULL_DIS = 1000;
	private int pow = 100 ,HP = 100, wight , hight;
	private SceneInterface scene;
	private boolean destroy , bonus = true;
	private FlyObject plain;
	private Sound shoot_01;
	private int lev;
	private final int queue = 50;
	private ShaderProgram paperShader;

	
	public BOT(OrthographicCamera camera , SpriteBatch batch , float x , float y , SceneInterface scene , PLAIN plain , int lev ) {
		this.camera = camera;
		this.batch = batch;
		this.scene = scene;
		this.plain = plain;
		this.forw = new Vector2(1, 0);
		this.posit = new Vector2(0, 0);
		this.plainforw = new Vector2(1, 0);
		this.x = x;
		this.y = y;
		wight = Gdx.graphics.getWidth();
		hight = Gdx.graphics.getHeight();
		plane = SpriteLoader.getLoadSprite(0);
		plane_right = SpriteLoader.getLoadSprite(0);
		plane_damage =  SpriteLoader.getLoadSprite(15);
		plane_left =  SpriteLoader.getLoadSprite(16);
		plane.setScale(SCAIL);
		plane_right.setScale(SCAIL);
		plane_damage.setScale(SCAIL);
		plane_left.setScale(SCAIL);
		
		health_bar_s = SpriteLoader.getLoadSprite(24);
		health_bar = SpriteLoader.getLoadSprite(25);
		health_bar_s.setScale(SCAIL);
		health_bar.setScale(SCAIL);
		paperShader = SpriteLoader.getShader(1);


		shoot_01 = SpriteLoader.getLoadSound(0);
		
		this.lev = lev;
		System.out.println(lev);
		switch (lev) {
		case 0:
			this.BULL_TIM = 2;
			this.pow = 100;
			this.BULL_DIS = 2000;
			break;
		case 1:
			this.BULL_TIM = 1;
			this.pow = 200;
			this.BULL_DIS = 1000;
			break;
		case 2:
			this.BULL_TIM = 0.5f;
			this.pow = 300;
			this.BULL_DIS = 700;
			break;
		}
		
	}
	
	@Override
	public void render(float dt) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		health_bar_s.draw(batch);
		health_bar.draw(batch);
		
		if(destroy==false)	
			batch.setShader(paperShader);
		plane.draw(batch);
		if(destroy==false)	
			batch.setShader(null);
		batch.end();
	}

	@Override
	public void update(float dt) {
		
		health_bar.setPosition(x, y+hight/9);
		health_bar_s.setPosition(x, y+hight/9);
		
		health_bar.setScale((float)SCAIL*((float)HP/100f), SCAIL);
		
		posit.set(-x, -y);
		if(!destroy)
		{
			if(forw.x >= 0)
			{
				plane.setTexture(plane_right.getTexture());
			}
			else
			{
				plane.setTexture(plane_left.getTexture());
			}
			
			plainforw = new Vector2( plain.getPositon().add(posit));
			if(((PLAIN)plain).getDes())
			{
				forw.lerp(new Vector2(1,0), 0.2f);
				forw.nor();
			}
			else
			{
				forw.lerp(plainforw, 0.01f);
				forw.nor();
			}
			
			bulltimer +=dt;
			if(forw.angle() - plainforw.angle() < 0.1f&& bulltimer > BULL_TIM&&!((PLAIN)plain).getDestroy()&&Vector2.len(plain.getPositon().add(posit).x, plain.getPositon().add(posit).y) <BULL_DIS )
			{
				bulltimer = 0;
				shoot_01.play(0.5f);
				scene.setFlyObject(new BULLET(camera,batch, this.forw, new Vector2(x+forw.x*35, y+forw.y*35),scene));
			}
			
			x+=dt*forw.x*pow;
			y+=dt*forw.y*pow;
			
			plane.setRotation(forw.angle());
			
		}
		if(HP<60)
		{
			timer += dt;
			if(timer>(float)(HP+30)/100)
			{
				scene.setFlyObject(new SMOKE(camera,batch, new Vector2(0,-1), new Vector2(x, y)));
				timer = 0;
			}
		}
		
		if(y<0||y>scene.getWight())
			destroy = true;
		
		if(HP<=0)
		{
			destroy = true;
		}
		if(destroy)
		{
			plane = plane_damage;
			if(bonus)
			{
				BONUS bon = new BONUS(camera, batch, x, y, scene, plain);
				scene.setFlyObject(bon);
				bonus = false;
			}
			
			HP = 0;
			pow = 0;
		}
		
		if(destroy&&y>0)
		{
			y-=dt*g;
		}
		plane.setPosition(x, y);

	}

	@Override
	public void dispose() {
		
	}

	@Override
	public boolean getDestroy() {
		return false;
	}
	public boolean getDes() {
		return destroy;
	}
	@Override
	public Vector2 getPositon() {
		return new Vector2(x,y);
	}

	@Override
	public void setDamage(int t)
	{
		HP-=t;
	}

	@Override
	public boolean isPlain()
	{
		return true;
	}
	
	@Override
	public int getQueue() {
		return queue;
	}

}
