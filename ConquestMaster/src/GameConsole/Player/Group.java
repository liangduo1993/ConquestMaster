package gameConsole.player;

import java.util.ArrayList;

import gameConsole.world.World;


/**
 * This class is to store all the players into a list and manage to add players, set and
 * get the world.
 */
public class Group {
	private ArrayList<Player> players = new ArrayList<Player>();
	private World world;
	private ArrayList<Player> place = new ArrayList<Player>();
	private String groupName;

	/**
	 * To get the players list
	 * @return the list contains the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * To set the players list
	 * @param players the players list need to be setted
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	/**
	 * To add a player to the players list
	 * @param p1 the player need to be added
	 */
	public void addPlayer(Player p1) {
		this.players.add(p1);
	}

	/**
	 * To get the world where the players can play in
	 * @return the game's world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * To set the desired world
	 * @param world the world wanted to be setted
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	public ArrayList<Player> getPlace() {
		return place;
	}
	public void setPlace(ArrayList<Player> place) {
		this.place = place;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
