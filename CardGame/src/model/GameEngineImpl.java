/**
 * The GameEngineImpl class contains all of the functionality for the game engine model. 
 * It implements the abstract methods from the GameEngine interface and maximizes cohesion
 * to allow for each method to have it's own specific purpose.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package model;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine {

	private TreeMap<String, Player> players = new TreeMap<>();

	private Collection<GameEngineCallback> gameEngineCallbacks = new LinkedList<>();

	private Deque<PlayingCard> playingCards;

	private final int MIN = 0;

	private final int MAX = 1000;

	/**
	 * Constructor of the GameEngineImple class. As a Game Engine gets initiated,
	 * the deck of cards gets created and shuffled.
	 */
	public GameEngineImpl() {
		playingCards = getShuffledHalfDeck();
	}

	/**
	 * dealCards is the helper method to dealPlayer and dealHouse. This method
	 * checks whether it is dealing with the house or the player, and uses the
	 * appropriate methods when dealing the cards to each person. Returns the end
	 * result for the player/house.
	 * 
	 * @param player
	 * @param delay
	 * @return
	 * @throws IllegalArgumentException
	 */
	private int dealCards(Player player, int delay) throws IllegalArgumentException {
		// Check if the delay for the player/ house is invalid.
		if (player != null && (delay < MIN || delay > MAX) || player == null && delay < MIN)
			throw new IllegalArgumentException("Invalid delay arg");

		PlayingCard dealtCard = null;

		int totalSum = MIN;

		while (totalSum < BUST_LEVEL) {
			try {
				// Re-make and re-shuffle the deck of playingCards if there are no cards left.
				if (playingCards.size() == MIN)
					playingCards = getShuffledHalfDeck();

				// Store the current dealt card and add the score of that card to the result
				// of the player/ house.
				dealtCard = playingCards.pop();
				totalSum += dealtCard.getScore();

				// Output the information about each card dealt.
				if (totalSum <= BUST_LEVEL) {					
					for (GameEngineCallback callback : gameEngineCallbacks) {
						if (player != null) {
							callback.nextCard(player, dealtCard, this);
						} else {
							callback.nextHouseCard(dealtCard, this);
						}
					}
					
					Thread.sleep(delay);
				}
				
			} catch (Exception e) {
				// Log error message
			}
		}

		// When/if the player/house busts, output the bust card information
		// and decrease the score of that card from the end result.
		if (totalSum > BUST_LEVEL) {

			for (GameEngineCallback callback : gameEngineCallbacks) {
				if (player != null) {
					callback.bustCard(player, dealtCard, this);
				} else {
					callback.houseBustCard(dealtCard, this);
				}
			}

			totalSum -= dealtCard.getScore();
		}

		return totalSum;
	}

	/**
	 * dealPlayer implements the game functionality for the player being passed.
	 */
	@Override
	public void dealPlayer(Player player, int delay) throws IllegalArgumentException {
		// Check if the player being passed is a valid player on the table.
		if (players.get(player.getPlayerId()) == null)
			throw new IllegalArgumentException("Invalid player");

		// Check if the player placed a valid bet before dealing cards.
		if (player.getBet() > MIN) {
			int playerTotal = dealCards(player, delay);

			// Output the result of the round for the player.
			for (GameEngineCallback callback : gameEngineCallbacks)
				callback.result(player, playerTotal, this);

			player.setResult(playerTotal);
		} else {
			player.setResult(0);
		}
	}

	/**
	 * dealHouse implements the game functionality for the house.
	 */
	@Override
	public void dealHouse(int delay) throws IllegalArgumentException {
		int houseTotal = dealCards(null, delay);

		// After everyone has been dealt cards and everyone has gotten their
		// end results, every player is applied their win/ loss.
		for (Map.Entry<String, Player> player : players.entrySet())
			applyWinLoss(player.getValue(), houseTotal);

		// Output the result of the round for the house and the winnings/ loss
		// of each player.
		for (GameEngineCallback callback : gameEngineCallbacks)
			callback.houseResult(houseTotal, this);

		// After everything has been finalised, we reset the bet of each player
		// before the start of the next round.
		for (Map.Entry<String, Player> player : players.entrySet())
			player.getValue().resetBet();
	}

	/**
	 * applyWinLoss checks whether the player won against the house or lost, and
	 * sets their points appropriately.
	 */
	@Override
	public void applyWinLoss(Player player, int houseResult) {
		if (player.getResult() > houseResult) {
			player.setPoints(player.getPoints() + player.getBet()); // Player win
		} else if (player.getResult() < houseResult) {
			player.setPoints(player.getPoints() - player.getBet()); // Player loss
		}
	}

	@Override
	public void addPlayer(Player player) {
		players.put(player.getPlayerId(), player);
	}

	@Override
	public Player getPlayer(String id) {
		return players.get(id);
	}

	@Override
	public boolean removePlayer(Player player) {
		return players.remove(player.getPlayerId()) != null;
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		return player.setBet(bet);
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		gameEngineCallbacks.add(gameEngineCallback);
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		return gameEngineCallbacks.remove(gameEngineCallback);
	}

	@Override
	public Collection<Player> getAllPlayers() {
		return Collections.unmodifiableCollection(players.values());
	}

	/**
	 * Helper method for getShuffledHalfDeck to improve clarity.
	 * 
	 * @return new created deck of cards.
	 */
	private LinkedList<PlayingCard> getHalfDeck() {
		LinkedList<PlayingCard> newDeck = new LinkedList<>();

		for (Suit suit : Suit.values()) {
			for (Value value : Value.values()) {
				PlayingCard newCard = new PlayingCardImpl(suit, value);
				newDeck.add(newCard);
			}
		}

		return newDeck;
	}

	/**
	 * getShuffledHalfDeck creates and shuffles a new deck of cards to be returned.
	 */
	@Override
	public Deque<PlayingCard> getShuffledHalfDeck() {
		// Create Half Deck
		LinkedList<PlayingCard> newDeck = getHalfDeck();

		// Shuffle Half Deck
		Collections.shuffle(newDeck);

		// Return Shuffled Deck
		return newDeck;
	}
}