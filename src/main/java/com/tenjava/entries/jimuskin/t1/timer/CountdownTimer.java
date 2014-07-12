package com.tenjava.entries.jimuskin.t1.timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.tenjava.entries.jimuskin.t1.TenJava;

public class CountdownTimer implements Runnable{

	private TenJava plugin;
	
	public CountdownTimer(TenJava plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		if(this.plugin.countdownTimer <= 0){
			if(!this.plugin.readyup()){
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "There were not enough players to begin...");
				this.plugin.countdownTimer = 30;
			}
		}else{
			this.plugin.countdownTimer--;
			
			if(this.plugin.countdownTimer == 20){
				Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "20"  + ChatColor.AQUA +  " seconds until the games start!");
			}
			if(this.plugin.countdownTimer == 10){
				Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "10"  + ChatColor.AQUA +  " seconds until the games start!");
			}
		}
	}
}