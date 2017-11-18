package GameConsole.Strategy;

import java.util.List;
import java.util.Map;

import GameConsole.Model.Army.AbstractTroop;
import GameConsole.Model.Domain.Country;

public class RandomStrategy extends OriginalStrategy implements Strategy{

	@Override
	public void attack() {
		Country country1 = getGameState().getCountry1();
		Country country2 = getGameState().getCountry2();
		int decision1 = getRandTroops(country1.getTroops());
		int decision2 = getRandTroops(country2.getTroops());
		Map<String, Object> result = getGameState().getCurrPlayer().originalAttack(country1, country2,
				decision1, decision2);
		boolean flag = (boolean) result.get("result");
		if(flag){
			//占领国家后移动军队的数量
			int moveNum = getRandTroops(country1.getTroops());
			country2.addInfrantry(moveNum);
			country1.removeTroops(moveNum);
		}
	}

	@Override
	public void reinforce() {
		this.getPlayer().addInfantry(this.getRandCountry());
	}

	@Override
	public void fortify() {
		int moveTroopNum = getRandTroops(getGameState().getCountry1().getTroops());
		getGameState().getCurrPlayer().moveTroops(getGameState().getCountry1(), getGameState().getCountry2(),
				moveTroopNum);
	}
	
	private Country getRandCountry(){
		List<Country> countrys = this.getPlayer().getCountries();
		int rand = (int) (Math.random()*countrys.size());
		return countrys.get(rand);
	}
	
	private int getRandTroops(List<AbstractTroop> troops){
		return (int) (Math.random()*troops.size());
	}
}
