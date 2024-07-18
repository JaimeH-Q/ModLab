package ml.jaime.managers;

import ml.jaime.ModLab;
import ml.jaime.model.DeserializedItem;
import ml.jaime.files.ItemsFile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffManager {
    private final ModLab plugin;
    public StaffManager(ModLab plugin){
        this.plugin = plugin;
    }

    private final List<String> activeStaffs = new ArrayList<>();
    private final Map<String, ItemStack[]> savedInventories = new HashMap<String, ItemStack[]>();

    public Boolean alternateStaff(Player player){
        String playerName = player.getName();
        if(activeStaffs.contains(playerName)){
            activeStaffs.remove(playerName);
            restoreSavedInventory(player);
            return false;
        }
        activeStaffs.add(playerName);
        giveStaffItems(player);
        return true;
    }

    public void giveStaffItems(Player player){
        ItemsFile itemsFile = plugin.getItemsFile();

        PlayerInventory inventory = player.getInventory();
        savedInventories.put(player.getName(), inventory.getContents());
        inventory.clear();

        DeserializedItem teleportMenuItem = itemsFile.getTeleportMenu();
        DeserializedItem freezeItem = itemsFile.getFreezeItem();

        inventory.setItem(teleportMenuItem.getSlot(), teleportMenuItem.getItemStack());
        inventory.setItem(freezeItem.getSlot(), freezeItem.getItemStack());


    }

    public void restoreSavedInventory(Player player){
        String playerName = player.getName();

        if(!savedInventories.containsKey(playerName)){
            player.sendMessage("Items lost");
            player.getInventory().clear();
            return;
        }
        player.getInventory().setContents(savedInventories.get(playerName));
    }


    public boolean isOnDuty(Player player){
        return activeStaffs.contains(player.getName());
    }



}
