package ca.maceman.mapgenerator.commons.utils;

public interface INoiseGenerator {

	/**
	 *  generates a array of random values (Noise)
	 *  
	 * @param width
	 * @param height
	 * @param borderWidth
	 * @return
	 */
	public float[][] GenerateWhiteNoise(int width, int height, int borderWidth);

	/**
	 *  uses a white noise array to create a new smooth array
	 * 
	 * @param baseNoise
	 * @param octave
	 * @return
	 */
	public float[][] GenerateSmoothNoise(float[][] baseNoise, int octave);

	/**
	 * creates a perlin noise using an array of smooth noises with increased
	 * octaves. this returns an array with large areas of similar values where
	 * the individual values are different enough so as to leave detail between
	 * the variations creating a smoky appearance
	 * 
	 * @param smoothNoise
	 * @param octaveCount
	 * @return
	 */
	public float[][] GeneratePerlinNoise(float[][] smoothNoise, int octaveCount);
}
