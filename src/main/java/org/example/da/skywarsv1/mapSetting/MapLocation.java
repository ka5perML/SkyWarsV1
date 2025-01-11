package org.example.da.skywarsv1.mapSetting;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.List;

@Getter
public enum MapLocation {
    MAP1(Arrays.asList(
            new Location(Bukkit.getWorld("world"),78,72,-366,0,0),
            new Location(Bukkit.getWorld("world"),80,72,-369,0,0),
            new Location(Bukkit.getWorld("world"),82,72,-370,0,0),
            new Location(Bukkit.getWorld("world"),84,72,-372,0,0),
            new Location(Bukkit.getWorld("world"),86,72,-370,0,0),
            new Location(Bukkit.getWorld("world"),88,72,-368,0,0),
            new Location(Bukkit.getWorld("world"),90,72,-366,0,0),
            new Location(Bukkit.getWorld("world"),88,72,-364,0,0),
            new Location(Bukkit.getWorld("world"),86,72,-362,0,0)
    )),
    SPAWN(Arrays.asList(new Location(Bukkit.getWorld("world"),62,66,-379,0,0)));

    private final List<Location> locations;

    MapLocation(List<Location> locations) {
        this.locations = locations;
    }
}
