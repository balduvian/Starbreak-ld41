package starBreaker.scripts;

import static org.lwjgl.glfw.GLFW.*;

import game.Game;
import game.Script;

public class BackgroundScript extends Script{
	
	private float lvlW2;
	private float lvlH2;
	
	private float xOff;
	private float yOff;
	private float rotation;
	private float parallax;	
	private float scale;
	
	private float movement;
	private float speed;
	
	private float brap;
	
	@Override
	public Script clone() {
		return new BackgroundScript();
	}

	@Override
	public void start(float[] params) {
		xOff = params[0];
		yOff = params[1];
		scale = params[2];
		speed = params[3];
		parallax = scene.getIndex().getLayers()[parent.getLayer()].getParallax();
		parent.setRotation(rotation);
		parent.setSize(scale, scale);
		lvlW2 = scene.getMap().getWidth()/2;
		lvlH2 = scene.getMap().getHeight()/2;
	}

	@Override
	public void update() {
		movement += speed*Game.time;
		movement %= scale;
		
		if(scene.getIndex().getWindow().keyPressed(GLFW_KEY_W)) {
			brap += Game.time/10;
			System.out.println(brap);
		}
		
		if(scene.getIndex().getWindow().keyPressed(GLFW_KEY_W)) {
			brap -= Game.time/10;
			System.out.println(brap);
		}

		
		
		parent.setPosition((Math.round(((scene.getCPos().x)*parallax*brap)/scale) )*scale+(float)(xOff), (Math.round(((scene.getCPos().y)*parallax*brap)/scale) )*scale+(float)(yOff)-movement);
		//this is a yeet!!!!!!!!!!!!!!!!!!!!!!!!!!! OOF not really anymore
	}

}
