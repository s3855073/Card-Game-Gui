/**
 * The PlayerState class keeps track of the state of every player during each game
 * in the Card Game GUI application.
 * It implements several methods to maximise cohesion and return the state
 * of each player when called.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package view.model;

import java.util.ArrayList;
import java.util.Collection;

import model.interfaces.Player;

public class PlayerState {
	private Collection<Player> dealtPlayers = new ArrayList<>();
	private Collection<Player> playersPlacedBets = new ArrayList<>();
	private Collection<Player> bustedPlayers = new ArrayList<>();
	
	private Player dealingPlayer = null;
	private Player lastDealtPlayer = null;
	
	public Player getDealingPlayer() {
		return dealingPlayer;
	}
	
	public void setDealingPlayer(Player player) {
		dealingPlayer = player;
	}
	
	public Collection<Player> getDealtPlayers() {
		return dealtPlayers;
	}
	
	public Player getLastDealtPlayer() {
		return lastDealtPlayer;
	}
	
	public void setLastDealtPlayer(Player player) {
		lastDealtPlayer = player;
	}
	
	public boolean isDealing(Player player) {
		return (dealingPlayer != null && dealingPlayer.equals(player)) ;
	}
	
	public void addDealtPlayer(Player player) {
		dealtPlayers.add(player);
	}
	
	public void clearDealtPlayers() {
		dealtPlayers.clear();
	}
	
	public boolean hasBeenDealt(Player player) {
		return dealtPlayers.contains(player);
	}
	
	public void addBetPlayer(Player p) {
		playersPlacedBets.add(p);
	}
	
	public void removeBetPlayer(Player p) {
		playersPlacedBets.remove(p);
	}
	
	public boolean allPlayersDealt() {
		return ((!dealtPlayers.isEmpty() && dealtPlayers.containsAll(playersPlacedBets)));
	}

	public void clearBetPlayers() {
		playersPlacedBets.clear();
	}

	public boolean hasPlayerBusted(Player player) {
		return bustedPlayers.contains(player);
	}
	
	public void addBustedPlayer(Player player) {
		bustedPlayers.add(player);
	}
	
	public void clearBustedPlayers() {
		bustedPlayers.clear();
	}
}
