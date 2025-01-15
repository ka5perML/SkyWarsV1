package org.example.da.skywarsv1;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.skywarsv1.gameStation.GameStateListener;
import org.example.da.skywarsv1.gameStation.GameStateManager;
import org.example.da.skywarsv1.mapSetting.MapLoad;
import org.example.da.skywarsv1.mapSetting.PlayerTeleport;
import org.example.da.skywarsv1.playerSetting.PlayerListener;
import org.example.da.skywarsv1.playerSetting.PlayerSetting;

public final class SkyWarsV1 extends JavaPlugin {
    private GameStateManager stageManager;
    private MapLoad mapLoad;
    private PlayerTeleport teleport;
    private PlayerSetting playerSetting;
    @Override
    public void onEnable() {
        this.mapLoad = new MapLoad(this);
        this.teleport = new PlayerTeleport(mapLoad);
        this.playerSetting = new PlayerSetting(this);
        this.stageManager = new GameStateManager(this, mapLoad, teleport, playerSetting);

        registerListener(
                new GameStateListener(stageManager, mapLoad, teleport),
                new PlayerListener(stageManager, playerSetting)
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
