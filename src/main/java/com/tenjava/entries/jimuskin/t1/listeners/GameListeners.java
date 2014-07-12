package com.tenjava.entries.jimuskin.t1.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.tenjava.entries.jimuskin.t1.Stages;
import com.tenjava.entries.jimuskin.t1.TenJava;

public class GameListeners implements Listener{

	private TenJava plugin;
	public GameListeners(TenJava plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		if(this.plugin.stage == Stages.READY) 
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event){
		event.setCancelled(true);
	}
}
