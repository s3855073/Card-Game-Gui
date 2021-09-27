/**
 * The PlayerListListener class implements the ActionListener interface
 * and acts as the event listener for the Player List ComboBox. It handles
 * the game logic when the button is clicked, and updates the GUI
 * based on the relative methods when selecting a player.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;

import model.interfaces.Player;
import view.gui.AppFrame;
import view.gui.CardPanel;
import view.gui.PlayerToolbar;
import view.gui.SouthPanel;
import view.model.PlayerState;

public class PlayerListListener implements ItemListener {	
	private PlayerState playerState;

	private PlayerToolbar pToolbar;

	private JButton dealButton;
	private JButton betButton;
	private JButton resetButton;
	private JButton removeButton;

	private CardPanel cardPanel;
	private SouthPanel statusPanel;

	private final int MIN = 0;

	public PlayerListListener(AppFrame appFrame) {
		pToolbar = appFrame.getNorthPanel().getPlayerToolbar();
		dealButton = appFrame.getNorthPanel().getPlayerToolbar().getDealButton();
		betButton = appFrame.getNorthPanel().getPlayerToolbar().getBetButton();
		resetButton = appFrame.getNorthPanel().getPlayerToolbar().getResetButton();
		removeButton = appFrame.getNorthPanel().getPlayerToolbar().getRemoveButton();
		cardPanel = appFrame.getCentrePanel().getCardPanel();
		statusPanel = appFrame.getSouthPanel();
		playerState = appFrame.getPlayerState();
	}

	/**
	 * Activates whenever the button is clicked. Handles the game logic and implements
	 * the GUI following the respective methods for each component.
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		Player player = (Player) pToolbar.getPlayerList().getSelectedItem();

		cardPanel.repaint();
		cardPanel.revalidate();

		if (player != null) {
			statusPanel.setStatusText(String.format("Switched to .. %s", player.getPlayerName()));
			
			// Check if the selected player is not the House and round has not started.
			if (!player.getPlayerId().equals("0") && !playerState.isDealing(player)
					&& !playerState.hasBeenDealt(player)) {
				if (player.getBet() == MIN) {
					removeButton.setEnabled(true); // If player has not set a bet yet.
				} else {
					removeButton.setEnabled(false);
				}
				
				if (player.getBet() > MIN && playerState.getDealingPlayer() != null) {
					dealButton.setEnabled(false); // If round has started and another player is dealing 
					betButton.setEnabled(false);  // but the selected player has set bet.
					resetButton.setEnabled(true);
				} else if (player.getBet() > MIN && playerState.getDealingPlayer() == null && (playerState.getLastDealtPlayer() == null || playerState.getLastDealtPlayer().equals(pToolbar.getHouse()))) {
					dealButton.setEnabled(true); // If round has not started and player has set bet.
					betButton.setEnabled(false);
					resetButton.setEnabled(true);
				} else if (player.getBet() > MIN && playerState.getDealingPlayer() == null && playerState.getLastDealtPlayer() != null) {
					dealButton.setEnabled(true); // If round has started and player has set bet.
					betButton.setEnabled(false);
					resetButton.setEnabled(false);
				} else if ((playerState.getLastDealtPlayer() == null && playerState.getDealingPlayer() == null)
								|| (playerState.getLastDealtPlayer() != null
										&& playerState.getLastDealtPlayer().equals(pToolbar.getHouse()))) {
					dealButton.setEnabled(false); // If the round has not started and the player has not set a bet yet.
					betButton.setEnabled(true);
					resetButton.setEnabled(false);
				}
			} else { // If the selected player is the House or if the current player is dealing.
				dealButton.setEnabled(false);
				betButton.setEnabled(false);
				resetButton.setEnabled(false);
				removeButton.setEnabled(false);
			}
		} else { // If player was deselected.
			statusPanel.setStatusText("Switched to .. noone");

			dealButton.setEnabled(false);
			betButton.setEnabled(false);
			resetButton.setEnabled(false);
			removeButton.setEnabled(false);
		}

	}

}
