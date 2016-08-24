package ca.maceman.mapgenerator.commons.model;

public class Tile {

	private float depth;
	private int x;
	private int y;
	private int width;
	private int height;
	private TileType tileType;

	public Tile(float depth, int x, int y, int width, int height, TileType tileType) {
		super();
		this.depth = depth;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tileType = tileType;
	}

	public Tile(float depth, int x, int y) {
		super();
		this.depth = depth;
		this.x = x;
		this.y = y;
		width = 1;
		height = 1;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public TileType getType() {
		return tileType;
	}

	public void setType(TileType tileType) {
		this.tileType = tileType;
	}

}
