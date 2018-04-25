package game;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.TexRect;
import engine.Window;

public class GameObject {
	
	private float rotation;
	private Vector3f position;
	private Vector2f size;
	private TexRect texture;
	private Script script;
	private boolean enabled;
	private boolean invis;
	private int tag;
	private int index;
	private int layer;
	
	private boolean eternal;
	
	private int frame;
	private boolean gui;
	
	private float red, green, blue, alpha;
	
	private Font font;
	private char[] text;
	private boolean centered;
	
	private boolean rainbow;
	
	private int type;
	
	private boolean onScreen;
	
	public GameObject(float w, float h, TexRect t, Script sc, boolean e, int l, int p) {
		init(new Vector2f(w, h), t, sc, e, l, p);
	}
	
	public GameObject(Vector2f s, TexRect t, Script sc, boolean e, int l, int p) {
		init(s, t, sc, true, l, p);
	}
	
	private void init(Vector2f s, TexRect t, Script sc, boolean e, int l, int p) {
		size = new Vector2f(s);
		if(t == null) {
			invis = true;
		}else {
			invis = false;
			texture = t;
		}
		script = sc.clone();
		enabled = e;
		layer = l;
		position = new Vector3f(0,0,0);
		rotation = 0;
		frame = 0;
		gui = false;
		eternal = false;
		centered = false;
		rainbow = false;
		type = p;
		
		red = 1;
		green = 1;
		blue = 1;
		alpha = 1;
	}
	
	public void setRainbow(boolean b) {
		rainbow = b;
	}
	
	public void giveFont(Font f, boolean c) {
		font = f;
		text = new char[] {};
		centered = c;
	}
	
	public void setText(String c) {
		text = c.toCharArray();
	}
	
	public void eternalize() {
		eternal = true;
	}
	
	public boolean isEternal() {
		return eternal;
	}
	
	public void update() {
		if(enabled) {
			script.update();
		}
	}
	
	public void render() {
		if(enabled) {
			if(!invis){
				texture.setRotation(rotation);
				texture.setDims(size.x, size.y);
				
				texture.setPosition(position.x, position.y, position.z);
				Vector4f fr = texture.getTexture().getFrame(frame, 0);
				
				
				float[] pass = null;
				if(rainbow) {
					pass = new float[] {fr.x,fr.y,fr.z,fr.w,1,1,1,1,1};
				}else {
					pass = new float[] {fr.x,fr.y,fr.z,fr.w,red,green,blue,alpha,0};
				}
				
				if(gui) {
					texture.guiRender(pass);
				}else {
					texture.render(pass);
				}
			}
			if(font != null) {
				if(gui) {
					font.guiRender(position.x, position.y, text, centered, rainbow);
				}else {
					font.render(position.x, position.y, text, centered, rainbow);
				}
			}
		}
	}
	
	public GameObject create(Scene s, float x, float y) {
		GameObject ret = new GameObject(size, texture, script, enabled, layer, type);
		ret.script.setParents(ret, s);
		return ret;
	}
	
	public int getLayer() {
		return layer;
	}
	
	public void setIndex(int i) {
		index = i;
	}
	
	public void deIncrement() {
		--index;
	}
	
	public void setEnabled(boolean e) {
		enabled = e;
	}
	
	public void setTag(int t) {
		tag = t;
	}
	
	public void setPosition(float x, float y) {
		position = new Vector3f(x, y, position.z);
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public void setX(float v) {
		position.x = v;
	}
	
	public void setY(float v) {
		position.y = v;
	}
	
	public void setZ(float v) {
		position.z = v;
	}
	
	public float getW() {
		return size.x;
	}
	
	public float getH() {
		return size.y;
	}
	
	public void movePosition(float mx, float my) {
		position = position.add(new Vector3f((float)(mx*Game.time), (float)(my*Game.time), 0));
	}
	
	public void setSize(float w, float h) {
		size = new Vector2f(w, h);
	}
	
	public Vector2f getPosition() {
		return new Vector2f(position.x, position.y);
	}
	
	public Vector2f getSize() {
		return size;
	}
	
	public Script getScript() {
		return script;
	}
	
	public int getTag() {
		return tag;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setRotation(float r) {
		rotation = r;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setFrame(int f) {
		frame = f;
	}
	
	public void setGui(boolean g) {
		gui = g;
	}
	
	public void setColor(float r, float g, float b) {
		red = r;
		green = g;
		blue = b;
	}
	
	public void setColor(float r, float g, float b, float a) {
		red = r;
		green = g;
		blue = b;
		alpha = a;
	}
	
	public int getType() {
		return type;
	}
	
	public void setOnScreen(boolean b) {
		onScreen = b;
	}
	
	public boolean getOnScreen() {
		return onScreen;
	}
}
