package org.example.da.skywarsv1.gameStation;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.da.skywarsv1.mapSetting.MapLoad;
import org.example.da.skywarsv1.mapSetting.PlayerTeleport;

public class GameStateListener implements Listener {
    private final GameStateManager gameStateManager;
    private MapLoad mapLoad;
    private PlayerTeleport teleport;

    public GameStateListener(GameStateManager gameStateManager, MapLoad mapLoad, PlayerTeleport teleport) {
        this.gameStateManager = gameStateManager;
        this.mapLoad = mapLoad;
        this.teleport = teleport;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (gameStateManager.getGameState() == GameState.LOBBY) {
            event.getPlayer().teleport(mapLoad.getLocationSpawn().get(0));
            event.getPlayer().sendMessage("Игра еще не началась.");
        }else if (gameStateManager.getGameState() == GameState.START){
            teleport.teleportCleanLocation(event.getPlayer());
        }else {
            event.getPlayer().sendMessage("Вы наблюдатель.");
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (gameStateManager.getGameState() == GameState.GAME) {
            event.getEntity().spigot().respawn();
            event.getEntity().getPlayer().teleport(mapLoad.getLocationSpawn().get(0));
            event.getEntity().getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }
}
