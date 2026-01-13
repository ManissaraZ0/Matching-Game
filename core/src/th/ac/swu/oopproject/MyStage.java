package th.ac.swu.oopproject;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import th.ac.swu.gamelib.OnScreenLogger;
import th.ac.swu.gamelib.collision.CollisionDetection;

public class MyStage extends Stage implements SceneListener {
	private StartScene startScene;
	private HowToScene howToScene;
	private HighScoreScene highScoreScene;
	private EnterNameScene enterNameScene;
	private MainScene mainScene;
	private TimeoutScene timeoutScene;
	private BonusScene bonusScene;
	private ScoreSummaryScene scoreSummaryScene;
	private Group holder = new Group();

	public MyStage(Viewport viewport, Batch batch, CollisionDetection collisionDetection) {
//		super(new FitViewport(1920, 1080), new PolygonSpriteBatch());
		super(viewport, batch);
		startScene = new StartScene(this, new UserInfo(null, null, null, null, false));
		startScene.setName("startScene");
//		mainScene = new MainScene(this, this);
//		mainScene.setName("mainScene");
//		scoreSummaryScene = new ScoreSummaryScene(this, 1000, 1000);
		howToScene = new HowToScene(this, new UserInfo(null, null, null, null, false));
		howToScene.setName("howToScene");
		highScoreScene = new HighScoreScene(this, new UserInfo(null, null, null, null, false));
		highScoreScene.setName("highScoreScene");
//		enterNameScene = new EnterNameScene(this);
//		enterNameScene.setName("enterNameScene");

//		useOnlyStartScene();
//		useOnlyHowToScene();
//		useOnlyHighScoreScene();
//		useOnlyEnterNameScene();
//		useOnlyMainScene();
//		useOnlyTimeoutScene();
//		useOnlyBonusScene();
//		useOnlyScoreSummaryScene();
		useSceneHolder();

//		addActor(new OnScreenLogger());
//		addActor(new SoundTest());
		// addListener(new EventTester());
	}

	private void useOnlyStartScene() {
		addActor(startScene);
	}
	
	private void useOnlyHowToScene() {
		addActor(howToScene);
	}
	
	private void useOnlyHighScoreScene() {
		addActor(highScoreScene);
	}
	
	private void useOnlyEnterNameScene() {
		addActor(enterNameScene);
	}

	private void useOnlyMainScene() {
		addActor(mainScene);
	}

	private void useOnlyTimeoutScene() {
		addActor(timeoutScene);
	}
	
	private void useOnlyBonusScene() {
		addActor(bonusScene);
	}
	
	private void useOnlyScoreSummaryScene() {
		addActor(scoreSummaryScene);
	}

	private void useSceneHolder() {
		holder.addActor(startScene);
		addActor(holder);
	}

	@Override
	public void sceneClosed(Group scene) {
		// Gdx.app.log("at start", ":"+scene +", " + startScene);
		if (scene.getName().equals(startScene.getName())) {
			holder.clear();
			StartScene start = (StartScene) scene;
			if (startScene.getClickedBtn() == "startBtn") {
				enterNameScene = new EnterNameScene(this, start.getUserInfo());
				enterNameScene.setName("enterNameScene");
				holder.addActor(enterNameScene);
			} else if (startScene.getClickedBtn() == "howToBtn") {
				howToScene = new HowToScene(this, start.getUserInfo());
				howToScene.setName("howToScene");
				holder.addActor(howToScene);
			} else if (startScene.getClickedBtn() == "trophyBtn") {
				highScoreScene = new HighScoreScene(this, start.getUserInfo());
				highScoreScene.setName("highScoreScene");
				holder.addActor(highScoreScene);
			}
		} else if (scene.getName().equals(highScoreScene.getName())) {
			holder.clear();
			HighScoreScene highScoreScene = (HighScoreScene) scene;
			startScene.setUserInfo(highScoreScene.getUserInfo());
			holder.addActor(startScene);
		} else if (scene.getName().equals(howToScene.getName())) {
			holder.clear();
			HowToScene howToScene = (HowToScene) scene;
			startScene.setUserInfo(howToScene.getUserInfo());
			holder.addActor(startScene);
		} else if (scene.getName().equals(enterNameScene.getName())) {
			holder.clear();
			EnterNameScene enterName = (EnterNameScene) scene;
			mainScene = new MainScene(this, this, enterName.getUserInfo());
			mainScene.setName("mainScene");
			holder.addActor(mainScene);
		} else if (scene.getName().equals(mainScene.getName())) {
			holder.clear();
			MainScene main = (MainScene) scene;
			timeoutScene = new TimeoutScene(this, main.getUserInfo());
			timeoutScene.setName("timeoutScene");
			holder.addActor(timeoutScene);
		} else if (scene.getName().equals(timeoutScene.getName())) {
			holder.clear();
			TimeoutScene timeout = (TimeoutScene) scene;
			bonusScene = new BonusScene(this, this, timeout.getUserInfo());
			bonusScene.setName("bonusScene");			
			holder.addActor(bonusScene);
		} else if (scene.getName().equals(bonusScene.getName())) {
			holder.clear();
			BonusScene bonus = (BonusScene) scene;
			scoreSummaryScene = new ScoreSummaryScene(this, bonus.getUserInfo());
			scoreSummaryScene.setName("scoreSummaryScene");	
			holder.addActor(scoreSummaryScene);
		} else if (scene.getName().equals(scoreSummaryScene.getName())) {
			holder.clear();
			ScoreSummaryScene scoreSummary = (ScoreSummaryScene) scene;
			if (scoreSummaryScene.getClickedBtn() == "retryBtn") {
				mainScene = new MainScene(this, this, scoreSummary.getUserInfo());
				mainScene.setName("mainScene");
				holder.addActor(mainScene);
			} else if (scoreSummaryScene.getClickedBtn() == "homeBtn") {
				startScene = new StartScene(this, new UserInfo(null, null, null, null, false));
				startScene.setName("startScene");
				holder.addActor(startScene);
			}
		}
	}
}
