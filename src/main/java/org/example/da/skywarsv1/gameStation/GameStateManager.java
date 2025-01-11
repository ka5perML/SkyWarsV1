package org.example.da.skywarsv1.gameStation;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
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
    @Getter
    @Setter
    private ArrayList<Player> playerList = new ArrayList<>();
    @Getter
    private GameStage gameStage;
    public GameStateManager(JavaPlugin plugin){
        this.gameStage = GameStage.LOBBY;
        this.stateLobby = new StateLobby(plugin,this);
        this.stateStart = new StateStart(plugin,this);
        stageChange(gameStage);
    }
    public void setState(GameStage stage) {
        this.gameStage = stage;
        Bukkit.broadcastMessage("Стадия " + stage.name());
        stageChange(stage);
    }

    private void stageChange(GameStage gameStage){
        switch (gameStage){
            case LOBBY:
                stateLobby.checkPlayerInGame();
                break;
            case START:
                stateStart.startGame();
                startCountdown();
                break;
            case GAME:
                startGame();
                break;
            case END:
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
