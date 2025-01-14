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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StateStart {
    private JavaPlugin plugin;
    private GameStateManager gameStateManager;
    private MapLoad mapLoader;
    public StateStart(JavaPlugin plugin,GameStateManager gameStateManager, MapLoad mapLoader){
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
        this.mapLoader = mapLoader;
    }
    public void startGame(){
        teleportInLocationPlayer();
        gameStateStart();
    }
    private void gameStateStart(){
        if(gameStateManager.getGameState() != GameState.START) return;
        new BukkitRunnable() {
            int countdown = 60;
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
                if(countdown == 0){
                    gameStateManager.setState(GameState.GAME);
                    this.cancel();
                }
                Bukkit.broadcastMessage(String.valueOf(countdown));
                countdown--;
            }
        }.runTaskTimer(plugin,0,20);
    }
    private void kickRandomPlayer(){
        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());

        Random random = new Random();
        Player randomPlayer = onlinePlayers.get(random.nextInt(onlinePlayers.size()));
        randomPlayer.kickPlayer("full");
    }
    @SneakyThrows
    private void teleportInLocationPlayer() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        List<Location> busyLocation = new ArrayList<>();
        List<Location> mapLocations = mapLoader.getLocationPlayerList();

        for (int i = 0; i < players.size(); i++) {
            if (mapLocations.size() < i) break;
            if(!busyLocation.contains(mapLocations.get(i))) {
                players.get(i).teleport(mapLocations.get(i));
                busyLocation.add(mapLocations.get(i));
            }
        }
    }
}
