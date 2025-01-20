package org.example.da.skywarsv1.player;

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

    public void gameDontStart(Player player){
        player.setAllowFlight(false);
        player.setFlying(false);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        updatePlayerVisibility(player);
        player.setHealth(20.0);
    }

    public void gameStart(Player player){
        player.getInventory().clear();
        player.setGameMode(GameMode.SPECTATOR);
    }

    private void updatePlayerVisibility(Player player) {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            onlinePlayer.hidePlayer(player);
            onlinePlayer.showPlayer(player);
        });
    }
}
