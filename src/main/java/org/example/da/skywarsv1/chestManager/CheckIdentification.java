package org.example.da.skywarsv1.chestManager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class CheckIdentification {
    @Getter
    private final List<Location> islandChest = new ArrayList<>();

    @Getter
    private final List<Location> midChest = new ArrayList<>();

    public CheckIdentification(){
        setupChestSpawns();
    }
    private void setupChestSpawns() {
        islandChest.add(new Location(Bukkit.getWorld("world"), 80, 72, -362));
        islandChest.add(new Location(Bukkit.getWorld("world"), 82, 72, -360));
        islandChest.add(new Location(Bukkit.getWorld("world"), 84, 72, -358));
        islandChest.add(new Location(Bukkit.getWorld("world"), 86, 72, -360));
        islandChest.add(new Location(Bukkit.getWorld("world"), 88, 72, -362));
        islandChest.add(new Location(Bukkit.getWorld("world"), 92, 72, -366));
        islandChest.add(new Location(Bukkit.getWorld("world"), 88, 72, -370));
        islandChest.add(new Location(Bukkit.getWorld("world"), 86, 72, -372));
        islandChest.add(new Location(Bukkit.getWorld("world"), 84, 72, -3));


        midChest.add(new Location(Bukkit.getWorld("world"), 84, 72, -366));
    }

}
