package MapEditor.template;

import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import MapEditor.mainFrame;
import MapEditor.Core.ConquestMap;

public class MapInfoPanel extends JPanel{
	private JTable contTable, terTable;  
	private ConquestMap map = mainFrame.map;
	
	public MapInfoPanel(){
		String[][] values = new String[Math.max(map.territories.size(), map.continents.size())][2];
		for(int i = 0; i < map.territories.size(); i++){
			values[i][0] = map.territories.get(i).getName();
		}
		
		System.out.println(Arrays.asList(values));
		
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(
				new DefaultTableModel(
					values,
						new String[] { "Territory", "Continent" }) {
					private static final long serialVersionUID = 1L;
					boolean[] columnEditables = new boolean[] { false, false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Add");
		btnNewButton.setBounds(820, 352, 93, 23);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Delete");
		btnNewButton_1.setBounds(978, 352, 93, 23);
		frame.getContentPane().add(btnNewButton_1);
	}
	 
	 
	
}
