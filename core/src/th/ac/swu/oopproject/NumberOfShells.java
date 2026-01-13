package th.ac.swu.oopproject;

import com.badlogic.gdx.scenes.scene2d.Group;

public class NumberOfShells extends Group {
	private int numberOfShells = 0;

	public NumberOfShells(int updateNumberOfShells) {
		numberOfShells = updateNumberOfShells;
	}
	
	public void increment(int updateNumberOfShells) {
		numberOfShells += updateNumberOfShells;
	}
	
	public void decrease() {
		numberOfShells -= 2;
	}
	
	public int getNumberOfShells() {
		return numberOfShells;
	}
}
