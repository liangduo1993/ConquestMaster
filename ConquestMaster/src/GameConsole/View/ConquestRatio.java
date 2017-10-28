package GameConsole.View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
		this.setLayout(new GridLayout(1, 1));
		this.setBounds(0, 0, 500, 25);

		for (int i = 0; i < playerNum; i++) {
			Player cur = state.getAllPlayers().getPlayers().get(i);

			JLabel lb1 = new JLabel(cur.getCountries().size() + "");
			lb1.setHorizontalAlignment(SwingConstants.CENTER);

			lb1.setSize(10 * cur.getCountries().size(), 25);

			lb1.setBackground(cur.getColor());
			lb1.setOpaque(true);
			this.add(lb1);
			lable.add(lb1);

		}

	}

	// /**
	// * this method is using to override paintComponent method and draw
	// rectangles to show the ratio.
	// */
	// public void paintComponent(Graphics g) {
	// g.drawRect(0, 0, this.getWidth(), 25);
	// int loc = 0;
	// for(int i = 0; i < playerNum; i++){
	// Player cur = state.getAllPlayers().getPlayers().get(i);
	// g.drawRect(loc, 0, 10 * cur.getCountries().size(), 25);
	// loc += 10 * cur.getCountries().size();
	// }
	//
	// }

	@Override
	public void update(Observable o, Object arg) {
		for (int i = 0; i < playerNum; i++) {
			Player cur = state.getAllPlayers().getPlayers().get(i);
			JLabel lb1 = lable.get(i);
			lb1.setText("" + cur.getCountries().size());
			lb1.setSize(10 * cur.getCountries().size(), 25);
		}
	}

}
