package ca.maceman.mapgenerator.commons.generator.impl;

import java.awt.Point;
import java.util.Random;

import ca.maceman.mapgenerator.commons.constants.TileTypes;
import ca.maceman.mapgenerator.commons.generator.IMapGenerator;
import ca.maceman.mapgenerator.commons.manager.TileManager;
import ca.maceman.mapgenerator.commons.model.Tile;
import ca.maceman.mapgenerator.commons.model.TileMap;

/**
 * Simple implementation of the {@link IMapGenerator}.
 * 
 * @author Macemanca
 * 
 */
public class SimpleMapGenerator implements IMapGenerator {

	TileMap tileMap = null;
	Random random = new Random();
	TileManager tileManager = null;

	/**
	 * {@inheritDoc}
	 */
	public TileMap GenerateTerrainMap(int width, int height, int octave, int octaveCount, boolean isIsland) throws Exception {

		int seed = random.nextInt();

		return GenerateTerrainMap(width, height, octave, octaveCount, isIsland, seed);
	}

	/**
	 * {@inheritDoc}
	 */
	public TileMap GenerateTerrainMap(int width, int height, int octave, int octaveCount, boolean isIsland, int seed) throws Exception {

		tileMap = new TileMap();
		tileManager = new TileManager();
		Tile tile = new Tile(0, 0, 0, null);

		tileMap.setHeight(height);
		tileMap.setWidth(width);

		random = new Random(seed);

		SimpleNoiseGenerator noiseGenerator = new SimpleNoiseGenerator(seed);

		float[][] depthMap = null;

		if (isIsland) {
			depthMap = noiseGenerator.GeneratePerlinNoise(noiseGenerator.GenerateSmoothNoise(noiseGenerator.GenerateRadialWhiteNoise(width, height, height / 10), octave), octaveCount);
		} else {
			depthMap = noiseGenerator.GeneratePerlinNoise(noiseGenerator.GenerateSmoothNoise(noiseGenerator.GenerateWhiteNoise(width, height, 0), octave), octaveCount);
		}

		if (depthMap != null) {
			for (int mapX = 0; mapX < width; mapX++) {
				for (int mapY = 0; mapY < height; mapY++) {
					tile = new Tile(depthMap[mapX][mapY], mapX, mapY, tileMap);

					tile.setType(tileManager.determineTileType(tile));
					tileManager.addTileToTileMap(tileMap, tile);
				}
			}
		} else {
			throw new Exception("depthMap is empty.");
		}

		addRivers(tileMap);

		return tileMap;
	}

	/**
	 * Adds rivers to the tilemap.
	 * 
	 * Places a river source {@link Tile} and finds a path of least resistance
	 * to the closest SHALLOW_WATER {@link Tile}
	 * 
	 * @throws Exception
	 */
	private void addRivers(TileMap tileMap) throws Exception {
		/* TODO: Implement as runnables */
		/* TODO: generalize pathing method */

		int dieRoll = 0;
		Tile destinationTile = null;

		for (Tile[] tileRow : tileMap.getTiles()) {
			for (Tile sourceTile : tileRow) {
				if (sourceTile.getType().getId() == TileTypes.MOUNTAIN.getId()) {

					/* Generate river Sources */
					dieRoll = random.nextInt(100);

					if (dieRoll >= 98) {
						sourceTile.setType(TileTypes.RIVER_SOURCE);
						destinationTile = tileManager.findClosestOfType(tileMap, sourceTile, TileTypes.SHALLOW_WATER);
						PlotRiver(tileMap, sourceTile, destinationTile);
					}
				}
			}
		}
	}

	/**
	 * Using recursion we make a path to the closest sea-level tile by using the
	 * path of least resistance; using the depth of the tile as the weight of
	 * said resistance.
	 * 
	 * @return
	 */
	private void PlotRiver(TileMap tileMap, Tile sourceTile, Tile destinationTile) {

		Tile[] surroundingTiles = null;
		//
		//		int maxTileColumn = tileMap.getWidth() - 1;
		//		int maxTileRow = tileMap.getHeight() - 1;

		float smallestDepth = 10000; // smallest depth so far
		float currentDepth = 0; // smallest depth so far

		surroundingTiles = tileManager.getSurroundingTiles(tileMap, sourceTile, 1);
		Tile easiestTile = null;
		for (Tile currentTile : surroundingTiles) {
			if (currentTile.getType().getId() != TileTypes.RIVER_SOURCE.getId() && currentTile.getType().getId() != TileTypes.RIVER.getId()) {
				currentDepth = tileManager.getDifficulty(sourceTile, currentTile, surroundingTiles);
				if (currentDepth < smallestDepth) {
					smallestDepth = currentDepth;
					easiestTile = currentTile;
				}
			}

		}

		sourceTile = easiestTile;
		if (sourceTile != null && sourceTile.getType().getId() != TileTypes.SHALLOW_WATER.getId()) {
			sourceTile.setType(TileTypes.RIVER);
			PlotRiver(tileMap, sourceTile, destinationTile);
		}
	}

	private void AddRoads(Tile[][] tiles, Random r) {

	}

	/**
	 * using recursion we make a path to water by using the path of least
	 * resistance; using the depth of the tile as the weight of said resistance.
	 * 
	 * @param tiles
	 * @param i
	 * @param j
	 * @param closestTile
	 */
	private void PlotRoads(Tile[][] tiles, int i, int j, Point closestTile) {

	}

}
