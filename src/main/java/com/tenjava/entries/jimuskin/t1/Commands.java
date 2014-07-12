package com.tenjava.entries.jimuskin.t1;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Commands {
	
	private TenJava plugin;
	
	public Commands(TenJava plugin){
		this.plugin = plugin;
	}
	
	public void addSpawn(Player player, String[] args){
		if(!player.hasPermission("tenjava.setspawn")){
			player.sendMessage(ChatColor.RED + "You do not have permission!");
			return;
		}
		if(args.length == 0){
			this.plugin.lobby = player.getLocation();
			player.sendMessage(ChatColor.GREEN + "Set the spawnpoint for the lobby!");
		}else if(args.length == 1){
			if(this.plugin.spawnManager.spawns.containsKey(args[1])){
				this.plugin.spawnManager.addSpawn(args[1], player.getLocation());
				player.sendMessage(ChatColor.GREEN + "Successfully added a spawnpoint to the map " + args[1]);
			}else{
				player.sendMessage(ChatColor.RED + "Unknown map...");
			}
		}else{
			player.sendMessage(ChatColor.RED + "Invalid args... ");
		}
	}
	
	public void startGame(Player player, String[] args){
		if(args.length == 0){
			if(!this.plugin.readyup()){
				player.sendMessage(ChatColor.RED + "Not enough players...");
			}
		}else{
			player.sendMessage(ChatColor.RED + "Invalid args...");
		}
	}
}