/**
 * The NewGameListener class implements the ActionListener interface
 * and acts as the event listener for the New Game option inside the Frame's menu bar. 
 * When activated, it resets the game and clears all necessary parameters.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import model.GameEngineImpl;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameEngineCallbackImpl;
import view.gui.AppFrame;
import view.gui.CardPanel;
import view.gui.GameEngineCallbackGUI;
import view.gui.SouthPanel;
import view.gui.SummaryPanel;
import view.interfaces.GameEngineCallback;
import view.model.PlayerState;

public class NewGameListener implements ActionListener {
	private AppFrame appFrame;
	private GameEngine gameEngine;
	private PlayerState playerState;
	
	private JComboBox<Player> playerList;
	
	private CardPanel cardPanel;
	private SummaryPanel summaryPanel;
	private SouthPanel statusPanel;
	
	private Player house;
	
	public NewGameListener(AppFrame appFrame) {
		this.appFrame = appFrame;
		
		gameEngine = appFrame.getGameEngine();
		playerState = appFrame.getPlayerState();
		
		playerList = appFrame.getNorthPanel().getPlayerToolbar().getPlayerList();
		cardPanel = appFrame.getCentrePanel().getCardPanel();
		summaryPanel = appFrame.getCentrePanel().getSummaryPanel();
		statusPanel = appFrame.getSouthPanel();
		
		house = appFrame.getNorthPanel().getPlayerToolbar().getHouse();
	}
	
	/**
	 * Activates whenever the new game option is clicked, it will ask for confirmation before
	 * clearing all of the necessary parameters and reinitializing a new Game Engine object, and
	 * the necessary Game Engine Callback objects too.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int newGameConfirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to start a new game?", "New Game", JOptionPane.YES_NO_CANCEL_OPTION);
		
		if (newGameConfirmation == JOptionPane.YES_OPTION) {
			for (Player player : gameEngine.getAllPlayers()) {
				summaryPanel.removePlayerRow(player);
			}
			
			GameEngine gameEngine = new GameEngineImpl();
			appFrame.setGameEngine(gameEngine);
			
			GameEngineCallback callbackGUI = new GameEngineCallbackGUI(appFrame);
			GameEngineCallback callback = new GameEngineCallbackImpl();
			
			gameEngine.addGameEngineCallback(callbackGUI);
			gameEngine.addGameEngineCallback(callback);
			
			// After the game engine and the callbacks have been successfully created, 
			// we update the GUI.
			playerState.clearDealtPlayers();
			playerState.clearBetPlayers();
			
			house.setResult(0);
			playerList.removeAllItems();
			playerList.addItem(house);
			cardPanel.clearPlayers();
			cardPanel.addPlayer(house);
			statusPanel.newGame();
			
			JOptionPane.showMessageDialog(null, "Game has been reset");
		}
	}
}
