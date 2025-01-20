package org.example.da.skywarsv1.map.loader;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class MapLoad {
    private final JavaPlugin plugin;
    private final File dataFile;
    private FileConfiguration config;
    @Getter
    private List<Location> locationPlayerList;
    @Getter
    private List<Location> locationIslandChestList ;
    @Getter
    private List<Location> locationMidChestList;
    @Getter
    private List<Location> locationSpawn;
    public MapLoad(JavaPlugin plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "Maps.yml");
        checkFolder();
        this.config = YamlConfiguration.loadConfiguration(dataFile);
        loadMap();
    }

    @SneakyThrows
    private void checkFolder() {
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            dataFile.createNewFile();
            Bukkit.getLogger().severe("Created new file: " + dataFile.getAbsolutePath());
        }
        Bukkit.getLogger().info("Config find: " + dataFile.getAbsolutePath());
    }

    private void loadMap(){
        MapName mapName = randomMap();
        this.locationPlayerList = loadLocation(mapName);
        this.locationIslandChestList = loadChestIslandCoordinats(mapName);
        this.locationMidChestList = loadChestMidCoordinats(mapName);
        this.locationSpawn = loadSpawn(mapName);
    }

    private MapName randomMap(){
        MapName[] values = MapName.values();
        int random = ThreadLocalRandom.current().nextInt(values.length);
        return values[random];
    }

    @SneakyThrows
    private List<Location> loadLocation(MapName mapName) {
        String path = "map." + mapName + ".coordinates";

        List<List<Double>> coordinates = (List<List<Double>>) config.getList(path);
        if (coordinates == null) {
            Bukkit.getLogger().warning("No coordinates found for map: " + mapName);
            return new ArrayList<>();
        }
        return coordinates.stream()
                .filter(list -> list.size() >= 5)
                .map(list->
                        new Location(Bukkit.getWorld("world"),
                                list.get(0),
                                list.get(1),
                                list.get(2),
                                list.get(3).floatValue(),
                                list.get(4).floatValue()
                                ))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private List<Location> loadSpawn(MapName mapName) {
        String path = "map." + mapName + ".spawn";

        List<List<Double>> coordinates = (List<List<Double>>) config.getList(path);
        if (coordinates == null) {
            Bukkit.getLogger().warning("No coordinates found for map: " + mapName);
            return new ArrayList<>();
        }
        return coordinates.stream()
                .filter(list -> list.size() >= 5)
                .map(list->
                        new Location(Bukkit.getWorld("world"),
                                list.get(0),
                                list.get(1),
                                list.get(2),
                                list.get(3).floatValue(),
                                list.get(4).floatValue()
                        ))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private List<Location> loadChestIslandCoordinats(MapName mapName) {
        String path = "map." + mapName + ".coordinatesIslandChest";

        List<List<Double>> coordinates = (List<List<Double>>) config.getList(path);
        if (coordinates == null) {
            Bukkit.getLogger().warning("No coordinates found for map: " + mapName);
            return new ArrayList<>();
        }
        return coordinates.stream()
                .filter(list -> list.size() >= 3)
                .map(list->
                        new Location(Bukkit.getWorld("world"),
                                list.get(0),
                                list.get(1),
                                list.get(2)
                        ))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private List<Location> loadChestMidCoordinats(MapName mapName) {
        String path = "map." + mapName + ".coordinatesMidChest";

        List<List<Double>> coordinates = (List<List<Double>>) config.getList(path);
        if (coordinates == null) {
            Bukkit.getLogger().warning("No coordinates found for map: " + mapName);
            return new ArrayList<>();
        }
        return coordinates.stream()
                .filter(list -> list.size() >= 3)
                .map(list->
                        new Location(Bukkit.getWorld("world"),
                                list.get(0),
                                list.get(1),
                                list.get(2)
                        ))
                .collect(Collectors.toList());
    }
}
