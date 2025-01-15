package org.example.da.skywarsv1.mapSetting;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.skywarsv1.gameStation.GameState;
import org.example.da.skywarsv1.gameStation.GameStateManager;

public class GameTimer {
    private BossBar bossBar;
    private int timer;
    private JavaPlugin plugin;
    private GameStateManager gameStateManager;
    public GameTimer(JavaPlugin plugin, int timer, GameStateManager gameStateManager){
        this.timer = timer;
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
        this.bossBar = Bukkit.createBossBar("Таймер: " + timer +" секунд",BarColor.GREEN, BarStyle.SOLID);
    }
    public void startBossBarTimer() {
        Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);

        new BukkitRunnable() {

            @Override
            public void run() {
                if(timer == 0){
                    gameStateManager.setState(GameState.END);
                    this.cancel();
                    stopTimer();
                    return;
                }
                if(gameStateManager.getGameState() != GameState.GAME){
                    this.cancel();
                    stopTimer();
                    return;
                }
                bossBar.setTitle("Таймер: " + timer + " секунд");
                bossBar.setProgress(timer / 180.0);
                timer--;
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    private void stopTimer() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            bossBar.removePlayer(player);
        });
    }
}
