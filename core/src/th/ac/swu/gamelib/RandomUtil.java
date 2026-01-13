package th.ac.swu.gamelib;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class RandomUtil {
	
	// 0 to (maxValue-1)
    public static int randomInt(int maxValue)
    {
        return (int)(random() * maxValue);
    }
    
    // start to endInclusive
    public static int randomInt(int start, int endInclusive) {
        int range = endInclusive - start + 1;
        return (int)(random() * range) + start;
    }

    // 0.0 to 1.0 (exclusive)
    public static float random() {
		return (float)Math.random();
	}
    
    public static float random(float endExclusive) {
    	return random() * endExclusive;
    }
    
    public static float random(float start, float endExclusive) {
    	float range = endExclusive - start;
    	return random() * range + start;
    }

    public static Vector2 direction()
    {
        double theta = RandomUtil.random() * 2 * Math.PI;
        return new Vector2((float)Math.cos(theta), (float)Math.sin(theta));
    }
    
    public static Vector2 inCircle(float r) {
    	return direction().scl(random() * r);
    }
    
    public static Vector2 outlineCircle(float r) {
    	return direction().scl(r);
    }
    
    // Down Only
    public static Vector2 downwardVector(float speed) {
    	// Don't move x, and move y -1 | Multiply by speed
        return new Vector2(0, -1).scl(speed);
    }

    public static Color color()
    {
        return GameUtil.color(randomInt(256), randomInt(256), randomInt(256));
    }
    
}
