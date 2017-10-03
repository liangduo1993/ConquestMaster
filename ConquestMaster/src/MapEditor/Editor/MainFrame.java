package MapEditor.Editor;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import MapEditor.Core.ConquestMap;
import MapEditor.template.core.MapDisplay;

public class MainFrame extends JFrame {
	public static ConquestMap map;
	public static MapDisplay mapDisplay;
	public FileChooserPanel fcp;
	public static LogPanel lp;


	public MainFrame() {
		setTitle("MapEditor V1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));
		setBounds(100, 100, 900, 900);

		map = new ConquestMap();
		lp = new LogPanel();
		fcp = new FileChooserPanel();
		//mapDisplay = new MapDisplay();
		
		getContentPane().add(fcp, "North");
	//	getContentPane().add(new JButton("South"), "South");
		getContentPane().add(lp, "West");
		getContentPane().add(new JButton("East"), "East");
		//getContentPane().add(mapDisplay, "Center");

		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new MainFrame();
			}
		});
	}

}
