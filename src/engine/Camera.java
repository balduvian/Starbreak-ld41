package engine;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Camera {
	
	private float width, height;
	private Vector3f position;
	private float rotation;
	private Matrix4f projection, projView;
	
	public Camera(float w, float h) {
		position = new Vector3f();
		rotation = 0;
		projView = new Matrix4f();
		setDims(w, h);
	}
	
	public void setDims(float w, float h) {
		width = w;
		height = h;
		float halfW = width/2f;
		float halfH = height/2f;
		projection = new Matrix4f().setOrtho(-halfW,halfW,halfH,-halfH,1,-1);
	}
	
	public void update() {
		projection.translate(position.negate(new Vector3f()), projView);
		projView.rotateZ(-rotation);
	}
	
	public Matrix4f getParallaxView(float p) {
		projection.translate(position.mul(p).negate(new Vector3f()), projView);
		projView.rotateZ(-rotation);
		return new Matrix4f(projView);
	}
	
	public Matrix4f getProjview() {
		return new Matrix4f(projView);
	}
	
	public Matrix4f getProjection() {
		return new Matrix4f(projection);
	}
	
	public void move(float x, float y, float z) {
		position.add(x, y, z);
	}
	
	public void set(float x, float y, float z) {
		position.set(x, y, z);
	}
	
	public void setPosition(Vector2f v) {
		position.set(v, 0);
	}
	
	public void setPosition(float x, float y) {
		position.set(x, y, 0);
	}
	
	public void set(Vector3f p) {
		position.set(p);
	}
	
	public void setRotation(float r) {
		rotation = r;
	}
	
	public void rotate(float r) {
		rotation += r;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setX(float x) {
		position.set(x, position.y, position.z);
	}
	
	public void setY(float y) {
		position.set(position.x, y, position.z);
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public float getRotation() {
		return rotation;
	}
}
