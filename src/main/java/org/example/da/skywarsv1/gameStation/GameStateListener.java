package org.example.da.skywarsv1.gameStation;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.da.skywarsv1.mapSetting.MapLocation;

import java.util.ArrayList;
import java.util.List;

public class GameStateListener implements Listener {
    private final GameStateManager gameStateManager;
    private List<Location> mapLocations = new ArrayList<>(MapLocation.SPAWN.getLocations());
    public GameStateListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (gameStateManager.getGameState() == GameState.LOBBY) {
            gameStateManager.playerSetting();
            event.getPlayer().teleport(mapLocations.get(0));
            event.getPlayer().sendMessage("Игра еще не началась.");
            return;
        }
        event.getPlayer().sendMessage("Вы наблюдатель.");
        event.getPlayer().setGameMode(GameMode.SPECTATOR);
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        if(gameStateManager.getGameState() != GameState.GAME) return;
        Player player = e.getEntity().getPlayer();
        player.setGameMode(GameMode.SPECTATOR);
    }
}
