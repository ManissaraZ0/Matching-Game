package th.ac.swu.oopproject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Score extends Group {
	private Label label;
	private int score = 0;

	public Score(BitmapFont font, Color color) {
		var style = new LabelStyle(font, color);
		label = new Label(getScoreText(), style);
		addActor(label);
		setSize(label.getWidth(), label.getHeight());
	}
	
	public void clear() {
		score = 0;
		updateLabel();
	}
	
	public void increment(int point) {
		score += point;
		updateLabel();
	}
	
	@Deprecated
	public void doubleScore() {
		score *= 2;
		updateLabel();
	}

	private void updateLabel() {
		label.setText(getScoreText());
	}
	
	public int getScore() {
		return score;
	}
	
	private String getScoreText() {
		return String.format("SCORE : %5d ", score);
	}
}
