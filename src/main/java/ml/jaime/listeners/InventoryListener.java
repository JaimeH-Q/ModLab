package ml.jaime.listeners;

import ml.jaime.ModLab;
import ml.jaime.files.MessagesFile;
import ml.jaime.model.DeserializedItem;
import ml.jaime.model.InventoryPlayer;
import ml.jaime.model.InventorySection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

import static ml.jaime.utils.MessageUtils.*;

public class InventoryListener implements Listener {
    private final ModLab plugin;
    public InventoryListener(ModLab plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTeleportMenuClick(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();
        InventoryPlayer inventoryPlayer = plugin.getMenuManager().getInventoryPlayer(player);
        if(inventoryPlayer == null || !inventoryPlayer.getInventorySection().equals(InventorySection.TELEPORT_MENU)){
            return;
        }
        event.setCancelled(true);
        MessagesFile messages = plugin.getMessagesFile();

        ItemStack head = event.getCurrentItem();
        if(head == null){return;}
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        if(skullMeta == null){return;}
        Player targetPlayer = Bukkit.getPlayer(skullMeta.getOwningPlayer().getName());
        if(targetPlayer == null){
            player.sendMessage(
                    getColoredMessage(messages.getPrefix() + messages.getTeleportMenuOfflinePlayerMessage())
                            .replace("%player%", skullMeta.getOwningPlayer().getName()));
            return;
        }
        player.sendMessage(getColoredMessage(replacePlayerPlaceholders
                (messages.getPrefix() + messages.getTeleportMenuSuccess(), targetPlayer)));
        player.teleport(targetPlayer);



    }

    @EventHandler
    public void preventStaffItemDrag(InventoryDragEvent event){
        event.getWhoClicked().sendMessage("me disparé");
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (!plugin.getStaffManager().isOnDuty(player)) {
            return;
        }
        if (plugin.getConfigFile().isStaffItemsMove()) {
            return;
        }
        player.sendMessage("hola 1");
        ItemStack draggedItem = event.getOldCursor();
        player.sendMessage("agarré " + draggedItem.getItemMeta().displayName());
        List<DeserializedItem> allStaffItems = plugin.getItemsFile().getAllStaffItems();
        for (DeserializedItem item : allStaffItems) {
            if (item.getItemStack().displayName().equals(draggedItem.displayName())) {
                player.sendMessage("dragged " + draggedItem.displayName());
                event.setCancelled(true);
                player.updateInventory();
                return;
            }
        }
    }

    @EventHandler
    public void preventStaffItemMove(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (!plugin.getStaffManager().isOnDuty(player)) {
            return;
        }
        if (plugin.getConfigFile().isStaffItemsMove()) {
            return;
        }
        ItemStack clickedItem = event.getCurrentItem();
        if(clickedItem.getType().equals(Material.AIR)){
            clickedItem = event.getCursor();
        }
        if(event.getClick() == ClickType.NUMBER_KEY){
            clickedItem = player.getInventory().getItem(event.getHotbarButton());
        }
        if (clickedItem == null) {
            return;
        }
        List<DeserializedItem> allStaffItems = plugin.getItemsFile().getAllStaffItems();
        for (DeserializedItem item : allStaffItems) {
            if (item.getItemStack().displayName().equals(clickedItem.displayName())) {
                event.setCancelled(true);
                player.updateInventory();
                return;
            }
        }
    }


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        plugin.getMenuManager().removeInventoryPlayer((Player) event.getPlayer());
    }
}
