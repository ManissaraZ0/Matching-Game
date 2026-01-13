package th.ac.swu.gamelib.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

// Transform จาก Group จะส่งต่อยังตัวลูกๆ ด้วย
public class Grid extends Group {
	private List<Actor> lineList = new ArrayList<Actor>();
	private Color oldColor;
	
	public Grid(float tileWidth, int countX, int countY, Color color, float lineWidth) {
		this(tileWidth, tileWidth, countX, countY, color, lineWidth);
	}
	public Grid(float tileWidth, float tileHeight, int countX, int countY, Color color, float lineWidth) {
		this.setColor(color);
		
		for(int i=0; i<=countX; ++i)
			addActor(new VerticalLine(i*tileWidth, 0, countY*tileHeight, color, lineWidth));
		
		for(int i=0; i<=countY; ++i)
			addActor(new HorizontalLine(i*tileHeight, 0, countX*tileWidth, color, lineWidth));
		
		lineList.addAll(Arrays.asList(this.getChildren().toArray()));
	}
	

	// ต้องส่งต่อ color ไปยังตัวลูกๆ เอง เฉพาะลูกๆ ที่เป็น line
	@Override
	public void setColor(Color color) {
		for(var child: lineList)
			child.setColor(color);

		oldColor = color.cpy();
		super.setColor(color);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if(oldColor != this.getColor())
			setColor(this.getColor()); // เพื่อปรับสีให้กับทุก child; ใช้กรณี AlphaAction แอบมาแก้ค่าสีภายใน actor โดยตรง
	}
}
