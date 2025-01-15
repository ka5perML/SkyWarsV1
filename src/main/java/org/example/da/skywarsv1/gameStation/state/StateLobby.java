package org.example.da.skywarsv1.gameStation.state;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.skywarsv1.gameStation.GameState;
import org.example.da.skywarsv1.gameStation.GameStateManager;
import org.example.da.skywarsv1.mapSetting.BlockChange;
import org.example.da.skywarsv1.mapSetting.MapLoad;
import org.example.da.skywarsv1.playerSetting.PlayerSetting;

import java.util.ArrayList;
import java.util.List;

public class StateLobby {
    private JavaPlugin plugin;
    private GameStateManager gameStateManager;
    private MapLoad mapLoad;
    private BlockChange blockChange;
    private PlayerSetting playerSetting;
    public StateLobby(JavaPlugin plugin, GameStateManager gameStateManager, MapLoad mapLoad, PlayerSetting playerSetting){
        this.plugin = plugin;
        this.mapLoad = mapLoad;
        this.gameStateManager = gameStateManager;
        this.blockChange = new BlockChange();
        this.playerSetting = playerSetting;
    }
    public void checkPlayerInGame(){
        if(gameStateManager.getGameState() != GameState.LOBBY) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            playerSetting.playerSetting(player);
        });
        blockChange.changeBlock(mapLoad.getLocationPlayerList().toArray(new Location[0]));
        teleportInLocationSpawn();
        new BukkitRunnable() {
            int ticks = 0;
            @Override
            public void run() {
                int onlinePlayersCount = Bukkit.getOnlinePlayers().size();
                if(onlinePlayersCount >= gameStateManager.getMinPlayer()){
                    gameStateManager.setState(GameState.START);
                    this.cancel();
                    return;
                }
                if(ticks % 20 == 0) Bukkit.broadcastMessage("Еще " + (gameStateManager.getMinPlayer() - onlinePlayersCount) + " игрок.");
                ticks++;
            }
        }.runTaskTimer(plugin,0,20);
    }
    private void teleportInLocationSpawn(){
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        players.forEach(player -> {
            player.teleport(mapLoad.getLocationSpawn().get(0));
        });
    }
}
