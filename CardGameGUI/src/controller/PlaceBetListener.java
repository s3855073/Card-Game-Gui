/**
 * The PlaceBetListener class implements the ActionListener interface
 * and acts as the event listener for the Place Bet button. It handles
 * the game logic when the button is clicked, and updates the GUI
 * based on the relative methods when setting the bet for a player.
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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import model.interfaces.Player;
import view.gui.AppFrame;
import view.gui.SouthPanel;
import view.gui.SummaryPanel;
import view.model.PlayerState;

public class PlaceBetListener implements ActionListener {
	private PlayerState playerState;

	private JComboBox<Player> comboBox;
	private JButton dealButton;
	private JButton betButton;
	private JButton resetButton;
	
	private SummaryPanel summaryPanel;
	private SouthPanel statusPanel;

	private final int MIN = 1;
	private final int MAX = Integer.MAX_VALUE;
	private final int STEP = 100;
	private final int START = 500;
	
	JSpinner playerBet = new JSpinner(new SpinnerNumberModel(START, MIN, MAX, STEP)); // Use a JSpinner for number only input.

	Object[] playerBetInput = { "Place Bet:", playerBet };

	public PlaceBetListener(AppFrame appFrame) {
		comboBox = appFrame.getNorthPanel().getPlayerToolbar().getPlayerList();
		dealButton = appFrame.getNorthPanel().getPlayerToolbar().getDealButton();
		betButton = appFrame.getNorthPanel().getPlayerToolbar().getBetButton();
		resetButton = appFrame.getNorthPanel().getPlayerToolbar().getResetButton();
		summaryPanel = appFrame.getCentrePanel().getSummaryPanel();
		statusPanel = appFrame.getSouthPanel();

		playerState = appFrame.getPlayerState();
	}

	/**
	 * Activates whenever the button is clicked. Handles the game logic and implements
	 * the GUI following the respective methods for each component.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Player player = (Player) comboBox.getSelectedItem();

		int paneOption = JOptionPane.showConfirmDialog(null, playerBetInput, "New Bet", JOptionPane.OK_CANCEL_OPTION);

		if (paneOption == JOptionPane.OK_OPTION // Check if the bet input is valid
				&& ((playerBet.getValue() != null && ((int) playerBet.getValue()) >= MIN))) {

			if (player.setBet((int) playerBet.getValue())) { // Check if the bet is valid.
				// After the player's bet has been set, update the GUI.
				playerState.addBetPlayer(player);
				
				dealButton.setEnabled(true);
				betButton.setEnabled(false);
				resetButton.setEnabled(true);
				summaryPanel.setPlayerInfo(player, 3, (int) playerBet.getValue());
				statusPanel.setStatusText(String.format("%s placed bet .. %d", player.getPlayerName(), player.getBet()));
			} else {
				JOptionPane.showMessageDialog(null, String.format("%s has insufficient funds", player.getPlayerName()));
				actionPerformed(e);
			}
		} else if (paneOption != JOptionPane.CLOSED_OPTION && paneOption != JOptionPane.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(null, "Invalid input. Please try again");
			actionPerformed(e);
		}
	}

}
