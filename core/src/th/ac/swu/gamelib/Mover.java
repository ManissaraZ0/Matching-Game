package th.ac.swu.gamelib;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;

public class Mover extends Action {
	private Vector2 v;
	
	public Vector2 getV() { return v.cpy(); }
	public void setV(Vector2 v) { this.v = v.cpy(); } 

	public Mover(Vector2 v) {
		this.v = v.cpy();
	}

	@Override
	public boolean act(float delta_t) {
		if(target == null)
			return false;
		
		//**pos = oldPos + v*delta_t  (from physics formula: s = s0 + v*t)
		var pos = Actors.getPosition(target).add(v.cpy().scl(delta_t));
		Actors.setPosition(target, pos);
		return false;
	}

}
