package MapEditor.Model;

import java.util.ArrayList;

public class Territory {
	public String name;
	Continent cont;
	private int centerY = -1;
	private int centerX = -1;
	public ArrayList<Territory> links = new ArrayList<>();
	public ArrayList<String> linkNames = new ArrayList<>();
	public boolean hasReached = false;

	public Territory() {
	}

	public void addLink(Territory t) {
		if (t == this) {
			return;
		}
		if (!this.links.contains(t)) {
			this.links.add(t);
		}
	}

	public int getCenterX() {
		return (int) this.centerX;
	}

	public int getCenterY() {
		return (int) this.centerY;
	}

	public Continent getContinent() {
		return this.cont;
	}

	public ArrayList<Territory> getLinks() {
		return this.links;
	}

	public void removeLink(Territory t) {
		if (this.links.contains(t)) {
			this.links.remove(t);
		}
	}

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
