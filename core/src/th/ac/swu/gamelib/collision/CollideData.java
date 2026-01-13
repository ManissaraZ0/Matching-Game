package th.ac.swu.gamelib.collision;

import com.badlogic.gdx.math.Rectangle;

public record CollideData(Rectangle overlapRect, boolean firstContact) {

}
