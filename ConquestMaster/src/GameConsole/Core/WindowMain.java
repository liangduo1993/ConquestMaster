package GameConsole.Core;

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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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

import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;
import GameConsole.Strategy.AggressiveStrategy;
import GameConsole.Strategy.BenevolentStrategy;
import GameConsole.Strategy.CheaterStrategy;
import GameConsole.Strategy.HumanStrategy;
import GameConsole.Strategy.RandomStrategy;
import GameConsole.View.CardExchangeView;
import GameConsole.View.ConquestRatio;
import GameConsole.View.CountryButton;
import GameConsole.View.DomiInfoPanel;
import GameConsole.View.LogPanel;
import GameConsole.View.MapDisplayer;
import GameConsole.View.PhaseView;
import GameConsole.View.TournamentGamePanel;
import GameConsole.View.TournamentResultPanel;

/**
 * This class is to display the interface of the game where the player can
 * visually perform game operations.
 */
public class WindowMain {
	private JFrame frame1;
	private JPanel cards;
	private CardExchangeView cardPanel;
	private PhaseView phaseView;
	private CardLayout cardLayout;
	public JPanel mapPanel, Country1Display, Country2Display;
	private DomiInfoPanel domiInfoPanel;
	private JTextField player1TextField, player2TextField, player3TextField, player4TextField, player5TextField;
	private JLabel playerWonLabell;
	private ConquestRatio cRatioPanel;
	private LogPanel lp = LogPanel.getInstance();
	public BufferedImage buttonImage;
	private JButton nextStage, cancelCountryButton;
	public JPanel unitDisplay;
	public JLabel numberOfTroops, country1, country2;
	private JScrollPane mainScroll;
	public MapDisplayer mapDisplayer;

	private GameState gameState;
	private JFileChooser fc;
	public int troopsLeft;

	private JPanel mainScreen, playerSelect, resultsScreen;
	private JPanel tournamentResultPanel;

	/**
	 * Constructor method
	 * 
	 * @throws IOException
	 */
	public WindowMain() throws IOException {
		init();
	}

	/**
	 * Method to initiate the game.
	 */
	private void init() {
		frame1 = new JFrame();
		frame1.setResizable(false);
		frame1.setVisible(true);
		frame1.setTitle("Risk - The Game of Global Domination");
		frame1.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame1.setBounds(100, 100, 1200, 900);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.getContentPane().setLayout(new CardLayout(0, 0));
		cards = new JPanel(new CardLayout());
		cards.setBounds(100, 100, 1200, 900);

		cards.setVisible(true);
		mainScreen = new JPanel();
		playerSelect = new JPanel();
		mapPanel = new JPanel();
		tournamentResultPanel = new JPanel();
		mapPanel.setBackground(Color.LIGHT_GRAY);

		resultsScreen = new JPanel();
		cards.add(mainScreen, "Main Screen");
		cards.add(playerSelect, "Player Selection");
		cards.add(mapPanel, "Game");
		cards.add(resultsScreen, "Results");
		cards.add(tournamentResultPanel, "tournamentResultPanel");
		frame1.getContentPane().add(cards);

		mainScreen.setBackground(Color.LIGHT_GRAY);
		mainScreen.setLayout(null);

		this.cardLayout = (CardLayout) cards.getLayout();
		JPanel singleGame = new JPanel();
		singleGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				openMapFile();
			}
		});

		JPanel tournamentGame = new JPanel();
		tournamentGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TournamentGamePanel tgp = new TournamentGamePanel();
				cards.add(tgp, "Tournament Mode");
				cardLayout.show(cards, "Tournament Mode");
				tgp.startGameButt.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						ArrayList<String> strategies = new ArrayList<>();
						ArrayList<String> paths = new ArrayList<>();
						ArrayList<String> probs = new ArrayList<>();

						if (tgp.path1 != null && tgp.path1.endsWith(".map")) {
							paths.add(tgp.path1);
						}
						if (tgp.path2 != null && tgp.path2.endsWith(".map")) {
							paths.add(tgp.path2);
						}
						if (tgp.path3 != null && tgp.path3.endsWith(".map")) {
							paths.add(tgp.path3);
						}
						if (tgp.path4 != null && tgp.path4.endsWith(".map")) {
							paths.add(tgp.path4);
						}
						if (tgp.path5 != null && tgp.path5.endsWith(".map")) {
							paths.add(tgp.path5);
						}

						if (paths.size() == 0)
							probs.add("Please choose at least one map!");

						String strategy1 = (String) tgp.comboBox_6.getSelectedItem();
						String strategy2 = (String) tgp.comboBox_7.getSelectedItem();
						String strategy3 = (String) tgp.comboBox_8.getSelectedItem();
						String strategy4 = (String) tgp.comboBox_9.getSelectedItem();
						if (!strategy1.equals("none"))
							strategies.add(strategy1);
						if (!strategy2.equals("none"))
							strategies.add(strategy2);
						if (strategy3 != null && !strategy3.equals("none"))
							strategies.add(strategy3);
						if (strategy4 != null && !strategy4.equals("none"))
							strategies.add(strategy4);

						if (strategies.size() < 2)
							probs.add("There must be at least two players");

						int gameTimes = Integer.parseInt((String) tgp.comboBox.getSelectedItem());

						String gameTurnsString = tgp.textField.getText();
						if (gameTurnsString.equals(""))
							probs.add("Please input game turns!");
						int gameTurns = 0;
						try {
							gameTurns = Integer.parseInt(gameTurnsString);
							if (gameTurns < 10 || gameTurns > 50)
								probs.add("Please input correct game turns from 10 to 50!");
						} catch (NumberFormatException numError) {
							probs.add("The game turns must be a number!");
						}

						System.out.println(probs.size());
						if (!probs.isEmpty()) {
							JOptionPane.showMessageDialog(null, probs.get(0));
							return;
						} else {

							Object[][] results = new Object[paths.size()][gameTimes];

							for (int j = 0; j < paths.size(); j++) {
								String mapPath = paths.get(j);
								for (int i = 0; i < gameTimes; i++) {
									String result = newGameState(mapPath, strategies, gameTurns);
									System.out.println("============");
									System.out.println(result);
									System.out.println("============");
									results[j][i] = result;
								}
							}

							TournamentResultPanel trp = new TournamentResultPanel(results);
							cards.add(trp, "tournamentResultPanel");
							cardLayout.show(cards, "tournamentResultPanel");
							trp.returnToMainFrame.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									cardLayout.show(cards, "Main Screen");
								}
							});

						}
						// if(!tgp.comboBox_6.getSelectedItem().equals("none")&&!tgp.comboBox_7.getSelectedItem().equals("none")){
						// String strategy1 = (String)
						// tgp.comboBox_6.getSelectedItem();
						// String strategy2 = (String)
						// tgp.comboBox_7.getSelectedItem();
						// String strategy3 = (String)
						// tgp.comboBox_8.getSelectedItem();
						// String strategy4 = (String)
						// tgp.comboBox_9.getSelectedItem();
						// strategies.add(strategy1);
						// strategies.add(strategy2);
						// strategies.add(strategy3);
						// strategies.add(strategy4);
						// for(int i=0;i<strategies.size();i++){
						// System.out.println(strategies.get(i));
						// }
						//
						//
						// //System.out.println(gameTimes);
						//
						//
						// if(!gameTurnsString.equals("")){
						// int gameTurns =
						// Integer.parseInt(tgp.textField.getText());
						//
						// if(gameTurns>=10&&gameTurns<=50){
						//
						//
						// for (int i = 0; i < gameTimes; i++) {
						// newGameState(path, strategies, gameTurns);
						// }
						//
						// }else{
						// JOptionPane.showMessageDialog(null, "Please input
						// correct game turns from 10 to 50!");
						// }
						// }else{
						// JOptionPane.showMessageDialog(null, "Please input
						// game turns");
						// }
						// }else{
						// JOptionPane.showMessageDialog(null, "there must be at
						// least two players");
						// }
						//

					}
				});
				tgp.CancelButt.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						cardLayout.show(cards, "Main Screen");
					}
				});
			}
		});
		tournamentGame.setBackground(new Color(255, 0, 0));
		tournamentGame.setBounds(451, 680 + 50, 317, 77);
		mainScreen.add(tournamentGame);
		tournamentGame.setLayout(null);
		JLabel tournament = new JLabel("Tournament Mode");
		tournament.setForeground(new Color(255, 255, 255));
		tournament.setFont(new Font("Tahoma", Font.PLAIN, 30));
		tournament.setHorizontalAlignment(SwingConstants.CENTER);
		tournament.setBounds(0, 0, 317, 77);
		tournamentGame.add(tournament);

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
		singleGame.setBackground(new Color(255, 0, 0));
		singleGame.setBounds(451, 680 - 50, 317, 77);
		mainScreen.add(singleGame);
		singleGame.setLayout(null);

		JLabel labelStart = new JLabel("Single Game Mode");
		labelStart.setForeground(new Color(255, 255, 255));
		labelStart.setFont(new Font("Tahoma", Font.PLAIN, 30));
		labelStart.setHorizontalAlignment(SwingConstants.CENTER);
		labelStart.setBounds(0, 0, 317, 77);
		singleGame.add(labelStart);

		JLabel label = new JLabel("");
		label.setBounds(0, 0, 1194, 860);
		mainScreen.add(label);
		label.setIcon(new ImageIcon("resources/GimpFiles/StartGame.png"));

	}

	/**
	 * To open a map file in the main window
	 */
	public void openMapFile() {
		fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String path = file.getAbsolutePath();
			try {
				gameState = new GameState(this, path);
				cardLayout.show(cards, "Player Selection");
				initialize();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this.frame1, "The map applies warn & the map is not valid!");
				e1.printStackTrace();
				cardLayout.show(cards, "Main Screen");
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
		phaseView = new PhaseView(gameState, this);
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

		country1 = new JLabel((String) null);
		country2 = new JLabel((String) null);

		mapDisplayer = new MapDisplayer(mapPanel, gameState.getWorld());
		this.buttonImage = mapDisplayer.getButtonImage();
		System.out.println("button nums: " + mapDisplayer.getButtons().size());

		cancelCountryButton = new JButton("Cancel\r\n");
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

				addMenu();

				mapPanel.add(lp);
				if (playerOneText.isVisible()) {
					Player p1 = new Player(player1TextField.getText(), Color.cyan, gameState, new HumanStrategy());
					gameState.addPlayer(p1);
					gameState.setCurrPlayer(p1);
				}
				if (playerTwoText.isVisible()) {
					// Player p2 = new Player(player2TextField.getText(),
					// Color.magenta, gameState, new HumanStrategy());
					Player p2 = new Player(player2TextField.getText(), Color.magenta, gameState,
							new AggressiveStrategy());
					gameState.addPlayer(p2);
				}
				if (playerThreeText.isVisible()) {
					// Player p3 = new Player(player3TextField.getText(),
					// Color.green, gameState, new HumanStrategy());
					Player p3 = new Player(player3TextField.getText(), Color.green, gameState,
							new BenevolentStrategy());
					gameState.addPlayer(p3);
				}
				if (playerFourText.isVisible()) {
					// Player p4 = new Player(player4TextField.getText(),
					// Color.blue, gameState, new HumanStrategy());
					Player p4 = new Player(player4TextField.getText(), Color.blue, gameState, new RandomStrategy());
					gameState.addPlayer(p4);
				}
				if (playerFiveText.isVisible()) {
					// Player p5 = new Player(player5TextField.getText(),
					// Color.red, gameState, new HumanStrategy());
					Player p5 = new Player(player5TextField.getText(), Color.red, gameState, new CheaterStrategy());
					gameState.addPlayer(p5);
				}
				gameState.gameStart(true);

				domiInfoPanel = new DomiInfoPanel(gameState);
				domiInfoPanel.setPreferredSize(new Dimension(domiInfoPanel.getWidth(), domiInfoPanel.getHeight()));
				mainScroll = new JScrollPane();
				mainScroll.setBounds(buttonImage.getWidth() + 130, 150, 380, buttonImage.getHeight());
				mainScroll.setViewportView(domiInfoPanel);
				mapPanel.add(mainScroll);

				cRatioPanel = new ConquestRatio(gameState);
				cRatioPanel.setBounds(100, 150 - 40, gameState.getWorld().getDeck().size() * ConquestRatio.ratio, 20);
				mapPanel.add(cRatioPanel);

				registerObserver();

				for (Player p : gameState.getAllPlayers().getPlayers()) {
					p.setInitTroop(p.getBonus());
					System.out.println(p.getName() + " has: " + p.getInitTroop());
				}

				troopsLeft = gameState.getCurrPlayer().getInitTroop();
				numberOfTroops.setText("" + troopsLeft);
				System.out.println(gameState.getAllPlayers().getPlayers().size());
				if (gameState.getAllPlayers().getPlayers().size() > 0) {
					if (gameState.getAllPlayers().getPlayers().size() > 0) {
						phaseView.player1Name.setText(gameState.getAllPlayers().getPlayers().get(0).getName());
						gameState.getAllPlayers().getPlayers().get(0).setPlayerTextName(phaseView.player1Name);
						phaseView.player1Name.setVisible(true);
						phaseView.player1Name.setBackground(Color.GRAY);
					}
					if (gameState.getAllPlayers().getPlayers().size() > 1) {
						phaseView.player2Name.setText(gameState.getAllPlayers().getPlayers().get(1).getName());
						gameState.getAllPlayers().getPlayers().get(1).setPlayerTextName(phaseView.player2Name);
						phaseView.player2Name.setVisible(true);
					}
					if (gameState.getAllPlayers().getPlayers().size() > 2) {
						phaseView.player3Name.setText(gameState.getAllPlayers().getPlayers().get(2).getName());
						gameState.getAllPlayers().getPlayers().get(2).setPlayerTextName(phaseView.player3Name);
						phaseView.player3Name.setVisible(true);
					}
					if (gameState.getAllPlayers().getPlayers().size() > 3) {
						phaseView.player4Name.setText(gameState.getAllPlayers().getPlayers().get(3).getName());
						gameState.getAllPlayers().getPlayers().get(3).setPlayerTextName(phaseView.player4Name);
						phaseView.player4Name.setVisible(true);
					}
					if (gameState.getAllPlayers().getPlayers().size() > 4) {
						phaseView.player5Name.setText(gameState.getAllPlayers().getPlayers().get(4).getName());
						gameState.getAllPlayers().getPlayers().get(4).setPlayerTextName(phaseView.player5Name);
						phaseView.player5Name.setVisible(true);
					}
				}
				lp.addLog("=====It's " + gameState.getCurrPlayer().getName() + "'s turn.=====");

				// ================
				SingleGameMode singleGame = new SingleGameMode(gameState);
				singleGame.execute();
				// ================
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

		numberOfTroops = new JLabel("0");
		unitDisplay = new JPanel();
		numberOfTroops.setFont(new Font("Tahoma", Font.PLAIN, 20));

		int yM = 30;

		unitDisplay.setVisible(true);

		phaseView.phasePanel.setBounds(640, buttonImage.getHeight() + 240 - yM, 250, 250);
		mapPanel.add(phaseView.phasePanel);

		phaseView.namePanel.setBounds(100, 40, 975, 60);
		mapPanel.add(phaseView.namePanel);

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

		Country1Display = new JPanel();
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
		Country2Display = new JPanel();
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

		// cardPanel = new CardExchangeView(this.gameState);

		nextStage = new JButton();
		nextStage.setBackground(Color.RED);
		nextStage.setBounds(900 - 20, buttonImage.getHeight() + 300 - yM, 170, 50);
		mapPanel.add(nextStage);
		nextStage.setLayout(null);

		JLabel nextStageLabel = new JLabel("Next Stage");
		nextStageLabel.setForeground(Color.WHITE);
		nextStageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nextStageLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		nextStageLabel.setBounds(0, 0, 170, 50);
		nextStage.add(nextStageLabel);

		bindNextStage();

		bindButtons();

	}

	private void bindNextStage() {
		nextStage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(gameState.getCurrPhase() + "::" + gameState.getFirstRound());
				if (troopsLeft == 0) {
					gameState.setCurrPhase((gameState.getCurrPhase() + 1) % 3);
					if (gameState.getFirstRound() > 1) {
						if (gameState.getCurrPhase() == 0) {
							// gameState.getCurrPlayer().setHasMoved(false);
							// gameState.setNextPlayer();
							lp.addLog("=====It's " + gameState.getCurrPlayer().getName() + "'s turn.=====");
							lp.addLog("It is the Reinforcement phase!");

							// gameState.setCountry1(null);
							// gameState.setCountry2(null);

							// troopsLeft =
							// gameState.getCurrPlayer().getBonus();
							// numberOfTroops.setText(Integer.toString(troopsLeft));
						}
						if (gameState.getCurrPhase() == 1 && !gameState.getCurrPlayer().checkIfCanAttack()) {
							lp.addLog("Attack phase automatically skips!");
							gameState.setCurrPhase(2);
						}

					}
				}
			}
		});
	}

	private void bindButtons() {
		for (CountryButton countryButton : mapDisplayer.getButtons()) {
			countryButton.b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Player currentPlayer = gameState.getCurrPlayer();
					if (gameState.getFirstRound() == 1) {

						if (currentPlayer == countryButton.country.getPlayer() && currentPlayer.getInitTroop() > 0) {
							// currentPlayer.setInitTroop(currentPlayer.getInitTroop()
							// - 1);
							gameState.setCurrClick(countryButton.country);
							// ==========
							// currentPlayer.reinforce();
							// ==========
							// currentPlayer.addInfantry(countryButton.country);

							lp.addLog(currentPlayer.getName() + " adds one troop to " + countryButton.name);
						} else {
							JOptionPane.showMessageDialog(countryButton.b, "That country does not belong to you.");
							lp.addLog("That country does not belong to you.");
							return;
						}

						// gameState.setNextPlayer();
						// lp.addLog("=====It's " +
						// gameState.getCurrPlayer().getName() + "'s
						// turn.=====");
						// numberOfTroops.setText(Integer.toString(gameState.getCurrPlayer().getInitTroop()));
						// troopsLeft =
						// gameState.getCurrPlayer().getInitTroop();
						// gameState.setCountry1(null);
						// gameState.setCountry2(null);

						System.out.println("current player name:" + gameState.getCurrPlayer().getName());
						System.out.println("current round num:" + gameState.getFirstRound());
						System.out.println("current phase:" + gameState.getCurrPhase());

						// boolean isFinished = true;
						// for (Player p :
						// gameState.getAllPlayers().getPlayers()) {
						// if (p.getInitTroop() > 0)
						// isFinished = false;
						// }
						// if (isFinished) {
						// gameState.setFirstRound(gameState.getFirstRound() +
						// 1);
						// lp.addLog("It is the Reinforcement phase!");
						//
						// System.out.println("round +1 !!!");
						// troopsLeft = gameState.getCurrPlayer().getBonus();
						// numberOfTroops.setText(Integer.toString(troopsLeft));
						// }

					} else if (gameState.getFirstRound() > 1) {
						if (gameState.getCurrPhase() == 0) {
							System.out.println("click on button!");
							if (gameState.getCurrPlayer() == countryButton.country.getPlayer() && troopsLeft > 0) {
								troopsLeft--;
								gameState.setCurrClick(countryButton.country);
								// =============
								// currentPlayer.reinforce();
								// ===========
								// currentPlayer.reinforce(countryButton.country);
								numberOfTroops.setText(Integer.toString(troopsLeft));
								gameState.updateCountryLabels();
								lp.addLog(currentPlayer.getName() + " reinforces one troop to " + countryButton.name);
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
								if (countryButton.country.getTroopNum() > 1) {
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

										// =======
										// currentPlayer.attack();
										// =======

										// if
										// (!gameState.getCurrPlayer().checkIfCanAttack())
										// {
										// nextStage.doClick();
										// lp.addLog("Attack phase is
										// automatically skipped!");
										// }
										// gameState.updateCountryLabels();
										//

									} else {
										JOptionPane.showMessageDialog(countryButton.b,
												"Countries are not adjacent, select the second country again.");
										lp.addLog("Countries are not adjacent, select the second country again.");
										gameState.setCountry2(null);
										country2.setText((String) null);
									}
								}
							}
						} else if (!gameState.getCurrPlayer().isHasMoved()) {
							Country tempCountry1 = null;

							if (gameState.getCountry1() == null) {
								tempCountry1 = countryButton.country;
								if (tempCountry1.getTroopNum() == 1 ) {
									JOptionPane.showMessageDialog(countryButton.b,
											"Troops cannot be moved from here because "
													+ gameState.getCountry1().getName() + " only has 1 troop.");
									lp.addLog("Troops cannot be moved from here because "
											+ gameState.getCountry1().getName() + " only has 1 troop.");
								} else if(tempCountry1.getPlayer() != gameState.getCurrPlayer()){
									JOptionPane.showMessageDialog(countryButton.b,
											"Country doesn't belong to you!");
									lp.addLog("Country doesn't belong to you!");
								}else {
									cancelCountryButton.setVisible(true);
									cancelCountryButton.setEnabled(true);
									gameState.setCountry1(tempCountry1);
									country1.setText(gameState.getCountry1().getName());
								}
							} else if (countryButton.country.getPlayer() == gameState.getCurrPlayer()
									&& gameState.getCountry1() != null && gameState.getCountry2() == null) {
								gameState.setCountry2(countryButton.country);
								country2.setText(gameState.getCountry2().getName());
								if (gameState.getCountry1().checkAdjacent(gameState.getCountry2())) {
									cancelCountryButton.setVisible(false);
									cancelCountryButton.setEnabled(false);

									// currentPlayer.fortify();

								} else {
									JOptionPane.showMessageDialog(countryButton.b,
											"Countries are not adjacent, select the second country again.");
									lp.addLog("Countries are not adjacent, select the second country again.");
									gameState.setCountry2(null);
									country2.setText((String) null);
								}
								// gameState.updateCountryLabels();
								// gameState.setCountry1(null);
								// gameState.setCountry2(null);
								// country1.setText((String) null);
								// country2.setText((String) null);
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
		cardPanel = new CardExchangeView(this.gameState);
		if (gameState.getAllPlayers().getPlayers().size() > 0) {
			for (Player p : gameState.getAllPlayers().getPlayers()) {
				p.addObserver(domiInfoPanel);
				p.addObserver(cardPanel);
				p.addObserver(cRatioPanel);
				p.changed();
			}
		}
		gameState.addObserver(phaseView);
	}

	/**
	 * Method to load the game
	 */
	public void loadGame() {
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(null);
		File path = fc.getSelectedFile();
		System.out.println(path.getAbsolutePath());
		try {
			GameLoader gl = new GameLoader(this, path.getAbsolutePath());
			this.gameState = gl.getGameState();

			mapPanel.removeAll();
			mapDisplayer = new MapDisplayer(mapPanel, gameState.getWorld());
			buttonImage = mapDisplayer.getButtonImage();
			addCompOnMapPanel();

			registerObserver();
			bindButtons();
			// bindNextStage();
			gameState.changed();

			gameState.updateCountryLabels();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Method to save the game
	 */
	public void saveGame() {
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(null);
		File path = fc.getSelectedFile();

		System.out.println(path.getAbsolutePath());

		GameSaver gs = new GameSaver(gameState, this);
		try {
			gs.save(path.getAbsolutePath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void readjustLoc() {
		cancelCountryButton.setBounds(371 + 40, buttonImage.getHeight() + 250, 124, 60);
		frame1.setBounds(100, 100, 750 + buttonImage.getWidth(), 370 + buttonImage.getHeight());
		lp.setBounds(buttonImage.getWidth() + 550, 30, 200, frame1.getHeight() - 100);
		mainScroll.setBounds(buttonImage.getWidth() + 130, 150, 380, buttonImage.getHeight());
		int yM = 30;
		phaseView.phasePanel.setBounds(640, buttonImage.getHeight() + 240 - yM, 250, 250);
		unitDisplay.setBounds(410, buttonImage.getHeight() + 240 - yM, 220, 30);
		Country1Display.setBounds(100, buttonImage.getHeight() + 240 - yM, 300, 35);
		Country2Display.setBounds(100, buttonImage.getHeight() + 290 - yM, 300, 35);
		nextStage.setBounds(900 - 20, buttonImage.getHeight() + 300 - yM, 170, 50);
		phaseView.namePanel.setBounds(100, 40, 975, 60);
	}

	public void addCompOnMapPanel() {
		mapPanel.add(cancelCountryButton);
		// mapPanel.add(frame1);
		mapPanel.add(lp);
		lp.log.setText("");
		addMenu();

		domiInfoPanel = new DomiInfoPanel(gameState);
		domiInfoPanel.setPreferredSize(new Dimension(domiInfoPanel.getWidth(), domiInfoPanel.getHeight()));
		mainScroll.setViewportView(domiInfoPanel);
		mapPanel.add(mainScroll);

		phaseView = new PhaseView(gameState, this);
		mapPanel.add(phaseView.phasePanel);
		mapPanel.add(phaseView.namePanel);
		numberOfTroops.setText("" + troopsLeft);

		mapPanel.add(unitDisplay);
		mapPanel.add(Country1Display);
		mapPanel.add(Country2Display);
		mapPanel.add(nextStage);
		readjustLoc();

		System.out.println("!!!!!!");
		System.out.println(gameState.getAllPlayers().getPlayers().size());
		System.out.println("!!!!!!");
		if (gameState.getAllPlayers().getPlayers().size() > 0) {
			if (gameState.getAllPlayers().getPlayers().size() > 0) {
				phaseView.player1Name.setText(gameState.getAllPlayers().getPlayers().get(0).getName());
				gameState.getAllPlayers().getPlayers().get(0).setPlayerTextName(phaseView.player1Name);
				phaseView.player1Name.setVisible(true);
			}
			if (gameState.getAllPlayers().getPlayers().size() > 1) {
				phaseView.player2Name.setText(gameState.getAllPlayers().getPlayers().get(1).getName());
				gameState.getAllPlayers().getPlayers().get(1).setPlayerTextName(phaseView.player2Name);
				phaseView.player2Name.setVisible(true);
			}
			if (gameState.getAllPlayers().getPlayers().size() > 2) {
				phaseView.player3Name.setText(gameState.getAllPlayers().getPlayers().get(2).getName());
				gameState.getAllPlayers().getPlayers().get(2).setPlayerTextName(phaseView.player3Name);
				phaseView.player3Name.setVisible(true);
			}
			if (gameState.getAllPlayers().getPlayers().size() > 3) {
				phaseView.player4Name.setText(gameState.getAllPlayers().getPlayers().get(3).getName());
				gameState.getAllPlayers().getPlayers().get(3).setPlayerTextName(phaseView.player4Name);
				phaseView.player4Name.setVisible(true);
			}
			if (gameState.getAllPlayers().getPlayers().size() > 4) {
				phaseView.player5Name.setText(gameState.getAllPlayers().getPlayers().get(4).getName());
				gameState.getAllPlayers().getPlayers().get(4).setPlayerTextName(phaseView.player5Name);
				phaseView.player5Name.setVisible(true);
			}
		}

	}

	/**
	 * Method to a menu which includes Exit, Save, Load option
	 */
	public void addMenu() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, frame1.getWidth(), 30);
		mapPanel.add(menuBar);
		JMenu mnNewMenu = new JMenu("Game");
		menuBar.add(mnNewMenu);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveGame();
			}
		});
		JMenuItem LoadMenuItem = new JMenuItem("Load");
		LoadMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadGame();

			}
		});
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnNewMenu.add(exitMenuItem);
		mnNewMenu.add(saveMenuItem);
		mnNewMenu.add(LoadMenuItem);

	}

	public String newGameState(String path, ArrayList<String> strategies, int gameTurns) {
		// StringBuffer sb = new StringBuffer(100);
		ArrayList<Player> players = new ArrayList<>();
		String result = "";
		try {
			gameState = new GameState(this, path);
			for (int i = 0; i < strategies.size(); i++) {
				if (strategies.get(i).equals("aggressive")) {
					Player p = new Player("p" + (i + 1), Color.magenta, gameState, new AggressiveStrategy());
					players.add(p);
				} else if (strategies.get(i).equals("benevolent")) {
					Player p = new Player("p" + (i + 1), Color.magenta, gameState, new BenevolentStrategy());
					players.add(p);
				} else if (strategies.get(i).equals("cheater")) {
					Player p = new Player("p" + (i + 1), Color.magenta, gameState, new CheaterStrategy());
					players.add(p);
				} else if (strategies.get(i).equals("random")) {
					Player p = new Player("p" + (i + 1), Color.magenta, gameState, new RandomStrategy());
					players.add(p);
				}
			}
			for (int x = 0; x < players.size(); x++) {
				System.out.println(players.size());
				System.out.println(players.get(x).getName());
			}

			TournamentStimulater gameSt = new TournamentStimulater(gameState, players, gameTurns, false);

			result = gameSt.execute();
			// sb.append(gameSt.execute());
			// sb.append("\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public JButton getNextStage() {
		return nextStage;
	}

	public void setNextStage(JButton nextStage) {
		this.nextStage = nextStage;
	}

}
