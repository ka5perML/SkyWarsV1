package org.example.da.skywarsv1.mapSetting;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class Spawn {
    private Location location = new Location(Bukkit.getWorld("world"), 294.5, 114.5, -11.5);
    public void teleportPlayerInSpawn(Player player){
        player.teleport(location);
    }
}
