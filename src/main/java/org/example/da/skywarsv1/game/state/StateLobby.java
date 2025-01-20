package org.example.da.skywarsv1.game.state;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.skywarsv1.game.GameState;
import org.example.da.skywarsv1.game.GameStateManager;
import org.example.da.skywarsv1.map.BlockChange;
import org.example.da.skywarsv1.map.loader.MapLoad;
import org.example.da.skywarsv1.player.PlayerSetting;

import java.util.ArrayList;
import java.util.List;

public class StateLobby {
    private final JavaPlugin plugin;
    private final GameStateManager gameStateManager;
    private final MapLoad mapLoad;
    private final BlockChange blockChange;
    private final PlayerSetting playerSetting;
    public StateLobby(JavaPlugin plugin, GameStateManager gameStateManager, MapLoad mapLoad, PlayerSetting playerSetting){
        this.plugin = plugin;
        this.mapLoad = mapLoad;
        this.gameStateManager = gameStateManager;
        this.blockChange = new BlockChange();
        this.playerSetting = playerSetting;
    }

    public void checkPlayerInGame(){
        if(gameStateManager.getGameState() != GameState.LOBBY) return;
        blockChange.changeBlock(mapLoad.getLocationPlayerList().toArray(new Location[0]));
        Bukkit.getOnlinePlayers().forEach(player -> {
            playerRemove(player);
        });
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
    public void playerRemove(Player player){
        playerSetting.gameDontStart(player);
        player.setAllowFlight(false);
    }
}
