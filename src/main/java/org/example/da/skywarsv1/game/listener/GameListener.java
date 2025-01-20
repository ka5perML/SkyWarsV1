package org.example.da.skywarsv1.game.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.da.skywarsv1.map.loader.MapLoad;
import org.example.da.skywarsv1.player.BlockBreakerForPlayer;
import org.example.da.skywarsv1.player.PlayerKillCounter;
import org.example.da.skywarsv1.player.PlayerSetting;

public class GameListener implements Listener {
    private final MapLoad mapLoad;
    private final PlayerSetting playerSetting;
    private final BlockBreakerForPlayer breaker;
    private final PlayerKillCounter playerKillCounter;

    public GameListener(MapLoad mapLoad, PlayerSetting playerSetting, BlockBreakerForPlayer breaker, PlayerKillCounter playerKillCounter) {
        this.mapLoad = mapLoad;
        this.playerSetting = playerSetting;
        this.breaker = breaker;
        this.playerKillCounter = playerKillCounter;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(mapLoad.getLocationSpawn().get(0));
        player.getPlayer().sendMessage("Игра уже идет.");
        playerSetting.gameStart(player);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player playerKiller = event.getEntity().getKiller();
        playerKillCounter.addKill(playerKiller);

        Player diedPlayer = event.getEntity();
        diedPlayer.spigot().respawn();
        playerSetting.gameStart(diedPlayer);
        diedPlayer.teleport(mapLoad.getLocationSpawn().get(0));
    }

    @EventHandler
    public void onPlayerPlace(BlockPlaceEvent event){
        Block block = event.getBlock();
        Location location = block.getLocation();

        breaker.addPlayerPutBlock(location);
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
}
