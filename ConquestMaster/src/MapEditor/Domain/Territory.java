package MapEditor.Domain;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Territory {
	//private static Logger logger = Logger.getLogger(Territory.class.getName());
	public String name;
	Continent cont;
	private int centerY = -1;
	private int centerX = -1;
	//boolean isBridge;
	public ArrayList<Territory> links = new ArrayList<>();
	public ArrayList<String> linkNames = new ArrayList<>();
	//private transient ArrayList<Territory> recommendedLinks;
	//private transient ArrayList<Territory> missingLinks;
	//private transient ArrayList<Territory> extraLinks;
	//private transient ArrayList<Point> edges;
	//private transient Rectangle boundingBox;
	//private transient Color originalColor;

	public Territory() {
		this.links = new ArrayList();
	}

	public void addLink(Territory t) {
		if (t == this) {
			return;
		}
		if (!this.links.contains(t)) {
			this.links.add(t);
			//clearCachedRecommendationCalculations();
		}
	}

//	public void addRecommendedLink(Territory ter) {
//		if (this.recommendedLinks == null) {
//			this.recommendedLinks = new ArrayList();
//		}
//		if (!this.recommendedLinks.contains(ter)) {
//			this.recommendedLinks.add(ter);
//			clearCachedRecommendationCalculations();
//		}
//	}
//
//	public void clearCachedRecommendationCalculations() {
//		this.missingLinks = null;
//		this.extraLinks = null;
//	}
//
//	public void clearEdges() {
//		this.edges = null;
//	}
//
//	public void clearRecommendedLinks() {
//		clearRecommendedLinks(false);
//	}
//
//	void clearRecommendedLinks(boolean createEmpty) {
//		this.recommendedLinks = null;
//		this.missingLinks = null;
//		this.extraLinks = null;
//		if (createEmpty) {
//			this.recommendedLinks = new ArrayList();
//		}
//	}
//
//	public Rectangle getBoundingBox() {
//		if (this.boundingBox != null) {
//			return this.boundingBox;
//		}
//		if (this.edges == null) {
//			return null;
//		}
//		int x = Integer.MAX_VALUE;
//		int y = Integer.MAX_VALUE;
//		int x2 = -1;
//		int y2 = -1;
//		for (Point p : this.edges) {
//			if (p.x < x) {
//				x = p.x;
//			}
//			if (p.x > x2) {
//				x2 = p.x;
//			}
//			if (p.y < y) {
//				y = p.y;
//			}
//			if (p.y > y2) {
//				y2 = p.y;
//			}
//		}
//		this.boundingBox = new Rectangle(x, y, x2 - x, y2 - y);
//		return this.boundingBox;
//	}

	public int getCenterX() {
		return (int) this.centerX;
	}

//	public float getCenterXFloat() {
//		return this.centerX;
//	}

	public int getCenterY() {
		return (int) this.centerY;
	}
//
//	public float getCenterYFloat() {
//		return this.centerY;
//	}

	public Continent getContinent() {
		return this.cont;
	}

//	public ArrayList<Point> getEdges() {
//		return this.edges;
//	}
//
//	public ArrayList<Territory> getExtraLinks() {
//		if (this.extraLinks == null) {
//			if ((this.recommendedLinks == null) || (this.links == null)) {
//				return null;
//			}
//			this.extraLinks = ((ArrayList) this.links.clone());
//			this.extraLinks.removeAll(this.recommendedLinks);
//		}
//		return this.extraLinks;
//	}

	public ArrayList<Territory> getLinks() {
		return this.links;
	}

//	public ArrayList<Territory> getMissingLinks() {
//		if (this.missingLinks == null) {
//			if ((this.recommendedLinks == null) || (this.links == null)) {
//				return null;
//			}
//			this.missingLinks = ((ArrayList) this.recommendedLinks.clone());
//			this.missingLinks.removeAll(this.links);
//		}
//		return this.missingLinks;
//	}
//
//	public final Color getOriginalColor() {
//		return this.originalColor;
//	}
//
//	final ArrayList<Territory> getRecommendedLinks() {
//		return this.recommendedLinks;
//	}

//	public boolean hasSuggestedLinks() {
//		return ((getMissingLinks() != null) && (!getMissingLinks().isEmpty()))
//				|| ((getExtraLinks() != null) && (!getExtraLinks().isEmpty()));
//	}
//
//	public boolean isNear(Territory ter, int minDist, int minPoints) {
//		ArrayList<Point> terEdges = ter.getEdges();
//		if ((this.edges == null) || (this.edges.isEmpty())) {
//			logger.warning("Analyzer - edges not calculated for territory [territory=" + ter.name + "]");
//			return false;
//		}
//		if ((terEdges == null) || (terEdges.isEmpty())) {
//			logger.warning("Analyzer - edges not calculated for territory [territory=" + ter.name + "]");
//			return false;
//		}
//		Rectangle bb = (Rectangle) getBoundingBox().clone();
//		bb.grow(minDist, minDist);
//		if (!bb.intersects(ter.getBoundingBox())) {
//			return false;
//		}
//		int contacts = 0;
//		for (Point p : this.edges) {
//			if (contacts >= minPoints) {
//				if (logger.isLoggable(Level.FINER)) {
//					logger.finer("Analyzer - territories border, minimum contact points reached [territory=" + this.name
//							+ "][territory=" + ter.name + "][contact points >= " + contacts + "]");
//				}
//				return true;
//			}
//			for (Point q : terEdges) {
//				if (p.distance(q) <= minDist) {
//					contacts++;
//					break;
//				}
//			}
//		}
//		if (logger.isLoggable(Level.FINER)) {
//			logger.finer("Analyzer - territories do not border, insufficient contact points [territory=" + this.name
//					+ "][territory=" + ter.name + "][contact points=" + contacts + "]");
//		}
//		return false;
//	}

	public void removeLink(Territory t) {
		if (this.links.contains(t)) {
			this.links.remove(t);
		//	clearCachedRecommendationCalculations();
		}
	}

//	public void removeReferences(Territory t) {
//		this.links.remove(t);
//		if (this.recommendedLinks != null) {
//			this.recommendedLinks.remove(t);
//		}
//		if (this.missingLinks != null) {
//			this.missingLinks.remove(t);
//		}
//		if (this.extraLinks != null) {
//			this.extraLinks.remove(t);
//		}
//	}

//	public void setCenter(float x, float y) {
//		this.centerX = x;
//		this.centerY = y;
//		clearEdges();
//		this.originalColor = null;
//	}

	public void setCenter(int x, int y) {
		this.centerX = x;
		this.centerY = y;
//		clearEdges();
//		this.originalColor = null;
	}

	public void setContinent(Continent cont) {
		this.cont = cont;
	}

//	public void setEdges(ArrayList<Point> edges) {
//		this.edges = edges;
//		this.boundingBox = null;
//	}
//
//	public final void setOriginalColor(Color originalColor) {
//		this.originalColor = originalColor;
//	}

	@Override
	public String toString() {
		return "Territory [name=" + name + ", continnet="+ cont +", centerY=" + centerY + ", centerX=" + centerX + ", linkes="
				+ linkNames + "]";
	}

//	public static Logger getLogger() {
//		return logger;
//	}
//
//	public static void setLogger(Logger logger) {
//		Territory.logger = logger;
//	}

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

//	public boolean isBridge() {
//		return isBridge;
//	}
//
//	public void setBridge(boolean isBridge) {
//		this.isBridge = isBridge;
//	}

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

//	public void setRecommendedLinks(ArrayList<Territory> recommendedLinks) {
//		this.recommendedLinks = recommendedLinks;
//	}
//
//	public void setMissingLinks(ArrayList<Territory> missingLinks) {
//		this.missingLinks = missingLinks;
//	}
//
//	public void setExtraLinks(ArrayList<Territory> extraLinks) {
//		this.extraLinks = extraLinks;
//	}
//
//	public void setBoundingBox(Rectangle boundingBox) {
//		this.boundingBox = boundingBox;
//	}

}
