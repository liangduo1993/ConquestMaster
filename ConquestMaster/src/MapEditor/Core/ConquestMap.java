package MapEditor.Core;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JTextArea;

import MapEditor.Domain.Continent;
import MapEditor.Domain.Territory;
import MapEditor.Util.StringUtil;
import MapEditor.View.LogPanel;

public class ConquestMap implements Comparator<Object> {
	public static enum ScrollOptions {
		HORIZONTAL, VERTICAL, NONE;
	}

	private final String newline = "\n";
	private static Random rnd = new Random();
	static final String NEWCONTINENTSTRING = "<new continent>";
	static final String UNTITLEDMAPNAME = "Untitled Map";
	// private static Logger logger =
	// Logger.getLogger(ConquestMapMaker.class.getPackage().getName());
	private String mapFilePath;
	private String imageFilePath;
	private ScrollOptions scroll;
	private String author;
	private boolean wrap;
	private boolean warn;
	public ArrayList<Continent> continents = new ArrayList<>();
	public ArrayList<Territory> territories = new ArrayList<>();
	// public String lastContinentUsed = null;
	// public ExtendedProperties props;
	public boolean dirty = false;
	// public ConquestMapMaker cmm;
	//private JTextArea log = mainFrame.lp.log;
	private JTextArea log = new LogPanel().log;

	public ConquestMap() {
		this.continents = new ArrayList();
		this.territories = new ArrayList();
		clear();
		// this.cmm = cmm;
		// this.props = props;
	}

	public boolean addContinent(Continent cont) {
		if (findContinent(cont.getName()) == null) {
			this.continents.add(cont);
			// this.lastContinentUsed = cont.getName();
			this.dirty = true;
			return true;
		}
		return false;
	}

	public void addTerritory(Territory ter) {
		if (findTerritory(ter.name) == null) {
			this.territories.add(ter);

			ArrayList<String> linkNames = ter.getLinkNames();
			if (linkNames.size() > 0) {
				for (String name : linkNames) {
					Territory neighbour = findTerritory(name);
					if (neighbour != null) {
						neighbour.getLinkNames().add(ter.getName());
						buildTerritoryLinks(neighbour);
					}
				}
			}

			this.dirty = true;
		}
	}

	// public boolean allTerritiriesReachable() {
	// if ((this.territories == null) || (this.territories.isEmpty())) {
	// return true;
	// }
	// HashSet<Territory> inside = new HashSet();
	//
	// ArrayList<Territory> fringe = new ArrayList();
	//
	// fringe.add((Territory) this.territories.get(0));
	// while (!fringe.isEmpty()) {
	// Territory ter = (Territory) fringe.remove(0);
	// inside.add(ter);
	// if (ter.getLinks() != null) {
	// for (Territory ter2 : ter.getLinks()) {
	// if (!inside.contains(ter2)) {
	// fringe.add(ter2);
	// }
	// }
	// }
	// }
	// ArrayList<Territory> outside = new ArrayList();
	// outside.addAll(this.territories);
	// outside.removeAll(inside);
	// if (outside.isEmpty()) {
	// return true;
	// }
	// while (!outside.isEmpty()) {
	// for (Territory out : outside) {
	// for (Territory in : inside) {
	// if (((out.getLinks() != null) && (out.getLinks().contains(in)))
	// || ((in.getLinks() != null) && (in.getLinks().contains(out)))) {
	// fringe.add(out);
	// break;
	// }
	// }
	// }
	// if (!fringe.isEmpty()) {
	// inside.addAll(fringe);
	// outside.removeAll(fringe);
	// fringe.clear();
	// } else {
	// return false;
	// }
	// }
	// return true;
	// }
	//

	public void clear() {
		this.mapFilePath = null;
		this.imageFilePath = null;
		this.author = null;
		this.scroll = ScrollOptions.HORIZONTAL;
		this.wrap = false;
		this.warn = true;
		this.continents.clear();
		this.territories.clear();
		this.dirty = false;
	}

	public int compare(Object o1, Object o2) {
		if ((o1 == null) && (o2 == null)) {
			return 0;
		}
		if (o1 == null) {
			return -1;
		}
		if (o2 == null) {
			return 1;
		}
		if (((o1 instanceof Territory)) && ((o2 instanceof Territory))) {
			Territory a = (Territory) o1;
			Territory b = (Territory) o2;
			if (a.getContinent() != b.getContinent()) {
				return compare(a.getContinent(), b.getContinent());
			}
			return a.name.compareTo(b.name);
		}
		return o1.toString().compareTo(o2.toString());
	}

	private ScrollOptions convertScrollOptionsString(String option, ScrollOptions defaultValue) {
		try {
			return ScrollOptions.valueOf(option.toUpperCase());
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public int countTerritories(Continent cont) {
		int total = 0;
		for (Territory ter : this.territories) {
			if (ter.getContinent() == cont) {
				total++;
			}
		}
		return total;
	}

	public void deleteContinent(Continent cont) {
		if (this.continents.contains(cont)) {
			this.continents.remove(cont);
			ArrayList<Territory> temp = new ArrayList();
			for (Territory ter : this.territories) {
				if (ter.getContinent() == cont) {
					temp.add(ter);
				}
			}
			for (Territory ter : temp) {
				deleteTerritory(ter);
			}
			this.dirty = true;
		}
	}

	public void deleteTerritory(Territory ter) {
		if (this.territories.contains(ter)) {
			this.territories.remove(ter);
			// for (Territory t : this.territories) {
			// t.removeReferences(ter);
			// }
			ArrayList<String> linkNames = ter.getLinkNames();
			if (linkNames.size() > 0) {
				for (String name : linkNames) {
					Territory neighbour = findTerritory(name);
					if (neighbour != null) {
						neighbour.getLinkNames().remove(ter.getName());
						buildTerritoryLinks(neighbour);
					}
				}
			}
		}

		this.dirty = true;
	}

	// Territory findClosestTerritory(int x, int y, int max) {
	// Territory close = null;
	// double closeDist = max + 1;
	// for (Territory ter : this.territories) {
	// double d = Point2D.distance(x, y, ter.getCenterX(), ter.getCenterY());
	// if (d < closeDist) {
	// close = ter;
	// closeDist = d;
	// }
	// }
	// return close;
	// }

	public Continent findContinent(String name) {
		for (Continent cont : this.continents) {
			if (name.equalsIgnoreCase(cont.getName())) {
				return cont;
			}
		}
		return null;
	}

	public int findContinentIndex(String name) {
		for (int i = 0; i < this.continents.size(); i++) {
			Continent cont = (Continent) this.continents.get(i);
			if (name.equalsIgnoreCase(cont.getName())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Makes the pointer of LineNumberReader points to the target section.
	 * 
	 * @param in
	 * @param section
	 * @throws IOException
	 */
	public void findSection(LineNumberReader in, String section) throws IOException {
		String head = "[" + section + "]";
		String line;
		do {
			line = in.readLine();
			if (line == null) {
				throw new EOFException("EOF encountered before section " + head + " found");
			}
		} while (!line.equalsIgnoreCase(head));
	}

	public Territory findTerritory(String name) {
		for (Territory ter : this.territories) {
			if (name.equalsIgnoreCase(ter.name)) {
				return ter;
			}
		}
		return null;
	}

	public int findTerritoryIndex(String name) {
		for (int i = 0; i < this.territories.size(); i++) {
			Territory ter = (Territory) this.territories.get(i);
			if (name.equalsIgnoreCase(ter.name)) {
				return i;
			}
		}
		return -1;
	}

	public final String getAuthor() {
		return this.author;
	}

	public HashSet<Territory> getContinentTerritories(Continent cont) {
		HashSet<Territory> ters = new HashSet();
		for (Territory ter : this.territories) {
			if (ter.getContinent() == cont) {
				ters.add(ter);
			}
		}
		return ters;
	}

	public String getImageFileName() {
		if (this.imageFilePath == null) {
			return "";
		}
		return new File(this.imageFilePath).getName();
	}

	public String getImageFilePath() {
		return this.imageFilePath;
	}

	public File getMapDirectory() {
		if (this.mapFilePath == null) {
			return null;
		}
		return new File(this.mapFilePath).getParentFile();
	}

	public String getMapFilePath() {
		return this.mapFilePath;
	}

	public String getMapName() {
		if (this.mapFilePath == null) {
			return "Untitled Map";
		}
		return new File(this.mapFilePath).getName();
	}

	// public int getNextUnusedNameNum(String prefix, int startNum) {
	// String name = null;
	// startNum--;
	// do {
	// startNum++;
	// name = prefix + startNum;
	// } while (findTerritory(name) != null);
	// return startNum;
	// }

	public String getSaveImageFilePath() {
		if (this.imageFilePath == null) {
			return "";
		}
		if (isDisparateImageFileDirectory()) {
			return this.imageFilePath;
		}
		return getImageFileName();
	}

	public final ScrollOptions getScroll() {
		return this.scroll;
	}

	public boolean hasOneWayLinks() {
		for (Territory ter : this.territories) {
			if (ter.getLinks() != null) {
				for (Territory ter2 : ter.getLinks()) {
					if ((ter2.getLinks() == null) || (!ter2.getLinks().contains(ter))) {
						log.append(ter2.getName() + " has no link with " + ter.getName()
								+ ", please check your map file!" + newline);
						System.out.println(ter.getName() + " has no link with " + ter2.getName());
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isDisparateImageFileDirectory() {
		if (this.imageFilePath == null) {
			return false;
		}
		if (this.mapFilePath == null) {
			return false;
		}
		File mapDir = new File(this.mapFilePath).getParentFile();
		File imgDir = new File(this.imageFilePath).getParentFile();
		return (mapDir != null) && (imgDir != null) && (mapDir.compareTo(imgDir) != 0);
	}

	public final boolean isWarn() {
		return this.warn;
	}

	public final boolean isWrap() {
		return this.wrap;
	}

	public void load(String mapFilePath) throws IOException {
		// logger.fine("Loading map file [" + mapFilePath + "]");
		clear();
		this.mapFilePath = mapFilePath;
		LineNumberReader in = new LineNumberReader(new FileReader(mapFilePath));
		loadMapSection(in);
		loadContinents(in);
		loadTerritories(in);
		this.dirty = false;
		if (!validityCheck()) {
			clear();
		}

		// this.props.put("LastMap", mapFilePath);
		// this.props.put("LastPath", new File(mapFilePath).getParent());
	}

	private void loadContinents(LineNumberReader in) throws IOException {
		this.continents.clear();
		for (;;) {
			String line = in.readLine();
			if (line == null) {
				throw new IOException("[Territories] Section expected; found EOF");
			}
			if (!line.trim().equals("")) {
				if (line.startsWith("[")) {
					if (line.equalsIgnoreCase("[Territories]")) {
						for (Continent c : this.continents) {
							System.out.println(c);
						}
						return;
					}
					throw new IOException("[Territories] Section expected; found " + line);
				}
				int eqloc = line.indexOf("=");
				if (eqloc == -1) {
					throw new IOException("Invalid continent line: " + line);
				}
				String cname = line.substring(0, eqloc).trim();
				int cbonus = StringUtil.parseInt(line.substring(eqloc + 1).trim(), -99999);
				if ((cname.length() < 1) || (cbonus == -99999)) {
					throw new IOException("Invalid continent line: " + line);
				}
				this.continents.add(new Continent(cname, cbonus));
			}
		}

	}

	private void loadMapSection(LineNumberReader in) throws IOException {
		findSection(in, "Map");
		for (;;) {
			String line = in.readLine();
			if (line == null) {
				throw new IOException("[Continents] Section expected; found EOF");
			}
			if (!line.trim().equals("")) {
				if (line.startsWith("[")) {
					if (line.equalsIgnoreCase("[Continents]")) {
						return;
					}
					throw new IOException("[Continents] Section expected; found " + line);
				}
				String[] pair = line.split("=", 2);
				if ((pair != null) && (pair.length == 2)) {
					String prop = pair[0].toLowerCase();
					String val = pair[1];
					if ("image".equals(prop)) {
						if ((val != null) && (val.length() > 0)) {
							this.imageFilePath = (new File(this.mapFilePath).getParent() + "\\" + val);
						}
					} else if ("wrap".equals(prop)) {
						this.wrap = val.equalsIgnoreCase("yes");
					} else if ("scroll".equals(prop)) {
						this.scroll = convertScrollOptionsString(val, ScrollOptions.NONE);
					} else if ("author".equals(prop)) {
						this.author = val;
					} else if ("warn".equals(prop)) {
						this.warn = val.equalsIgnoreCase("yes");
					}
				}
			}
		}
	}

	private void loadTerritories(LineNumberReader in) throws IOException {
		this.territories.clear();
		Territory ter;
		for (;;) {
			String line = in.readLine();
			if (line == null) {
				break;
			}
			if (!line.trim().equals("")) {
				ter = parseTerritoryLine(line);
				this.territories.add(ter);
			}
		}
		for (Territory t : this.territories) {
			buildTerritoryLinks(t);
			// t.setLinkNames(null);
			System.out.println(t);
		}
	}

	public void buildTerritoryLinks(Territory t) {
		if (findTerritory(t.getName()) != null) {
			t.links = new ArrayList<Territory>();
			if (t.getLinkNames().size() > 0) {
				for (String linkName : t.getLinkNames()) {
					Territory link = findTerritory(linkName);
					t.getLinks().add(link);
				}
				System.out.println(t.getName() + "'s link: =============");
				for (Territory neighbour : t.getLinks()) {
					System.out.println(neighbour);
				}
				System.out.println("===================");

			}
		}
	}

	private Territory parseTerritoryLine(String line) throws IOException {
		try {
			StringTokenizer st = new StringTokenizer(line, ",");
			Territory ter = new Territory();
			ter.name = st.nextToken().trim();
			ter.setCenter(Integer.parseInt(st.nextToken().trim()), Integer.parseInt(st.nextToken().trim()));
			ter.setContinent(findContinent(st.nextToken().trim()));
			if ((ter.name == null) || (ter.name.length() < 0)) {
				throw new Exception("name not found");
			}
			if (ter.getContinent() == null) {
				throw new Exception("continent not found");
			}
			if ((ter.getCenterX() == -1) || (ter.getCenterY() == -1)) {
				throw new Exception("invalid coordinates");
			}
			while (st.hasMoreTokens()) {
				ter.getLinkNames().add(st.nextToken().trim());
			}
			return ter;
		} catch (Exception e) {
			throw new IOException("Invalid territory line (" + e + "): " + line);
		}
	}

	// public void renameTerritories() {
	// if (this.continents == null) {
	// return;
	// }
	// for (Continent cont : this.continents) {
	// renameTerritories(cont);
	// }
	// }
	public void updateContinent(String oldName, String newName, int newBonus) {
		Continent continent = findContinent(oldName);
		continent.setName(newName);
		continent.setBonus(newBonus);
	}

	// public void renameTerritories(ArrayList<Territory> al, String prefix, int
	// sequenceStart) {
	// if ((al == null) || (al.isEmpty())) {
	// return;
	// }
	// for (Territory ter : al) {
	// ter.name = ("T#" + rnd.nextLong());
	// }
	// int i = sequenceStart;
	// for (Territory ter : al) {
	// i = getNextUnusedNameNum(prefix, i);
	// ter.name = (prefix + i);
	// i++;
	// }
	// }

	// public void renameTerritories(Continent cont) {
	// if ((this.territories == null) || (cont == null)) {
	// return;
	// }
	// ArrayList<Territory> ters = new ArrayList();
	// for (Territory ter : this.territories) {
	// if (ter.getContinent() == cont) {
	// ters.add(ter);
	// }
	// }
	// renameTerritories(ters, cont.getName() + " ", 1);
	// }

	public void save() throws IOException {
		if (validityCheck()) {
			if (this.mapFilePath != null) {
				save(this.mapFilePath);
			} else {
				throw new IOException("No path specified");
			}
		}
	}

	public void save(PrintWriter out) {
		sortContinentsCollection();
		sortTerritoriesCollection();

		out.println("[Map]");
		out.println("image=" + getSaveImageFilePath());
		out.println("wrap=" + (this.wrap ? "yes" : "no"));
		out.println("scroll=" + (this.scroll == null ? "" : this.scroll.toString().toLowerCase()));
		if (this.author != null) {
			out.println("author=" + this.author);
		}
		out.println("warn=" + (this.warn ? "yes" : "no"));
		out.println();
		out.println("[Continents]");
		if (this.continents != null) {
			for (Continent cont : this.continents) {
				//if (countTerritories(cont) > 0) {
					out.println(cont.getName() + "=" + cont.getBonus());
			//	}
			}
		}
		out.println();
		out.println("[Territories]");
		if (this.territories != null) {
			Continent curCont = null;
			for (Territory ter : this.territories) {
				if (curCont == null) {
					curCont = ter.getContinent();
				} else if (curCont != ter.getContinent()) {
					curCont = ter.getCont();
					out.println();
				}
				out.print(ter.name);
				out.print(',');
				out.print(ter.getCenterX());
				out.print(',');
				out.print(ter.getCenterY());
				out.print(',');
				out.print(ter.getContinent().getName());
				for (String linkName : ter.linkNames) {
					out.print(',');
					out.print(linkName);
				}
				out.println();
			}
		}
	}

	public void save(String path) throws IOException {
		if (validityCheck()) {
			this.mapFilePath = path;

			File f = new File(path);
			FileOutputStream fos = new FileOutputStream(f);
			PrintWriter out = new PrintWriter(fos);
			save(out);
			if (out.checkError()) {
				throw new IOException("An error occurred while attempting to save the map file");
			}
			this.dirty = false;
			// this.props.put("LastMap", path);
			// this.props.put("LastPath", new File(path).getParent());
		}
	}

	// public void scaleAllTerritories(float pctX, float pctY) {
	// for (Territory ter : this.territories) {
	// ter.setCenter(ter.getCenterXFloat() * pctX, ter.getCenterYFloat() *
	// pctY);
	// }
	// this.dirty = true;
	// }

	public final void setAuthor(String author) {
		if (!StringUtil.equal(author, this.author)) {
			this.author = author;
			this.dirty = true;
		}
	}

	public void setContinentName(Continent cont, String name) {
		if ((name != null) && (name.length() > 0)) {
			// this.lastContinentUsed = name;
			cont.setName(name);
			// this.cmm.updateContinentData();
			this.dirty = true;
		}
	}

	public void setImageFilePath(String imageFilePath) {
		if (!StringUtil.equal(this.imageFilePath, imageFilePath)) {
			this.imageFilePath = imageFilePath;
			this.dirty = true;
		}
	}

	public void setMapFilePath(String mapFilePath) {
		this.mapFilePath = mapFilePath;
	}

	public final void setScroll(ScrollOptions scroll) {
		if (this.scroll != scroll) {
			this.scroll = scroll;
			this.dirty = true;
		}
	}

	// public void setTerritoryContinentName(Territory ter, String name) {
	// if ((name != null) && (name.length() > 0)) {
	// // this.lastContinentUsed = name;
	// if (name.equals("<new continent>")) {
	// // if (!this.cmm.disableValueChanged.isHeld()) {
	// // Continent cont = this.cmm.newContinentPrompt();
	// if (cont != null) {
	// addContinent(cont);
	// ter.setContinent(cont);
	// this.cmm.updateContinentData();
	// }
	// }
	// } else {
	// Continent newCont = findContinent(name);
	// if (newCont != ter.getContinent()) {
	// if (newCont != null) {
	// ter.setContinent(newCont);
	// } else {
	// ter.getContinent().setName(name);
	// }
	// this.cmm.updateContinentData();
	// }
	// }
	// this.dirty = true;
	// }
	// }

	// public void setTerritoryName(Territory ter, String name) {
	// if ((name != null) && (name.length() > 0)) {
	// Territory terDup = findTerritory(name);
	// if ((terDup != ter) && (terDup != null)) {
	// this.cmm.popupMessage("Territory name \"" + name + "\" is already in
	// use", "Invalid Rename");
	// } else {
	// ter.name = name;
	// this.dirty = true;
	// }
	// }
	// }

	public final void setWarn(boolean warn) {
		if (warn != this.warn) {
			this.warn = warn;
			this.dirty = true;
		}
	}

	public final void setWrap(boolean wrap) {
		if (wrap != this.wrap) {
			this.wrap = wrap;
			this.dirty = true;
		}
	}

	void sortContinentsCollection() {
		if ((this.continents != null) && (!this.continents.isEmpty())) {
			Collections.sort(this.continents, this);
		}
	}

	void sortTerritoriesCollection() {
		if ((this.territories != null) && (!this.territories.isEmpty())) {
			Collections.sort(this.territories, this);
		}
	}

	public boolean validityCheck() {
		ArrayList<String> probs = new ArrayList();
		// if ((this.territories == null) || (this.territories.isEmpty())) {
		// probs.add("Map contains no territories");
		// }
		// if ((this.continents == null) || (this.continents.isEmpty())) {
		// probs.add("Map contains no continents");
		// }
//		for (Continent cont : this.continents) {
//			if (countTerritories(cont) == 0) {
//				probs.add(cont + "has not be allocated territories!");
//			}
//		}
		if ((this.imageFilePath == null) || (this.imageFilePath.isEmpty())) {
			probs.add("Map image is not specified");
		} else if (getMapDirectory() != null) {
			if (isDisparateImageFileDirectory()) {
				probs.add("Map and image files are not located in the same directory");
			}
		}
		if ((this.warn) && (hasOneWayLinks())) {
			probs.add("");
		}
		if (probs.isEmpty()) {
			return true;
		}

		for (String string : probs) {
			log.append(string + newline);
		}

		return false;
	}

	public static void main(String[] args) {
		ConquestMap map = new ConquestMap();
		try {
			map.load("C:\\Users\\Liang\\Desktop\\test\\Atlantis.map");
			System.out.println("=============================");

			Territory t = new Territory();
			t.setName("China");
			t.setCenter(100, 100);
			t.setContinent(map.findContinent("Kala"));
			t.setLinkNames(new ArrayList<String>());
			t.getLinkNames().add("Jer");
			t.getLinkNames().add("Rove");
			t.getLinkNames().add("Ssag");
			map.addTerritory(t);
			map.buildTerritoryLinks(t);

			// Territory t = map.findTerritory("Forgoth");
			// map.deleteTerritory(t);
			for (Territory tt : map.territories) {
				System.out.println(tt);
			}
			map.save();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
