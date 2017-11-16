package GameConsole.View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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


import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class is the interface for the beginning of the tournament mode where the user
 * can select maps, computer players, number of games and turns
 */
public class TournamentGamePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	private JComboBox<String> comboBox_6;
	private JComboBox<String> comboBox_7;
	private JComboBox<String> comboBox_8;
	private JComboBox<String> comboBox_9;



	/**
	 * Create the application.
	 */
	public TournamentGamePanel() {

		setLayout(null);
		setBounds(0, 0, 735, 591);
		JLabel lblNewLabel = new JLabel("Player Selection ");
		lblNewLabel.setBackground(new Color(255, 255, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 22));
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBounds(0, 0, 182, 38);
		add(lblNewLabel);

		JLabel p1Label = new JLabel("Computer 1:");
		p1Label.setBounds(91, 76, 75, 15);
		add(p1Label);

		JLabel p2Label = new JLabel("Computer 2:");
		p2Label.setBounds(381, 76, 75, 15);
		add(p2Label);

		JLabel p3Label = new JLabel("Computer 3:");
		p3Label.setBounds(91, 126, 75, 15);
		add(p3Label);

		JLabel p4Label = new JLabel("Computer 4:");
		p4Label.setBounds(381, 123, 75, 15);
		add(p4Label);

		comboBox_6 = new JComboBox();
		comboBox_6.setModel(
				new DefaultComboBoxModel(new String[] { "none", "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_6.setBounds(211, 76, 102, 21);
		add(comboBox_6);

		comboBox_7 = new JComboBox();
		comboBox_7.setModel(
				new DefaultComboBoxModel(new String[] { "none", "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_7.setBounds(501, 76, 102, 21);
		comboBox_7.setEnabled(false);
		add(comboBox_7);

		comboBox_8 = new JComboBox();
		comboBox_8.setModel(
				new DefaultComboBoxModel(new String[] { "none", "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_8.setBounds(211, 126, 102, 21);
		comboBox_8.setEnabled(false);
		add(comboBox_8);

		comboBox_9 = new JComboBox();
		comboBox_9.setModel(
				new DefaultComboBoxModel(new String[] { "none", "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_9.setBounds(501, 126, 102, 21);
		comboBox_9.setEnabled(false);
		add(comboBox_9);

		comboBox_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = (String) comboBox_6.getSelectedItem();
				if (text.equals("none")) {
					comboBox_7.setEnabled(false);
				} else {
					comboBox_7.setEnabled(true);
				}
			}
		});

		comboBox_7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String text7 = (String) comboBox_7.getSelectedItem();
				if (text7.equals("none")) {
					comboBox_8.setEnabled(false);
				} else {
					comboBox_8.setEnabled(true);
				}
			}
		});

		comboBox_8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String text8 = (String) comboBox_8.getSelectedItem();
				if (text8.equals("none")) {
					comboBox_9.setEnabled(false);
				} else {
					comboBox_9.setEnabled(true);
				}
			}
		});

		JButton startGameButt = new JButton("Start Game");
		startGameButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int gameTimes = Integer.parseInt(textField.getText());
				if(gameTimes>=10&&gameTimes<=50){
					
				}else{
					JOptionPane.showMessageDialog(null, "Plase input the correct Game Times on each map from 10 to 50!!!");
				}
			}
		});
		startGameButt.setBackground(Color.WHITE);
		startGameButt.setBounds(175, 537, 102, 23);
		add(startGameButt);

		JButton CancelButt = new JButton("Cancel");
		CancelButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		CancelButt.setBounds(448, 537, 93, 23);
		add(CancelButt);

		JLabel lblNewLabel_1 = new JLabel("Games Number");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(70, 185, 102, 15);
		add(lblNewLabel_1);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));
		comboBox.setBounds(211, 182, 102, 21);
		add(comboBox);

		JLabel lblNewLabel_2 = new JLabel("Max Turns :");
		lblNewLabel_2.setBounds(381, 185, 75, 15);
		add(lblNewLabel_2);

		textField = new JTextField();
		textField.setBounds(501, 182, 102, 21);
		add(textField);
		textField.setColumns(10);

		JLabel lblNewM5Label = new JLabel("(input 10-50)");
		lblNewM5Label.setBounds(628, 185, 84, 15);
		add(lblNewM5Label);

		JLabel lblNewLabel_4 = new JLabel("Map Selection");
		lblNewLabel_4.setFont(new Font("Calibri", Font.PLAIN, 22));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(0, 220, 182, 38);
		add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Map 1:");
		lblNewLabel_5.setBounds(91, 260, 54, 15);
		add(lblNewLabel_5);

		JLabel lblMap = new JLabel("Map 2:");
		lblMap.setBounds(91, 300, 54, 15);
		add(lblMap);

		JLabel lblMap_1 = new JLabel("Map 3:");
		lblMap_1.setBounds(91, 340, 54, 15);
		add(lblMap_1);

		JLabel lblMap_2 = new JLabel("Map 4:");
		lblMap_2.setBounds(91, 380, 54, 15);
		add(lblMap_2);

		JLabel lblMap_3 = new JLabel("Map 5:");
		lblMap_3.setBounds(91, 420, 54, 15);
		add(lblMap_3);

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
		add(btnSelectM);

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
		add(btnSelectM_1);

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
		add(btnSelectM_2);

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
		add(btnSelectM_3);

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
		add(btnSelectM_4);

		M1Label = new JLabel("");
		M1Label.setHorizontalAlignment(SwingConstants.CENTER);
		M1Label.setForeground(Color.RED);
		M1Label.setBounds(381, 260, 297, 19);
		add(M1Label);

		M2Label = new JLabel("");
		M2Label.setHorizontalAlignment(SwingConstants.CENTER);
		M2Label.setForeground(Color.RED);
		M2Label.setBounds(381, 298, 297, 19);
		add(M2Label);

		M3Label = new JLabel("");
		M3Label.setHorizontalAlignment(SwingConstants.CENTER);
		M3Label.setForeground(Color.RED);
		M3Label.setBounds(381, 340, 297, 19);
		add(M3Label);

		M4Label = new JLabel("");
		M4Label.setHorizontalAlignment(SwingConstants.CENTER);
		M4Label.setForeground(Color.RED);
		M4Label.setBounds(381, 380, 297, 19);
		add(M4Label);

		M5Label = new JLabel("");
		M5Label.setHorizontalAlignment(SwingConstants.CENTER);
		M5Label.setForeground(Color.RED);
		M5Label.setBounds(381, 420, 297, 19);
		add(M5Label);
		setVisible(true);
	}
	
	//test JPanel
	public static void main(String[] args) {
		JFrame jFrame = new JFrame("");
		jFrame.getContentPane().setLayout(null);
		jFrame.setBounds(0, 0, 900, 800);
		TournamentGamePanel sgp = new TournamentGamePanel();
		sgp.setBounds(jFrame.getWidth()/7, jFrame.getHeight()/6, 735, 591);
		jFrame.getContentPane().add(sgp);
		jFrame.setVisible(true);
	}
}
