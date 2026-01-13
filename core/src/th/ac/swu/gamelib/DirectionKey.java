package th.ac.swu.gamelib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class DirectionKey {
	private static final float cos45 = (float)Math.cos(Math.PI/4);
	
	public static Vector2 getDirection() {		
		var direction = getRawDirection();
		
		if(direction.x !=0 && direction.y != 0)
			direction.scl(cos45);
		
		return direction;
	}
	
	// not normalize (not multiply with cos45 when press many directions)
	public static Vector2 getRawDirection() {
		Vector2 direction = new Vector2();
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
			direction.y ++;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
			direction.y --;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
			direction.x --;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			direction.x ++;
		
		return direction;
	}

	public static Vector2 getRawJustPressedDirection() {
		Vector2 direction = new Vector2();
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
			direction.y ++;
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
			direction.y --;
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
			direction.x --;
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
			direction.x ++;
		
		return direction;
	}
	
	public static Vector2 getLeftRightDirection() {
		var direction = getRawDirection();
		direction.y = 0;
		return direction;
	}
}
