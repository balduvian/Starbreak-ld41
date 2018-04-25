package starBreaker.scripts;

import game.Block;
import game.Game;
import game.Script;
import starBreaker.index.StarIndex;

public class StarScript extends Script{

	private float speed;
	private float rotation;
	private int x;
	private int y;
	private Block block;
	
	private boolean dead;
	private double deathTimer;
	private double deathTime;
	
	@Override
	public Script clone() {
		return new StarScript();
	}

	@Override
	public void start(float[] params) {
		x = (int)parent.getX();
		y = (int)parent.getY();
		rotation = 0;
		dead = false;
		deathTime = 1;
		getSpeed();
	}

	@Override
	public void update() {
		if(scene.getIndex().getBool(StarIndex.VAR_INCONTROL)) {
			rotation += speed*Game.time;
			rotation %= (float)(Math.PI*2);
			parent.setRotation(rotation);
		}
		
		if(block.isUpdated()){
			getSpeed();
		}
		
		if(block.isDestroyed() && !dead) {
			scene.getIndex().setInt(StarIndex.VAR_STARS_COLLECTED, scene.getIndex().getInt(StarIndex.VAR_STARS_COLLECTED)+1);
			dead = true;
			deathTimer = 0;
			scene.soundEffect(StarIndex.SOUND_STAR_COLLECT, 0.5f);
		}
		
		if(dead) {
			if(deathTimer < deathTime) {
				deathTimer += Game.time;
				parent.setSize((float)(deathTimer/2+0.3), (float)(deathTimer/2+0.3));
				parent.setColor(1, 1, 1, 1-(float)(deathTimer/deathTime));
			}else {
				scene.destroy(parent);
			}
		}
	}
	
	public void getSpeed() {
		block = scene.getMap().access(x, y);
		speed = scene.getIndex().getFloat(StarIndex.VAR_PLANET_SPEEDS+block.getValue());
	}

}
