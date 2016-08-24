package ca.maceman.mapgenerator.commons.utils.impl;

import java.util.Random;

import ca.maceman.mapgenerator.commons.utils.INoiseGenerator;

public class SimpleNoiseGenerator implements INoiseGenerator {

	private long seed = 0;
	private Random r;

	public SimpleNoiseGenerator(long seed) {
		this.seed = seed;
	}
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
				if (((i <= borderWidth) || (i >= width - borderWidth) || (j <= borderWidth) || (j >= height - borderWidth))
						&& borderWidth != 0) {
					noise[i][j] = (float) 0;
				} else {
					noise[i][j] = (float) r.nextDouble();
				}
			}
		}
		return noise;
	}

	public float[][] GenerateRadialWhiteNoise(int width, int height) {

		float[][] noise = new float[width][height];
		float distanceX = 0f;
		float distanceY = 0f;
		float distanceToCenter = 0f;
		int borderWidth = 10;
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
				if (((i <= borderWidth) || (i >= width - borderWidth)
						|| (j <= borderWidth) || (j >= height - borderWidth))
						&& borderWidth != 0) {
					noise[i][j] = (float) 0;
				} else {
					noise[i][j] = (float) r.nextDouble()
							- (distanceToCenter / 256);
				}
			}
		}

		return noise;
	}

	public float[][] GenerateSmoothNoise(float[][] baseNoise, int octave) {

		float horizontal_blend = 0f;
		float vertical_blend = 0f;
		float top = 0f;
		float bottom = 0f;
		int sample_ia = 0;
		int sample_ib = 0;
		int sample_ja = 0;
		int sample_jb = 0;
		int width = baseNoise.length;
		int height = baseNoise[0].length;
		int samplePeriod = 1 << octave; // Shift bits the numbers of octaves to
										// the left, example : 1 << 2 means 0001
										// becomes 0100
		float[][] smoothNoise = new float[width][height];
		float sampleFrequency = 1.0f / samplePeriod;

		for (int i = 0; i < width; i++) {

			/* Calculate the horizontal sampling indices */
			sample_ia = (i / samplePeriod) * samplePeriod;
			sample_ib = (sample_ia + samplePeriod) % width; // wrap around
			horizontal_blend = (i - sample_ia) * sampleFrequency;

			for (int j = 0; j < height; j++) {

				/* Calculate the vertical sampling indices */
				sample_ja = (j / samplePeriod) * samplePeriod;
				sample_jb = (sample_ja + samplePeriod) % height; // wrap around
				vertical_blend = (j - sample_ja) * sampleFrequency;

				/* Blend the top and bottom corners */
				top = interpolate(baseNoise[sample_ia][sample_ja],
						baseNoise[sample_ib][sample_ja], horizontal_blend);
				bottom = interpolate(baseNoise[sample_ia][sample_jb],
						baseNoise[sample_ib][sample_jb], horizontal_blend);

				/* Final blend */
				smoothNoise[i][j] = interpolate(top, bottom, vertical_blend);
			}
		}
		return smoothNoise;
	}

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

	private float interpolate(float x0, float x1, float alpha) {
		return x0 * (1 - alpha) + alpha * x1;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

}
