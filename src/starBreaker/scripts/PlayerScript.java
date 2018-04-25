package starBreaker.scripts;

import engine.Window;
import game.Game;
import game.Script;
import game.Variable;
import starBreaker.index.StarIndex;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerScript extends Script{

	private boolean player;
	
	private Window window;
	private float rotation;
	private boolean grounded;
	private float speed;
	private float jumpSpeed;
	
	private float velocity;
	private float acceleration;
	private Variable inControl;
	
	private boolean jumped;
	
	private int color;
	
	private boolean locked;
	
	public Script clone() {
		return new PlayerScript();
	}
	
	private double aiTimer;
	
	@Override
	public void start(float[] params) {
		window = scene.getIndex().getWindow();
		inControl = scene.getIndex().getVar(StarIndex.VAR_INCONTROL);
		grounded = true;
		
		rotation = (float)(3*Math.PI/2);
		
		jumpSpeed = 1f;
		acceleration = 0.1f;
		velocity = 0;
		
		player = params[0] == 0;
		if(!player) {
			parent.setColor(0.35f, 0.35f, 0.35f);
			reTime();
		}
		
		parent.setFrame((int)params[0]);
		
		locked = false;
		
		planetPosition();
	}

	@Override
	public void update() {
		
		jumped = false;
		
		if(inControl.getBool()) {
			if(grounded) {			
				rotation += (Game.time*speed);
				rotation %= (2*Math.PI);
				
				planetPosition();
				
				if(player) {
					if(window.keyPressed(GLFW_KEY_SPACE) && !locked) {
						
						velocity = jumpSpeed;
						grounded = false;
						
						scene.soundEffect(StarIndex.SOUND_JUMP, 0.5f);
						
					}
				}else {
					aiTimer -= Game.time;
					if(aiTimer <= 0) {
						
						reTime();
						
						if(parent.getOnScreen()) {
							velocity = jumpSpeed;
							grounded = false;
						}
					}
				}
			}else {
				velocity += acceleration;
				
				parent.movePosition((float)Math.cos(rotation)*velocity, (float)Math.sin(rotation)*velocity);
				
				int centerX = Math.round(parent.getX());
				int centerY = Math.round(parent.getY());
				
				if(scene.getMap().access(centerX, centerY).isSolid() && Math.pow(parent.getX()-centerX, 2)+Math.pow(parent.getY()-centerY, 2) < 0.125) {
					grounded = true;
					
					rotation = (float)Math.atan2((parent.getY()-centerY), (parent.getX()-centerX));//YEEEEEEEEEEEEEEET
					
					jumped = true;
					
					if(player) {
						scene.soundEffect(StarIndex.SOUND_LAND, velocity/speed);
					}
					
					planetPosition();
				}
			}
		}
		
		parent.setRotation(rotation+(float)Math.PI/2);
	}
	
	public void lockdown() {
		locked = true;
	}
	
	public int[] planetPosition() {
		int centerX = Math.round(parent.getX());
		int centerY = Math.round(parent.getY());
		
		speed = scene.getIndex().getFloat(StarIndex.VAR_PLANET_SPEEDS+scene.getMap().access(centerX, centerY).getValue());
		
		parent.setX(centerX + (float)Math.cos(rotation)*0.35f);
		parent.setY(centerY + (float)Math.sin(rotation)*0.35f);
	
		return new int[] {centerX, centerY};
	}
	
	public void setColor(int c) {
		color = c;
		parent.setColor(scene.getIndex().getFloat(StarIndex.VAR_PLANET_COLORS+c*3), scene.getIndex().getFloat(StarIndex.VAR_PLANET_COLORS+c*3+1), scene.getIndex().getFloat(StarIndex.VAR_PLANET_COLORS+c*3+2));
	}
	
	public boolean getJumped() {
		return jumped;
	}
	
	private void reTime() {
		aiTimer = Math.random()*3+1;
	}
	
	public boolean getGrounded() {
		return grounded;
	}
	
}