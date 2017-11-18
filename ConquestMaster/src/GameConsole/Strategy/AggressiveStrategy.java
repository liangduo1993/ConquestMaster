package GameConsole.Strategy;

import java.util.List;
import java.util.Map;

import GameConsole.Model.Domain.Country;

public class AggressiveStrategy extends OriginalStrategy implements Strategy{

	@Override
	public void attack() {
		Country country1 = getGameState().getCountry1();
		Country country2 = getGameState().getCountry2();
		int decision1 = country1.getTroops().size();
		int decision2 = 1;
		for(;;){
			Map<String, Object> result = getGameState().getCurrPlayer().originalAttack(country1, country2,
					decision1, decision2);
			boolean flag = (boolean) result.get("result");
			if(flag){
				//占领国家后移动军队的数量
				int moveNum = 1;
				country2.addInfrantry(moveNum);
				country1.removeTroops(moveNum);
				break;
			}else if(country1.getTroops().size() == 0){
				break;
			}
		}
	}

	@Override
	public void reinforce() {
		this.getPlayer().addInfantry(this.getStrongestContry());
	}

	@Override
	public void fortify() {
		int moveTroopNum = 1;
		getGameState().getCurrPlayer().moveTroops(getGameState().getCountry1(), getGameState().getCountry2(),
				moveTroopNum);
	}
	
	private Country getStrongestContry(){
		List<Country> countrys = this.getPlayer().getCountries();
		Country strongestCountry = new Country();
		for(Country country:countrys){
			if(country.getTroops().size() > strongestCountry.getTroops().size()){
				strongestCountry = country;
			}
		}
		return strongestCountry;
	}
}
