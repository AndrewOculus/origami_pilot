package com.noncom.origami_pilot;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;

public class Menu_01 extends ApplicationAdapter implements SceneInterface
{



	private Skin skin;
	private Stage stage;
	private Table table;
	private MyGdxGame root;
	private SpriteBatch batch;
	private Sprite sprite;
	private int BUTTON_SIZE = 400 , BACKGR_SCAIL = 8;
	private int width, heidgt;
	
	public Menu_01(final MyGdxGame root)
	{
		  width = Gdx.graphics.getWidth();
		  heidgt = Gdx.graphics.getHeight();
	
		  BUTTON_SIZE = (int)(width / 6);
		  
		  sprite = SpriteLoader.getLoadSprite(4);
		  sprite.setPosition(0,0);
		  sprite.setScale(BACKGR_SCAIL);
		  
		  ImageButton.ImageButtonStyle ImageButtonRight = new ImageButton.ImageButtonStyle();
		  ImageButtonRight.up =  new TextureRegionDrawable(new TextureRegion(SpriteLoader.getLoadSprite(9).getTexture()));
		  ImageButtonRight.down = new TextureRegionDrawable(new TextureRegion(SpriteLoader.getLoadSprite(10).getTexture()));

		  ImageButton exitB = new ImageButton(ImageButtonRight);
		  exitB.setSize(BUTTON_SIZE,BUTTON_SIZE);
		  exitB.setPosition(Gdx.graphics.getWidth()/2-BUTTON_SIZE/2, Gdx.graphics.getHeight()*0.24f);

		  exitB.addListener(new ClickListener() {
		      @Override
		      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		    	  Gdx.app.exit();

		          return true;
		      }
		      @Override
		      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		      }
		  });
		  ImageButton.ImageButtonStyle startstyle = new ImageButton.ImageButtonStyle();

		  startstyle.up =  new TextureRegionDrawable(new TextureRegion(SpriteLoader.getLoadSprite(8).getTexture()));
		  startstyle.down = new TextureRegionDrawable(new TextureRegion(SpriteLoader.getLoadSprite(6).getTexture()));
		  
		  ImageButton startB = new ImageButton(startstyle);
		  startB.setSize(BUTTON_SIZE, BUTTON_SIZE);
		  startB.setPosition(Gdx.graphics.getWidth()/2-BUTTON_SIZE/2, Gdx.graphics.getHeight()/2);

		  startB.addListener(new ClickListener() {
		      @Override
		      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		    	  root.setSceneNum(root.SCENE1);
		          return true;
		      }
		      @Override
		      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		      }
		  });
		  
		  //
		  
		  batch = new SpriteBatch();
		  this.root = root;
		  skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	      stage = new Stage(new ScreenViewport());
	      stage.addActor(startB);
	      stage.addActor(exitB);
	
	      Gdx.input.setInputProcessor(stage);
	      
	    
	}
	
	@Override
	public void update(float dt) {
	      Gdx.gl.glClearColor(1, 1, 1, 1);
	      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		  batch.begin();
		  sprite.draw(batch);
		  batch.end();
	      stage.act(dt);
	      stage.draw();	
	}

	@Override
	public void dispose() {
	     stage.dispose();
	}

	@Override
	public void setFlyObject(FlyObject value) {
		
	}


	@Override
	public Array<FlyObject> getFlyObject() {
		return null;
	}
	
	
	@Override
	public int getWight() {
		return 0;
	}
	
	@Override
	public MyGdxGame getMGDX()
	{
		return null;
	}

}
