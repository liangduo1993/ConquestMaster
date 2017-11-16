package GameConsole.Strategy;

import GameConsole.Core.GameState;
import GameConsole.Model.Player.Player;

public interface Strategy {
	
	
	void attack();
	void reinforce();
	void fortify();
	
	GameState getGameState();
	void setGameState(GameState gameState);
	Player getPlayer();
	void setPlayer(Player player);

}
