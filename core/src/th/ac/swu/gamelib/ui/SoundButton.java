package th.ac.swu.gamelib.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SoundButton extends Actor {
	Sound soundbtn;
	
	public SoundButton() {
	}
	
	public SoundButton(String sound) {
		soundbtn = Gdx.audio.newSound(Gdx.files.internal(sound));
		soundbtn.play();
	}
}
