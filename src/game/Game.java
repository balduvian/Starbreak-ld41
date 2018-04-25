package game;

import engine.ALManagement;
import engine.Camera;
import engine.Window;

//engine
//embgine

public class Game{
	
	private final int FPS = 60;
	
	public static int fps;
	public static double time;
	
	protected Window window;
	protected Camera camera;
	protected ALManagement audio;
	
	private Scene scene;
	private GameIndex index;
	
	public Game(GameIndex in, int fs) {
		
		window = in.getWindow();
		camera = in.getCamera();
		audio = in.getAudio();
		
		index = in;
		
		switchScene(fs);
	}
	
	public void begin() {
		long usingFPS = 1000000000 / FPS;
		
		long last = System.nanoTime();
		long lastSec = last;
		int frames = 0;
		while(!window.shouldClose()) {
			long now = System.nanoTime();
			if(now-last > usingFPS) {
				time = (now-last)/1000000000d;
				update();
				render();
				last = now; 
				++frames;
			}
			if(now-lastSec > 1000000000) {
				fps = frames; 
				frames = 0;
				lastSec = now;
				//System.out.println(fps);
			}
		}
		audio.destroy();
	}
	
	public void switchScene(int i) {
		scene = index.getScene(i);
		scene.start();
	}
	
	private void update() {
		window.update();
		
		scene.update();
		
		camera.update();
	}
	
	private void render() {
		window.clear();
		
		scene.render();
		
		window.swap();
	}
	
}