package GameConsole.View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.UIManager;

public class SoloGamePanel {

	private JFrame frame;

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

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Player Selection ");
		lblNewLabel.setBackground(new Color(255, 255, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 29));
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBounds(23, 21, 702, 66);
		panel.add(lblNewLabel);

		JLabel p1Label = new JLabel("New label");
		p1Label.setBounds(91, 110, 54, 15);
		panel.add(p1Label);

		JLabel p2Label = new JLabel("New label");
		p2Label.setBounds(91, 160, 54, 15);
		panel.add(p2Label);

		JLabel p3Label = new JLabel("New label");
		p3Label.setBounds(91, 210, 54, 15);
		panel.add(p3Label);

		JLabel p4Label = new JLabel("New label");
		p4Label.setBounds(91, 260, 54, 15);
		panel.add(p4Label);

		JLabel p5Label = new JLabel("New label");
		p5Label.setBounds(91, 310, 54, 15);
		panel.add(p5Label);

		JLabel p6Label = new JLabel("New label");
		p6Label.setBounds(91, 360, 54, 15);
		panel.add(p6Label);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "none", "human", "computer" }));
		comboBox.setBounds(244, 110, 102, 21);
		panel.add(comboBox);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "none", "human", "computer" }));
		comboBox_1.setBounds(244, 160, 102, 21);
		panel.add(comboBox_1);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "none", "human", "computer" }));
		comboBox_2.setBounds(244, 210, 102, 21);
		panel.add(comboBox_2);

		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] { "none", "human", "computer" }));
		comboBox_3.setBounds(244, 260, 102, 21);
		panel.add(comboBox_3);

		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(new String[] { "none", "human", "computer" }));
		comboBox_4.setBounds(244, 310, 102, 21);
		panel.add(comboBox_4);

		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setModel(new DefaultComboBoxModel(new String[] { "none", "human", "computer" }));
		comboBox_5.setBounds(244, 360, 102, 21);
		panel.add(comboBox_5);

		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setModel(
				new DefaultComboBoxModel(new String[] { "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_6.setBounds(500, 107, 102, 21);
		panel.add(comboBox_6);

		JComboBox comboBox_7 = new JComboBox();
		comboBox_7.setModel(
				new DefaultComboBoxModel(new String[] { "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_7.setBounds(500, 157, 102, 21);
		panel.add(comboBox_7);

		JComboBox comboBox_8 = new JComboBox();
		comboBox_8.setModel(
				new DefaultComboBoxModel(new String[] { "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_8.setBounds(500, 207, 102, 21);
		panel.add(comboBox_8);

		JComboBox comboBox_9 = new JComboBox();
		comboBox_9.setModel(
				new DefaultComboBoxModel(new String[] { "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_9.setBounds(500, 260, 102, 21);
		panel.add(comboBox_9);

		JComboBox comboBox_10 = new JComboBox();
		comboBox_10.setModel(
				new DefaultComboBoxModel(new String[] { "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_10.setBounds(500, 307, 102, 21);
		panel.add(comboBox_10);

		JComboBox comboBox_11 = new JComboBox();
		comboBox_11.setModel(
				new DefaultComboBoxModel(new String[] { "aggressive ", "benevolent ", "random ", "cheater " }));
		comboBox_11.setBounds(500, 357, 102, 21);
		panel.add(comboBox_11);

		JButton startGameButt = new JButton("Start Game");
		startGameButt.setBackground(Color.WHITE);
		startGameButt.setBounds(198, 443, 102, 23);
		panel.add(startGameButt);

		JButton CancelButt = new JButton("Cancel");
		CancelButt.setBounds(447, 443, 93, 23);
		panel.add(CancelButt);
	}
}
