package com.noncom.origami_pilot;
import com.badlogic.gdx.utils.*;

public interface SceneInterface {

	public void update(float dt);
	public void dispose();
	public void setFlyObject(FlyObject value);
	public Array<FlyObject> getFlyObject();
	public int getWight();
	public MyGdxGame getMGDX();
}
