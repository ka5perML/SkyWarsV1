package org.example.da.skywarsv1.game.state;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.skywarsv1.game.GameStateManager;
import org.example.da.skywarsv1.map.BlockChange;
import org.example.da.skywarsv1.map.ResetWorld;
import org.example.da.skywarsv1.player.BlockBreakerForPlayer;
import org.example.da.skywarsv1.player.PlayerKill;
import org.example.da.skywarsv1.player.PlayerKillCounter;

import java.util.List;
import java.util.stream.Collectors;

public class StateEnd {
    private final BlockChange blockChange;
    private final BlockBreakerForPlayer breaker;
    private final PlayerKillCounter playerKillCounter;
    private final JavaPlugin plugin;
    private final ResetWorld resetWorld;
    private final GameStateManager gameManager;
    public StateEnd(BlockChange blockChange, BlockBreakerForPlayer breaker, PlayerKillCounter playerKillCounter, JavaPlugin plugin, ResetWorld resetWorld, GameStateManager gameManager){
        this.blockChange = blockChange;
        this.breaker = breaker;
        this.playerKillCounter = playerKillCounter;
        this.plugin = plugin;
        this.resetWorld = resetWorld;
        this.gameManager = gameManager;
    }

    public void endGame(){
        messageWinner();
        gameEnd();
    }

    @SneakyThrows
    private void gameEnd(){
        breaker.getPlayerPutBlock().forEach(blockChange::replaceBlockInAir);

        Bukkit.getScheduler().runTaskLater(plugin,this::restartGame,200);

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setAllowFlight(true);
        });
    }

    @SneakyThrows
    private void messageWinner(){
        List<String> playerList = Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getGameMode() == GameMode.SURVIVAL)
                .map(player -> player.getDisplayName())
                .collect(Collectors.toList());

        if (!playerList.isEmpty()) {
            Bukkit.broadcastMessage("Победитель " + String.join(", ", playerList));
        } else {
            Bukkit.broadcastMessage("Нет победителей.");
        }

        List<PlayerKill> playerKillList = playerKillCounter.getSortedList();

        if (playerKillList.size() >= 3) {
            Bukkit.broadcastMessage("Топ 3 по убийствам \n" +
                    "1. " + playerKillList.get(0).getPlayer() + " = " + playerKillList.get(0).getKillCount() + "\n" +
                    "2. " + playerKillList.get(1).getPlayer() + " = " + playerKillList.get(1).getKillCount() + "\n" +
                    "3. " + playerKillList.get(2).getPlayer() + " = " + playerKillList.get(2).getKillCount());
        } else if (!playerKillList.isEmpty()) {
            Bukkit.broadcastMessage("Топ игроков по убийствам:");
            for (int i = 0; i < playerKillList.size(); i++) {
                PlayerKill playerKill = playerKillList.get(i);
                Bukkit.broadcastMessage((i + 1) + ". " + playerKill.getPlayer() + " = " + playerKill.getKillCount());
            }
        } else {
            Bukkit.broadcastMessage("Нет данных об убийствах.");
        }
    }

    private void restartGame(){
        resetWorld.resetWorld();
        gameManager.restartGame();
    }
}
