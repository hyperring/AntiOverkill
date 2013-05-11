package com.hyperring.AntiOverkill.player;

import java.util.ArrayList;

public class PlayerManager {
	ArrayList<PvPPlayer> players = new ArrayList<PvPPlayer>();
	
	public boolean allowPvP(String defender, String attacker){
		PvPPlayer lDefender = getPlayerFromCache(defender);
		return lDefender.allowAttackFrom(attacker);
	}
	
	public void onPlayerDeath(String defender, String attacker){
		PvPPlayer lAttacker = getPlayerFromCache(attacker);
		lAttacker.onKill(defender);
		
		PvPPlayer lDefender = getPlayerFromCache(defender);
		lDefender.onKilledBy(attacker);
	}
	
	private PvPPlayer generateNewPlayer(String username){
		PvPPlayer newpvper = new PvPPlayer(username);
		players.add(newpvper);
		return newpvper;
	}
	
	public PvPPlayer getPlayerFromCache(String username){
		for (PvPPlayer player : players)
			if (player.equals(username))
				return player;
		return generateNewPlayer(username);
	}
}
