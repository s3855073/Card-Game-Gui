/**
 * The NorthPanel class contains all of the building blocks for the North Panel
 * in the Card Game Application. 
 * It sets a Border Layout and creates a Player Toolbar panel, which is used for
 * laying out the buttons for the game functionality.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package view.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class NorthPanel extends JPanel {
	private AppFrame appFrame;
	
	private PlayerToolbar playerToolbar;
	
	public NorthPanel(AppFrame appFrame) {
		this.appFrame = appFrame;
		
		setBorder(BorderFactory.createTitledBorder("Player Toolbar"));
		setLayout(new BorderLayout());
	}
	
	/**
	 * populate builds the Toolbar component on top of the North Panel
	 * for the Card Game Application GUI.
	 */
	public void populate() {
		playerToolbar = new PlayerToolbar(appFrame);
		playerToolbar.populate();
		add(playerToolbar, BorderLayout.PAGE_START);
	}
	
	public PlayerToolbar getPlayerToolbar() {
		return playerToolbar;
	}
}
