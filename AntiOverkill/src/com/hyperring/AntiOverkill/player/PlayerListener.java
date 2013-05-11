package com.hyperring.AntiOverkill.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerListener implements Listener {
	private PlayerManager playerManager;
	
	public PlayerListener(PlayerManager playerManager){
		this.playerManager = playerManager;
	}
	
	@EventHandler
	public void onEntityAttack(EntityDamageByEntityEvent event){
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player){
			if (!playerManager.allowPvP(((Player)event.getEntity()).getName(), ((Player)event.getDamager()).getName())){
				event.setCancelled(true);
				((Player)event.getDamager()).sendMessage(ChatColor.RED + "You have already killed this person three times in the last thirty minutes. Give them a break!");
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		Entity entity = event.getEntity();
		if (entity instanceof Player && ((Player)entity).getKiller() instanceof Player) {
			playerManager.onPlayerDeath(((Player)entity).getName(), ((Player)entity).getKiller().getName());
		}
	}
}
