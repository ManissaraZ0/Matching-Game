package th.ac.swu.oopproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import th.ac.swu.gamelib.FontCache;
import th.ac.swu.gamelib.GameUtil;
import th.ac.swu.gamelib.RunnableActionEx;
import th.ac.swu.gamelib.TextureCache;
import th.ac.swu.gamelib.ui.MyImageButton;

public class EnterNameScene extends Group {
	private SceneListener listener;
	private Label labelName;
	private Label labelGuest;
	private UserInfo userInfo;

	public EnterNameScene(SceneListener listener, UserInfo userInfo) {
		this.listener = listener;
		this.userInfo = userInfo;

		// Background
		Texture bgTexture = TextureCache.get("enterNameScene.png");
		var bgActor = new Image(bgTexture);
		addActor(bgActor);

		// Enter Name Box
		Image NameBox = new Image(TextureCache.get("namebox.png"));
		NameBox.setOrigin(Align.center);
		NameBox.setPosition(1920 / 2, 400, Align.center);
		NameBox.setName("nameBox");
		addActor(NameBox);

		// Next Button
		Image nextBtnImage = new Image(TextureCache.get("nextbtn.png"));
		MyImageButton nextBtn = new MyImageButton(nextBtnImage);
		nextBtn.setPosition(1920 / 2, 160, Align.center);
		nextBtn.setName("howToBtn");
		addActor(nextBtn);
		addClickListener(nextBtn);
		nextBtn.addButtonListener(() -> this.userInfo.setName((name.length() != 0) ? name : "GUEST"));

		// Create Font
		var font = FontCache.get("KENTT.ttf", 75);

		// Show guest
		var styleguest = new LabelStyle(font, GameUtil.color(204, 200, 196));
		labelGuest = new Label("GUEST", styleguest);
		if (getUserName() != null) {
			addActor(labelGuest);
			labelGuest.setPosition(550, 400);
		}

		// Show Name
		var styleName = new LabelStyle(font, GameUtil.color(113, 76, 41));
		labelName = new Label(getUserName(), styleName);
		addActor(labelName);
		labelName.setPosition(550, 445);
//		System.out.println(getUserName());

		// Sound Button
		SoundTest sound = new SoundTest("bump.wav");
		this.userInfo.setSound(sound);

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
			nextBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());

			hidePlaySoundBtn(playSoundBtn);
		});

		// ButtonListener muteSoundBtn
		muteSoundBtn.addButtonListener(() -> {
			sound.playMuteSound();
			this.userInfo.setMuted(false);

			sound.music.play();
			nextBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());

			resetMuteSoundEvent(playSoundBtn);
		});

		if (this.userInfo.getMuted() == true) {
			hidePlaySoundBtn(playSoundBtn);
			nextBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
		} else {
			resetMuteSoundEvent(playSoundBtn);
			nextBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
		}

		// Cross Hair สำหรับ Debugging
//		addActor(new CrossHair(1920 / 2, 400));
//		addActor(new CrossHair(1920 / 2, 180));
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		for (int i = 0; i < 256; i++) {
			if (Gdx.input.isKeyJustPressed(i)) {
				int pressedKey = i;
				if (pressedKey != -1) {
					processKey(pressedKey);
				}
			}
		}
	}

	private String name = "";

	private void processKey(int keycode) {
		if (keycode == Input.Keys.ENTER) {
			this.userInfo.setName((name.length() != 0) ? name : "GUEST");
			addListener();
		} else if (keycode == Input.Keys.BACKSPACE && name.length() > 0) {
			name = name.substring(0, name.length() - 1);
		} else {
			String character = getCharacterForKeyCode(keycode);
			if (character != null && name.length() <= 10) {
				name += character;
			}
		}

		setUserName(name);
		updateLabel();
	}

	private String getCharacterForKeyCode(int keycode) {
		if ((keycode >= Input.Keys.A && keycode <= Input.Keys.Z)
				|| (keycode >= Input.Keys.NUM_0 && keycode <= Input.Keys.NUM_9)) {
			// Convert the keycode to its corresponding character
			return Input.Keys.toString(keycode);
		}
		return null; // Return null if the keycode doesn't represent a character
	}

	private String getUserName() {
		return name;
	}

	private void updateLabel() {
		labelName.setText(getUserName());
		if (name.length() != 0) {
			labelGuest.setText("");
		} else {
			labelGuest.setText("GUEST");
		}
	}

	private void setUserName(String newName) {
		name = newName;
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

	// Click Listener
	private void addClickListener(MyImageButton imageBtn) {
		imageBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				Gdx.app.log(imageBtn.getName(), "clicked");
				addAction(Actions.sequence(Actions.fadeOut(0.5f), new RunnableActionEx(() -> {
					if (EnterNameScene.this.listener != null) {
						EnterNameScene.this.listener.sceneClosed(EnterNameScene.this);
					}
				})));
			}
		});
	}

	private void addListener() {
		addAction(Actions.sequence(Actions.fadeOut(0.5f), new RunnableActionEx(() -> {
			if (EnterNameScene.this.listener != null) {
				EnterNameScene.this.listener.sceneClosed(EnterNameScene.this);
			}
		})));
	}

	// Get UserInfo for send to next scene
	public UserInfo getUserInfo() {
		return this.userInfo;
	}
}
