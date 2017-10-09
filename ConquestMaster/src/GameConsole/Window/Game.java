package GameConsole.Window;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import GameConsole.World.Country;
import GameConsole.World.World;

public class Game {

	private JPanel frame;
	private ArrayList<CountryButton> buttons = new ArrayList<CountryButton>(42);
	private World world;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//GameState gamestate = new GameState();
					JFrame frame1 = new JFrame();
					frame1.setResizable(false);
					frame1.setTitle("Risk - The Game of Global Domination");
					frame1.getContentPane().setBackground(Color.LIGHT_GRAY);
					frame1.setBounds(100, 100, 1200, 900);
					frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame1.setVisible(true);
					frame1.getContentPane().setLayout(new CardLayout(0, 0));
					
					JPanel thing = new JPanel();
					thing.setLayout(null);
					//new Game(thing, gamestate.getWorld());
					frame1.getContentPane().add(thing);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		//MapLoader mapLoader = new MapLoader();
		//mapLoader.setWorld(world);
		int countryNum = mapLoader.countries.size();
	//	System.out.println(mapLoader.countries.contains(mapLoader.continents.get(0).getCountries().get(0)));
		
		
		for(int i = 0; i < countryNum; i++) {
			BufferedImage buttonImage = ImageIO.read(new File(mapLoader.getImageFilePath()));
			String buttonName = mapLoader.countries.get(i).getName();
			Country country = mapLoader.countries.get(i);
			CountryButton countryButton = new CountryButton(buttonImage, buttonName, country);
			int x = country.getXLoc();
			int y = country.getYLoc();
			countryButton.setDoubleBounds(x - 10, y - 10, 20, 20);
			map.add(countryButton.getLabel());
			map.add(countryButton.b);
			buttons.add(countryButton);
			country.setButton(countryButton);
		}
				
	}
	
	public JPanel getFrame(){
		return frame;
	}
	public ArrayList<CountryButton> getButtons(){
		return buttons;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	
}
