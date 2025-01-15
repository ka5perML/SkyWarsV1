package org.example.da.skywarsv1.playerSetting;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerSetting {
    private JavaPlugin plugin;
    public PlayerSetting(JavaPlugin plugin){
        this.plugin = plugin;
        playerStats();
    }
    @SneakyThrows
    private void playerStats(){
        new BukkitRunnable(){
            int tick;
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.setFoodLevel(20);
                    if(player.getHealth() < 20.0)
                    player.setHealth(player.getHealth() + 0.5);
                });
                tick++;
            }
        }.runTaskTimer(plugin,0,20);
    }

    public void playerSetting(Player player){
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20.0);
    }
}
