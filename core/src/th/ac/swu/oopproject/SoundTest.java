package th.ac.swu.oopproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import th.ac.swu.gamelib.TextureCache;
import th.ac.swu.gamelib.ui.MyImageButton;

public class SoundTest extends Actor {
	Music music;
	Sound sound;
	Sound soundbtn;
	private boolean isMuted;
	
	public SoundTest() {
	}
	
	public SoundTest(String musicBackground) {
		music = Gdx.audio.newMusic(Gdx.files.internal(musicBackground));
		music.play();
		
		sound = Gdx.audio.newSound(Gdx.files.internal("bump.wav"));
		
		/*this.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				processKey(keycode);
				return false;
			}			
		});*/
		
		isMuted = false;
	}
	
	public void SoundButton(String sound) {
		soundbtn = Gdx.audio.newSound(Gdx.files.internal(sound));
		soundbtn.play();
	}
	
	public void clearSound() {
        if (music != null) {
            music.stop();
            music.dispose();
        }
        if (sound != null) {
            sound.stop();
            sound.dispose();
        }
    }
	
	public void setSound(String musicBackground) {
        if (music != null) {
            music.stop();
            music.dispose();
        }
        music = Gdx.audio.newMusic(Gdx.files.internal(musicBackground));
        music.play();
    }
	
	// Check mute status and change it
	public void playStopSound() {
        if (!isMuted) {
            music.pause();
        } else {
            music.play();
        }
        isMuted = !isMuted;
    }
	
	// Call when click mute button
	public void playMuteSound() {
		processKey(Input.Keys.S);
    }
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.S))
			processKey(Input.Keys.S);
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
			processKey(Input.Keys.SPACE);
	}

	private void processKey(int keycode) {
		// System.out.println("In Key");
		if(keycode == Input.Keys.SPACE)
			sound.play();
		else if(keycode == Input.Keys.S)
		{
			// System.out.println("S Key");
			music.setPosition(0);
//			if(!music.isPlaying())
//				music.play();
//			else
//				music.pause();
			playStopSound();
		}
	}
	
}
