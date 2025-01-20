package org.example.da.skywarsv1.game.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.da.skywarsv1.map.loader.MapLoad;
import org.example.da.skywarsv1.player.PlayerSetting;


public class EndListener implements Listener {
    private final MapLoad mapLoad;
    private final PlayerSetting playerSetting;

    public EndListener(MapLoad mapLoad, PlayerSetting playerSetting){
        this.mapLoad = mapLoad;
        this.playerSetting = playerSetting;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(mapLoad.getLocationSpawn().get(0));
        player.getPlayer().sendMessage("Игра уже закончилась.");
        playerSetting.gameStart(player);
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPlace(BlockPlaceEvent event){
        event.setCancelled(true);
    }
}
