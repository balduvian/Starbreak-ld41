package engine;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform1f;

import org.joml.Vector4f;

import game.Game;

public class Rai2DShader extends Shader {
	
	private int enableLoc;
	private int texLoc;
	private int colorLoc;
	
	private double timer;//used internally
	
	protected void subRoutine(float[] p) {
		glUniform4f(texLoc, p[0], p[1], p[2], p[3]);
		glUniform1f(enableLoc, p[8]);
		
		timer += Game.time/4;
		timer %= Math.PI*2;
		
		glUniform1f(colorLoc, (float)timer);
	}
	
	protected Rai2DShader() {
		super("shaders/rai2d.vs", "shaders/rai2d.fs");
		
	}
	
	protected void getUniforms() {
		colorLoc = glGetUniformLocation(program, "gradient");
		enableLoc = glGetUniformLocation(program, "enable");
		texLoc = glGetUniformLocation(program, "frame");
	}
	
}
