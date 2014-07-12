package com.tenjava.entries.jimuskin.t1;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.tenjava.entries.jimuskin.t1.listeners.PlayerServerListener;
import com.tenjava.entries.jimuskin.t1.managers.SpawnManager;
import com.tenjava.entries.jimuskin.t1.timer.CountdownTimer;
import com.tenjava.entries.jimuskin.t1.timer.ReadyupTimer;

public class TenJava extends JavaPlugin{
	
	//Some game variables
	public int countdownTimer = 60;
	public int readyupTimer = 5;
	public Stages stage;
	public String map = "";
	
	//The timers
	private int countdown;
	private int readyup;
	
	//Location for the lobby.
	public Location lobby;
	
	//Some of the other classes. To be initialized.
	public SpawnManager spawnManager;
	
	
	public void onEnable(){
		this.saveDefaultConfig();
		
		this.spawnManager = new SpawnManager(this);
		this.countdown = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new CountdownTimer(this), 0, 20L);
		this.stage = Stages.LOBBY;
		
		
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerServerListener(this), this);
		
		this.initiateLobby();
	}
	
	public void onDisable(){
		this.spawnManager.saveSpawns();
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
	
	//Let everyone take their positions on the mark.
	public boolean readyup(){
		if(Bukkit.getOnlinePlayers().length < 2){
			return false;
		}
		
		this.map = this.spawnManager.loadRandomMap();
		
		for(Player p : Bukkit.getOnlinePlayers()){
			p.teleport(this.spawnManager.getRandomSpawn(this.map));
			p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "You are currently playing on the map " 
					+ ChatColor.GOLD + this.map);
		}
		this.stage = Stages.READY;
		Bukkit.getServer().getScheduler().cancelTask(countdown);
		this.readyupTimer = 5;
		this.readyup = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ReadyupTimer(this), 0, 20L);
		return true;
	}
	
	public void start(){
		this.stage = Stages.GAME;
		Bukkit.getServer().getScheduler().cancelTask(readyup);
		for(Player player : Bukkit.getOnlinePlayers()){
			player.setWalkSpeed(player.getWalkSpeed() * 2);
		}
	}
	
	public void end(Player player){
		this.stage = Stages.LOBBY;
		this.countdownTimer = 60;
		
		this.countdown = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new CountdownTimer(this), 0, 20L);
		
		Bukkit.getServer().broadcastMessage(ChatColor.GREEN + player.getName() + " has won the race!"); 
		
		for(Player p : Bukkit.getOnlinePlayers()){
			p.setWalkSpeed(0.2F);
			p.teleport(lobby);
		}
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "You must be a player to perform any of these commands.");
			return true;
		}
		
		Player player = (Player) sender;
		
		Commands commands = new Commands(this);
			if(label.equalsIgnoreCase("setspawn")){
				commands.addSpawn(player, args);
			}
			if(label.equalsIgnoreCase("start")){
				commands.startGame(player, args);
				if(!this.readyup()) sender.sendMessage("Not enough players.");
			}
			
			return true;
		}
}