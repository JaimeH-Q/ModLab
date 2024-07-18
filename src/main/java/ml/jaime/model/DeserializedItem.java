package ml.jaime.files;

import org.bukkit.inventory.ItemStack;

public class DeserializedItem {
    private final ItemStack itemStack;
    private final int slot;

    public DeserializedItem(ItemStack itemStack, int slot) {
        this.itemStack = itemStack;
        this.slot = slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getSlot() {
        return slot;
    }
}
