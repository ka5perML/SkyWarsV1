package org.example.da.skywarsv1.playerSetting;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.da.skywarsv1.gameStation.GameState;
import org.example.da.skywarsv1.gameStation.GameStateManager;

public class PlayerListener implements Listener {
    private GameStateManager gameStateManager;
    private PlayerSetting playerSetting;
    public PlayerListener(GameStateManager gameStateManager, PlayerSetting playerSetting){
        this.gameStateManager = gameStateManager;
        this.playerSetting = playerSetting;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);
        if (gameStateManager.getGameState() == GameState.START || gameStateManager.getGameState() == GameState.LOBBY){
            playerSetting.playerSetting(e.getPlayer());
        }
    }
}
