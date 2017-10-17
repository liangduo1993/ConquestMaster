package GameConsole.Window;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import GameConsole.World.Country;
import GameConsole.World.World;
/**
 *  The main entrance of the game
 *
 */
public class Game {

	private JPanel frame;
	private ArrayList<CountryButton> buttons = new ArrayList<CountryButton>(42);
	private World world;
	
	/**
	 * Create the application.
	 * @throws IOException
	 */
	public Game(JPanel map, World world) throws IOException {
		initialize(map, world);
	}

	
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize(JPanel map, World world) throws IOException {
		frame = map;
		MapLoader mapLoader = world.getMapLoader();
		this.world = world;
		int countryNum = mapLoader.countries.size();
		
		for(int i = 0; i < countryNum; i++) {
			BufferedImage buttonImage = ImageIO.read(new File(mapLoader.getImageFilePath()));
			String buttonName = mapLoader.countries.get(i).getName();
			Country country = mapLoader.countries.get(i);
			CountryButton countryButton = new CountryButton(buttonImage, buttonName, country);
			int x = country.getXLoc();
			int y = country.getYLoc();
			countryButton.setDoubleBounds(x - 10 + 85, y - 10 + 200, 50, 20);
			map.add(countryButton.getLabel());
			map.add(countryButton.b);
			buttons.add(countryButton);
			country.setButton(countryButton);
		}
				
	}

	/**
	 * To get the frame
	 * @return the frame with JPanel type
	 */
	public JPanel getFrame(){
		return frame;
	}

	/**
	 * To get the button list
	 * @return the button list with ArrayList type
	 */
	public ArrayList<CountryButton> getButtons(){
		return buttons;
	}

	/**
	 * To get the world where the players are in
	 * @return the world where the players are in with World type
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * To set the world
	 * @param world the selected world that will be set as the world where the players are in
	 */
	public void setWorld(World world) {
		this.world = world;
	}
	
	
}
