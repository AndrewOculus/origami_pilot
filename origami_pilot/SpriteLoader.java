package com.noncom.origami_pilot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class SpriteLoader {

	private static Texture[] spratearr = new Texture[31];
	private static Sound[] sounds = new Sound[6];
	private static ShaderProgram smokeShader , paperShader , fireShader;
	
	public static void Load(){
		//sprites
		spratearr[0] = getIT("sprites/paper_sprite_01.png");
		spratearr[1] = getIT("sprites/paper_sprite_02.png");
		spratearr[2] = getIT("sprites/paper_sprite_03.png");
		spratearr[3] = getIT("sprites/paper_sprite_04.png");
		spratearr[4] = getIT("sprites/paper_sprite_05.png");
		spratearr[5] = getIT("sprites/paper_sprite_06.png");
		spratearr[6] = getIT("sprites/paper_sprite_07.png");
		spratearr[7] = getIT("sprites/paper_sprite_08.png");
		spratearr[8] = getIT("sprites/paper_sprite_09.png");
		spratearr[9] = getIT("sprites/paper_sprite_10.png");
		spratearr[10] = getIT("sprites/paper_sprite_11.png");
		spratearr[11] = getIT("sprites/paper_sprite_12.png");
		spratearr[12] = getIT("sprites/paper_sprite_13.png");
		spratearr[13] = getIT("sprites/paper_sprite_14.png");
		spratearr[14] = getIT("sprites/paper_sprite_15.png");
		spratearr[15] = getIT("sprites/paper_sprite_16.png");
		spratearr[16] = getIT("sprites/paper_sprite_17.png");
		spratearr[17] = getIT("sprites/paper_sprite_18.png");
		spratearr[18] = getIT("sprites/paper_sprite_19.png");
		spratearr[19] = getIT("sprites/paper_sprite_20.png");
		spratearr[20] = getIT("sprites/paper_sprite_21.png");
		spratearr[21] = getIT("sprites/paper_sprite_22.png");
		spratearr[22] = getIT("sprites/paper_sprite_23.png");
		spratearr[23] = getIT("sprites/paper_sprite_24.png");
		spratearr[24] = getIT("sprites/paper_sprite_25.png");
		spratearr[25] = getIT("sprites/paper_sprite_26.png");
		spratearr[26] = getIT("sprites/paper_sprite_27.png");
		spratearr[27] = getIT("sprites/paper_sprite_28.png");
		spratearr[28] = getIT("sprites/paper_sprite_29.png");
		spratearr[29] = getIT("sprites/paper_sprite_30.png");
		spratearr[30] = getIT("sprites/paper_sprite_31.png");


		//shaders
		ShaderProgram.pedantic = false;
		smokeShader = new ShaderProgram(Gdx.files.internal("shaders/vertSmoke"), Gdx.files.internal("shaders/fragSmoke"));
		paperShader = new ShaderProgram(Gdx.files.internal("shaders/vertPaper"), Gdx.files.internal("shaders/fragPaper"));
		fireShader = new ShaderProgram(Gdx.files.internal("shaders/vertFire"), Gdx.files.internal("shaders/fragFire"));

		
		//sounds
		sounds[0] = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot_01.wav"));
		sounds[1] = Gdx.audio.newSound(Gdx.files.internal("sounds/damage_01.wav"));
		
	}
	
	private static Texture getIT(String path)
	{
		return new Texture(Gdx.files.internal(path));
	}
	public static Sprite getLoadSprite(int id)
	{
		return new Sprite( spratearr[id]);
	}
	public static Sound getLoadSound(int id)
	{
		return sounds[id];
	}
	public static ShaderProgram getShader(int id)
	{
		if(id==0)
			return smokeShader;
		if(id==1)
			return paperShader;
		if(id==2)
			return fireShader;
		else
			return null;
	}

	public static void dispose()
	{
		for(Texture s:spratearr)
		{
			s.dispose();
		}

	}
}
