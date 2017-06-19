package com.noncom.origami_pilot;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;

public class AControl
{
	private Skin skin;
	private Stage stage;
	private Table table;
	private TextButton back , fire;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite joyst_01 , joyst_02;
	private ShapeRenderer sr;
	private int[] x , y;
	private int bx = 0 , by = 0 , width , height , touchNum ;
	private float j_scale_01 = 0.5f , j_scale_02 = 0.3f;
	private int BUTTON_SIZE = 400;
	private boolean isbase = true;
	private float dx = 0, dy = 0;
	private SceneInterface scene;
	private PLAIN plain;
	
	public AControl(OrthographicCamera camera, SpriteBatch batch , final SceneInterface scene, final PLAIN plain)
	{
		this.plain = plain;
		this.scene = scene;
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		//butons
		j_scale_01 = (float)width/3500f; 
		
		joyst_01 = SpriteLoader.getLoadSprite(20);
		joyst_01.setScale(j_scale_01);
		
		j_scale_02 = (float)width/6500f; 
		
		joyst_02 = SpriteLoader.getLoadSprite(20);
		joyst_02.setScale(j_scale_02);
		joyst_02.setColor(0.1f,0.1f,0.1f,0.5f);

		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		stage = new Stage(new ScreenViewport());
	
		BUTTON_SIZE = (int)(width / 6);
		
		ImageButton.ImageButtonStyle ImageButtonRight = new ImageButton.ImageButtonStyle();
		ImageButtonRight.up =  new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sprites/paper_sprite_12.png"))));
		ImageButtonRight.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sprites/paper_sprite_13.png"))));

		ImageButton exitB = new ImageButton(ImageButtonRight);
		exitB.setSize(BUTTON_SIZE,BUTTON_SIZE);
		exitB.setPosition(width*0.1f-BUTTON_SIZE/2, height*0.2f);

		exitB.addListener(new ClickListener() {
		    @Override
		    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		    	plain.fire();
		        return true;
		     }
		     @Override
		     public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		     }
		  });
		  ImageButton.ImageButtonStyle startstyle = new ImageButton.ImageButtonStyle();

		  startstyle.up =  new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sprites/paper_sprite_14.png"))));
		  startstyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sprites/paper_sprite_15.png"))));
		  
		  ImageButton startB = new ImageButton(startstyle);
		  startB.setSize(BUTTON_SIZE, BUTTON_SIZE);
		  startB.setPosition(width*0.1f-BUTTON_SIZE/2, height*0.6f);

		  startB.addListener(new ClickListener() {
		      @Override
		      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		    	  scene.getMGDX().setSceneNum(scene.getMGDX().MENU);
		          return true;
		      }
		      @Override
		      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		      }
		  });
		
		  ImageButton.ImageButtonStyle pausestyle = new ImageButton.ImageButtonStyle();

		  pausestyle.up =  new TextureRegionDrawable(new TextureRegion(SpriteLoader.getLoadSprite(27).getTexture()));
		  pausestyle.down = new TextureRegionDrawable(new TextureRegion(SpriteLoader.getLoadSprite(28).getTexture()));
		  
		  ImageButton pauseB = new ImageButton(pausestyle);
		  pauseB.setSize(BUTTON_SIZE, BUTTON_SIZE);
		  pauseB.setPosition(width*0.8f-BUTTON_SIZE/2, height*0.6f);

		  pauseB.addListener(new ClickListener() {
		      @Override
		      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		    	  scene.getMGDX().setPause();
		          return true;
		      }
		      @Override
		      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		      }
		  }); 
		  
		stage.addActor(pauseB);
		stage.addActor(startB);
		stage.addActor(exitB);

		  
		//butons

		
		this.camera = camera;
		this.batch = new SpriteBatch();
		sr = new ShapeRenderer(); 
		sr.setColor(0,0,0,0);
		x = new int[10];
		y = new int[10];
	}
	public void update(float dt)
	{
		for(int i = 0 ; i < 3 ; i++)
		{

			if(Gdx.input.isTouched(i)&&isbase&&Gdx.input.getX(i)>height/2)
			{
				bx = Gdx.input.getX(i);
				by = Gdx.graphics.getHeight() - Gdx.input.getY(i);
				touchNum = i;
				isbase = false;
			}
			if((Gdx.input.getX(touchNum)==0 && Gdx.input.getY(touchNum)==0&&touchNum!=12)||!Gdx.input.isTouched(touchNum))
			{
				bx = 0;
				by = 0;
				touchNum = 12;
				isbase=true;
			}
			
		}

		render(dt);
	}
	private void render(float dt)
	{
		joyst_01.setColor(1,1,1,0.5f);
		batch.begin();
	
		if(!isbase&&touchNum!=12)
		{
			dy = Gdx.graphics.getHeight() - Gdx.input.getY(touchNum) - by;

			joyst_01.setPosition(bx - joyst_01.getWidth()/2, by - joyst_01.getHeight()/2);
			joyst_01.draw(batch);
			if(Vector2.dst(Gdx.input.getX(touchNum), Gdx.graphics.getHeight() - Gdx.input.getY(touchNum), bx, by)<joyst_01.getWidth()*j_scale_01/2)
			{
				joyst_02.setPosition( Gdx.input.getX(touchNum) - joyst_02.getWidth()/2 , Gdx.graphics.getHeight() - Gdx.input.getY(touchNum) - joyst_02.getHeight()/2);
				joyst_02.draw(batch);
			}
			else
			{
				Vector2 nor = new Vector2(-bx + Gdx.input.getX(touchNum), -by + Gdx.graphics.getHeight()- Gdx.input.getY(touchNum));
				nor.setLength(joyst_01.getWidth()*j_scale_01/2);
				nor.add(bx, by);
				joyst_02.setPosition( nor.x - joyst_02.getWidth()/2 , nor.y - joyst_02.getHeight()/2);
				joyst_02.draw(batch);
			}
			
		}
		else
		{
			dy = 0;
		}
		
		batch.end();
		/*
		sr.begin(ShapeRenderer.ShapeType.Filled);
		for(int i = 0 ; i < 3 ; i++)
		{
			if(Gdx.input.isTouched(touchNum))
			{
				dy = Gdx.graphics.getHeight() - Gdx.input.getY(touchNum) - by;
				sr.circle(bx, by , 100);
			}
			else
			{
				dy = 0;
			}
		}	
		sr.end();
		*/
		Gdx.input.setInputProcessor(stage);
		stage.draw();
		stage.act(dt);
	}
	public float getDY(){return dy;}
}
