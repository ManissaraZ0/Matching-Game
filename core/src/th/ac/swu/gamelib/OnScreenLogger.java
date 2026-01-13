package th.ac.swu.gamelib;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class OnScreenLogger extends Table implements ApplicationLogger {
	private ApplicationLogger previous = null;
	private Label label;
	private LogList logList = new LogList();
	
	public OnScreenLogger() {

		Table table = this;
		table.setFillParent(true);
		//table.setDebug(true);
		table.setTouchable(Touchable.disabled);
		
		var font = FontCache.get("cour.ttf", 30, Color.BLUE, 2, 2);
		var style = new LabelStyle(font, Color.WHITE);
		label = new Label("--- On Screen Logger ---", style);
		table.add(label).expandY().expandX().top().left();

		startIntercept();
	}
	
	public void startIntercept() {
		if(previous != null)
			return;
		previous = Gdx.app.getApplicationLogger();
		Gdx.app.setApplicationLogger(this);
	}
	
	@Override
	public void log(String tag, String message) {
		if(previous != null)
			previous.log(tag, message);
		
		logList.log("["+tag+"] "+ message);
		label.setText(logList.getCombined());
	}

	@Override
	public void log(String tag, String message, Throwable exception) {
		if(previous != null)
			previous.log(tag, message, exception);
	}

	@Override
	public void error(String tag, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String tag, String message, Throwable exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(String tag, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(String tag, String message, Throwable exception) {
		// TODO Auto-generated method stub

	}

}
