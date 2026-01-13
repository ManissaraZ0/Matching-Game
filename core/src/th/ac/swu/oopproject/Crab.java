package th.ac.swu.oopproject;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import th.ac.swu.gamelib.Actors;
import th.ac.swu.gamelib.DirectionKey;
import th.ac.swu.gamelib.TextureCache;
import th.ac.swu.gamelib.animation.MyAnimation;
import th.ac.swu.gamelib.animation.MyAnimationStates;
import th.ac.swu.gamelib.animation.RegionSelector;
import th.ac.swu.gamelib.collision.CollideData;
import th.ac.swu.gamelib.collision.CollisionObj;

public class Crab extends Group {
    private static final int GROUP_CODE = 0;

	public Crab() {
		super();
		// 1. Load Texture
		var texture = TextureCache.get("crab.png");
		// 2. Split to Regions 2D
		var regions2d = TextureRegion.split(texture, 163, 86);
		// 3. Use "Selector" to select to Regions 1D
		var selector = new RegionSelector(regions2d);
		var regions1d = selector.from(0, 15);
		// 4. Create "Image" Actor
		Image actor = new Image(regions2d[0][0]);
		
		this.setSize(163, 86); // อย่าลืม set size ของ group ด้วย เพื่อให้ collision object ทำงานได้
		actor.setSize(163, 86);
		this.scaleBy(0.5f);
				
		var stay = new MyAnimation(actor, regions1d, 2.0f);
		var left = new MyAnimation(actor, regions1d, 2.0f);
		var right = new MyAnimation(actor, regions1d, 2.0f);
				
		var states = new MyAnimationStates(stay, left, right);
		actor.addAction(new DirectionAnimator(states));

		actor.setName("crab");
		
        addCollisionObj();
		
        this.addActor(actor);
	}
	
	private void addCollisionObj() {
		this.setUserObject(new CollisionObj(this, GROUP_CODE) {
			@Override
			public void OnCollide(CollisionObj objB, CollideData collideData) {
				var direction = this.RelativeDirection(collideData.overlapRect());
				
				if(direction.y != 0)
				{
					Actors.changePosition(this.actor, new Vector2(0, -collideData.overlapRect().height * direction.y));
					V.y = 0;
				}
				if (direction.x != 0)
				{
					Actors.changePosition(this.actor, new Vector2(-collideData.overlapRect().width * direction.x, 0));
					V.x = 0;					
				}
			}
		});
		
	}
	
	private Vector2 V = new Vector2();

	@Override
	public void act(float delta) {
		super.act(delta);

		float speed = 800;
		Vector2 direction = DirectionKey.getLeftRightDirection();
		V.x = direction.x * speed;

		float a = -1000;		
		V.y += a * delta;

		// Mover
		var pos = Actors.getPosition(this).add(V.cpy().scl(delta));
		Actors.setPosition(this, pos);
	}
}
