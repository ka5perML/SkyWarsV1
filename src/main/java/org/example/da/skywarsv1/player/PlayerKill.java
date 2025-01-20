package org.example.da.skywarsv1.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerKill {
    private Player player;
    private int killCount;
}
