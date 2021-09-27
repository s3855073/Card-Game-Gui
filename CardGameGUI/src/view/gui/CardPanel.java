/**
 * The CardPanel class contains all of the building blocks for the Centre Panel's
 * Card Panel. 
 * It sets the layout of the Card Panel and deals with drawing the player's cards directly
 * onto the Card Panel when they are being dealt to, or have been dealt.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;
import view.model.PlayerState;

@SuppressWarnings("serial")
public class CardPanel extends JPanel {
	private AppFrame appFrame;
	private PlayerState playerState;

	private HashMap<String, ArrayList<PlayingCard>> playerCards = new HashMap<>();
	private HashMap<Value, String> cardValues = new HashMap<>();
	private HashMap<Suit, Color> cardSuits = new HashMap<>();

	private final int FINAL_FONT_SIZE = 16;
	private final int CARD_SCALE = 256;

	private final int CARD_GAP = 10;
	private final int TOTAL_CARD_GAP = CARD_GAP * 7;

	private final double HEIGHT_SCALE = 1.5;

	public CardPanel(AppFrame appFrame) {
		this.appFrame = appFrame;
		playerState = appFrame.getPlayerState();
		playerCards.put("0", new ArrayList<PlayingCard>());

		cardValues.put(Value.ACE, "A"); // Set the value string for each card value.
		cardValues.put(Value.EIGHT, "8");
		cardValues.put(Value.NINE, "9");
		cardValues.put(Value.TEN, "10");
		cardValues.put(Value.JACK, "J");
		cardValues.put(Value.QUEEN, "Q");
		cardValues.put(Value.KING, "K");

		cardSuits.put(Suit.CLUBS, Color.BLACK); // Set the suit color for each card suit.
		cardSuits.put(Suit.SPADES, Color.BLACK);
		cardSuits.put(Suit.HEARTS, Color.RED);
		cardSuits.put(Suit.DIAMONDS, Color.RED);

		setBorder(BorderFactory.createTitledBorder(""));
		setPreferredSize(
				new Dimension(appFrame.getCentrePanel().getWidth(),
						appFrame.getCentrePanel().getHeight() * 2 / 3));
	}

	public void addPlayer(Player player) {
		playerCards.put(player.getPlayerId(), new ArrayList<PlayingCard>());
	}

	public void removePlayer(Player player) {
		playerCards.remove(player.getPlayerId());
	}
	
	public void clearPlayers() {
		playerCards.clear();
	}

	public void addCard(Player player, PlayingCard card) {
		playerCards.get(player.getPlayerId()).add(card);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Player player = (Player) appFrame
				.getNorthPanel()
						.getPlayerToolbar()
								.getPlayerList()
										.getSelectedItem();

		// Check if the player is being dealt to, has been dealt, or if the round has finished.
		if (player != null && ((playerState.getDealingPlayer() != null && playerState.isDealing(player))
				|| (playerState.getLastDealtPlayer() != null
						&& playerState.getLastDealtPlayer().getPlayerId().equals("0"))
								|| (playerState.hasBeenDealt(player)))) {
			Graphics2D g2D = (Graphics2D) g;

			// Initialise the variables for the helper methods to use.
			int cardXAlign = 10;
			int cardYAlign = (int) ((getHeight() / 2) - (((getWidth() - TOTAL_CARD_GAP) / 6) * HEIGHT_SCALE) / 2);

			int cardWidth = (int) ((getWidth() - TOTAL_CARD_GAP) / 6);
			int cardHeight = (int) (cardWidth * HEIGHT_SCALE);

			Font font = g2D.getFont();
			FontMetrics fontMetrics = g2D.getFontMetrics(font);
			FontRenderContext fRC = fontMetrics.getFontRenderContext();

			for (PlayingCard card : playerCards.get(player.getPlayerId())) {
				// Draw the card background for each card.
				drawBackground(player, card, g2D, cardXAlign, cardYAlign, cardWidth, cardHeight);

				// Draw the card suit for each card.
				drawSuit(g2D, card, cardXAlign, cardYAlign, cardWidth, cardHeight);

				// Draw the card value for each card.
				drawValue(font, fRC, g2D, card, cardXAlign, cardYAlign, cardWidth, cardHeight);

				// Increment the x alignment for each card when the card has finished drawing.
				cardXAlign += cardWidth + CARD_GAP;
			}
		}
	}

	/**
	 * drawBackground draws the white background for each dealt card.
	 */
	private void drawBackground(Player player, PlayingCard card, Graphics2D g2D, int cardXAlign, int cardYAlign, int cardWidth,
			int cardHeight) {
		if (playerState.hasPlayerBusted(player) && playerCards.get(player.getPlayerId()).get(playerCards.get(player.getPlayerId()).size() - 1).equals(card)) {
			g2D.setColor(Color.LIGHT_GRAY);
		} else {
			g2D.setColor(Color.WHITE);
		}

		g2D.fillRoundRect(cardXAlign, cardYAlign, cardWidth, cardHeight, cardWidth / 8, cardWidth / 8);
	}
	
	/**
	 * drawSuit draws the specified card's suit as an image on the Card Panel using basic
	 * geometry to map and lay out the suits in the correct space. It also scales the suit
	 * based on the current card's height.
	 */
	private void drawSuit(Graphics2D g2D, PlayingCard card, int cardXAlign, int cardYAlign, int cardWidth,
			int cardHeight) {
		int[] suitXAlign = { cardXAlign + (cardWidth / 6) - (cardWidth / 12),
				cardXAlign + (cardWidth * 5 / 6) - (cardWidth / 12), cardXAlign + (cardWidth / 2) - (cardWidth / 12),
				cardXAlign + (cardWidth / 6) - (cardWidth / 12), cardXAlign + (cardWidth * 5 / 6) - (cardWidth / 12) };

		int[] suitYAlign = { cardYAlign + (cardHeight / 4) - (cardWidth / 12),
				cardYAlign + (cardHeight / 4) - (cardWidth / 12), getHeight() / 2 - (cardWidth / 12),
				cardYAlign + (cardHeight * 3 / 4) - (cardWidth / 12),
				cardYAlign + (cardHeight * 3 / 4) - (cardWidth / 12) };

		g2D.setColor(cardSuits.get(card.getSuit()));
		Image scaledFace = null;

		try {
			scaledFace = ImageIO.read(new File(String.format("img%s%s.png", File.separator, card.getSuit().toString())))
					.getScaledInstance(cardWidth / 6, cardWidth / 6, Image.SCALE_SMOOTH);
		} catch (IOException e) {

		}

		BufferedImage cardFace = new BufferedImage(scaledFace.getWidth(null), scaledFace.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D cFG2D = (Graphics2D) cardFace.getGraphics();

		cFG2D.drawImage(scaledFace, 0, 0, null);
		
		for (int i = 0; i < 5; i++)
			g2D.drawImage(cardFace, null, suitXAlign[i], suitYAlign[i]);
	}

	/**
	 * drawValue draws the specified card's value as a string on the Card Panel using basic
	 * geometry to map and lay out the strings in the correct space. It also scales the font
	 * based on the current card's height.
	 */
	private void drawValue(Font font, FontRenderContext fRC, Graphics2D g2D, PlayingCard card, int cardXAlign,
			int cardYAlign, int cardWidth, int cardHeight) {
		g2D.setFont(font.deriveFont((float) FINAL_FONT_SIZE * cardHeight / CARD_SCALE));

		int fontHeight = (int) g2D.getFont().getStringBounds(cardValues.get(card.getValue()), fRC).getHeight();
		int fontWidth = (int) g2D.getFont().getStringBounds(cardValues.get(card.getValue()), fRC).getWidth();

		g2D.drawString(cardValues.get(card.getValue()), cardXAlign + (cardWidth / 6) - (fontWidth / 2),
				cardYAlign + (cardHeight / 9) + (fontHeight / 3));
		g2D.drawString(cardValues.get(card.getValue()), cardXAlign + (cardWidth * 5 / 6) - (fontWidth / 2),
				cardYAlign + (cardHeight * 8 / 9) + (fontHeight / 3));
	}

	/**
	 * clearCards clears each player's card collection at the start of each round.
	 */
	public void clearCards() {
		for (String key : playerCards.keySet()) {
			playerCards.put(key, new ArrayList<PlayingCard>());
		}
	}

	/**
	 * addHouseCard adds the house's cards to the collection.
	 */
	public void addHouseCard(PlayingCard card) {
		playerCards.get("0").add(card);
	}
}
