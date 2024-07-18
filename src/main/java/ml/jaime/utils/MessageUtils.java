package ml.jaime.utils;

import ml.jaime.ModLab;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {
    public static String getColoredMessage(String text){
        text = text.replaceAll("&#", "#");

        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(text);

        if(!text.isEmpty()){
            while (matcher.find()) {
                String color = text.substring(matcher.start(), matcher.end());
                try {
                    text = text.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
                } catch (NoSuchMethodError e){
                    text = text.replace(color, "");
                }

                matcher = pattern.matcher(text);

            }

        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String replacePlayerPlaceholders(String text, Player player){
        Chat chat = ModLab.getChat();
        String newText = text.replace("%player%", player.getName())
                .replace("%x%", String.format("%.2f", player.getLocation().getX()))
                .replace("%y%", String.format("%.2f", player.getLocation().getY()))
                .replace("%z%", String.format("%.2f", player.getLocation().getZ()));
        if(chat != null){
            String prefix = getColoredMessage(chat.getPlayerPrefix(player));
            String suffix = getColoredMessage(chat.getPlayerSuffix(player));
            String displayName = prefix + player.getName() + suffix;
            newText = newText.replace("%player_display_name%", displayName)
                    .replace("%player_prefix%", prefix)
                    .replace("%player_suffix%", suffix);
        }
        return newText;

    }

    public static String getReplacedAndColored(String text, Player player){
        return getColoredMessage(replacePlayerPlaceholders(text, player));
    }
}
