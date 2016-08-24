package ca.maceman.mapgenerator.commons.manager;

import java.awt.Point;
import java.awt.geom.Line2D;

import ca.maceman.mapgenerator.commons.constants.TileTypes;
import ca.maceman.mapgenerator.commons.model.Tile;
import ca.maceman.mapgenerator.commons.model.TileMap;
import ca.maceman.mapgenerator.commons.model.TileType;

public class TileManager {
	public boolean borderTileCheck(Tile tile) {
		return tile.getXPosition() != 0
				&& tile.getYPosition() != 0
				&& tile.getXPosition() < tile.getParentTileMap().getWidth() - 1
				&& tile.getYPosition() < tile.getParentTileMap().getHeight() - 1;

	}

	public void addTileToTileMap(TileMap tileMap, Tile tile) throws Exception {

		if (tileMap.getTiles() == null) {
			if (tileMap.getWidth() != 0 && tileMap.getHeight() != 0) {
				tileMap.setTiles(new Tile[tileMap.getWidth()][tileMap.getHeight()]);
			} else {
				throw new Exception("Could not add Tile to TileMap. TileMap:" + tileMap.toString());
			}
		}

		if (tileMap.getTiles()[tile.getXPosition()][tile.getYPosition()] == null) {

		}
	}

	public void plotPath(TileMap tileMap, Tile sourceTile, Tile destinationTile) {

		Tile[] surroundingTiles = null;

		int maxTileColumn = tileMap.getWidth() - 1;
		int maxTileRow = tileMap.getHeight() - 1;

		if (borderTileCheck(sourceTile)) {

			float smallestDepth = 10000; // smallest depth so far

			for (Tile currentTile : surroundingTiles) {
				if (currentTile.getType().getId() != TileTypes.RIVER_SOURCE.getId()
						&& currentTile.getType().getId() != TileTypes.RIVER.getId()) {
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
	 * 
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
		float heuristic = 0;

		for (Tile currentTile : surroundingTiles) {

			/* if 0, point is on line */
			if (Line2D.ptSegDist(
					sourceTile.getXPosition(),
					sourceTile.getYPosition(),
					destinationTile.getXPosition(),
					destinationTile.getYPosition(),
					currentTile.getXPosition(),
					currentTile.getYPosition()
					) <= 0.5) {
				estimatedCostToMove += currentTile.getDepth();
			}
		}

		costToMove = sourceTile.getDepth() * 2;
		bestCostToMove = costToMove + estimatedCostToMove;

		return bestCostToMove;

	}

	public Tile findClosestOfType(TileMap tileMap, Tile sourceTile, TileType type) throws Exception {

		float lastDistance = -1;
		float currentDistance = 0;
		Tile closestTile = null;

		for (Tile[] tileRow : tileMap.getTiles()) {
			for (Tile currentTile : tileRow) {

				currentDistance = getDistanceBetweenTwoTiles(sourceTile, currentTile);

				if (currentDistance < lastDistance || lastDistance == -1) {
					lastDistance = currentDistance;
					closestTile = currentTile;
				}
			}
		}

		if (closestTile == null) {
			throw new Exception("Could not find a tile of type '" + type.getName() + "'");
		}

		return closestTile;
	}

	public float getDistanceBetweenTwoTiles(Tile sourceTile, Tile destinationTile) {
		float distance = (float) Math.sqrt(Math.pow((sourceTile.getXPosition() - destinationTile.getXPosition()), 2)
						+ Math.pow((sourceTile.getYPosition() - destinationTile.getYPosition()), 2));

		return distance;
	}

	public Tile findLowestTile(Tile[][] tiles, int x, int y) {
		return null;
	}
}
