package GameConsole.View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.UIManager;

import GameConsole.Core.GameState;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SoloGamePanel {

	private JFrame frame;
	private JTextField textField;
	private JFileChooser fc1;
	private JFileChooser fc2;
	private JFileChooser fc3;
	private JFileChooser fc4;
	private JFileChooser fc5;
	
	private JLabel M1Label;
	private JLabel M2Label;
	private JLabel M3Label;
	private JLabel M4Label;
	private JLabel M5Label;
	

	/**
	 * Launch the application.
	 * 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SoloGamePanel window = new SoloGamePanel();
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
	public SoloGamePanel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 751, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 735, 591);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Player Selection ");
		lblNewLabel.setBackground(new Color(255, 255, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 22));
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBounds(0, 0, 182, 38);
		panel.add(lblNewLabel);

		JLabel p1Label = new JLabel("Computer 1:");
		p1Label.setBounds(91, 76, 75, 15);
		panel.add(p1Label);

		JLabel p2Label = new JLabel("Computer 2:");
		p2Label.setBounds(381, 76, 75, 15);
		panel.add(p2Label);

		JLabel p3Label = new JLabel("Computer 3:");
		p3Label.setBounds(91, 126, 75, 15);
		panel.add(p3Label);

		JLabel p4Label = new JLabel("Computer 4:");
		p4Label.setBounds(381, 123, 75, 15);
		panel.add(p4Label);

		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setModel(
				new DefaultComboBoxModel(new String[] { "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_6.setBounds(211, 76, 102, 21);
		panel.add(comboBox_6);

		JComboBox comboBox_7 = new JComboBox();
		comboBox_7.setModel(
				new DefaultComboBoxModel(new String[] { "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_7.setBounds(501, 76, 102, 21);
		panel.add(comboBox_7);

		JComboBox comboBox_8 = new JComboBox();
		comboBox_8.setModel(
				new DefaultComboBoxModel(new String[] { "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_8.setBounds(211, 126, 102, 21);
		panel.add(comboBox_8);

		JComboBox comboBox_9 = new JComboBox();
		comboBox_9.setModel(
				new DefaultComboBoxModel(new String[] { "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_9.setBounds(501, 126, 102, 21);
		panel.add(comboBox_9);

		JButton startGameButt = new JButton("Start Game");
		startGameButt.setBackground(Color.WHITE);
		startGameButt.setBounds(175, 537, 102, 23);
		panel.add(startGameButt);

		JButton CancelButt = new JButton("Cancel");
		CancelButt.setBounds(448, 537, 93, 23);
		panel.add(CancelButt);
		
		JLabel lblNewLabel_1 = new JLabel("Games Number");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(70, 185, 102, 15);
		panel.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5"}));
		comboBox.setBounds(211, 182, 102, 21);
		panel.add(comboBox);
		
		JLabel lblNewLabel_2 = new JLabel("Max Turns :");
		lblNewLabel_2.setBounds(381, 185, 75, 15);
		panel.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(501, 182, 102, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewM5Label = new JLabel("(input 10-50)");
		lblNewM5Label.setBounds(628, 185, 84, 15);
		panel.add(lblNewM5Label);
		
		JLabel lblNewLabel_4 = new JLabel("Map Selection");
		lblNewLabel_4.setFont(new Font("Calibri", Font.PLAIN, 22));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(0, 220, 182, 38);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Map 1:");
		lblNewLabel_5.setBounds(91, 260, 54, 15);
		panel.add(lblNewLabel_5);
		
		JLabel lblMap = new JLabel("Map 2:");
		lblMap.setBounds(91, 300, 54, 15);
		panel.add(lblMap);
		
		JLabel lblMap_1 = new JLabel("Map 3:");
		lblMap_1.setBounds(91, 340, 54, 15);
		panel.add(lblMap_1);
		
		JLabel lblMap_2 = new JLabel("Map 4:");
		lblMap_2.setBounds(91, 380, 54, 15);
		panel.add(lblMap_2);
		
		JLabel lblMap_3 = new JLabel("Map 5:");
		lblMap_3.setBounds(91, 420, 54, 15);
		panel.add(lblMap_3);
		
		JButton btnSelectM = new JButton("select M1");
		btnSelectM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc1 = new JFileChooser();
				int returnVal = fc1.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc1.getSelectedFile();
					String path = file.getAbsolutePath();
					M1Label.setText(path);
			}
			}
		});
		btnSelectM.setBounds(211, 256, 93, 23);
		panel.add(btnSelectM);
		
		JButton btnSelectM_1 = new JButton("select M2");
		btnSelectM_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc2 = new JFileChooser();
				int returnVal = fc2.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc2.getSelectedFile();
					String path = file.getAbsolutePath();
					M2Label.setText(path);
			}
			}
		});
		btnSelectM_1.setBounds(211, 296, 93, 23);
		panel.add(btnSelectM_1);
		
		JButton btnSelectM_2 = new JButton("select M3");
		btnSelectM_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc3 = new JFileChooser();
				int returnVal = fc3.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc3.getSelectedFile();
					String path = file.getAbsolutePath();
					M3Label.setText(path);
			}
			}
		});
		btnSelectM_2.setBounds(211, 336, 93, 23);
		panel.add(btnSelectM_2);
		
		JButton btnSelectM_3 = new JButton("select M4");
		btnSelectM_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc4 = new JFileChooser();
				int returnVal = fc4.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc4.getSelectedFile();
					String path = file.getAbsolutePath();
					M4Label.setText(path);
				}
			}
		});
		btnSelectM_3.setBounds(211, 376, 93, 23);
		panel.add(btnSelectM_3);
		
		JButton btnSelectM_4 = new JButton("select M5");
		btnSelectM_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc5 = new JFileChooser();
				int returnVal = fc5.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc5.getSelectedFile();
					String path = file.getAbsolutePath();
					M5Label.setText(path);
				}
			}
		});
		btnSelectM_4.setBounds(211, 416, 93, 23);
		panel.add(btnSelectM_4);
		
		M1Label = new JLabel("");
		M1Label.setHorizontalAlignment(SwingConstants.CENTER);
		M1Label.setForeground(Color.RED);
		M1Label.setBounds(381, 260, 297, 19);
		panel.add(M1Label);
		
		M2Label = new JLabel("");
		M2Label.setHorizontalAlignment(SwingConstants.CENTER);
		M2Label.setForeground(Color.RED);
		M2Label.setBounds(381, 298, 297, 19);
		panel.add(M2Label);
		
		M3Label= new JLabel("");
		M3Label.setHorizontalAlignment(SwingConstants.CENTER);
		M3Label.setForeground(Color.RED);
		M3Label.setBounds(381, 340, 297, 19);
		panel.add(M3Label);
		
		M4Label= new JLabel("");
		M4Label.setHorizontalAlignment(SwingConstants.CENTER);
		M4Label.setForeground(Color.RED);
		M4Label.setBounds(381, 380, 297, 19);
		panel.add(M4Label);
		
		M5Label = new JLabel("");
		M5Label.setHorizontalAlignment(SwingConstants.CENTER);
		M5Label.setForeground(Color.RED);
		M5Label.setBounds(381, 420, 297, 19);
		panel.add(M5Label);
	}
}
