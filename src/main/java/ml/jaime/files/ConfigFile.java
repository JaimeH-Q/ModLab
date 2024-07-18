package ml.jaime.files;

import ml.jaime.ModLab;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigFile {
    private final YamlFile configFile;
    public ConfigFile(ModLab plugin) {
        configFile = new YamlFile("config.yml", null, plugin);
        loadConfig();
    }

    private String freezeLeaveCommand;

    public void loadConfig(){
        configFile.registerConfig();
        FileConfiguration config = configFile.getConfig();

        freezeLeaveCommand = config.getString("freeze_leave_command");
    }

    public String getFreezeLeaveCommand() {
        return freezeLeaveCommand;
    }
}
