package ca.maceman.mapgenerator.commons.manager;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import ca.maceman.mapgenerator.commons.constants.TileTypes;
import ca.maceman.mapgenerator.commons.model.Tile;
import ca.maceman.mapgenerator.commons.model.TileMap;
import ca.maceman.mapgenerator.commons.model.TileType;

/**
 * Holds various methods for managing {@link Tile}s
 * 
 * @author Macemanca
 * 
 */
public class TileManager {

	/**
	 * Verifies if a {@link Tile} is bordering its {@link TileMap}
	 * 
	 * @param tile
	 * @return
	 */
	public boolean borderTileCheck(Tile tile) {
		return (tile.getXPosition() == 0 || tile.getYPosition() == 0 || tile.getXPosition() > tile.getParentTileMap().getWidth() - 1 || tile.getYPosition() < tile.getParentTileMap().getHeight() - 1);

	}

	/**
	 * Verifies if a {@link Tile} is bordering its {@link TileMap}
	 * 
	 * @param tile
	 * @return
	 */
	public boolean outOfBorderCheck(TileMap tileMap, int row, int column) {
		return row >= 0 && column >= 0 && row <= tileMap.getWidth() - 1 && column <= tileMap.getHeight() - 1;
	}

	/**
	 * Adds a {@link Tile} to a {@link TileMap}
	 * 
	 * @param tileMap
	 * @param tile
	 * @throws Exception
	 */
	public void addTileToTileMap(TileMap tileMap, Tile tile) throws Exception {

		if (tileMap.getTiles() == null) {
			if (tileMap.getWidth() != 0 && tileMap.getHeight() != 0) {
				tileMap.setTiles(new Tile[tileMap.getWidth()][tileMap.getHeight()]);
			} else {
				throw new Exception("Could not add Tile to TileMap. TileMap:" + tileMap.toString());
			}
		}

		tile.setParentTileMap(tileMap);
		tileMap.getTiles()[tile.getXPosition()][tile.getYPosition()] = tile;
	}

	/**
	 * General Pathing method. Can be used for roads and rivers. TODO
	 * 
	 * @param tileMap
	 * @param sourceTile
	 * @param destinationTile
	 */
	public void plotPath(TileMap tileMap, Tile sourceTile, Tile destinationTile) {

		Tile[] surroundingTiles = null;

		int maxTileColumn = tileMap.getWidth() - 1;
		int maxTileRow = tileMap.getHeight() - 1;

		if (borderTileCheck(sourceTile)) {

			float smallestDepth = 10000; // smallest depth so far

			for (Tile currentTile : surroundingTiles) {
				if (currentTile.getType().getId() != TileTypes.RIVER_SOURCE.getId() && currentTile.getType().getId() != TileTypes.RIVER.getId()) {
					if (getDifficulty(sourceTile, currentTile, surroundingTiles) < smallestDepth) {
						smallestDepth = getDifficulty(sourceTile, currentTile, surroundingTiles);
						sourceTile = currentTile;
					}
				}

			}
		}
		if (sourceTile.getType().getId() != TileTypes.SHALLOW_WATER.getId()) {
			sourceTile.setType(TileTypes.RIVER);
			plotPath(tileMap, sourceTile, destinationTile);
		}
	}

	/**
	 * Finds the best route that an ongoing path should take to get to it's
	 * destination.
	 * 
	 * Looks at the 8 surrounding tiles to find which would be easiest to pass
	 * through to get to its destination. This is done using {@link Line2D}.
	 * 
	 * @param sourceTile
	 * @param destinationTile
	 * @param surroundingTiles
	 * @return
	 */
	public float getDifficulty(Tile sourceTile, Tile destinationTile, Tile[] surroundingTiles) {
		float costToMove = 0;
		float estimatedCostToMove = 0;
		float bestCostToMove = 0;

		for (Tile currentTile : surroundingTiles) {

			/* if 0, point is on line */
			if (Line2D.ptSegDist(sourceTile.getXPosition(), sourceTile.getYPosition(), destinationTile.getXPosition(), destinationTile.getYPosition(), currentTile.getXPosition(), currentTile.getYPosition()) <= 0.5) {
				estimatedCostToMove += currentTile.getDepth();
			}
		}

		costToMove = sourceTile.getDepth() * 2;
		bestCostToMove = costToMove + estimatedCostToMove;

		return bestCostToMove;

	}

	/**
	 * Finds the closest tile of a certain type relative to a given tile's
	 * position.
	 * 
	 * @param tileMap
	 * @param sourceTile
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Tile findClosestOfType(TileMap tileMap, Tile sourceTile, TileType type) throws Exception {

		float lastDistance = -1;
		float currentDistance = 0;
		Tile closestTile = null;

		for (Tile[] tileRow : tileMap.getTiles()) {
			for (Tile currentTile : tileRow) {

				if (currentTile.getType().getId() == type.getId()) {
					currentDistance = getDistanceBetweenTwoTiles(sourceTile, currentTile);

					if (currentDistance < lastDistance || lastDistance == -1) {
						lastDistance = currentDistance;
						closestTile = currentTile;
					}
				}
			}
		}

		if (closestTile == null) {
			throw new Exception("Could not find a tile of type '" + type.getName() + "'");
		}

		return closestTile;
	}

	/**
	 * Finds the distance between two points.
	 * 
	 * @param sourceTile
	 * @param destinationTile
	 * @return
	 */
	public float getDistanceBetweenTwoTiles(Tile sourceTile, Tile destinationTile) {
		float distance = (float) Math.sqrt(Math.pow((sourceTile.getXPosition() - destinationTile.getXPosition()), 2) + Math.pow((sourceTile.getYPosition() - destinationTile.getYPosition()), 2));

		return distance;
	}

	/**
	 * Finds the lowest tile in a given 2d array of tiles. TODO
	 * 
	 * @param tiles
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile findLowestTile(Tile[][] tiles, int x, int y) {
		return null;
	}

	/**
	 * Determines the tiles type based on its depth
	 * 
	 * @param tile
	 * @return
	 */
	public TileType determineTileType(Tile tile) {
		TileType type = null;

		if (tile.getDepth() < 0.1) {
			type = TileTypes.BLACK_WATER;
		} else if ((tile.getDepth() >= 0.1) && (tile.getDepth() < 0.2)) {
			type = TileTypes.DEEP_WATER;
		} else if ((tile.getDepth() >= 0.2) && (tile.getDepth() < 0.3)) {
			type = TileTypes.WATER;
		} else if ((tile.getDepth() >= 0.3) && (tile.getDepth() < 0.4)) {
			type = TileTypes.SHALLOW_WATER;
		} else if ((tile.getDepth() >= 0.4) && (tile.getDepth() < 0.45)) {
			type = TileTypes.SAND;
		} else if ((tile.getDepth() >= 0.45) && (tile.getDepth() < 0.6)) {
			type = TileTypes.GRASS;
		} else if ((tile.getDepth() >= 0.6) && (tile.getDepth() < 0.7)) {
			type = TileTypes.FOREST;
		} else if ((tile.getDepth() >= 0.7) && (tile.getDepth() < 0.8)) {
			type = TileTypes.BASE_MOUNTAIN;
		} else if ((tile.getDepth() >= 0.8) && (tile.getDepth() < 0.9)) {
			type = TileTypes.MOUNTAIN;
		} else if (tile.getDepth() >= 0.9) {
			type = TileTypes.SNOW;
		} else {
			type = TileTypes.EMPTY;
		}

		return type;
	}

	public Tile[] getSurroundingTiles(TileMap tileMap, Tile sourceTile, int range) {

		int currentXpos = 0;
		int currentYpos = 0;
		int numberOfTiles = (range + 1) * 4;
		int minRange = 0 - range;

		ArrayList<Tile> surroundingTiles = new ArrayList<Tile>();

		for (int x = minRange; x <= range; x++) {
			for (int y = minRange; y <= range; y++) {
				currentXpos = sourceTile.getXPosition() - x;
				currentYpos = sourceTile.getYPosition() - y;
				if (x == range
						|| x == minRange
						|| y == range
						|| y == minRange) {
					if (outOfBorderCheck(tileMap, currentXpos, currentYpos)) {
						surroundingTiles.add(tileMap.getTile(currentXpos, currentYpos));
					}
				}
			}
		}

		Tile[] surroundingTilesArr =  surroundingTiles.toArray(new Tile[surroundingTiles.size()]);

		return surroundingTilesArr;
	}
}
