package ml.jaime.commands;

import ml.jaime.ModLab;
import ml.jaime.files.MessagesFile;
import ml.jaime.managers.StaffManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static ml.jaime.utils.MessageUtils.getColoredMessage;

public class StaffCommand implements CommandExecutor {
    private final ModLab plugin;
    public StaffCommand(ModLab plugin){
        this.plugin = plugin;
    }

    public boolean  onCommand(CommandSender sender, Command command, String alias, String[] args){
        MessagesFile messages = plugin.getMessagesFile();
        String prefix = messages.getPrefix();

        if(!(sender instanceof Player)){
            sender.sendMessage(getColoredMessage(prefix + messages.getPlayerOnlyMessage()));
            return true;
        }
        if(!sender.hasPermission("modlab.staff")){
            sender.sendMessage(getColoredMessage(prefix + messages.getNoPermissionMessage()));
            return true;
        }

        StaffManager staffManager = plugin.getStaffManager();
        Player player = (Player) sender;
        if(staffManager.alternateStaff(player)){
            player.sendMessage(getColoredMessage(prefix + messages.getStaffModeOn()));
            return true;
        }

        player.sendMessage(getColoredMessage(prefix + messages.getStaffModeOff()));
        return true;
    }
}
