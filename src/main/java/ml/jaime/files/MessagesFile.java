package ml.jaime.files;

import ml.jaime.ModLab;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MessagesFile {
    private final YamlFile messagesFile;
    public MessagesFile (ModLab plugin){
        messagesFile = new YamlFile("messages.yml", null, plugin);
        loadMessages();
    }

    private List<String> helpMessage;
    private String noPermissionMessage;
    private String playerNotFoundMessage;
    private String prefix;
    private String reloadedMesage;
    private String playerOnlyMessage;
    private String staffModeOn;
    private String staffModeOff;
    private String teleportMenuOfflinePlayerMessage;
    private String teleportMenuSuccess;
    private String playerFreezeSuccess;
    private String playerUnfreezeSuccess;
    private String playerNotFrozen;
    private String playerFreezeError;
    private String playerAlreadyFrozen;
    private String gotFrozenMessage;
    private String gotUnfrozenMessage;
    private String badUsageFreeze;


    public void loadMessages() {
        messagesFile.registerConfig();
        FileConfiguration messages = messagesFile.getConfig();

        noPermissionMessage = messages.getString("no_permission");
        helpMessage = messages.getStringList("help_message");
        prefix = messages.getString("prefix");
        reloadedMesage = messages.getString("reloaded");
        playerOnlyMessage = messages.getString("player_only");
        staffModeOn = messages.getString("staff_mode_on");
        staffModeOff = messages.getString("staff_mode_off");
        teleportMenuOfflinePlayerMessage = messages.getString("teleport_menu_offline_player");
        teleportMenuSuccess = messages.getString("teleport_menu_success");
        playerFreezeError = messages.getString("player_freeze_error");
        playerFreezeSuccess = messages.getString("player_freeze_success");
        playerUnfreezeSuccess = messages.getString("player_unfreeze_success");
        gotFrozenMessage = messages.getString("got_frozen");
        playerNotFrozen = messages.getString("player_not_frozen");
        playerAlreadyFrozen = messages.getString("player_already_frozen");
        gotUnfrozenMessage = messages.getString("got_unfrozen");
        badUsageFreeze = messages.getString("bad_usage_freeze");
        playerNotFoundMessage = messages.getString("player_not_found");
    }

    public String getStaffModeOff() {
        return staffModeOff;
    }

    public String getTeleportMenuSuccess() {
        return teleportMenuSuccess;
    }

    public String getStaffModeOn() {
        return staffModeOn;
    }

    public String getPlayerNotFoundMessage() {
        return playerNotFoundMessage;
    }

    public List<String> getHelpMessage() {
        return helpMessage;
    }

    public String getNoPermissionMessage() {
        return noPermissionMessage;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getReloadedMesage() {
        return reloadedMesage;
    }

    public String getPlayerOnlyMessage() {
        return playerOnlyMessage;
    }

    public String getTeleportMenuOfflinePlayerMessage() {
        return teleportMenuOfflinePlayerMessage;
    }

    public String getPlayerFreezeSuccess() {
        return playerFreezeSuccess;
    }

    public String getPlayerUnfreezeSuccess() {
        return playerUnfreezeSuccess;
    }

    public String getPlayerFreezeError() {
        return playerFreezeError;
    }

    public String getGotFrozenMessage() {
        return gotFrozenMessage;
    }

    public String getPlayerNotFrozen() {
        return playerNotFrozen;
    }

    public String getPlayerAlreadyFrozen() {
        return playerAlreadyFrozen;
    }

    public String getGotUnfrozenMessage() {
        return gotUnfrozenMessage;
    }

    public String getBadUsageFreeze() {
        return badUsageFreeze;
    }
}
