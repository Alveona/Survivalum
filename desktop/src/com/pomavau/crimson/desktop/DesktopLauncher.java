package com.pomavau.crimson.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pomavau.crimson.crimsonTD;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		System.setProperty("user.name", "EnglishWords");
		//config.useGL30 = true;
		config.width = 1145;
		config.height = 616;
		new LwjglApplication(crimsonTD.getInstance(), config);
	}
}
