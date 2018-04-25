package game;

import org.joml.Vector4f;

import engine.TexRect;
import starBreaker.index.StarIndex;

public class Block {

	private float rotation;
	
	private boolean solid;
	private TexRect texture;
	
	private int value;
	
	private boolean destroyed;
	private boolean updated;
	private boolean corrupted;
	
	private double corruptTimer;
	private double cTime;
	
	private Block(Block b) {
		solid = b.solid;
		if(b.texture != null) {
			texture = b.texture;
		}
		cTime = 0.5;//OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOFFFFFFFFFFFFFFFFFFFFF
	}
	
	public boolean isUpdated() {
		return updated;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public boolean isCorrupted() {
		return corrupted;
	}
	
	public void corruptNotify() {
		corrupted = true;
		corruptTimer = cTime;
	}
	
	public void destroyNotify() {
		corrupted = false;
		destroyed = true;
	}
	
	public void updateNotify() {
		corrupted = false;
		updated = true;
	}
	
	public Block(boolean s, TexRect t) {
		solid = s;
		texture = t;
		if(texture != null) {
			Vector4f temp = t.getTexture().getFrame(0, 0);
		}
		corrupted = false;
	}
	
	public void render(float x, float y, float z, float r, float g, float b) {
		if(texture != null) {
			texture.setDims(1, 1);
			texture.setPosition(x, y, z);
			texture.setRotation(rotation);
			if(corrupted) {
				texture.render(new float[] {0.4f, 0.4f, 0.4f, 1, 0.25f});
			}else {
				texture.render(new float[] {r, g, b, 1, 0.25f});
			}
		}
	}
	
	public void update(Variable[] i) {
		destroyed = false;
		updated = false;
		rotation += i[StarIndex.VAR_PLANET_SPEEDS+value].getFloat()*Game.time;
		if(corrupted) {
			corruptTimer -= Game.time;
			if(corruptTimer <= 0) {
				corrupted = false;
			}
		}
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public Block create() {
		return new Block(this);
	}
	
	public void setValue(int v) {
		value = v;
	}
	
	public int getValue() {
		return value;
	}
}
