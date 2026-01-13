package th.ac.swu.gamelib.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import th.ac.swu.gamelib.Actors;
import th.ac.swu.gamelib.GameUtil;

public class MyImageButton extends GenericButton {
	private Label label;
	private Image bgImage;
	private boolean isMuted = false;
	
    public void setTextColor (Color color) { label.setColor(color); }
    public Color HighlightColor = GameUtil.color(229, 241, 251);
    public Color PressedColor = GameUtil.color(204, 228, 247);

	public MyImageButton(Image bgImage) {
		this(bgImage, null, null, Color.BLACK, false);
	}

	public MyImageButton(Image bgImage, String text, BitmapFont font, Color fontColor, boolean isMuted) {
		super(Actors.getSize(bgImage).scl(bgImage.getScaleX(), bgImage.getScaleY()));
		var center = Actors.getCenterPosition(this);

		this.bgImage = bgImage;
		bgImage.setX(0);
		bgImage.setY(0);
		addActor(bgImage);

		if(text != null && font != null) {
			var style = new LabelStyle(font, fontColor);
			label = new Label(text, style);
			addActor(label);
			Actors.setCenterPosition(label, center);
		}
		
		this.isMuted = false;
	}
	
	// setBgImage
	public void setBgImage(Image bgImage) {
		this.bgImage = bgImage;
	}
	
	// Set IsMuted
	public void setMuted(boolean isMuted) {
		this.isMuted = isMuted;
	}

	@Override
	protected void updateVisual(ButtonState state) {
        if (state == ButtonState.Pressed) {
            bgImage.setColor(PressedColor);
            if (this.isMuted == false) {
//            	System.out.println("Check Pressed Sound");
            	addActor(new SoundButton("TukKueDud.ogg"));
            }
        } else if (state == ButtonState.Highlight) {
        	bgImage.setColor(HighlightColor);
        	if (this.isMuted == false) {
//        		System.out.println("Check Highlight Sound");
            	addActor(new SoundButton("DubBu.ogg"));
            }
        } else {
        	bgImage.setColor(Color.WHITE);
        }
    }
}
