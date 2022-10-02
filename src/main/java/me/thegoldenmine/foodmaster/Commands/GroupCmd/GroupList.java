package me.thegoldenmine.foodmaster.Commands.GroupCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class GroupList {
    private final FoodMaster plugin;

    public GroupList(FoodMaster plugin) {
        this.plugin = plugin;
    }

    public void getGroupPlayers(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        String Name;
        if (plugin.mainConfig.getStrMain("name") != null) {
            Name = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            Name = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + Name + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + Name + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + Name + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            final Set<UUID> playerGroup = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
            if (playerGroup.size() <= 1) {
                plugin.allGroups.remove(playerGroup);
                playerGroup.remove(player.getUniqueId());
                plugin.allGroups.removeIf(Set::isEmpty);
                player.sendMessage(INFO + "You have been removed from the group, because there is no one else in the group.");
            } else {
                String names = plugin.playerGroup.getPlayerNamesFromGroupString(player).replace("\"\"", "").replace("[", "").replace("]", "");
                player.sendMessage(NORMAL + "The players in this group are " + gold + "" + italic + "" + names);
            }
        } else {
            player.sendMessage(WARN + "You are not in a group, but you can join one.");
        }
    }
}
