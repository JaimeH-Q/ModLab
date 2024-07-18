package ml.jaime.listeners;

import ml.jaime.ModLab;
import ml.jaime.files.MessagesFile;
import ml.jaime.model.InventoryPlayer;
import ml.jaime.model.InventorySection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
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
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
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
    public void onInventoryClose(InventoryCloseEvent event){
        plugin.getMenuManager().removeInventoryPlayer((Player) event.getPlayer());
    }
}
