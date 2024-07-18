package ml.jaime.managers;

import ml.jaime.ModLab;
import ml.jaime.model.DeserializedItem;
import ml.jaime.files.ItemsFile;
import org.bukkit.GameMode;
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

    private final Map<Player, GameMode> activeStaffs = new HashMap<>();
    private final Map<String, ItemStack[]> savedInventories = new HashMap<>();

    public Boolean alternateStaff(Player player){
        GameMode staffGamemode = plugin.getConfigFile().getStaffGamemode();
        if(activeStaffs.containsKey(player)){
            GameMode previousGamemode = activeStaffs.get(player);
            player.setGameMode(previousGamemode);
            if(previousGamemode.equals(GameMode.SURVIVAL)){
                player.setAllowFlight(false);
            }
            activeStaffs.remove(player);
            restoreSavedInventory(player);
            return false;
        }

        activeStaffs.put(player, player.getGameMode());
        if(!player.getGameMode().equals(GameMode.CREATIVE)){
            player.setGameMode(staffGamemode);
        }
        if(staffGamemode.equals(GameMode.SURVIVAL)){player.setAllowFlight(true);}
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
        return activeStaffs.containsKey(player);
    }




}
