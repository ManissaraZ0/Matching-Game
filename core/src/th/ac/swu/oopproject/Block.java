package th.ac.swu.oopproject;

import com.badlogic.gdx.graphics.Color;

import th.ac.swu.gamelib.collision.CollisionObj;
import th.ac.swu.gamelib.ui.RectangleActor;

public class Block extends RectangleActor {
    private static final int GROUP_CODE = 0;

	public Block(float x, float y, float w, float h, Color color) {
		super(x, y, w, h, color);
		
		this.setUserObject(new CollisionObj(this, GROUP_CODE));
	}

}