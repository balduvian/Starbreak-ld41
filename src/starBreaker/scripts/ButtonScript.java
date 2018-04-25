package starBreaker.scripts;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Camera;
import engine.Window;
import game.Script;
import starBreaker.index.StarIndex;

import static org.lwjgl.glfw.GLFW.*;

public class ButtonScript extends Script{

	boolean enabled;
	
	boolean ready;
	boolean hover;
	boolean pressed;
	boolean highlight;
	Window w;
	Camera c;
	
	float up;
	float right;
	float down;
	float left;
	
	int func;
	
	@Override
	public Script clone() {
		return new ButtonScript();
	}

	@Override
	public void start(float[] params) {
		hover = false;
		pressed = false;
		ready = false;
		highlight = false;
		func = (int)params[0];
		enabled = params[1] == 1;
		parent.giveFont(scene.getIndex().getFont(StarIndex.FONT_STAR_FONT), true);
		switch(func) {
		case(0):
			parent.setText("PLAY");
			break;
		case(1):
			parent.setText("HELP");
			break;
		case(2):
			parent.setText("");
		}
		w = scene.getIndex().getWindow();
		c = scene.getIndex().getCamera();
		up = parent.getY()-parent.getH()/2;
		right = parent.getX()+parent.getW()/2;
		down = parent.getY()+parent.getH()/2;
		left = parent.getX()-parent.getW()/2;
	}

	//ah yes, the joyous button pipeline
	@Override
	public void update() {
		if(enabled) {
			Vector3f mouse = w.getMouseCoords(c);
			boolean d = w.mousePressed(GLFW_MOUSE_BUTTON_1);
			
			hover = (mouse.x > left && mouse.x < right && mouse.y > up && mouse.y < down);
			
			if(highlight) {
				if(d) {
					if(ready && hover){
						ready = false;
						action();
					}
				}else {
					ready = true;
					if(!hover) {
						highlight = false;
					}
				}
			}else {
				parent.setRainbow(false);
				if(hover) {
					highlight = true;
					ready = !d;
				}
			}
			parent.setRainbow(hover);
		}else {
			parent.setRainbow(false);
		}
	}

	private void action() {
		switch(func) {
			case(0):
				scene.getIndex().switchScene(StarIndex.SCENE_GAME_SCENE);
				break;
			case(1):
				scene.getIndex().setBool(StarIndex.VAR_HELP_UP, true);
				break;
			case(2):
				scene.getIndex().setBool(StarIndex.VAR_HELP_UP, false);
				break;
		}
	}
	
	public void setEnable(boolean b) {
		enabled = b;
	}
}
