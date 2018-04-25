package engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class ColRect extends Rect{
	
	private Shader shader;
	
	public ColRect(Camera c, Shader s) {
		super(c);
		shader = s;
	}
	
	public void render(float[] params) {
		Matrix4f transform = new Matrix4f().translate(position.add(width/2,height/2,0,new Vector3f())).rotateZ(rotation).scale(width,height,1).translate(-1f,-1f,0);
		renderRoutine(params, camera.getProjview().mul(transform, new Matrix4f()));
	}
	
	public void render() {
		Matrix4f transform = new Matrix4f().translate(position.add(width/2,height/2,0,new Vector3f())).rotateZ(rotation).scale(width,height,1).translate(-1f,-1f,0);
		renderRoutine(camera.getProjview().mul(transform, new Matrix4f()));
	}
	
	public void guiRender(float[] params) {
		renderRoutine(params, camera.getProjection().translate(position.add(width/2,height/2,0,new Vector3f())).rotateZ(rotation).scale(width,height,1).translate(-0.5f,-0.5f,0));
	}
	
	public void guiRender() { 
		renderRoutine(camera.getProjection().translate(position.add(width/2,height/2,0,new Vector3f())).rotateZ(rotation).scale(width,height,1).translate(-0.5f,-0.5f,0));
	}
	
	private void renderRoutine(float[] params, Matrix4f transform) {
		shader.enable(params);
		shader.setMvp(transform);
		vao.render();
		shader.disable();
	}
	
	private void renderRoutine(Matrix4f transform) {
		shader.enable();
		shader.setMvp(transform);
		vao.render();
		shader.disable();
	}
	
}
