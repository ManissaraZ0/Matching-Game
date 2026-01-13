package th.ac.swu.gamelib.animation;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RegionSelector {
	
	// [row][col]
	private TextureRegion[][] region2d;
	//private int colCount;
	private ArrayList<TextureRegion> list;

	public RegionSelector(TextureRegion[][] region2d) {
		this.region2d = region2d;
		
		list = new ArrayList<TextureRegion>();
		for(int row=0; row < region2d.length; ++row) {
			int colCount = region2d[row].length;
			for(int col=0; col < colCount; ++col) {
				list.add(region2d[row][col]);
			}
		}
	}
	
	public TextureRegion[] all() {
		TextureRegion[] regions = new TextureRegion[list.size()];
		return list.toArray(regions);
	}
	
	public TextureRegion[] from(int from, int length) {
		TextureRegion[] regions = new TextureRegion[length];
		return list.subList(from, from + length).toArray(regions);
	}
	
	public TextureRegion[] pick(int ... indexList) {
		TextureRegion[] regions = new TextureRegion[indexList.length];
		
		for(int i=0; i<indexList.length; ++i) {
			int index = indexList[i];
			regions[i] = list.get(index);
		}
		
		return regions;
	}

}
