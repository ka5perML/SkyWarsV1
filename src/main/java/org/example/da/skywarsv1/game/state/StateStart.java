package org.example.da.skywarsv1.game.state;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.example.da.skywarsv1.game.GameState;
import org.example.da.skywarsv1.game.GameStateManager;
import org.example.da.skywarsv1.map.loader.MapLoad;
import org.example.da.skywarsv1.map.PlayerTeleport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StateStart {
    private final JavaPlugin plugin;
    private final GameStateManager gameStateManager;
    private final PlayerTeleport teleport;
    private int timer;
    public StateStart(JavaPlugin plugin,GameStateManager gameStateManager, MapLoad mapLoader, PlayerTeleport teleport){
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
        this.teleport = teleport;
    }

    public void startGame(){
        Bukkit.getOnlinePlayers().forEach( player -> {
            teleport.teleportCleanLocation(player);
        });
        gameStateStart();
    }

    private void gameStateStart(){
        if(gameStateManager.getGameState() != GameState.START) return;
        timer = 20;
        new BukkitRunnable() {
            @Override
            public void run() {
                int onlinePlayersCount = Bukkit.getOnlinePlayers().size();
                if(onlinePlayersCount > gameStateManager.getMaxPlayer()){
                    kickRandomPlayer();
                }
                if(onlinePlayersCount < gameStateManager.getMinPlayer()){
                    gameStateManager.setState(GameState.LOBBY);
                    this.cancel();
                }
                if(timer == 0){
                    gameStateManager.setState(GameState.GAME);
                    this.cancel();
                }
                Bukkit.broadcastMessage(String.valueOf(timer));
                timer--;
            }
        }.runTaskTimer(plugin,0,20);
    }

    private void kickRandomPlayer(){
        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());

        Random random = new Random();
        Player randomPlayer = onlinePlayers.get(random.nextInt(onlinePlayers.size()));
        randomPlayer.kickPlayer("full");
    }
}
