package starBreaker.scripts;

import game.Game;
import game.GameObject;
import game.Script;
import starBreaker.index.StarIndex;

public class LogoScript extends Script{

	private double startTimer;
	private double fadeTimer;
	
	private GameObject white;
	private GameObject playButton;
	//private GameObject helpButton;
	private GameObject helpWall;
	private GameObject helpText;
	
	private float cSpeed;
	private float movement;
	
	private int m1;
	private int m2;
	
	private boolean ready;
	
	@Override
	public Script clone() {
		return new LogoScript();
	}

	@Override
	public void start(float[] params) {
		
		ready = false;
		
		helpText = scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_DUMMY), 0, 0, new float[] {});
		
		helpWall = scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_HELP_WALL), 0, 0, new float[] {2, 1});
		helpWall.setEnabled(false);
		
		helpText.giveFont(scene.getIndex().getFont(StarIndex.FONT_STAR_FONT), true);
		helpText.setText("press space to jump ffffffffffffffffffffffffff");//\n \n land on planets to \n change their color \n \n match 3 in a row \n \n collect stars by \n breaking planets
		helpText.setEnabled(false);
		helpText.setGui(true);
		
		parent.setGui(true);
		white = scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_WHITE), 0, 0, new float[] {});
		white.setGui(true);
		scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_TITLE), 0, -0.625f, new float[] {}).setRainbow(true);
		
		playButton = scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_BUTTON), 0, 0.15f, new float[] {0, 0});
		//helpButton = scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_BUTTON), 0, 0.5625f, new float[] {1, 0});
		
		for (int i = -5; i < 6; i++) {
			for (int j = -3; j < 4; j++) {
				scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_BACKGROUND1), 0, 0, new float[] {i*1f,j*1f,1f, 0.025f});
				scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_BACKGROUND2), 0, 0, new float[] {i*1.5f,j*1.5f,1.5f, 0.05f});
			}
		}
		
		cSpeed = 0.75f;
		
		if(scene.getIndex().getVar(StarIndex.VAR_ALREADY_SEEN).getBool()) {
			startTimer = 0;
			((ButtonScript)playButton.getScript()).setEnable(true);
			//((ButtonScript)helpButton.getScript()).setEnable(true);
			parent.setColor(1, 1, 1, 0);
			white.setColor(1, 1, 1, 0);
			ready = true;
		}else {
			scene.getIndex().getVar(StarIndex.VAR_ALREADY_SEEN).setBool(true);
			startTimer = 2;
		}
		
		reM();
	}

	@Override
	public void update() {
		if(startTimer > 0) {
			startTimer -= Game.time;
			if(startTimer <= 0) {
				fadeTimer = 2;
			}
		}
		
		if(fadeTimer > 0) {
			fadeTimer -= Game.time;
			parent.setColor(1, 1, 1, (float)(fadeTimer/1));
			white.setColor(1, 1, 1, (float)(fadeTimer/1));
			if(fadeTimer <= 0) {
				((ButtonScript)playButton.getScript()).setEnable(true);
				//((ButtonScript)helpButton.getScript()).setEnable(true);
				ready = true;
			}
		}
		
		if(ready) {
			if(scene.getIndex().getBool(StarIndex.VAR_HELP_UP)) {
				//helpWall.setEnabled(true);
				helpText.setEnabled(true);
				((ButtonScript)playButton.getScript()).setEnable(false);
				//((ButtonScript)helpButton.getScript()).setEnable(false);
			}else {
				//helpWall.setEnabled(false);
				helpText.setEnabled(false);
				((ButtonScript)playButton.getScript()).setEnable(true);
				//((ButtonScript)helpButton.getScript()).setEnable(true);
			}
			
			movement += cSpeed * Game.time;
			
			if(movement >= Math.PI*2) {
				reM();
				movement %= Math.PI*2;
			}
			scene.getIndex().getCamera().setPosition(m1*(float)(Math.sin(movement)*0.03), m2*(float)(Math.sin(movement)*0.03));
		}
	}

	private void reM() {
		if(Math.random()>0.5) {
			m1 = 1;
		}else {
			m1 = -1;
		}
		if(Math.random()>0.5) {
			m2 = 1;
		}else {
			m2 = -1;
		}
	}
}
