/**
 * The AppFrameListener class implements the ComponentListener interface
 * and acts as the event listener for the App Frame. It handles
 * the updates when the AppFrame is resized and calls the appropriate methods to rescale
 * the Centre Panel's Card Panel and Summary Panel
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package controller;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import view.gui.AppFrame;
import view.gui.CardPanel;
import view.gui.SummaryPanel;

public class AppFrameListener implements ComponentListener {
	private AppFrame appFrame;
	
	private CardPanel cardPanel;
	private SummaryPanel summaryPanel;
	
	public AppFrameListener(AppFrame appFrame) {
		this.appFrame = appFrame;
		
		cardPanel = appFrame.getCentrePanel().getCardPanel();
		summaryPanel = appFrame.getCentrePanel().getSummaryPanel();
	}
	
	/**
	 * Activates whenever the application frame is resized. Sets the new preferred size
	 * of the panel's to the required third of the Centre Panel for the Summary Panel, and the
	 * Card Panel filling in the rest.
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		cardPanel.setPreferredSize(new Dimension(appFrame.getWidth(), appFrame.getCentrePanel().getHeight() * 2 / 3));
		summaryPanel.setPreferredSize(new Dimension(appFrame.getWidth(), appFrame.getCentrePanel().getHeight() / 3));
		
		cardPanel.repaint();
		cardPanel.revalidate();
		summaryPanel.repaint();
		summaryPanel.revalidate();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		
	}

}
