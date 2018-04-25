package game;

public class NullScript extends Script{

	@Override
	public Script clone() {
		return new NullScript();
	}

	@Override
	public void start(float[] params) {
	}

	@Override
	public void update() {
	}

}
