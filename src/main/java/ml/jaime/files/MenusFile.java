package ml.jaime.files;

import ml.jaime.ModLab;
import ml.jaime.model.DeserializedMenu;
import ml.jaime.utils.MessageUtils;
import org.bukkit.configuration.ConfigurationSection;

public class MenusFile {
    private final YamlFile menusFile;
    public MenusFile(ModLab plugin) {
        menusFile = new YamlFile("config.yml", null, plugin);
        loadMenus();
    }

    private DeserializedMenu teleportMenu;

    public void loadMenus(){
        menusFile.registerConfig();
        ConfigurationSection menus = menusFile.getConfig().getConfigurationSection("menus");

        teleportMenu = deserializeMenu(menus.getConfigurationSection("teleport_menu"));
    }

    private DeserializedMenu deserializeMenu(ConfigurationSection menuSection){
        String title = MessageUtils.getColoredMessage(menuSection.getString("title"));
        int rows = menuSection.getInt("rows");
        if(rows == 0){ rows = 1;}

        return new DeserializedMenu(title, rows);
    }

    public DeserializedMenu getTeleportMenu() {
        return teleportMenu;
    }
}
