package th.ac.swu.gamelib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import th.ac.swu.gamelib.collision.CollisionDetection;

public class OneStageGame {
	private Stage stage;
	private Color background = GameUtil.color(0, 80, 0);
	private boolean checkEscExit = true;
	public CollisionDetection collisionDetection = new CollisionDetection();
	public boolean collisionDetectionEnable = true;
	
	public void setBackground(Color color) {background = color; }
	
	public void checkEscExit(boolean check) { checkEscExit = check; }
	
	public OneStageGame(Color background) {
		this.background = background;
		// stage still null; need to call initStage().
	}
	
	public void initStage(Stage stage) {
		this.stage = stage;
		Gdx.input.setInputProcessor(stage);
	}

	public void highLevelRender() {
		input();
		process();
		collisionDetect();
		output();
	}
	
	private void input() {
		if(!checkEscExit)
			return;
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.F12)) {
			if(Gdx.graphics.isFullscreen()) {
				setWindowMode();
			}
			else {
				var scr = Gdx.graphics.getDisplayMode();
				Gdx.graphics.setFullscreenMode(scr);
			}
		}
			
	}
	
	private void setWindowMode() {
		var scr = Gdx.graphics.getDisplayMode();
		var viewport = stage.getViewport();
		if(viewport.getWorldWidth() < scr.width && viewport.getWorldHeight() < scr.height)
			Gdx.graphics.setWindowedMode((int)viewport.getWorldWidth(), (int)viewport.getWorldHeight());
		else
			Gdx.graphics.setWindowedMode(scr.width/2, scr.height/2);
		Gdx.graphics.setUndecorated(false);	
	}

	private void process() {
		stage.act();
	}
	
	private void collisionDetect() {
		if(collisionDetection == null || !collisionDetectionEnable)
			return;
		collisionDetection.DetectAndResolve(stage.getRoot());
	}

	private void output() {
		ScreenUtils.clear(background);
		stage.draw();
	}
}

