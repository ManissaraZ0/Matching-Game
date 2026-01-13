package th.ac.swu.gamelib.animation;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class MyAnimationStates extends Action {
	private int last = -1;
	private MyAnimation[] animations;

	public MyAnimationStates(MyAnimation ... animations) {
		this.animations = animations;
		//new SequenceAction()
	}

    public MyAnimation GetAnimation(int index)
    {
        return animations[index];
    }
    public void Restart()
    {
        if (last == -1)
            return;
        GetAnimation(last).restart();
    }
    public void Pause()
    {
        if (last == -1)
            return;
        GetAnimation(last).pause();
    }

    public void Animate(int index)
    {
        GetAnimation(index).play();
        if (index == last)
            return;

        for (int i = 0; i < animations.length; ++i)
            GetAnimation(i).running = false;

        var animation = GetAnimation(index);
        animation.play();
        animation.restart();
        last = index;
    }

	@Override
	public boolean act(float delta) {
		if(last == -1)
			return false;
		
		var ani = GetAnimation(last);
		ani.setTarget(this.target);
		ani.setActor(this.actor);
		ani.act(delta);
		return false;
	}

}
