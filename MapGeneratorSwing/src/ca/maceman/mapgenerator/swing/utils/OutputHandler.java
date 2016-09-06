package ca.maceman.mapgenerator.swing.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ca.maceman.mapgenerator.commons.model.Tile;
import ca.maceman.mapgenerator.commons.model.TileMap;

public class OutputHandler {

	public OutputHandler() {

	}

	public BufferedImage tileMapToImage(TileMap tileMap) {
		int width = tileMap.getWidth();
		int height = tileMap.getHeight();
		int blackWaterColour = new Color(0, 0, 100).getRGB();
		int deepWaterColour = new Color(0, 75, 190).getRGB();
		int waterColour = new Color(0, 100, 250).getRGB();
		int shallowWaterColour = new Color(0, 190, 250).getRGB();
		int sandColour = new Color(245, 225, 135).getRGB();
		int grassColour = new Color(0, 200, 0).getRGB();
		int forestColour = new Color(50, 130, 0).getRGB();
		int baseMountainColour = new Color(94, 94, 94).getRGB();
		int mountainColour = new Color(180, 180, 180).getRGB();
		int snowColour = new Color(255, 255, 255).getRGB();
		int riverSourceColour = shallowWaterColour;
		int riverColour = shallowWaterColour;
		int lakeSourceColour = Color.RED.getRGB();
		int lakeColour = Color.ORANGE.getRGB();
		int buildingColour = Color.RED.getRGB();
		int wallColour = Color.GRAY.getRGB();
		
		BufferedImage bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (Tile[] tileRow : tileMap.getTiles()) {
			for (Tile currentTile : tileRow) {
				switch (currentTile.getType().getId()) {
				case 1:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), blackWaterColour);
					break;
				case 2:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), deepWaterColour);
					break;
				case 3:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), waterColour);
					break;
				case 4:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), shallowWaterColour);
					break;
				case 5:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), sandColour);
					break;
				case 6:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), grassColour);
					break;
				case 7:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), forestColour);
					break;
				case 8:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), baseMountainColour);
					break;
				case 9:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), mountainColour);
					break;
				case 10:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), snowColour);
					break;
				case 11:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), riverSourceColour);
					break;
				case 12:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), riverColour);
					break;
				case 13:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), lakeSourceColour);
					break;
				case 14:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), lakeColour);
					break;
				case 15:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), buildingColour);
					break;
				case 16:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), wallColour);
					break;
				default:
					bufferImage.setRGB(currentTile.getXPosition(), currentTile.getYPosition(), Color.black.getRGB());
					break;
				}
			}
		}
		return bufferImage;
	}

	public File saveImageToFile(BufferedImage image) throws IOException{
		 
	    File outputfile = new File("map.png");
	    if (outputfile.exists()){
	    	int fileNumber = 0;
	    	while(outputfile.exists()){
	    		fileNumber++;
	    		outputfile = new File("map_"+fileNumber+".png");
	    	}
	    }
	    ImageIO.write(image, "png", outputfile);
	    return outputfile;
	}
}
