package ml.jaime.managers;

import ml.jaime.ModLab;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FreezeManager {
    private final List<Player> frozenPlayers;

    public FreezeManager(ModLab plugin) {
        this.frozenPlayers = new ArrayList<>();;
    }

    public void FreezePlayer(Player player){
        frozenPlayers.add(player);
    }

    public void UnfreezePlayer(Player player){
        frozenPlayers.removeIf(freezedPlayer -> freezedPlayer.equals(player));
    }

    public boolean isPlayerFrozen(Player player){
        return frozenPlayers.contains(player);
    }


}
