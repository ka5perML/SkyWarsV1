package org.example.da.skywarsv1.gameStation.state;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.example.da.skywarsv1.gameStation.GameState;
import org.example.da.skywarsv1.gameStation.GameStateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StateEnd {
    private GameStateManager gameStateManager;
    public StateEnd(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
    }
    public void endGame(){
        messageWinner();
        gameEnd();
    }
    @SneakyThrows
    private void gameEnd(){
        List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        playerList.forEach(player -> {
            player.setHealth(20);
            player.setGameMode(GameMode.SURVIVAL);
        });
        Thread.sleep(5000);
        Bukkit.reload();
    }
    private void messageWinner(){
        List<Player> playerList = Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getGameMode() == GameMode.SURVIVAL)
                .collect(Collectors.toList());
        Bukkit.broadcastMessage("Победитель " + playerList.stream().toList());
    }
}
