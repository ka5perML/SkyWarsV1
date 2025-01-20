package org.example.da.skywarsv1.game;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.skywarsv1.chest.ChestManager;
import org.example.da.skywarsv1.game.listener.EndListener;
import org.example.da.skywarsv1.game.listener.GameListener;
import org.example.da.skywarsv1.game.listener.LobbyListener;
import org.example.da.skywarsv1.game.listener.StartListener;
import org.example.da.skywarsv1.game.state.StateEnd;
import org.example.da.skywarsv1.game.state.StateGame;
import org.example.da.skywarsv1.game.state.StateLobby;
import org.example.da.skywarsv1.game.state.StateStart;
import org.example.da.skywarsv1.map.BlockChange;
import org.example.da.skywarsv1.map.loader.MapLoad;
import org.example.da.skywarsv1.map.PlayerTeleport;
import org.example.da.skywarsv1.map.ResetWorld;
import org.example.da.skywarsv1.player.BlockBreakerForPlayer;
import org.example.da.skywarsv1.player.PlayerKillCounter;
import org.example.da.skywarsv1.player.PlayerSetting;

import java.util.HashMap;
import java.util.Map;

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
    private final ResetWorld resetWorld;
    private final Map<GameState, Listener> stateListeners = new HashMap<>();
    private Listener currentListener;
    private final JavaPlugin plugin;

    public GameStateManager(JavaPlugin plugin, MapLoad mapLoad, PlayerTeleport teleport, PlayerSetting playerSetting,
                            BlockBreakerForPlayer breaker, PlayerKillCounter playerKillCounter, ResetWorld resetWorld){
        this.resetWorld = resetWorld;
        this.plugin = plugin;
        this.gameState = GameState.LOBBY;
        this.blockChange = new BlockChange();
        this.stateEnd = new StateEnd(blockChange,breaker, playerKillCounter, plugin,resetWorld, this);
        this.chestManager = new ChestManager(plugin, mapLoad, this);
        this.stateLobby = new StateLobby(plugin,this, mapLoad, playerSetting);
        this.stateStart = new StateStart(plugin,this, mapLoad, teleport);
        this.stateGame = new StateGame(plugin,this, playerKillCounter, chestManager);
        this.playerKillCounter = playerKillCounter;
        stateListeners.put(GameState.LOBBY, new LobbyListener(mapLoad, playerSetting));
        stateListeners.put(GameState.START, new StartListener(mapLoad, playerSetting, teleport));
        stateListeners.put(GameState.GAME, new GameListener(mapLoad, playerSetting, breaker, playerKillCounter));
        stateListeners.put(GameState.END, new EndListener(mapLoad, playerSetting));
        setState(gameState);
    }

    public void setState(GameState state) {
        this.gameState = state;
        Bukkit.broadcastMessage("Стадия " + state.name());
        stateChange(state);

        if (currentListener != null) {
            unregisterListener(currentListener);
        }

        Listener newListener = stateListeners.get(state);
        if (newListener != null) {
            registerListener(newListener);
            currentListener = newListener;
        }
    }

    private void stateChange(GameState gameStage){
        switch (gameState){
            case LOBBY:
                stateLobby.checkPlayerInGame();
                break;
            case START:
                stateStart.startGame();
                break;
            case GAME:
                stateGame.game();
                break;
            case END:
                stateEnd.endGame();
                break;

        }
    }

    public void restartGame() {
        setState(GameState.LOBBY);
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    private void unregisterListener(Listener listener) {
        HandlerList.unregisterAll(listener);
    }
}
