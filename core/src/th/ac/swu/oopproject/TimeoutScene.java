package th.ac.swu.oopproject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import th.ac.swu.gamelib.RunnableActionEx;
import th.ac.swu.gamelib.TextureCache;

public class TimeoutScene extends Group {
	private SceneListener listener;
	private UserInfo userInfo;
	
	private int score;

	public TimeoutScene(SceneListener listener, UserInfo userInfo) {
		this.listener = listener;
		this.userInfo = userInfo;
		
		// Background
		Texture bgTexture = TextureCache.get("timeoutScene.png");
		var bgActor = new Image(bgTexture);
		addActor(bgActor);
		
//		this.score = score;

		this.addAction(Actions.delay(0.5f, new RunnableActionEx(() -> {
			if (this.listener != null)
				this.listener.sceneClosed(TimeoutScene.this);
		})));
	}

	public int getScore() {
		return this.score;
	}
	
	public UserInfo getUserInfo() {
		return this.userInfo;
	}
}
