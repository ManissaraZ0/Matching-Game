package th.ac.swu.gamelib.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonBatch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import th.ac.swu.gamelib.GameUtil;

// ignore Transform (position, scaling, rotation)
// but use parent transform
public class HorizontalLine extends Actor {
	private float y;
	private float x0;
	private float x1;
	private float width;
	
	private float[] vertices;
	private short[] polygonPoints;
	
	public HorizontalLine(float y, float x0, float x1, Color color, float width) {
		this.y = y;
		this.x0 = x0;
		this.x1 = x1;
		super.setColor(color);
		this.width = width;
		
		calcPolygons();
	}
	
	private void calcPolygons() {
		float colorf = getColor().toFloatBits();
		float half = width/2; // halfWidth
		
		vertices = new float[] {
			x0-half, y-half, colorf, 0, 0,
			x1+half, y-half, colorf, 0, 0,
			x1+half, y+half, colorf, 0, 0,
			x0-half, y+half, colorf, 0, 0,
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
}
