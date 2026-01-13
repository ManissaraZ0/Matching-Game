package th.ac.swu.gamelib.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class HollowRect extends Grid {

	public HollowRect(float tileWidth, Color color, float lineWidth) {
		this(tileWidth, tileWidth, color, lineWidth);
	}
	public HollowRect(float tileWidth, float tileHeight, Color color, float lineWidth) {
		super(tileWidth, tileHeight, 1, 1, color, lineWidth);
	}
	
	public HollowRect(Rectangle rect, Color color, float lineWidth) {
		this(rect.width, rect.height, color, lineWidth);
		setPosition(rect.x, rect.y);
	}
	
}
