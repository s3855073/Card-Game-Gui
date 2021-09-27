/**
 * The AppFrame class contains all of the building blocks for the Card Game Application. 
 * It sets the layout of the application GUI and builds the panel components for 
 * all of the children components to be built on, maximizing cohesion
 * by allowing getter methods for each panel component.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.ExitOptionListener;
import controller.GameDelayListener;
import controller.NewGameListener;
import model.interfaces.GameEngine;
import view.model.PlayerState;

@SuppressWarnings("serial")
public class AppFrame extends JFrame {
	private GameEngine gameEngine;
	private PlayerState playerState;

	private NorthPanel northPanel;
	private CentrePanel centrePanel;
	private SouthPanel southPanel;
	
	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	
	private final int HALF = 2;
	private final double SCALE = 1.5;
	
	private final int MIN_WIDTH = (int) dimension.getWidth() / HALF;
	private final int MIN_HEIGHT = (int) dimension.getHeight() / HALF;
	private final int PREF_WIDTH = (int) (MIN_WIDTH * SCALE);
	private final int PREF_HEIGHT = (int) (MIN_HEIGHT * SCALE);
	
	private int gameDelay = 100;

	
	/**
	 * Constructor of the AppFrame class. As the application's frame gets initialised,
	 * the frame properties are set and a new Player State is created to keep track of
	 * all foreseeable player's states during the games.
	 */
	public AppFrame(GameEngine gameEngine) {
		super("CardGameGUI");

		this.gameEngine = gameEngine;
		playerState = new PlayerState();
		
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setBounds(MIN_WIDTH - (PREF_WIDTH / HALF), MIN_HEIGHT - (PREF_HEIGHT / HALF), PREF_WIDTH, PREF_HEIGHT);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * populate builds the layering components of the application GUI when the
	 * App Frame gets initialised.
	 */
	public void populate() {
		southPanel = new SouthPanel(this);
		southPanel.populate();
		add(southPanel, BorderLayout.SOUTH);
		
		centrePanel = new CentrePanel(this);
		centrePanel.populate();
		add(centrePanel, BorderLayout.CENTER);
		
		northPanel = new NorthPanel(this);
		northPanel.populate();
		add(northPanel, BorderLayout.NORTH);

		JMenuBar menuBar = new JMenuBar();
		JMenu menuOptions = new JMenu("Options");
		menuBar.add(menuOptions);
		
		JMenuItem newGameOption = new JMenuItem("New Game");
		newGameOption.addActionListener(new NewGameListener(this));
		menuOptions.add(newGameOption);
		
		JMenuItem setDelayOption = new JMenuItem("Set delay");
		setDelayOption.addActionListener(new GameDelayListener(this));
		menuOptions.add(setDelayOption);
		
		JMenuItem exitOption = new JMenuItem("Exit");
		exitOption.addActionListener(new ExitOptionListener());
		menuOptions.add(exitOption);
		
		setJMenuBar(menuBar);

		repaint();
		revalidate();
	}

	public GameEngine getGameEngine() {
		return gameEngine;
	}
	
	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
	
	public int getGameDelay() {
		return gameDelay;
	}
	
	public void setGameDelay(int newDelay) {
		gameDelay = newDelay;
	}
	
	public PlayerState getPlayerState() {
		return playerState;
	}

	public NorthPanel getNorthPanel() {
		return northPanel;
	}

	public CentrePanel getCentrePanel() {
		return centrePanel;
	}

	public SouthPanel getSouthPanel() {
		return southPanel;
	}
}
