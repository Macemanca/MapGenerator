package ca.maceman.mapgenerator.commons.generator.impl;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Random;

import ca.maceman.mapgenerator.commons.constants.TileTypes;
import ca.maceman.mapgenerator.commons.generator.IMapGenerator;
import ca.maceman.mapgenerator.commons.manager.TileManager;
import ca.maceman.mapgenerator.commons.model.Tile;
import ca.maceman.mapgenerator.commons.model.TileMap;
import ca.maceman.mapgenerator.commons.model.TileType;

public class SimpleMapGenerator implements IMapGenerator {

	TileMap tileMap = null;
	TileManager tileManager = null;
	Random random = new Random();

	public TileMap GenerateTerrainMap(int width, int height, int octave, int octaveCount) throws Exception {

		int seed = random.nextInt();

		return GenerateTerrainMap(width, height, octave, octaveCount, seed);
	}

	public TileMap GenerateTerrainMap(int width, int height, int octave, int octaveCount, int seed) throws Exception {

		tileMap = new TileMap();
		tileManager = new TileManager();

		tileMap.setHeight(height);
		tileMap.setWidth(width);

		random = new Random(seed);

		SimpleNoiseGenerator noiseGenerator = new SimpleNoiseGenerator(seed);

		float[][] depthMap = noiseGenerator.GeneratePerlinNoise(noiseGenerator.GenerateSmoothNoise(noiseGenerator.GenerateRadialWhiteNoise(width, height), octave), octaveCount);

		for (int mapX = 0; mapX < width; mapX++) {
			for (int mapY = 0; mapY < height; mapY++) {
				tileManager.addTileToTileMap(tileMap, new Tile(depthMap[mapX][mapY], mapX, mapY));
			}
		}

		addRivers(tileMap);
		
		return tileMap;
	}

	/**
	 * Adds rivers to the tilemap.
	 * 
	 * Places a river source {@link Tile} and finds a path of least resistance
	 * to the closest SHALLOW_WATER {@link Tile}
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

					if (dieRoll >= 90) {
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

		int maxTileColumn = tileMap.getWidth() - 1;
		int maxTileRow = tileMap.getHeight() - 1;

		if (tileManager.borderTileCheck(sourceTile)) {

			float smallestDepth = 10000; // smallest depth so far

			for (Tile currentTile : surroundingTiles) {
				if (currentTile.getType().getId() != TileTypes.RIVER_SOURCE.getId()
						&& currentTile.getType().getId() != TileTypes.RIVER.getId()) {
					if (tileManager.getDifficulty(sourceTile, currentTile, surroundingTiles) < smallestDepth) {
						smallestDepth = tileManager.getDifficulty(sourceTile, currentTile, surroundingTiles);
						sourceTile = currentTile;
					}
				}

			}
		}
		if (sourceTile.getType().getId() != TileTypes.SHALLOW_WATER.getId()) {
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
