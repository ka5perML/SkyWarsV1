package org.example.da.skywarsv1.gameStation.state;

import org.bukkit.Bukkit;
import org.bukkit.Color;
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
                if(survivalPlayers.size() >= 1){
                    gameStateManager.setState(GameState.END);
                    this.cancel();
                }
                timer++;
            }
        }.runTaskTimer(plugin,0,20);
    }
    private void startBossBarTimer() {
        BossBar bossBar = Bukkit.createBossBar("Таймер: 60 секунд", BarColor.GREEN, BarStyle.SOLID);

        Bukkit.getOnlinePlayers().forEach(player -> {
            bossBar.addPlayer(player);
        });

        new BukkitRunnable() {
            int timeLeft = 180; // Начинаем с 60 секунд

            @Override
            public void run() {
                if (timeLeft <= 0 || gameStateManager.getGameState() != GameState.GAME) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        bossBar.removePlayer(player);
                    });
                    this.cancel();
                }
                if (timeLeft <= 0 && gameStateManager.getGameState() == GameState.GAME) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        bossBar.removePlayer(player);
                    });
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
