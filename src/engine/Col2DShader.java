package engine;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

public class Col2DShader extends Shader {

	private int colorLoc;
	
	protected Col2DShader() {
		super("shaders/col2d.vs", "shaders/col2d.fs");
		
	}
	
	protected void getUniforms() {
		colorLoc = glGetUniformLocation(program, "color");
	}

	@Override
	protected void subRoutine(float[] p) {
		glUniform4f(colorLoc, p[0], p[1], p[2], p[3]);
	}
	
}
