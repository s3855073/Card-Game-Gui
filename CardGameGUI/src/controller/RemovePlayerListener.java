/**
 * The RemovePlayerListener class implements the ActionListener interface
 * and acts as the event listener for the Remove Player button. It handles
 * the game logic when the button is clicked, and updates the GUI
 * based on the relative methods when removing a player.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.gui.AppFrame;
import view.gui.CardPanel;
import view.gui.SouthPanel;
import view.gui.SummaryPanel;
import view.model.PlayerState;

public class RemovePlayerListener implements ActionListener {
	private GameEngine engine;
	private PlayerState playerState;
	
	private JComboBox<Player> comboBox;
	
	private CardPanel cardPanel;
	private SummaryPanel summaryPanel;
	private SouthPanel statusPanel;

	public RemovePlayerListener(AppFrame appFrame) {
		engine = appFrame.getGameEngine();
		playerState = appFrame.getPlayerState();
		
		comboBox = appFrame.getNorthPanel().getPlayerToolbar().getPlayerList();
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
		Player player = (Player) comboBox.getSelectedItem();

		if (engine.removePlayer(player)) {
			// After a the player has been successfully removed, update the GUI.
			playerState.removeBetPlayer(player);

			comboBox.removeItem(player);
			cardPanel.removePlayer(player);
			summaryPanel.removePlayerRow(player);
			
			statusPanel.setStatusText(String.format("Removed %s", player.getPlayerName()));
		}
	}

}
