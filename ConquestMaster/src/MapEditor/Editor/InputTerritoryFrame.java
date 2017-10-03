package MapEditor.Editor;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import MapEditor.Core.ConquestMap;
import MapEditor.Domain.Continent;
import MapEditor.Domain.Territory;

public class InputTerritoryFrame {

	private JFrame frmInputTerritory;
	private JTextField textField;
	private JTextArea textArea, textArea_1, textArea_2;
	private JLabel errMsg = new JLabel();
	private JList<String> list;
	private JButton btnNewButton, btnNewButton_1;
	private Territory unchanged;
	private Territory changed;
	// private ConquestMap map = MainFrame.map;
	// private LogPanel log = MainFrame.lp;
	public ConquestMap map;
	private List<Continent> contList;

	public InputTerritoryFrame(String unchangedName) {
		this.map = new ConquestMap();
		try {
			map.load("C:\\Users\\Liang\\Desktop\\test\\Atlantis.map");
			System.out.println("=============================");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.contList = map.continents;
		this.unchanged = map.findTerritory(unchangedName);
		initialize();
	}

	private void initialize() {
		frmInputTerritory = new JFrame();
		frmInputTerritory.setTitle("Input Territory");
		frmInputTerritory.setBounds(100, 100, 528, 656);
		frmInputTerritory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInputTerritory.getContentPane().setLayout(null);

		errMsg.setBounds(60, 65, 200, 15);
		errMsg.setForeground(Color.RED);
		frmInputTerritory.getContentPane().add(errMsg);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(60, 90, 54, 15);
		frmInputTerritory.getContentPane().add(lblName);

		textArea = new JTextArea();
		textArea.setBounds(193, 89, 114, 24);
		frmInputTerritory.getContentPane().add(textArea);

		JLabel lblLocation = new JLabel("location\uFF1A");
		lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblLocation.setBounds(38, 210, 94, 15);
		frmInputTerritory.getContentPane().add(lblLocation);

		textArea_1 = new JTextArea();
		textArea_1.setBounds(193, 211, 54, 24);
		frmInputTerritory.getContentPane().add(textArea_1);

		JLabel lblNewLabel = new JLabel("Continent:");
		lblNewLabel.setBounds(53, 330, 61, 15);
		frmInputTerritory.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setBounds(193, 329, 128, 24);
		frmInputTerritory.getContentPane().add(scrollPane);

		list = new JList<>();
		list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			//String[] values = new String[] { "", "2", "3", "4", "5", "6", "7", "8" };
			Object[] values = contList.toArray();
			
			
			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return ((Continent)values[index]).getName();
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);

		JLabel lblNewLabel_1 = new JLabel(",");
		lblNewLabel_1.setBounds(257, 220, 54, 15);
		frmInputTerritory.getContentPane().add(lblNewLabel_1);

		textArea_2 = new JTextArea();
		textArea_2.setBounds(275, 211, 54, 24);
		frmInputTerritory.getContentPane().add(textArea_2);

		JLabel lblNewLabel_2 = new JLabel("Neighbours(split with \",\"):");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(38, 417, 198, 48);
		frmInputTerritory.getContentPane().add(lblNewLabel_2);

		textField = new JTextField();
		textField.setToolTipText("");
		textField.setBounds(60, 475, 387, 74);
		frmInputTerritory.getContentPane().add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("comfirm");
		btnNewButton.setBounds(60, 584, 93, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateInput()) {
					changed = new Territory();
					String name = textArea.getText().trim();
					Float centerX = Float.parseFloat(textArea_1.getText().trim());
					Float centerY = Float.parseFloat(textArea_2.getText().trim());
					String[] linkNames = textField.getText().trim().split(",");
					String continent = contList.get(list.getSelectedIndex()).getName();
					if (linkNames.length > 0) {
						for (String linkName : linkNames) {
							changed.getLinkNames().add(linkName);
						}
					}
					changed.setName(name);
					changed.setCenter(centerX, centerY);
					changed.setContinent(map.findContinent(continent));
					
					map.deleteTerritory(unchanged);
					map.addTerritory(changed);
					map.buildTerritoryLinks(changed);
					unchanged = changed;
					for(Territory t: map.territories){
						System.out.println(t);
					}
					
					try {
						map.save();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println(name);
					System.out.println(textArea_1.getText().trim());
					System.out.println(textArea_2.getText().trim());
					System.out.println(list.getSelectedValue());
					System.out.println(textField.getText().trim());
				}
			}

		});
		frmInputTerritory.getContentPane().add(btnNewButton);

		btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(354, 584, 93, 23);

		frmInputTerritory.getContentPane().add(btnNewButton_1);

		if (unchanged != null) {
			textArea.setText(unchanged.getName());
			textArea_1.setText(String.valueOf(unchanged.getCenterXFloat()));
			textArea_2.setText(String.valueOf(unchanged.getCenterYFloat()));
			if (unchanged.getLinkNames().size() > 0) {
				StringBuilder sb = new StringBuilder(40);
				for (String linkName : unchanged.getLinkNames()) {
					sb.append(linkName).append(",");
				}
				sb.delete(sb.length() - 1, sb.length());
				textField.setText(sb.toString());
			}
		}

		// String[] str_list = {"123","asd","ttg"};
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
		// TODO Auto-generated method stub
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

					InputTerritoryFrame window = new InputTerritoryFrame("Forgoth");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
