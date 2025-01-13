package org.example.da.skywarsv1.gameStation;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.skywarsv1.chestManager.ChestManager;
import org.example.da.skywarsv1.gameStation.state.StateEnd;
import org.example.da.skywarsv1.gameStation.state.StateGame;
import org.example.da.skywarsv1.gameStation.state.StateLobby;
import org.example.da.skywarsv1.gameStation.state.StateStart;

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
    @Setter
    private ArrayList<Player> playerList = new ArrayList<>();
    @Getter
    private GameState gameState;
    private ChestManager chestManager;
    public GameStateManager(JavaPlugin plugin){
        this.gameState = GameState.LOBBY;
        this.stateEnd = new StateEnd(this);
        this.stateLobby = new StateLobby(plugin,this);
        this.stateStart = new StateStart(plugin,this);
        this.stateGame = new StateGame(plugin,this);
        this.chestManager = new ChestManager(plugin);
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
    public void playerSetting(){
        for(Player player : Bukkit.getOnlinePlayers()){
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20);
        }
    }
}
