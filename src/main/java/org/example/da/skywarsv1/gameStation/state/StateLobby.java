package org.example.da.skywarsv1.gameStation.state;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.skywarsv1.gameStation.GameState;
import org.example.da.skywarsv1.gameStation.GameStateManager;
import org.example.da.skywarsv1.mapSetting.MapLocation;

import java.util.ArrayList;
import java.util.List;

public class StateLobby {
    private JavaPlugin plugin;
    private GameStateManager gameStateManager;
    public StateLobby(JavaPlugin plugin, GameStateManager gameStateManager){
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
    }
    public void checkPlayerInGame(){
        if(gameStateManager.getGameState() != GameState.LOBBY) return;
        teleportInLocationSpawn();
        new BukkitRunnable() {
            int ticks = 0;
            @Override
            public void run() {
                int onlinePlayersCount = Bukkit.getOnlinePlayers().size();
                if(onlinePlayersCount >= gameStateManager.getMinPlayer()){
                    gameStateManager.setState(GameState.START);
                    this.cancel();
                }
                if(ticks % 20 == 0) Bukkit.broadcastMessage("Еще " + (gameStateManager.getMinPlayer() - onlinePlayersCount) + " игрок.");
                ticks++;
            }
        }.runTaskTimer(plugin,0,20);
    }
    private void teleportInLocationSpawn(){
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        List<Location> mapLocations = new ArrayList<>(MapLocation.SPAWN.getLocations());
        players.forEach(player -> {
            player.teleport(mapLocations.get(0));
        });
    }
}
