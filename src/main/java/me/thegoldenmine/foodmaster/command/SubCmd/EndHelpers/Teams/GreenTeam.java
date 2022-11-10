package me.thegoldenmine.foodmaster.command.SubCmd.EndHelpers.Teams;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class GreenTeam {
    private final FoodMaster plugin;

    public GreenTeam(FoodMaster main) {
        plugin = main;
    }

    public void Players(Player player) {
        ChatColor gold = ChatColor.GOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        if (plugin.deathmatch.getGroupPlayersInGreenTeam(player) != null && !plugin.deathmatch.getGroupPlayersInGreenTeam(player).isEmpty()) {
            for (UUID uuid1 : plugin.deathmatch.getGroupPlayersInGreenTeam(player)) {
                if (uuid1 != null) {
                    Player player12 = Bukkit.getPlayer(uuid1);
                    if (player12 != null) {
                        int deaths1 = plugin.inGameDeaths.get(uuid1);
                        int kills1 = plugin.inGameKills.get(uuid1);
                        float KD11 = (float) kills1 / deaths1;
                        String[] list111 = String.valueOf(KD11).split("");
                        String first11 = list111[0];
                        String middle11 = list111[1];
                        String last11 = list111[2];
                        String finalString11 = first11 + middle11 + last11;
                        player.sendMessage(gold + " " + player12.getName());
                        player.sendMessage(red + "" + italic + "  Kills: " + kills1);
                        player.sendMessage(red + "" + italic + "  Deaths: " + deaths1);
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "    K/D: " + finalString11);
                        int win1 = plugin.mainConfig.getIntWin(String.valueOf(uuid1));
                        int lose1 = plugin.mainConfig.getIntLose(String.valueOf(uuid1));
                        float WL = (float) win1 / lose1;
                        String[] listw = String.valueOf(WL).split("");
                        String one = listw[0];
                        String two = listw[1];
                        String three = listw[2];
                        String wl = one + two + three;
                        player.sendMessage(green + "" + italic + "  Wins: " + win1);
                        player.sendMessage(red + "" + italic + "  Losses: " + lose1);
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "   W/L: " + wl);
                        player.sendMessage("");
                    }
                }
            }
        }
    }

    public void Wins(Player player) {
        // give them the win
        ChatColor gold = ChatColor.GOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        Set<UUID> groupPlayersInGreenTeam = plugin.deathmatch.getGroupPlayersInGreenTeam(player);
        if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
            for (UUID uuid1 : groupPlayersInGreenTeam) {
                if (uuid1 != null) {
                    Player player2 = Bukkit.getPlayer(uuid1);
                    if (player2 != null) {
                        plugin.winners.add(uuid1);
                    }
                }
            }
            for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                if (uuid != null) {
                    Player player1 = Bukkit.getPlayer(uuid);
                    if (player1 != null && !groupPlayersInGreenTeam.contains(uuid)) {
                        plugin.losses.add(uuid);
                    }
                }
            }
            // show the winners
            Players(player);
            // the stats for your team
            UUID uniqueId = player.getUniqueId();
            Set<UUID> groupPlayersInRedTeam = plugin.deathmatch.getGroupPlayersInRedTeam(player);
            Set<UUID> groupPlayersInBlueTeam = plugin.deathmatch.getGroupPlayersInBlueTeam(player);
            Set<UUID> groupPlayersInCyanTeam = plugin.deathmatch.getGroupPlayersInCyanTeam(player);
            Set<UUID> groupPlayersInYellowTeam = plugin.deathmatch.getGroupPlayersInYellowTeam(player);
            if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + red + "" + italic + "Red");
                player.sendMessage("");
                plugin.redTeam.Players(player);
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    plugin.cyanTeam.Players(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    plugin.yellowTeam.Players(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    plugin.blueTeam.Players(player);
                }
            } else if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.BLUE + "" + italic + "Blue");
                player.sendMessage("");
                plugin.blueTeam.Players(player);
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    plugin.cyanTeam.Players(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    Players(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    plugin.redTeam.Players(player);
                }
            } else if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + aqua + "" + italic + "Cyan");
                player.sendMessage("");
                plugin.cyanTeam.Players(player);
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    plugin.redTeam.Players(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    plugin.yellowTeam.Players(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    plugin.blueTeam.Players(player);
                }
            } else if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty() && groupPlayersInYellowTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + yellow + "" + italic + "Yellow");
                player.sendMessage("");
                plugin.yellowTeam.Players(player);
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    plugin.cyanTeam.Players(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    plugin.redTeam.Players(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    plugin.blueTeam.Players(player);
                }
            } else {
                player.sendMessage(playerStats);
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    plugin.redTeam.Players(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    plugin.cyanTeam.Players(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    plugin.blueTeam.Players(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    plugin.yellowTeam.Players(player);
                }
            }
        }
    }
}
