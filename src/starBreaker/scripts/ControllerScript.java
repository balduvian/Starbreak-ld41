package starBreaker.scripts;

import java.util.ArrayList;

import engine.Camera;
import game.Block;
import game.Game;
import game.GameObject;
import game.Map;
import game.Script;
import game.Variable;
import starBreaker.index.StarIndex;

public class ControllerScript extends Script{

	private double startTimer;
	private GameObject player;
	private int lvlW;
	private int lvlH;
	private Camera camera;
	private Variable inControl;
	
	private double restartTimer;
	private boolean dead;
	private double advanceTimer;
	private boolean ascend;
	
	private float shakeFactor;
	
	private int nextColor;
	
	private boolean revalidate;
	
	private double comboTimer;
	
	private GameObject levelText;
	private GameObject starText;
	private int starGoal;
	private Variable stars;
	
	private ArrayList<GameObject> eList;
	private int enemies;
	private int enemyGoal;
	
	@Override
	public Script clone() {
		return new ControllerScript();
	}

	@Override
	public void start(float[] params) {
		
		if(!scene.getIndex().getBool(StarIndex.VAR_TRANSITION_CREATE)) {
			scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_TRANSITION), 0, 0, new float[] {});
			scene.getIndex().setBool(StarIndex.VAR_TRANSITION_CREATE, true);
		}
		
		lvlW = scene.getMap().getWidth();
		lvlH = scene.getMap().getHeight();
		camera = scene.getIndex().getCamera();
		
		levelText = scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_DUMMY), -1.925f, -1.025f, new float[] {});
		levelText.giveFont(scene.getIndex().getFont(StarIndex.FONT_STAR_FONT), false);
		levelText.setGui(true);
		
		starText = scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_DUMMY), -1.925f, -0.85f, new float[] {});
		starText.giveFont(scene.getIndex().getFont(StarIndex.FONT_STAR_FONT), false);
		starText.setGui(true);
		
		player = scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_PLAYER), parent.getX(), parent.getY(), new float[] {0});
		
		inControl = scene.getIndex().getVar(StarIndex.VAR_INCONTROL);
		dead = false;
		ascend = false;
		startTimer = 3;
		
		enemyGoal = scene.getIndex().getInt(StarIndex.VAR_LEVEL)*2;
		enemies = 0;
		eList = new ArrayList<GameObject>();
		
		stars = scene.getIndex().getVar(StarIndex.VAR_STARS_COLLECTED);
		stars.setInt(0);
		starGoal = scene.getMap().getEntityCount(StarIndex.ENTITY_STAR);
				
		revalidate = false;
		
		for (int i = -9; i < 10; i++) {
			for (int j = -9; j < 10; j++) {
				scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_BACKGROUND1), 0, 0, new float[] {i*1f,j*1f,1f, 0.025f});
				scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_BACKGROUND2), 0, 0, new float[] {i*1.5f,j*1.5f,1.5f, 0.05f});
			}
		}
		
		scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_COUNTDOWN), 0, 0, new float[] {0}).setRainbow(true);
		
		int[][] initial = new int[lvlW-2][lvlH-2];
		for(int i = 0;i < lvlW-2; ++i) {
			for(int j = 0; j < lvlH-2; ++j) {
				int st = (int)(Math.random()*6);
				try {
					if(initial[i-1][j] == st) {
						++st;
						st %= 6;
					}
				}catch(Exception ex) {}
				try {
					if(initial[i][j-1] == st) {
						++st;
						st %= 6;
					}
				}catch(Exception ex) {}
				
				initial[i][j] = st;
				
				scene.getMap().access(i+1, j+1).setValue(st);
			}
		}
		
		while(enemies < enemyGoal) {
			spawnEnemy();
		}
		
		nextColor = (int)(Math.random()*6);
		newColor();
	}

	private void death() {
		dead = true;
		shakeFactor = 0.1f;
		restartTimer = 3;
		scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_OOF), 0, 0, new float[] {1}).setRainbow(true);
		scene.soundEffect(StarIndex.SOUND_OOF, 0.5f);
		
		scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), player.getX(), player.getY(), new float[] {(float)(Math.random()*(Math.PI/6)+(Math.PI/2)), 0.5f, 0.1f, nextColor, 1});
		scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), player.getX(), player.getY(), new float[] {(float)(Math.random()*(Math.PI/6)+(7*Math.PI/4)), 0.5f, 0.1f, nextColor, 1});
		scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), player.getX(), player.getY(), new float[] {(float)(Math.random()*(Math.PI/6)+(5*Math.PI/4)), 0.5f, 0.1f, nextColor, 1});
		
		scene.destroy(player);
	}
	
	@Override
	public void update() {
		
		if(startTimer > 0) {
			startTimer -= Game.time;
			 inControl.setBool(false);
		}else {
			if(!dead && !ascend) {
				if(player.getX()<-1 || player.getX() > scene.getMap().getWidth() || player.getY()<-1 || player.getY() > scene.getMap().getHeight() ) {
					death();
				}else if(stars.getInt()==starGoal){
					ascend = true;
					
					scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_YEET), 0, 0, new float[] {1}).setRainbow(true);
					
					starText.setRainbow(true);
					levelText.setRainbow(true);
					if(scene.getIndex().getVar(StarIndex.VAR_LEVEL).getInt() == StarIndex.MAP_LEVEL5){
						scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_YEET), 1, 0.5625f, new float[] {1}).setRainbow(true);
						scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_YEET), 1, -0.5625f, new float[] {1}).setRainbow(true);
						scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_YEET), -1, 0.5625f, new float[] {1}).setRainbow(true);
						scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_YEET), -1, -0.5625f, new float[] {1}).setRainbow(true);
						advanceTimer = 5;
					}else {
						advanceTimer = 3;
					}
					scene.soundEffect(StarIndex.SOUND_WIN, 0.5f);
					((PlayerScript)player.getScript()).lockdown();
					
					for(int i = enemies; i > -1; --i) {
						try {
							killEnemy(i);
						}catch(Exception ex) {}
					}
				}
				startTimer = 0;
				inControl.setBool(true);
			}
		}
		
		if(advanceTimer > 0) {
			advanceTimer -= Game.time;
			if(advanceTimer < 0.5){
				scene.getIndex().setBool(StarIndex.VAR_TRANSITION_TIME, true);
			}
			if(scene.getIndex().getVar(StarIndex.VAR_LEVEL).getInt() == StarIndex.MAP_LEVEL5){
				scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_YEET), 0, 0, new float[] {1}).setRainbow(true);
			}
		}else if (ascend){
			if(scene.getIndex().getVar(StarIndex.VAR_LEVEL).getInt() == StarIndex.MAP_LEVEL5) {
				scene.getIndex().switchScene(StarIndex.SCENE_TITLE_SCENE);
			}else {
				scene.switchMap(scene.getIndex().getInt(StarIndex.VAR_LEVEL)+1);
			}
		}
		
		
		if(restartTimer > 0) {
			restartTimer -= Game.time;
			if(restartTimer < 0.5){
				scene.getIndex().setBool(StarIndex.VAR_TRANSITION_TIME, true);
			}		
		}else if(dead){
			scene.switchMap(scene.getIndex().getInt(StarIndex.VAR_LEVEL));
		}
		
		
		if(((PlayerScript)player.getScript()).getJumped()) {
			
			Block b = scene.getMap().access((int)Math.round(player.getX()), (int)Math.round(player.getY()));
			b.setValue(nextColor);
			b.updateNotify();
			newColor();
		}
		
		camera.setPosition(player.getPosition());
		
		if(camera.getX() < 0) {
			camera.setX(0);
		}
		if(camera.getY() < 0) {
			camera.setY(0);
		}
		if(camera.getX() > lvlW-1) {
			camera.setX(lvlW-1);
		}
		if(camera.getY() > lvlH-1) {
			camera.setY(lvlH-1);
		}
		
		if(shakeFactor>0) {
			camera.move((float)(Math.random()-0.5)*shakeFactor, (float)(Math.random()-0.5)*shakeFactor, 0);
			shakeFactor -= 0.1*Game.time;
			if(shakeFactor < 0) {
				shakeFactor = 0;
			}
		}
		
		if(revalidate) {
			if(comboTimer > 0) {
				comboTimer -= Game.time;
			}else {
				revalidate = false;
				check();
			}
		}
		
		if(enemies < enemyGoal) {
			spawnEnemy();
		}
		
		for(int i = 0; i < enemies; ++i) {
			GameObject enemy = eList.get(i);
			if(enemy.getX()<-1 || enemy.getX() > scene.getMap().getWidth() || enemy.getY()<-1 || enemy.getY() > scene.getMap().getHeight()) {
				killEnemy(i);
			}else if(((PlayerScript)enemy.getScript()).getGrounded() && scene.getMap().access(Math.round(enemy.getX()), Math.round(enemy.getY())).isDestroyed()){
				killEnemy(i);
			}else if(!dead && enemy.getX()-enemy.getW()/2 < player.getX()+player.getW()/2 && enemy.getX()+enemy.getW()/2 > player.getX()-player.getW()/2 && enemy.getY()-enemy.getH()/2 < player.getY()+player.getH()/2 && enemy.getY()+enemy.getH()/2 > player.getY()-player.getH()/2) {
				death();
			}else if(((PlayerScript)enemy.getScript()).getJumped()){
				int x = Math.round(enemy.getX());
				int y = Math.round(enemy.getY());
				
				float w2 = scene.getIndex().getGameWidth()/2;
				float h2 = scene.getIndex().getGameHeight()/2;
				if(enemy.getOnScreen()) {
					if( ((PlayerScript)player.getScript()).getGrounded() && Math.round(player.getX()) == x && Math.round(player.getY()) == y) {
						death();
					}
					scene.getMap().access(x, y).corruptNotify();
					scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), x, y, new float[] {(float)(Math.random()*(Math.PI/6)+(Math.PI/4)), 0.5f, 0.1f, 6, 0});
					scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), x, y, new float[] {(float)(Math.random()*(Math.PI/6)+(3*Math.PI/4)), 0.5f, 0.1f, 6, 0});
					scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), x, y, new float[] {(float)(Math.random()*(Math.PI/6)+(5*Math.PI/4)), 0.5f, 0.1f, 6, 0});
					scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), x, y, new float[] {(float)(Math.random()*(Math.PI/6)+(7*Math.PI/4)), 0.5f, 0.1f, 6, 0});
				}
			}
		}
		
		levelText.setText("LEVEL: " + Integer.toString(scene.getIndex().getInt(StarIndex.VAR_LEVEL)));
		starText.setText("STARS: " + Integer.toString(stars.getInt()) + "/" + Integer.toString(starGoal));
	}
	
	public void spawnEnemy() {
		int randX = (int)(Math.random()*lvlW);
		int randY = (int)(Math.random()*lvlH);
		if(scene.getMap().access(randX, randY).isSolid() && (Math.abs(player.getX()-randX) > scene.getIndex().getGameWidth()/2 || Math.abs(player.getY()-randY) > scene.getIndex().getGameHeight()/2)) {
			GameObject eTemp = scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_PLAYER), randX, randY, new float[] {1});
			int[] kk = ((PlayerScript)eTemp.getScript()).planetPosition();
			//System.out.println(randX + " " +randY+" | "+kk[0]+ " "+kk[1]+ " | "+scene.getMap().access(kk[0], kk[1]).isSolid()+" "+scene.getMap().access(randX, randY).isSolid());
			
			eList.add(eTemp);
			++enemies;
		}
	}
	
	public void killEnemy(int i) {
		
		GameObject enemy = eList.get(i);
		
		eList.remove(i);
		
		--enemies;
		scene.destroy(enemy);
		float w2 = scene.getIndex().getGameWidth()/2;
		float h2 = scene.getIndex().getGameHeight()/2;
		if(enemy.getX() > scene.getCPos().x-h2 && enemy.getX() < scene.getCPos().x+h2 && enemy.getY() > scene.getCPos().y-h2 && enemy.getY() < scene.getCPos().y+h2) {
			scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), enemy.getX(), enemy.getY(), new float[] {(float)(Math.random()*(Math.PI/6)+(Math.PI/2)), 0.5f, 0.1f, 6, 1});
			scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), enemy.getX(), enemy.getY(), new float[] {(float)(Math.random()*(Math.PI/6)+(7*Math.PI/4)), 0.5f, 0.1f, 6, 1});
			scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), enemy.getX(), enemy.getY(), new float[] {(float)(Math.random()*(Math.PI/6)+(5*Math.PI/4)), 0.5f, 0.1f, 6, 1});
		}
	}
	
	public void newColor() {
		
		int nc = (int)(Math.random()*6);
		if(nc == nextColor) {
			nextColor = (++nc%6);
		}else {
			nextColor = nc;
		}
		
		int startX = Math.round(player.getX());
		int startY = Math.round(player.getY());
		boolean found = false;
		
		superloop: for(int i = startX-1; i < startX+2; ++i) {
			int[] tots = new int[6];
			int flag = -1;
			for(int j = startY-1; j < startY+2; ++j) {
				if(scene.getMap().access(i, j).isSolid()) {
					int nn = scene.getMap().access(i, j).getValue();
					++tots[nn];
					//System.out.println("VV: " + nn + " | " + tots[nn]);
					if(tots[nn] == 2) {
						flag = nn;
					}
				}else {
					flag = -1;
				}
			}
			if(flag != -1) {
				//System.out.println(scene.getMap().access(startX, startY).getValue()+ " |  " +flag);
				if(i != startX || scene.getMap().access(startX, startY).getValue() == flag) {
					//System.out.println("found vert "+flag);
					nextColor = flag;
					found = true;
					break superloop;
				}else {
					//System.out.println("reject vert "+flag);
				}
			}
		}
		
		if(!found) {
			superloop: for(int j = startY-1; j < startY+2; ++j) {
				int[] tots = new int[6];
				int flag = -1;
				for(int i = startX-1; i < startX+2; ++i) {
					if(scene.getMap().access(i, j).isSolid()) {
						int nn = scene.getMap().access(i, j).getValue();
						++tots[nn];
						//System.out.println("HH: " + nn + " | " + tots[nn]);
						if(tots[nn] == 2) {
							flag = nn;
						}
					}else {
						flag = -1;
					}
				}
				if(flag != -1) {
					//System.out.println(scene.getMap().access(startX, startY).getValue()+ " |  " +flag);
					if(j != startY || scene.getMap().access(startX, startY).getValue() == flag) {
						//System.out.println("found horiz "+flag);
						nextColor = flag;
						found = true;
						break superloop;
					}else {
						//System.out.println("reject horix "+flag);
					}
				}
			}
		}
		
		((PlayerScript)player.getScript()).setColor(nextColor);
		
		revalidate = true;
	}
	
	private void check() {
		Map m = scene.getMap();
		
		ArrayList<int[]> blockArray = new ArrayList<int[]>(); 
		boolean broken = false;
		
		superloop: for(int i = 1; i < lvlW-1; ++i) {
			int c = -1;
			int t = 0;
			for(int j = 1; j < lvlH; ++j) {
				Block b = m.access(i, j);
				int nw = -2;
				if(b.isSolid()) {
					nw = m.access(i, j).getValue();
				}
				if(nw >= -1 && c >-1 && nw == c) {
					++t;
				}
				else {
					if(t >= 3) {
						for(int k = j-1; k > j-t-1; --k) {

							blockArray.add(new int[] {i, k});
							
						}
						break superloop;
					}

					c = m.access(i, j).getValue();
					t = 1;
				}
			}
		}
		
		superloop: for(int j = 1; j < lvlH; ++j) {
			int c = -1;
			int t = 0;
			for(int i = 1; i < lvlW; ++i) {
				Block b = m.access(i, j);
				int nw = -2;
				if(b.isSolid()) {
					nw = m.access(i, j).getValue();
				}
				if(nw >= -1 && c >-1 && nw == c) {
					++t;
				}
				else {
					if(t >= 3) {
						for(int k = i-1; k > i-t-1; --k) {
							
							blockArray.add(new int[] {k, j});
							
						}
						break superloop;
					}

					c = m.access(i, j).getValue();
					t = 1;
				}
			}
		}
		
		for(int[] i : blockArray) {
			breakBlock(i[0], i[1]);
			broken = true;
		}
		if(broken) {
			newColor();
			revalidate = true;
			comboTimer = 0.5;
			scene.soundEffect(StarIndex.SOUND_EXPLOSION, (float)(Math.random()*0.5+0.5));
		}
	}

	private void breakBlock(int i, int j) {
		Map m = scene.getMap();
		int c = m.access(i, j).getValue();
		System.out.println(i);
		int ch = m.access(i, j).getValue();
		int tempColor = (int)(Math.random()*6);
		if(tempColor == ch) {
			++tempColor;
			tempColor %= 6;
		}
		m.access(i, j).destroyNotify();
		m.access(i, j).setValue(tempColor);
		scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), i, j, new float[] {(float)(Math.random()*(Math.PI/6)+(Math.PI/4)), 0.5f, 0.1f, c, 0});
		scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), i, j, new float[] {(float)(Math.random()*(Math.PI/6)+(3*Math.PI/4)), 0.5f, 0.1f, c, 0});
		scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), i, j, new float[] {(float)(Math.random()*(Math.PI/6)+(5*Math.PI/4)), 0.5f, 0.1f, c, 0});
		scene.createEntity(scene.getIndex().getGameObject(StarIndex.ENTITY_SHARD), i, j, new float[] {(float)(Math.random()*(Math.PI/6)+(7*Math.PI/4)), 0.5f, 0.1f, c, 0});
		shakeFactor += 0.017f;
	}
}