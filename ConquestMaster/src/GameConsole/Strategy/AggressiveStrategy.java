package GameConsole.Strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import GameConsole.Core.GameLoader;
import GameConsole.Core.GameState;
import GameConsole.Core.WindowMain;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;

public class AggressiveStrategy extends OriginalStrategy implements Strategy {

	@Override
	public void attack() {
		for (;;) {
			Country strongestC = this.getStrongestContry();
			int decision1 = strongestC.getTroops().size() >= 3 ? 3 : strongestC.getTroops().size();
			int decision2 = 1;
			Country country1 = strongestC;
			Country country2 = new Country();
			for (Country neighbour : strongestC.getBorderingCountries()) {
				if (neighbour.getPlayer() != this.getPlayer()) {
					country2 = neighbour;
					break;
				}
			}
			if (country2.getPlayer() != null) {
				Map<String, Object> result = getGameState().getCurrPlayer().originalAttack(country1, country2,
						decision1, decision2);
				boolean flag = (boolean) result.get("result");
				if (flag) {
					int moveNum = strongestC.getTroops().size() - 1;
					country2.addInfrantry(moveNum);
					country1.removeTroops(moveNum);
					// break;
				}
				if (!getPlayer().checkIfCanAttack()) {
					break;
				}
			}
		}
	}

	@Override
	public void reinforce() {
		int num = getPlayer().getBonusAndChangeCard();
		for (int i = 0; i < num; i++) {
			this.getPlayer().addInfantry(this.getStrongestContry());
		}
	}

	@Override
	public void fortify() {
		getPlayer().giveCards();
		Country strongestC = getStrongestContry();
		for (Country neighbour : strongestC.getBorderingCountries()) {
			if (neighbour.getPlayer() == this.getPlayer()) {
				getGameState().getCurrPlayer().moveTroops(neighbour, strongestC, neighbour.getTroops().size() - 1);
			}
		}
	}

	private Country getStrongestContry() {
		List<Country> countrys = this.getPlayer().getCountries();
		List<Country> canAttackCountries = new ArrayList<>();
		for (Country c : countrys) {
			if (c.getTroops().size() > 1) {
				boolean flag = false;
				for (Country neighbour : c.getBorderingCountries()) {
					if (neighbour.getPlayer() != this.getPlayer())
						flag = true;
				}
				if (flag) {
					canAttackCountries.add(c);
				}
			}
		}

		Country strongestCountry = new Country();
		for (Country country : canAttackCountries) {
			if (country.getTroops().size() > strongestCountry.getTroops().size()) {
				strongestCountry = country;
			}
		}
		return strongestCountry;
	}
	
	public static void main(String[] args) throws Exception {
		GameLoader gl = new GameLoader(null, "C:\\Users\\Liang\\Documents\\13.txt");
		GameState gs = gl.getGameState();
		Player p1 = gs.getAllPlayers().getPlayers().get(0);
		AggressiveStrategy s1 = new AggressiveStrategy();
		s1.setGameState(gs);
		s1.setPlayer(p1);
		
		p1.setStrategy(s1);
		
		for(Country c: p1.getCountries()){
			System.out.println(c.getName() + ": " + c.getTroops().size());
		}
		System.out.println("======");
		
		p1.reinforce();
		
		for(Country c: p1.getCountries()){
			System.out.println(c.getName() + ": " + c.getTroops().size());
		}		
		
		
		
	}
	
	
	
}
