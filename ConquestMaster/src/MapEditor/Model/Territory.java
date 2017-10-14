package MapEditor.Model;

import java.util.ArrayList;

/**
 * This class handle territory linked relationship, and manage to set and get territories names.
 */
public class Territory {
	public String name;
	Continent cont;
	private int centerY = -1;
	private int centerX = -1;
	public ArrayList<Territory> links = new ArrayList<>();
	public ArrayList<String> linkNames = new ArrayList<>();
	public boolean hasReached = false;
	
	/**
	 * constructor method.
	 */
	public Territory() {
	}
	
	/**
	 * method to add connection between territories.
	 * @param t input territory that want to create link.
	 */
	public void addLink(Territory t) {
		if (t == this) {
			return;
		}
		if (!this.links.contains(t)) {
			this.links.add(t);
		}
	}
	
	/**
	 * To get the absolute coordinateX of a territory.
	 * @return	return X coordinate of this territory
	 */
	public int getCenterX() {
		return (int) this.centerX;
	}
	
	/**
	 * To get the absolute coordinateY of a territory.
	 * @return return Y coordinate of this territory
	 */
	public int getCenterY() {
		return (int) this.centerY;
	}
	
	/**
	 * To get the continent that a territory is in.
	 * @return	return the territory's continent
	 */
	public Continent getContinent() {
		return this.cont;
	}
	/**
	 * use list to storage a territory connected territories.
	 * @return return the arraylist
	 */	
	public ArrayList<Territory> getLinks() {
		return this.links;
	}
	
	/**
	 * delete a link relationship of a territory from the 
	 * @param t choose one territory that wants to delete the connection.
	 */
	public void removeLink(Territory t) {
		if (this.links.contains(t)) {
			this.links.remove(t);
		}
	}
	
	/**
	 * create the ceter
	 * @param x
	 * @param y
	 */
	public void setCenter(int x, int y) {
		this.centerX = x;
		this.centerY = y;
	}

	public void setContinent(Continent cont) {
		this.cont = cont;
	}

	@Override
	public String toString() {
		return "Territory [name=" + name + ", continnet=" + cont + ", centerY=" + centerY + ", centerX=" + centerX
				+ ", linkes=" + linkNames + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Continent getCont() {
		return cont;
	}

	public void setCont(Continent cont) {
		this.cont = cont;
	}

	public ArrayList<String> getLinkNames() {
		return linkNames;
	}

	public void setLinkNames(ArrayList<String> linkNames) {
		this.linkNames = linkNames;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setLinks(ArrayList<Territory> links) {
		this.links = links;
	}

}
