package ml.jaime.model;

import org.bukkit.entity.Player;

public class InventoryPlayer {
    private final Player player;
    private InventorySection inventorySection;

    public InventoryPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public InventorySection getInventorySection() {
        return inventorySection;
    }

    public void setInventorySection(InventorySection inventorySection) {
        this.inventorySection = inventorySection;
    }
}
