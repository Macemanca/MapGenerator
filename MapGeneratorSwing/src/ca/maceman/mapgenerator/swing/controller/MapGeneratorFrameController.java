package ca.maceman.mapgenerator.swing.controller;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

	private MapGeneratorFrame mapGeneratorFrame = null;
	private IMapGenerator mapGenerator = null;
	private OutputHandler outputHandler = null;
	private BufferedImage originalImage = null;

	
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
	 * Displays a message in a dialog box.
	 * 
	 * @param message
	 */
	public void showInfo(String message) {
		JOptionPane.showMessageDialog(mapGeneratorFrame,  message, "Info", JOptionPane.INFORMATION_MESSAGE);
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
				originalImage = (BufferedImage) ((ImageIcon)mapGeneratorFrame.getLabelImageHolder().getIcon()).getImage();
				
			} else {

				throw new Exception("tileMap is null.");
			}
		} catch (Exception e) {
			showErr(e.toString());
			e.printStackTrace();
		}

	}

	public void scaleImage(int i) {
		try {
			
			if (originalImage != null) {
				
				int width = originalImage.getWidth();
				int height = originalImage.getHeight();

				BufferedImage newImage = new BufferedImage(width * i, height * i,BufferedImage.TYPE_INT_ARGB);
				AffineTransform t = new AffineTransform();
				t.scale((double) i, (double) i);
				AffineTransformOp scale = new AffineTransformOp(t, AffineTransformOp.TYPE_BILINEAR);
				newImage = scale.filter(originalImage, newImage);

				mapGeneratorFrame.getLabelImageHolder().setIcon(new ImageIcon((Image) newImage));

				mapGeneratorFrame.getLabelImageHolder().setSize(
						new Dimension(newImage.getWidth(), newImage.getHeight()));
				mapGeneratorFrame.getPanelImg().setPreferredSize(
						mapGeneratorFrame.getLabelImageHolder().getSize());
				mapGeneratorFrame.getPanelImg().repaint();
				mapGeneratorFrame.getPanelImg().revalidate();

				mapGeneratorFrame.getMainScrollPane().setViewportView(mapGeneratorFrame.getPanelImg());
				mapGeneratorFrame.getMainScrollPane().repaint();
				mapGeneratorFrame.getMainScrollPane().revalidate();

				mapGeneratorFrame.repaint();
				mapGeneratorFrame.revalidate();
			}else{
				showErr("You have not yet generated a map.");
			}
			
		} catch (Exception e) {
			showErr(e.toString());
		}
	}

	public void saveImageToFile() {
		String filePath = "";
		try {
			filePath = outputHandler.saveImageToFile(originalImage).getAbsolutePath();
		} catch (IOException e) {
			showErr(e.toString());
		}
		showInfo("The has been saved to : " + filePath);
		
	}
}
