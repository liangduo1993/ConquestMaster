package GameConsole.Strategy;

import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GameConsole.View.LogPanel;

/**
 * A concrete Strategy that implements human strategy operation
 */
public class HumanStrategy extends OriginalStrategy implements Strategy {

	/**
	 * Method to attack.
	 */
	@Override
	public void attack() {
		LogPanel lp = LogPanel.getInstance();

		JPanel numdice1 = new JPanel();
		JLabel label = new JLabel("Attacker selects how many dice to roll");
		numdice1.add(label);
		DefaultComboBoxModel<String> select1 = new DefaultComboBoxModel<>();
		for (int i = Math.min(getGameState().getCountry1().getTroopNum() - 1, 3); i >= 1; i--) {
			select1.addElement(Integer.toString(i));
		}
		JComboBox<String> list1 = new JComboBox<>(select1);
		numdice1.add(list1);
		int message1 = -1;
		while (message1 != JOptionPane.OK_OPTION) {
			message1 = JOptionPane.showConfirmDialog(null, numdice1, "Number of Dices", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
		}
		int decision1 = Integer.parseInt(list1.getSelectedItem().toString());
		lp.addLog("Attacker chooses " + decision1 + " dices!");

		numdice1.remove(label);
		numdice1.remove(list1);

		numdice1.add(new JLabel("Defender selects how many dice to roll"));
		DefaultComboBoxModel<String> select2 = new DefaultComboBoxModel<>();
		for (int i = 1; i <= Math.min(getGameState().getCountry2().getTroopNum(), 2); i++) {
			select2.addElement(Integer.toString(i));
		}
		JComboBox<String> list2 = new JComboBox<>(select2);
		numdice1.add(list2);
		int message2 = -1;
		while (message2 != JOptionPane.OK_OPTION) {
			message2 = JOptionPane.showConfirmDialog(null, numdice1, "Number of Dices", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
		}
		int decision2 = Integer.parseInt(list2.getSelectedItem().toString());
		lp.addLog("Defender chooses " + decision2 + " dices!");

		Map<String, Object> result = getGameState().getCurrPlayer().originalAttack(getGameState().getCountry1(), getGameState().getCountry2(),
				decision1, decision2);
		JOptionPane.showConfirmDialog(null,(String)result.get("dice"), "Dice Result",JOptionPane.OK_OPTION);
		
		if ((Boolean)result.get("result")) {
			int moveNum = 0;
			JPanel numPanel = new JPanel();
			numPanel.add(new JLabel("Congrats you conquered " + getGameState().getCountry2().getName() + " with "
					+ getGameState().getCountry1().getName() + ". How many troops would you like to add?"));
			lp.addLog("Congrats " + getGameState().getCurrPlayer().getName() + " conquered "
					+ getGameState().getCountry2().getName() + " with " + getGameState().getCountry1().getName());
			DefaultComboBoxModel<String> selection = new DefaultComboBoxModel<String>();
			for (int i = getGameState().getCountry1().getTroopNum() - 1; i >= 1; i--) {
				selection.addElement(Integer.toString(i));
			}
			JComboBox<String> comboBox = new JComboBox<String>(selection);
			numPanel.add(comboBox);

			int result1 = -1;
			while (result1 != JOptionPane.OK_OPTION) {
				result1 = JOptionPane.showConfirmDialog(null, numPanel, "Number of Troops", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
			}
			moveNum = Integer.parseInt(comboBox.getSelectedItem().toString());
			getGameState().getCountry2().addInfrantry(moveNum);
			getGameState().getCountry1().removeTroops(moveNum);
			lp.addLog(getGameState().getCurrPlayer().getName() + " leaves " + moveNum + " troops!");

		}

	}

	/**
	 * Method to reinforce.
	 */
	@Override
	public void reinforce() {
		this.getPlayer().addInfantry(this.getGameState().getCurrClick());
	}

	/**
	 * Method to fortify
	 */
	@Override
	public void fortify() {
		JPanel numPanel = new JPanel();
		numPanel.add(new JLabel("Select how many troops to add"));
		DefaultComboBoxModel<String> selection = new DefaultComboBoxModel<String>();
		for (int i = 1; i < getGameState().getCountry1().getTroopNum(); i++) {
			selection.addElement(Integer.toString(i));
		}
		JComboBox<String> comboBox = new JComboBox<String>(selection);
		numPanel.add(comboBox);
		int result = JOptionPane.showConfirmDialog(null, numPanel, "Number of Troops", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			getGameState().getCurrPlayer().moveTroops(getGameState().getCountry1(), getGameState().getCountry2(),
					Integer.parseInt(comboBox.getSelectedItem().toString()));
			getGameState().getCurrPlayer().setHasMoved(true);
			getGameState().updateCountryLabels();
			getGameState().setCountry1(null);
			getGameState().setCountry2(null);
			getGameState().getWindow().country1.setText((String) null);
			getGameState().getWindow().country2.setText((String) null);
		} else {
			JOptionPane.showMessageDialog(null, "Move was cancelled.");
			LogPanel.getInstance().addLog("Move was cancelled.");
			getGameState().setCountry1(null);
			getGameState().setCountry2(null);
			getGameState().getWindow().country1.setText((String) null);
			getGameState().getWindow().country2.setText((String) null);
		}

	}

}
