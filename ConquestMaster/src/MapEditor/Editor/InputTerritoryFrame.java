package MapEditor.Editor;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import MapEditor.Domain.Territory;

public class InputTerritoryFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextField tName, tLocX, tLocY, tContinent, tLinks;
	private JLabel lName, lLocX, lLocY, lContinent, lLinks;
	private JButton confirm, cancel;
	private Territory t;
//	private ConquestMap map = MainFrame.map;
//	private LogPanel log = MainFrame.lp;
	
	public InputTerritoryFrame(String name){
		setTitle("Input Territory");
		setBounds(0, 0, 1000, 1000);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1000, 1000);
		
		confirm = new JButton("Confirm", null);
		confirm.addActionListener(this);
		confirm.setBounds(150, 600, 50, 20);
		cancel = new JButton("Cancel", null);
		cancel.addActionListener(this);
		cancel.setBounds(250, 600, 50, 20);
		
		lName = new JLabel("Name: ");
		lName.setHorizontalAlignment(SwingConstants.CENTER);
		lName.setBounds(50, 100, 20, 20);
		tName = new JTextField();
		
		
		
		
		
		
		panel.add(lName);
		panel.add(confirm);
		panel.add(cancel);
		this.getContentPane().add(panel);
		
		pack();
		setVisible(true);
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new InputTerritoryFrame(null);
			}
		});
	}
	
	
}
