package ca.maceman.mapgenerator.swing.controller;

import javax.swing.JOptionPane;

import ca.maceman.mapgenerator.commons.generator.IMapGenerator;
import ca.maceman.mapgenerator.commons.generator.impl.SimpleMapGenerator;
import ca.maceman.mapgenerator.commons.model.TileMap;
import ca.maceman.mapgenerator.swing.ui.MapGeneratorFrame;

/**
 * The {@link MapGeneratorFrame} handler class
 */
public class MapGeneratorFrameController {

	private MapGeneratorFrame mapGeneratorFrame;
	private IMapGenerator mapGenerator;

	public MapGeneratorFrameController(MapGeneratorFrame mapGeneratorFrame) {
		this.mapGeneratorFrame = mapGeneratorFrame;
		mapGenerator = new SimpleMapGenerator();
	}

	/**
	 * Generates a new map for the frame to use.
	 * 
	 * @param mapWidth
	 * @param mapHeight
	 * @param mapBorderWidth
	 * @param octaveCount
	 * @param seed
	 * @return {@link TileMap}
	 */
	public TileMap GenerateNewMap(String mapWidthString, String mapHeightString, String mapBorderWidthString, int octaveCount, String mapSeedString) {

		int mapWidth = 0;
		int mapHeight = 0;
		int mapBorderWidth = 0;
		int mapSeed = 0;

		try {
			mapWidth = Integer.parseInt(mapWidthString);
		} catch (Exception e) {
			showErr("The width given is not a numeric value.");
		}

		try {
			mapHeight = Integer.parseInt(mapHeightString);
		} catch (Exception e) {
			showErr("The height given is not a numeric value.");
		}

		try {
			mapBorderWidth = Integer.parseInt(mapBorderWidthString);
		} catch (Exception e) {
			showErr("The border width given is not a numeric value.");
		}

		try {
			mapSeed = Integer.parseInt(mapSeedString);
		} catch (Exception e) {
			showErr("The seed given is not a numeric value.");
		}

		return null;
	}

	/**
	 * Displays an error message in a dialog box.
	 * 
	 * @param errorMessage
	 */
	public void showErr(String errorMessage) {
		JOptionPane.showMessageDialog(mapGeneratorFrame, "An error has occured: " + errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void MapToPanel() {
		// TODO Auto-generated method stub

	}

	public void scaledImage(int i) {
		// TODO Auto-generated method stub

	}
}
