package org.example.da.skywarsv1.gameStation;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.skywarsv1.chestManager.ChestManager;
import org.example.da.skywarsv1.gameStation.state.StateEnd;
import org.example.da.skywarsv1.gameStation.state.StateGame;
import org.example.da.skywarsv1.gameStation.state.StateLobby;
import org.example.da.skywarsv1.gameStation.state.StateStart;
import org.example.da.skywarsv1.mapSetting.BlockChange;
import org.example.da.skywarsv1.mapSetting.MapLoad;
import org.example.da.skywarsv1.mapSetting.PlayerTeleport;
import org.example.da.skywarsv1.playerSetting.PlayerSetting;

import java.util.ArrayList;

public class GameStateManager {
    @Getter
    private final int maxPlayer = 12;
    @Getter
    private final int minPlayer = 2;
    private StateLobby stateLobby;
    private StateStart stateStart;
    private StateGame stateGame;
    private StateEnd stateEnd;
    @Getter
    private GameState gameState;
    private ChestManager chestManager;
    private PlayerTeleport teleport;
    private BlockChange blockChange;
    private MapLoad mapLoad;
    public GameStateManager(JavaPlugin plugin, MapLoad mapLoad, PlayerTeleport teleport, PlayerSetting playerSetting){
        this.mapLoad = mapLoad;
        this.gameState = GameState.LOBBY;
        this.teleport = teleport;
        this.stateEnd = new StateEnd(this);
        this.stateLobby = new StateLobby(plugin,this, mapLoad, playerSetting);
        this.stateStart = new StateStart(plugin,this, mapLoad, teleport);
        this.stateGame = new StateGame(plugin,this);
        this.chestManager = new ChestManager(plugin, mapLoad);
        this.blockChange = new BlockChange();
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
        Bukkit.broadcastMessage("Обратный отсчёт начат!");
    }

    private void startGame() {
        Bukkit.broadcastMessage("Игра началась!");
    }

    private void endGame() {
        Bukkit.broadcastMessage("Игра завершена!");
    }
}
