package ca.maceman.mapgenerator.commons.generator;

import ca.maceman.mapgenerator.commons.model.TileMap;

public interface IMapGenerator {
	
	public TileMap GenerateTerrainMap(int width, int height, int octave, int octaveCount);
	public TileMap GenerateTerrainMap(int width, int height, int octave, int octaveCount, int seed);

}
