package ca.maceman.mapgenerator.commons.model;

/**
 * The tilemap class holds a 2-dimensional array of tiles. It has a width and a
 * height.
 * 
 * @author Macemanca
 *
 */
public class TileMap {

	private int width = 0;
	private int height = 0;
	private Tile[][] tiles = null;

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

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

}
