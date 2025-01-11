package org.example.da.skywarsv1.gameStation.state;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.skywarsv1.gameStation.GameStage;
import org.example.da.skywarsv1.gameStation.GameStateManager;
import org.example.da.skywarsv1.mapSetting.MapLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StateStart {
    private JavaPlugin plugin;
    private GameStateManager gameStateManager;
    public StateStart(JavaPlugin plugin,GameStateManager gameStateManager){
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
    }
    public void startGame(){
        gameStateStart();
    }
    private void gameStateStart(){
        if(gameStateManager.getGameStage() != GameStage.START) return;
        new BukkitRunnable() {
            int countdown = 10;
            boolean fastTimer = false;
            @Override
            public void run() {
                int onlinePlayersCount = Bukkit.getOnlinePlayers().size();
                if(onlinePlayersCount > gameStateManager.getMaxPlayer()){
                    kickRandomPlayer();
                    return;
                }
                if(onlinePlayersCount < gameStateManager.getMinPlayer()){
                    gameStateManager.setState(GameStage.LOBBY);
                    this.cancel();
                    return;
                }
                if(countdown == 0){
                    teleportInLocationPlayer();
                    playerSetting();
                    gameStateManager.setState(GameStage.GAME);
                    this.cancel();
                    return;
                }
                Bukkit.broadcastMessage(String.valueOf(countdown));
                countdown--;
            }
        }.runTaskTimer(plugin,0,20);
    }
    private void kickRandomPlayer(){
        Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]);

        Random random = new Random();
        Player randomPlayer = onlinePlayers[random.nextInt(onlinePlayers.length)];
        randomPlayer.kickPlayer("full");
    }
    private void teleportInLocationPlayer(){
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        List<Location> mapLocations = new ArrayList<>(MapLocation.MAP1.getLocations());
        players.forEach(player -> {
            player.teleport(mapLocations.get(players.indexOf(player)));
        });
    }
    private void playerSetting(){
        for(Player player : Bukkit.getOnlinePlayers()){
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20);
        }
    }
}
