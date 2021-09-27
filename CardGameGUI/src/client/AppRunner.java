/**
 * The AppRunner class contains the main method of the Card Game GUI application,
 * and creates the necessary Game Engine, Game Engine Callback's, and the Application
 * Frame to construct the application for player use.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package client;

import javax.swing.SwingUtilities;

import controller.AppFrameListener;
import model.GameEngineImpl;
import model.interfaces.GameEngine;
import view.GameEngineCallbackImpl;
import view.gui.AppFrame;
import view.gui.GameEngineCallbackGUI;
import view.interfaces.GameEngineCallback;

public class AppRunner {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameEngine gameEngine = new GameEngineImpl();
				
				AppFrame appFrame = new AppFrame(gameEngine);
				appFrame.populate();
				appFrame.addComponentListener(new AppFrameListener(appFrame));
				
				GameEngineCallback callbackGUI = new GameEngineCallbackGUI(appFrame);
				GameEngineCallback callback = new GameEngineCallbackImpl();
				
				gameEngine.addGameEngineCallback(callbackGUI);
				gameEngine.addGameEngineCallback(callback);
			}
		});
	}
}
