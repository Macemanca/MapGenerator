package ca.maceman.mapgenerator.commons.generator.impl;

import java.util.Random;

import ca.maceman.mapgenerator.commons.generator.INoiseGenerator;

/**
 * Simple implementation of the {@link INoiseGenerator}
 * 
 * @author Macemanca
 * 
 */
public class SimpleNoiseGenerator implements INoiseGenerator {

	private long seed = 0;
	private Random r;

	/**
	 * Simple Constructor
	 * 
	 * @param seed
	 */
	public SimpleNoiseGenerator(long seed) {
		this.seed = seed;
	}

	/**
	 * {@inheritDoc}}
	 */
	public float[][] GenerateWhiteNoise(int width, int height, int borderWidth) {

		float[][] noise = new float[width][height];

		if (seed != 0) {
			r = new Random(seed);
		} else {
			r = new Random();
		}

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				/* Check if coordinate is part of the border */
				if (((i <= borderWidth) || (i >= width - borderWidth) || (j <= borderWidth) || (j >= height - borderWidth)) && borderWidth != 0) {

					noise[i][j] = (float) 0;
				} else {
					noise[i][j] = (float) r.nextDouble();
				}
			}
		}
		return noise;
	}

	/**
	 * {@inheritDoc}}
	 */
	public float[][] GenerateRadialWhiteNoise(int width, int height, int borderThickness) {

		float[][] noise = new float[width][height];
		float distanceX = 0f;
		float distanceY = 0f;
		float distanceToCenter = 0f;
		int centerX = width / 2;
		int centerY = height / 2;

		if (seed != 0) {
			r = new Random(seed);
		} else {
			r = new Random();
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				distanceX = (centerX - i) * (centerX - i);
				distanceY = (centerY - j) * (centerY - j);
				distanceToCenter = (float) Math.sqrt(distanceX + distanceY);

				/* Check if coordinate is part of the border */
				if (((i <= borderThickness) || (i >= width - borderThickness) || (j <= borderThickness) || (j >= height - borderThickness)) && borderThickness != 0) {

					noise[i][j] = (float) 0;
				} else {
					noise[i][j] = (float) r.nextDouble() - (distanceToCenter / ((width + height) / 2));
				}
			}
		}

		return noise;
	}

	/**
	 * {@inheritDoc}}
	 */
	public float[][] GenerateSmoothNoise(float[][] baseNoise, int octave) {

		float horizontal_blend = 0f;
		float vertical_blend = 0f;
		float top = 0f;
		float bottom = 0f;
		int sample_ia = 0;
		int sample_ib = 0;
		int sample_Y_A = 0;
		int sample_Y_B = 0;
		int width = baseNoise.length;
		int height = baseNoise[0].length;
		int samplePeriod = 1 << octave;
		float[][] smoothNoise = new float[width][height];
		float sampleFrequency = 1.0f / samplePeriod;

		for (int mapX = 0; mapX < width; mapX++) {

			/* Calculate the horizontal sampling indices */
			sample_ia = (mapX / samplePeriod) * samplePeriod;
			sample_ib = (sample_ia + samplePeriod) % width;
			horizontal_blend = (mapX - sample_ia) * sampleFrequency;

			for (int mapY = 0; mapY < height; mapY++) {

				/* Calculate the vertical sampling indices */
				sample_Y_A = (mapY / samplePeriod) * samplePeriod;
				sample_Y_B = (sample_Y_A + samplePeriod) % height; // wrap
																	// around
				vertical_blend = (mapY - sample_Y_A) * sampleFrequency;

				/* Blend the top and bottom corners */
				top = interpolate(baseNoise[sample_ia][sample_Y_A], baseNoise[sample_ib][sample_Y_A], horizontal_blend);
				bottom = interpolate(baseNoise[sample_ia][sample_Y_B], baseNoise[sample_ib][sample_Y_B], horizontal_blend);

				/* Final blend */
				smoothNoise[mapX][mapY] = interpolate(top, bottom, vertical_blend);
			}
		}
		return smoothNoise;
	}

	/**
	 * {@inheritDoc}}
	 */
	public float[][] GeneratePerlinNoise(float[][] baseNoise, int octaveCount) {
		float[][][] smoothNoise = new float[octaveCount][][];
		float persistance = 0.5f;
		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;
		int width = baseNoise.length;
		int height = baseNoise[0].length;

		float[][] perlinNoise = new float[width][height];

		/* Generate smooth noises */
		for (int i = 0; i < octaveCount; i++) {
			smoothNoise[i] = GenerateSmoothNoise(baseNoise, i);
		}

		/* Blend noises together */
		for (int octave = octaveCount - 1; octave >= 0; octave--) {

			amplitude *= persistance;
			totalAmplitude += amplitude;

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
				}
			}
		}

		/* Normalization */
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				perlinNoise[i][j] /= totalAmplitude;
			}
		}

		return perlinNoise;
	}

	/**
	 * Interpolate 2 points. Used for blending noises.
	 * 
	 * @param pointA
	 * @param pointB
	 * @param alpha
	 * @return
	 */
	private float interpolate(float pointA, float pointB, float alpha) {
		return pointA * (1 - alpha) + alpha * pointB;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

}
