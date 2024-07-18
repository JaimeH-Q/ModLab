package ml.jaime.commands;

import me.flame.menus.menu.Menu;
import me.flame.menus.menu.Menus;
import ml.jaime.ModLab;
import ml.jaime.files.MessagesFile;
import static ml.jaime.utils.MessageUtils.getColoredMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCmd implements CommandExecutor {
    private final ModLab plugin;
    public MainCmd(ModLab plugin){
        this.plugin = plugin;
    }

    public boolean  onCommand(CommandSender sender, Command command, String alias, String[] args){
        MessagesFile messages = plugin.getMessagesFile();
        String prefix = messages.getPrefix();

        if(args.length == 0 || args[0].equalsIgnoreCase("help")){
            sendHelpMessage(sender, messages);
            return true;
        }

        if(args[0].equalsIgnoreCase("reload")){
            if(!sender.hasPermission("modlab.reload")){
                sender.sendMessage(getColoredMessage(prefix + messages.getNoPermissionMessage()));
                return true;
            }
            plugin.reloadAllFiles();
            sender.sendMessage(getColoredMessage(prefix + messages.getReloadedMesage()));
            return true;
        }

        return true;
    }

    private void sendHelpMessage(CommandSender sender, MessagesFile messages){
        if(!sender.hasPermission("modlab.help")){
            sender.sendMessage(getColoredMessage(messages.getNoPermissionMessage()));
            return;
        }

        for(String m : messages.getHelpMessage()){
            sender.sendMessage(getColoredMessage(m));
        }

    }
}
