package MapEditor.View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import MapEditor.mainFrame;
import MapEditor.Core.ConquestMap;
import MapEditor.Domain.Continent;
import MapEditor.Domain.Territory;
import MapEditor.Util.MyStringUtil;

public class InputContinentFrame {
	private TablePanel infoPanel = mainFrame.infoPanel;
	private JFrame frmInputcontinent;
	private JTextField tName, tBonus;
	private JButton confirmBtn, cancelBtn;
	private JLabel errMsg = new JLabel();

	private ConquestMap map = mainFrame.map;
	private LogPanel log = mainFrame.lp;
	// public ConquestMap map;
	private Continent unchanged;
	private Continent changed;

	/**
	 * Create the application.
	 */
	public InputContinentFrame(String unchangedName) {
//		this.map = new ConquestMap();
//		try {
//			map.load("C:\\Users\\Liang\\Desktop\\test\\Atlantis.map");
//			System.out.println("=============================");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		this.unchanged = map.findContinent(unchangedName);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInputcontinent = new JFrame();
		frmInputcontinent.setType(Type.UTILITY);
		frmInputcontinent.setTitle("InputContinent");
		frmInputcontinent.setBounds(100, 100, 520, 428);
		// frmInputcontinent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInputcontinent.getContentPane().setLayout(null);

		errMsg.setBounds(94, 60, 400, 15);
		errMsg.setForeground(Color.RED);
		frmInputcontinent.getContentPane().add(errMsg);

		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setBounds(94, 84, 54, 15);
		frmInputcontinent.getContentPane().add(lblNewLabel);

		tName = new JTextField();
		tName.setBounds(189, 81, 118, 21);
		frmInputcontinent.getContentPane().add(tName);
		tName.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Bonus:");
		lblNewLabel_1.setBounds(94, 152, 54, 15);
		frmInputcontinent.getContentPane().add(lblNewLabel_1);

		tBonus = new JTextField();
		tBonus.setBounds(189, 149, 118, 21);
		frmInputcontinent.getContentPane().add(tBonus);
		tBonus.setColumns(10);

		// Confirm Button Event
		confirmBtn = new JButton("Confirm");
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				infoPanel = mainFrame.infoPanel;
				errMsg.setText("");
				if (validateInput()) {
					changed = new Continent();
					String name = tName.getText().trim();
					int bonus = Integer.parseInt(tBonus.getText().trim());
					changed.setName(name);
					changed.setBonus(bonus);

					if (unchanged == null) {
						map.addContinent(changed);
					} else {
						map.updateContinent(unchanged.getName(), name, bonus);
					}

					unchanged = changed;
					for (Continent t : map.continents) {
						System.out.println(t);
					}
					// try {
					// map.save();
					// } catch (IOException e1) {
					// e1.printStackTrace();
					// }
					infoPanel.updateTable();
					frmInputcontinent.setVisible(false);
				}
			}

		});
		confirmBtn.setBounds(71, 302, 93, 23);
		frmInputcontinent.getContentPane().add(confirmBtn);

		// Cancel Button Event exit Window
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmInputcontinent.setVisible(false);
			}
		});
		cancelBtn.setBounds(284, 302, 93, 23);
		frmInputcontinent.getContentPane().add(cancelBtn);

		if (unchanged != null) {
			tName.setText(unchanged.getName());
			tBonus.setText(String.valueOf(unchanged.getBonus()));
		}

		frmInputcontinent.setVisible(true);
	}

	private boolean validateInput() {
		if (!MyStringUtil.isNumeric(tBonus.getText())) {
			errMsg.setText("Bonus must be a Integer!");
			return false;
		}
		if (!MyStringUtil.hasLength(tName.getText()) || !MyStringUtil.hasLength(tBonus.getText())) {
			errMsg.setText("Name and Bonus cannot be empty!");
			return false;
		}

		return true;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputContinentFrame window = new InputContinentFrame("Kala");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
