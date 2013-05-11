package com.hyperring.AntiOverkill.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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
		if (event.getEntity() instanceof Player){
			Player defender = (Player)event.getEntity();
			Player attacker = null;
			
			if (event.getDamager() instanceof Player)
				attacker = (Player)event.getDamager();
			else if (event.getDamager() instanceof Projectile){
				Projectile projectile = (Projectile)event.getDamager();
				if (projectile.getShooter() instanceof Player)
					attacker = (Player)projectile.getShooter();
			}
			
			if ( attacker != null && !playerManager.allowPvP(defender.getName(), attacker.getName()) ){
				event.setCancelled(true);
				attacker.sendMessage(ChatColor.RED + "You have already killed "+defender.getDisplayName()+ChatColor.RED+" three times in the last thirty minutes. Give them a break!");
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
