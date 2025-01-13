package org.example.da.skywarsv1.gameStation.state;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.example.da.skywarsv1.gameStation.GameState;
import org.example.da.skywarsv1.gameStation.GameStateManager;
import org.example.da.skywarsv1.mapSetting.MapLocation;

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
            player.teleport(MapLocation.SPAWN.getLocations().get(0));
        });
        Thread.sleep(5000);
        gameStateManager.setState(GameState.LOBBY);
    }
    private void messageWinner(){
        List<Player> playerList = Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getGameMode() == GameMode.SURVIVAL).collect(Collectors.toList());
        Bukkit.broadcastMessage("Победитель " + playerList.get(0).getDisplayName());
    }
}
