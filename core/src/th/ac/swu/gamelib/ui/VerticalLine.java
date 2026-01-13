package th.ac.swu.gamelib.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonBatch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import th.ac.swu.gamelib.GameUtil;

//ignore Transform (position, scaling, rotation)
//but use parent transform
public class VerticalLine extends Actor {
	private float x;
	private float y0;
	private float y1;
	private float width;
	
	private float[] vertices;
	private short[] polygonPoints;
	
	public VerticalLine(float x, float y0, float y1, Color color, float width) {
		this.x = x;
		this.y0 = y0;
		this.y1 = y1;
		super.setColor(color);
		this.width = width;
		
		calcPolygons();
	}
	
	private void calcPolygons() {
		float colorf = getColor().toFloatBits();
		float half = width/2; // halfWidth
		
		vertices = new float[] {
			x-half, y0-half, colorf, 0, 0,
			x-half, y1+half, colorf, 0, 0,
			x+half, y1+half, colorf, 0, 0,
			x+half, y0-half, colorf, 0, 0,
		};
		polygonPoints = new short[] {
			0,1,2,2,3,0
		};
	}
	
	@Override
	public void setColor(Color color) {
		super.setColor(color);
		calcPolygons();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		var texture = GameUtil.getWhitePixel();		
		((PolygonSpriteBatch)batch).draw(texture, vertices, 0, vertices.length, polygonPoints, 0, polygonPoints.length);		

		super.draw(batch, parentAlpha);
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", super.toString(), this.getColor().toString());
	}
}
