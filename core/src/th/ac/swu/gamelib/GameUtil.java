package th.ac.swu.gamelib;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameUtil {
	public static Color color(int r, int g, int b) {
		return color(r, g, b, 255);
	}
	public static Color color(int r, int g, int b, int alpha) {
		return new Color(r/255.0f, g/255.0f, b/255.0f, alpha/255.0f);
	}
	
	private static Texture whitePixel = null;
	// adapted from: https://stackoverflow.com/questions/15397074/libgdx-how-to-draw-filled-rectangle-in-the-right-place-in-scene2d
	public static Texture getWhitePixel() {
		if(whitePixel != null)
			return whitePixel;
		
		Color color = Color.WHITE;
		Pixmap pixmap = new Pixmap(1,1,Format.RGBA8888);
		pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, 1, 1);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        whitePixel = texture;
        return whitePixel;
	}
	
	public static Vector2 getPosition(Actor actor) {
		return new Vector2(actor.getX(), actor.getY());
	}
	
	public static void setPosition(Actor actor, Vector2 position) {
		actor.setPosition(position.x, position.y);
	}
	
	public static void changePosition(Actor actor, Vector2 displacement) {
		var position = getPosition(actor);
		position.add(displacement);
		setPosition(actor, position);
	}

	// these methods are moved into "Actors" class
/*	@Deprecated
	public static Vector2 getPosition(Actor actor) {
		return Actors.getPosition(actor);
	}
	
	@Deprecated
	public static void setPosition(Actor actor, Vector2 position) {
		Actors.setPosition(actor, position);
	}
	
	@Deprecated
	public static void changePosition(Actor actor, Vector2 displacement) {
		Actors.changePosition(actor, displacement);
	}*/
}
