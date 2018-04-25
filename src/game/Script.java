package game;

import engine.Window;

public abstract class Script {
	
	protected GameObject parent;
	protected Scene scene;
	
	public void setParents(GameObject p, Scene s) {
		parent = p;
		scene = s;
	}
	
	abstract public Script clone();
	
	abstract public void start(float[] params);
	
	abstract public void update();
}
