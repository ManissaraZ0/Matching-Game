package th.ac.swu.gamelib.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;

import th.ac.swu.gamelib.Actors;

public class CameraMan extends Actor {
	private Viewport viewport;
	
	public CameraMan(Viewport viewport) {
		this.viewport = viewport;
	}
	
	@Override
	public void act(float delta) {
		var position = this.localToStageCoordinates(new Vector2());
		viewport.getCamera().position.x = position.x;
		viewport.getCamera().position.y = position.y;
		super.act(delta);
	}

}
