package GameConsole.View;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import GameConsole.Core.GameState;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;

public class DomiInfoPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;

	private ArrayList<JScrollPane> tables = new ArrayList<>();
	private int playerNum;

	private GameState state;

	/**
	 * Create the application.
	 */
	public DomiInfoPanel(GameState state) {
		this.state = state;
		playerNum = state.getAllPlayers().getPlayers().size();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 350, 650);

		this.setLayout(null);

		System.out.println("!!!!!!!!!!!" + playerNum);

		for (int i = 0; i < playerNum; i++) {
			JLabel newLabel = new JLabel(state.getAllPlayers().getPlayers().get(i).getName());
			newLabel.setBounds(10, 55 + 150 * i, 54, 15);
			this.add(newLabel);
		}

		for (int i = 0; i < playerNum; i++) {

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(60, 26 + 170 * i, 250, 140);
			this.add(scrollPane);

			Player cur = state.getAllPlayers().getPlayers().get(i);
			ArrayList<Country> countries = cur.getCountries();
			System.out.println(countries);
			Object[][] model = new Object[countries.size()][3];
			for (int row = 0; row < countries.size(); row++) {
				model[row][0] = countries.get(row).getName();
				model[row][1] = countries.get(row).getTroops().size();
				model[row][2] = countries.get(row).getContinent().getName();
			}

			JTable table_11 = new JTable();
			table_11.setModel(new DefaultTableModel(model, new String[] { "CountryName", "TroopNum", "Continent" }) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			scrollPane.setViewportView(table_11);
			tables.add(scrollPane);
		}

		for (JScrollPane jTable : tables) {
			this.add(jTable);

		}

	}

	@Override
	public void update(Observable o, Object arg) {
		for (int i = 0; i < playerNum; i++) {
			Player cur = state.getAllPlayers().getPlayers().get(i);
			ArrayList<Country> countries = cur.getCountries();
			Object[][] model = new Object[countries.size()][3];
			for (int row = 0; row < countries.size(); row++) {
				model[row][0] = countries.get(row).getName();
				model[row][1] = countries.get(row).getTroops().size();
				model[row][2] = countries.get(row).getContinent().getName();
			}
			JScrollPane scrollPane = tables.get(i);
			JTable table_11 = new JTable();
			table_11.setModel(new DefaultTableModel(model, new String[] { "CountryName", "TroopNum", "Continent" }) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			scrollPane.setViewportView(table_11);
		}

	}
}
