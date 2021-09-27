/**
 * The DealPlayerListener class implements the ActionListener interface
 * and acts as the event listener for the Deal Button. It handles
 * the game logic when the button is clicked, and updates the GUI
 * based on the relative methods when dealing to a player.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.gui.AppFrame;
import view.gui.CardPanel;
import view.gui.PlayerToolbar;
import view.gui.SouthPanel;
import view.gui.SummaryPanel;
import view.model.PlayerState;

public class DealPlayerListener implements ActionListener {
	private AppFrame appFrame;
	private GameEngine engine;
	private PlayerState playerState;
	
	private JComboBox<Player> playerList;
	private JButton dealButton;
	private JButton betButton;
	private JButton resetButton;
	private JButton removeButton;
	
	private PlayerToolbar pToolbar;
	private CardPanel cardPanel;
	private SummaryPanel summaryPanel;
	private SouthPanel statusPanel;
	
	private Player house;
	
	private final int ZERO = 0;
	
	public DealPlayerListener(AppFrame appFrame) {
		this.appFrame = appFrame;
		engine = appFrame.getGameEngine();
		playerState = appFrame.getPlayerState();

		pToolbar = appFrame.getNorthPanel().getPlayerToolbar();
		
		playerList = pToolbar.getPlayerList();
		dealButton = pToolbar.getDealButton();
		betButton = pToolbar.getBetButton();
		resetButton = pToolbar.getResetButton();
		removeButton = pToolbar.getRemoveButton();
		
		house = pToolbar.getHouse();
		
		cardPanel = appFrame.getCentrePanel().getCardPanel();
		summaryPanel = appFrame.getCentrePanel().getSummaryPanel();
		statusPanel = appFrame.getSouthPanel();
	}

	/**
	 * Activates whenever the button is clicked. Handles the game logic and implements
	 * the GUI following the respective methods for each component.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		new Thread() {
			
			@Override
			public void run() {
				disablePlayerToolbar();
				
				playerState.setLastDealtPlayer(null);
				
				checkNewGame();

				Player player = (Player) pToolbar.getPlayerList().getSelectedItem();
				
				dealToPlayer(player);

				checkAllPlayersDealt();
			} 
		}.start();
	}
	
	/**
	 * Disables the buttons for the currently selected player.
	 */
	private void disablePlayerToolbar() {
		dealButton.setEnabled(false);
		betButton.setEnabled(false);
		resetButton.setEnabled(false);
		removeButton.setEnabled(false);
	}
	
	/**
	 * Checks whether a new round has started and updates the GUI.
	 */
	private void checkNewGame() {
		if (playerState.getDealtPlayers().isEmpty()) {
			playerState.clearBustedPlayers();
			cardPanel.clearCards();

			for (Player player : engine.getAllPlayers()) {
				summaryPanel.setPlayerInfo(player, 4, ZERO);
				summaryPanel.setPlayerInfo(player, 5, "NA");
			}
		}
	}
	
	/**
	 * Deals to the currently selected player and updates the GUI.
	 */
	private void dealToPlayer(Player player) {
		playerState.setDealingPlayer(player);
		statusPanel.setStatusText(String.format("Dealing to .. %s", player.getPlayerName()));
		
		if (!player.getPlayerId().equals("0")) {
			engine.dealPlayer(player, appFrame.getGameDelay());

			playerState.addDealtPlayer(player);
		}
		
		playerState.setLastDealtPlayer(player);
		playerState.setDealingPlayer(null);
	}
	
	/**
	 * Checks whether all players have been dealt. Deals to the house if so, and updates the GUI.
	 */
	private void checkAllPlayersDealt() {
		if (playerState.allPlayersDealt()) {
			JOptionPane.showMessageDialog(null, "The House is now being dealt");
			
			playerState.setDealingPlayer(house);
			statusPanel.setStatusText(String.format("Dealing to .. %s", house.getPlayerName()));
			
			playerList.setSelectedItem(house);
			
			engine.dealHouse(appFrame.getGameDelay());
			
			playerState.setLastDealtPlayer(house);
			playerState.setDealingPlayer(null);
		}
	}

}
