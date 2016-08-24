package ca.maceman.mapgenerator.commons.generator;

import ca.maceman.mapgenerator.commons.model.TileMap;

/**
 * Holds the logic for creating a {@link TileMap}.
 * 
 * @author Andy Massé
 *
 */
public interface IMapGenerator {

	/**
	 * Generates a {@link TileMap} using the {@link INoiseGenerator} and returns it.
	 * 
	 * @param width
	 * @param height
	 * @param octave
	 * @param octaveCount
	 * @return the {@link TileMap}
	 * @throws Exception
	 */
	public TileMap GenerateTerrainMap(int width, int height, int octave, int octaveCount) throws Exception;
	
	/**
	 * Generates a {@link TileMap} using the {@link INoiseGenerator} with a random seed and returns it.
	 * 
	 * 
	 * 
	 * @param width
	 * @param height
	 * @param octave
	 * @param octaveCount
	 * @param seed
	 * @return the {@link TileMap}
	 * @throws Exception
	 */
	public TileMap GenerateTerrainMap(int width, int height, int octave, int octaveCount, int seed) throws Exception;

}
