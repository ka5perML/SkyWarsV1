package org.example.da.skywarsv1.gameStation;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.da.skywarsv1.mapSetting.MapLoad;
import org.example.da.skywarsv1.mapSetting.PlayerTeleport;
import org.example.da.skywarsv1.playerSetting.PlayerKillCounter;

public class GameStateListener implements Listener {
    private final GameStateManager gameStateManager;
    private final MapLoad mapLoad;
    private final PlayerTeleport teleport;
    private final PlayerKillCounter playerKillCounter;

    public GameStateListener(GameStateManager gameStateManager, MapLoad mapLoad, PlayerTeleport teleport, PlayerKillCounter playerKillCounter) {
        this.gameStateManager = gameStateManager;
        this.mapLoad = mapLoad;
        this.teleport = teleport;
        this.playerKillCounter = playerKillCounter;
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
        Player playerKiller = event.getEntity().getKiller();
        if (gameStateManager.getGameState() == GameState.GAME) {
            playerKillCounter.addKill(playerKiller);

            event.getEntity().spigot().respawn();
            event.getEntity().getPlayer().teleport(mapLoad.getLocationSpawn().get(0));
            event.getEntity().getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }
}
