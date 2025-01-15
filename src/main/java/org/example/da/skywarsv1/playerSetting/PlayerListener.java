package org.example.da.skywarsv1.playerSetting;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.da.skywarsv1.gameStation.GameState;
import org.example.da.skywarsv1.gameStation.GameStateManager;

public class PlayerListener implements Listener {
    private GameStateManager gameStateManager;
    private PlayerSetting playerSetting;
    private BlockBreakerForPlayer breaker;
    public PlayerListener(GameStateManager gameStateManager, PlayerSetting playerSetting, BlockBreakerForPlayer breaker){
        this.gameStateManager = gameStateManager;
        this.playerSetting = playerSetting;
        this.breaker = breaker;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);
        if (gameStateManager.getGameState() == GameState.START || gameStateManager.getGameState() == GameState.LOBBY){
            playerSetting.playerSetting(e.getPlayer());
        }
    }
    @EventHandler
    public void onPlayerBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        Location location = block.getLocation();

        if(breaker.isBlockBreak(location) || event.getPlayer().getGameMode() == GameMode.CREATIVE){
            breaker.removeBlockBreak(location);
            return;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void onPlayerPlace(BlockPlaceEvent event){
        Block block = event.getBlock();
        Location location = block.getLocation();

        breaker.addPlayerPutBlock(location);
    }
}
