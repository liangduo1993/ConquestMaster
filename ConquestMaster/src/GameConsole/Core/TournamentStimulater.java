package GameConsole.Core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import GameConsole.Model.Player.Player;
import GameConsole.Strategy.AggressiveStrategy;
import GameConsole.Strategy.BenevolentStrategy;
import GameConsole.Strategy.RandomStrategy;

/**
 * This class is for tournament game mode
 */
public class TournamentStimulater {

	private GameState gs;
	private int turn;
	private List<Player> players;

	/**
	 * Constructor of the TournamentStimulater class with coming parameters
	 * @param gs the game state with GameState type
	 * @param players the list of players in the game
	 * @param turn the current turn with int type
	 * @param flag1 if the game start with a UI
	 * @param flag2 if the gamestate needs to be initilized
	 */
	public TournamentStimulater(GameState gs, List<Player> players, int turn, boolean flag1, boolean flag2) {
		this.gs = gs;
		gs.getAllPlayers().setPlayers(players);
		gs.setCurrPlayer(players.get(0));
		this.players = players;
		this.turn = turn;
		if(flag2)
			gs.gameStart(flag1);
	}

	/**
	 * This method is mainly to display the details of the game on the console
	 * @return "draw" String after the turns
	 */
	public String execute() {
		Player tempP = gs.getCurrPlayer();
		while(tempP.getInitTroop() > 0){
			tempP.reinforce();
			gs.setNextPlayer();
			tempP = gs.getCurrPlayer();
		}
//		for (Player p : players) {
//			p.reinforce();
//		}

		gs.setFirstRound(2);

		while (gs.getFirstRound() <= turn) {
			Player currPlayer = gs.getCurrPlayer();
			System.out.println(currPlayer.getName() + " starts!");

			System.out.println("reinforce: " + currPlayer.getBonusAndChangeCard());
			currPlayer.reinforce();
			System.out.println("attack");
			currPlayer.attack();
			System.out.println("fortify");
			currPlayer.fortify();

			List<Player> removeList = new ArrayList<>();
			for (Player p : players) {
				if (p.getCountries().size() == 0) {
					removeList.add(p);
				}
				System.out.println(p.getName() + ":" + p.getCountries().size());
			}
			this.players.removeAll(removeList);

			System.out.println(gs.getFirstRound());

			if (players.size() == 1) {
				return players.get(0).getStrategy().getName();
			}

			if (currPlayer == players.get(players.size() - 1)) {
				gs.setCurrPlayer(players.get(0));
				gs.setFirstRound(gs.getFirstRound() + 1);
			} else {
				gs.setCurrPlayer(players.get(players.indexOf(currPlayer) + 1));
			}

		}

		return "draw";
	}

//	// AggressiveStrategy
//	// BenevolentStrategy
//	// CheaterStrategy
//	// RandomStrategy
//
//	/**
//	 * Launch the application.
//	 * @param args
//	 * @throws Exception
//	 */
//	public static void main(String[] args) throws Exception {
//		StringBuffer sb = new StringBuffer(100);
//		WindowMain win = new WindowMain();
//		for (int i = 0; i < 50; i++) {
//			GameState gs = new GameState( win, "resources/ConquestMaps/Atlantis.map");
//			Player p1 = new Player("p1", Color.magenta, gs, new AggressiveStrategy());
//			Player p2 = new Player("p2", Color.green, gs, new BenevolentStrategy());
//			//Player p3 = new Player("p3", Color.blue, gs, new CheaterStrategy());
//			Player p4 = new Player("p4", Color.red, gs, new RandomStrategy());
//			List<Player> list = new ArrayList<>();
//			list.add(p1);
//			list.add(p2);
//			//list.add(p3);
//			list.add(p4);
//
//			TournamentStimulater gameSt = new TournamentStimulater(gs, list, 30,false);
//			gs.gameStart(false);
//			sb.append(gameSt.execute());
//			sb.append("\r\n");
//
//		}
//		System.out.println("=====================");
//		System.out.println(sb.toString());
//		System.out.println("=====================");
//
//	}

}
