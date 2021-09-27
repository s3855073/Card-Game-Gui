/**
 * The SimplePlayer class contains all of the functionality for the player model. 
 * It implements the abstract methods from the Player interface and maximizes cohesion
 * to allow for each method to have it's own specific purpose.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package model;

import model.interfaces.Player;

public class SimplePlayer implements Player {

	private String id;
	private String playerName;
	private int points;
	private int bet;
	private int result;

	private final int MIN = 0;

	// Constructor for SimplePlayer
	public SimplePlayer(String id, String playerName, int initialPoints) {
		if (id == null || playerName == null)
			throw new IllegalArgumentException("Illegal args");

		this.id = id;
		this.playerName = playerName;
		this.points = initialPoints;
	}

	@Override
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public int getPoints() {
		return points;
	}

	@Override
	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String getPlayerId() {
		return id;
	}

	/**
	 * setBet checks whether the player can submit a valid bet that they have the
	 * funds for. Returns true if valid, false otherwise.
	 */
	@Override
	public boolean setBet(int bet) {
		boolean hasFunds = false;

		if (bet == MIN) {
			resetBet();
		} else if (bet > MIN && points >= bet) {
			this.bet = bet;
			hasFunds = true;
		}

		return hasFunds;
	}

	@Override
	public int getBet() {
		return bet;
	}

	@Override
	public void resetBet() {
		bet = 0;
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public boolean equals(Player player) {
		return (id == player.getPlayerId());
	}

	@Override
	public int hashCode() {
		return id.hashCode() + playerName.hashCode();
	}

	/**
	 * equals(Object obj) checks whether the Object obj being compared to shares the
	 * same player id and player name as the current SimplePlayer object, given that
	 * it's the Player interface.
	 */
	@Override
	public boolean equals(Object obj) {
		boolean isEquals = false;

		if (obj instanceof Player) {
			Player player = (Player) obj;

			if (id.equals(player.getPlayerId()) && playerName.equals(player.getPlayerName()))
				isEquals = true;
		}

		return isEquals;
	}

	@Override
	public int compareTo(Player player) {
		return id.compareTo(player.getPlayerId());
	}

	@Override
	public String toString() {
		return String.format("Player: id=%s, name=%s, bet=%d, points=%d, RESULT .. %d\n", id, playerName, bet,
				points, result);
	}
}
