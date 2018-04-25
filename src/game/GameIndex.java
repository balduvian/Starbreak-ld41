package game;

import engine.ALManagement;
import engine.Camera;
import engine.Shader;
import engine.Sound;
import engine.Window;
import starBreaker.index.StarIndex;

abstract public class GameIndex {
	
	protected int firstScene;
	protected float gameWidth;
	protected float gameHeight;
	
	protected Window window;
	protected Camera camera;
	protected ALManagement audio;
	
	protected Block[] blockList;
	protected Map[] mapList;
	protected GameObject[] gameObjectList;
	protected Scene[] sceneList;
	protected Variable[] variableList;
	protected SortLayer[] sortLayerList;
	protected Font[] fontList;
	protected Sound[] soundList;
	
	protected String gameName;
	
	private Game game;
	
	public GameIndex(float gw, float gh, String n, int fs) {
		gameWidth = gw;
		gameHeight = gh;
		gameName = n;
		
		camera = new Camera(gameWidth, gameHeight);
		window = new Window(00, 450, gameName, true);
		audio = new ALManagement();
		Shader.init();
		
		loadGameObjects();
		loadBlocks();
		loadMaps();
		loadSortLayers();
		loadScenes();
		loadVariables();
		loadFonts();
		loadSounds();
		
		game = new Game(this, fs);
		
		game.begin();
	}
	
	protected abstract void loadGameObjects();
	
	protected abstract void loadMaps();
	
	protected abstract void loadBlocks();
	
	protected abstract void loadScenes();
	
	protected abstract void loadVariables();
	
	protected abstract void loadSortLayers();
	
	protected abstract void loadFonts();
	
	protected abstract void loadSounds();
	
	public String name() {
		return gameName;
	}
	
	public Block blocks(int i) {
		return blockList[i];
	}
	
	public Block[] blockList() {
		return blockList;
	}
	
	public Map maps(int i) {
		return mapList[i];
	}
	
	public GameObject getGameObject(int i) {
		return gameObjectList[i];
	}
	
	public Scene getScene(int i) {
		return sceneList[i];
	}
	
	public int getInt(int i) {
		return variableList[i].getInt();
	}
	
	public float getFloat(int i) {
		return variableList[i].getFloat();
	}
	
	public boolean getBool(int i) {
		return variableList[i].getBool();
	}
	
	public void setInt(int i, int v) {
		variableList[i].setInt(v);
	}
	
	public void setFloat(int i, float v) {
		variableList[i].setFloat(v);
	}
	
	public void setBool(int i, boolean v) {
		variableList[i].setBool(v);
	}
	
	public Variable getVar(int i) {
		return variableList[i];
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public Window getWindow() {
		return window;
	}
	
	public ALManagement getAudio() {
		return audio;
	}
	
	public float getGameWidth() {
		return gameWidth;
	}
	
	public float getGameHeight() {
		return gameHeight;
	}
	
	public void switchScene(int s) {
		game.switchScene(s);
	}
	
	public SortLayer[] getLayers() {
		return sortLayerList;
	}
	
	public Variable[] varaibleList() {
		return variableList;
	}
	
	public Font getFont(int i){
		return fontList[i];
	};
	
	public Sound getSound(int i) {
		return soundList[i];
	}
}
