package th.ac.swu.gamelib;

import com.badlogic.gdx.graphics.Color;

public record FontInfo(String name, int size,
					   Color shadowColor, int shadowOffsetX, int shadowOffsetY) 
{
	public FontInfo(String name, int size) {
		this(name, size, new Color(0, 0, 0, 0.75f), 0, 0);
	}
}
