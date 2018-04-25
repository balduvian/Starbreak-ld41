package starBreaker.scripts;

import game.Game;
import game.Script;
import starBreaker.index.StarIndex;

public class ShardScript extends Script {

	private float rotation;
	private float speed;
	private float acceleration;
	
	private int lvlW;
	private int lvlH;
	
	public Script clone() {
		return new ShardScript();
	}

	@Override
	public void start(float[] params) {
		rotation = params[0];
		speed = params[1];
		acceleration = params[2];
		
		int c = (int)params[3];
		parent.setColor(scene.getIndex().getFloat(StarIndex.VAR_PLANET_COLORS+c*3), scene.getIndex().getFloat(StarIndex.VAR_PLANET_COLORS+c*3+1), scene.getIndex().getFloat(StarIndex.VAR_PLANET_COLORS+c*3+2));
		
		parent.setFrame((int)params[4]);
		
		lvlW = scene.getMap().getWidth();
		lvlH = scene.getMap().getHeight();
		
		parent.setRotation(rotation);
	}

	@Override
	public void update() {
		speed += acceleration;
		
		parent.movePosition((float)(speed*Math.cos(rotation)), (float)(speed*Math.sin(rotation)));
		
		float x = parent.getX();
		float y = parent.getY();
		
		if(!parent.getOnScreen()) {
			scene.destroy(parent);
		}
	}

}
