package game;

import org.joml.Vector4f;

import engine.TexRect;

public class Font {
	
	private TexRect sheet;
	
	private float charWidth;
	private float charHeight;
	
	public void render(float x, float y, char[] s, boolean ce, boolean r) {
		sheet.setDims(charWidth, charHeight);
		if(ce) {
			x -= (s.length*charWidth)/2f-charWidth/2;
		}
		int l = s.length;
		
		float enable = (r) ? 1 : 0;
		
		int line = 0;
		
		for(int i = 0; i < l; ++i) {
			char c = s[i];
			if(c == '\n') {
				++line;
			}else {
				int cx = c%16;
				int cy = c/16;
				sheet.setPosition(x+charWidth*i, y+charHeight*line);
				Vector4f coords = sheet.getTexture().getFrame(cx, cy);
				sheet.render(new float[] {coords.x, coords.y, coords.z, coords.w, 1, 1, 1, 1, enable});
			}
		}
	}
	
	public void guiRender(float x, float y, char[] s, boolean ce, boolean r) {
		sheet.setDims(charWidth, charHeight);
		if(ce) {
			x -= (s.length*charWidth)/2f-charWidth/2;
		}
		int l = s.length;
		
		float enable = (r) ? 1 : 0;
		
		for(int i = 0; i < l; ++i) {
			char c = s[i];
			int cx = c%16;
			int cy = c/16;
			//System.out.println("cx: "+cx+" | cy:"+cy);
			sheet.setPosition(x+charWidth*i, y);
			Vector4f coords = sheet.getTexture().getFrame(cx, cy);
			sheet.guiRender(new float[] {coords.x, coords.y, coords.z, coords.w, 1, 1, 1, 1, enable});
		}
	}
	
	public Font(float cw, float ch, TexRect t) {
		sheet = t;
		charWidth = cw;
		charHeight = ch;
	}
}
