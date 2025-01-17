package org.example.da.skywarsv1.gameStation;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.skywarsv1.chestManager.ChestManager;
import org.example.da.skywarsv1.gameStation.state.StateEnd;
import org.example.da.skywarsv1.gameStation.state.StateGame;
import org.example.da.skywarsv1.gameStation.state.StateLobby;
import org.example.da.skywarsv1.gameStation.state.StateStart;
import org.example.da.skywarsv1.mapSetting.BlockChange;
import org.example.da.skywarsv1.mapSetting.MapLoad;
import org.example.da.skywarsv1.mapSetting.PlayerTeleport;
import org.example.da.skywarsv1.playerSetting.BlockBreakerForPlayer;
import org.example.da.skywarsv1.playerSetting.PlayerKillCounter;
import org.example.da.skywarsv1.playerSetting.PlayerSetting;

public class GameStateManager {
    @Getter
    private final int maxPlayer = 12;
    @Getter
    private final int minPlayer = 2;
    private final StateLobby stateLobby;
    private final StateStart stateStart;
    private final StateGame stateGame;
    private final StateEnd stateEnd;
    @Getter
    private GameState gameState;
    private final ChestManager chestManager;
    private final BlockChange blockChange;
    private final PlayerKillCounter playerKillCounter;

    public GameStateManager(JavaPlugin plugin, MapLoad mapLoad, PlayerTeleport teleport, PlayerSetting playerSetting,
                            BlockBreakerForPlayer breaker, PlayerKillCounter playerKillCounter){
        this.gameState = GameState.LOBBY;
        this.blockChange = new BlockChange();
        this.stateEnd = new StateEnd(blockChange,breaker, playerKillCounter);
        this.stateLobby = new StateLobby(plugin,this, mapLoad, playerSetting);
        this.stateStart = new StateStart(plugin,this, mapLoad, teleport);
        this.stateGame = new StateGame(plugin,this);
        this.chestManager = new ChestManager(plugin, mapLoad);
        this.playerKillCounter = playerKillCounter;
        stateChange(gameState);
    }
    public void setState(GameState state) {
        this.gameState = state;
        Bukkit.broadcastMessage("Стадия " + state.name());
        stateChange(state);
    }

    private void stateChange(GameState gameStage){
        switch (gameState){
            case LOBBY:
                stateLobby.checkPlayerInGame();
                break;
            case START:
                stateStart.startGame();
                startCountdown();
                break;
            case GAME:
                playerKillCounter.startCounter();
                chestManager.startChestSystem();
                stateGame.game();
                startGame();
                break;
            case END:
                stateEnd.endGame();
                endGame();
                break;

        }
    }

    private void startCountdown() {
        Bukkit.getLogger().info("Обратный отсчёт начат!");
    }

    private void startGame() {
        Bukkit.getLogger().info("Игра началась!");
    }

    private void endGame() {
        Bukkit.getLogger().info("Игра завершена!");
    }
}
