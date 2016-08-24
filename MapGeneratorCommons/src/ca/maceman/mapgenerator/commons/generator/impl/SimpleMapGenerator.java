package ca.maceman.mapgenerator.commons.generator.impl;

import java.awt.Point;
import java.util.Random;

import ca.maceman.mapgenerator.commons.constants.TileTypes;
import ca.maceman.mapgenerator.commons.generator.IMapGenerator;
import ca.maceman.mapgenerator.commons.model.Tile;
import ca.maceman.mapgenerator.commons.model.TileMap;
import ca.maceman.mapgenerator.commons.model.TileType;
import ca.maceman.mapgenerator.commons.utils.impl.SimpleNoiseGenerator;

public class SimpleMapGenerator implements IMapGenerator {

	Tile[][] tiles = null;
	Random r = new Random();

	public TileMap GenerateTerrainMap(int width, int height, int octave,
			int octaveCount) {

		int seed = r.nextInt();

		return GenerateTerrainMap(width, height, octave, octaveCount, seed);
	}

	public TileMap GenerateTerrainMap(int width, int height, int octave,
			int octaveCount, int seed) {

		TileMap tileMap = new TileMap();

		r = new Random(seed);

		tiles = new Tile[width][height];

		SimpleNoiseGenerator noiseGenerator = new SimpleNoiseGenerator(seed);

		float[][] heightMap = noiseGenerator.GeneratePerlinNoise(noiseGenerator
				.GenerateSmoothNoise(
						noiseGenerator.GenerateRadialWhiteNoise(width, height),
						octave), octaveCount);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j] = new Tile(heightMap[i][j], i, j);
			}
		}
		
		tileMap.setTiles(tiles);
//		addRivers();

		return tileMap;
	}

//	private void addRivers() {
//
//		int width = tiles.length;
//		int height = tiles[0].length;
//		int c = 0;
//
//		for (int i = 0; i < width; i++) {
//			for (int j = 0; j < height; j++) {
//				if (tiles[i][j].getType().getId() == TileTypes.MOUNTAIN.getId()) {
//
//					// Generate river Sources
//					c = r.nextInt(100);
//					if (c >= 90) {
//						tiles[i][j].setType(TileTypes.RIVER_SOURCE);
//
//						// Rivers go to ocean by path of least resistance or
//						// stop when no other way is found
//
//						Point closestTile = findClosestOfType(tiles,
//								TileTypes.SHALLOW_WATER, i, j, 301); // May have
//																		// to
//																		// make
//																		// these
//																		// as
//																		// runnables
//																		// to
//																		// fix
//																		// long
//																		// map
//																		// gen
//																		// times
//
//						PlotRiver(tiles, i, j, closestTile);
//						if (i + 10 < width) {
//							i = i + 10;
//						}
//						if (j + 10 < height) {
//							j = j + 10;
//						}
//
//					}
//
//				}
//			}
//		}
//	}

	/**
	 * Using recursion we make a path to the closest sea-level tile by using the
	 * path of least resistance; using the depth of the tile as the weight of
	 * said resistance.
	 * 
	 * @return
	 */
	private void PlotRiver() {

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

	/**
	 * Finds a path and stores the coordinates of each tile along the path into
	 * a point array
	 * 
	 * @param x
	 * @param y
	 * @param destX
	 * @param destY
	 * @return
	 */
	private float getDifficulty(int x, int y, int destX, int destY) {
		return 0.0f;

	}

	private float EstimateHeuristic(int x, int y, int destX, int destY) {
		return 0.0f;

	}

	private Tile findClosestOfType(TileType type, int x, int y, int radius) {
		return null;
	}

	private Tile findLowestTile(Tile[][] tiles, int x, int y, int radius) {
		return null;
	}
}
