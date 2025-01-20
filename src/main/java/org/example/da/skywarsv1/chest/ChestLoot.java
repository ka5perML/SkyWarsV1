package org.example.da.skywarsv1.chest;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ChestLoot {
    private final Random random = new Random();

    // Заполнение сундуков
    public void fillChest(Inventory inventory, boolean isMidChest) {
        inventory.clear(); // Очищаем сундук перед заполнением

        if (!isMidChest) {
            inventory.addItem(new ItemStack(Material.IRON_SWORD));
            inventory.addItem(new ItemStack(Material.COOKED_BEEF, random.nextInt(3) + 1));
            inventory.addItem(new ItemStack(Material.WOOD, random.nextInt(5) + 3));
        }

        if (isMidChest) {
            inventory.addItem(new ItemStack(Material.DIAMOND_SWORD));
            inventory.addItem(new ItemStack(Material.GOLDEN_APPLE, random.nextInt(2) + 1));
            inventory.addItem(new ItemStack(Material.ENDER_PEARL, random.nextInt(2) + 1));
        }
    }
}
