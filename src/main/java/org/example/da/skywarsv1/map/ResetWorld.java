package org.example.da.skywarsv1.map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.example.da.skywarsv1.map.loader.MapLoad;
import org.example.da.skywarsv1.player.BlockBreakerForPlayer;
import org.example.da.skywarsv1.player.PlayerKillCounter;

public class ResetWorld {
    private final MapLoad mapLoad;
    private final BlockBreakerForPlayer breaker;
    private final PlayerKillCounter playerKillCounter;

    public ResetWorld(MapLoad mapLoad, BlockBreakerForPlayer breaker, PlayerKillCounter playerKillCounter) {
        this.mapLoad = mapLoad;
        this.breaker = breaker;
        this.playerKillCounter = playerKillCounter;
    }

    public void resetWorld() {
        World world = Bukkit.getWorld("world");
        if (world != null) {

            world.getEntities().stream()
                    .filter(entity -> !(entity instanceof Player))
                    .forEach(Entity::remove);

            Location spawnLocation = mapLoad.getLocationSpawn().get(0);

            for (Player player : world.getPlayers()) {
                player.teleport(spawnLocation);
                player.getInventory().clear();
                player.setHealth(20.0);
                player.setFlying(false);
            }

        }
        playerKillCounter.getPlayerKills().clear();
        breaker.clean();
    }
}
