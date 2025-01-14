package org.example.da.skywarsv1.gameStation;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.da.skywarsv1.mapSetting.Spawn;

public class GameStateListener implements Listener {
    private final GameStateManager gameStateManager;
    private Spawn spawn;

    public GameStateListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.spawn = new Spawn();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (gameStateManager.getGameState() == GameState.LOBBY) {
            gameStateManager.playerSetting();
            spawn.teleportPlayerInSpawn(event.getPlayer());
            event.getPlayer().sendMessage("Игра еще не началась.");
            return;
        }
        event.getPlayer().sendMessage("Вы наблюдатель.");
        event.getPlayer().setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (gameStateManager.getGameState() == GameState.GAME) {
            event.getEntity().spigot().respawn();
            event.getEntity().setGameMode(GameMode.SPECTATOR);
        }
    }
}
