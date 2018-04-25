package engine;
import org.joml.Vector3f;

abstract public class Rect {
	
	protected Camera camera;
	
	protected static VAO vao;
	
	protected Vector3f position;
	
	protected float width, height, rotation;
	
	protected Rect(Camera c) {
		position = new Vector3f();
		rotation = 0;
		width = 1;
		height = 1;
		camera = c;
		init();
	}
	
	private void init() {
		if(vao==null) {
			float vertices[] = {
		             0.5f, -0.5f, 0,
		             0.5f,  0.5f, 0,
		             -0.5f,  0.5f, 0,
		             -0.5f, -0.5f, 0
		        };
		        int indices[] = {
		            0, 1, 3,
		            1, 2, 3
		        };
	        float[] texCoords = {
	        		1, 0,
	                1, 1,
	                0, 1,
	                0, 0
	        };
		        
			vao = new VAO(vertices, indices);
			vao.addAttrib(texCoords, 2);
		}
	}
	
	public void translate(float x, float y, float z) {
		position.add(x, y, z);
	}
	
	public void setPosition(float x, float y, float z) {
		position.set(x, y, z);
	}
	
	public void setPosition(float x, float y) {
		position.set(x, y, 0);
	}
	
	public void setPosition(Vector3f p) {
		position.set(p);
	}
	
	public void setRotation(float r) {
		rotation = r;
	}
	
	public void rotate(float r) {
		rotation += r;
	}
	
	public void setDims(float w, float h) {
		width = w;
		height = h;
	}
	
	public void setWidth(float w) {
		width = w;
	}
	
	public void setHeight(float h) {
		height = h;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getWidth() {
		return width;
	}
	
}
