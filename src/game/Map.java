package game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import starBreaker.index.StarIndex;

public class Map {
	
	private Block[][] tiles;
	private Block edgeTile;
	
	private int width;
	private int height;
	private GameObject[] entities;
	private int[][] positions;
	private int[][] data;
	
	public Map(Block edge) {
		edgeTile = edge;
		
		tiles = new Block[][] {{}};
		entities = new GameObject[] {};
	}
	
	public Map(int[][][] array, Block[] bReference, GameObject[] eReference, int edge) {
		
		edgeTile = bReference[edge];
		
		width = array.length;
		height = array[0].length;
		ArrayList<GameObject> eList = new ArrayList<GameObject>();
		ArrayList<int[]> pList = new ArrayList<int[]>();
		ArrayList<int[]> dList = new ArrayList<int[]>();
		tiles = new Block[width][height];
		
		for(int i = 0; i < width; ++i) {
			for(int j = 0; j < height; ++j) {
				tiles[i][j] = bReference[array[i][j][0]].create();
				if(array[i][j][1] != -1) {
					GameObject temp = eReference[array[i][j][1]];
					eList.add(temp);
					pList.add(new int[] {i,j});
					dList.add(new int[] {array[i][j][2]});
				}
			}
		}
		
		entities = new GameObject[] {};
		entities = eList.toArray(entities);
		positions = new int[][] {};
		positions = pList.toArray(positions);
		data = new int[][] {};
		data = dList.toArray(data);
	}
	
	public void update(int u, int r, int d, int l, Variable[] fr) {
		
		for(int i = l; i < r; ++i) {
			for(int j = u; j < d; ++j) {
				access(i, j).update(fr);
			}
		}
		
	}
	
	public Block access(int x, int y) {
		try {
			return tiles[x][y];
		}catch(Exception ex) {
			return edgeTile;
		}
	}
	
	public void setBlock(Block b, int x, int y) {
		tiles[x][y] = b;
	}
	
	public GameObject[] getEntities() {
		return entities;
	}
	
	public int[][] getPositions() {
		return positions;
	}
	
	public int[][] getData(){
		return data;
	}
	
	public void render(int u, int r, int d, int l, Variable[] fr) {
		
			for(int i = l; i < r; ++i) {
				for(int j = u; j < d; ++j) {
					Block b = access(i, j);
					int v = b.getValue();
					float red = fr[StarIndex.VAR_PLANET_COLORS+v*3].getFloat();
					float green = fr[StarIndex.VAR_PLANET_COLORS+v*3+1].getFloat();
					float blue = fr[StarIndex.VAR_PLANET_COLORS+v*3+2].getFloat();
					b.render(i, j, Scene.Z_LAYER_MAP, red, green, blue);
				}
			}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getEntityCount(int type) {
		int count = 0;
		for(GameObject e : entities) {
			if(	e.getType() == type) {
				++count;
			}
		}
		return count;
	}
}
