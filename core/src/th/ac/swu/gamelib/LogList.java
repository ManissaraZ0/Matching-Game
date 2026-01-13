package th.ac.swu.gamelib;

import java.util.ArrayList;
import java.util.List;

public class LogList {
	private final int size = 7;
	private List<String> list = new ArrayList<String>();
	
	public void log(String message) {
		list.add(message);
		trimHead();
	}

	private void trimHead() {
		if(list.size() > size)
			list.removeFirst();
	}
	
	public String getCombined() {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<list.size();++i) {
			sb.append(list.get(i));
			if(i < list.size()-1)
				sb.append("\n");
		}
		return sb.toString();
	}
}
