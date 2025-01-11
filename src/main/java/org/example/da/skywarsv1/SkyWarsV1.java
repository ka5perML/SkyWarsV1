package org.example.da.skywarsv1;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.skywarsv1.gameStation.GameStateListener;
import org.example.da.skywarsv1.gameStation.GameStateManager;

public final class SkyWarsV1 extends JavaPlugin {
    private GameStateManager stageManager;

    @Override
    public void onEnable() {
        stageManager = new GameStateManager(this);

        registerListener(
                new GameStateListener(stageManager)
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
