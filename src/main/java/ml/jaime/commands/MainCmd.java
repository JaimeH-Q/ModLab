package ml.jaime.commands;

import ml.jaime.ModLab;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {
    private final ModLab plugin;
    public MainCommand(ModLab plugin){
        this.plugin = plugin;
    }

    public boolean  onCommand(CommandSender sender, Command command, String alias, String[] args){



        return true;
    }
}
