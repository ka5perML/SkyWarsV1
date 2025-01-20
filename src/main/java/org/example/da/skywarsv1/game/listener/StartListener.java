package org.example.da.skywarsv1.game.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.da.skywarsv1.map.loader.MapLoad;
import org.example.da.skywarsv1.map.PlayerTeleport;
import org.example.da.skywarsv1.player.PlayerSetting;

public class StartListener implements Listener {
    private final MapLoad mapLoad;
    private final PlayerSetting playerSetting;
    private final PlayerTeleport teleport;


    public StartListener(MapLoad mapLoad, PlayerSetting playerSetting, PlayerTeleport teleport) {
        this.mapLoad = mapLoad;
        this.playerSetting = playerSetting;
        this.teleport = teleport;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        teleport.teleportCleanLocation(player);
        player.getPlayer().sendMessage("Игра еще не началась.");
        playerSetting.gameDontStart(player);
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e){
        e.setCancelled(true);
    }
}
