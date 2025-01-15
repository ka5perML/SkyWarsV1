package org.example.da.skywarsv1.mapSetting;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;

import static org.bukkit.Bukkit.getServer;

public class BlockChange {
    public void changeBlock(Location... locations) {
        for(Location location : locations) {
            Block block = getServer().getWorlds().get(0).getBlockAt(location.clone().add(0,-1,0));
            block.setType(Material.GLASS);
        }
    }
    public void replaceBlockInAir(Location location){
        Block block = getServer().getWorlds().get(0).getBlockAt(location);
        block.setType(Material.AIR);
    }
    public void breakBlockUnderPlayer() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Location playerLoc = player.getLocation();
            Block blockUnderPlayer = playerLoc.clone().subtract(0, 1, 0).getBlock();

            if (blockUnderPlayer.getType() != Material.AIR) {

                blockUnderPlayer.setType(Material.AIR);

                player.getWorld().playSound(playerLoc, Sound.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
            }
        });
    }
}
