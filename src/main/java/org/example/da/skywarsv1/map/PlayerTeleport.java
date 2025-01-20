package org.example.da.skywarsv1.map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.example.da.skywarsv1.map.loader.MapLoad;

public class PlayerTeleport {
    private MapLoad mapLoad;
    public PlayerTeleport(MapLoad mapLoad){
        this.mapLoad = mapLoad;
    }

    public void teleportCleanLocation(Player player){
        for(Location loc : mapLoad.getLocationPlayerList()) {
            if (isLocationFree(loc)){
                player.teleport(loc);
            }
        }
    }

    private boolean isLocationFree(Location loc) {
        return loc.getWorld().getNearbyEntities(loc, 0.5, 2, 0.5).stream()
                .noneMatch(e -> e instanceof Player);
    }
}
