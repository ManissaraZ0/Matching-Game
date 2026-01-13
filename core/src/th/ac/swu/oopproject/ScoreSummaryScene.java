package th.ac.swu.oopproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import th.ac.swu.gamelib.ui.MyImageButton;

public class ScoreSummaryScene extends Group {
	private SceneListener listener;
	private String clickedBtn = null;
	private UserInfo userInfo;
	private SoundTest sound = new SoundTest();

	public ScoreSummaryScene(SceneListener listener, UserInfo userInfo) {
		this.listener = listener;
		this.userInfo = userInfo;

		// Save data
		saveData("../assets/userInfo.txt", this.userInfo);

		// Background
		Texture bgTexture = TextureCache.get("scoreSummaryScene.png");
		var bgActor = new Image(bgTexture);
		addActor(bgActor);

		// Sound
		sound.setSound("tadaSound.ogg");
		addActor(sound);
		userInfo.setSound(sound);

		// Get score
		int scoreGame = userInfo.getScore().getScore();
		int scoreBonus = userInfo.getStarScore().getScore();
		// Total score
		int totalScore = scoreGame + scoreBonus;
		
		// Font Total Score
		var fontTotalScore = FontCache.get("KENTT.ttf", 250);

		var styleShadow = new LabelStyle(fontTotalScore, GameUtil.color(113, 76, 41));
		Label labelShadow = new Label(String.format("%d", totalScore), styleShadow);
		addActor(labelShadow);
		labelShadow.setPosition((1920 / 2) - 20, 480, Align.center);

		var style = new LabelStyle(fontTotalScore, Color.WHITE);
		Label label = new Label(String.format("%d", totalScore), style);
		addActor(label);
		label.setPosition(1920 / 2, 480, Align.center);

		// Font Score Details
		var fontDetail = FontCache.get("KENTT.ttf", 50);

		var styleDetailShadow = new LabelStyle(fontDetail, GameUtil.color(113, 76, 41));
		// Game score
		Label gameScoreShadow = new Label(String.format("Game score : %3d", scoreGame), styleDetailShadow);
		addActor(gameScoreShadow);
		gameScoreShadow.setPosition((1920 / 2) - 7, 300, Align.center);
		// Bonus score
		Label bonusScoreShadow = new Label(String.format("Bonus score : %3d", scoreBonus), styleDetailShadow);
		addActor(bonusScoreShadow);
		bonusScoreShadow.setPosition((1920 / 2) - 7, 250, Align.center);

		var styleDetail = new LabelStyle(fontDetail, Color.WHITE);
		// Game score
		Label gameScore = new Label(String.format("Game score : %3d", scoreGame), styleDetail);
		addActor(gameScore);
		gameScore.setPosition(1920 / 2, 300, Align.center);
		// Bonus score
		Label bonusScore = new Label(String.format("Bonus score : %3d", scoreBonus), styleDetail);
		addActor(bonusScore);
		bonusScore.setPosition(1920 / 2, 250, Align.center);

		// Retry Button
		Image retryBtnImage = new Image(TextureCache.get("retrybtn.png"));
		MyImageButton retryBtn = new MyImageButton(retryBtnImage);
		retryBtn.setPosition(54, 0);
		retryBtn.setName("retryBtn");
		addActor(retryBtn);
		addClickListener(retryBtn);

		// Home Button
		Image homeBtnImage = new Image(TextureCache.get("homebtn.png"));
		MyImageButton homeBtn = new MyImageButton(homeBtnImage);
		homeBtn.setPosition(1430, 0);
		homeBtn.setName("homeBtn");
		addActor(homeBtn);
		addClickListener(homeBtn);

		// Cross Hair สำหรับ Debugging
//		addActor(new CrossHair(1920 / 2, 400));
//		addActor(new CrossHair(1920 / 2, 180));

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
			retryBtn.setMuted(this.userInfo.getMuted());
			homeBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());

			hidePlaySoundBtn(playSoundBtn);
		});

		// ButtonListener muteSoundBtn
		muteSoundBtn.addButtonListener(() -> {
			sound.playMuteSound();
			this.userInfo.setMuted(false);

			sound.music.play();
			retryBtn.setMuted(this.userInfo.getMuted());
			homeBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());

			resetMuteSoundEvent(playSoundBtn);
		});

		// Check Muted
		if (this.userInfo.getMuted() == true) {
			sound.music.pause();
			hidePlaySoundBtn(playSoundBtn);
			retryBtn.setMuted(this.userInfo.getMuted());
			homeBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
		} else {
			sound.music.play();
			resetMuteSoundEvent(playSoundBtn);
			retryBtn.setMuted(this.userInfo.getMuted());
			homeBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
		}
	}

	// Getter & Setter ClickedBtn for check what scene will show next
	public String getClickedBtn() {
		return clickedBtn;
	}

	private void setClickedBtn(MyImageButton imageBtn) {
		clickedBtn = imageBtn.getName();
	}

	// Click Listener
	private void addClickListener(MyImageButton imageBtn) {
		imageBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Set clicked button for check what button clicked
				setClickedBtn(imageBtn);
				// Gdx.app.log(imageBtn.getName(), "clicked");
				addAction(Actions.sequence(Actions.fadeOut(0.5f), new RunnableActionEx(() -> {
					if (ScoreSummaryScene.this.listener != null) {
						ScoreSummaryScene.this.listener.sceneClosed(ScoreSummaryScene.this);
					}
				})));
			}
		});
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

	private static void saveData(String filename, UserInfo userInfo) {
		String name = userInfo.getName();
		int scoreGame = userInfo.getScore().getScore();
		int scoreBonus = userInfo.getStarScore().getScore();
		int totalScore = scoreGame + scoreBonus;

		FileHandle fileHandle = Gdx.files.internal(filename);
		try {
			// Open the file in append mode
			FileWriter fw = new FileWriter(fileHandle.file(), true);
			PrintWriter out = new PrintWriter(fw);
			// Append the user information to the file
			out.println(name + "|" + totalScore);
			// Close the file
			out.close();
		} catch (IOException e) {
			System.out.println("Error : " + e.getMessage());
		}
	}
	
	// Get UserInfo for send to next scene
	public UserInfo getUserInfo() {
		return this.userInfo;
	}
}
