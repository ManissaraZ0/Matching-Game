package th.ac.swu.gamelib.collision;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

// 27/4/24 : ปัจจุบันยังไม่รองรับ Actor ที่ setOrigin เป็นจุดอื่นพร้อมกับ scaling
public class CollisionDetection {
    public boolean FirstContactDetectionEnable = true;
    public int MaxGroup() { return 10; }
    private CollisionGroups groups;
    private ArrayList<TwoGroups> detectors = new ArrayList<TwoGroups>();
    public void AddDetector(int groupCode1, int groupCode2)
    {
        detectors.add(new TwoGroups(groupCode1, groupCode2));
    }
    public CollisionDetection()
    {
        groups = new CollisionGroups(MaxGroup());
    }
    public void DetectAndResolve(Group root)
    {
        groups = new CollisionGroups(MaxGroup());
        FindAllCollisionObjs(root);
        FirstContactInit();
        DetectAndResolveAllGroups();
    }

    private void FirstContactInit()
    {
        if (!FirstContactDetectionEnable)
            return;

        for (int i = 0; i < groups.MaxGroup(); i++)
        {
            var list = groups.GetList(i);
            for (int j = 0; j < list.size(); ++j)
            {
                var obj = list.get(j);
                obj.LastObjList = obj.CurrentObjList; // move Cur list into Last List
                obj.CurrentObjList = new ArrayList<CollisionObj>(); // new Cur List
            }
        }
    }

    private void DetectAndResolveAllGroups()
    {
        ArrayList<CollisionObj> list; // nullable
        list = groups.IsSingleGroup(); 
        if(list != null) // found
            DetectAndResolveSameList(list);
        else
        {
            for (int i = 0; i < detectors.size(); i++)
            {
                var list1 = groups.GetList(detectors.get(i).groupCode1());
                var list2 = groups.GetList(detectors.get(i).groupCode2());
                DetectAndResolveTwoLists(list1, list2);
            }
        }
    }

    private void DetectAndResolveTwoLists(ArrayList<CollisionObj> list1, ArrayList<CollisionObj> list2)
    {
        if(list1 == list2)
            DetectAndResolveSameList(list1);
        else
            DetectAndResolveDistinctLists(list1, list2);
    }

    private void DetectAndResolveSameList(ArrayList<CollisionObj> list)
    {
        for (int i = 0; i < list.size(); i++)
            for (int j = i + 1; j < list.size(); j++)
                DetectAndResolve(list.get(i), list.get(j));
    }
    private void DetectAndResolveDistinctLists(ArrayList<CollisionObj> list1, ArrayList<CollisionObj> list2)
    {
        for (int i = 0; i < list1.size(); i++)
            for (int j = 0; j < list2.size(); j++)
                DetectAndResolve(list1.get(i), list2.get(j));
    }

    private void DetectAndResolve(CollisionObj objA, CollisionObj objB)
    {
        if (objA == objB)
            return;

        Rectangle overlapRect = objA.IsCollide(objB);
        if (overlapRect != null)
            InvokeCollideBoth(objA, objB, overlapRect);
    }
    private void InvokeCollideBoth(CollisionObj objA, CollisionObj objB, Rectangle overlapRect)
    {
    	boolean FirstContact = false;
        if(FirstContactDetectionEnable)
        {
            FirstContact = !objB.LastObjList.contains(objA);
            objA.CurrentObjList.add(objB);
            objB.CurrentObjList.add(objA);
        }

    	CollideData collideData = new CollideData(overlapRect, FirstContact);

        objA.InvokeCollide(objB, collideData);
        objB.InvokeCollide(objA, collideData);
    }


    // หาเจอแล้วค่อยๆ เติมเข้า list
    private void FindAllCollisionObjs(Actor node)
    {
        assert(node != null);
        var userObj = node.getUserObject();
        if (userObj != null && userObj instanceof CollisionObj) {
            var collisionObj = (CollisionObj) userObj;
            groups.Add(collisionObj);
        }

        if(node instanceof Group)
        {
            var enumerable = (Group)node;
            for (var child : enumerable.getChildren())
            {
                FindAllCollisionObjs(child);
            }
        }
    }
}
