package org.example.da.skywarsv1.game.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.da.skywarsv1.map.loader.MapLoad;
import org.example.da.skywarsv1.player.PlayerSetting;

public class LobbyListener implements Listener {
    private final MapLoad mapLoad;
    private final PlayerSetting playerSetting;

    public LobbyListener(MapLoad mapLoad, PlayerSetting playerSetting) {
        this.mapLoad = mapLoad;
        this.playerSetting = playerSetting;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(mapLoad.getLocationSpawn().get(0));
        player.getPlayer().sendMessage("Игра еще не началась.");
        playerSetting.gameDontStart(player);
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e){
        e.setCancelled(true);
    }
}
