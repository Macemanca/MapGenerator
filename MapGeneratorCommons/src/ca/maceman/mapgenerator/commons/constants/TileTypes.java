package ca.maceman.mapgenerator.commons.constants;

import ca.maceman.mapgenerator.commons.model.TileType;

/**
 * Constants class for the different TileTypes.
 * @author Macemanca
 *
 */
public class TileTypes {
	public static TileType EMPTY = new TileType(0, "EMPTY");
	public static TileType BLACK_WATER = new TileType(1, "BLACK_WATER");
	public static TileType DEEP_WATER = new TileType(2, "DEEP_WATER");
	public static TileType WATER = new TileType(3, "WATER");
	public static TileType SHALLOW_WATER = new TileType(4, "SHALLOW_WATER");
	public static TileType SAND = new TileType(5, "SAND");
	public static TileType GRASS = new TileType(6, "GRASS");
	public static TileType FOREST = new TileType(7, "FOREST");
	public static TileType BASE_MOUNTAIN = new TileType(8, "BASE_MOUNTAIN");
	public static TileType MOUNTAIN = new TileType(9, "MOUNTAIN");
	public static TileType SNOW = new TileType(10, "SNOW");
	public static TileType RIVER_SOURCE = new TileType(11, "RIVER_SOURCE");
	public static TileType RIVER = new TileType(12, "RIVER");
	public static TileType LAKE_SOURCE = new TileType(13, "LAKE_SOURCE");
	public static TileType LAKE = new TileType(14, "LAKE");
}
