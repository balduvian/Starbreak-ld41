package game;

import java.util.ArrayList;

import engine.Camera;

public class SortLayer {
	
	private int len;
	private ArrayList<GameObject> entities;
	private float min;
	private float max;
	private float rotation;
	private float parallax;
	private boolean mapLayer;
	
	public SortLayer(float i, float a, boolean m, float r, float p) {
		rotation = r;
		parallax = p;
		min = i;
		max = a;
		mapLayer = m;
		if(!m) {
			len = 0;
			entities = new ArrayList<GameObject>();
		}
	}
	
	public void update(Camera c) {
		for(int i = 0; i < len; ++i) {
			GameObject o = entities.get(i);
			o.setZ(min+(((float)i/len)*(max-min+0.001f)));
			
			o.setOnScreen(o.getX()+o.getW() >= c.getX()-c.getWidth()/2 && o.getX()-o.getW() <= c.getX()+c.getWidth()/2 && o.getY()+o.getH() >= c.getY()-c.getHeight()/2 && o.getY()-o.getH() <= c.getY()+c.getHeight()/2);
			
			o.update();
		}
	}
	
	public void render() {
		for(int i = 0; i < len; ++i) {
			entities.get(i).render();
		}
	}
	
	public void add(GameObject o) {
		o.setIndex(len);
		entities.add(o);
		++len;
	}
	
	public void remove(int i) {
		entities.remove(i);
		--len;
		for(int g = i; g < len; ++g) {
			entities.get(g).setIndex(entities.get(g).getIndex()-1);
		}
	}
	
	public void clear() {
		for(int g = len-1; g > -1; --g) {
			GameObject i = entities.get(g);
			if(!i.isEternal()) {
				remove(g);
			}
		}
	}
	
	public boolean isMap() {
		return mapLayer;
	}
	
	public void sendForward(int i) {
		
		GameObject temp = entities.get(i);
		remove(i);
		add(temp);
		
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public float getParallax() {
		return parallax;
	}
}
