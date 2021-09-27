/**
 * The GameDelayListener class implements the ActionListener interface
 * and acts as the event listener for the Set Game Delay Menu Option. 
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */


package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import view.gui.AppFrame;

public class GameDelayListener implements ActionListener {
	private AppFrame appFrame;
	
	private JSpinner newGameDelay = new JSpinner(new SpinnerNumberModel(200, 1, 3000, 1)); // Use JSpinner for number only input.

	private Object[] gameDelayInput = {
			"Set New Delay:", newGameDelay
	};
	
	private final int MIN = 0;
	
	public GameDelayListener(AppFrame appFrame) {
		this.appFrame = appFrame;
	}

	/**
	 * Activates whenever the button is clicked.
	 * Sets the game's delay time if valid. Previous delay if not set.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int paneOption = JOptionPane.showConfirmDialog(null, gameDelayInput, "Set Delay", JOptionPane.OK_CANCEL_OPTION);

		if (paneOption == JOptionPane.OK_OPTION && ((newGameDelay.getValue() != null && ((int) newGameDelay.getValue()) > MIN))) {
			appFrame.setGameDelay((int) newGameDelay.getValue());
		} 
	}

}
