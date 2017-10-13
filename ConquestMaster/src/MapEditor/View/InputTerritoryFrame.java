package MapEditor.View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import MapEditor.Core.mainFrame;
import MapEditor.Model.ConquestMap;
import MapEditor.Model.Continent;
import MapEditor.Model.Territory;
import MapEditor.Util.MyStringUtil;

public class InputTerritoryFrame {
	//private TablePanel infoPanel = mainFrame.infoPanel;
	private JFrame frmInputTerritory;
	private JTextField neighbourNames;
	private JTextArea tName, tCenterX, tCenterY;
	private JLabel errMsg = new JLabel();
	// private JList<String> list;
	private JComboBox<Continent> comboBox;
	private JButton confirmBtn, cancelBtn;
	private Territory unchanged;
	private Territory changed;
	private ConquestMap map;
	//private LogPanel log = mainFrame.lp;
	// public ConquestMap map;
	//private List<Continent> contList;

	public InputTerritoryFrame(String unchangedName, ConquestMap map) {
		// this.map = new ConquestMap();
		// try {
		// map.load("C:\\Users\\Liang\\Desktop\\test\\Atlantis.map");
		// System.out.println("=============================");
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//this.contList = map.continents;
		this.map = map;
		this.unchanged = map.findTerritory(unchangedName);
		initialize();
	}

	private void initialize() {
		frmInputTerritory = new JFrame();
		frmInputTerritory.setTitle("Input Territory");
		frmInputTerritory.setBounds(100, 100, 528, 656);
		// frmInputTerritory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInputTerritory.getContentPane().setLayout(null);

		errMsg.setBounds(60, 65, 400, 15);
		errMsg.setForeground(Color.RED);
		frmInputTerritory.getContentPane().add(errMsg);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(60, 90, 54, 15);
		frmInputTerritory.getContentPane().add(lblName);

		tName = new JTextArea();
		tName.setBounds(193, 89, 114, 24);
		frmInputTerritory.getContentPane().add(tName);

		JLabel lblLocation = new JLabel("location\uFF1A");
		lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblLocation.setBounds(38, 210, 94, 15);
		frmInputTerritory.getContentPane().add(lblLocation);

		tCenterX = new JTextArea();
		tCenterX.setBounds(193, 211, 54, 24);
		frmInputTerritory.getContentPane().add(tCenterX);

		JLabel lblNewLabel = new JLabel("Continent:");
		lblNewLabel.setBounds(53, 330, 61, 15);
		frmInputTerritory.getContentPane().add(lblNewLabel);

		// JScrollPane scrollPane = new JScrollPane();
		// scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		// scrollPane.setBounds(193, 329, 128, 24);
		// frmInputTerritory.getContentPane().add(scrollPane);

		// list = new JList<>();
		// list.setModel(new AbstractListModel<String>() {
		// private static final long serialVersionUID = 1L;
		// // String[] values = new String[] { "", "2", "3", "4", "5", "6",
		// // "7", "8" };
		// Object[] values = contList.toArray();
		//
		// public int getSize() {
		// return values.length;
		// }
		//
		// public String getElementAt(int index) {
		// return ((Continent) values[index]).getName();
		// }
		// });
		// list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// scrollPane.setViewportView(list);

		comboBox = new JComboBox<>();
		comboBox.setBounds(193, 329, 128, 24);
		//Continent[] values = map.continents.toArray(new Continent[map.continents.size() + 1]);
		Continent[] values = new Continent[map.continents.size() + 1];
		values[0] = null;
		for(int index = 1; index < values.length; index++){
			values[index] = map.continents.get(index - 1);
		}
		
		comboBox.setModel(new DefaultComboBoxModel<>(values));
		frmInputTerritory.getContentPane().add(comboBox);

		JLabel lblNewLabel_1 = new JLabel(",");
		lblNewLabel_1.setBounds(257, 220, 54, 15);
		frmInputTerritory.getContentPane().add(lblNewLabel_1);

		tCenterY = new JTextArea();
		tCenterY.setBounds(275, 211, 54, 24);
		frmInputTerritory.getContentPane().add(tCenterY);

		JLabel lblNewLabel_2 = new JLabel("Neighbours(split with \",\"):");
		lblNewLabel_2.setForeground(Color.BLACK);
		lblNewLabel_2.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(38, 417, 198, 48);
		frmInputTerritory.getContentPane().add(lblNewLabel_2);

		neighbourNames = new JTextField();
		neighbourNames.setToolTipText("");
		neighbourNames.setBounds(60, 475, 387, 74);
		frmInputTerritory.getContentPane().add(neighbourNames);
		neighbourNames.setColumns(10);

		confirmBtn = new JButton("comfirm");
		confirmBtn.setBounds(60, 584, 93, 23);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errMsg.setText("");
				if (validateInput()) {
					changed = new Territory();
					String name = tName.getText().trim();
					int centerX = Integer.parseInt(tCenterX.getText().trim());
					int centerY = Integer.parseInt(tCenterY.getText().trim());
					String[] linkNames = neighbourNames.getText().trim().split(",");
					Continent c = (Continent) comboBox.getSelectedItem();
					if (linkNames.length > 0) {
						for (String linkName : linkNames) {
							changed.getLinkNames().add(linkName);
						}
					}
					changed.setName(name);
					changed.setCenter(centerX, centerY);
					changed.setContinent(c);

					map.deleteTerritory(unchanged);
					map.addTerritory(changed);
					map.buildTerritoryLinks(changed);
				//	unchanged = changed;
//					for (Territory t : map.territories) {
//						System.out.println(t);
//					}

					// try {
					// map.save();
					// } catch (IOException e1) {
					// e1.printStackTrace();
					// }
					// System.out.println(name);
					// System.out.println(tCenterX.getText().trim());
					// System.out.println(tCenterY.getText().trim());
					// // System.out.println(list.getSelectedValue());
					// System.out.println(neighbourNames.getText().trim());
					//infoPanel.updateTable();
					frmInputTerritory.setVisible(false);
				}
			}

		});
		frmInputTerritory.getContentPane().add(confirmBtn);

		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmInputTerritory.setVisible(false);
			}
		});
		cancelBtn.setBounds(354, 584, 93, 23);

		frmInputTerritory.getContentPane().add(cancelBtn);

		if (unchanged != null) {
			tName.setText(unchanged.getName());
			tCenterX.setText(String.valueOf(unchanged.getCenterX()));
			tCenterY.setText(String.valueOf(unchanged.getCenterY()));
			comboBox.setSelectedItem(unchanged.getContinent());
			if (unchanged.getLinkNames().size() > 0) {
				// StringBuilder sb = new StringBuilder(40);
				// for (String linkName : unchanged.getLinkNames()) {
				// sb.append(linkName).append(",");
				// }
				// sb.delete(sb.length() - 1, sb.length());
				// neighbourNames.setText(sb.toString());
				neighbourNames.setText(MyStringUtil.joinString(
						unchanged.getLinkNames().toArray(new String[unchanged.getLinkNames().size()]), ","));
			}
		}

		frmInputTerritory.setVisible(true);
	}

	public Territory getUnchanged() {
		return unchanged;
	}

	public void setUnchanged(Territory unchanged) {
		this.unchanged = unchanged;
	}

	public Territory getChanged() {
		return changed;
	}

	public void setChanged(Territory changed) {
		this.changed = changed;
	}

	private boolean validateInput() {
		if (!MyStringUtil.hasLength(tName.getText()) || !MyStringUtil.hasLength(tCenterX.getText())
				|| !MyStringUtil.hasLength(tCenterY.getText())) {
			errMsg.setText("Name and Location cannot be empty!");
			return false;
		}

		if ((unchanged != null && !tName.getText().trim().equals(unchanged.getName())
				&& map.findTerritory(tName.getText().trim()) != null)
				|| (unchanged == null && map.findTerritory(tName.getText().trim()) != null)) {
			errMsg.setText("The name is already in use!");
			return false;
		}

		if (!MyStringUtil.isNumeric(tCenterX.getText()) || !MyStringUtil.isNumeric(tCenterY.getText())) {
			errMsg.setText("Please enter the number in (X, Y) location!");
			return false;
		}
		// if (list.isSelectionEmpty()) {
		// errMsg.setText("Please select a continent!");
		// return false;
		// }
		if (neighbourNames.getText().contains(tName.getText())) {
			errMsg.setText("Cannot have a connection with itself!");
			return false;
		}

		if (MyStringUtil.hasLength(neighbourNames.getText())) {
			List<String> neighbourList = Arrays.asList(neighbourNames.getText().split(","));
			for (String name : neighbourList) {
				if (map.findTerritory(name) == null) {
					errMsg.setText("Please enter the correct name of Linked Territory!");
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// ConquestMap map = new ConquestMap();
					// try {
					// map.load("C:\\Users\\Liang\\Desktop\\test\\Atlantis.map");
					// System.out.println("=============================");
					//
					// // Territory t = new Territory();
					// // t.setName("China");
					// // t.setCenter(100, 100);
					// // t.setContinent(map.findContinent("Kala"));
					// // t.setLinkNames(new ArrayList<String>());
					// // t.getLinkNames().add("Jer");
					// // t.getLinkNames().add("Rove");
					// // t.getLinkNames().add("Ssag");
					// // map.addTerritory(t);
					// // map.buildTerritoryLinks(t);
					// //
					// // // Territory t = map.findTerritory("Forgoth");
					// // // map.deleteTerritory(t);
					// // for (Territory tt : map.territories) {
					// // System.out.println(tt);
					// // }
					// // map.save();
					// } catch (IOException e) {
					// e.printStackTrace();
					// }

					InputTerritoryFrame window = new InputTerritoryFrame("", new ConquestMap());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
