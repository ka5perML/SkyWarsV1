package org.example.da.skywarsv1.gameStation.state;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.example.da.skywarsv1.gameStation.GameStateManager;
import org.example.da.skywarsv1.mapSetting.BlockChange;
import org.example.da.skywarsv1.playerSetting.BlockBreakerForPlayer;

import java.util.List;
import java.util.stream.Collectors;

public class StateEnd {
    private GameStateManager gameStateManager;
    private BlockChange blockChange;
    private BlockBreakerForPlayer breaker;
    public StateEnd(GameStateManager gameStateManager, BlockChange blockChange, BlockBreakerForPlayer breaker){
        this.gameStateManager = gameStateManager;
        this.blockChange = blockChange;
        this.breaker = breaker;
    }
    public void endGame(){
        messageWinner();
        gameEnd();
    }
    @SneakyThrows
    private void gameEnd(){
        breaker.getPlayerPutBlock().forEach(location->{
            blockChange.replaceBlockInAir(location);
        });
        Thread.sleep(10000);
        Bukkit.reload();
    }
    @SneakyThrows
    private void messageWinner(){
        List<String> playerList = Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getGameMode() == GameMode.SURVIVAL)
                .map(player -> player.getDisplayName())
                .collect(Collectors.toList());
        Bukkit.broadcastMessage("Победитель " + playerList);
    }
}
