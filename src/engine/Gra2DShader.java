package engine;

public class Gra2DShader extends Shader {
	
	protected Gra2DShader() {
		super("shaders/gra2d.vs", "shaders/col2d.fs");
	}
	
	protected void getUniforms() {
	}

	@Override
	protected void subRoutine(float[] params) {

	}
}
