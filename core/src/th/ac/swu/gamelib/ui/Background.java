package th.ac.swu.gamelib.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;

import th.ac.swu.gamelib.GameUtil;

public class Background extends RectangleActor {
	public Background(float x, float y, float w, float h, Color color) {
		super(x,y,w,h, color);
	}
	
	public Background(Viewport viewport, Color color) {
		super(0,0,viewport.getWorldWidth(), viewport.getWorldHeight(), 
								GameUtil.color(0, 80, 0));
	}
}
