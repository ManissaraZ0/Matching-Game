package th.ac.swu.gamelib.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import th.ac.swu.gamelib.Actors;
import th.ac.swu.gamelib.GameUtil;

public class MyTextButton extends GenericButton {
	private Label label;
	private HollowRect outline;
	private RectangleActor bg;
	
    public Color NormalColor = GameUtil.color(225, 225, 225);
    public Color HighlightColor = GameUtil.color(229, 241, 251);
    public Color PressedColor = GameUtil.color(204, 228, 247);

    public Color NormalColorLine = GameUtil.color(173, 173, 173);
    public Color HighlightColorLine = GameUtil.color(0, 120, 215);
    public Color PressedColorLine = GameUtil.color(0, 84, 153);

    public void setTextColor(Color color) {
    	label.setColor(color);
    }

	public MyTextButton(String text, BitmapFont font, Vector2 size) {
		super(size);
		var center = Actors.getCenterPosition(this);

		bg = new RectangleActor(0, 0, size.x, size.y, NormalColor);
		addActor(bg);
		
		outline = new HollowRect(size.x, size.y, NormalColorLine, 2);
		addActor(outline);
		
		var style = new LabelStyle(font, Color.BLACK);
		label = new Label(text, style);
		addActor(label);
		Actors.setCenterPosition(label, center);
	}

	@Override
	protected void updateVisual(ButtonState state) {
        if (state == ButtonState.Pressed)
            CreateBackgroundRect(PressedColor, PressedColorLine);
        else if (state == ButtonState.Highlight)
            CreateBackgroundRect(HighlightColor, HighlightColorLine);
        else
            CreateBackgroundRect(NormalColor, NormalColorLine);
	}
    private void CreateBackgroundRect(Color fillColor, Color outlineColor)
    {
    	bg.setColor(fillColor);
    	outline.setColor(outlineColor);
    }

}
