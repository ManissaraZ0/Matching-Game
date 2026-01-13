package th.ac.swu.gamelib;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class Actors {
	
	public static String toString(Actor actor) {
		return String.format("{Actor:Name=%s; Position=%s; Size=%s; Origin=%s}\n",
				actor.getName(), getPosition(actor), getSize(actor), getOrigin(actor));
	}
	
	// ใช้ scaling มาคูณ width, height ด้วย
	public static Rectangle getBounds(Actor actor) {
		return new Rectangle(actor.getX(), actor.getY(), 
							actor.getWidth() * actor.getScaleX(), actor.getHeight() * actor.getScaleY());
	}

	public static Vector2 getPosition(Actor actor) {
		return new Vector2(actor.getX(), actor.getY());
	}
	
	public static void setPosition(Actor actor, Vector2 position) {
		actor.setPosition(position.x, position.y);
	}
	
	public static Vector2 getSize(Actor actor) {
		return new Vector2(actor.getWidth(), actor.getHeight());
	}
	
	public static Vector2 getOrigin(Actor actor) {
		return new Vector2(actor.getOriginX(), actor.getOriginY());
	}
	
	public static void setCenterOrigin(Actor actor) {
		actor.setOrigin(Align.center);
	}

	// idea from: https://stackoverflow.com/questions/28079531/actor-origin-in-scene-2d 
	public static Vector2 getCenterPosition(Actor actor) {
		return getPosition(actor).add(getSize(actor).scl(0.5f));
	}
	
	public static void setCenterPosition(Actor actor, Vector2 position) {
		actor.setPosition(position.x - actor.getWidth()/2, position.y - actor.getHeight()/2);
	}
	
	public static void changePosition(Actor actor, Vector2 displacement) {
		var position = getPosition(actor);
		position.add(displacement);
		setPosition(actor, position);
	}
}
