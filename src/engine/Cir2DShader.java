package engine;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.*;

public class Cir2DShader extends Shader {
	
	private int sizeLoc;
	private int passColorLoc;
	
	protected Cir2DShader() {
		super("shaders/cir2d.vs", "shaders/cir2d.fs");
	}
	
	protected void getUniforms() {
		passColorLoc = glGetUniformLocation(program, "passColor");
		sizeLoc = glGetUniformLocation(program, "size");
	}

	@Override
	protected void subRoutine(float[] p) {
		glUniform4f(passColorLoc, p[0], p[1], p[2], p[3]);
		glUniform1f(sizeLoc, p[4]);
	}
}
