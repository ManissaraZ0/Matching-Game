package th.ac.swu.gamelib;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class SelfDragListener extends InputListener {
	private Actor actor;
	private int mouseButton;
	
	public SelfDragListener(Actor actor, int mouseButton) {
		this.actor = actor;
		this.mouseButton = mouseButton;
	}

	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		if(button != mouseButton)
			return false;
		savePosition(new Vector2(x,y));
		return true;
	}
	
	private Vector2 dif;
	public void savePosition(Vector2 p) {
		// both in parent coordinate
		Vector2 click = actor.localToParentCoordinates(p);
		Vector2 origin = new Vector2(actor.getX(), actor.getY());
		dif = click.cpy().sub(origin);
	}

	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		var newClick = actor.localToParentCoordinates(new Vector2(x,y));
		var newOrigin = newClick.cpy().sub(dif);
		actor.setPosition(newOrigin.x, newOrigin.y);
	}
}

/*
 * 		System.out.println("Click = " + click);
		System.out.println("Origin = " + origin);
		System.out.println("getPosition = "+ actor.getX() + "," +actor.getY());
		System.out.println("dif = " + dif);

		System.out.println("NewClick = " + newClick);
		System.out.println("NewOrigin = " + newOrigin);
 * */
