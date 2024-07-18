package ml.jaime.commands;

import ml.jaime.ModLab;
import ml.jaime.files.MessagesFile;
import ml.jaime.managers.FreezeManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static ml.jaime.utils.MessageUtils.getColoredMessage;
import static ml.jaime.utils.MessageUtils.getReplacedAndColored;

public class FreezeCommand implements CommandExecutor {
    private final ModLab plugin;
    public FreezeCommand(ModLab plugin){
        this.plugin = plugin;
    }

    public boolean  onCommand(CommandSender sender, Command command, String alias, String[] args){
        MessagesFile messagesFile = plugin.getMessagesFile();
        String prefix = messagesFile.getPrefix();
        if(args.length == 0){
            sender.sendMessage(getColoredMessage(prefix + messagesFile.getBadUsageFreeze()));
            return true;
        }
        if(!sender.hasPermission("modlab.freeze")){
            sender.sendMessage(getColoredMessage(prefix + messagesFile.getNoPermissionMessage()));
            return true;
        }
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if(targetPlayer == null){
            sender.sendMessage(getColoredMessage(
                    prefix + messagesFile.getPlayerNotFoundMessage().replace("%player%", args[0])));
            return true;
        }
        FreezeManager freezeManager = plugin.getFreezeManager();

        if(alias.equals("freeze")){
            if(freezeManager.isPlayerFrozen(targetPlayer)){
                sender.sendMessage(getReplacedAndColored(prefix + messagesFile.getPlayerAlreadyFrozen(), targetPlayer));
                return true;
            }
            if(targetPlayer.hasPermission("modlab.freeze.immunity")){
                sender.sendMessage(getReplacedAndColored(prefix + messagesFile.getPlayerFreezeError(), targetPlayer));
                return true;
            }
            freezeManager.FreezePlayer(targetPlayer);
            sender.sendMessage(getReplacedAndColored(prefix + messagesFile.getPlayerFreezeSuccess(), targetPlayer));
            targetPlayer.sendMessage(getColoredMessage(messagesFile.getGotFrozenMessage()));
            return true;
        } else {
            if(!freezeManager.isPlayerFrozen(targetPlayer)){
                sender.sendMessage(getReplacedAndColored(prefix + messagesFile.getPlayerNotFrozen(), targetPlayer));
                return true;
            }
            freezeManager.UnfreezePlayer(targetPlayer);
            sender.sendMessage(getReplacedAndColored(prefix + messagesFile.getPlayerUnfreezeSuccess(), targetPlayer));
            targetPlayer.sendMessage(getColoredMessage(messagesFile.getGotUnfrozenMessage()));
        }


        return true;
    }
}
