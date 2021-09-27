/**
 * The GameEngineCallbackGUI class implements all of the Abstract methods
 * of the GameEngineCallback interface.
 * It updates the GUI based on the status of the current player
 * being dealt/resulted by taking advantage of the helper methods
 * I have created in their respective classes.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package view.gui;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;
import view.model.PlayerState;

public class GameEngineCallbackGUI implements GameEngineCallback {
	private GameEngine gameEngine;
	private PlayerState playerState;
	
	private PlayerToolbar pToolbar;
	
	private JComboBox<Player> playerList;
	
	private CardPanel cardPanel;
	private SummaryPanel summaryPanel;
	private SouthPanel statusPanel;
	
	private final int NONE = 0;

	public GameEngineCallbackGUI(AppFrame appFrame) {
		gameEngine = appFrame.getGameEngine();
		playerState = appFrame.getPlayerState();
		
		pToolbar = appFrame.getNorthPanel().getPlayerToolbar();
		playerList = pToolbar.getPlayerList();
		cardPanel = appFrame.getCentrePanel().getCardPanel();
		summaryPanel = appFrame.getCentrePanel().getSummaryPanel();
		statusPanel = appFrame.getSouthPanel();
	}

	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				cardPanel.addCard(player, card);
				cardPanel.repaint();
				cardPanel.revalidate();
			}
		});
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				playerState.addBustedPlayer(player);
				cardPanel.addCard(player, card);
				cardPanel.repaint();
				cardPanel.revalidate();
			}
		});
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				summaryPanel.setPlayerInfo(player, 4, result);
				statusPanel.setStatusText(String.format("%s scored .. %d", player.getPlayerName(), result));
			}
		});
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				cardPanel.addHouseCard(card);
				cardPanel.repaint();
				cardPanel.revalidate();
			}
		});
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				playerState.addBustedPlayer(pToolbar.getHouse());
				cardPanel.addHouseCard(card);
				cardPanel.repaint();
				cardPanel.revalidate();
			}
		});
	}

	/**
	 * houseResult gets called at the end of each game, which
	 * sets the results for each player that was dealt and applies their
	 * outcome depending on how they compared to the House. 
	 * If a player went broke, then they will be removed from the game,
	 * after which the game will reset all necessary parameters to start a new
	 * game.
	 */
	@Override
	public void houseResult(int result, GameEngine engine) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				for (Player player : engine.getAllPlayers()) {
					
					if (!playerState.hasBeenDealt(player)) { 	// Check if player had been dealt.
						summaryPanel.setPlayerInfo(player, 5, "NA");
					} else if (player.getResult() > result){ 	// Check if dealt player won.
						summaryPanel.setPlayerInfo(player, 5, "WIN");
					} else if (player.getResult() < result) { 	// Check if dealt player lost.
						summaryPanel.setPlayerInfo(player, 5, "LOSS");
					} else { 									// Check if dealt player drew.
						summaryPanel.setPlayerInfo(player, 5, "DRAW");
					}
					
					summaryPanel.setPlayerInfo(player, 2, player.getPoints());
					summaryPanel.setPlayerInfo(player, 3, NONE);
					
					if (player.getPoints() == NONE) { // Check if player went broke.
						JOptionPane.showMessageDialog(null, String.format("%s has gone broke.\nThey have been removed from the game.", player.getPlayerName()));
						
						gameEngine.removePlayer(player);
						playerList.removeItem(player);
						summaryPanel.removePlayerRow(player);
					}
				}

				// After each player has been updated, we will reset the game.
				statusPanel.setStatusText(String.format("House scored .. %d", result));
				playerState.clearDealtPlayers();
				playerState.clearBetPlayers();
				
				statusPanel.newRound();
			}
		});
	}

}
