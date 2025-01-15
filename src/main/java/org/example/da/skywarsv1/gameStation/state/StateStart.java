package org.example.da.skywarsv1.gameStation.state;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.skywarsv1.gameStation.GameState;
import org.example.da.skywarsv1.gameStation.GameStateManager;
import org.example.da.skywarsv1.mapSetting.MapLoad;
import org.example.da.skywarsv1.mapSetting.PlayerTeleport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StateStart {
    private JavaPlugin plugin;
    private GameStateManager gameStateManager;
    private MapLoad mapLoader;
    private PlayerTeleport teleport;
    private int timer = 20;
    public StateStart(JavaPlugin plugin,GameStateManager gameStateManager, MapLoad mapLoader, PlayerTeleport teleport){
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
        this.mapLoader = mapLoader;
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
