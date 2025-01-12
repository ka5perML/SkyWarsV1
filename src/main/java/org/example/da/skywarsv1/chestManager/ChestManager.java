package org.example.da.skywarsv1.chestManager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestManager {
    private CheckIdentification chectIdentificator;
    private ChestLoot chestLoot;
    private JavaPlugin plugin;
    public ChestManager(JavaPlugin plugin){
        chectIdentificator = new CheckIdentification();
        chestLoot = new ChestLoot();
        this.plugin = plugin;
    }
    public void startChestSystem(){
        new BukkitRunnable(){
            @Override
            public void run() {
                chect();
            }
        }.runTaskTimer(plugin,0, 200);
    }
    private void chect(){
        for (Location loc : chectIdentificator.getIslandChest()) {
            spawnChest(loc, false);
        }

        for (Location loc : chectIdentificator.getMidChest()) {
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
