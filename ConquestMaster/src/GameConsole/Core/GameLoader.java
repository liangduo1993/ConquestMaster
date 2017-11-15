package GameConsole.Core;

import java.awt.Color;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import GameConsole.Model.Domain.Card;
import GameConsole.Model.Domain.Continent;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;
import GameConsole.View.MapDisplayer;

public class GameLoader {
	private GameState game;
	private String path;
	private int playerNum;
	private String currName;

	public GameLoader(WindowMain win, String gamePath) throws Exception {
		LineNumberReader in = new LineNumberReader(new FileReader(gamePath));

		findSection(in, "Map");
		String line = in.readLine();
		String[] pair = line.split("=", 2);
		if (pair[0].equals("mapFilePath")) {
			this.path = pair[1];
		}
		this.game = new GameState(win, path);

		loadGameState(in);
		loadPlayers(in);
		loadCountries(in);

		System.out.println("==============");
		System.out.println(game.getAllPlayers().getPlayers().size());
		for (Player p : game.getAllPlayers().getPlayers()) {
			System.out.println(p.getName());
		}
		System.out.println("==============");

		for (Player p : game.getAllPlayers().getPlayers()) {
			if (p.getName().equals(currName))
				game.setCurrPlayer(p);
		}
		
	}

	public GameState getGameState() {
		return this.game;
	}

	private void loadGameState(LineNumberReader in) throws IOException {
		findSection(in, "GameState");
		for (;;) {
			String line = in.readLine();
			if (line.equalsIgnoreCase("[Players]")) {
				return;
			}

			String[] pair = line.split("=", 2);
			if (pair.length == 2) {
				String prop = pair[0];
				String val = pair[1];
				if ("firstRound".equals(prop)) {
					game.setFirstRound(Integer.parseInt(val));
				} else if ("currPlayer".equals(prop)) {
					currName = val;
				} else if ("currPhase".equals(prop)) {
					game.setCurrPhase(Integer.parseInt(val));
				} else if ("playerNum".equals(prop)) {
					this.playerNum = Integer.parseInt(val);
				}
			}
		}
	}

	private void loadPlayers(LineNumberReader in) throws IOException {
			Player p1 = new Player("", Color.cyan, game);
			game.addPlayer(p1);
			if (playerNum >= 2) {
				Player p2 = new Player("", Color.magenta, game);
				game.addPlayer(p2);
			}
			if (playerNum >= 3) {
				Player p3 = new Player("", Color.green, game);
				game.addPlayer(p3);
			}
			if (playerNum >= 4) {
				Player p4 = new Player("", Color.blue, game);
				game.addPlayer(p4);
			}
			if (playerNum >= 5) {
				Player p5 = new Player("", Color.red, game);
				game.addPlayer(p5);
			}

		//findSection(in, "Players");
		for (int index = 0; index < this.playerNum; index++) {
			Player p = game.getAllPlayers().getPlayers().get(index);
			//findSection(in, "Player" + (index + 1));
			for (;;) {
				String line = in.readLine();
//				if (line.equalsIgnoreCase("[Countries]")) {
//					return;
//				}
//				if (line.startsWith("Player")) {
//					break;
//				}
				if (line.equals("")) {
					break;
				}
				
				String[] pair = line.split("=", 2);
				if (pair.length == 2) {
					String prop = pair[0];
					String val = pair[1];
					if ("name".equals(prop)) {
						p.setName(val);
					} else if ("isConquered".equals(prop)) {
						if ("true".equals(val))
							p.isConquered(true);
						if ("false".equals(val))
							p.isConquered(false);
					} else if ("hasMoved".equals(prop)) {
						if ("true".equals(val))
							p.setHasMoved(true);
						if ("false".equals(val))
							p.setHasMoved(false);
					} else if ("initTroop".equals(prop)) {
						p.setInitTroop(Integer.parseInt(val));
					} else if ("onhand".equals(prop)) {
						String[] onhand = val.split(" ");
						if (!val.trim().equals("") && onhand.length != 0) {
							for (int i = 0; i < onhand.length; i++) {
								p.getOnHand().add(new Card(Integer.parseInt(onhand[i])));
							}
						}
					} else if ("countries".equals(prop)) {
						String[] countries = val.split(",");
						if (countries.length != 0) {
							for (int i = 0; i < countries.length; i++) {
								
								for(Continent con: game.getWorld().getContinents()){
									for(Country cou: con.getCountries()){
										if(cou.getName().equals(countries[i])){
											p.addCountry(cou);
										}
									}
								}
								
								
//								p.addCountry(game.getWorld().getMapLoader().findCountry(countries[i]));
							
							
							
							}
						}
					}

				}
			}
		}
	}

	private void loadCountries(LineNumberReader in) throws IOException {
		 findSection(in, "Countries");
		for (;;) {
			String line = in.readLine();
			if (line == null) {
				return;
			}
			String[] lineInfo = line.split(",", 3);
			if (lineInfo.length == 3) {
				String cName = lineInfo[0];
				String pName = lineInfo[1];
				int tNum = Integer.parseInt(lineInfo[2]);

				for (Player p : game.getAllPlayers().getPlayers()) {
					if (p.getName().equals(pName)) {
						for (Continent con : game.getWorld().getContinents()) {
							for (Country cou : con.getCountries()) {
								if (cou.getName().equals(cName)) {
									cou.setPlayer(p);
									cou.addInfrantry(tNum);
								}
							}

						}

					}
				}

			}
		}
	}

	/**
	 * Makes the pointer of LineNumberReader points to the target section.
	 * 
	 * @param in
	 *            The input LineNumberReader.
	 * @param section
	 *            The section among "Map", "Continent" and "Territory".
	 * @throws IOException
	 */
	private int findSection(LineNumberReader in, String section) throws IOException {
		String head = "[" + section + "]";
		String line;
		do {
			line = in.readLine();
			if (line == null) {
				throw new EOFException("EOF encountered before section " + head + " found");
			}
		} while (!line.equalsIgnoreCase(head));
		return in.getLineNumber();
	}

}
