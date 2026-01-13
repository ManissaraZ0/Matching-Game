package th.ac.swu.oopproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import th.ac.swu.gamelib.RunnableActionEx;
import th.ac.swu.gamelib.TextureCache;
import th.ac.swu.gamelib.animation.MyAnimation;
import th.ac.swu.gamelib.animation.RegionSelector;
import th.ac.swu.gamelib.ui.MyImageButton;

public class HowToScene extends Group {
	private SceneListener listener;
	private UserInfo userInfo;

	public HowToScene(SceneListener listener, UserInfo userInfo) {
		this.listener = listener;
		this.userInfo = userInfo;

		// Background
		Texture bgTexture = TextureCache.get("howToScene.png");
		var bgActor = new Image(bgTexture);
		addActor(bgActor);

		// Back Button
		Image backBtnImage = new Image(TextureCache.get("backbtn.png"));
		MyImageButton backBtn = new MyImageButton(backBtnImage);
		backBtn.setPosition(325, 250, Align.center);
		backBtn.setName("backBtn");
		addActor(backBtn);
		addClickListener(backBtn);

		// Sound Button
		SoundTest sound = userInfo.getSound();
		
		Image muteSoundBtnImage = new Image(TextureCache.get("muteSound.png"));
		MyImageButton muteSoundBtn = new MyImageButton(muteSoundBtnImage);
		muteSoundBtn.setPosition(1820, 100, Align.center);
		muteSoundBtn.setName("SoundBtn");
		addActor(muteSoundBtn);

		Image playSoundBtnImage = new Image(TextureCache.get("playSound.png"));
		MyImageButton playSoundBtn = new MyImageButton(playSoundBtnImage);
		playSoundBtn.setPosition(1820, 100, Align.center);
		playSoundBtn.setName("playSoundBtn");
		addActor(playSoundBtn);

		// ButtonListener playSoundBtn
		playSoundBtn.addButtonListener(() -> {
			sound.playMuteSound();
			this.userInfo.setMuted(true);

			sound.music.pause();
			backBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());

			hidePlaySoundBtn(playSoundBtn);
		});

		// ButtonListener muteSoundBtn
		muteSoundBtn.addButtonListener(() -> {
			sound.playMuteSound();
			this.userInfo.setMuted(false);

			sound.music.play();
			backBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());

			resetMuteSoundEvent(playSoundBtn);
		});
		
		if (this.userInfo.getMuted() == true) {
			hidePlaySoundBtn(playSoundBtn);
			backBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
		} else {
			resetMuteSoundEvent(playSoundBtn);
			backBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
		}
	}

	// Sound Event
	private void hidePlaySoundBtn(MyImageButton btn) {
		btn.addAction(Actions.visible(false));
		btn.setTouchable(Touchable.disabled);
	}

	private void resetMuteSoundEvent(MyImageButton btn) {
		btn.addAction(Actions.visible(true));
		btn.setTouchable(Touchable.enabled);
	}

	private void addClickListener(MyImageButton imageBtn) {
		imageBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				Gdx.app.log(imageBtn.getName(), "clicked");
				addAction(Actions.sequence(Actions.fadeOut(1.5f), new RunnableActionEx(() -> {
					if (HowToScene.this.listener != null) {
						HowToScene.this.listener.sceneClosed(HowToScene.this);
					}
				})));
			}
		});
	}
	
	// Get UserInfo for send to next scene
	public UserInfo getUserInfo() {
		return this.userInfo;
	}
}
