package com.noncom.origami_pilot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class PLAIN implements FlyObject , InputProcessor
{

	private Sprite plane , plane_damage , plane_left , plane_right , health_bar_s , health_bar , fire_part ,noise;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Vector2 forw;
	private float x = 0, y = 0 , timer = 0 , g = 400, SCAIL =0.2f , shaderTimer = 0f;
	private int pow = 100;
	private final int POW = 300;
	private boolean Upper , Downer , Speed;
	private SceneInterface scene;
	private int HP = 100 , wight , hight;
	private boolean destroy;
	private AControl con;
	private Sound shoot_01;
	private final int queue = 100;
	private ShaderProgram paperShader , fireShader;
	
	public PLAIN(OrthographicCamera camera , SpriteBatch batch , float x , float y , SceneInterface scene ) {
		this.camera = camera;
		this.batch = batch;
		this.scene = scene;
		this.forw = new Vector2(1, 0);
		this.x = x;
		this.y = y;
		plane = SpriteLoader.getLoadSprite(0);
		plane_right = SpriteLoader.getLoadSprite(0);
		plane_damage =  SpriteLoader.getLoadSprite(15);
		plane_left =  SpriteLoader.getLoadSprite(16);
		plane.setScale(SCAIL);
		plane_right.setScale(SCAIL);
		plane_damage.setScale(SCAIL);
		plane_left.setScale(SCAIL);
		//Gdx.input.setInputProcessor(this);
		con = new AControl(camera , batch, scene  , this);
		wight = Gdx.graphics.getWidth();
		hight = Gdx.graphics.getHeight();
		
		health_bar_s = SpriteLoader.getLoadSprite(24);
		health_bar = SpriteLoader.getLoadSprite(25);
		health_bar_s.setScale(SCAIL);
		health_bar.setScale(SCAIL);
		paperShader = SpriteLoader.getShader(1);
		
		//fireShader = SpriteLoader.getShader(2);
		//fire_part = SpriteLoader.getLoadSprite(29);
		//fire_part.setScale(SCAIL*2.0f);
		noise = SpriteLoader.getLoadSprite(30);
		
		shoot_01 = SpriteLoader.getLoadSound(0);
	}
	
	@Override
	public void render(float dt) {
		batch.setProjectionMatrix(camera.combined);
		
		shaderTimer += dt*10;
		if(shaderTimer > 100){shaderTimer =0f;}
		
		paperShader.begin();
		paperShader.setUniformf("timedelta", shaderTimer);
		paperShader.end();
		
		//fireShader.begin();
		//fireShader.setUniformf("timedelta", shaderTimer);
		
		//noise.getTexture().bind(1);
		//fireShader.setUniformi("u_texture_dis", 1);



		//fireShader.end();
		
		batch.begin();
		
		//batch.setShader(fireShader);
		//fire_part.setPosition(x, y);
		//fire_part.draw(batch);
		batch.setShader(null);
		
		
		health_bar_s.draw(batch);
		
		health_bar.draw(batch);
		
		if(destroy==false)	
			batch.setShader(paperShader);
		plane.draw(batch);
		if(destroy==false)	
			batch.setShader(null);
		batch.end();
		
		

		
		//control
		con.update(dt);
	}

	@Override
	public void update(float dt) {

		health_bar.setPosition(x, y+hight/9);
		health_bar_s.setPosition(x, y+hight/9);
		
		health_bar.setScale((float)SCAIL*((float)HP/100f), SCAIL);
		
		if(!destroy)
		{
			forw.setAngle(forw.angle()+con.getDY()*dt);
			
			if(Upper)
				forw.setAngle(forw.angle()+pow*dt);
			if(Downer)
				forw.setAngle(forw.angle()-pow*dt);
			if(Speed)
				pow = POW;
			else
				pow = (int)(POW*1.5f);
			
			x+=dt*forw.x*pow;
			y+=dt*forw.y*pow;
			
			plane.setRotation(forw.angle());
			if(forw.x >= 0)
			{
				plane.setTexture(plane_right.getTexture());
			}
			else
			{
				plane.setTexture(plane_left.getTexture());			
			}
		}
		if(HP<60)
		{
			timer += dt;
			if(timer>(float)(HP+60)/100)
			{
				scene.setFlyObject(new SMOKE(camera,batch, new Vector2(0,-1), new Vector2(x, y)));
				timer = 0;
			}
		}
		
		if(y<0||y>scene.getWight())
			destroy = true;
	
		if(HP<=0)
		{destroy=true;}
		
		if(destroy)
		{
			plane = plane_damage;
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
		//plane.getTexture().dispose();
	}

	public void fire()
	{
		if(!destroy&&!scene.getMGDX().ISPAUSE)
		{
			shoot_01.play();
			scene.setFlyObject(new BULLET(camera,batch, this.forw, new Vector2(x+forw.x*35, y+forw.y*35),scene));
		}
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode==Keys.W)
			Upper = true;
		if(keycode==Keys.S)
			Downer = true;
		if(keycode==Keys.SPACE)
			Speed = true;
		if(keycode==Keys.A)
			scene.setFlyObject(new BULLET(camera,batch, this.forw, new Vector2(x+forw.x*35, y+forw.y*35),scene));
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode==Keys.W)
			Upper = false;
		if(keycode==Keys.S)
			Downer = false;
		if(keycode==Keys.SPACE)
			Speed = false;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(pointer == 1)
		{
			scene.setFlyObject(new BULLET(camera,batch, this.forw, new Vector2(x+forw.x*35, y+forw.y*35),scene));
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean getDestroy() {
		return false;
	}
	public boolean getDes() {
		return destroy;
	}
	public Vector2 getPosition(){return new Vector2(x,y);}

	@Override
	public Vector2 getPositon() {
		return new Vector2(x,y);
	}
	public void setDamage(int t){
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
