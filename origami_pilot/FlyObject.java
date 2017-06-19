package com.noncom.origami_pilot;

import com.badlogic.gdx.math.Vector2;

public interface FlyObject {

	public void render(float dt);
	public void update(float dt);
	public void dispose();
	public boolean getDestroy();
	public Vector2 getPositon();
	public boolean isPlain();
	public void setDamage(int t);
	public int getQueue();
}
