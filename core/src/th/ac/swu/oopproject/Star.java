package th.ac.swu.oopproject;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import th.ac.swu.gamelib.Mover;
import th.ac.swu.gamelib.RandomUtil;
import th.ac.swu.gamelib.TextureCache;
import th.ac.swu.gamelib.animation.MyAnimation;
import th.ac.swu.gamelib.animation.RegionSelector;
import th.ac.swu.gamelib.collision.CollideData;
import th.ac.swu.gamelib.collision.CollisionObj;
import th.ac.swu.gamelib.ui.SoundButton;

public class Star extends Group {
	private static final int GROUP_CODE = 0;
	private boolean isMuted = false;

	public Star(Vector2 centerOfScene) {
		super();

		// 1. Load Texture
		var texture = TextureCache.get("star.png");
		// 2. Split to Regions 2D
		var regions2d = TextureRegion.split(texture, 64, 64);
		// 3. Use "Selector" to select to Regions 1D
		var selector = new RegionSelector(regions2d);
		var regions1d = selector.from(0, 11);
		// 4. Create "Image" Actor
		Image actor = new Image(regions2d[0][0]);

		this.setSize(64, 64); // อย่าลืม set size ของ group ด้วย เพื่อให้ collision obj ทำงานได้
		actor.setSize(64, 64);
		this.setPosition(RandomUtil.random(300, 1570), 1080);

		// 5. Add animation
		actor.addAction(new MyAnimation(actor, regions1d, 2.0f));

		actor.setName("star");

		this.addActor(actor);

		// Add collision
		this.setUserObject(new CollisionObj(this, GROUP_CODE) {
			@Override
			public void OnCollide(CollisionObj objB, CollideData collideData) {
				if (objB.actor instanceof Crab) {
					StarScore sc = StarScore.getInstance();
					// For sound
					if (!isMuted) {
						addActor(new SoundTest("starSound.ogg"));
					}
					// Add star score
					sc.increaseScore(10);
					// For debugging
					// System.err.println(sc.getScore());
					Star.this.remove();
				}
			}
		});

		// Add auto moved
		float speed = 200;
		// Create new method for down only
		Vector2 downwardVelocity = RandomUtil.downwardVector(speed);
		var mover = new Mover(downwardVelocity);
		this.addAction(mover);
		// Set the downward velocity to the mover object
		mover.setV(downwardVelocity);

	}

	// Set muted sound for star
	public void setMuted(boolean isMuted) {
		this.isMuted = isMuted;
	}
}
