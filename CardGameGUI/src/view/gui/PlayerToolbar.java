/**
 * The PlayerToolbar class contains all of the button components and combobox for 
 * the North Panel in the Card Game Application. 
 * It creates the buttons and implements their event listeners to handle the game
 * logic through the MVC pattern.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package view.gui;

import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JToolBar;

import controller.AddPlayerListener;
import controller.DealPlayerListener;
import controller.PlaceBetListener;
import controller.PlayerListListener;
import controller.RemovePlayerListener;
import controller.ResetBetListener;
import model.SimplePlayer;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class PlayerToolbar extends JToolBar {
	private AppFrame appFrame;

	private JButton dealButton;
	private JButton betButton;
	private JButton resetButton;
	private JButton removeButton;

	private JComboBox<Player> playerList;
	
	private Player house;
	
	public PlayerToolbar(AppFrame appFrame) {
		this.appFrame = appFrame;
	}

	/**
	 * populate builds the game buttons and combobox on top of the
	 * Player Toolbar, and adds their respective event listeners
	 * which contains the logic to update the game GUI depending
	 * on the state of the game.
	 */
	public void populate() {
		dealButton = new JButton("Deal");
		dealButton.setEnabled(false);
		add(dealButton);

		betButton = new JButton("Place Bet");
		betButton.setEnabled(false);
		add(betButton);

		resetButton = new JButton("Reset Bet");
		resetButton.setEnabled(false);
		add(resetButton);

		/**
		 * We have set the JComboBox to store Player items, and are using
		 * a renderer to add a layer on top of the JComboBox in order to
		 * display the name of the player, rather than all of the
		 * properties of the player itself.
		 */
		playerList = new JComboBox<Player>();
		house = new SimplePlayer("0", "House", 0); // We create the House as a player
		// when we build the ComboBox, but we don't add it to the Game Engine.
		playerList.addItem(house);
		playerList.setEditable(false);
		playerList.setRenderer(new DefaultListCellRenderer() {

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

				if (value != null)
					setText(((Player) value).getPlayerName());

				return this;
			}

		});
		add(playerList);

		AbstractButton addButton = new JButton("Add Player");
		add(addButton);

		removeButton = new JButton("Remove Player");
		removeButton.setEnabled(false);
		add(removeButton);

		dealButton.addActionListener(new DealPlayerListener(appFrame));
		betButton.addActionListener(new PlaceBetListener(appFrame));
		resetButton.addActionListener(new ResetBetListener(appFrame));
		playerList.addItemListener(new PlayerListListener(appFrame));
		addButton.addActionListener(new AddPlayerListener(appFrame));
		removeButton.addActionListener(new RemovePlayerListener(appFrame));
	}
	
	public Player getHouse() {
		return house;
	}

	public JButton getDealButton() {
		return dealButton;
	}

	public JButton getRemoveButton() {
		return removeButton;
	}

	public JComboBox<Player> getPlayerList() {
		return playerList;
	}

	public JButton getBetButton() {
		return betButton;
	}

	public JButton getResetButton() {
		return resetButton;
	}
}
