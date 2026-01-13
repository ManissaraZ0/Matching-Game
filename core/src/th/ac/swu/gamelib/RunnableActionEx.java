package th.ac.swu.gamelib;

import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

public class RunnableActionEx extends RunnableAction {

	public RunnableActionEx(Runnable runnable) {
		this.setRunnable(runnable);
	}
}
