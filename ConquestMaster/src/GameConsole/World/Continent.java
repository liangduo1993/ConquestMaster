package gameConsole.world;


import java.util.ArrayList;
		
/**
 * 
 * This class create methods to add/remove countries to Continents, get/set Continents bonus and so on
 * the toString method lists the countries on the continent.
 *
 */
public class Continent {
	private ArrayList<Country> countries = new ArrayList<Country>();
	private int bonus;
	private String color;
	private String name;
	private World world;

	public Continent(String color, String name, int bonus, World world){
		this.bonus = bonus;
		this.color = color;
		this.name = name;
		this.world = world;
	}
	
	/**
	 * constructor method with incoming parameters.
	 * 
	 * @param bonus a continent's bonus after conquest it
	 * @param name continent name with String type
	 * 
	 */
	public Continent(int bonus, String name) {
		super();
		this.bonus = bonus;
		this.name = name;
	}



	public String toString() {
		String retString = "";
		for(Country c: this.countries) {
			retString += c.getName() + " ";
		}
		return retString;
	}
	/**
	 * add a country to Continent
	 * @param c the country was added
	 */
	public void addCountry(Country c){
		c.setContinent(this);
		countries.add(c);
	}
	/**
	 * remove a country from Continent
	 * @param c the country was removed
	 */
	public void removeCountry(Country c){
		for(Country A : countries){
			if(A.equals(c)){
				countries.remove(A);
				break;
			}
		}
	}
	public ArrayList<Country> getCountries(){
		return countries;
	}
	public void setBonus(int bonus){
		this.bonus = bonus;
	}
	public int getBonus(){
		return bonus;
	}
	public void setColor(String col){
		color = col;
	}
	public String getColor(){
		return color;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setWorld(World w){
		world = w;
	}
	public World getWorld(){
		return world;
	}
	/*public player checkIfRuled(){
		player placeHolder = new player();
		return placeHolder;
	}*/
}
