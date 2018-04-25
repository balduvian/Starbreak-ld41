package starBreaker.scripts;

import game.Game;
import game.Script;
import starBreaker.index.StarIndex;

public class CountdownScript extends Script{

	private Action[] actionList;
	private int current;
	
	public Script clone() {
		return new CountdownScript();
	}

	@Override
	public void start(float[] params) {
		if(params[0] == 0) {
			actionList = new Action[] {
				new Action(StarIndex.SOUND_SWOOSH, 0.5f),
				new Action(0, 0, -(float)(Math.PI/12), 1.2f, 0.25, 0),
				new Action(0, 0, (float)(Math.PI/24), 0.833333333333f, 0.25, 0),
				new Action(0, 0, -(float)(Math.PI/48), 1f, 0.25, 0),
				new Action(0, 0, 0, 1f, 0.25, 0),
				
				new Action(StarIndex.SOUND_SWOOSH, 0.5f),
				new Action(0, 0, (float)(Math.PI/12), 1.2f, 0.25, 1),
				new Action(0, 0, -(float)(Math.PI/24), 0.833333333333f, 0.25, 1),
				new Action(0, 0, (float)(Math.PI/48), 1f, 0.25, 1),
				new Action(0, 0, 0, 1f, 0.25, 1),
				
				new Action(StarIndex.SOUND_SWOOSH, 0.5f),
				new Action(0, 0, -(float)(Math.PI/12), 1.2f, 0.25, 2),
				new Action(0, 0, (float)(Math.PI/24), 0.833333333333f, 0.25, 2),
				new Action(0, 0, -(float)(Math.PI/48), 1f, 0.25, 2),
				new Action(0, 0, 0, 1f, 0.25, 2),
				
				
			};
		}else if(params[0] == 1) {
			actionList = new Action[] {
					new Action(0, 0, -(float)(Math.PI/12), 1.5f, 0.333333333, 0),
					new Action(0, 0, (float)(Math.PI/24), 0.666666666667f, 0.333333333, 0),
					new Action(0, 0, -(float)(Math.PI/48), 1f, 0.333333333, 0),
					new Action(0, 0, 0, 1f, 0.333333333, 0),
					new Action(0, 0, 0, 1f, 1.666666666, 0),
				};
		}
		current = 0;
		parent.setGui(true);
	}

	@Override
	public void update() {
		try {
			if(actionList[current].update()) {
				++current;
			}
			if(current == actionList.length) {
				scene.destroy(parent);
			}
		}catch(Exception ex) {
			
		}
		
	}
	
	private class Action{
		
		float xi, xd;
		float yi, yd;
		
		float ri, rd;
		
		float sd;
		
		float wi;
		float hi;
		
		double time;
		double timer;
		
		int frame;
		
		int sound;
		float volume;
		
		public Action(int s, float f) {
			sound = s;
			volume = f;
		}
		
		public Action(float x, float y, float r, float s, double i, int f) {
			xd = x;
			yd = y;
			rd = r;
			sd = s;
			time = i;
			timer = 0;
			frame = f;
			sound = -1;
		}
		
		void start() {
			xi = parent.getX();
			yi = parent.getY();
			ri = parent.getRotation();
			wi = parent.getSize().x;
			hi = parent.getSize().y;
			parent.setFrame(frame);
			if(sound != -1) {
				scene.soundEffect(sound, volume);
			}
		}
		
		boolean update() {
			boolean done = false;
			if(timer == 0) {
				start();
			}
			
			if(sound != -1) {
				return true;
			}
			
			timer += Game.time;
			
			if(timer >= time) {
				timer = time;
				done = true;
			}
			
			float along = (float)Math.sqrt(1 - Math.pow((timer/time)-1, 2) );
			
			parent.setX((float)(xi + along * (xd-xi)));
			parent.setY((float)(yi + along * (yd-yi)));
			
			parent.setRotation((float)(ri + along * (rd-ri)));
			
			parent.setSize((float)(wi + along * ((wi*sd)-wi)), (float)(hi + along * ((hi*sd)-hi)));		
			return done;
		}
	}

}
