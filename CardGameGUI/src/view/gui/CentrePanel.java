/**
 * The CentrePanel class contains all of the building blocks for the Centred Panel
 * in the Card Game Application. 
 * It sets a Border Layout and creates a north "Card" panel, and a south "Summary"
 * panel, which are to be used for the main functionality of the application.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package view.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CentrePanel extends JPanel {
	private AppFrame appFrame;
	
	private CardPanel cardPanel;
	private SummaryPanel summaryPanel;
	
	public CentrePanel(AppFrame appFrame) {
		this.appFrame = appFrame;
		
		setLayout(new BorderLayout());
	}
	
	/**
	 * populate builds the components on top of the Center Panel
	 * for the Card Game Application GUI.
	 */
	public void populate() {
		cardPanel = new CardPanel(appFrame);
		add(cardPanel, BorderLayout.NORTH);
		
		summaryPanel = new SummaryPanel(appFrame);
		summaryPanel.populate();
		add(summaryPanel, BorderLayout.SOUTH);
	}
	
	public CardPanel getCardPanel() {
		return cardPanel;
	}
	
	public SummaryPanel getSummaryPanel() {
		return summaryPanel;
	}
}
