package com.tenjava.entries.jimuskin.t1.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.tenjava.entries.jimuskin.t1.TenJava;

public class SpawnManager {

	private TenJava plugin;

	public SpawnManager(TenJava plugin) {
		this.plugin = plugin;
		this.initiateSpawns();
	}

	public Map<String, ArrayList<Location>> spawns = new HashMap<String, ArrayList<Location>>();

	public void addSpawn(String map, Location spawn) {
		if (spawns.get(map).contains(spawn)) {
			return;
		}
		spawns.get(map).add(spawn);
	}

	// Initiating the spawn points for the race courses.
	private void initiateSpawns() {
		FileConfiguration config = this.plugin.getConfig();
		if (!config.contains("maps")) {
			config.set("maps", Arrays.asList("Map1", "Map2"));
		}

		for (String maps : config.getStringList("maps")) {
			if (!config.contains(maps)) {
				config.set(maps, Arrays.asList("world,0,0,0", "world,1,2,3"));
			}
			spawns.put(maps, null);
			
			ArrayList<Location> locations = new ArrayList<Location>();
			
			for (String locs : config.getStringList(maps)) {
				World world = Bukkit.getWorld("world");
				int x = 0;
				int y = 0;
				int z = 0;
				String[] specs = locs.split(",");
				try {
					world = Bukkit.getWorld(specs[0]);
					x = new Integer(specs[1]);
					y = new Integer(specs[2]);
					z = new Integer(specs[3]);

				} catch (Exception e) {
					System.out.println("Error: Cannot load the map spawns: "
							+ e.getMessage());
				}

				Location loc = new Location(world, x, y, z);
				
				locations.add(loc);
			}
			spawns.put(maps, locations);
		}
		this.plugin.saveConfig();
	}
	
	public void saveSpawns(){
		FileConfiguration config = this.plugin.getConfig();
		
		config.set("maps", spawns.keySet());
		
		for(String maps : spawns.keySet()){
			List<String> spawnsloc = new ArrayList<String>();
			
			for(Location loc : spawns.get(maps)){
				String locstr = loc.getWorld() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
				spawnsloc.add(locstr);
			}
			
			config.set(maps, spawnsloc);
		}
	}
	
	public Location getRandomSpawn(String map){
		return spawns.get(map).get(new Random().nextInt(spawns.get(map).size()));
	}
	
	public String loadRandomMap(){
		List<String> maps = new ArrayList<String>();
		for(String s : spawns.keySet()){
			maps.add(s);
		}
		return maps.get(new Random().nextInt(maps.size()));
	}
}