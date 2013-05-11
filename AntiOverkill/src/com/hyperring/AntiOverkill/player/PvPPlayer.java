package com.hyperring.AntiOverkill.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PvPPlayer {
	private String username;
	private HashMap<String, List<Long>> killers = new HashMap<String, List<Long>>();

	public PvPPlayer (String username){
		this.username = username;
	}
	
	/**
	 * Removes any old entries from the attack list
	 * @param otherPlayer
	 */
	private void recalculateOverkills(String otherPlayer){
		List<Long> attacks = killers.get(otherPlayer);
		ArrayList<Long> updatedAttacks = new ArrayList<Long>();
		
		for (Long attackTime : attacks)
			if ((System.currentTimeMillis() - attackTime) < 1800000)
				updatedAttacks.add(attackTime);
		
		killers.put(otherPlayer, updatedAttacks);
	}
	
	/**
	 * Adds another death by otherPlayer to my local count.
	 * @param otherPlayer
	 */
	private void incrementAttacksBy(String otherPlayer){
		killers.get(otherPlayer).add(System.currentTimeMillis());		
	}
	
	private void resetKillsBy(String otherPlayer){
		killers.put(otherPlayer, new ArrayList<Long>());
	}
	
	/**
	 * Called when I kill otherPlayer.
	 * @param otherPlayer
	 */
	public void onKill(String otherPlayer){
		//If you attack the player, it is PvP not player camping.
		if (beenKilledBy(otherPlayer)) {
			resetKillsBy(otherPlayer);
		}
	}
	
	/**
	 * Called when I am killed by otherPlayer.
	 * @param otherPlayer
	 */
	public void onKilledBy(String otherPlayer){
		incrementAttacksBy(otherPlayer);
	}
	
	/**
	 * Tests if otherPlayer can attack me (aka not overkilled me).
	 * @param otherPlayer
	 * @return
	 */
	public boolean allowAttackFrom(String otherPlayer){
		if (beenKilledBy(otherPlayer))
			recalculateOverkills(otherPlayer);
		else 
			killers.put(otherPlayer, new ArrayList<Long>());
		return !isOverkilledBy(otherPlayer);
	}
	
	/**
	 * Tests if attacker has overkilled me. 
	 * Note: Call @ref recalculateOverkills first to ensure accuracy of attack list.
	 * Warning: Assumes otherPlayer has attack me at least once!
	 * 
	 * @param otherPlayer Attacking player.
	 * @return True if killed 3 times by otherPlayer.
	 */
	private boolean isOverkilledBy(String otherPlayer){
		List<Long> attacks = killers.get(otherPlayer);
		return attacks.size() >= 3;
	}
	
	/**
	 * Tests if player has killed me before.
	 * @param otherPlayer 
	 * @return
	 */
	private boolean beenKilledBy(String otherPlayer){
		if (killers.containsKey(otherPlayer))
			return killers.get(otherPlayer).size() > 0;
		return false;
	}
	
	/**
	 * Tests if the specified username refers to this instance.
	 * @param username
	 * @return
	 */
	public boolean equals(String username){
		return this.username.equals(username);
	}
}
