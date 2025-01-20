package org.example.da.skywarsv1.game.state;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.skywarsv1.chest.ChestManager;
import org.example.da.skywarsv1.game.GameState;
import org.example.da.skywarsv1.game.GameStateManager;
import org.example.da.skywarsv1.map.BlockChange;
import org.example.da.skywarsv1.map.GameTimer;
import org.example.da.skywarsv1.player.PlayerKillCounter;

import java.util.List;
import java.util.stream.Collectors;

public class StateGame {
    private final JavaPlugin plugin;
    private final GameStateManager gameStateManager;
    private final BlockChange blockChange;
    private final GameTimer gameTimer;
    private final PlayerKillCounter playerKillCounter;
    private final ChestManager chestManager;

    public StateGame(JavaPlugin plugin, GameStateManager gameStateManager, PlayerKillCounter playerKillCounter, ChestManager chestManager){
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
        this.playerKillCounter = playerKillCounter;
        this.chestManager = chestManager;
        this.blockChange = new BlockChange();
        this.gameTimer = new GameTimer(plugin,180,gameStateManager);
    }

    public void game(){
        blockChange.breakBlockUnderPlayer();
        gameTimer.startBossBarTimer();
        gameStage();
        playerKillCounter.startCounter();
        chestManager.startChestSystem();
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
