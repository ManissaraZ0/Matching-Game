package th.ac.swu.oopproject;

import com.badlogic.gdx.scenes.scene2d.Group;

public class UserInfo extends Group {
	String name;
	Score score;
	StarScore starScore;
	SoundTest sound;
	boolean isMuted = false;
	
	public UserInfo(String name, Score score, StarScore starScore, SoundTest sound, boolean isMuted) {
		this.name = name;
        this.score = score;
        this.starScore = starScore;
        this.sound = sound;
        this.isMuted = isMuted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public StarScore getStarScore() {
		return starScore;
	}

	public void setStarScore(StarScore starScore) {
		this.starScore = starScore;
	}

	public SoundTest getSound() {
		return sound;
	}

	public void setSound(SoundTest sound) {
		this.sound = sound;
	}
	
	public boolean getMuted() {
		return isMuted;
	}

	public void setMuted(boolean isMuted) {
		this.isMuted = isMuted;
	}
}
