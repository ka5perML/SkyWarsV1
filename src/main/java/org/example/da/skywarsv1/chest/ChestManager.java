package org.example.da.skywarsv1.chest;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.skywarsv1.game.GameState;
import org.example.da.skywarsv1.game.GameStateManager;
import org.example.da.skywarsv1.map.loader.MapLoad;

public class ChestManager {
    private ChestLoot chestLoot;
    private final JavaPlugin plugin;
    private final MapLoad mapLoad;
    private final GameStateManager gameStateManager;
    public ChestManager(JavaPlugin plugin, MapLoad mapLoad, GameStateManager gameStateManager){
        this.mapLoad = mapLoad;
        this.gameStateManager = gameStateManager;
        chestLoot = new ChestLoot();
        this.plugin = plugin;
    }
    public void startChestSystem(){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(gameStateManager.getGameState() != GameState.GAME){
                    this.cancel();
                    return;
                }
                chest();
                Bukkit.broadcastMessage("Chest update");
            }
        }.runTaskTimer(plugin,0, 600);
    }
    private void chest(){
        for (Location loc : mapLoad.getLocationIslandChestList()) {
            spawnChest(loc, false);
        }

        for (Location loc : mapLoad.getLocationMidChestList()) {
            spawnChest(loc, true);
        }
    }
    private void spawnChest(Location location, boolean isMidChest) {
        Block block = location.getBlock();

        if (block.getType() != Material.CHEST) {
            block.setType(Material.CHEST);
        }

        if (block.getState() instanceof Chest) {
            Chest chest = (Chest) block.getState();
            chestLoot.fillChest(chest.getInventory(), isMidChest);
        }
    }
}
