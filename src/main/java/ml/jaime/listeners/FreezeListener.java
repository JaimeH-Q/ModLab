package ml.jaime.listeners;

import ml.jaime.ModLab;
import ml.jaime.files.MessagesFile;
import ml.jaime.managers.FreezeManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import static ml.jaime.utils.MessageUtils.*;

public class FreezeListener implements Listener {
    private final ModLab plugin;

    public FreezeListener(ModLab plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFreezeMove(PlayerMoveEvent event){
        FreezeManager freezeManager = plugin.getFreezeManager();
        Player player = event.getPlayer();
        if(!freezeManager.isPlayerFrozen(player)){ return; }
        event.setCancelled(true);
    }

    @EventHandler
    public void onFreezeLeave(PlayerQuitEvent event){
        FreezeManager freezeManager = plugin.getFreezeManager();
        Player player = event.getPlayer();
        if(!freezeManager.isPlayerFrozen(player)){ return; }
        String freezeLeaveCommand = plugin.getConfigFile().getFreezeLeaveCommand();

        plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), getReplacedAndColored(freezeLeaveCommand, player));

    }
}
