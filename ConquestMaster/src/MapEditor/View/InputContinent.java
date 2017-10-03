package MapEditor.View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Window.Type;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InputContinent {

	private JFrame frmInputcontinent;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputContinent window = new InputContinent();
					window.frmInputcontinent.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InputContinent() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInputcontinent = new JFrame();
		frmInputcontinent.setType(Type.UTILITY);
		frmInputcontinent.setTitle("InputContinent");
		frmInputcontinent.setBounds(100, 100, 520, 428);
		frmInputcontinent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInputcontinent.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setBounds(94, 84, 54, 15);
		frmInputcontinent.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(189, 81, 118, 21);
		frmInputcontinent.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Bonus:");
		lblNewLabel_1.setBounds(94, 152, 54, 15);
		frmInputcontinent.getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(189, 149, 118, 21);
		frmInputcontinent.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		//Confirm Button Event
		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(71, 302, 93, 23);
		frmInputcontinent.getContentPane().add(btnNewButton);
		
		//Cancel Button Event exit Window
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(284, 302, 93, 23);
		frmInputcontinent.getContentPane().add(btnNewButton_1);
	}
}
