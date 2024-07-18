package ml.jaime.managers;

import com.destroystokyo.paper.profile.PlayerProfile;
import me.flame.menus.items.MenuItem;
import me.flame.menus.menu.Menu;
import me.flame.menus.menu.Menus;
import ml.jaime.ModLab;
import ml.jaime.model.DeserializedMenu;
import ml.jaime.model.InventoryPlayer;
import ml.jaime.model.InventorySection;
import net.skinsrestorer.api.SkinsRestorer;
import net.skinsrestorer.api.exception.DataRequestException;
import net.skinsrestorer.api.property.MojangSkinDataResult;
import net.skinsrestorer.api.property.SkinIdentifier;
import net.skinsrestorer.api.property.SkinProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import static ml.jaime.utils.MessageUtils.replacePlayerPlaceholders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class MenuManager {
    private final Menus menus;
    private final ModLab plugin;
    private List<InventoryPlayer> players;

    public MenuManager(ModLab plugin){
        this.plugin = plugin;
        this.menus = new Menus(plugin);
        players = new ArrayList<>();
    }

    public InventoryPlayer getInventoryPlayer(Player player){
        for(InventoryPlayer inventoryPlayer : players){
            if(inventoryPlayer.getPlayer().equals(player)){
                return inventoryPlayer;
            }
        }
        return null;
    }

    public void removeInventoryPlayer(Player player){
        players.removeIf(inventoryPlayer -> inventoryPlayer.getPlayer().equals(player));
    }


    public void openTeleportMenu(InventoryPlayer inventoryPlayer) {
        inventoryPlayer.setInventorySection(InventorySection.TELEPORT_MENU);
        Player player = inventoryPlayer.getPlayer();
        DeserializedMenu deserializedMenu = plugin.getMenusFile().getTeleportMenu();

        List<Player> onlinePlayers = new ArrayList<>(plugin.getServer().getOnlinePlayers());
        ItemStack playerItemBase = plugin.getItemsFile().getTeleportMenuPlayerItem().getItemStack();
        playerItemBase.setType(Material.PLAYER_HEAD);
        int rows = onlinePlayers.size() / 9 + (onlinePlayers.size() % 9 > 0 ? 1 : 0);
        Menu teleportMenu = Menu.builder(menus, rows).title(deserializedMenu.getTitle()).normal();

        // Construct player heads
        for(Player p : onlinePlayers){
            ItemStack playerItem = new ItemStack(playerItemBase);
            SkullMeta skullMeta = (SkullMeta) playerItem.getItemMeta();
            skullMeta.setDisplayName(replacePlayerPlaceholders(playerItem.getItemMeta().getDisplayName(), p));
            List<String> newLore = new ArrayList<>();
            for(String m : skullMeta.getLore()){
                newLore.add(replacePlayerPlaceholders(m, p));
            }
            skullMeta.setLore(newLore);
            skullMeta.setOwningPlayer(p);

            playerItem.setItemMeta(skullMeta);
            teleportMenu.addItem(MenuItem.of(playerItem));
        }

        teleportMenu.open(player);
        players.add(inventoryPlayer);

    }
}
