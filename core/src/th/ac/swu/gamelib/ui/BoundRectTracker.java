package th.ac.swu.gamelib.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import th.ac.swu.gamelib.Actors;

public class BoundRectTracker extends Group {
	private HollowRect hollow;
	private Actor actor;

	public BoundRectTracker(Actor actor) {
		this.actor = actor;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(hollow != null)
			hollow.remove();
		hollow = new HollowRect(Actors.getBounds(actor), Color.RED, 2.0f);
		addActor(hollow);
	}
}
