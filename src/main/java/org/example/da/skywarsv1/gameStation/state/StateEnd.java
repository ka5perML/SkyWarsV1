package org.example.da.skywarsv1.gameStation.state;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.example.da.skywarsv1.mapSetting.BlockChange;
import org.example.da.skywarsv1.playerSetting.BlockBreakerForPlayer;
import org.example.da.skywarsv1.playerSetting.PlayerKill;
import org.example.da.skywarsv1.playerSetting.PlayerKillCounter;

import java.util.List;
import java.util.stream.Collectors;

public class StateEnd {
    private final BlockChange blockChange;
    private final BlockBreakerForPlayer breaker;
    private final PlayerKillCounter playerKillCounter;
    public StateEnd(BlockChange blockChange, BlockBreakerForPlayer breaker, PlayerKillCounter playerKillCounter){
        this.blockChange = blockChange;
        this.breaker = breaker;
        this.playerKillCounter = playerKillCounter;
    }
    public void endGame(){
        messageWinner();
        gameEnd();
    }
    @SneakyThrows
    private void gameEnd(){
        breaker.getPlayerPutBlock().forEach(blockChange::replaceBlockInAir);
        Thread.sleep(10000);
        Bukkit.reload();
    }
    @SneakyThrows
    private void messageWinner(){
        List<String> playerList = Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getGameMode() == GameMode.SURVIVAL)
                .map(player -> player.getDisplayName())
                .collect(Collectors.toList());
        List<PlayerKill> playerKillList = playerKillCounter.getSortedList();
        Bukkit.broadcastMessage("Победитель " + playerList);
        Bukkit.broadcastMessage("Топ 3 по убийствам \n" +
                "1. " + playerKillList.get(0).getPlayer() + " = " + playerKillList.get(0).getKillCount() + "\n" +
                "2. " + playerKillList.get(1).getPlayer() + " = " + playerKillList.get(0).getKillCount() + "\n" +
                "3. " + playerKillList.get(2).getPlayer() + " = " + playerKillList.get(0).getKillCount());
    }
}
