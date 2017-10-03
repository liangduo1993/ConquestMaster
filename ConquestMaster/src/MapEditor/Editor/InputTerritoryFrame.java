package MapEditor.Editor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.DropMode;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InputTerritoryFrame {

	private JFrame frmInputTerritory;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputTerritoryFrame window = new InputTerritoryFrame();
					window.frmInputTerritory.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InputTerritoryFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInputTerritory = new JFrame();
		frmInputTerritory.setTitle("Input Territory");
		frmInputTerritory.setBounds(100, 100, 528, 656);
		frmInputTerritory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInputTerritory.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(60, 90, 54, 15);
		frmInputTerritory.getContentPane().add(lblName);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(193, 89, 114, 24);
		frmInputTerritory.getContentPane().add(textArea);
		
		JLabel lblLocation = new JLabel("location\uFF1A");
		lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblLocation.setBounds(38, 210, 94, 15);
		frmInputTerritory.getContentPane().add(lblLocation);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(193, 211, 54, 24);
		frmInputTerritory.getContentPane().add(textArea_1);
		
		JLabel lblNewLabel = new JLabel("Continent:");
		lblNewLabel.setBounds(53, 330, 61, 15);
		frmInputTerritory.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setBounds(193, 329, 128, 24);
		frmInputTerritory.getContentPane().add(scrollPane);
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"", "2", "3", "4", "5", "6", "7", "8"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		JLabel lblNewLabel_1 = new JLabel(",");
		lblNewLabel_1.setBounds(257, 220, 54, 15);
		frmInputTerritory.getContentPane().add(lblNewLabel_1);
		
		JTextArea textArea_2 = new JTextArea();
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
		
		JButton btnNewButton = new JButton("comfirm");
		btnNewButton.setBounds(60, 584, 93, 23);
		frmInputTerritory.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(354, 584, 93, 23);
		
		frmInputTerritory.getContentPane().add(btnNewButton_1);
		
		String[] str_list = {"123","asd","ttg"};
	}
}
