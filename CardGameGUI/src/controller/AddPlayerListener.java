/**
 * The AddPlayerListener class implements the ActionListener interface
 * and acts as the event listener for the Add Player button. It handles
 * the game logic when the button is clicked, and updates the GUI
 * based on the relative methods when adding a player.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.gui.AppFrame;
import view.gui.CardPanel;
import view.gui.SouthPanel;
import view.gui.SummaryPanel;

public class AddPlayerListener implements ActionListener {
	private GameEngine engine;

	private JComboBox<Player> comboBox;
	
	private CardPanel cardPanel;
	private SummaryPanel summaryPanel;
	private SouthPanel statusPanel;

	private final int MIN = 1;
	private final int MAX = Integer.MAX_VALUE;
	private final int STEP = 500;
	private final int START = 3000;
	
	private JTextField newPlayerId = new JTextField();
	private JTextField newPlayerName = new JTextField();
	private JSpinner newPlayerPoints = new JSpinner(new SpinnerNumberModel(START, MIN, MAX, STEP)); // Using a JSpinner for number
																							// for the player's points.
	private Object[] newPlayerInfo = { "Player Id:", newPlayerId, "Player Name:", newPlayerName, "Points:", newPlayerPoints };

	public AddPlayerListener(AppFrame appFrame) {
		engine = appFrame.getGameEngine();
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
		int paneOption = JOptionPane.showConfirmDialog(cardPanel, newPlayerInfo, "Add Player",
				JOptionPane.OK_CANCEL_OPTION);

		if (paneOption == JOptionPane.OK_OPTION 	// Check if the player's properties are valid.
				&& ((!newPlayerId.getText().equals("0") && !newPlayerId.getText().equals(""))
						&& !newPlayerName.getText().equals("")
								&& (newPlayerPoints.getValue() != null && ((int) newPlayerPoints.getValue()) >= MIN))) {

			Player newPlayer = new SimplePlayer(newPlayerId.getText(),
					newPlayerName.getText(),
							(int) newPlayerPoints.getValue());

			if (engine.getPlayer(newPlayerId.getText()) != null) { 	// If the player already exists,
																	// remove them from the GUI.
				comboBox.removeItem(engine.getPlayer(newPlayerId.getText()));
				summaryPanel.removePlayerRow(newPlayer);
			}

			engine.addPlayer(newPlayer);

			// After a new player has been successfully created, update the GUI.
			summaryPanel.addPlayerRow(newPlayer); 
			cardPanel.addPlayer(newPlayer);
			comboBox.addItem(newPlayer);

			comboBox.setSelectedItem(newPlayer);
			statusPanel.setStatusText(String.format("New Player Added .. %s", newPlayer.getPlayerName()));
		} else if (paneOption != JOptionPane.CLOSED_OPTION && paneOption != JOptionPane.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(null, "Invalid input. Please try again");
			actionPerformed(e);
		}

		// Reset the text input fields when a player has been successfully created or canceled.
		newPlayerId.setText("");
		newPlayerName.setText("");
		newPlayerPoints.setValue(START);
	}

}
