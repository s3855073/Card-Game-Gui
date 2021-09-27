/**
 * The ResetBetListener class implements the ActionListener interface
 * and acts as the event listener for the Reset Bet button. It handles
 * the game logic when the button is clicked, and updates the GUI
 * based on the relative methods when resetting the bet for a player.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import model.interfaces.Player;
import view.gui.AppFrame;
import view.gui.SouthPanel;
import view.gui.SummaryPanel;
import view.model.PlayerState;

public class ResetBetListener implements ActionListener {
	private PlayerState playerState;

	private JComboBox<Player> comboBox;
	private JButton dealButton;
	private JButton resetButton;
	private JButton betButton;
	
	private SummaryPanel summaryPanel;
	private SouthPanel statusPanel;
	
	private final int NONE = 0;

	public ResetBetListener(AppFrame appFrame) {
		playerState = appFrame.getPlayerState();

		comboBox = appFrame.getNorthPanel().getPlayerToolbar().getPlayerList();
		dealButton = appFrame.getNorthPanel().getPlayerToolbar().getDealButton();
		betButton = appFrame.getNorthPanel().getPlayerToolbar().getBetButton();
		resetButton = appFrame.getNorthPanel().getPlayerToolbar().getResetButton();
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

		player.resetBet();

		// After the player's bet has been reset, update the GUI.
		playerState.removeBetPlayer(player);

		summaryPanel.setPlayerInfo(player, 3, NONE);
		dealButton.setEnabled(false);
		betButton.setEnabled(true);
		resetButton.setEnabled(false);
		statusPanel.setStatusText(String.format("%s reset bet", player.getPlayerName()));
	}

}
