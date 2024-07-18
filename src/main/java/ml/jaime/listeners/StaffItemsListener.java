package ml.jaime.listeners;

import me.flame.menus.menu.Menu;
import me.flame.menus.menu.Menus;
import ml.jaime.ModLab;
import ml.jaime.files.MessagesFile;
import ml.jaime.managers.FreezeManager;
import ml.jaime.managers.MenuManager;
import ml.jaime.model.DeserializedItem;
import ml.jaime.model.InventoryPlayer;
import net.skinsrestorer.api.exception.DataRequestException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static ml.jaime.utils.MessageUtils.*;

public class StaffItemsListener implements Listener {
    private final ModLab plugin;
    public StaffItemsListener(ModLab plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTeleportMenuInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(!plugin.getStaffManager().isOnDuty(player)){
            return;
        }
        ItemStack interactedItem = event.getItem();
        if(interactedItem == null){ return; }
        ItemStack teleportMenuItem = plugin.getItemsFile().getTeleportMenu().getItemStack();
        if(!interactedItem.getItemMeta().getDisplayName().equals(teleportMenuItem.getItemMeta().getDisplayName())){
            return;
        }
        if(!event.getAction().isLeftClick() && !event.getAction().isRightClick()){return;}
        event.setCancelled(true);

        plugin.getMenuManager().openTeleportMenu(new InventoryPlayer(player));
    }

    @EventHandler
    public void onStaffItemDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        if(!plugin.getStaffManager().isOnDuty(player)){
            return;
        }
        ItemStack droppedItem = event.getItemDrop().getItemStack();
        event.setCancelled(true);
    }

    @EventHandler
    public void freezeItemInteract(PlayerInteractEntityEvent event){
        Player clicker = event.getPlayer();
        if(!plugin.getStaffManager().isOnDuty(clicker)){return; }
        if(!event.getHand().equals(EquipmentSlot.HAND)){return;}
        ItemStack freezeItem = plugin.getItemsFile().getFreezeItem().getItemStack();
        ItemStack usedItem = clicker.getInventory().getItemInMainHand();
        if(usedItem.getItemMeta() == null){return;}
        if(!usedItem.getItemMeta().displayName().equals(freezeItem.getItemMeta().displayName())){
            return;
        }
        if(!(event.getRightClicked() instanceof Player targetPlayer)){ return; }
        MessagesFile messagesFile = plugin.getMessagesFile();
        String prefix = messagesFile.getPrefix();
        FreezeManager freezeManager = plugin.getFreezeManager();

        if(targetPlayer.hasPermission("modlab.freeze.immunity")){
            clicker.sendMessage(getReplacedAndColored(prefix + messagesFile.getPlayerFreezeError(), targetPlayer));
            return;
        }

        if(freezeManager.isPlayerFrozen(targetPlayer)){
            clicker.sendMessage(getReplacedAndColored(
                    prefix + messagesFile.getPlayerUnfreezeSuccess(), targetPlayer));
            freezeManager.UnfreezePlayer(targetPlayer);
            targetPlayer.sendMessage(getColoredMessage(messagesFile.getGotUnfrozenMessage()));
        } else {
            clicker.sendMessage(getReplacedAndColored(
                    prefix + messagesFile.getPlayerFreezeSuccess(), targetPlayer));
            freezeManager.FreezePlayer(targetPlayer);
            targetPlayer.sendMessage(getColoredMessage(messagesFile.getGotFrozenMessage()));
        }
    }

    @EventHandler
    public void preventStaffBlocksPlace(BlockPlaceEvent event){
        if(!plugin.getStaffManager().isOnDuty(event.getPlayer())){return;}
        // Staff items can never be placed
        List<DeserializedItem> allStaffItems = plugin.getItemsFile().getAllStaffItems();
        for(DeserializedItem item: allStaffItems){
            if(item.getItemStack().displayName().equals(event.getItemInHand().displayName())){
                event.setCancelled(true);
                return;
            }
        }
        boolean canStaffPlaceBlocks = plugin.getConfigFile().isStaffBlockPlace();
        event.setCancelled(!canStaffPlaceBlocks);
    }


}
