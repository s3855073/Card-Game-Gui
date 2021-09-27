package view;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

/**
 * 
 * Skeleton/Partial example implementation of GameEngineCallback showing Java
 * logging behaviour
 * 
 * @author Caspar Ryan
 * @see view.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback {
	public static final Logger logger = Logger.getLogger(GameEngineCallbackImpl.class.getName());

	// utility method to set output level of logging handlers
	public static Logger setAllHandlers(Level level, Logger logger, boolean recursive) {
		// end recursion?
		if (logger != null) {
			logger.setLevel(level);
			for (Handler handler : logger.getHandlers())
				handler.setLevel(level);
			// recursion
			setAllHandlers(level, logger.getParent(), recursive);
		}
		return logger;
	}

	public GameEngineCallbackImpl() {
		// NOTE can also set the console to FINE in %JRE_HOME%\lib\logging.properties
		setAllHandlers(Level.INFO, logger, true);
	}

	/**
	 * nextCard outputs the information of the current card being dealt to the
	 * player.
	 */
	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		// intermediate results logged at Level.FINE
		// TODO: complete this method to log results
		logger.log(Level.INFO,
				String.format("Card dealt to %s .. %s", player.getPlayerName(), card.toString()));
	}

	/**
	 * result outputs the result of the round for the player.
	 */
	@Override
	public void result(Player player, int result, GameEngine engine) {
		// final results logged at Level.INFO
		// logger.log(Level.INFO, "Result data to log .. String.format() is good
		// here!");
		// TODO: complete this method to log results

		logger.log(Level.INFO, String.format("%s, final result=%d", player.getPlayerName(), result));
	}

	/**
	 * bustCard outputs the information of the current card dealt to the player if
	 * that card caused them to bust.
	 */
	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		logger.log(Level.INFO, String.format("Card dealt to %s .. %s ... YOU BUSTED!",
				player.getPlayerName(), card.toString()));
	}

	/**
	 * nextHouseCard outputs the information of the current card being dealt to the
	 * house.
	 */
	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		logger.log(Level.INFO, String.format("Card dealt to House .. %s", card.toString()));

	}

	/**
	 * houseBustCard outputs the information of the current card dealt to the house
	 * if that card caused them to bust.
	 */
	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		logger.log(Level.INFO,
				String.format("Card dealt to House .. %s ... HOUSE BUSTED!", card.toString()));
	}

	/**
	 * houseResult outputs the result of the round for the house, before outputting
	 * the end results of each player and their winnings/ losses.
	 */
	@Override
	public void houseResult(int result, GameEngine engine) {
		logger.log(Level.INFO, String.format("House, final result=%d", result));

		StringBuilder str = new StringBuilder("Final Player Results\n");

		for (Player player : engine.getAllPlayers()) {
			str.append(player.toString());
		}

		logger.log(Level.INFO, str.toString());
	}
}
