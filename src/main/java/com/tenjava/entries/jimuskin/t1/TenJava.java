package com.tenjava.entries.jimuskin.t1;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.tenjava.entries.jimuskin.t1.timer.CountdownTimer;

public class TenJava extends JavaPlugin {
	
	//Storing all the custom instances of players in here, so I have access to them later.
	private static Map<UUID, GamePlayer> GamePlayers = new HashMap<UUID, GamePlayer>();
	
	//Some game variables
	public int countdownTimer = 60;
	public boolean ingame = false;
	
	//The timers
	private int countdown;
	
	
	//Location for the lobby.
	public Location lobby;
	
	
	
	public void onEnable(){
		this.saveDefaultConfig();
		this.countdown = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new CountdownTimer(this), 0, 20L);
		
		this.initiateLobby();
	}
	
	public void onDisable(){
		
	}
	
	private void initiateLobby(){
		
		World world = Bukkit.getWorld("world");
		int x = 0;
		int y = 0;
		int z = 0;
		
		if(this.getConfig().contains("spawn")){
			String[] specs = this.getConfig().getString("spawn").split(",");
			
			try{
				world = Bukkit.getWorld(specs[0]);
				x = new Integer(specs[1]);
				y = new Integer(specs[2]);
				z = new Integer(specs[3]);
				
			}catch(Exception e){
				System.out.println("Error: Cannot load the lobby spawn: " + e.getMessage());
			}
			
			lobby = new Location(world, x, y, z);
		}
	}
	
	public boolean start(){
		if(Bukkit.getOnlinePlayers().length < 4){
			return false;
		}
		
		for(Player p : Bukkit.getOnlinePlayers()){
			p.teleport();
		}
		
		this.ingame = true;
		return true;
	}
	
	/*@Param player the player*/
	public static void addGamePlayer(GamePlayer player){
		GamePlayers.put(player.getPlayer().getUniqueId(), player);
	}
	
	public static GamePlayer getGamePlayer(UUID id){
		if(GamePlayers.containsKey(id)){
			return GamePlayers.get(id);
		}
		return null;
	}
}