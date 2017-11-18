package GameConsole.Strategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import GameConsole.Model.Domain.Country;

public class CheaterStrategy extends OriginalStrategy implements Strategy{

	@Override
	public void attack() {
		Set<Country> neighbours = new HashSet<>();
		for(Country c: getPlayer().getCountries()){
			for(Country neighbour: c.getBorderingCountries()){
				if(neighbour.getPlayer() != this.getPlayer()){
					neighbours.add(neighbour);
				}
			}
		}
		
		
		for (Country neighbour : neighbours) {
			neighbour.setPlayer(this.getPlayer());
			neighbour.getTroops().clear();
//			for(Country c: getPlayer().getCountries()){
//				if(c.getBorderingCountries().contains(neighbour) && c.getTroops().size() > 1){
//					neighbour.addInfrantry(1);
//					c.removeTroops(1);
//					break;
//				}
//			}
			neighbour.addInfrantry(1);
		}
		
		
		
		
		
	}

	@Override
	public void reinforce() {
		ArrayList<Country> list = getPlayer().getCountries();
		for (Country c : list) {
			int currTroop = c.getTroops().size();
			c.addInfrantry(currTroop);
		}
		
	}

	@Override
	public void fortify() {
		getPlayer().giveCards();
		ArrayList<Country> list = getPlayer().getCountries();
		for (Country c : list) {
			for(Country neighbour: c.getBorderingCountries()){
				if(neighbour.getPlayer() != this.getPlayer()){
					int currTroop = c.getTroops().size();
					c.addInfrantry(currTroop);
				}
			}
		}
	}
	

}
