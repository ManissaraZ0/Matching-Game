package th.ac.swu.oopproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

import th.ac.swu.gamelib.FontCache;
import th.ac.swu.gamelib.GameUtil;
import th.ac.swu.gamelib.RandomUtil;
import th.ac.swu.gamelib.RunnableActionEx;
import th.ac.swu.gamelib.TextureCache;
import th.ac.swu.gamelib.animation.MyAnimation;
import th.ac.swu.gamelib.animation.RegionSelector;
import th.ac.swu.gamelib.collision.CollisionDetection;
import th.ac.swu.gamelib.ui.BoundRectTracker;
import th.ac.swu.gamelib.ui.MyImageButton;

public class BonusScene extends Group {
	private SceneListener listener;
	private StarScore sc;
	private int scoreGame;
	private UserInfo userInfo;
	private SoundTest sound = new SoundTest();

	public BonusScene(SceneListener listener, Stage stage, UserInfo userInfo) {
		this.setBounds(0, 0, stage.getWidth(), stage.getHeight());
		this.listener = listener;
		// this.scoreGame = scoreGame;
		this.userInfo = userInfo;

		// Background
		bcScene();

		// Sound
		sound.setSound("bonusTime.ogg");
		addActor(sound);
		userInfo.setSound(sound);

		// Bonus Label
		var font = FontCache.get("KENTT.ttf", 150);
		var style = new LabelStyle(font, Color.WHITE);
		Label label = new Label("Bonus Time", style);
		addActor(label);
		label.setPosition(this.getWidth() / 2, 900, Align.center);

		// Score
		sc = StarScore.getInstance();
		addActor(sc);
		sc.clear();
		sc.setPosition((this.getWidth() / 2) + 250, 780, Align.center);

		// Set Score
		userInfo.setStarScore(sc);

		// Time
		var fontTime = FontCache.get("KENTT.ttf", 50);
		Time time = new Time(fontTime, Color.WHITE);
		addActor(time);
		time.setPosition((this.getWidth() / 2) - 252, 780, Align.center);
		addTimeEvent(time);

		// Create crab
		Group crab = new Crab();
		crab.setPosition(797, 50);
		crab.setName("crab");
		addActor(crab);
		// addActor(new BoundRectTracker(crab));

		// Create floor & wall
		Block floor = new Block(0, 0, 1920, 50, Color.CLEAR);
		floor.setName("floor");
		addActor(floor);

		Block wallLeft = new Block(300, 10, 50, 1870, Color.CLEAR);
		wallLeft.setName("wallLeft");
		addActor(wallLeft);

		Block wallRight = new Block(1570, 50, 50, 1870, Color.CLEAR);
		wallRight.setName("wallRight");
		addActor(wallRight);

		// Star
		List<Star> stars = new ArrayList<>(); // For collecting all star for mute sound
		// Random birth of star
		var birthOfStar = new Vector2(RandomUtil.random(300, 1500), 1080);
		this.addAction(Actions.delay(0.5f, new RunnableActionEx(() -> {
			// System.out.println(time.getTime());
			
			// Random the number of stars
			int totalStar = RandomUtil.randomInt(30,35);
			for (int i = 0; i < totalStar; ++i) {
				// Random delay time star will fall
				float randomNumber = RandomUtil.random() * 0.5f;
				// System.out.println("Random number between 0.0f and 0.5f: " + randomNumber);
				float delayTime = i * randomNumber;
				this.addAction(Actions.sequence(Actions.delay(delayTime), new RunnableActionEx(() -> {
					// Create star
					Star star = new Star(birthOfStar);
					addActor(star);
					// For Sound
					soundListener(star);
					stars.add(star);
				})));
			}
		})));

		this.addAction(Actions.delay(0.5f, new RunnableActionEx(() -> {
			Timer.schedule(new Timer.Task() {
				@Override
				public void run() {
					if (time.getTime() == 0) {
						// If time's up, close this scene
						addAction(Actions.sequence(Actions.fadeOut(0.5f), new RunnableActionEx(() -> {
							if (BonusScene.this.listener != null) {
								sound.clearSound();
								BonusScene.this.listener.sceneClosed(BonusScene.this);
							}
						})));
					}
				}
			}, 0, 0.5f); // Check the condition every 0.5 seconds
		})));

		// collisionDetection.AddDetector(0, 1);

		// collisionDetection.AddDetector(0, 2);

		// this.debugAll();

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
			muteSoundBtn.setMuted(this.userInfo.getMuted());
			for (Star star : stars) {
				star.setMuted(this.userInfo.getMuted());
			}

			hidePlaySoundBtn(playSoundBtn);
		});

		// ButtonListener muteSoundBtn
		muteSoundBtn.addButtonListener(() -> {
			sound.playMuteSound();
			this.userInfo.setMuted(false);

			sound.music.play();
			muteSoundBtn.setMuted(this.userInfo.getMuted());
			for (Star star : stars) {
				star.setMuted(this.userInfo.getMuted());
			}

			resetMuteSoundEvent(playSoundBtn);
		});

		// Check Muted
		if (this.userInfo.getMuted() == true) {
			sound.music.pause();
			hidePlaySoundBtn(playSoundBtn);
			muteSoundBtn.setMuted(this.userInfo.getMuted());
		} else {
			sound.music.play();
			resetMuteSoundEvent(playSoundBtn);
			muteSoundBtn.setMuted(this.userInfo.getMuted());
		}
	}

	// Get Game Score
	public int getScoreGame() {
		return scoreGame;
	}

	// Get Bonus Score
	public int getScore() {
		return sc.getScore();
	}

	// Background animation
	private void bcScene() {
		// 1. Load Texture
		var texture = TextureCache.get("bonusScene.png");
		// 2. Split to Regions 2D
		var regions2d = TextureRegion.split(texture, 400, 200);
		// 3. Create "Selector"
		var selector = new RegionSelector(regions2d);
		// 4. Create "Image" Actor
		Image actor = new Image(regions2d[0][0]);
		var regions1d = selector.from(0, 14);
		actor.scaleBy(4.5f);
		actor.setPosition(0, 0);
		addActor(actor);
		actor.addAction(new MyAnimation(actor, regions1d, 2.0f));
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
	
	private void soundListener(Star star) {
		if (this.userInfo.getMuted() == true) {
			star.setMuted(this.userInfo.getMuted());
		} else {
			star.setMuted(this.userInfo.getMuted());
		}
	}

	// Bonus time
	private void addTimeEvent(Time time) {
		this.addAction(Actions.delay(0.5f, new RunnableActionEx(() -> {
			time.startCountdown(10);
		})));
	}

	// Get UserInfo for send to next scene
	public UserInfo getUserInfo() {
		return this.userInfo;
	}
}
