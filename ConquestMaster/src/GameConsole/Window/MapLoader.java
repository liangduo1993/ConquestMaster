package GameConsole.Window;

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

import GameConsole.World.Continent;
import GameConsole.World.Country;
import GameConsole.World.World;
import MapEditor.Util.StringUtil;

public class MapLoader {
	public static enum ScrollOptions {
		HORIZONTAL, VERTICAL, NONE;
	}

	private final String newline = "\n";
	private String mapFilePath;
	private String imageFilePath;
	private ScrollOptions scroll;
	private String author;
	private boolean wrap;
	private boolean warn;
	public ArrayList<Continent> continents = new ArrayList<>();
	public ArrayList<Country> countries = new ArrayList<>();

	public MapLoader() {
		clear();
	}

	public void setWorld(World world){
	  System.out.println(this.continents.size());
	  for(Continent continent : this.continents){
	     world.addContinent(continent);
	  }
	}
	
	// public boolean addContinent(Continent cont) {
	// if (findContinent(cont.getName()) == null) {
	// this.continents.add(cont);
	// return true;
	// }
	// return false;
	// }
	//
	// public void addCountry(Country ter) {
	// if (findCountry(ter.getName()) == null) {
	// this.countries.add(ter);
	//
	// ArrayList<String> linkNames = ter.getLinkNames();
	// if (linkNames.size() > 0) {
	// for (String name : linkNames) {
	// Country neighbour = findCountry(name);
	// if (neighbour != null) {
	// neighbour.getLinkNames().add(ter.getName());
	// buildCountryName(neighbour);
	// }
	// }
	// }
	// }
	// }

	public void clear() {
		this.mapFilePath = null;
		this.imageFilePath = null;
		this.author = null;
		this.scroll = ScrollOptions.HORIZONTAL;
		this.wrap = false;
		this.warn = true;
		this.continents.clear();
		this.countries.clear();
	}

	private ScrollOptions convertScrollOptionsString(String option, ScrollOptions defaultValue) {
		try {
			return ScrollOptions.valueOf(option.toUpperCase());
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public Continent findContinent(String name) {
		for (Continent cont : this.continents) {
			if (name.equalsIgnoreCase(cont.getName())) {
				return cont;
			}
		}
		return null;
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

	public Country findCountry(String name) {
		for (Country ter : this.countries) {
			if (name.equalsIgnoreCase(ter.getName())) {
				return ter;
			}
		}
		return null;
	}

	public final String getAuthor() {
		return this.author;
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

	public final ScrollOptions getScroll() {
		return this.scroll;
	}

	public final boolean isWarn() {
		return this.warn;
	}

	public final boolean isWrap() {
		return this.wrap;
	}

	public void load(String mapFilePath) throws IOException {
		clear();
		this.mapFilePath = mapFilePath;
		LineNumberReader in = new LineNumberReader(new FileReader(mapFilePath));
		loadMapSection(in);
		loadContinents(in);
		loadCountries(in);
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
//							System.out.println(c.getName());
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
				this.continents.add(new Continent(cbonus, cname));
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

	private void loadCountries(LineNumberReader in) throws IOException {
		this.countries.clear();
		Country ter;
		for (;;) {
			String line = in.readLine();
			if (line == null) {
				break;
			}
			if (!line.trim().equals("")) {
				ter = parseCountryLine(line);
				this.countries.add(ter);
				Continent c = findContinent(ter.getContinent().getName());
				c.addCountry(ter);
			}
		}
		for (Country t : this.countries) {
			buildCountryName(t);
//			System.out.println(t);
		}
	}

	public void buildCountryName(Country t) {
		if (findCountry(t.getName()) != null) {
			// t.setBorderingCountries(new ArrayList<Country>());
			if (t.getLinkNames().size() > 0) {
				for (String linkName : t.getLinkNames()) {
					Country link = findCountry(linkName);
					if (!t.getBorderingCountries().contains(link)) {
						t.addBorderingCountry(link);
					}
				}
			//	System.out.println(t.getName() + "'s link: =============");
//				for (Country neighbour : t.getBorderingCountries()) {
//					System.out.println(neighbour.getName());
//				}
			//	System.out.println("===================");

			}
		}
	}

	private Country parseCountryLine(String line) throws IOException {
		try {
			StringTokenizer st = new StringTokenizer(line, ",");
			Country ter = new Country();
			ter.setName(st.nextToken().trim());
			ter.setXLoc(Integer.parseInt(st.nextToken().trim()));
			ter.setYLoc(Integer.parseInt(st.nextToken().trim()));
			String name = st.nextToken().trim();
			ter.setContinent(findContinent(name));

			// if ((ter.getName() == null) || (ter.getName().length() < 0)) {
			// throw new Exception("name not found");
			// }
			// if (ter.getContinent() == null) {
			// throw new Exception("continent not found");
			// }
			// if ((ter.getXLoc() == -1) || (ter.getYLoc() == -1)) {
			// throw new Exception("invalid coordinates");
			// }
			while (st.hasMoreTokens()) {
				ter.getLinkNames().add(st.nextToken().trim());
			}
			return ter;
		} catch (Exception e) {
			throw new IOException("Invalid territory line (" + e + "): " + line);
		}
	}

	public final void setAuthor(String author) {
		if (!StringUtil.equal(author, this.author)) {
			this.author = author;
		}
	}

	public void setImageFilePath(String imageFilePath) {
		if (!StringUtil.equal(this.imageFilePath, imageFilePath)) {
			this.imageFilePath = imageFilePath;
		}
	}

	public void setMapFilePath(String mapFilePath) {
		this.mapFilePath = mapFilePath;
	}

	public final void setScroll(ScrollOptions scroll) {
		if (this.scroll != scroll) {
			this.scroll = scroll;
		}
	}

	public final void setWarn(boolean warn) {
		if (warn != this.warn) {
			this.warn = warn;
		}
	}

	public final void setWrap(boolean wrap) {
		if (wrap != this.wrap) {
			this.wrap = wrap;
		}
	}

	public static void main(String[] args) {
		MapLoader map = new MapLoader();
		try {
			map.load("C:\\Users\\Liang\\Desktop\\test\\Atlantis.map");
			System.out.println("=============================");

			// Country t = new Country();
			// t.setName("China");
			// t.setXLoc(100);
			// t.setYLoc(100);
			// t.setContinent(map.findContinent("Kala"));
			// t.setLinkNames(new ArrayList<String>());
			// t.getLinkNames().add("Jer");
			// t.getLinkNames().add("Rove");
			// t.getLinkNames().add("Ssag");
			// map.addCountry(t);
			// map.buildCountryName(t);

			// Territory t = map.findTerritory("Forgoth");
			// map.deleteTerritory(t);
			for (Continent tt : map.continents) {
//				System.out.println(tt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
