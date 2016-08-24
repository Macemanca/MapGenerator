package ca.maceman.mapgenerator.commons.model;

/**
 * A tile to be used in the {@link TileMap}
 * 
 * Has a position, a size, a depth and a {@link TileType}
 * @author masa015
 *
 */
public class Tile {

	private float depth;
	private int xPosition;
	private int yPosition;
	private int width;
	private int height;
	private TileType tileType;
	private TileMap parentTileMap;

	public Tile(float depth, int x, int y, int width, int height, TileType tileType, TileMap parentTileMap) {
		super();
		this.depth = depth;
		this.xPosition = x;
		this.yPosition = y;
		this.width = width;
		this.height = height;
		this.tileType = tileType;
		this.parentTileMap = parentTileMap;
	}

	public Tile(float depth, int x, int y) {
		super();
		this.depth = depth;
		this.xPosition = x;
		this.yPosition = y;
		width = 1;
		height = 1;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public int getXPosition() {
		return xPosition;
	}

	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
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

	public TileMap getParentTileMap() {
		return parentTileMap;
	}

	public void setParentTileMap(TileMap parentTileMap) {
		this.parentTileMap = parentTileMap;
	}

}
