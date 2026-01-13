package th.ac.swu.gamelib;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;

public class TextureCache {
	private static Map<String, Texture> cache;
	
	static {
		cache = new HashMap<String, Texture>();
	}
	
	public static Texture get(String name) {
		Texture texture = cache.get(name);
		if(texture != null)
			return texture;
		
		texture = new Texture(name);
		cache.put(name, texture);
		return texture;
	}
}
