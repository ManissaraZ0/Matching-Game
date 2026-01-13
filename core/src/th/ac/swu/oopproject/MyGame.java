package th.ac.swu.oopproject;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import th.ac.swu.gamelib.GameUtil;
import th.ac.swu.gamelib.MyOneStageGame;
import th.ac.swu.gamelib.OneStageGame;

public class MyGame extends MyOneStageGame {
	Stage stage;
	OneStageGame game;
	PolygonSpriteBatch batch;
	Viewport viewport;
	
	@Override
	public void create () {
		super.initStep1(GameUtil.color(0, 0, 0));
		viewport = new FitViewport(1920, 1080);
		batch = new PolygonSpriteBatch();
		
		stage = new MyStage(viewport, batch, getCollisionDetection());
		game = new OneStageGame(GameUtil.color(0, 0, 0));
		game.initStage(stage);
		
		super.initStep2(stage);
	}

	@Override
	public void render () {
		game.highLevelRender();
	}

	@Override
	public void dispose () {
		stage.dispose();
	}
}
