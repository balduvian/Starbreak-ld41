package engine;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class GraRect extends Rect{

	private Gra2DShader shader = Shader.GRA2DSHADER;
	
	public GraRect(Camera c) {
		super(c);
	}
	
	public void render() {
		shader.enable();
		Matrix4f transform = new Matrix4f().translate(position.add(width/2,height/2,0,new Vector3f())).rotateZ(rotation).scale(width,height,1).translate(-1f,-1f,0);
		shader.setMvp(camera.getProjview().mul(transform, new Matrix4f()));
		vao.render();
		shader.disable();
	}

}