package ca.maceman.mapgenerator.commons.model;

/**
 * Model for the {@link Tile}'s Type.
 * 
 * It has an ID and a name. This class should be used for assigning behaviour through different methods.
 * 
 * @author Andy
 *
 */
public class TileType {
	private int id;
	private String name;

	public TileType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
