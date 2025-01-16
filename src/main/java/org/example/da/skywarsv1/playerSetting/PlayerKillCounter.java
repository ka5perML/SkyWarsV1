package org.example.da.skywarsv1.playerSetting;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class PlayerKillCounter {
    private List<PlayerKill> playerKills;
    public PlayerKillCounter(){
        this.playerKills = new ArrayList<>();
    }
    public void startCounter(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            playerKills.add(new PlayerKill(player, 0));
        });
    }

}
