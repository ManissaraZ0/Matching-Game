package th.ac.swu.oopproject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import th.ac.swu.gamelib.FontCache;
import th.ac.swu.gamelib.GameUtil;

public class StarScore extends Group {
	private Label label;
    private int score = 0;
    
    private static StarScore sc = new StarScore();
    
    public StarScore() {
    	var font = FontCache.get("KENTT.ttf", 50);
		var style = new LabelStyle(font, Color.WHITE);
		label = new Label(getScoreText(), style);
		addActor(label);
		setSize(label.getWidth(), label.getHeight());
	}
    
    public static StarScore getInstance() {
    	return sc;
    }

    public void clear() {
		score = 0;
		updateLabel();
	}

    // Method to increase the score
    public void increaseScore(int points) {
        score += points;
        updateLabel();
    }

    // Method to decrease the score
    public void decreaseScore(int points) {
        score -= points;
        if (score < 0) {
            score = 0; // Ensure score never goes negative
            updateLabel();
        }
    }
    
    private void updateLabel() {
		label.setText(getScoreText());
	}

    public int getScore() {
        return score;
    }
    
    public String getScoreText() {
		return String.format("SCORE : %3d", score);
	}
}