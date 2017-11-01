package GameConsole.View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import GameConsole.Core.GameState;
import GameConsole.Model.Domain.Card;
import GameConsole.Model.Player.Player;

/**
 * This class is to display the interface of the game where the player can
 * visually perform game operations.
 */
public class WindowMain implements ActionListener, Observer {
	private JFrame frame1;
	private JPanel cards; // card lay out panel for whole frame
	private JPanel cardPanel;
	private CardLayout cardLayout;
	private JPanel mapPanel;
	private DomiInfoPanel domiInfoPanel;
	private JButton openButton;
	private JTextField player1TextField, player2TextField, player3TextField, player4TextField, player5TextField;
	private JLabel playerWonLabell;
	private ConquestRatio cRatioPanel;
	private LogPanel lp = LogPanel.getInstance();
	private BufferedImage buttonImage;
	private JButton nextStage;
	private JPanel arrow0, arrow1, arrow2;
	private JLabel moveStageLabel, attackStageLabel, obtainTroopLabel;
	private JPanel unitDisplay;

	private GameState gameState;
	private JFileChooser fc;
	private int troopsLeft;

	/**
	 * Constructor method
	 * 
	 * @throws IOException
	 */
	public WindowMain() throws IOException {
		this.openMapFile();

	}

	/**
	 * To open a map file in the main window
	 */
	public void openMapFile() {
		frame1 = new JFrame();
		frame1.setResizable(false);
		frame1.setTitle("Risk - The Game of Global Domination");
		frame1.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame1.setBounds(100, 100, 1200, 900);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.getContentPane().setLayout(new CardLayout(0, 0));
		fc = new JFileChooser();
		openButton = new JButton("Open Map", null);
		openButton.addActionListener(this);
		openButton.setBounds(400, 400, 250, 100);

		this.frame1.add(openButton, BorderLayout.PAGE_START);
		frame1.setVisible(true);

	}

	/**
	 * Method to listen for action event on openButton
	 * 
	 * @param e
	 *            a object allows to access the properties of the ActionEvent with
	 *            ActionEvent type
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(this.frame1);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				String path = file.getAbsolutePath();
				this.frame1.remove(openButton);
				try {
					gameState = new GameState(this, path);
					initialize();
					openButton.setVisible(false);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this.frame1, "The map applies warn & the map is not valid!");
					e1.printStackTrace();
					this.frame1.dispose();
					openMapFile();
				}
			}
		}
	}

	/**
	 * Method to initial the start game interface, the player selection window
	 * interface, and the game play interface
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		cards = new JPanel(new CardLayout());
		cards.setBounds(100, 100, 1200, 900);

		cards.setVisible(true);
		JPanel mainScreen = new JPanel();
		JPanel playerSelect = new JPanel();
		mapPanel = new JPanel();
		mapPanel.setBackground(Color.LIGHT_GRAY);

		JPanel resultsScreen = new JPanel();
		cards.add(mainScreen, "Main Screen");
		cards.add(playerSelect, "Player Selection");
		cards.add(mapPanel, "Game");
		cards.add(resultsScreen, "Results");
		frame1.getContentPane().add(cards);

		mainScreen.setBackground(Color.LIGHT_GRAY);
		mainScreen.setLayout(null);

		this.cardLayout = (CardLayout) cards.getLayout();
		JPanel startGame = new JPanel();
		startGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cardLayout.show(cards, "Player Selection");
			}
		});

		JPanel exitPanel = new JPanel();
		exitPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		exitPanel.setBackground(Color.RED);
		exitPanel.setBounds(1130, 820, 50, 30);
		mainScreen.add(exitPanel);
		exitPanel.setLayout(null);

		JLabel exitLabel = new JLabel("Exit");
		exitLabel.setForeground(Color.WHITE);
		exitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exitLabel.setBounds(0, 0, 50, 30);
		exitPanel.add(exitLabel);
		startGame.setBackground(new Color(255, 0, 0));
		startGame.setBounds(451, 680, 317, 77);
		mainScreen.add(startGame);
		startGame.setLayout(null);

		JLabel labelStart = new JLabel("Start Game");
		labelStart.setForeground(new Color(255, 255, 255));
		labelStart.setFont(new Font("Tahoma", Font.PLAIN, 60));
		labelStart.setHorizontalAlignment(SwingConstants.CENTER);
		labelStart.setBounds(0, 0, 317, 77);
		startGame.add(labelStart);

		JLabel label = new JLabel("");
		label.setBounds(0, 0, 1194, 860);
		mainScreen.add(label);
		label.setIcon(new ImageIcon("resources/GimpFiles/StartGame.png"));

		Scrollbar scrollbar = new Scrollbar();
		scrollbar.setBounds(1168, 102, 26, 200);
		mainScreen.add(scrollbar);
		playerSelect.setBackground(Color.LIGHT_GRAY);
		playerSelect.setLayout(null);
		JPanel playerQuestion = new JPanel();
		playerQuestion.setLayout(null);
		playerQuestion.setBorder(new LineBorder(new Color(0, 0, 0), 5, true));
		playerQuestion.setBackground(Color.RED);
		playerQuestion.setBounds(200, 100, 800, 100);
		playerSelect.add(playerQuestion);

		JLabel label_2 = new JLabel("How many Players will be playing?");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("Dialog", Font.PLAIN, 50));
		label_2.setBackground(Color.RED);
		label_2.setBounds(0, 0, 800, 100);
		playerQuestion.add(label_2);

		JPanel playerTwo = new JPanel();
		JPanel playerThree = new JPanel();
		JPanel playerFour = new JPanel();
		JPanel playerFive = new JPanel();
		JPanel playerOneText = new JPanel();
		playerOneText.setBackground(Color.CYAN);
		JPanel playerTwoText = new JPanel();
		playerTwoText.setBackground(Color.MAGENTA);
		JPanel playerThreeText = new JPanel();
		playerThreeText.setBackground(Color.GREEN);
		JPanel playerFourText = new JPanel();
		playerFourText.setBackground(Color.blue);
		JPanel playerFiveText = new JPanel();
		playerFiveText.setBackground(Color.red);
		playerOneText.setVisible(false);
		playerTwoText.setVisible(false);
		playerThreeText.setVisible(false);
		playerFourText.setVisible(false);
		playerFiveText.setVisible(false);

		playerTwo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				playerTwo.setBackground(Color.BLUE);
				playerThree.setBackground(Color.RED);
				playerFour.setBackground(Color.RED);
				playerFive.setBackground(Color.RED);
				playerOneText.setVisible(true);
				playerTwoText.setVisible(true);
				playerThreeText.setVisible(false);
				playerFourText.setVisible(false);
				playerFiveText.setVisible(false);

			}
		});
		playerThree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				playerTwo.setBackground(Color.RED);
				playerThree.setBackground(Color.BLUE);
				playerFour.setBackground(Color.RED);
				playerFive.setBackground(Color.RED);
				playerOneText.setVisible(true);
				playerTwoText.setVisible(true);
				playerThreeText.setVisible(true);
				playerFourText.setVisible(false);
				playerFiveText.setVisible(false);
			}
		});
		playerFour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				playerTwo.setBackground(Color.RED);
				playerThree.setBackground(Color.RED);
				playerFour.setBackground(Color.BLUE);
				playerFive.setBackground(Color.RED);
				playerOneText.setVisible(true);
				playerTwoText.setVisible(true);
				playerThreeText.setVisible(true);
				playerFourText.setVisible(true);
				playerFiveText.setVisible(false);
			}
		});
		playerFive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				playerTwo.setBackground(Color.RED);
				playerThree.setBackground(Color.RED);
				playerFour.setBackground(Color.RED);
				playerFive.setBackground(Color.BLUE);
				playerOneText.setVisible(true);
				playerTwoText.setVisible(true);
				playerThreeText.setVisible(true);
				playerFourText.setVisible(true);
				playerFiveText.setVisible(true);
			}
		});
		playerTwo.setLayout(null);
		playerTwo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		playerTwo.setBackground(Color.RED);
		playerTwo.setBounds(200, 300, 100, 100);
		playerSelect.add(playerTwo);

		JLabel playerTwoLabel = new JLabel("2");
		playerTwoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerTwoLabel.setForeground(Color.WHITE);
		playerTwoLabel.setFont(new Font("Dialog", Font.PLAIN, 50));
		playerTwoLabel.setBackground(new Color(0, 128, 128));
		playerTwoLabel.setBounds(0, 0, 100, 100);
		playerTwo.add(playerTwoLabel);

		playerThree.setLayout(null);
		playerThree.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		playerThree.setBackground(Color.RED);
		playerThree.setBounds(433, 300, 100, 100);
		playerSelect.add(playerThree);

		JLabel playerThreeLabel = new JLabel("3");
		playerThreeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerThreeLabel.setForeground(Color.WHITE);
		playerThreeLabel.setFont(new Font("Dialog", Font.PLAIN, 50));
		playerThreeLabel.setBackground(new Color(0, 128, 128));
		playerThreeLabel.setBounds(0, 0, 100, 100);
		playerThree.add(playerThreeLabel);

		playerFour.setLayout(null);
		playerFour.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		playerFour.setBackground(Color.RED);
		playerFour.setBounds(667, 300, 100, 100);
		playerSelect.add(playerFour);

		JLabel playerFourLabel = new JLabel("4");
		playerFourLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerFourLabel.setForeground(Color.WHITE);
		playerFourLabel.setFont(new Font("Dialog", Font.PLAIN, 50));
		playerFourLabel.setBackground(new Color(0, 128, 128));
		playerFourLabel.setBounds(0, 0, 100, 100);
		playerFour.add(playerFourLabel);

		playerFive.setLayout(null);
		playerFive.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		playerFive.setBackground(Color.RED);
		playerFive.setBounds(900, 300, 100, 100);
		playerSelect.add(playerFive);

		JLabel playerFiveLabel = new JLabel("5");
		playerFiveLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerFiveLabel.setForeground(Color.WHITE);
		playerFiveLabel.setFont(new Font("Dialog", Font.PLAIN, 50));
		playerFiveLabel.setBackground(new Color(0, 128, 128));
		playerFiveLabel.setBounds(0, 0, 100, 100);
		playerFive.add(playerFiveLabel);

		JPanel cancelPanel = new JPanel();
		cancelPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cardLayout.show(cards, "Main Screen");
			}
		});
		cancelPanel.setLayout(null);
		cancelPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		cancelPanel.setBackground(Color.RED);
		cancelPanel.setBounds(700, 780, 200, 50);
		playerSelect.add(cancelPanel);

		JLabel cancelLabel = new JLabel("Cancel");
		cancelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cancelLabel.setForeground(Color.WHITE);
		cancelLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cancelLabel.setBounds(0, 0, 200, 50);
		cancelPanel.add(cancelLabel);
		JPanel startGamePanel = new JPanel();

		JFormattedTextField player1Name = new JFormattedTextField();
		player1Name.setVisible(false);
		player1Name.setBackground(Color.GRAY);
		JFormattedTextField player2Name = new JFormattedTextField();
		player2Name.setVisible(false);
		JFormattedTextField player3Name = new JFormattedTextField();
		player3Name.setVisible(false);
		JFormattedTextField player4Name = new JFormattedTextField();
		player4Name.setVisible(false);
		JFormattedTextField player5Name = new JFormattedTextField();
		player5Name.setVisible(false);
		JLabel numberOfTroops = new JLabel("0");
		JLabel country1 = new JLabel((String) null);
		JLabel country2 = new JLabel((String) null);

		MapDisplayer g = new MapDisplayer(mapPanel, gameState.getWorld());
		this.buttonImage = g.getButtonImage();
		System.out.println("button nums: " + g.getButtons().size());

		JButton cancelCountryButton = new JButton("Cancel\r\n");
		cancelCountryButton.setVisible(false);
		cancelCountryButton.setEnabled(false);
		cancelCountryButton.setBackground(Color.RED);
		cancelCountryButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cancelCountryButton.setBounds(371 + 40, buttonImage.getHeight() + 250, 124, 60);
		mapPanel.add(cancelCountryButton);

		cancelCountryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameState.setCountry1(null);
				gameState.setCountry2(null);
				country1.setText((String) null);
				country2.setText((String) null);
				cancelCountryButton.setEnabled(false);
				cancelCountryButton.setVisible(false);
			}
		});

		startGamePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cardLayout.show(cards, "Game");
				frame1.setBounds(100, 100, 750 + buttonImage.getWidth(), 370 + buttonImage.getHeight());
				lp.setBounds(buttonImage.getWidth() + 550, 30, 200, frame1.getHeight() - 100);
				lp.setBackground(Color.white);

				JMenuBar menuBar = new JMenuBar();
				menuBar.setBounds(0, 0, frame1.getWidth(), 30);
				mapPanel.add(menuBar);
				JMenu mnNewMenu = new JMenu("Game");
				menuBar.add(mnNewMenu);
				JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
				mntmNewMenuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
					}
				});
				mnNewMenu.add(mntmNewMenuItem);

				mapPanel.add(lp);
				if (playerOneText.isVisible()) {
					Player p1 = new Player(player1TextField.getText(), Color.cyan, gameState);
					gameState.addPlayer(p1);
					gameState.setCurrPlayer(p1);
				}
				if (playerTwoText.isVisible()) {
					Player p2 = new Player(player2TextField.getText(), Color.magenta, gameState);
					gameState.addPlayer(p2);
				}
				if (playerThreeText.isVisible()) {
					Player p3 = new Player(player3TextField.getText(), Color.green, gameState);
					gameState.addPlayer(p3);
				}
				if (playerFourText.isVisible()) {
					Player p4 = new Player(player4TextField.getText(), Color.blue, gameState);
					gameState.addPlayer(p4);
				}
				if (playerFiveText.isVisible()) {
					Player p5 = new Player(player5TextField.getText(), Color.red, gameState);
					gameState.addPlayer(p5);
				}
				gameState.gameStart();

				domiInfoPanel = new DomiInfoPanel(gameState);
				domiInfoPanel.setPreferredSize(new Dimension(domiInfoPanel.getWidth(), domiInfoPanel.getHeight()));
				JScrollPane mainScroll = new JScrollPane();
				mainScroll.setBounds(buttonImage.getWidth() + 130, 150, 380, buttonImage.getHeight());
				mainScroll.setViewportView(domiInfoPanel);
				mapPanel.add(mainScroll);

				cRatioPanel = new ConquestRatio(gameState);
				cRatioPanel.setBounds(100, 150 - 40, gameState.getWorld().getDeck().size() * ConquestRatio.ratio, 20);
				mapPanel.add(cRatioPanel);

				registerObserver();
				phaseViewUpdate();

				troopsLeft = gameState.getCurrPlayer().getBonus();
				numberOfTroops.setText(Integer.toString(troopsLeft));
				for (Player p : gameState.getAllPlayers().getPlayers()) {
					p.setInitTroop(troopsLeft);
					System.out.println(p.getName() + " has: " + p.getInitTroop());
				}
				System.out.println(gameState.getAllPlayers().getPlayers().size());
				if (gameState.getAllPlayers().getPlayers().size() > 0) { // Display
																			// the
					if (gameState.getAllPlayers().getPlayers().size() > 0) {
						player1Name.setText(gameState.getAllPlayers().getPlayers().get(0).getName());
						gameState.getAllPlayers().getPlayers().get(0).setPlayerTextName(player1Name);
						player1Name.setVisible(true);
						player1Name.setBackground(Color.GRAY);
					}
					if (gameState.getAllPlayers().getPlayers().size() > 1) {
						player2Name.setText(gameState.getAllPlayers().getPlayers().get(1).getName());
						gameState.getAllPlayers().getPlayers().get(1).setPlayerTextName(player2Name);
						player2Name.setVisible(true);
					}
					if (gameState.getAllPlayers().getPlayers().size() > 2) {
						player3Name.setText(gameState.getAllPlayers().getPlayers().get(2).getName());
						gameState.getAllPlayers().getPlayers().get(2).setPlayerTextName(player3Name);
						player3Name.setVisible(true);
					}
					if (gameState.getAllPlayers().getPlayers().size() > 3) {
						player4Name.setText(gameState.getAllPlayers().getPlayers().get(3).getName());
						gameState.getAllPlayers().getPlayers().get(3).setPlayerTextName(player4Name);
						player4Name.setVisible(true);
					}
					if (gameState.getAllPlayers().getPlayers().size() > 4) {
						player5Name.setText(gameState.getAllPlayers().getPlayers().get(4).getName());
						gameState.getAllPlayers().getPlayers().get(4).setPlayerTextName(player5Name);
						player5Name.setVisible(true);
					}
				}
				lp.addLog("=====It's " + gameState.getCurrPlayer().getName() + "'s turn.=====");
			}
		});

		startGamePanel.setLayout(null);
		startGamePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		startGamePanel.setBackground(Color.RED);
		startGamePanel.setBounds(300, 780, 200, 50);
		playerSelect.add(startGamePanel);

		JLabel startGameLabel = new JLabel("Start Game!");
		startGameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		startGameLabel.setForeground(Color.WHITE);
		startGameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		startGameLabel.setBounds(0, 0, 200, 50);
		startGamePanel.add(startGameLabel);

		JPanel playerNamesPanel = new JPanel();
		playerNamesPanel.setBackground(Color.LIGHT_GRAY);
		playerNamesPanel.setBounds(200, 450, 800, 300);
		playerSelect.add(playerNamesPanel);
		playerNamesPanel.setLayout(null);

		playerOneText.setBounds(0, 0, 300, 80);
		playerOneText.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerNamesPanel.add(playerOneText);

		JLabel player1Text = new JLabel("Name of Player 1:");
		player1Text.setFont(new Font("Tahoma", Font.PLAIN, 30));
		playerOneText.add(player1Text);

		player1TextField = new JTextField();
		player1TextField.setText("");
		playerOneText.add(player1TextField);
		player1TextField.setColumns(10);

		playerTwoText.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerTwoText.setBounds(500, 0, 300, 80);
		playerNamesPanel.add(playerTwoText);

		JLabel player2Text = new JLabel("Name of Player 2:");
		player2Text.setFont(new Font("Tahoma", Font.PLAIN, 30));
		playerTwoText.add(player2Text);

		player2TextField = new JTextField();
		player2TextField.setText("");
		player2TextField.setColumns(10);
		playerTwoText.add(player2TextField);

		playerThreeText.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerThreeText.setBounds(0, 120, 300, 80);
		playerNamesPanel.add(playerThreeText);

		JLabel player3Text = new JLabel("Name of Player 3:");
		player3Text.setFont(new Font("Tahoma", Font.PLAIN, 30));
		playerThreeText.add(player3Text);

		player3TextField = new JTextField();
		player3TextField.setText("");
		player3TextField.setColumns(10);
		playerThreeText.add(player3TextField);

		playerFourText.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerFourText.setBounds(500, 120, 300, 80);
		playerNamesPanel.add(playerFourText);

		JLabel player4Text = new JLabel("Name of Player 4:");
		player4Text.setFont(new Font("Tahoma", Font.PLAIN, 30));
		playerFourText.add(player4Text);

		player4TextField = new JTextField();
		player4TextField.setColumns(10);
		playerFourText.add(player4TextField);

		playerFiveText.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerFiveText.setBounds(250, 220, 300, 80);
		playerNamesPanel.add(playerFiveText);

		JLabel player5Text = new JLabel("Name of Player 5:");
		player5Text.setFont(new Font("Tahoma", Font.PLAIN, 30));
		playerFiveText.add(player5Text);

		player5TextField = new JTextField();
		player5TextField.setColumns(10);
		playerFiveText.add(player5TextField);
		this.playerWonLabell = new JLabel("playerName");
		mapPanel.setLayout(null);

		player1Name.setBackground(Color.LIGHT_GRAY);
		player1Name.setForeground(Color.CYAN);
		player1Name.setFont(new Font("Tahoma", Font.PLAIN, 30));
		player1Name.setHorizontalAlignment(SwingConstants.CENTER);
		player1Name.setEditable(false);
		player1Name.setBounds(100, 40, 175, 60);
		mapPanel.add(player1Name);

		player2Name.setHorizontalAlignment(SwingConstants.CENTER);
		player2Name.setForeground(Color.MAGENTA);
		player2Name.setFont(new Font("Tahoma", Font.PLAIN, 30));
		player2Name.setEditable(false);
		player2Name.setBackground(Color.LIGHT_GRAY);
		player2Name.setBounds(300, 40, 175, 60);
		mapPanel.add(player2Name);

		player3Name.setHorizontalAlignment(SwingConstants.CENTER);
		player3Name.setForeground(Color.GREEN);
		player3Name.setFont(new Font("Tahoma", Font.PLAIN, 30));
		player3Name.setEditable(false);
		player3Name.setBackground(Color.LIGHT_GRAY);
		player3Name.setBounds(500, 40, 175, 60);
		mapPanel.add(player3Name);

		player4Name.setHorizontalAlignment(SwingConstants.CENTER);
		player4Name.setForeground(Color.blue);
		player4Name.setFont(new Font("Tahoma", Font.PLAIN, 30));
		player4Name.setEditable(false);
		player4Name.setBackground(Color.LIGHT_GRAY);
		player4Name.setBounds(700, 40, 175, 60);
		mapPanel.add(player4Name);

		player5Name.setHorizontalAlignment(SwingConstants.CENTER);
		player5Name.setForeground(Color.red);
		player5Name.setFont(new Font("Tahoma", Font.PLAIN, 30));
		player5Name.setEditable(false);
		player5Name.setBackground(Color.LIGHT_GRAY);
		player5Name.setBounds(900, 40, 175, 60);
		mapPanel.add(player5Name);

		arrow0 = new JPanel();
		arrow1 = new JPanel();
		arrow2 = new JPanel();
		unitDisplay = new JPanel();

		numberOfTroops.setFont(new Font("Tahoma", Font.PLAIN, 20));

		int yM = 30;

		unitDisplay.setVisible(true);

		JPanel panel_15 = new JPanel();
		panel_15.setBackground(Color.LIGHT_GRAY);
		panel_15.setBounds(680, buttonImage.getHeight() + 240 - yM, 200, 31);
		mapPanel.add(panel_15);
		panel_15.setLayout(null);

		obtainTroopLabel = new JLabel("");
		obtainTroopLabel.setBounds(0, 0, 200, 31);
		panel_15.add(obtainTroopLabel);
		obtainTroopLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JPanel panel_16 = new JPanel();
		panel_16.setBackground(Color.LIGHT_GRAY);
		panel_16.setBounds(680, buttonImage.getHeight() + 280 - yM, 200, 31);
		mapPanel.add(panel_16);
		panel_16.setLayout(null);

		attackStageLabel = new JLabel("");
		attackStageLabel.setBounds(0, 0, 200, 31);
		panel_16.add(attackStageLabel);
		attackStageLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		attackStageLabel.setVisible(false);

		JPanel panel_17 = new JPanel();
		panel_17.setBackground(Color.LIGHT_GRAY);
		panel_17.setBounds(680, buttonImage.getHeight() + 320 - yM, 200, 31);
		mapPanel.add(panel_17);
		panel_17.setLayout(null);

		moveStageLabel = new JLabel("");
		moveStageLabel.setBounds(0, 0, 200, 31);
		panel_17.add(moveStageLabel);
		moveStageLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		moveStageLabel.setVisible(false);

		arrow0.setBackground(Color.LIGHT_GRAY);
		arrow0.setBounds(640, buttonImage.getHeight() + 240 - yM, 30, 30);
		mapPanel.add(arrow0);
		arrow0.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("resources/GimpFiles/arrow.png"));
		lblNewLabel_3.setBounds(0, 0, 30, 30);
		arrow0.add(lblNewLabel_3);

		arrow1.setBounds(640, buttonImage.getHeight() + 280 - yM, 30, 30);
		mapPanel.add(arrow1);
		arrow1.setLayout(null);
		arrow1.setBackground(Color.LIGHT_GRAY);

		JLabel label_7 = new JLabel("");
		label_7.setIcon(new ImageIcon("resources/GimpFiles/arrow.png"));
		label_7.setBounds(0, 0, 30, 30);
		arrow1.add(label_7);

		arrow2.setLayout(null);
		arrow2.setBackground(Color.LIGHT_GRAY);
		arrow2.setBounds(640, buttonImage.getHeight() + 320 - yM, 30, 30);
		mapPanel.add(arrow2);

		JLabel label_8 = new JLabel("");
		label_8.setIcon(new ImageIcon("resources/GimpFiles/arrow.png"));

		label_8.setBounds(0, 0, 30, 30);
		arrow2.add(label_8);

		unitDisplay.setBackground(Color.LIGHT_GRAY);
		unitDisplay.setBounds(410, buttonImage.getHeight() + 240 - yM, 220, 30);
		mapPanel.add(unitDisplay);
		unitDisplay.setLayout(null);

		JLabel troopRemainingLabel = new JLabel("Troops Remaining: ");
		troopRemainingLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		troopRemainingLabel.setBounds(0, 0, 174, 30);
		unitDisplay.add(troopRemainingLabel);

		numberOfTroops.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfTroops.setBounds(175, 0, 45, 30);
		unitDisplay.add(numberOfTroops);

		JPanel Country1Display = new JPanel();
		Country1Display.setOpaque(false);
		Country1Display.setBounds(100, buttonImage.getHeight() + 240 - yM, 300, 35);
		mapPanel.add(Country1Display);

		JLabel Country1Label = new JLabel("Country 1:");
		Country1Label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Country1Display.add(Country1Label);
		country1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		country1.setHorizontalAlignment(SwingConstants.LEADING);
		Country1Display.add(country1);
		Country1Display.setLayout(new GridLayout(1, 2));
		JPanel Country2Display = new JPanel();
		Country2Display.setOpaque(false);
		Country2Display.setBounds(100, buttonImage.getHeight() + 290 - yM, 300, 35);
		mapPanel.add(Country2Display);

		JLabel Country2Label = new JLabel("Country 2:");
		Country2Label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Country2Display.add(Country2Label);

		country2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		country2.setHorizontalAlignment(SwingConstants.LEADING);
		Country2Display.add(country2);
		Country2Display.setLayout(new GridLayout(1, 2));
		resultsScreen.setBackground(Color.LIGHT_GRAY);

		resultsScreen.setLayout(null);

		JPanel playerPanelWin = new JPanel();
		playerPanelWin.setBackground(Color.RED);
		playerPanelWin.setBounds(300, 100, 600, 150);
		resultsScreen.add(playerPanelWin);
		playerPanelWin.setLayout(null);

		playerWonLabell.setForeground(Color.WHITE);
		playerWonLabell.setBounds(0, 0, 600, 150);
		playerPanelWin.add(playerWonLabell);
		playerWonLabell.setFont(new Font("Tahoma", Font.PLAIN, 70));
		playerWonLabell.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel playerWinTextPanel = new JPanel();
		playerWinTextPanel.setBackground(Color.RED);
		playerWinTextPanel.setBounds(300, 280, 600, 75);
		resultsScreen.add(playerWinTextPanel);
		playerWinTextPanel.setLayout(null);

		JLabel playerWinTextLabel = new JLabel("Has won the game!");
		playerWinTextLabel.setForeground(Color.WHITE);
		playerWinTextLabel.setBounds(0, 0, 600, 75);
		playerWinTextPanel.add(playerWinTextLabel);
		playerWinTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));
		playerWinTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel label_12 = new JLabel("");
		label_12.setIcon(new ImageIcon("resources/GimpFiles/Soldiers.png"));
		label_12.setBounds(300, 400, 600, 321);
		resultsScreen.add(label_12);

		cardPanel = new JPanel();
		mapPanel.add(cardPanel);
		cardPanel.setBounds(100, buttonImage.getHeight() + 170 - 10, 900, 40);
		cardPanel.setBackground(Color.LIGHT_GRAY);
		cardPanel.setLayout(null);

		nextStage = new JButton();
		nextStage.setBackground(Color.RED);
		nextStage.setBounds(900 + 40, buttonImage.getHeight() + 300 - yM, 170, 50);
		mapPanel.add(nextStage);
		nextStage.setLayout(null);

		JLabel nextStageLabel = new JLabel("Next Stage");
		nextStageLabel.setForeground(Color.WHITE);
		nextStageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nextStageLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		nextStageLabel.setBounds(0, 0, 170, 50);
		nextStage.add(nextStageLabel);

		nextStage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (troopsLeft == 0) {
					gameState.setCurrPhase((gameState.getCurrPhase() + 1) % 3);
					if (gameState.getFirstRound() > 1) {
						if (gameState.getCurrPhase() == 0) {
							gameState.getCurrPlayer().getPlayerTextName().setBackground(Color.LIGHT_GRAY);
							gameState.getCurrPlayer().setHasMoved(false);
							gameState.setNextPlayer();
							lp.addLog("=====It's " + gameState.getCurrPlayer().getName() + "'s turn.=====");
							lp.addLog("It is the Reinforcement phase!");
							gameState.getCurrPlayer().getPlayerTextName().setBackground(Color.GRAY);
							gameState.setCountry1(null);
							gameState.setCountry2(null);

							cardViewUpdate();
							troopsLeft = gameState.getCurrPlayer().getBonus();
							numberOfTroops.setText(Integer.toString(troopsLeft));
							cardViewUpdate();
						}
						if (gameState.getCurrPhase() == 1 && !gameState.getCurrPlayer().checkIfCanAttack()) {
							gameState.setCurrPhase(2);
						}
						if (gameState.getCurrPhase() == 2) {
							gameState.getCurrPlayer().giveCards();
							cardViewUpdate();
						}
					}
				}
			}
		});

		for (CountryButton countryButton : g.getButtons()) {
			countryButton.b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (gameState.getFirstRound() == 1) {
						arrow0.setVisible(true);
						arrow1.setVisible(false);
						arrow2.setVisible(false);

						Player currentPlayer = gameState.getCurrPlayer();

						if (currentPlayer == countryButton.country.getPlayer() && currentPlayer.getInitTroop() > 0) {
							currentPlayer.setInitTroop(currentPlayer.getInitTroop() - 1);
							currentPlayer.addInfantry(countryButton.country);
							gameState.updateCountryLabels();
							lp.addLog(currentPlayer.getName() + " adds one troop to " + countryButton.name);
						} else {
							JOptionPane.showMessageDialog(countryButton.b, "That country does not belong to you.");
							lp.addLog("That country does not belong to you.");
							return;
						}

						gameState.getCurrPlayer().getPlayerTextName().setBackground(Color.LIGHT_GRAY);
						gameState.setNextPlayer();
						lp.addLog("=====It's " + gameState.getCurrPlayer().getName() + "'s turn.=====");
						numberOfTroops.setText(Integer.toString(gameState.getCurrPlayer().getInitTroop()));
						gameState.getCurrPlayer().getPlayerTextName().setBackground(Color.GRAY);
						gameState.setCountry1(null);
						gameState.setCountry2(null);

						System.out.println("current player name:" + gameState.getCurrPlayer().getName());
						System.out.println("current round num:" + gameState.getFirstRound());
						System.out.println("current phase:" + gameState.getCurrPhase());

						boolean isFinished = true;
						for (Player p : gameState.getAllPlayers().getPlayers()) {
							if (p.getInitTroop() > 0)
								isFinished = false;
						}
						if (isFinished) {
							gameState.setFirstRound(gameState.getFirstRound() + 1);
							lp.addLog("It is the Reinforcement phase!");

							System.out.println("round +1 !!!");
							troopsLeft = gameState.getCurrPlayer().getBonus();
							numberOfTroops.setText(Integer.toString(troopsLeft));
							attackStageLabel.setVisible(true);
							moveStageLabel.setVisible(true);
						}

					} else if (gameState.getFirstRound() > 1) {
						if (gameState.getCurrPhase() == 0) {
							System.out.println("click on button!");
							Player currentPlayer = gameState.getCurrPlayer();
							if (gameState.getCurrPlayer() == countryButton.country.getPlayer() && troopsLeft > 0) {
								troopsLeft--;
								currentPlayer.addInfantry(countryButton.country);
								numberOfTroops.setText(Integer.toString(troopsLeft));
								gameState.updateCountryLabels();
								lp.addLog(currentPlayer.getName() + " adds one troop to " + countryButton.name);
							} else if (troopsLeft == 0) {
								JOptionPane.showMessageDialog(countryButton.b, "Out of troops to add");
								lp.addLog("Out of troops to add");
							} else {
								JOptionPane.showMessageDialog(countryButton.b, "That country does not belong to you.");
								lp.addLog("That country does not belong to you.");
							}
						} else if (gameState.getCurrPhase() == 1) {
							if (countryButton.country.getPlayer() == gameState.getCurrPlayer()
									&& gameState.getCountry1() == null) {
								if (countryButton.country.getTroops().size() > 1) {
									gameState.setCountry1(countryButton.country);
									country1.setText(gameState.getCountry1().getName());
									cancelCountryButton.setVisible(true);
									cancelCountryButton.setEnabled(true);
								} else {
									gameState.setCountry1(null);
									JOptionPane.showMessageDialog(countryButton.b,
											"Country does not have enough troops to attack");
									lp.addLog("Country does not have enough troops to attack");
									country1.setText((String) null);
								}
							} else if (countryButton.country.getPlayer() != gameState.getCurrPlayer()
									&& gameState.getCountry1() != null && gameState.getCountry2() == null) {
								gameState.setCountry2(countryButton.country);
								if (gameState.getCountry1().checkAdjacent(gameState.getCountry2())) {
									cancelCountryButton.setVisible(false);
									cancelCountryButton.setEnabled(false);
									country2.setText(gameState.getCountry2().getName());

									if (gameState.getCountry1().getPlayer() == gameState.getCurrPlayer()
											&& gameState.getCountry2().getPlayer() != gameState.getCurrPlayer()) {
										JPanel numdice1 = new JPanel();
										JLabel label = new JLabel("Attacker selects how many dice to roll");
										numdice1.add(label);
										DefaultComboBoxModel<String> select1 = new DefaultComboBoxModel<>();
										for (int i = Math.min(gameState.getCountry1().getTroops().size() - 1,
												3); i >=1 ; i--) {
											select1.addElement(Integer.toString(i));
										}
										JComboBox<String> list1 = new JComboBox<>(select1);
										numdice1.add(list1);
										int message1 = -1;
										while (message1 != JOptionPane.OK_OPTION) {
											message1 = JOptionPane.showConfirmDialog(null, numdice1, "Number of Dices",
													JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
										}
										int decision1 = Integer.parseInt(list1.getSelectedItem().toString());
										lp.addLog("Attacker chooses " + decision1 + " dices!");

										numdice1.remove(label);
										numdice1.remove(list1);

										numdice1.add(new JLabel("Defender selects how many dice to roll"));
										DefaultComboBoxModel<String> select2 = new DefaultComboBoxModel<>();
										for (int i = 1; i <= Math.min(gameState.getCountry2().getTroops().size(),
												2); i++) {
											select2.addElement(Integer.toString(i));
										}
										JComboBox<String> list2 = new JComboBox<>(select2);
										numdice1.add(list2);
										int message2 = -1;
										while (message2 != JOptionPane.OK_OPTION) {
											message2 = JOptionPane.showConfirmDialog(null, numdice1, "Number of Dices",
													JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
										}
										int decision2 = Integer.parseInt(list2.getSelectedItem().toString());
										lp.addLog("Defender chooses " + decision2 + " dices!");

										try {
											if(gameState.getCurrPlayer().attack(gameState.getCountry1(),
													gameState.getCountry2(), decision1, decision2)){
												System.out.println("conquest!!!!!");
												int moveNum = 0;
												JPanel numPanel = new JPanel();
												numPanel.add(new JLabel("Congrats you conquered " + gameState.getCountry2().getName() + " with " + gameState.getCountry1().getName()
														+ ". How many troops would you like to add?"));
												lp.addLog("Congrats " + gameState.getCurrPlayer().getName() + " conquered " + gameState.getCountry2().getName() + " with " + gameState.getCountry1().getName());
												DefaultComboBoxModel<String> selection = new DefaultComboBoxModel<String>();
												for (int i = gameState.getCountry1().getTroops().size() - 1; i >=1 ; i--) {
													selection.addElement(Integer.toString(i));
												}
												JComboBox<String> comboBox = new JComboBox<String>(selection);
												numPanel.add(comboBox);
												int result = JOptionPane.showConfirmDialog(null, numPanel, "Number of Troops", JOptionPane.OK_CANCEL_OPTION,
														JOptionPane.QUESTION_MESSAGE);
												if (result == JOptionPane.CANCEL_OPTION) {
													moveNum = 1; // if they cancel it will just move one
												} else {
													moveNum = Integer.parseInt(comboBox.getSelectedItem().toString());
												}
												moveNum = Integer.parseInt(comboBox.getSelectedItem().toString());
												gameState.getCountry2().addInfrantry(moveNum);
												gameState.getCountry1().removeTroops(moveNum); // removing troops from the origin country
												lp.addLog(gameState.getCurrPlayer().getName() + " leaves " + moveNum + " troops!");
												
												if(gameState.getCurrPlayer().checkWinGame()){
													initializeEndGame();
												}
											}
										} catch (Exception e1) {
											e1.printStackTrace();
											lp.addLog(e1.getMessage());
										}
									}

									if (!gameState.getCurrPlayer().checkIfCanAttack()) {
										nextStage.doClick();
									}
									gameState.updateCountryLabels();
									gameState.setCountry1(null);
									gameState.setCountry2(null);
									country1.setText((String) null);
									country2.setText((String) null);
								} else {
									JOptionPane.showMessageDialog(countryButton.b,
											"Countries are not adjacent, select the second country again.");
									lp.addLog("Countries are not adjacent, select the second country again.");
									gameState.setCountry2(null);
									country2.setText((String) null);
								}
							}
						} else if (!gameState.getCurrPlayer().isHasMoved()) {
							if (gameState.getCountry1() == null) {
								gameState.setCountry1(countryButton.country);
								country1.setText(gameState.getCountry1().getName());
								if (gameState.getCountry1().getTroops().size() == 1) {
									JOptionPane.showMessageDialog(countryButton.b,
											"Troops cannot be moved from here because "
													+ gameState.getCountry1().getName() + " only has 1 troop.");
									lp.addLog("Troops cannot be moved from here because "
											+ gameState.getCountry1().getName() + " only has 1 troop.");
									gameState.setCountry1(null);
									country1.setText((String) null);
								} else {
									cancelCountryButton.setVisible(true);
									cancelCountryButton.setEnabled(true);
								}
							} else if (countryButton.country.getPlayer() == gameState.getCurrPlayer()
									&& gameState.getCountry1() != null && gameState.getCountry2() == null) {
								gameState.setCountry2(countryButton.country);
								country2.setText(gameState.getCountry2().getName());
								if (gameState.getCountry1().checkAdjacent(gameState.getCountry2())) {
									cancelCountryButton.setVisible(false);
									cancelCountryButton.setEnabled(false);
									JPanel numPanel = new JPanel();
									numPanel.add(new JLabel("Select how many troops to add"));
									DefaultComboBoxModel<String> selection = new DefaultComboBoxModel<String>();
									for (int i = 1; i < gameState.getCountry1().getTroops().size(); i++) {
										selection.addElement(Integer.toString(i));
									}
									JComboBox<String> comboBox = new JComboBox<String>(selection);
									numPanel.add(comboBox);
									int result = JOptionPane.showConfirmDialog(null, numPanel, "Number of Troops",
											JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
									if (result == JOptionPane.OK_OPTION) {
										gameState.getCurrPlayer().moveTroops(gameState.getCountry1(),
												gameState.getCountry2(),
												Integer.parseInt(comboBox.getSelectedItem().toString()));
										gameState.getCurrPlayer().setHasMoved(true);
										gameState.updateCountryLabels();
										gameState.setCountry1(null);
										gameState.setCountry2(null);
										country1.setText((String) null);
										country2.setText((String) null);
									} else {
										JOptionPane.showMessageDialog(countryButton.b, "Move was cancelled.");
										lp.addLog("Move was cancelled.");
										gameState.setCountry1(null);
										gameState.setCountry2(null);
										country1.setText((String) null);
										country2.setText((String) null);
									}
								} else {
									JOptionPane.showMessageDialog(countryButton.b,
											"Countries are not adjacent, select the second country again.");
									lp.addLog("Countries are not adjacent, select the second country again.");
									gameState.setCountry2(null);
									country2.setText((String) null);
								}
								gameState.updateCountryLabels();
								gameState.setCountry1(null);
								gameState.setCountry2(null);
								country1.setText((String) null);
								country2.setText((String) null);
							}
						}
					}
				}
			});
		}

	}

	/**
	 * Method to initial the interface when the game end
	 */
	public void initializeEndGame() {
		playerWonLabell.setText(gameState.getCurrPlayer().getName());
		lp.addLog(gameState.getCurrPlayer().getName() + " has won the game!");
		cardLayout.show(cards, "Results");
	}

	/**
	 * Method to connect the observer object to the model object
	 */
	public void registerObserver() {
		if (gameState.getAllPlayers().getPlayers().size() > 0) {
			for (Player p : gameState.getAllPlayers().getPlayers()) {
				p.addObserver(domiInfoPanel);
				p.addObserver(this);
				p.addObserver(cRatioPanel);
			}
		}
		gameState.addObserver(this);
	}

	/**
	 * Method does updates to the view offered to the player
	 * 
	 * @param o
	 *            the observable object with Observable type
	 * @param arg
	 *            an argument passed to the notifyObservers method with Object type
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Player) {
			cardViewUpdate();
		} else if (o instanceof GameState) {
			phaseViewUpdate();
		}
		System.out.println("state changed!");

	}

	/**
	 * Method to update the hand cards view
	 */
	public void cardViewUpdate() {
		Player currentPlayer = gameState.getCurrPlayer();
		if (currentPlayer != null) {
			ArrayList<Card> onHand = currentPlayer.getOnHand();
			cardPanel.removeAll();
			for (int i = 0; i < onHand.size(); i++) {
				JLabel card = new JLabel();
				switch (onHand.get(i).getType()) {
				case 0:
					card.setIcon(new ImageIcon("resources/CardImages/type1.png"));
					break;
				case 1:
					card.setIcon(new ImageIcon("resources/CardImages/type2.png"));
					break;
				case 2:
					card.setIcon(new ImageIcon("resources/CardImages/type3.png"));
					break;
				}
				card.setBounds(80 * i - 40 + 40, 0, 60, 40);
				cardPanel.add(card);
			}
			System.out.println("there's " + cardPanel.getComponentCount() + " cards!");
			cardPanel.repaint();
		}
	}

	/**
	 * Method to update the phase view
	 */
	private void phaseViewUpdate() {
		if (gameState.getFirstRound() == 1) {
			obtainTroopLabel.setVisible(true);
			attackStageLabel.setVisible(false);
			moveStageLabel.setVisible(false);
			arrow0.setVisible(true);
			arrow1.setVisible(false);
			arrow2.setVisible(false);
			obtainTroopLabel.setText("Startup phase");
			unitDisplay.setVisible(true);

		} else {
			attackStageLabel.setVisible(true);
			moveStageLabel.setVisible(true);
			obtainTroopLabel.setText("Reinforcement phase");
			attackStageLabel.setText("Attack phase");
			moveStageLabel.setText("Fortification phase");
			if (gameState.getCurrPhase() == 0) {
				arrow0.setVisible(true);
				arrow1.setVisible(false);
				arrow2.setVisible(false);
				unitDisplay.setVisible(true);
			} else if (gameState.getCurrPhase() == 1) {
				arrow0.setVisible(false);
				arrow1.setVisible(true);
				arrow2.setVisible(false);
				unitDisplay.setVisible(false);
				lp.addLog("It is the Attack phase!");
			} else if (gameState.getCurrPhase() == 2) {
				arrow0.setVisible(false);
				arrow1.setVisible(false);
				arrow2.setVisible(true);
				unitDisplay.setVisible(false);
				lp.addLog("It is the Fortification phase!");
			}
		}
	}

}
