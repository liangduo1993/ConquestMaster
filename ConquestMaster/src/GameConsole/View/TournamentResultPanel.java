package GameConsole.View;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;

public class TournamentResultPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public TournamentResultPanel() {
		setLayout(null);
		setBounds(0, 0, 1200, 900);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 348, 608, 108);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Map 1", null, null, null, null, null},
				{"Map 2", null, null, null, null, null},
				{"Map 3", null, null, null, null, null},
				{"Map 4", null, null, null, null, null},
				{"Map 5", null, null, null, null, null},
			},
			new String[] {
				"", "Game 1", "Game 2", "Game 3", "Game 4", "Game 5"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true, true, true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		JLabel lblNewLabel = new JLabel("Tournamet Result");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel.setBounds(20, 62, 655, 98);
		add(lblNewLabel);
		setVisible(true);
	}
}
