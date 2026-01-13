package th.ac.swu.gamelib.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public abstract class GenericButton extends Group {

	public GenericButton(Vector2 size) {
		this.setWidth(size.x);
		this.setHeight(size.y);
		
		addEvents();
	}
	
	private MyButtonListener listener = null;
	public void addButtonListener(MyButtonListener listener) 
	{
		if(listener != null && this.listener != null)
			throw new RuntimeException("GenericButton : cannot add more than one MyButtonListener!!!");
		this.listener = listener; 
	}
	
	protected enum ButtonState { Pressed, Highlight, Normal };
    private boolean bPressed = false; // กดโดนที่ปุ่ม
    private boolean bHover = false;   // Mouse อยู่บริเวณปุ่ม

	protected abstract void updateVisual(ButtonState state);
	
	private void addEvents() {
		addListener(new InputListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				bHover = true;
				updateVisual();
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if(pointer != -1)
					return;
				bHover = false;
				updateVisual();
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(pointer != Input.Buttons.LEFT)
					return false;
				
				if(bHover)
					bPressed = true;
				updateVisual();
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if(pointer != Input.Buttons.LEFT)
					return;

				if (bPressed && bHover)
					if(listener != null)
						listener.Clicked(); // ยิง event

				bPressed = false;
				updateVisual();
			}
		});
	}
	
    private void updateVisual()
    {
        ButtonState state;
        if (bHover && bPressed)
            state = ButtonState.Pressed;
        else if (bHover && !bPressed || bPressed && !bHover)
            state = ButtonState.Highlight;
        else
            state = ButtonState.Normal;

        updateVisual(state);
    }
}
