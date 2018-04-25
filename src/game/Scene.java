package game;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

import starBreaker.index.StarIndex;

public class Scene {
	
	public static final float Z_LAYER_BKG = 0.9f;
	public static final float Z_LAYER_MAP_BKG = 0.5f;
	public static final float Z_LAYER_MAP = 0f;
	public static final float Z_LAYER_ENTITIES = -0.3f;
	public static final float Z_LAYER_GUI = -0.5f;
	
	private GameIndex index;
	
	private SortLayer[] sortLayers;
	private int layers;
	
	private Map map;
	private int startMap;
	
	private int u;
	private int r;
	private int d;
	private int l;
	
	private Vector3f cPos;
	
	public Scene(GameIndex x, int sm) {
		index = x;
		startMap = sm;
		sortLayers = x.getLayers();
		layers = sortLayers.length;
	}
	
	public void start() {
		switchMap(startMap);
	}
	
	public void switchMap(int m) {
		index.setInt(StarIndex.VAR_LEVEL, m);
		for(int i = 0; i < layers; ++i) {
			SortLayer la = sortLayers[i];
			if(!la.isMap()) {
				la.clear();
			}
		}
		map = index.maps(m);
		GameObject[] list = map.getEntities();
		int[][] pos = map.getPositions();
		int[][] dat = map.getData();
		int l = list.length;
		for(int i = 0; i < l; ++i) {
			createEntity(list[i], pos[i][0], pos[i][1], new float[] {dat[i][0]});
		}
		
		update();
		render();
	}
	
	public void update() {
		
		for(int i = layers-1; i > -1; --i) {
			SortLayer la = sortLayers[i];
			if(la.isMap()) {
				
				u = (int)Math.round(index.camera.getY() - index.gameHeight/2-1);
				r = (int)Math.round(index.camera.getX() + index.gameWidth/2+1);
				d = (int)Math.round(index.camera.getY() + index.gameHeight/2+1);
				l = (int)Math.round(index.camera.getX() - index.gameWidth/2-1);
				
				if(index.getInt(StarIndex.VAR_INCONTROL)==1) {
					map.update(u, r, d, l, index.varaibleList());
				}
			}else {
				cPos = new Vector3f(index.camera.getPosition());
				la.update(index.getCamera());
			}
		}		
	}
	
	public void render() {
		
		cPos = new Vector3f(index.camera.getPosition());
		for(int i = 0; i < layers; ++i) {
			SortLayer la = sortLayers[i];
			index.camera.setRotation(la.getRotation());
			index.camera.set(cPos.mul(la.getParallax(), new Vector3f()));
			index.camera.update();
			if(la.isMap()) {
				map.render(u, r, d, l, index.varaibleList());
			}else {
				la.render();
			}
		}
	
	}
	
	public GameIndex getIndex() {
		return index;
	}
	
	public Map getMap() {
		return map;
	}
	
	public void destroy(GameObject o) {
		sortLayers[o.getLayer()].remove(o.getIndex());
	}
	
	public GameObject createEntity(GameObject o, float x, float y, float[] params) {
		GameObject ret = o.create(this, x, y);
		sortLayers[o.getLayer()].add(ret);
		ret.setPosition(x, y);
		ret.getScript().start(params);
		return ret;
	}
	
	public void sendForward(GameObject o) {
		sortLayers[o.getLayer()].sendForward(o.getIndex());
	}
	
	public Vector3f getCPos(){
		return cPos;
	}
	
	public void soundEffect(int i, float v) {
		index.getSound(i).setVolume(v);
		index.getSound(i).play(false);
	}
}
