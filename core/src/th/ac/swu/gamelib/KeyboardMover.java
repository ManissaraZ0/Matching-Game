package th.ac.swu.gamelib;

import com.badlogic.gdx.math.Vector2;

public class KeyboardMover extends Mover {
	private float speed;

	public KeyboardMover(float speed) {
		super(Vector2.Zero);
		this.speed = speed;
	}

	@Override
	public boolean act(float delta_t) {
		Vector2 direction = DirectionKey.getDirection();
		super.setV(direction.cpy().scl(speed));
		return super.act(delta_t);
	}
}
