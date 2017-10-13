package MapEditor.Core;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import MapEditor.Model.ConquestMap;
import MapEditor.View.LogPanel;
import MapEditor.View.NewMapFrame;
import MapEditor.View.TablePanel;

public class mainFrame {

	private JFrame frame;
	private JMenuBar menuBar;
	public static ConquestMap map;
	//public static MapDisplay mapDisplay;
	public FileChooser fcp;
	public static LogPanel lp;
	public static TablePanel infoPanel;
	
	
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
		lp = new LogPanel();
		map = new ConquestMap();

		frame = new JFrame();
		frame.setBounds(400, 100, 1089, 788);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("new");
		mnFile.add(mntmNew);
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//fcp = new FileChooser("new");
				new NewMapFrame();
			}
		});

		JMenuItem mntmLoadMap = new JMenuItem("load map");
		mnFile.add(mntmLoadMap);
		mntmLoadMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fcp = new FileChooser("load");
			}
		});

		JMenuItem mntmSaveMap = new JMenuItem("save map");
		mnFile.add(mntmSaveMap);
		mntmSaveMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fcp = new FileChooser("save");
			}
		});

		JMenuItem mntmSaveAsMap = new JMenuItem("save as");
		mnFile.add(mntmSaveAsMap);
		mntmSaveAsMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fcp = new FileChooser("saveas");
			}
		});

		JMenuItem mntmExit = new JMenuItem("exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		frame.getContentPane().setLayout(null);

		

		lp.setBounds(0, 0, 250, 695);
		frame.getContentPane().add(lp);
		
		infoPanel = new TablePanel(map);
		infoPanel.setBounds(700, 10, 400, 695);
		infoPanel.setVisible(false);
		frame.getContentPane().add(infoPanel);
		map.addObserver(infoPanel);
		
		frame.setVisible(true);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame window = new mainFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}