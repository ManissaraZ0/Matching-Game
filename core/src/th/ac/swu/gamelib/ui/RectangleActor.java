package th.ac.swu.gamelib.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import th.ac.swu.gamelib.GameUtil;

public class RectangleActor extends Actor {
	private Texture texture;

	// why color is float: https://stackoverflow.com/questions/32834140/why-does-opengl-use-4-floats-to-define-colour-normally
	public RectangleActor(float x, float y, float w, float h, Color color) {
		super();
		setPosition(x, y);
		setWidth(w);
		setHeight(h);
		texture = GameUtil.getWhitePixel();
		setColor(color);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.setColor(getColor());
		batch.draw(texture, getX(),getY(),getWidth(),getHeight());
	}
	
	
}
