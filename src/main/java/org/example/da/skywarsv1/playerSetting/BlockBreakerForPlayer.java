package org.example.da.skywarsv1.playerSetting;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class BlockBreakerForPlayer {
    @Getter
    private List<Location> playerPutBlock;

    public BlockBreakerForPlayer(){
        this.playerPutBlock = new ArrayList<>();
    }
    public void addPlayerPutBlock(Location location){
        playerPutBlock.add(location);
    }
    public void removeBlockBreak(Location location){
        playerPutBlock.remove(location);
    }

    public boolean isBlockBreak(Location location){
        return playerPutBlock.contains(location);
    }
}
