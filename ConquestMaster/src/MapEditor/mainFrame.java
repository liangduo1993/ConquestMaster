package MapEditor;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextPane;



import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class mainFrame {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame window = new mainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0,0,1071,788);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("new");
		mnFile.add(mntmNew);
		
		JMenuItem mntmLoadMap = new JMenuItem("load map");
		mnFile.add(mntmLoadMap);
		
		JMenuItem mntmSaveMap = new JMenuItem("save map");
		mnFile.add(mntmSaveMap);
		
		JMenuItem mntmExit = new JMenuItem("exit");
		mntmExit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 695, 1063, 42);
		frame.getContentPane().add(panel);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_2 = new JButton("New button");
		panel.add(btnNewButton_2);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GREEN);
		panel_1.setForeground(Color.WHITE);
		panel_1.setBounds(0, 0, 204, 695);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(0, 0, 204, 695);
		panel_1.add(textPane);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.PINK);
		panel_2.setForeground(Color.WHITE);
		panel_2.setBounds(205, 0, 605, 695);
		frame.getContentPane().add(panel_2);
		

		String[] territories = {"1","2","3","4"};
		//list.setValueIsAdjusting(true);
		//list.setBounds(844, 41, -25, -15);
		//frame.getContentPane().add(list);
		
		JLabel lblTerriroy = new JLabel("Terriroies");
		lblTerriroy.setHorizontalAlignment(SwingConstants.CENTER);
		lblTerriroy.setBounds(809, 0, 254, 26);
		frame.getContentPane().add(lblTerriroy);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(809, 36, 254, 659);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("contient");
		lblNewLabel_1.setBounds(0, 261, 127, 15);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Territory");
		lblNewLabel.setBounds(36, 30, 79, 20);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(125, 31, 107, 22);
		panel_3.add(scrollPane);
		
		JList list = new JList();
		list.setValueIsAdjusting(true);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"1", "3", "4", "5", "6", "7"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(list);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(125, 261, 107, 22);
		panel_3.add(scrollPane_1);
		
		JList list_1 = new JList();
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] {"a", "b", "c", "d"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane_1.setViewportView(list_1);
		
		JLabel lblChangeName = new JLabel("change name");
		lblChangeName.setHorizontalAlignment(SwingConstants.CENTER);
		lblChangeName.setBounds(21, 75, 94, 22);
		panel_3.add(lblChangeName);
		
		textField = new JTextField();
		textField.setBounds(125, 76, 107, 21);
		panel_3.add(textField);
		textField.setColumns(10);
		
		JLabel lblChangeContient = new JLabel("change contient");
		lblChangeContient.setHorizontalAlignment(SwingConstants.CENTER);
		lblChangeContient.setBounds(21, 122, 106, 15);
		panel_3.add(lblChangeContient);
		
		textField_1 = new JTextField();
		textField_1.setBounds(125, 119, 107, 21);
		panel_3.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("Save");
		btnNewButton_4.setBounds(95, 626, 93, 23);
		panel_3.add(btnNewButton_4);
	}
}