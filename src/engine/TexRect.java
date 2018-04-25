package engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class TexRect extends Rect{
	
	private Shader shader;
	private Texture texture;
	private static int[] widths = new int[]{
		9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
		9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
		6, 2, 4, 9, 9, 9, 9, 2, 3, 3, 6, 6, 3, 5, 2, 5,
		6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 6, 6, 6, 6,
		8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
		9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 3, 5, 3, 4, 9,
		3, 7, 6, 6, 6, 6, 6, 6, 6, 2, 4, 6, 2, 7, 6, 6,
		6, 6, 5, 6, 6, 6, 6, 7, 6, 6, 6, 4, 2, 4, 6, 6,
	};
	
	public Texture getTexture() {
		return texture;
	}
	
	public TexRect(Camera c, Texture t, Shader s) {
		super(c);
		texture = t;
		shader = s;
	}
	
	
	public void render(float[] params, float parallax) {
		Matrix4f transform = new Matrix4f().translate(position).rotateZ(rotation).scale(width,height,1);
		renderRoutine(params, camera.getProjview().mul(transform, new Matrix4f()));
	}
	
	public void render(float[] params) {
		Matrix4f transform = new Matrix4f().translate(position).rotateZ(rotation).scale(width,height,1);
		renderRoutine(params, camera.getProjview().mul(transform, new Matrix4f()));
	}
	
	public void render() {
		Matrix4f transform = new Matrix4f().translate(position).rotateZ(rotation).scale(width,height,1);
		renderRoutine(camera.getProjview().mul(transform, new Matrix4f()));
	}
	
	public void guiRender(float[] params) {
		renderRoutine(params, camera.getProjection().translate(position).rotateZ(rotation).scale(width,height,1));
	}
	
	public void guiRender() { 
		renderRoutine(camera.getProjection().translate(position.add(width/2,height/2,0,new Vector3f())).rotateZ(rotation).scale(width,height,1).translate(-0.5f,-0.5f,0));
	}
	
	private void renderRoutine(float[] params, Matrix4f transform) {
		texture.bind();
		shader.enable(params);
		shader.setMvp(transform);
		vao.render();
		shader.disable();
		texture.unbind();
	}
	
	private void renderRoutine(Matrix4f transform) {
		texture.bind();
		shader.enable();
		shader.setMvp(transform);
		vao.render();
		shader.disable();
		texture.unbind();
	}
	
	public float renderText(String text, float x, float y, float size, boolean centered, float r, float g, float b, float a) {
		char[] map = text.toCharArray();
		float offset = 0;
		if(centered) {
			for(char c : map) {
				offset -= (widths[c]/8f*size)/2;
				offset += ((8-widths[c])/8f*size)/map.length;
			}
			offset += (1/8f*size);
		}
		setDims(size, size);
		setPosition(x+offset, y);
		for(char c : map){
			int locX = c%16;
			int locY = c/16;
			Vector4f v = texture.getFrame(locX, locY);
			render(new float[] {v.x,v.y,v.z,v.w, r, g, b, a});
			offset += (widths[c]/8f*size);
			setPosition(x+offset, y);
		}
		return offset;
	}
}
