package GameConsole.Strategy;

import java.util.List;

import GameConsole.Model.Domain.Country;

public class BenevolentStrategy extends OriginalStrategy implements Strategy {

	@Override
	public void attack() {
	}

	@Override
	public void reinforce() {
		this.getPlayer().addInfantry(this.getWeakestCountry());
	}

	@Override
	public void fortify() {
		for (;;) {
			Country country1 = getWeakestCountry();
			Country country2 = new Country();
			for (Country neighbour : country1.getBorderingCountries()) {
				if (neighbour.getPlayer() == this.getPlayer()) {
					country2 = neighbour;
					break;
				}
			}

			int minNum = country1.getTroops().size();
			int maxNum = country2.getTroops().size();
			
			int moveTroopNum = (maxNum - minNum) / 2;
			getGameState().getCurrPlayer().moveTroops(country2,country1,
					moveTroopNum);
			
			if(country2.getPlayer() != null)
				break;
			
		}
	}

	private Country getWeakestCountry() {
		List<Country> countrys = this.getPlayer().getCountries();
		Country weakestCountry = countrys.get(0);
		for (Country country : countrys) {
			if (country.getTroops().size() < weakestCountry.getTroops().size()) {
				weakestCountry = country;
			}
		}
		return weakestCountry;
	}

}
