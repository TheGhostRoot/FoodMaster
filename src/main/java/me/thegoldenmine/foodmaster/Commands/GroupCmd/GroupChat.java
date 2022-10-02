package me.thegoldenmine.foodmaster.Commands.GroupCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GroupChat {
    private final FoodMaster plugin;

    public GroupChat(FoodMaster plugin) {
        this.plugin = plugin;
    }

    public void groupChat(Player player, String[] args) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        StringBuilder message = new StringBuilder();
        // player is the one that sends the message
        // /fw group chat [message]
        //      0     1       2      index
        //      1     2       3      num
        if (args.length >= 3) {
            for (int i = 2; i < args.length; i++) {
                message.append(" ").append(args[i]);
            }
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                if (message.length() == 0) {
                    player.sendMessage(ERROR + "You should write a message.");
                } else {
                    for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                        Player players = Bukkit.getPlayer(uuid);
                        if (players != null) {
                            players.sendMessage(NORMAL + "" + aqua + "" + bold + "Group Chat " + darkGray + "" + strikethrough + "-" + gold + "" + italic + "" + player.getName() + "" + red + "" + bold + " -> " + green + "" + italic + "" + message);
                        }
                    }
                }
            } else {
                player.sendMessage(ERROR + "You need to be in a group to send messages.");
            }
        } else {
            player.sendMessage(INFO + "" + ChatColor.DARK_PURPLE + "" + italic + "/fm group chat " + gold + "" + italic + "[message] " + red + "" + bold + ">-" + darkGray + "" + italic + " Chat with your group.");
        }
    }
}
