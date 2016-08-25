package ca.maceman.mapgenerator.swing.controller;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import ca.maceman.mapgenerator.commons.generator.IMapGenerator;
import ca.maceman.mapgenerator.commons.generator.impl.SimpleMapGenerator;
import ca.maceman.mapgenerator.commons.model.TileMap;
import ca.maceman.mapgenerator.swing.ui.MapGeneratorFrame;
import ca.maceman.mapgenerator.swing.utils.OutputHandler;

/**
 * The {@link MapGeneratorFrame} handler class
 * 
 * @author Macemanca
 */
public class MapGeneratorFrameController {

	private MapGeneratorFrame mapGeneratorFrame;
	private IMapGenerator mapGenerator;
	private OutputHandler outputHandler;

	
	/**
	 * Simple constructor
	 * 
	 * @param mapGeneratorFrame
	 */
	public MapGeneratorFrameController(MapGeneratorFrame mapGeneratorFrame) {
		this.mapGeneratorFrame = mapGeneratorFrame;
		mapGenerator = new SimpleMapGenerator();
	}

	/**
	 * Generates a new map for the frame to use.
	 * 
	 * @param mapWidth
	 * @param mapHeight
	 * @param octaveCount
	 * @param seed
	 * @return {@link TileMap}
	 */
	public TileMap GenerateNewMap(String mapWidthString, String mapHeightString, int octaveCount, int octaves, boolean isIsland, String mapSeedString) {

		int mapWidth = 0;
		int mapHeight = 0;
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
			if (mapSeedString.equals("")) {

			} else {
				mapSeed = Integer.parseInt(mapSeedString);
			}
		} catch (Exception e) {
			showErr("The seed given is not a numeric value.");
		}

		TileMap tileMap = new TileMap();

		try {
			tileMap = mapGenerator.GenerateTerrainMap(mapWidth, mapHeight, octaves, octaveCount, isIsland, mapSeed);
		} catch (Exception e) {
			showErr(e.getMessage());
			e.printStackTrace();
		}

		return tileMap;
	}

	/**
	 * Displays an error message in a dialog box.
	 * 
	 * @param errorMessage
	 */
	public void showErr(String errorMessage) {
		JOptionPane.showMessageDialog(mapGeneratorFrame, "An error has occured: " + errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void MapToPanel(TileMap tileMap) {

		outputHandler = new OutputHandler();

		try {
			if (tileMap != null) {
				mapGeneratorFrame.getLabelImageHolder().setIcon(new ImageIcon((Image) outputHandler.tileMapToImage(tileMap)));
				mapGeneratorFrame.getLabelImageHolder().setSize(new Dimension(tileMap.getWidth(), tileMap.getHeight()));

				mapGeneratorFrame.getPanelImg().setPreferredSize(mapGeneratorFrame.getLabelImageHolder().getSize());
				mapGeneratorFrame.getPanelImg().repaint();
				mapGeneratorFrame.getPanelImg().revalidate();

				mapGeneratorFrame.getMainScrollPane().setViewportView(mapGeneratorFrame.getPanelImg());
				mapGeneratorFrame.getMainScrollPane().repaint();
				mapGeneratorFrame.getMainScrollPane().revalidate();

				mapGeneratorFrame.repaint();
				mapGeneratorFrame.revalidate();
			} else {

				throw new Exception("tileMap is null.");
			}
		} catch (Exception e) {
			showErr(e.toString());
			e.printStackTrace();
		}

	}

	public void scaledImage(int i) {
		// TODO Auto-generated method stub

	}
}
