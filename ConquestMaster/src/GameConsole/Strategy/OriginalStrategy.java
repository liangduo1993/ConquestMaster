package GameConsole.Strategy;

import GameConsole.Core.GameState;
import GameConsole.Model.Player.Player;

public class OriginalStrategy implements Strategy{
	private GameState gameState;
	private Player player;
	private String name;
	
	
	public void attack() {
		
	}
	public void reinforce() {
		
	}
	public void fortify() {
		
	}
	public GameState getGameState() {
		return gameState;
	}
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
