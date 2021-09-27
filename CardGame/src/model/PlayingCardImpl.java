/**
 * The PlayingCardImpl class contains all of the functionality for the card model. 
 * It implements the abstract methods from the PlayingCard interface and maximizes cohesion
 * to allow for each method to have it's own specific purpose.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package model;

import model.interfaces.PlayingCard;

public class PlayingCardImpl implements PlayingCard {

	private Suit suit;

	private Value value;

	/**
	 * Constructor for PlayingCardImpl. Once suit and value have been initialized,
	 * it checks what the value is and sets the score as the integer equivalent of
	 * the value of the card.
	 * 
	 * @param suit
	 * @param value
	 */
	public PlayingCardImpl(Suit suit, Value value) {
		if (suit == null || value == null)
			throw new IllegalArgumentException("illegal args");

		this.suit = suit;
		this.value = value;
	}

	@Override
	public Suit getSuit() {
		return suit;
	}

	@Override
	public Value getValue() {
		return value;
	}

	@Override
	public int getScore() {
		int score;
		
		if (value == Value.EIGHT) {
			score = 8;
		} else if (value == Value.NINE) {
			score = 9;
		} else if (value == Value.ACE) {
			score = 11;
		} else {
			score = 10;
		}
		
		return score;
	}

	/**
	 * toString returns the string equivalent of all data of the PlayingCardImpl
	 * card. It is formatted so that each value is capitalized.
	 */
	public String toString() {
		StringBuilder str = new StringBuilder(String.format("Suit: %s, Value: %s, Score: %d",
				suit.toString().substring(0, 1) + suit.toString().substring(1).toLowerCase(),
				value.toString().substring(0, 1) + value.toString().substring(1).toLowerCase(), getScore()));

		return str.toString();
	}

	@Override
	public boolean equals(PlayingCard card) {
		return (value == card.getValue() && suit == card.getSuit());
	}

	@Override
	public int hashCode() {
		return suit.hashCode() + value.hashCode();
	}

	/**
	 * equals(Object obj) checks whether the Object obj being compared to shares the
	 * same suit and value as the current PlayingCardImpl object, given that it
	 * implements the PlayingCard interface.
	 */
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;

		if (obj instanceof PlayingCard) {
			PlayingCard card = (PlayingCard) obj;

			if (suit.equals(card.getSuit()) && value.equals(card.getValue()))
				isEqual = true;
		}

		return isEqual;
	}
}
