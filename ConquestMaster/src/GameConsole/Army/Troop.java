package GameConsole.Army;

import GameConsole.Player.Player;
import GameConsole.World.Country;

/**
 * 
 * This class is the abstract class of troop, which defines some members and functions.
 *
 */
public abstract class Troop {
	protected Player player;
	protected String color;
	protected Country country;
	
	public abstract void die();
}
