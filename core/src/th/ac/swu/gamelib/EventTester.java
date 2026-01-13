package th.ac.swu.gamelib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class EventTester extends InputListener {
	
	private String format(float x, float y) {
		return String.format(" (x=%f,y=%f)", x, y);
	}
	private String formatAmount(float x, float y) {
		return String.format(" (amountX=%f,amountY=%f)", x, y);
	}
	
	private void log(InputEvent event, float x, float y, int pointer, int button) {
		Gdx.app.log(event.toString(), String.format("%s; %d, %d",format(x,y), pointer, button));		
	}
	
	private void log(InputEvent event, float x, float y, int pointer) {
		Gdx.app.log(event.toString(), String.format("%s; %d",format(x,y), pointer));		
	}
	
	private void log(InputEvent event, float x, float y, int pointer, Actor actor, String fromTo) {
		Gdx.app.log(event.toString(), String.format("%s; %d; %s=%s",format(x,y), pointer, fromTo, actor));		
	}
	
	private void log(InputEvent event, int keycode) {
		Gdx.app.log(event.toString(), String.format("Keycode=%d", keycode));		
	}

	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		log(event, x, y, pointer, button);
		super.touchDown(event, x, y, pointer, button);
		return true;
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		log(event, x, y, pointer, button);
		super.touchUp(event, x, y, pointer, button);
	}

	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		log(event, x, y, pointer);
		super.touchDragged(event, x, y, pointer);
	}

	@Override
	public boolean mouseMoved(InputEvent event, float x, float y) {
		Gdx.app.log(event.toString(), String.format("%s",format(x,y)));
		return super.mouseMoved(event, x, y);
	}

	@Override
	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		log(event, x, y, pointer, fromActor, "fromActor");
		super.enter(event, x, y, pointer, fromActor);
	}

	@Override
	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
		log(event, x, y, pointer, toActor, "toActor");
		super.exit(event, x, y, pointer, toActor);
	}

	@Override
	public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
		Gdx.app.log(event.toString(), String.format("%s;%s",format(x,y), formatAmount(amountX, amountY)));
		return super.scrolled(event, x, y, amountX, amountY);
	}

	//-----------------Keyboard Events ---------------------
	@Override
	public boolean keyDown(InputEvent event, int keycode) {
		log(event, keycode);
		return super.keyDown(event, keycode);
	}
	@Override
	public boolean keyUp(InputEvent event, int keycode) {
		log(event, keycode);
		return super.keyUp(event, keycode);
	}
	@Override
	public boolean keyTyped(InputEvent event, char character) {
		Gdx.app.log(event.toString(), String.format("Character='%c'", character));		
		return super.keyTyped(event, character);
	}
	
	
}
