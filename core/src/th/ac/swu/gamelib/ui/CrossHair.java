package th.ac.swu.gamelib.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

public class CrossHair extends Group {
	
	public CrossHair() {
		this(0, 0);
	}
	
	public CrossHair(Vector2 position) {
		this(position.x, position.y);
	}

	public CrossHair(float x, float y) {
		float halfLen = 10;
		Color color = Color.RED;
		addActor(new VerticalLine(0, halfLen, -halfLen, color, 2));
		addActor(new HorizontalLine(0, halfLen, -halfLen, color, 2));
		setX(x);
		setY(y);
	}

}
