package GameConsole.Strategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import GameConsole.Core.GameLoader;
import GameConsole.Core.GameState;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;

public class CheaterStrategy extends OriginalStrategy implements Strategy{

	public CheaterStrategy() {
		super();
		this.setName("Cheater");
	}
	
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
			neighbour.getPlayer().removeCountry(neighbour);
			neighbour.setPlayer(this.getPlayer());
			this.getPlayer().addCountry(neighbour);
			neighbour.setTroopNum(1);
//			for(Country c: getPlayer().getCountries()){
//				if(c.getBorderingCountries().contains(neighbour) && c.getTroops().size() > 1){
//					neighbour.addInfrantry(1);
//					c.removeTroops(1);
//					break;
//				}
//			}
		}
		
		
		
		
		
	}

	@Override
	public void reinforce() {
		ArrayList<Country> list = getPlayer().getCountries();
		for (Country c : list) {
			int currTroop = c.getTroopNum();
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
					int currTroop = c.getTroopNum();
					c.addInfrantry(currTroop);
					break;
				}
			}
		}
	}
	
	
	
	public static void main(String[] args) throws Exception {
		GameLoader gl = new GameLoader(null, "C:\\Users\\Liang\\Documents\\13.txt");
		GameState gs = gl.getGameState();
		Player p1 = gs.getAllPlayers().getPlayers().get(0);
		CheaterStrategy s1 = new CheaterStrategy();
		s1.setGameState(gs);
		s1.setPlayer(p1);
		
		p1.setStrategy(s1);
		
		for(Country c: p1.getCountries()){
			System.out.println(c.getName() + ": " + c.getTroopNum());
		}
		System.out.println("======");
		
		p1.reinforce();
		
		for(Country c: p1.getCountries()){
			System.out.println(c.getName() + ": " + c.getTroopNum());
		}		
		System.out.println("======");
		
		p1.fortify();
		for(Country c: p1.getCountries()){
			System.out.println(c.getName() + ": " + c.getTroopNum());
		}	
		System.out.println("======");
		
		p1.attack();
		
		for(Country c: p1.getCountries()){
			System.out.println(c.getName() + ": " + c.getTroopNum());
		}	
		System.out.println("======");
		
		p1.reinforce();
		
		for(Country c: p1.getCountries()){
			System.out.println(c.getName() + ": " + c.getTroopNum());
		}	
		System.out.println("======");
	}
	
	

}
