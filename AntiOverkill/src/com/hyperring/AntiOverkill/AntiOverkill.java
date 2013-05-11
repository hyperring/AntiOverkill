package com.hyperring.AntiOverkill;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.hyperring.AntiOverkill.player.PlayerListener;
import com.hyperring.AntiOverkill.player.PlayerManager;

public class AntiOverkill extends JavaPlugin {
	private PlayerManager playerManager;
	private PlayerListener playerListener;
	
	public void onEnable(){
		playerManager = new PlayerManager();
		playerListener = new PlayerListener(playerManager);
		
		this.getServer().getPluginManager().registerEvents(playerListener, this);
	}
	
	public void onDisable(){
		HandlerList.unregisterAll(playerListener);
		playerListener = null;
		playerManager = null; 
	}

}
