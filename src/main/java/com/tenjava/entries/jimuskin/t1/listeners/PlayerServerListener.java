package com.tenjava.entries.jimuskin.t1.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.tenjava.entries.jimuskin.t1.Stages;
import com.tenjava.entries.jimuskin.t1.TenJava;


public class PlayerServerListener implements Listener{
	
	private TenJava plugin;
	
	public PlayerServerListener(TenJava plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		event.setJoinMessage("");
		event.getPlayer().teleport(this.plugin.lobby);
	}
	
	@EventHandler
	public void onRaceEnd(PlayerInteractEvent event){
		if(event.getAction().equals(Action.PHYSICAL)){
			if(event.getClickedBlock().getType() == Material.GOLD_PLATE){
				if(this.plugin.stage == Stages.GAME){
					this.plugin.end(event.getPlayer());
				}
			}
		}
	}
}