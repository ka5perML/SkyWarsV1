package org.example.da.skywarsv1.gameStation.state;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.skywarsv1.gameStation.GameState;
import org.example.da.skywarsv1.gameStation.GameStateManager;
import org.example.da.skywarsv1.mapSetting.BlockChange;
import org.example.da.skywarsv1.mapSetting.GameTimer;

import java.util.List;
import java.util.stream.Collectors;

public class StateGame {
    private JavaPlugin plugin;
    private GameStateManager gameStateManager;
    private BlockChange blockChange;
    private GameTimer gameTimer;
    public StateGame(JavaPlugin plugin, GameStateManager gameStateManager){
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
        this.blockChange = new BlockChange();
        this.gameTimer = new GameTimer(plugin,180,gameStateManager);
    }

    public void game(){
        blockChange.breakBlockUnderPlayer();
        gameTimer.startBossBarTimer();
        gameStage();

    }
    private void gameStage(){
        new BukkitRunnable() {
            @Override
            public void run() {
                List<Player> survivalPlayers = Bukkit.getOnlinePlayers().stream()
                        .filter(player -> player.getGameMode() == GameMode.SURVIVAL)
                        .collect(Collectors.toList());
                if(survivalPlayers.size() <= 1 || gameStateManager.getGameState() != GameState.GAME){
                    gameStateManager.setState(GameState.END);
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin,0,20);
    }

}
