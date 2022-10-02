package me.thegoldenmine.foodmaster.Commands.SubCmd.EndHelpers.Teams;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class YellowTeam {
    private final FoodMaster plugin;

    public YellowTeam(FoodMaster main) {
        plugin = main;
    }

    public void Players(Player player) {
        ChatColor gold = ChatColor.GOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        if (plugin.deathmatch.getGroupPlayersInYellowTeam(player) != null && !plugin.deathmatch.getGroupPlayersInYellowTeam(player).isEmpty()) {
            for (UUID uuid1 : plugin.deathmatch.getGroupPlayersInYellowTeam(player)) {
                if (uuid1 != null) {
                    Player player2 = Bukkit.getPlayer(uuid1);
                    if (player2 != null) {
                        int kills = plugin.inGameKills.get(uuid1);
                        int deaths = plugin.inGameDeaths.get(uuid1);
                        float KD1 = (float) kills / deaths;
                        String[] list1 = String.valueOf(KD1).split("");
                        String first1 = list1[0];
                        String middle1 = list1[1];
                        String last1 = list1[2];
                        String finalString1 = first1 + middle1 + last1;
                        player.sendMessage(gold + " " + player2.getName());
                        player.sendMessage(red + "" + italic + "  Kills: " + kills);
                        player.sendMessage(red + "" + italic + "  Deaths: " + deaths);
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "    K/D: " + finalString1);
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
        ChatColor gold = ChatColor.GOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        // give them the win
        Set<UUID> groupPlayersInYellowTeam = plugin.deathmatch.getGroupPlayersInYellowTeam(player);
        if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
            for (UUID uuid1 : groupPlayersInYellowTeam) {
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
                    if (player1 != null && !groupPlayersInYellowTeam.contains(uuid)) {
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
            Set<UUID> groupPlayersInGreenTeam = plugin.deathmatch.getGroupPlayersInGreenTeam(player);
            if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + red + "" + italic + "Red");
                player.sendMessage("");
                plugin.redTeam.Players(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    plugin.greenTeam.Players(player);
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
            } else if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.BLUE + "" + italic + "Blue");
                player.sendMessage("");
                plugin.blueTeam.Players(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    plugin.greenTeam.Players(player);
                }
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
            } else if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty() && groupPlayersInGreenTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                player.sendMessage("");
                plugin.greenTeam.Players(player);
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    plugin.blueTeam.Players(player);
                }
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
            } else if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + aqua + "" + italic + "Cyan");
                player.sendMessage("");
                plugin.cyanTeam.Players(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    plugin.greenTeam.Players(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    plugin.blueTeam.Players(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    plugin.redTeam.Players(player);
                }
            } else {
                player.sendMessage(playerStats);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    plugin.greenTeam.Players(player);
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
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    plugin.redTeam.Players(player);
                }
            }
        }
    }
}
