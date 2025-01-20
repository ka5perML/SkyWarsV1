package org.example.da.skywarsv1.player;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerKillCounter {
    @Getter
    private final List<PlayerKill> playerKills;
    public PlayerKillCounter(){
        this.playerKills = new ArrayList<>();
    }

    public void startCounter(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            playerKills.add(new PlayerKill(player, 0));
        });
    }

    public List<PlayerKill> getSortedList(){
        return playerKills.stream()
                .sorted(Comparator.comparingInt(PlayerKill::getKillCount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public void addKill(Player player){
        playerKills.forEach(playerKill -> {
            if (playerKill.getPlayer().equals(player)) playerKill.setKillCount(playerKill.getKillCount() + 1);
        });
    }
}
