package engine;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.joml.Matrix4f;
import org.joml.Vector4f;

abstract public class Shader {
	
	public static Gam2DShader GAM2DSHADER;
	public static Col2DShader COL2DSHADER;
	public static Gra2DShader GRA2DSHADER;
	public static Cir2DShader CIR2DSHADER;
	public static Noi2DShader NOI2DSHADER;
	public static Rai2DShader RAI2DSHADER;
	
	protected int program;
	
	protected int mvpLoc;
	
	public static void init() {
		COL2DSHADER = new Col2DShader();
		GAM2DSHADER = new Gam2DShader();
		GRA2DSHADER = new Gra2DShader();
		CIR2DSHADER = new Cir2DShader();
		NOI2DSHADER = new Noi2DShader();
		RAI2DSHADER = new Rai2DShader();
	}
	
	protected Shader(String vertPath, String fragPath) {
		program = glCreateProgram();
		int vert = loadShader(vertPath, GL_VERTEX_SHADER);
		int frag = loadShader(fragPath, GL_FRAGMENT_SHADER);
		glAttachShader(program, vert);
		glAttachShader(program, frag);
		glLinkProgram(program);
		glDetachShader(program, vert);
		glDetachShader(program, frag);
		glDeleteShader(vert);
		glDeleteShader(frag);
		
		mvpLoc = glGetUniformLocation(program, "mvp");
		
		getUniforms();
	}
	
	private int loadShader(String path, int type) {
		StringBuilder build = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResource(path).openStream()));
			String line;
			while((line = br.readLine()) != null) {
				build.append(line);
				build.append('\n');
			}
			br.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		String src = build.toString();
		int shader = glCreateShader(type);
		glShaderSource(shader, src);
		
		glCompileShader(shader);
		if(glGetShaderi(shader,GL_COMPILE_STATUS) != 1) {
			throw new RuntimeException("Failed to compile shader | "+path+" | "+type+" | "+glGetShaderInfoLog(shader));
		}
		return shader;
	}
	
	protected abstract void getUniforms();
	
	//set mvp... does something
	public void setMvp(Matrix4f m) {
		glUniformMatrix4fv(mvpLoc, false, m.get(new float[16]));
	}
	
	//render with passing information
	public void enable(float params[]) {
		glUseProgram(program);
		subRoutine(params);
	}
	
	//this is what each shader uses to pass information into the shader
	abstract protected void subRoutine(float params[]);
	
	//run the shader without passing anything or calling the sub routine
	public void enable() {
		glUseProgram(program);
	}
	
	public void disable() {
		glUseProgram(0);
	}
	
	public void destroy() {
		glDeleteProgram(program);
	}
}
