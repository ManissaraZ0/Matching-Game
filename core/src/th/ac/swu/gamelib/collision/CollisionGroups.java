package th.ac.swu.gamelib.collision;

import java.util.ArrayList;

public class CollisionGroups {
    public int MaxGroup() { return listOfList.length; }
    private ArrayList<CollisionObj>[] listOfList;
    public CollisionGroups(int maxGroup)
    {
        listOfList = new ArrayList[maxGroup];

        for (int i = 0; i < listOfList.length; ++i)
        {
            listOfList[i] = new ArrayList<CollisionObj>();
        }
    }

    public ArrayList<CollisionObj> GetList(int index) 
    {
        return listOfList[index];
    }

    public void Add(CollisionObj obj)
    {
        assert(obj.GroupCode < listOfList.length);
        listOfList[obj.GroupCode].add(obj);
    }

    public ArrayList<CollisionObj> IsSingleGroup()
    {
        int countFound = 0;
        ArrayList<CollisionObj> list = null;

        for (int i = 0; i < listOfList.length; ++i)
            if (listOfList[i].size() > 0)
            {
                countFound++;
                list = listOfList[i];
            }

        if (countFound == 1)
        {
        	return list;
            //return true;
        }
        else // 0, or 2,3,4, ...
        {
            list = null;
            //return false;
            return list;
        }
    }
}
