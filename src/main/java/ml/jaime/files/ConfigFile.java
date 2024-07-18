package ml.jaime.files;

import ml.jaime.ModLab;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigFile {
    private final YamlFile configFile;
    public ConfigFile(ModLab plugin) {
        configFile = new YamlFile("config.yml", null, plugin);
        loadConfig();
    }

    private String freezeLeaveCommand;
    private boolean staffBlockPlace;
    private boolean staffItemsMove;
    private GameMode staffGamemode;

    public void loadConfig(){
        configFile.registerConfig();
        FileConfiguration config = configFile.getConfig();

        freezeLeaveCommand = config.getString("freeze_leave_command");
        staffItemsMove = config.getBoolean("staff_items_move");
        staffBlockPlace = config.getBoolean("staff_block_place");
        staffGamemode = GameMode.valueOf(config.getString("staff_gamemode"));
    }

    public String getFreezeLeaveCommand() {
        return freezeLeaveCommand;
    }

    public boolean isStaffBlockPlace() {
        return staffBlockPlace;
    }

    public boolean isStaffItemsMove() {
        return staffItemsMove;
    }

    public GameMode getStaffGamemode() {
        return staffGamemode;
    }
}
