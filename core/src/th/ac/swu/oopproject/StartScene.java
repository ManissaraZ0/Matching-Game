package th.ac.swu.oopproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import th.ac.swu.gamelib.RandomUtil;
import th.ac.swu.gamelib.RunnableActionEx;
import th.ac.swu.gamelib.TextureCache;
import th.ac.swu.gamelib.animation.MyAnimation;
import th.ac.swu.gamelib.animation.RegionSelector;
import th.ac.swu.gamelib.ui.CrossHair;
import th.ac.swu.gamelib.ui.MyImageButton;

public class StartScene extends Group {
	private SceneListener listener;
	private String clickedBtn = null;
	private UserInfo userInfo;
	private SoundTest sound = new SoundTest();

	public StartScene(SceneListener listener, UserInfo userInfo) {
		this.listener = listener;
		this.userInfo = userInfo;
		
		// Background
		Texture bgTexture = TextureCache.get("startScene.png");
		var bgActor = new Image(bgTexture);
		addActor(bgActor);
		
		// Wave
		var texture = TextureCache.get("waveBeach.png");
		var regions2d = TextureRegion.split(texture, 692, 502);
		var selector = new RegionSelector(regions2d);
		Image actor = new Image(regions2d[0][0]);
		var regions1d = selector.from(0, 9);
		actor.setPosition(-5, -800);
		actor.scaleBy(1.8f);
		addActor(actor);
		actor.addAction(new MyAnimation(actor, regions1d, 2.0f));
		
		// Sound
		sound.setSound("startSound.ogg");
		addActor(sound);
		
		// Start Button
		Image startBtnImage = new Image(TextureCache.get("startbtn2.png"));
		MyImageButton startBtn = new MyImageButton(startBtnImage);
		startBtn.setPosition(1920 / 2, 380, Align.center);
		startBtn.setName("startBtn");
		addActor(startBtn);
		addClickListener(startBtn);
		startBtn.addButtonListener(() -> sound.music.pause());
		
		// How to Button
		Image howToBtnImage = new Image(TextureCache.get("howtobtn2.png"));
		MyImageButton howToBtn = new MyImageButton(howToBtnImage);
		howToBtn.setPosition((1920 / 2) - 247, 160, Align.center);
		howToBtn.setName("howToBtn");
		addActor(howToBtn);
		addClickListener(howToBtn);
		
		// Quit Button
		Image quitBtnImage = new Image(TextureCache.get("quitbtn2.png"));
		MyImageButton quitBtn = new MyImageButton(quitBtnImage);
		quitBtn.setPosition((1920 / 2) + 257, 160, Align.center);
		quitBtn.setName("quitBtn");
		addActor(quitBtn);
		quitBtn.addButtonListener(() -> Gdx.app.exit());
		
		// Trophy
		var textureTrophy = TextureCache.get("trophy.png");
		var regions2dTrophy = TextureRegion.split(textureTrophy, 468, 498);
		var selectorTrophy = new RegionSelector(regions2dTrophy);
		Image trophy = new Image(regions2dTrophy[0][0]);
		var regions1dTrophy = selectorTrophy.from(0, 64);
		trophy.setSize(229, 243);
		MyImageButton trophyBtn = new MyImageButton(trophy);
		trophyBtn.addAction(new MyAnimation(trophy, regions1dTrophy, 2.5f));
		trophyBtn.setPosition(95, 400);
		trophyBtn.setName("trophyBtn");
		addActor(trophyBtn);
		addClickListener(trophyBtn);
		
		// Sound Button
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
			startBtn.setMuted(this.userInfo.getMuted());
			howToBtn.setMuted(this.userInfo.getMuted());
			quitBtn.setMuted(this.userInfo.getMuted());
			trophyBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
			
			hidePlaySoundBtn(playSoundBtn);
		});
		
		// ButtonListener muteSoundBtn
		muteSoundBtn.addButtonListener(() -> {
		    sound.playMuteSound();
		    this.userInfo.setMuted(false);
		    
		    sound.music.play();
			startBtn.setMuted(this.userInfo.getMuted());
			howToBtn.setMuted(this.userInfo.getMuted());
			quitBtn.setMuted(this.userInfo.getMuted());
			trophyBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
			
			resetMuteSoundEvent(playSoundBtn);
		});
		
		if (this.userInfo.getMuted() == true) {
			hidePlaySoundBtn(playSoundBtn);
			startBtn.setMuted(this.userInfo.getMuted());
			howToBtn.setMuted(this.userInfo.getMuted());
			quitBtn.setMuted(this.userInfo.getMuted());
			trophyBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
		} else {
			resetMuteSoundEvent(playSoundBtn);
			startBtn.setMuted(this.userInfo.getMuted());
			howToBtn.setMuted(this.userInfo.getMuted());
			quitBtn.setMuted(this.userInfo.getMuted());
			trophyBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
		}

		// Cross Hair สำหรับ Debugging
//		addActor(new CrossHair(1920 / 2, 380));
//		addActor(new CrossHair((1920 / 2) - 247, 160));
//		addActor(new CrossHair((1920 / 2) + 257, 160));

	}
	
	// Getter & Setter ClickedBtn for check what scene will show next
	public String getClickedBtn() {
		return clickedBtn;
	}
	
	private void setClickedBtn(MyImageButton imageBtn) {
		clickedBtn = imageBtn.getName();
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
				// Set clicked button for check what button clicked
				setClickedBtn(imageBtn);
				// Gdx.app.log(imageBtn.getName(), "clicked");
				addAction(Actions.sequence(Actions.fadeOut(1.5f), new RunnableActionEx(() -> {
					if (StartScene.this.listener != null) {
						StartScene.this.listener.sceneClosed(StartScene.this);
					}
					addAction(Actions.fadeIn(0.5f));
				})));
			}
		});
	}
	
	// Getter & Setter UserInfo for send to next scene
	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
