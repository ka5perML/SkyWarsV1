package org.example.da.skywarsv1.gameStation.state;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.skywarsv1.gameStation.GameState;
import org.example.da.skywarsv1.gameStation.GameStateManager;

import java.util.List;
import java.util.stream.Collectors;

public class StateGame {
    private JavaPlugin plugin;
    private GameStateManager gameStateManager;
    private boolean stopTimer = false;
    private boolean timerEnd = false;
    public StateGame(JavaPlugin plugin, GameStateManager gameStateManager){
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
    }

    public void game(){
        gameStage();
        startBossBarTimer();
    }
    private void gameStage(){
        new BukkitRunnable() {
            int timer = 0;
            @Override
            public void run() {
                List<Player> survivalPlayers = Bukkit.getOnlinePlayers().stream()
                        .filter(player -> player.getGameMode() == GameMode.SURVIVAL)
                        .collect(Collectors.toList());
                if(survivalPlayers.size() <= 1){
                    stopTimer = true;
                    gameStateManager.setState(GameState.END);
                    this.cancel();
                }
                if(timerEnd){
                    this.cancel();
                }
                timer++;
            }
        }.runTaskTimer(plugin,0,20);
    }
    private void startBossBarTimer() {
        BossBar bossBar = Bukkit.createBossBar("Таймер: 180 секунд", BarColor.GREEN, BarStyle.SOLID);

        Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);

        new BukkitRunnable() {
            int timeLeft = 180;

            @Override
            public void run() {
                if (stopTimer) {
                    Bukkit.getOnlinePlayers().forEach(bossBar::removePlayer);
                    this.cancel();
                }
                if(timeLeft == 0){
                    timerEnd = true;
                    Bukkit.getOnlinePlayers().forEach(bossBar::removePlayer);
                    gameStateManager.setState(GameState.END);
                    this.cancel();
                }
                bossBar.setTitle("Таймер: " + timeLeft + " секунд");
                bossBar.setProgress(timeLeft / 180.0);
                timeLeft--;
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
