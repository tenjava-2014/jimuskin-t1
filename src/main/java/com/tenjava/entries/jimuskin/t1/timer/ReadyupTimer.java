package com.tenjava.entries.jimuskin.t1.timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.tenjava.entries.jimuskin.t1.TenJava;

public class ReadyupTimer implements Runnable{
	
	private TenJava plugin;
	
	public ReadyupTimer(TenJava plugin){
		this.plugin = plugin;
	}

	@Override
	public void run() {
		if(this.plugin.readyupTimer == 0){
			this.plugin.start();
		}else{
			this.plugin.readyupTimer--;
			for(Player p : Bukkit.getOnlinePlayers()){
				p.sendMessage(ChatColor.AQUA + "Game starts in " + ChatColor.GOLD + this.plugin.readyupTimer);
			}
		}
	}
}