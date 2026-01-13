package th.ac.swu.oopproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Timer;

public class Time extends Group {
	private Label label;
	private int time = 0;
	private Timer.Task countdownTask;

	public Time(BitmapFont font, Color color) {
		var style = new LabelStyle(font, color);
		label = new Label(getTimeText(), style);
		addActor(label);
		setSize(label.getWidth(), label.getHeight());
	}
	
	public void clear() {
		time = 0;
		updateLabel();
	}
	
	public void increment(int seconds) {
		time += seconds;
		updateLabel();
	}
	
	public void startCountdown(int seconds) {
	    time = seconds; // Set initial time
	    updateLabel(); // Update label with initial time

	    countdownTask = new Timer.Task() {
	        @Override
	        public void run() {
	            time--;
	            updateLabel();

	            if (time <= 0) {
	                // Countdown finished
	                this.cancel(); // Stop the timer
	                updateLabel(); // Update label
	            }
	        }
	    };

	    // Schedule the countdown task to repeat every 1 second
	    Timer.schedule(countdownTask, 1f, 1f);
	}
	
	public void hide() {
        // Stop the countdown timer when the screen is hidden
        if (countdownTask != null) {
            countdownTask.cancel();
        }
    }

	// Get Time
	public int getTime() {
		return time;
	}

	// Get label
	public Label getLabel() {
		return label;
	}

	public Timer.Task getCountdownTask() {
		return countdownTask;
	}

	// Update label
	private void updateLabel() {
		label.setText(getTimeText());
	}
	
	private String getTimeText() {
		return String.format("TIME : %6d ", time);
	}
}
