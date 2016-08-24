package ca.maceman.mapgenerator.commons;

import ca.maceman.mapgenerator.commons.generator.IMapGenerator;
import ca.maceman.mapgenerator.commons.generator.impl.SimpleMapGenerator;
import ca.maceman.mapgenerator.commons.model.Tile;
import ca.maceman.mapgenerator.commons.model.TileMap;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TileMap map = new TileMap();
		IMapGenerator generator = new SimpleMapGenerator();
		map = generator.GenerateTerrainMap(100, 100, 6, 6);

		for (Tile[] tileRow : map.getTiles()) {
			System.out.print("}");
			System.out.println("{");
			for (Tile tile : tileRow) {
				System.out.print("(" + tile.getDepth() + "),");
			}
		}
		
		System.out.println(map.getTiles());
	}

}
