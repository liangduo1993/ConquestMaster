package GameConsole.Strategy;

import java.util.List;
import java.util.Map;

import GameConsole.Model.Domain.Country;

public class BenevolentStrategy extends OriginalStrategy implements Strategy{

	@Override
	public void attack() {
		Country country1 = getGameState().getCountry1();
		Country country2 = getGameState().getCountry2();
		int decision1 = 1;
		int decision2 = country2.getTroops().size();
		Map<String, Object> result = getGameState().getCurrPlayer().originalAttack(country1, country2,
				decision1, decision2);
		boolean flag = (boolean) result.get("result");
		if(flag){
			//占领国家后移动军队的数量
			int moveNum = country1.getTroops().size()-1;
			country2.addInfrantry(moveNum);
			country1.removeTroops(moveNum);
		}
	}

	@Override
	public void reinforce() {
		this.getPlayer().addInfantry(this.getWeakestCountry());
	}

	@Override
	public void fortify() {
		int moveTroopNum = 1;
		getGameState().getCurrPlayer().moveTroops(getGameState().getCountry1(), getGameState().getCountry2(),
				moveTroopNum);
	}
	
	private Country getWeakestCountry(){
		List<Country> countrys = this.getPlayer().getCountries();
		Country weakestCountry = countrys.get(0);
		for(Country country:countrys){
			if(country.getTroops().size() < weakestCountry.getTroops().size()){
				weakestCountry = country;
			}
		}
		return weakestCountry;
	}

}
