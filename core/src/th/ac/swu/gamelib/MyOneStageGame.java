package th.ac.swu.gamelib;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

import th.ac.swu.gamelib.collision.CollisionDetection;

public class MyOneStageGame extends ApplicationAdapter {
	private Stage stage;
	private OneStageGame game;

	public CollisionDetection getCollisionDetection() { return game.collisionDetection; }
	// called from "create()"
	public void initStep1(Color background) {
		game = new OneStageGame(background); // ต้องสร้างก่อน เพื่อให้ getCollisionDetection ทำงานได้
	}
	
	public void initStep2(Stage stage) {
		this.stage = stage;
		game.initStage(stage);
	}

	@Override
	public void render () {
		game.highLevelRender();
	}

	@Override
	public void dispose () {
		stage.dispose();
	}
	
	// https://libgdx.com/wiki/graphics/viewports
	// "When Stage is used, the Stage’s viewport needs to be updated when a resize event happens."
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.getViewport().update(width, height);
	}

}
