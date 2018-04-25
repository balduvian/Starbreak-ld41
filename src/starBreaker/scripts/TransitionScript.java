package starBreaker.scripts;

import game.Game;
import game.Script;
import starBreaker.index.StarIndex;

public class TransitionScript extends Script{

	private boolean going;
	private double timer;
	
	private double transitionTime = 2;
	
	@Override
	public Script clone() {
		return new TransitionScript();
	}

	@Override
	public void start(float[] params) {
		going = false;
		parent.setRotation((float)Math.PI/6);
		parent.setGui(true);
		parent.setPosition(-16, 0);
		parent.eternalize();
	}

	@Override
	public void update() {
		
		if(going) {
			if(timer < transitionTime) {
				timer += Game.time;
			}else {
				scene.getIndex().setBool(StarIndex.VAR_TRANSITION_TIME, false);
				going = false;
			}
			
			float along = (float)Math.sqrt(1 - Math.pow((timer/transitionTime)-1, 2) );
			
			parent.setPosition(-16 + (along * 32), 0);
			
		}else {
			if(scene.getIndex().getBool(StarIndex.VAR_TRANSITION_TIME)) {
				
				going = true;
				timer = 0;
			}
		}
	}

}
