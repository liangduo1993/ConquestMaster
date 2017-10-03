package MapEditor.View;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import MapEditor.mainFrame;
import MapEditor.Core.ConquestMap;

public class NewMapFrame {
	// private ConquestMap map = mainFrame.map;
	private JFrame frame;
	private JTextField textField;
	private JCheckBox wrapCheckBox, warnCheckBox;
	private JButton imgBtn, confirmBtn ;
	/**
	 * Create the application.
	 */
	public NewMapFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 100, 450, 400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("Author:");

		textField = new JTextField();
		textField.setColumns(10);

		wrapCheckBox = new JCheckBox("");
		
		warnCheckBox = new JCheckBox("");

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "2", "3", "4", "5" }));

		JLabel lblNewLabel_1 = new JLabel("ImagePath:");

		// ImagePath button no Name needed
		 imgBtn = new JButton("Please select a image!");
		imgBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		
		 confirmBtn = new JButton("Create");
		 confirmBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					
				}
			});

		JLabel lblNewLabel_2 = new JLabel("Wrap:");

		JLabel lblNewLabel_3 = new JLabel("Warn:");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_4 = new JLabel("Scroll:");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout
				.setHorizontalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
								groupLayout
										.createSequentialGroup().addGroup(groupLayout.createParallelGroup(
												Alignment.TRAILING)
												.addGroup(groupLayout
														.createSequentialGroup().addGap(55).addGroup(groupLayout
																.createParallelGroup(Alignment.LEADING)
																.addComponent(lblNewLabel).addComponent(lblNewLabel_1)
																.addGroup(Alignment.TRAILING, groupLayout
																		.createSequentialGroup()
																		.addPreferredGap(ComponentPlacement.RELATED, 30,
																				Short.MAX_VALUE)
																		.addComponent(lblNewLabel_2).addGap(6))
																.addComponent(lblNewLabel_4, Alignment.TRAILING))
														.addGap(25))
												.addGroup(groupLayout.createSequentialGroup().addContainerGap()
														.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 49,
																GroupLayout.PREFERRED_SIZE)
														.addGap(18)))
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(warnCheckBox).addComponent(wrapCheckBox)
												.addComponent(textField, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
														.addComponent(imgBtn).addComponent(comboBox,
																GroupLayout.PREFERRED_SIZE, 94,
																GroupLayout.PREFERRED_SIZE)))
										.addGap(201)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(5)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel).addComponent(
						textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(26)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(wrapCheckBox)
						.addComponent(lblNewLabel_2))
				.addGap(18)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(warnCheckBox).addComponent(
						lblNewLabel_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGap(33)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_4))
				.addGap(47).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(lblNewLabel_1).addComponent(imgBtn))
				.addGap(31)));
		frame.getContentPane().setLayout(groupLayout);

		frame.setVisible(true);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewMapFrame window = new NewMapFrame();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
