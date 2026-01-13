package th.ac.swu.oopproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
import th.ac.swu.gamelib.ui.CrossHair;
import th.ac.swu.gamelib.ui.MyImageButton;

public class HighScoreScene extends Group {
	private SceneListener listener;
	private UserInfo userInfo;

	public HighScoreScene(SceneListener listener, UserInfo userInfo) {
		this.listener = listener;
		this.userInfo = userInfo;

		// Background
		Texture bgTexture = TextureCache.get("highScoreScene.png");
		var bgActor = new Image(bgTexture);
		addActor(bgActor);

		// Read File
		List<User> userList = readFile("../assets/userInfo.txt");

		// Sorting by descending order
		Collections.sort(userList, Collections.reverseOrder());

		for (int i = 0; i < ((userList.size() > 5) ? 5 : userList.size()); i++) {
			User user = userList.get(i);
			// For debugging
			// System.out.println(user.getName());
			// System.out.println(user.getScore());

			// Create Font
			var font = FontCache.get("KENTT.ttf", 50);
			var style = new LabelStyle(font, GameUtil.color(118, 42, 0));

			Label name = new Label(user.getName(), style);
			addActor(name);
			name.setPosition(436, 650 - (100 * i), Align.left);
    		// addActor(new CrossHair(436, 650 - (100 * i)));

			Label score = new Label(user.getScore(), style);
			addActor(score);
			score.setPosition(1495, 650 - (100 * i), Align.right);
    		// addActor(new CrossHair(1495, 650 - (100 * i)));
		}

		// Back Button
		Image backBtnImage = new Image(TextureCache.get("backShellBtn.png"));
		MyImageButton backBtn = new MyImageButton(backBtnImage);
		backBtn.setPosition(250, 325, Align.center);
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
			backBtn.setMuted(true);
			muteSoundBtn.setMuted(true);

			hidePlaySoundBtn(playSoundBtn);
		});

		// ButtonListener muteSoundBtn
		muteSoundBtn.addButtonListener(() -> {
			sound.playMuteSound();
			this.userInfo.setMuted(false);

			sound.music.play();
			backBtn.setMuted(false);
			muteSoundBtn.setMuted(false);

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

	// Click Listener
	private void addClickListener(MyImageButton imageBtn) {
		imageBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Gdx.app.log(imageBtn.getName(), "clicked");
				addAction(Actions.sequence(Actions.fadeOut(0.5f), new RunnableActionEx(() -> {
					if (HighScoreScene.this.listener != null) {
						HighScoreScene.this.listener.sceneClosed(HighScoreScene.this);
					}
				})));
			}
		});
	}

	// For read file
	private static List<User> readFile(String filename) {
		List<User> users = new ArrayList<>();
		FileHandle fileHandle = Gdx.files.internal(filename);
		try (BufferedReader reader = new BufferedReader(fileHandle.reader())) {
			String line;
			while ((line = reader.readLine()) != null) {
				// Split by |
				String[] parts = line.split("\\|");
				// Check it really 2 part: name and score
				if (parts.length == 2) {
					String name = parts[0]; // Name
					int score = Integer.parseInt(parts[1]); // Score
					User u = new User(name, Integer.toString(score));
					users.add(u);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found : " + filename);
		} catch (IOException e) {
			System.out.println("Error : " + e.getMessage());
		}

		return users;
	}
	
	// Get UserInfo for send to next scene
	public UserInfo getUserInfo() {
		return this.userInfo;
	}
}
