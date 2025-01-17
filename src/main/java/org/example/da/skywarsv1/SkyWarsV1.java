package org.example.da.skywarsv1;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.skywarsv1.gameStation.GameStateListener;
import org.example.da.skywarsv1.gameStation.GameStateManager;
import org.example.da.skywarsv1.mapSetting.MapLoad;
import org.example.da.skywarsv1.mapSetting.PlayerTeleport;
import org.example.da.skywarsv1.playerSetting.BlockBreakerForPlayer;
import org.example.da.skywarsv1.playerSetting.PlayerKillCounter;
import org.example.da.skywarsv1.playerSetting.PlayerListener;
import org.example.da.skywarsv1.playerSetting.PlayerSetting;

public final class SkyWarsV1 extends JavaPlugin {
    private PlayerKillCounter playerKillCounter;
    @Override
    public void onEnable() {
        BlockBreakerForPlayer breaker = new BlockBreakerForPlayer();
        MapLoad mapLoad = new MapLoad(this);
        PlayerTeleport teleport = new PlayerTeleport(mapLoad);
        PlayerSetting playerSetting = new PlayerSetting(this);
        GameStateManager stageManager = new GameStateManager(this, mapLoad, teleport, playerSetting, breaker, playerKillCounter);
        this.playerKillCounter = new PlayerKillCounter();

        registerListener(
                new GameStateListener(stageManager, mapLoad, teleport,playerKillCounter),
                new PlayerListener(stageManager, playerSetting, breaker)
        );

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
