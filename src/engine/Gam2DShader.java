package engine;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

import org.joml.Vector4f;

public class Gam2DShader extends Shader {
	
	private int texLoc;
	private int colorLoc;
	
	protected void subRoutine(float[] p) {
		glUniform4f(texLoc, p[0], p[1], p[2], p[3]);
		glUniform4f(colorLoc, p[4], p[5], p[6], p[7]);
	}
	
	protected Gam2DShader() {
		super("shaders/gam2d.vs", "shaders/gam2d.fs");
		
	}
	
	protected void getUniforms() {
		texLoc = glGetUniformLocation(program, "frame");
		colorLoc = glGetUniformLocation(program, "inColor");
	}
	
}
