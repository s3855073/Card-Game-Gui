/**
 * The SouthPanel class contains all of the building blocks for the South Panel
 * in the Card Game Application. 
 * It sets a Border Layout and creates a west "Status" label and a east "Round"
 * label, which are to be used for to output the status of the games.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package view.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SouthPanel extends JPanel {
	private JLabel statusLabel;
	private JLabel roundLabel;
	
	private int roundCounter = 1;
	
	public SouthPanel(AppFrame appFrame) {
		setBorder(BorderFactory.createTitledBorder("Game Status"));
		setLayout(new BorderLayout());
	}
	
	/**
	 * populate builds the components on top of the South Panel
	 * for the Card Game Application GUI.
	 */
	public void populate() {
		statusLabel = new JLabel("Add a new player to begin!");
		add(statusLabel, BorderLayout.WEST);
		
		roundLabel = new JLabel("Round .. 1");
		add(roundLabel, BorderLayout.EAST);
	}
	
	public void setStatusText(String status) {
		statusLabel.setText(status);
	}

	public void newRound() {
		roundCounter++;
		roundLabel.setText(String.format("Round .. %d", roundCounter));
	}
	
	/**
	 * newGame resets the round counter and displays the original status messages
	 * on the status bar.
	 */
	public void newGame() {
		roundCounter = 0;
		setStatusText("Add a new player to begin!");
		newRound();
	}
}
