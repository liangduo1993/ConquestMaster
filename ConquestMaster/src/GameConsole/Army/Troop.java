package gameConsole.army;

import gameConsole.player.Player;
import gameConsole.world.Country;

public abstract class Troop {
	protected Player player;
	protected String color;
	protected Country country;
	
	public abstract void die();
}
