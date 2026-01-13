package th.ac.swu.oopproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

import th.ac.swu.gamelib.FontCache;
import th.ac.swu.gamelib.GameUtil;
import th.ac.swu.gamelib.RandomUtil;
import th.ac.swu.gamelib.RunnableActionEx;
import th.ac.swu.gamelib.TextureCache;
import th.ac.swu.gamelib.ui.MyImageButton;

public class MainScene extends Group {
	private SceneListener listener;
	private Score score;
	private UserInfo userInfo;
	private SoundTest sound = new SoundTest();

	public MainScene(Stage stage, SceneListener listener, UserInfo userInfo) {
		this.listener = listener;
		this.setBounds(0, 0, stage.getWidth(), stage.getHeight());
		this.userInfo = userInfo;

		// Background
		Texture bgTexture = TextureCache.get("mainScene.png");
		var bgActor = new Image(bgTexture);
		addActor(bgActor);

		// Sound
		sound.setSound("mainSound.ogg");
		addActor(sound);
		userInfo.setSound(sound);

		// Create Font
		var font = FontCache.get("KENTT.ttf", 50);
//		var style = new LabelStyle(font, GameUtil.color(236, 48, 72));

		// Create Scoring
		score = new Score(font, GameUtil.color(236, 48, 72));
		addActor(score);
		score.setPosition(1285, 964);
		// Set Score
		userInfo.setScore(score);

		// Create Timing
		Time time = new Time(font, GameUtil.color(236, 48, 72));
		addActor(time);
		time.setPosition(370, 964);
		addTimeEvent(time);

		// Create Shell
		NumberOfShells numberOfShells = new NumberOfShells(0);
		Texture imageTexture = new Texture(Gdx.files.internal("close.png"));

		// default rows = 2, cols = 2
		createShells(2, 2, 1, imageTexture, stage, numberOfShells, score);
//        createShells(2, 3, 2, imageTexture, stage, numberOfShells, score);
//        createShells(3, 4, 3, imageTexture, stage, numberOfShells, score);
//        createShells(4, 5, 4, imageTexture, stage, numberOfShells, score);
//        createShells(5, 6, 5, imageTexture, stage, numberOfShells, score);

		boolean isMuted = this.userInfo.getMuted();
		this.addAction(Actions.delay(0.5f, new RunnableActionEx(() -> {
			Timer.schedule(new Timer.Task() {
				@Override
				public void run() {
					if (time.getTime() == 5 && isMuted == false) {
						SoundTest countdownsound = new SoundTest();
						countdownsound.clearSound();
						countdownsound.setSound("5Seconds.ogg");
						addActor(countdownsound);
					}
					if (time.getTime() == 0) {
						addAction(Actions.sequence(Actions.fadeOut(0.5f), new RunnableActionEx(() -> {
							if (MainScene.this.listener != null) {
								if (isMuted == false) {
									sound.clearSound();
									sound.setSound("timeOut.ogg");
								}
								MainScene.this.listener.sceneClosed(MainScene.this);
							}
						})));
					}
				}
			}, 0, 0.5f); // Check the condition every 0.5 seconds
		})));

		// Increase Time Item
		Image timeItemUsedImage = new Image(TextureCache.get("timeItemUsed.png"));
		timeItemUsedImage.setOrigin(Align.center);
		timeItemUsedImage.setPosition(1760, 695, Align.center);
		timeItemUsedImage.setName("timeItemUsedImage");
		addActor(timeItemUsedImage);

		Image timeItemBtnImage = new Image(TextureCache.get("timeItem.png"));
		MyImageButton timeItemBtn = new MyImageButton(timeItemBtnImage);
		timeItemBtn.setPosition(1760, 695, Align.center);
		timeItemBtn.setName("timeItemBtn");
		addActor(timeItemBtn);
		timeItemBtn.addButtonListener(() -> {
			timeItemBtn.remove();
			time.increment(20);
		});

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
			timeItemBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
			for (MyImageButton button : allCloseShell) {
				button.setMuted(this.userInfo.getMuted());
			}

			hidePlaySoundBtn(playSoundBtn);
		});

		// ButtonListener muteSoundBtn
		muteSoundBtn.addButtonListener(() -> {
			sound.playMuteSound();
			this.userInfo.setMuted(false);

			sound.music.play();
			timeItemBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
			for (MyImageButton button : allCloseShell) {
				button.setMuted(this.userInfo.getMuted());
			}

			resetMuteSoundEvent(playSoundBtn);
		});

		// Check Muted
		if (this.userInfo.getMuted() == true) {
			sound.music.pause();
			hidePlaySoundBtn(playSoundBtn);
			timeItemBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
			for (MyImageButton button : allCloseShell) {
				button.setMuted(this.userInfo.getMuted());
			}
		} else {
			sound.music.play();
			resetMuteSoundEvent(playSoundBtn);
			timeItemBtn.setMuted(this.userInfo.getMuted());
			muteSoundBtn.setMuted(this.userInfo.getMuted());
			for (MyImageButton button : allCloseShell) {
				button.setMuted(this.userInfo.getMuted());
			}
		}
	}

	// For collecting all close shells
	private List<MyImageButton> allCloseShell = new ArrayList<>();
	
	private void createShells(int rows, int cols, int round, Texture imageTexture, Stage stage, NumberOfShells numberOfShells, Score score) {
		// Set the number of shells
		numberOfShells.increment(rows * cols);

		// Calculate padding and scale of shell image
		float percent = 1.0f; // Padding
		float percentImg = 1.0f; // Image
		float decreasePercent = 0.15f;
		float decreasePercentImg = 0.65f;
		
		// Round 4++
		if (numberOfShells.getNumberOfShells() > 12) {
			decreasePercent = 0.13f;
			decreasePercentImg = 0.60f;
		}
		
		// After first round decrease padding and scale of shell image
		if (round != 1) {
			int roundPercent = round > 4 ? 4 : round;
			percent = percent - (decreasePercent * roundPercent);
			percentImg = percentImg - (decreasePercentImg * roundPercent);
		}
		
		// Width & Height of image
		float imageWidth = imageTexture.getWidth() * percent;
		float imageHeight = imageTexture.getHeight() * percent;
		float padding = (imageWidth / 2) - (20f * percent);
		float totalWidth = cols * (imageWidth + padding) - padding;
		float totalHeight = rows * (imageHeight + padding) - padding;

		// Calculate starting position
		float startX = (stage.getWidth() - totalWidth) / 2 + padding;
		float startY = (stage.getHeight() - totalHeight) / 2 + padding - 10;

		// Collect numbers
		List<Integer> numbers = new ArrayList<>();
		
		// Random numbers
		for (int i = 0; i < numberOfShells.getNumberOfShells() / 2; i++) {
			int randomNumber;
			// Check List<Integer> numbers (if randomNumber is already in list, it will random again)
			do {
				randomNumber = RandomUtil.randomInt(1,10);
			} while (numbers.contains(randomNumber));
			// Added two times because it must be a pair.
			numbers.add(randomNumber);
			numbers.add(randomNumber);
		}
		
		// Shuffle number in List<Integer> numbers
		Collections.shuffle(numbers);
		
		// For debugging
		// System.out.println("Random numbers with non-repeating pairs: " + numbers);

		int i = 0; // For call numbers in List<Integer> numbers by index
		
		int[] openCount = { 0 }; // For counting how many the shell that have been opened
		
		MyImageButton[] firstOpened = { null }; // For collecting the first closed shell
		MyImageButton[] secondOpened = { null }; // For collecting the second closed shell
		
		Image[] firstImgOpened = { null }; // For collecting the first opened shell
		Image[] secondImgOpened = { null }; // For collecting the second opened shell
		
		int nextRows = rows;
		int nextCols = cols;
		
		// For increase the number of shells (If round greater than 4, don't increase row and column)
		if (round < 4) {
			nextRows = cols % 2 == 1 ? rows + 1 : rows;
			nextCols = cols + 1;
			while ((nextRows * nextCols) % 2 == 1) {
				// If it's not have pair
				nextRows = nextRows + 1;
			}
		}
		
		int nextRound = round + 1;
		int[] next = new int[] { nextRows, nextCols, nextRound };
		
		// For debugging
		// System.out.println("Prepare for Next Round Rows : " + nextRows + ", Cols : " + nextCols);

		// Create grid of images
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				float x = startX + col * (imageWidth + padding);
				float y = startY + row * (imageHeight + padding);

				// Shells that has been opened
				int imageNumber = numbers.get(i); // Get number in List<Integer> numbers by index(i)
				i++; // Increase index(i) to call the next number in List<Integer> numbers
				String imageName = String.format("open%d.png", imageNumber); // Get image by imageNumber
				Image openImage = new Image(TextureCache.get(imageName));
				openImage.setOrigin(Align.center);
				openImage.scaleBy(0.25f * percentImg);
				openImage.setPosition(x, y, Align.center);
				openImage.setName(String.format("open%d", imageNumber));
				addActor(openImage);

				// Top shell for closing the image shells that has been opened
				Image closeImage = new Image(TextureCache.get("close.png"));
				closeImage.scaleBy(0.25f * percentImg);
				MyImageButton closeShell = new MyImageButton(closeImage);
				closeShell.setPosition(x, y, Align.center);
				closeShell.setName(String.format("close%d", imageNumber));
				addActor(closeShell);
				// For Sound
				allCloseShell.add(closeShell);
				soundListener(closeShell);

				closeShell.addButtonListener(() -> {
					if (openCount[0] < 2) {
						// Check how many shells have been opened. If it's still less than 2, you can continue.
						addOpenEvent(closeShell);
						openCount[0]++;
						if (openCount[0] == 1) {
							// First open - assign image and top shell
							firstOpened[0] = closeShell;
							firstImgOpened[0] = openImage;
						} else if (openCount[0] == 2) {
							// Second open - assign image and top shell
							addOpenEvent(closeShell);
							secondOpened[0] = closeShell;
							secondImgOpened[0] = openImage;
							
							// Check matching
							if (!secondOpened[0].getName().equals(firstOpened[0].getName())) {
								// Matching incorrect
								this.addAction(Actions.sequence(Actions.delay(0.3f), new RunnableActionEx(() -> {
									resetOpenEvents(firstOpened[0], secondOpened[0]);
									openCount[0] = 0;
								})));
							} else {
								// Matching correct
								this.addAction(Actions.sequence(new RunnableActionEx(() -> {
									addScore(score); // Increase score
									numberOfShells.decrease(); // Decrease the number of shells one pair(2)
								}), Actions.sequence(Actions.delay(0.3f), new RunnableActionEx(() -> {
									closeEvent(firstImgOpened[0], secondImgOpened[0]); // Remove image below
									openCount[0] = 0; // Reset openCount[0] to zero
									
									// Check the number of shells in stage to go next round
									if (numberOfShells.getNumberOfShells() == 0) {
										createShells(next[0], next[1], next[2], imageTexture, stage, new NumberOfShells(0), score);
									}
								}))));
							}
						}
					}
				});

				// Cross Hair สำหรับ Debugging
				// addActor(new CrossHair(x, y));
			}
		}
	}

	// For remove image if matching correct
	private void closeEvent(Image firstImgOpened, Image secondImgOpened) {
		firstImgOpened.remove();
		secondImgOpened.remove();
	}

	// For open top shell
	private void addOpenEvent(MyImageButton btn) {
		btn.addAction(Actions.sequence(Actions.fadeOut(0.3f), Actions.visible(false)));
		btn.setTouchable(Touchable.disabled);
	}

	// For show top shell again if matching incorrect
	private void resetOpenEvents(MyImageButton button1, MyImageButton button2) {
		button1.addAction(Actions.sequence(Actions.fadeIn(0.3f), Actions.visible(true)));
		button1.setTouchable(Touchable.enabled);
		button2.addAction(Actions.sequence(Actions.fadeIn(0.3f), Actions.visible(true)));
		button2.setTouchable(Touchable.enabled);
	}

	// Score
	private void addScore(Score score) {
		score.increment(10);
	}

	public int getScore() {
		return score.getScore();
	}

	// Countdown timer
	private void addTimeEvent(Time time) {
		this.addAction(Actions.delay(0.5f, new RunnableActionEx(() -> {
			time.startCountdown(60);
		})));
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
	
	private void soundListener(MyImageButton btn) {
		if (this.userInfo.getMuted() == true) {
			btn.setMuted(this.userInfo.getMuted());
		} else {
			btn.setMuted(this.userInfo.getMuted());
		}
	}

	// Get UserInfo for send to next scene
	public UserInfo getUserInfo() {
		return this.userInfo;
	}
}
