package core;

import windows.Window;

public class MainGame {

	public static void main(String[] args) 
	{
		Window w = new Window(1280, 720, 10, 10, "Platform Game");
		w.initializeData();
		while(w.isVisible())
		{
			w.refreshFrame();
		}
		w.clearUp();
	}
}
