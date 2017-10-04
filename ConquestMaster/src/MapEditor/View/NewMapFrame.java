package MapEditor.View;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import MapEditor.mainFrame;
import MapEditor.Core.ConquestMap;
import MapEditor.Core.ConquestMap.ScrollOptions;


public class NewMapFrame {
	private ConquestMap map = mainFrame.map;
	private JFrame frame;
	private JTextField tAuthor;
	private JCheckBox wrapCheckBox, warnCheckBox;
	private JButton imgBtn, confirmBtn, cancelBtn;
	
	
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
		frame.setBounds(400, 100, 380, 370);
		frame.setResizable(false);
		frame.setTitle("New Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tAuthor = new JTextField();
		tAuthor.setBounds(140, 5, 100, 21);
		tAuthor.setColumns(10);

		wrapCheckBox = new JCheckBox("");
		wrapCheckBox.setBounds(140, 52, 21, 21);
		
		warnCheckBox = new JCheckBox("");
		warnCheckBox.setBounds(140, 91, 21, 21);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(140, 145, 110, 21);
		ScrollOptions[] values = ScrollOptions.values();
		comboBox.setModel(new DefaultComboBoxModel(values));

		JLabel lblNewLabel_1 = new JLabel("ImagePath:");
		lblNewLabel_1.setBounds(40, 217, 100, 15);

		// ImagePath button no Name needed
		imgBtn = new JButton("Please select a image!");
		imgBtn.setBounds(140, 213, 165, 23);
		imgBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		
		 confirmBtn = new JButton("Create");
		 confirmBtn.setBounds(70, 270, 100, 23);
		 confirmBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					
				}
			});
		 cancelBtn = new JButton("Create");
		 cancelBtn.setBounds(220, 270, 100, 23);
		 cancelBtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 
				 
			 }
		 });

		JLabel lblNewLabel_4 = new JLabel("Scroll:");
		lblNewLabel_4.setBounds(40, 148, 42, 15);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(lblNewLabel_1);
		frame.getContentPane().add(lblNewLabel_4);
		frame.getContentPane().add(warnCheckBox);
		frame.getContentPane().add(wrapCheckBox);
		frame.getContentPane().add(tAuthor);
		frame.getContentPane().add(imgBtn);
		frame.getContentPane().add(confirmBtn);
		frame.getContentPane().add(cancelBtn);
		frame.getContentPane().add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Author:");
		lblNewLabel.setBounds(40, 10, 54, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Warp:");
		lblNewLabel_2.setBounds(40, 56, 54, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Warn:");
		lblNewLabel_3.setBounds(40, 96, 54, 15);
		frame.getContentPane().add(lblNewLabel_3);

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