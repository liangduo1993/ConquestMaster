package GameConsole.Strategy;

import java.util.List;
import java.util.Map;

import GameConsole.Model.Army.AbstractTroop;
import GameConsole.Model.Domain.Country;

public class RandomStrategy extends OriginalStrategy implements Strategy {

	@Override
	public void attack() {
		int randomAttackTime = (int) (Math.random() * 50);
		for (;;) {
			Country country1 = getRandCountry();
			Country country2 = new Country();
			for (Country neighbour : country1.getBorderingCountries()) {
				if (neighbour.getPlayer() == this.getPlayer()) {
					country2 = neighbour;
					break;
				}
			}

			if (country1.getTroops().size() <= 1 || country2.getPlayer() == null) {
				continue;
			}

			int decision1 = country1.getTroops().size() >= 3 ? 3 : country1.getTroops().size();
			int decision2 = 1;

			Map<String, Object> result = getGameState().getCurrPlayer().originalAttack(country1, country2, decision1,
					decision2);
			boolean flag = (boolean) result.get("result");
			if (flag) {
				int moveNum = getRandTroops(country1.getTroops());
				country2.addInfrantry(moveNum);
				country1.removeTroops(moveNum);
			}
			randomAttackTime--;
			
			if(!getPlayer().checkIfCanAttack() || randomAttackTime == 0)
				break;
		}
	}

	@Override
	public void reinforce() {
		this.getPlayer().addInfantry(this.getRandCountry());
	}

	@Override
	public void fortify() {
		for (;;) {
			Country country1 = getRandCountry();
			Country country2 = new Country();
			for (Country neighbour : country1.getBorderingCountries()) {
				if (neighbour.getPlayer() == this.getPlayer()) {
					country2 = neighbour;
					break;
				}
			}

			int moveTroopNum = getRandTroops(country2.getTroops());
			getGameState().getCurrPlayer().moveTroops(country2, country1, moveTroopNum);

			if (country2.getPlayer() != null) {
				break;
			}
		}
	}

	private Country getRandCountry() {
		List<Country> countrys = this.getPlayer().getCountries();
		int rand = (int) (Math.random() * countrys.size());
		return countrys.get(rand);
	}

	private int getRandTroops(List<AbstractTroop> troops) {
		if (troops.size() == 1)
			return 0;
		else
			return ((int) (Math.random() * (troops.size() - 1))) + 1;
	}
}
