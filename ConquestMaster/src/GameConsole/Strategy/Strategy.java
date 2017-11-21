package GameConsole.Strategy;

/**
 * The classes that implement a concrete strategy should implement this.
 * The Player class uses this to use a concrete strategy.
 */
public interface Strategy {

	/**
	 * Method to attack.
	 */
	void attack();

	/**
	 * Method to reinforce.
	 */
	void reinforce();

	/**
	 * Method to fortify
	 */
	void fortify();

	/**
	 * Method to get game state.
	 * @return game state which is GameState type
	 */
//	GameState getGameState();
//
//	/**
//	 * Method to set game state
//	 * @param gameState the desired set phase of game which is GameState type
//	 */
//	void setGameState(GameState gameState);
//
//	/**
//	 * Method to get player
//	 * @return the player that got which is Player type
//	 */
//	Player getPlayer();
//
//	/**
//	 * Method to set player
//	 * @param player the desired set player which is Player type
//	 */
//	void setPlayer(Player player);
//
	/**
	 * Method to get player's name
	 * @return the player's name which is String type
	 */
	String getName();

	/**
	 * Method to set player's name
	 * @param name the desired name the user wants to set
	 */
	void setName(String name);

}
