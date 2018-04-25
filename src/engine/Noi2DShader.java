package engine;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform2f;

public class Noi2DShader extends Shader {
	
	private int passColorLoc;
	private int inPosLoc;
	
	protected Noi2DShader() {
		super("shaders/noi2d.vs", "shaders/noi2d.fs");
	}
	
	protected void getUniforms() {
		passColorLoc = glGetUniformLocation(program, "passColor");
		inPosLoc = glGetUniformLocation(program, "inPos");
	}

	@Override
	protected void subRoutine(float[] p) {
		glUniform4f(passColorLoc, p[0], p[1], p[2], p[3]);
		glUniform2f(inPosLoc, p[4], p[5]);
	}
}
