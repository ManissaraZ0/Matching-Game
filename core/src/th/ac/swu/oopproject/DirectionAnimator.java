package th.ac.swu.oopproject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;

import th.ac.swu.gamelib.DirectionKey;
import th.ac.swu.gamelib.animation.MyAnimationStates;

public class DirectionAnimator extends Action {
	private MyAnimationStates states;
	int[] animates;

	// animates : index numbers of (stay, left, right) animations
	public DirectionAnimator(MyAnimationStates states, int[] animates) {
		assert(animates != null && animates.length == 3);
		this.states = states;
		this.animates = animates;
	}

	public DirectionAnimator(MyAnimationStates states) {
		this(states, new int[] {0,1,2});
	}

	@Override
	public boolean act(float delta_t) {
		
		Vector2 direction = DirectionKey.getDirection();
		if (direction.x < 0)		// left
            states.Animate(animates[1]);
        else if (direction.x > 0)	// right
            states.Animate(animates[2]);
//        else if (direction.y > 0) 	// up
//            states.Animate(animates[3]);
//        else if (direction.y < 0) 	// down
//            states.Animate(animates[4]);
        else
            states.Animate(animates[0]);		
		
		states.setTarget(target);
		states.setActor(actor);
		states.act(delta_t);

		return false;
	}
}
