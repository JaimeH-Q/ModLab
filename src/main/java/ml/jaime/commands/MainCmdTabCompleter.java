package ml.jaime.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class MainCmdTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        if(!command.getName().equalsIgnoreCase("modlab")){
            return null;
        }

        if(!(sender instanceof Player)){
            return null;
        }
        Player player = (Player) sender;
        if(args.length == 1){
            List<String> firstArgs = new ArrayList<>();
            if(player.hasPermission("modlab.reload")){firstArgs.add("reload");}
            if(player.hasPermission("modlab.help")){firstArgs.add("help");}


            return firstArgs;
        }


        return null;
    }

}
