package org.example.da.skywarsv1;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.skywarsv1.game.GameStateManager;
import org.example.da.skywarsv1.map.loader.MapLoad;
import org.example.da.skywarsv1.map.PlayerTeleport;
import org.example.da.skywarsv1.map.ResetWorld;
import org.example.da.skywarsv1.player.BlockBreakerForPlayer;
import org.example.da.skywarsv1.player.PlayerKillCounter;
import org.example.da.skywarsv1.player.PlayerSetting;

public final class SkyWarsV1 extends JavaPlugin {
    @Override
    public void onEnable() {
        BlockBreakerForPlayer breaker = new BlockBreakerForPlayer();
        MapLoad mapLoad = new MapLoad(this);
        PlayerTeleport teleport = new PlayerTeleport(mapLoad);
        PlayerSetting playerSetting = new PlayerSetting(this);
        PlayerKillCounter playerKillCounter = new PlayerKillCounter();
        ResetWorld resetWorld = new ResetWorld(mapLoad, breaker, playerKillCounter);
        GameStateManager stageManager = new GameStateManager(this, mapLoad, teleport, playerSetting, breaker, playerKillCounter,resetWorld);

        Bukkit.getLogger().info("[SkyWars] online");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[SkyWars] offline");
    }

    private void registerListener(Listener... listeners){
        PluginManager pluginManager = getServer().getPluginManager();
        for(Listener listener : listeners){
            pluginManager.registerEvents(listener,this);
        }
    }
}
