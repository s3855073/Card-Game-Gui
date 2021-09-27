/**
 * The ExitOptionListener class implements the ActionListener interface
 * and acts as the event listener for the Exit Menu Option. 
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class ExitOptionListener implements ActionListener {
	private final int EXIT = 0;
	
	/**
	 * Activates whenever the button is clicked. Exits the application if confirmed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int exitConfirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
		
		if (exitConfirmation == JOptionPane.YES_OPTION)
			System.exit(EXIT);
	}

}
