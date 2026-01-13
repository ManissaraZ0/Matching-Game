package th.ac.swu.gamelib.collision;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import th.ac.swu.gamelib.Actors;

public class CollisionObj {
    public Actor actor;  //{ get; private set; }
    public int GroupCode = 0;
    public List<CollisionObj> LastObjList = new ArrayList<CollisionObj>();
    public List<CollisionObj> CurrentObjList = new ArrayList<CollisionObj>();
    public void debug() { actor.debug(); }
    public void OnCollide(CollisionObj objB, CollideData collideData) 
    { 
    	// for overriding
    }

    public CollisionObj(Actor actor)
    {
        this.actor = actor;
    }
    public CollisionObj(Actor actor, int groupCode)
    {
        this.actor = actor;
        GroupCode = groupCode;
    }
    
    public static CollisionObj createAndLink(Actor actor, int groupCode) {
    	var obj = new CollisionObj(actor, groupCode);
    	actor.setUserObject(obj);
    	return obj;
    }

    public void InvokeCollide(CollisionObj objB, CollideData collideData)
    {
        OnCollide(objB, collideData);
    }
    
    private static Rectangle calcOverlapRect (Rectangle a, Rectangle b) {
    	float left = Math.max(a.x, b.x);
    	float bottom = Math.max(a.y, b.y);
    	float right = Math.min(a.x + a.width, b.x + b.width);
    	float top = Math.min(a.y + a.height, b.y + b.height);
    	
    	float width = right - left;
    	float height = top - bottom;
    	if(width < 0 || height < 0)
    		return new Rectangle();

    	return new Rectangle(left, bottom, width, height);
	}

    
    // return collideData if collide
    // เช็ค shape ชน shape; ก็คือ Actor bound ชน actor bound
    // return null if not collide
    public Rectangle IsCollide(CollisionObj objB) {
    	Rectangle a = Actors.getBounds(this.actor);
    	Rectangle b = Actors.getBounds(objB.actor);

//    	if(!a.overlaps(b))
//    		return null;
    	
    	var overlapRect = calcOverlapRect(a, b);
    	if(overlapRect.width == 0 || overlapRect.height == 0)
    		return null;
    	
    	return overlapRect;
    }

    public Vector2 RelativeDirection(Rectangle overlap)
    {
    	Rectangle a = Actors.getBounds(this.actor);
        return RelativeDirection(a, overlap);
    }

    private static Vector2 RelativeDirection(Rectangle a, Rectangle overlap)
    {
    	Vector2 aCenter = new Vector2();
    	Vector2 oCenter = new Vector2();
        var point = (overlap.getCenter(oCenter).cpy().sub(a.getCenter(aCenter)));
        if (point.y * a.width > a.height * point.x) // up or left
            if (point.y * a.width > -a.height * point.x) // up or right
                return new Vector2(0, 1);
            else
                return new Vector2(-1, 0);
        else
            if (point.y * a.width > -a.height * point.x) // up or right
                return new Vector2(1, 0);
            else
                return new Vector2(0, -1);
    }
}
