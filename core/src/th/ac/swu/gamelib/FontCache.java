package th.ac.swu.gamelib;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontCache {
	private static Map<FontInfo, BitmapFont> cache;
	
	static {
		cache = new HashMap<FontInfo, BitmapFont>();
	}
	public static BitmapFont get(String name, int size,
			   Color shadowColor, int shadowOffsetX, int shadowOffsetY)
	{
		return get(new FontInfo(name, size, shadowColor, shadowOffsetX, shadowOffsetY));
	}
	
	public static BitmapFont get(String name, int size) {
		return get(new FontInfo(name, size));
	}
	
	public static BitmapFont get(FontInfo info) {
		BitmapFont font = cache.get(info);
		if(font != null)
			return font;
		
		var generator = new FreeTypeFontGenerator(Gdx.files.internal(info.name()));
		var parameter = new FreeTypeFontParameter();
		parameter.size = info.size();
		//parameter.color = Color.YELLOW;
		//parameter.borderColor = Color.YELLOW;
		//parameter.borderWidth = 5;
		parameter.shadowColor = info.shadowColor();
		parameter.shadowOffsetX = info.shadowOffsetX();
		parameter.shadowOffsetY = info.shadowOffsetY();
		font = generator.generateFont(parameter);
		cache.put(info, font);
		return font;
	}
}
