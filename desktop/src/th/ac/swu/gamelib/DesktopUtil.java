package th.ac.swu.gamelib;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopUtil {
	
	public static void setFullScreen(Lwjgl3ApplicationConfiguration config) {
		DisplayMode disp = Lwjgl3ApplicationConfiguration.getDisplayMode();
		config.setFullscreenMode(disp);
	}

}

