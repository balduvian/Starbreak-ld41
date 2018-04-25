package starBreaker.index;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import engine.Shader;
import engine.Sound;
import engine.TexRect;
import engine.Texture;
import game.Block;
import game.Font;
import game.GameIndex;
import game.GameObject;
import game.Map;
import game.NullScript;
import game.Scene;
import game.SortLayer;
import game.Variable;
import starBreaker.scripts.BackgroundScript;
import starBreaker.scripts.ButtonScript;
import starBreaker.scripts.ControllerScript;
import starBreaker.scripts.CountdownScript;
import starBreaker.scripts.LogoScript;
import starBreaker.scripts.PlayerScript;
import starBreaker.scripts.ShardScript;
import starBreaker.scripts.StarScript;
import starBreaker.scripts.TransitionScript;

public class StarIndex extends GameIndex{
	
	public static final int ENTITY_PLAYER = 0;
	public static final int ENTITY_CONTROLLER = 1;
	public static final int ENTITY_BACKGROUND1 = 2;
	public static final int ENTITY_BACKGROUND2 = 3;
	public static final int ENTITY_COUNTDOWN = 4;
	public static final int ENTITY_OOF = 5;
	public static final int ENTITY_TRANSITION = 6;
	public static final int ENTITY_NEXT_COLOR = 7;
	public static final int ENTITY_NEXT_COLOR_SHEET = 8;
	public static final int ENTITY_SHARD = 9;
	public static final int ENTITY_DUMMY = 10;
	public static final int ENTITY_STAR = 11;
	public static final int ENTITY_YEET = 12;
	public static final int ENTITY_LOGO = 13;
	public static final int ENTITY_WHITE = 14;
	public static final int ENTITY_TITLE = 15;
	public static final int ENTITY_BUTTON = 16;
	public static final int ENTITY_HELP_WALL = 17;
	
	public static final int BLOCK_SPACE = 0;
	public static final int BLOCK_PLANET = 1;
	
	public static final int VAR_PLANET_COLORS = 0;
	public static final int VAR_PLANET_SPEEDS = 21;
	public static final int VAR_LEVEL = 27;
	public static final int VAR_INCONTROL = 28;
	public static final int VAR_TRANSITION_CREATE = 29;
	public static final int VAR_TRANSITION_TIME = 30;
	public static final int VAR_STARS_COLLECTED = 31;
	public static final int VAR_ALREADY_SEEN = 32;
	public static final int VAR_HELP_UP = 33;
	
	public static final int MAP_TITLE_MAP = 0;
	public static final int MAP_LEVEL1 = 1;
	public static final int MAP_LEVEL2 = 2;
	public static final int MAP_LEVEL3 = 3;
	public static final int MAP_LEVEL4 = 4;
	public static final int MAP_LEVEL5 = 5;
	
	public static final int SCENE_TITLE_SCENE = 0;
	public static final int SCENE_GAME_SCENE = 1;
	
	public static final int LAYER_BACKGROUND1 = 0;
	public static final int LAYER_BACKGROUND2 = 1;
	public static final int LAYER_MAP = 2;
	public static final int LAYER_ENTITIES = 3;
	public static final int LAYER_GUI = 4;
	public static final int LAYER_TRANSITION = 5;
	
	public static final int FONT_STAR_FONT = 0;
	
	public static final int SOUND_EXPLOSION = 0;
	public static final int SOUND_JUMP = 1;
	public static final int SOUND_LAND = 2;
	public static final int SOUND_OOF = 3;
	public static final int SOUND_STAR_COLLECT = 4;
	public static final int SOUND_SWOOSH = 5;
	public static final int SOUND_WIN = 6;
	
	public static void main(String args[]) {
		new StarIndex(4f, 2.25f, "Starbreak", SCENE_TITLE_SCENE);
	}
	
	public StarIndex(float gw, float gh, String n, int fs) {
		super(gw, gh, n, fs);
	}

	@Override
	protected void loadGameObjects() {
		gameObjectList = new GameObject[] {
			new GameObject(0.2f,0.2f, new TexRect(camera, new Texture("resources/textures/player.png", 2, 1), Shader.GAM2DSHADER), new PlayerScript(), true, LAYER_ENTITIES, 0),//player
			new GameObject(0f,0f, null, new ControllerScript(), true, LAYER_BACKGROUND1, 1),//controller
			new GameObject(1f,1f, new TexRect(camera, new Texture("resources/textures/bkg-1.png", 1, 1), Shader.GAM2DSHADER), new BackgroundScript(), true, LAYER_BACKGROUND1, 2),//background 1
			new GameObject(1f,1f, new TexRect(camera, new Texture("resources/textures/bkg-2.png", 1, 1), Shader.GAM2DSHADER), new BackgroundScript(), true, LAYER_BACKGROUND2, 3),//background 2
			new GameObject(1f,1f, new TexRect(camera, new Texture("resources/textures/countdown.png", 3, 1), Shader.RAI2DSHADER), new CountdownScript(), true, LAYER_GUI, 4),//countdown
			new GameObject(3f,1f, new TexRect(camera, new Texture("resources/textures/oof.png", 1, 1), Shader.RAI2DSHADER), new CountdownScript(), true, LAYER_GUI, 5),//oof
			new GameObject(14f,14f, new TexRect(camera, new Texture("resources/textures/black.png", 1, 1), Shader.GAM2DSHADER), new TransitionScript(), true, LAYER_TRANSITION, 6),//transition
			new GameObject(1f,0.6f, new TexRect(camera, new Texture("resources/textures/next-color.png", 1, 1), Shader.GAM2DSHADER), new NullScript(), true, LAYER_GUI, 7),//next color holder
			new GameObject(1f,0.6f, new TexRect(camera, new Texture("resources/textures/next-color-sheet.png", 1, 1), Shader.GAM2DSHADER), new NullScript(), true, LAYER_GUI, 8),//only the color part
			new GameObject(0.25f, 0.25f, new TexRect(camera, new Texture("resources/textures/shard.png", 2, 1), Shader.GAM2DSHADER), new ShardScript(), true, LAYER_ENTITIES, 9),//planet shard
			new GameObject(1f, 1f, null, new NullScript(), true, LAYER_GUI, 10),//used for putting text on
			new GameObject(0.3f, 0.3f, new TexRect(camera, new Texture("resources/textures/star.png", 1, 1), Shader.GAM2DSHADER), new StarScript(), true, LAYER_ENTITIES, 11),//star
			new GameObject(3f, 1f, new TexRect(camera, new Texture("resources/textures/yeet.png", 1, 1), Shader.RAI2DSHADER), new CountdownScript(), true, LAYER_GUI, 12),//winning message for a level
			new GameObject(1f, 2f, new TexRect(camera, new Texture("resources/textures/logo-bw.png", 1, 1), Shader.GAM2DSHADER), new LogoScript(), true, LAYER_TRANSITION, 13),//balduvian dead flash
			new GameObject(4f, 2.25f, new TexRect(camera, new Texture("resources/textures/white.png", 1, 1), Shader.GAM2DSHADER), new NullScript(), true, LAYER_GUI, 14),//white background
			new GameObject(3f, 0.5f, new TexRect(camera, new Texture("resources/textures/title.png", 1, 1), Shader.RAI2DSHADER), new NullScript(), true, LAYER_ENTITIES, 15),//title
			new GameObject(2f, 0.375f, new TexRect(camera, new Texture("resources/textures/button.png", 1, 1), Shader.RAI2DSHADER), new ButtonScript(), true, LAYER_ENTITIES, 16),//button);
			
			new GameObject(2f, 2f, new TexRect(camera, new Texture("resources/textures/help-wall.png", 1, 1), Shader.RAI2DSHADER), new ButtonScript(), true, LAYER_GUI, 16),//button);
		};
	}

	@Override
	protected void loadBlocks() {
		blockList = new Block[] {
			new Block(false, null),//space block
			new Block(true, new TexRect(camera, new Texture("resources/textures/planet.png"), Shader.CIR2DSHADER)),//planet block
		};
	}
	
	@Override
	protected void loadMaps() {
		mapList = new Map[] {
			new Map(genMap(), blockList, gameObjectList, BLOCK_SPACE),	
			new Map(genMap("resources/maps/level1.txt"), blockList, gameObjectList, BLOCK_SPACE),
			new Map(genMap("resources/maps/level2.txt"), blockList, gameObjectList, BLOCK_SPACE),
			new Map(genMap("resources/maps/level3.txt"), blockList, gameObjectList, BLOCK_SPACE),
			new Map(genMap("resources/maps/level4.txt"), blockList, gameObjectList, BLOCK_SPACE),
			new Map(genMap("resources/maps/level5.txt"), blockList, gameObjectList, BLOCK_SPACE),
		};
	}

	@Override
	protected void loadScenes() {
		sceneList = new Scene[] {
			new Scene(this, MAP_TITLE_MAP),
			new Scene(this, MAP_LEVEL1),
		};
	}
	
	private int[][][] genMap(String path){
		int[][][] ret = null;
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResource(path).openStream()));
			int c;
			int mode = 0;
			int x = 0;
			int y = 0;
			int width = 0;
			int height = 0;
			while((c = br.read()) != -1) {
				if(mode == 0) {
					if(x==0) {
						width = c;
						++x;
					}else {
						height = c;
						x = 0;
						ret = new int[width][height][3];
						for(int i = 0; i < width; ++i) {
							for(int j = 0; j < height; ++j) {
								ret[i][j][1] = -1;
							}
						}
						++mode; 
					}
				}else if(mode == 1) {
					if(c == 10) {
						x = 0;
						++y;
					}else {
						switch(c) {
							case('O'):
								ret[x][y][0] = BLOCK_SPACE;
								ret[x][y][1] = -1;
								break;
							case('C'):
								ret[x][y][0] = BLOCK_PLANET;
								ret[x][y][1] = ENTITY_CONTROLLER;
								break;
							case('P'):
								ret[x][y][0] = BLOCK_PLANET;
								ret[x][y][1] = -1;
								break;
							case('S'):
								ret[x][y][0] = BLOCK_PLANET;
								ret[x][y][1] = ENTITY_STAR;
								break;
						}
						++x;
					}
				}
			}
		}catch(Exception ex) {}
		return ret;
	}
	
	private int[][][] genMap() {
		return new int[][][] {
			{
				{
					BLOCK_SPACE, ENTITY_LOGO, -1
				},
			}
		};
	}

	@Override //the floats and ints have been united <3
	protected void loadVariables() {
		variableList = new Variable[] {
			new Variable(0f),//0
			new Variable(1f),//1
			new Variable(1f),//2
			new Variable(0f),//3
			new Variable(0f),//4
			new Variable(1f),//5
			new Variable(1f),//6
			new Variable(1f),//7
			new Variable(0f),//8
			new Variable(1f),//9
			new Variable(0f),//10
			new Variable(0f),//11
			new Variable(0f),//12
			new Variable(1f),//13
			new Variable(0f),//14
			new Variable(1f),//15
			new Variable(0f), //16
			new Variable(1f),//17
			new Variable(0.4f),//18
			new Variable(0.4f),//19
			new Variable(0.4f),//20
	
			new Variable(((float)Math.PI)), //speed 1       //21
			new Variable(((float)Math.PI*1.1f)),   //speed 2//22
			new Variable(((float)Math.PI*1.2f)), //speed 3//23
			new Variable((-(float)Math.PI)),//speed 4//24
			new Variable((-(float)Math.PI*1.1f)),  //speed 5//25
			new Variable((-(float)Math.PI*1.2f)),//speed 6//26
			
			new Variable(0),//level//27
			new Variable(0),//incontrol
			
			new Variable(0),//transition created?
			new Variable(0),//transition boi
			
			new Variable(0),//stars collected
			new Variable(0),//first open y-1
			new Variable(0),//HELP UP
		};
	}

	@Override
	protected void loadSortLayers() {
		sortLayerList = new SortLayer[] {
			new SortLayer(1,0.25f,false, (float)(Math.PI/4), 0.2f),//background
			new SortLayer(0.25f,0,false, (float)(5*Math.PI/6), 0.3f),//background 2
			new SortLayer(0.5f,0f,true, 0, 1),//map
			new SortLayer(0,-0.5f,false, 0, 1),//entities
			new SortLayer(-0.5f,-0.7f,false, 0, 1),//gui
			new SortLayer(-0.7f,-1f, false, 0, 1),//transition
		};
	}
	
	protected void loadFonts() {
		fontList = new Font[] {
			new Font(0.10f, 0.15f, new TexRect(camera, new Texture("resources/textures/charSheet.png", 16, 8), Shader.RAI2DSHADER)),
		};
	}
	
	protected void loadSounds() {
		soundList = new Sound[] {
			new Sound("resources/sounds/explosion.wav"),	
			new Sound("resources/sounds/jump.wav"),
			new Sound("resources/sounds/land.wav"),
			new Sound("resources/sounds/oof.wav"),
			new Sound("resources/sounds/star-collect.wav"),
			new Sound("resources/sounds/swoosh.wav"),
			new Sound("resources/sounds/win.wav"),
		};
	}
}
