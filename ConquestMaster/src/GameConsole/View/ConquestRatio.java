package GameConsole.View;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import GameConsole.Core.GameState;
import GameConsole.Model.Player.Player;

/**
 * 
 * this class is responsible for displaying conquestRatio of a player. Based on
 * one player own countries/total countries.
 *
 */
public class ConquestRatio extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	private GameState state;
	private int playerNum;
	private ArrayList<JLabel> lable = new ArrayList<>();

	/**
	 * constructor method, conquestRatio extends JPanel and input parameters.
	 */
	public ConquestRatio(GameState state) {
		this.state = state;
		playerNum = state.getAllPlayers().getPlayers().size();
		this.setLayout(null);
		this.setBounds(0, 0, state.getWorld().getDeck().size() * 10, 25);
		System.out.println(this.getBounds());
		int loc = 0;
		for (int i = 0; i < playerNum; i++) {
			Player cur = state.getAllPlayers().getPlayers().get(i);
			int oldLoc = loc;
			loc += 10 * cur.getCountries().size();
			JLabel lb1 = new JLabel(cur.getCountries().size() + "");
			lb1.setBounds((loc + oldLoc) / 2, 0, 25, 25);
			lb1.setBackground(cur.getColor());
			lb1.setOpaque(true);
			this.add(lb1);
			lable.add(lb1);

		}

	}

	/**
	 * this method is using to override paintComponent method and draw
	 * rectangles to show the ratio.
	 */
	public void paintComponent(Graphics g) {
		Color color = g.getColor();
		int loc = 0;
		for (int i = 0; i < playerNum; i++) {
			Player cur = state.getAllPlayers().getPlayers().get(i);
			g.setColor(cur.getColor());
			g.fillRect(loc, 0, 10 * cur.getCountries().size(), 25);
			loc += 10 * cur.getCountries().size();
		}
		g.setColor(color);
	}

	@Override
	public void update(Observable o, Object arg) {
		int loc = 0;
		for (int i = 0; i < playerNum; i++) {
			Player cur = state.getAllPlayers().getPlayers().get(i);
			int oldLoc = loc;
			loc += 10 * cur.getCountries().size();
			JLabel lb1 = lable.get(i);
			lb1.setText("" + cur.getCountries().size());
			lb1.setBounds((loc + oldLoc) / 2, 0, 25, 25);
			System.out.println("//=====================");
			System.out.println("current loctions:" + lb1.getBounds());
		}
		this.repaint();
	}

}
