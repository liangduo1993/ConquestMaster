package GameConsole.Army;

import GameConsole.Player.Player;
import GameConsole.World.Country;

public abstract class Troop {
	protected Player player;
	protected String color;
	protected Country country;
	
	public abstract void die();
}
