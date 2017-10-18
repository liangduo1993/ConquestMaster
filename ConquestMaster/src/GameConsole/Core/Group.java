package GameConsole.Core;

import java.util.ArrayList;

import GameConsole.Model.Player.Player;

/**
 * This class handles the list of players and manages to perform actions
 * associated with the list of players
 */
public class Group {
	private ArrayList<Player> players = new ArrayList<Player>();
	private World world;
	private ArrayList<Player> place = new ArrayList<Player>();
	private String groupName;

	/**
	 * Method to get the players list
	 * @return the list of players with Arraylist type
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * To set the players list
	 * @param players the players list need to be set
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	/**
	 * To add a player to the players list
	 * @param p1 the palyer need to be added with Player type
	 */
	public void addPlayer(Player p1) {
		this.players.add(p1);
	}

	/**
	 * To get the world
	 * @return the world with World type
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * To set the desired world
	 * @param world the world wanted to be set
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

	/**
	 * To get the group name
	 * @return the group name with String type
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * To set the group name
	 * @param groupName the group name that want to be set with String type
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}