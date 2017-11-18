package GameConsole.Strategy;

import java.util.List;

import GameConsole.Model.Domain.Country;

public class BenevolentStrategy extends OriginalStrategy implements Strategy {

	public BenevolentStrategy() {
		super();
		this.setName("Benevolent");
	}

	@Override
	public void attack() {
	}

	@Override
	public void reinforce() {
		int num = getPlayer().getBonusAndChangeCard();
		for (int i = 0; i < num; i++) {
			this.getPlayer().addInfantry(this.getWeakestCountry());
		}
	}

	@Override
	public void fortify() {
		getPlayer().giveCards();
		Country country1 = getWeakestCountry();
		Country country2 = new Country();
		for (Country neighbour : country1.getBorderingCountries()) {
			if (neighbour.getPlayer() == this.getPlayer()) {
				country2 = neighbour;
				break;
			}
		}

		if(country2.getPlayer() == null) return;
		
		int minNum = country1.getTroopNum();
		int maxNum = country2.getTroopNum();

		int moveTroopNum = (maxNum - minNum) / 2;
		getGameState().getCurrPlayer().moveTroops(country2, country1, moveTroopNum);

	}

	private Country getWeakestCountry() {
		List<Country> countrys = this.getPlayer().getCountries();
		Country weakestCountry = countrys.get(0);
		for (Country country : countrys) {
			if (country.getTroopNum() < weakestCountry.getTroopNum()) {
				weakestCountry = country;
			}
		}
		return weakestCountry;
	}

}
