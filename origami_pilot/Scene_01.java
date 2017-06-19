package com.noncom.origami_pilot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.*;

public class Scene_01 extends ApplicationAdapter implements SceneInterface
{

	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Sprite scene_sprite , edge_sprite;
	private Sprite[] scene_sprites;
	private Array<FlyObject> flyObject;
	private PLAIN plane;
	private BOT bot;
	private int height, width , timer = 0;
	private final int SCALE=3;
	private MyGdxGame root;
	//debug start
	private BitmapFont font;
	private SpriteBatch batch_d;
	//debug end

	public Scene_01(final MyGdxGame root)
	{
		
		this.root = root;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(width , height );
		camera.position.set(width, height, 0);
		
		batch = new SpriteBatch();
		
		plane = new PLAIN(camera, batch, -50, 150 ,this);
		flyObject = new Array<FlyObject>();
		flyObject.add(plane);
		edge_sprite = SpriteLoader.getLoadSprite(17);
		
		scene_sprites = new Sprite[5];

		scene_sprites[0] =  SpriteLoader.getLoadSprite(4);
		scene_sprites[1] =  SpriteLoader.getLoadSprite(18);
		scene_sprites[2] =  SpriteLoader.getLoadSprite(19);
		scene_sprites[3] =  SpriteLoader.getLoadSprite(21);
		scene_sprites[4] =  SpriteLoader.getLoadSprite(22);

		scene_sprite = scene_sprites[MathUtils.random(0, scene_sprites.length-1)];
		scene_sprite.setScale(SCALE);
		edge_sprite.setScale(SCALE);
		edge_sprite.rotate(180);
		
		int rand = (int)MathUtils.random(0, 1);
		int x_resp = (int) ((rand == 1)? ( plane.getPositon().x-4000 ): ( plane.getPositon().x+4000 ));

		bot = new BOT(camera, batch, x_resp, 200, this, plane,MathUtils.random(0, 2));
		flyObject.add(bot);
		
		//debug
		font = new BitmapFont();
		batch_d = new SpriteBatch();
	}
	
	@Override
	public void update(float dt) 
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		for(int i = 0 ; i < 5; i++)
		{
			scene_sprite.setPosition((i-2+(int)(plane.getPosition().x/(scene_sprite.getWidth()*SCALE)))*(scene_sprite.getWidth()*SCALE),height/4);
			scene_sprite.draw(batch);
			scene_sprite.setPosition((i-2+(int)(plane.getPosition().x/(scene_sprite.getWidth()*SCALE)))*(scene_sprite.getWidth()*SCALE),height*1.65f);
			scene_sprite.draw(batch);
			edge_sprite.rotate(180);
			edge_sprite.setPosition((i-2+(int)(plane.getPosition().x/(scene_sprite.getWidth()*SCALE)))*(scene_sprite.getWidth()*SCALE),edge_sprite.getHeight()*SCALE/2f + width);
			edge_sprite.draw(batch);
			edge_sprite.rotate(180);
			edge_sprite.setPosition((i-2+(int)(plane.getPosition().x/(scene_sprite.getWidth()*SCALE)))*(scene_sprite.getWidth()*SCALE), -edge_sprite.getHeight()*SCALE/2);
			edge_sprite.draw(batch);
		}
		batch.end();

		for(FlyObject f:flyObject)
		{
			f.update(dt);
			
			if(f.getQueue() == 50)
				f.render(dt);
		}
	
		for(FlyObject f:flyObject)
		{
			if(f.getQueue() == 100)
				f.render(dt);
		}
		
		int index = 0;
		for(FlyObject f: flyObject)
		{
			
			if(f.getDestroy())
			{
				//f.dispose();
				flyObject.removeIndex(index);
			}
			index++;
		}
		
		
		timer+=dt;

		if( bot.getDes())
		{
			int rand = (int)MathUtils.random(0, 1);
			int x_resp = (int) ((rand == 1)? ( plane.getPositon().x-8000 ): ( plane.getPositon().x+8000 ));

			bot = new BOT(camera, batch, x_resp , 400, this, plane , MathUtils.random(0, 2));
			flyObject.add(bot);
			timer=0;
		}
		
		//debug
		batch_d.begin();
		font.draw(batch_d, "fps:"+1/dt, 10, 10);
		batch_d.end();
		camera.position.set(plane.getPosition().x+width/4,plane.getPosition().y + height/4,0);
		
		camera.update();
	}

	@Override
	public void dispose() {
		for(FlyObject f:flyObject)
		{
			f.dispose();
		}
		batch.dispose();
		batch_d.dispose();
	}
	@Override
	public void setFlyObject(FlyObject value){flyObject.add(value);}
	
	@Override
	public int getWight(){
		return width;
	}
	
	@Override
	public Array<FlyObject> getFlyObject() {
		return flyObject;
	}
	public MyGdxGame getScene()
	{
		return root;
	}
	@Override
	public MyGdxGame getMGDX()
	{
		return root;
	}

}
