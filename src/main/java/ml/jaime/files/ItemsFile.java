package ml.jaime.files;

import ml.jaime.ModLab;
import ml.jaime.model.DeserializedItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static ml.jaime.utils.MessageUtils.getColoredMessage;

public class ItemsFile{
    private final YamlFile itemsFile;
    public ItemsFile(ModLab plugin) {
        itemsFile = new YamlFile("config.yml", null, plugin);
        loadItems();
    }

    private DeserializedItem teleportMenu;
    private DeserializedItem teleportMenuPlayerItem;
    private DeserializedItem previousPageItem;
    private DeserializedItem nextPageItem;
    private DeserializedItem freezeItem;


    public void loadItems(){
        itemsFile.registerConfig();
        ConfigurationSection items = itemsFile.getConfig().getConfigurationSection("items");

        teleportMenu = getDeserializedItem(items.getConfigurationSection("teleport_menu"));
        teleportMenuPlayerItem = getDeserializedItem(items.getConfigurationSection("teleport_menu_player_item"));
        previousPageItem = getDeserializedItem(items.getConfigurationSection("previous_page"));
        nextPageItem = getDeserializedItem(items.getConfigurationSection("next_page"));
        freezeItem = getDeserializedItem(items.getConfigurationSection("freeze_item"));

    }

    public DeserializedItem getFreezeItem() {
        return freezeItem;
    }

    private DeserializedItem getDeserializedItem(ConfigurationSection itemSection){
        if(itemSection == null){
            return null;
        }

        Material material = Material.getMaterial(itemSection.getString("material"));

        String displayName = getColoredMessage(itemSection.getString("display_name"));
        if(displayName.isEmpty()){
            displayName = getColoredMessage("&cDisplay name error");
        }

        List<String> lore = new ArrayList<>();
        for(String m: itemSection.getStringList("lore")){
            lore.add(getColoredMessage(m));
        }

        int modelData = itemSection.getInt("custom_model_data");
        int slot = itemSection.getInt("slot");
        int amount = itemSection.getInt("amount");
        if(amount == 0){ amount = 1;}

        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(modelData);

        itemStack.setItemMeta(itemMeta);
        return new DeserializedItem(itemStack, slot);
    }

    public DeserializedItem getTeleportMenu() {
        return teleportMenu;
    }

    public DeserializedItem getTeleportMenuPlayerItem() {
        return teleportMenuPlayerItem;
    }

    public DeserializedItem getNextPageItem() {
        return nextPageItem;
    }

    public DeserializedItem getPreviousPageItem() {
        return previousPageItem;
    }

    public List<DeserializedItem> getAllStaffItems(){
        List<DeserializedItem> items = new ArrayList<>();
        items.add(teleportMenu);
        items.add(freezeItem);
        return items;
    }
}
