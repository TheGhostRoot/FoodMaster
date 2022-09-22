package me.thegoldenmine.foodmaster.Cmds.SubCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class EndTheGame {
    private final FoodMaster plugin;

    public EndTheGame(FoodMaster main) {
        plugin = main;
    }

    private void playersInGreenTeam(Player player) {
        ChatColor gold = ChatColor.GOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        if (plugin.getGroupPlayersInGreenTeam(player) != null && !plugin.getGroupPlayersInGreenTeam(player).isEmpty()) {
            for (UUID uuid1 : plugin.getGroupPlayersInGreenTeam(player)) {
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

    private void playersInYellowTeam(Player player) {
        ChatColor gold = ChatColor.GOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        if (plugin.getGroupPlayersInYellowTeam(player) != null && !plugin.getGroupPlayersInYellowTeam(player).isEmpty()) {
            for (UUID uuid1 : plugin.getGroupPlayersInYellowTeam(player)) {
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

    private void playersInCyanTeam(Player player) {
        ChatColor gold = ChatColor.GOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        if (plugin.getGroupPlayersInCyanTeam(player) != null && !plugin.getGroupPlayersInCyanTeam(player).isEmpty()) {
            for (UUID uuid1 : plugin.getGroupPlayersInCyanTeam(player)) {
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

    private void playersInRedTeam(Player player) {
        ChatColor gold = ChatColor.GOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        if (plugin.getGroupPlayersInRedTeam(player) != null && !plugin.getGroupPlayersInRedTeam(player).isEmpty()) {
            for (UUID uuid1 : plugin.getGroupPlayersInRedTeam(player)) {
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

    private void playersInBlueTeam(Player player) {
        ChatColor gold = ChatColor.GOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        if (plugin.getGroupPlayersInBlueTeam(player) != null && !plugin.getGroupPlayersInBlueTeam(player).isEmpty()) {
            for (UUID uuid1 : plugin.getGroupPlayersInBlueTeam(player)) {
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

    private void theRedTeamWins(Player player) {
        ChatColor gold = ChatColor.GOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        // give them the win
        Set<UUID> groupPlayersInRedTeam = plugin.getGroupPlayersInRedTeam(player);
        if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
            for (UUID uuid1 : groupPlayersInRedTeam) {
                if (uuid1 != null) {
                    Player player2 = Bukkit.getPlayer(uuid1);
                    if (player2 != null) {
                        plugin.winners.add(uuid1);
                    }
                }
            }
            for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player)) {
                if (uuid != null) {
                    Player player1 = Bukkit.getPlayer(uuid);
                    if (player1 != null && !groupPlayersInRedTeam.contains(uuid)) {
                        plugin.losses.add(uuid);
                    }
                }
            }
            // the stats for your team
            UUID uniqueId = player.getUniqueId();
            Set<UUID> groupPlayersInBlueTeam = plugin.getGroupPlayersInBlueTeam(player);
            Set<UUID> groupPlayersInCyanTeam = plugin.getGroupPlayersInCyanTeam(player);
            Set<UUID> groupPlayersInGreenTeam = plugin.getGroupPlayersInGreenTeam(player);
            Set<UUID> groupPlayersInYellowTeam = plugin.getGroupPlayersInYellowTeam(player);
            playersInRedTeam(player);
            String s = "Enemy Team: ";
            String s1 = "Your Team: ";
            String green1 = "Green";
            String yellow1 = "Yellow";
            String message = red + "" + italic + s + yellow + "" + italic + yellow1;
            String cyan = "Cyan";
            String blue = "Blue";
            String message1 = red + "" + italic + s + aqua + "" + italic + cyan;
            if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + s1 + ChatColor.BLUE + "" + italic + blue);
                player.sendMessage("");
                playersInBlueTeam(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.DARK_GREEN + "" + italic + green1);
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(message1);
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(message);
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
            } else if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty() && groupPlayersInGreenTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + s1 + green + "" + italic + green1);
                player.sendMessage("");
                playersInGreenTeam(player);
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.BLUE + "" + italic + blue);
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(message1);
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(message);
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
            } else if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + s1 + aqua + "" + italic + cyan);
                player.sendMessage("");
                playersInCyanTeam(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.DARK_GREEN + "" + italic + green1);
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.BLUE + "" + italic + blue);
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(message);
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
            } else if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty() && groupPlayersInYellowTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + s1 + yellow + "" + italic + yellow1);
                player.sendMessage("");
                playersInYellowTeam(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.DARK_GREEN + "" + italic + green1);
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(message1);
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.BLUE + "" + italic + blue);
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
            } else {
                player.sendMessage(playerStats);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.DARK_GREEN + "" + italic + green1);
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(message1);
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.BLUE + "" + italic + blue);
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(message);
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
            }
        }
    }

    private void theBlueTeamWins(Player player) {
        ChatColor gold = ChatColor.GOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        // give them the win
        Set<UUID> groupPlayersInBlueTeam = plugin.getGroupPlayersInBlueTeam(player);
        if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
            for (UUID uuid1 : groupPlayersInBlueTeam) {
                if (uuid1 != null) {
                    Player player2 = Bukkit.getPlayer(uuid1);
                    if (player2 != null) {
                        plugin.winners.add(uuid1);
                    }
                }
            }
            for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player)) {
                if (uuid != null) {
                    Player player1 = Bukkit.getPlayer(uuid);
                    if (player1 != null && !groupPlayersInBlueTeam.contains(uuid)) {
                        plugin.losses.add(uuid);
                    }
                }
            }
            // the stats for your team
            UUID uniqueId = player.getUniqueId();
            Set<UUID> groupPlayersInRedTeam = plugin.getGroupPlayersInRedTeam(player);
            Set<UUID> groupPlayersInCyanTeam = plugin.getGroupPlayersInCyanTeam(player);
            Set<UUID> groupPlayersInGreenTeam = plugin.getGroupPlayersInGreenTeam(player);
            Set<UUID> groupPlayersInYellowTeam = plugin.getGroupPlayersInYellowTeam(player);
            // show the winners
            playersInBlueTeam(player);
            String s = "Enemy Team: ";
            String s1 = "Your Team: ";
            if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + s1 + red + "" + italic + "Red");
                player.sendMessage("");
                playersInRedTeam(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
            } else if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty() && groupPlayersInGreenTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + s1 + ChatColor.DARK_GREEN + "" + italic + "Green");
                player.sendMessage("");
                playersInGreenTeam(player);
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
            } else if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + s1 + aqua + "" + italic + "Cyan");
                player.sendMessage("");
                playersInCyanTeam(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
            } else if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty() && groupPlayersInYellowTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + s1 + yellow + "" + italic + "Yellow");
                player.sendMessage("");
                playersInYellowTeam(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
            } else {
                player.sendMessage(playerStats);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + s + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
            }
        }
    }

    private void theYellowTeamWins(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        // give them the win
        Set<UUID> groupPlayersInYellowTeam = plugin.getGroupPlayersInYellowTeam(player);
        if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
            for (UUID uuid1 : groupPlayersInYellowTeam) {
                if (uuid1 != null) {
                    Player player2 = Bukkit.getPlayer(uuid1);
                    if (player2 != null) {
                        plugin.winners.add(uuid1);
                    }
                }
            }
            for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player)) {
                if (uuid != null) {
                    Player player1 = Bukkit.getPlayer(uuid);
                    if (player1 != null) {
                        if (!groupPlayersInYellowTeam.contains(uuid)) {
                            plugin.losses.add(uuid);
                        }
                    }
                }
            }
            // show the winners
            playersInYellowTeam(player);
            // the stats for your team
            UUID uniqueId = player.getUniqueId();
            Set<UUID> groupPlayersInRedTeam = plugin.getGroupPlayersInRedTeam(player);
            Set<UUID> groupPlayersInBlueTeam = plugin.getGroupPlayersInBlueTeam(player);
            Set<UUID> groupPlayersInCyanTeam = plugin.getGroupPlayersInCyanTeam(player);
            Set<UUID> groupPlayersInGreenTeam = plugin.getGroupPlayersInGreenTeam(player);
            if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + red + "" + italic + "Red");
                player.sendMessage("");
                playersInRedTeam(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
            } else if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.BLUE + "" + italic + "Blue");
                player.sendMessage("");
                playersInBlueTeam(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
            } else if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty() && groupPlayersInGreenTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                player.sendMessage("");
                playersInGreenTeam(player);
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
            } else if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + aqua + "" + italic + "Cyan");
                player.sendMessage("");
                playersInCyanTeam(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
            } else {
                player.sendMessage(playerStats);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
            }
        }
    }

    private void theCyanTeamWins(Player player) {
        // give them the win
        ChatColor gold = ChatColor.GOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        Set<UUID> groupPlayersInCyanTeam = plugin.getGroupPlayersInCyanTeam(player);
        if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
            for (UUID uuid1 : groupPlayersInCyanTeam) {
                if (uuid1 != null) {
                    Player player2 = Bukkit.getPlayer(uuid1);
                    if (player2 != null) {
                        plugin.winners.add(uuid1);
                    }
                }
            }
            for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player)) {
                if (uuid != null) {
                    Player player1 = Bukkit.getPlayer(uuid);
                    if (player1 != null) {
                        if (!groupPlayersInCyanTeam.contains(uuid)) {
                            plugin.losses.add(uuid);
                        }
                    }
                }
            }
            // show the winners
            playersInCyanTeam(player);
            // the stats for your team
            UUID uniqueId = player.getUniqueId();
            Set<UUID> groupPlayersInRedTeam = plugin.getGroupPlayersInRedTeam(player);
            Set<UUID> groupPlayersInBlueTeam = plugin.getGroupPlayersInBlueTeam(player);
            Set<UUID> groupPlayersInGreenTeam = plugin.getGroupPlayersInGreenTeam(player);
            Set<UUID> groupPlayersInYellowTeam = plugin.getGroupPlayersInYellowTeam(player);
            if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + red + "" + italic + "Red");
                player.sendMessage("");
                playersInRedTeam(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
            } else if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.BLUE + "" + italic + "Blue");
                player.sendMessage("");
                playersInBlueTeam(player);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
            } else if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty() && groupPlayersInGreenTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                player.sendMessage("");
                playersInGreenTeam(player);
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
            } else if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty() && groupPlayersInYellowTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + yellow + "" + italic + "Yellow");
                player.sendMessage("");
                playersInYellowTeam(player);
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
            } else {
                player.sendMessage(playerStats);
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
            }
        }
    }

    private void theGreenTeamWins(Player player) {
        // give them the win
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        Set<UUID> groupPlayersInGreenTeam = plugin.getGroupPlayersInGreenTeam(player);
        if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
            for (UUID uuid1 : groupPlayersInGreenTeam) {
                if (uuid1 != null) {
                    Player player2 = Bukkit.getPlayer(uuid1);
                    if (player2 != null) {
                        plugin.winners.add(uuid1);
                    }
                }
            }
            for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player)) {
                if (uuid != null) {
                    Player player1 = Bukkit.getPlayer(uuid);
                    if (player1 != null) {
                        if (!groupPlayersInGreenTeam.contains(uuid)) {
                            plugin.losses.add(uuid);
                        }
                    }
                }
            }
            // show the winners
            playersInGreenTeam(player);
            // the stats for your team
            UUID uniqueId = player.getUniqueId();
            Set<UUID> groupPlayersInRedTeam = plugin.getGroupPlayersInRedTeam(player);
            Set<UUID> groupPlayersInBlueTeam = plugin.getGroupPlayersInBlueTeam(player);
            Set<UUID> groupPlayersInCyanTeam = plugin.getGroupPlayersInCyanTeam(player);
            //Set<UUID> groupPlayersInGreenTeam = plugin.getGroupPlayersInGreenTeam(player);
            Set<UUID> groupPlayersInYellowTeam = plugin.getGroupPlayersInYellowTeam(player);
            if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + red + "" + italic + "Red");
                player.sendMessage("");
                playersInRedTeam(player);
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
            } else if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.BLUE + "" + italic + "Blue");
                player.sendMessage("");
                playersInBlueTeam(player);
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
            } else if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + aqua + "" + italic + "Cyan");
                player.sendMessage("");
                playersInCyanTeam(player);
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
            } else if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty() && groupPlayersInYellowTeam.contains(uniqueId)) {
                player.sendMessage(playerStats);
                player.sendMessage(green + "" + italic + "Your Team: " + yellow + "" + italic + "Yellow");
                player.sendMessage("");
                playersInYellowTeam(player);
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
            } else {
                player.sendMessage(playerStats);
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.DARK_RED + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                }
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                }
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                }
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    player.sendMessage(red + "" + italic + "Enemy Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                }
            }
        }
    }

    private void giveGameStats(UUID uuid1) {
        if (!plugin.inGameDeaths.containsKey(uuid1)) {
            plugin.inGameDeaths.put(uuid1, 0);
        }
        if (!plugin.inGameKills.containsKey(uuid1)) {
            plugin.inGameKills.put(uuid1, 0);
        }
    }

    private void reset(Player player, Location endLoc) {
        UUID uuid = player.getUniqueId();
        if (plugin.isPlayerInGame(player)) {
            plugin.removePlayerFromGame(player);
        }
        if (plugin.isPlayerInWaitingLobby(player)) {
            plugin.removePlayerFromWaitLobby.removePlayerFromWaitedLobby(player);
        }
        if (plugin.isPlayerInGroup(player)) {
            Set<UUID> playerGroupClone = new HashSet<>(plugin.getPlayersInGroupOfPlayer(player));
            if (!playerGroupClone.isEmpty()) {
                plugin.locOfPlayersInWaitingLobby.remove(playerGroupClone);
            }
        }
        // kits
        plugin.playersInPotatoKit.remove(uuid);
        plugin.playersInBreadKit.remove(uuid);
        plugin.playersInMelonKit.remove(uuid);
        plugin.playersInCookieKit.remove(uuid);
        plugin.playersInFishKit.remove(uuid);
        plugin.playersRandomKit.remove(uuid);
        // teams
        plugin.players2Teams.remove(uuid);
        plugin.players3Teams.remove(uuid);
        plugin.players4Teams.remove(uuid);
        plugin.players5Teams.remove(uuid);
        // others
        plugin.playersThatChoice5Teams.remove(uuid);
        plugin.playersThatChoice4Teams.remove(uuid);
        plugin.playersThatChoice2Teams.remove(uuid);
        plugin.kickedPlayers.remove(uuid);
        plugin.playersThatChoice3Teams.remove(uuid);
        plugin.ReadySystem.remove(uuid);
        plugin.ReadyPlayers.remove(uuid);
        plugin.playersInFreeForAll.remove(uuid);
        plugin.lives.remove(uuid);
        plugin.playersChoiceFreeForAll.remove(uuid);
        // pve
        plugin.playersPlayingPvESpider.remove(uuid);
        plugin.playersPlayingPvESkeleton.remove(uuid);
        plugin.playersPlayingPvEZombie.remove(uuid);
        plugin.playersPlayingPvESlime.remove(uuid);
        plugin.playersPlayingPvEEnderman.remove(uuid);
        plugin.playersChoicePvESpider.remove(uuid);
        plugin.playersChoicePvESkeleton.remove(uuid);
        plugin.playersChoicePvEZombie.remove(uuid);
        plugin.playersChoicePvEEnderman.remove(uuid);
        plugin.playersChoicePvESlime.remove(uuid);

        // Health
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        assert attributeInstance != null;
        attributeInstance.setBaseValue(20);
        player.setHealth(20);
        player.removePotionEffect(PotionEffectType.SPEED);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(endLoc);
    }

    private void theTeamWithTheMostKillsWins(Player player) {
        ChatColor gold = ChatColor.GOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        String gameStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Game stats" + aqua + "" + italic + " }==-->";
        if (plugin.isPlayerInGroup(player)) {
            // get the total kills of the teams
            // green1, yellow1, blue, cyan, red1
            List<Integer> greenKills = new ArrayList<>();
            int greenMaxKills = 0;
            Set<UUID> groupPlayersInGreenTeam = plugin.getGroupPlayersInGreenTeam(player);
            if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                for (UUID uuid : groupPlayersInGreenTeam) {
                    if (uuid != null) {
                        Player player1 = Bukkit.getPlayer(uuid);
                        if (player1 != null) {
                            greenKills.add(plugin.inGameKills.get(uuid));
                        }
                    }
                }
            }
            List<Integer> redKills = new ArrayList<>();
            int redMaxKills = 0;
            Set<UUID> groupPlayersInRedTeam = plugin.getGroupPlayersInRedTeam(player);
            if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                for (UUID uuid : groupPlayersInRedTeam) {
                    if (uuid != null) {
                        Player player1 = Bukkit.getPlayer(uuid);
                        if (player1 != null) {
                            redKills.add(plugin.inGameKills.get(uuid));
                        }
                    }
                }
            }
            List<Integer> blueKills = new ArrayList<>();
            int blueMaxKills = 0;
            Set<UUID> groupPlayersInBlueTeam = plugin.getGroupPlayersInBlueTeam(player);
            if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                for (UUID uuid : groupPlayersInBlueTeam) {
                    if (uuid != null) {
                        Player player1 = Bukkit.getPlayer(uuid);
                        if (player1 != null) {
                            blueKills.add(plugin.inGameKills.get(uuid));
                        }
                    }
                }
            }
            List<Integer> cyanKills = new ArrayList<>();
            int cyanMaxKills = 0;
            Set<UUID> groupPlayersInCyanTeam = plugin.getGroupPlayersInCyanTeam(player);
            if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                for (UUID uuid : groupPlayersInCyanTeam) {
                    if (uuid != null) {
                        Player player1 = Bukkit.getPlayer(uuid);
                        if (player1 != null) {
                            cyanKills.add(plugin.inGameKills.get(uuid));
                        }
                    }
                }
            }
            List<Integer> yellowKills = new ArrayList<>();
            int yellowMaxKills = 0;
            Set<UUID> groupPlayersInYellowTeam = plugin.getGroupPlayersInYellowTeam(player);
            if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                for (UUID uuid : groupPlayersInYellowTeam) {
                    if (uuid != null) {
                        Player player1 = Bukkit.getPlayer(uuid);
                        if (player1 != null) {
                            yellowKills.add(plugin.inGameKills.get(uuid));
                        }
                    }
                }
            }
            // calculate the kills
            if (!yellowKills.isEmpty()) {
                for (int kill : yellowKills) {
                    yellowMaxKills = yellowMaxKills + kill;
                }
            }
            if (!redKills.isEmpty()) {
                for (int kill : redKills) {
                    redMaxKills = redMaxKills + kill;
                }
            }
            if (!blueKills.isEmpty()) {
                for (int kill : blueKills) {
                    blueMaxKills = blueMaxKills + kill;
                }
            }
            if (!cyanKills.isEmpty()) {
                for (int kill : cyanKills) {
                    cyanMaxKills = cyanMaxKills + kill;
                }
            }
            if (!greenKills.isEmpty()) {
                for (int kill : greenKills) {
                    greenMaxKills = greenMaxKills + kill;
                }
            }
            // get the biggest one
            List<Integer> allKills = new ArrayList<>();
            allKills.add(yellowMaxKills);
            allKills.add(redMaxKills);
            allKills.add(blueMaxKills);
            allKills.add(cyanMaxKills);
            allKills.add(greenMaxKills);
            int winner = 0;
            for (int kill : allKills) {
                if (kill > winner) {
                    winner = kill;
                }
            }
            // yellow1
            UUID uuid = player.getUniqueId();
            Location loc = plugin.mainConfig.getLocationMain("end_location");
            boolean b = yellowMaxKills == redMaxKills;
            boolean b1 = yellowMaxKills == cyanMaxKills;
            boolean b2 = yellowMaxKills == greenMaxKills;
            boolean b3 = blueMaxKills == redMaxKills;
            boolean b4 = blueMaxKills == yellowMaxKills;
            boolean b5 = blueMaxKills == cyanMaxKills;
            boolean b6 = blueMaxKills == greenMaxKills;
            boolean b7 = cyanMaxKills == redMaxKills;
            boolean b8 = greenMaxKills == redMaxKills;
            boolean b9 = greenMaxKills == cyanMaxKills;

            boolean blue = !plugin.getGroupPlayersInBlueTeam(player).isEmpty();
            boolean red1 = !plugin.getGroupPlayersInRedTeam(player).isEmpty();
            boolean yellow1 = !plugin.getGroupPlayersInYellowTeam(player).isEmpty();
            boolean cyan = !plugin.getGroupPlayersInCyanTeam(player).isEmpty();
            boolean green1 = !plugin.getGroupPlayersInGreenTeam(player).isEmpty();

            boolean b10 = b7 && cyan && red1;
            boolean b12 = b9 && green1 && cyan;
            boolean b11 = b8 && green1 && red1;
            boolean b13 = b && yellow1 && red1;
            boolean b14 = b1 && yellow1 && cyan;
            boolean b15 = b2 && yellow1 && green1;
            boolean b16 = b3 && blue && red1;
            boolean b17 = b4 && blue && yellow1;
            boolean b18 = b5 && blue && cyan;
            boolean b19 = b6 && blue && green1;

            if (b13) {
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + red + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                    reset(player, loc);
                    return;
                } else if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty() && groupPlayersInYellowTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                    reset(player, loc);
                    return;
                }
            }
            String s = "  ! Tie !";
            if (b14) {
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                    reset(player, loc);
                    return;
                } else if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty() && groupPlayersInYellowTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                    reset(player, loc);
                    return;
                }
            }
            if (b15) {
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty() && groupPlayersInGreenTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                    reset(player, loc);
                    return;
                } else if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty() && groupPlayersInYellowTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                    reset(player, loc);
                    return;
                }
            }
            if (b16) {
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + red + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                    reset(player, loc);
                    return;
                } else if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                    reset(player, loc);
                    return;
                }
            }
            if (b17) {
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                    reset(player, loc);
                    return;
                } else if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty() && groupPlayersInYellowTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "  Your Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    playersInYellowTeam(player);
                    reset(player, loc);
                    return;
                }
            }
            if (b18) {
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                    reset(player, loc);
                    return;
                } else if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                    reset(player, loc);
                    return;
                }
            }
            if (b19) {
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    playersInBlueTeam(player);
                    reset(player, loc);
                    return;
                } else if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty() && groupPlayersInGreenTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                    reset(player, loc);
                    return;
                }
            }
            // cyan
            if (b10) {
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                    reset(player, loc);
                    return;
                } else if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + red + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                    reset(player, loc);
                    return;
                }
            }
            // green1
            if (b11) {
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + red + "" + italic + "Red");
                    player.sendMessage("");
                    playersInRedTeam(player);
                    reset(player, loc);
                    return;
                } else if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty() && groupPlayersInGreenTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                    reset(player, loc);
                    return;
                }
            }
            if (b12) {
                // greenMaxKills == cyanMaxKills
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    playersInCyanTeam(player);
                    reset(player, loc);
                    return;
                } else if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty() && groupPlayersInGreenTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    playersInGreenTeam(player);
                    reset(player, loc);
                    return;
                }
            }
            if (winner == yellowMaxKills) {
                if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                    // yellow1 team is the winner
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "  Winner team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    // give them the win
                    theYellowTeamWins(player);
                }
            } else if (winner == blueMaxKills) {
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                    // blue team is the winner
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "  Winner team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    theBlueTeamWins(player);
                }
            } else if (winner == cyanMaxKills) {
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                    // cyan team is the winner
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "  Winner team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    theCyanTeamWins(player);
                }
            } else if (winner == redMaxKills) {
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                    // red1 team is the winner
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "  Winner team: " + red + "" + italic + "Red");
                    player.sendMessage("");
                    theRedTeamWins(player);
                }
            } else if (winner == greenMaxKills) {
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                    // green1 team is the winner
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "  Winner team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    theGreenTeamWins(player);
                }
            }
        }
    }

    private void thePlayerWithTheMostKillsWins(Player player) {
        ChatColor gold = ChatColor.GOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        String gameStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Game stats" + aqua + "" + italic + " }==-->";
        if (plugin.isPlayerInGroup(player)) {
            // get the Most kills
            int mostKills = 0;
            for (int kill : plugin.inGameKills.values()) {
                if (mostKills < kill) {
                    mostKills = kill;
                }
            }
            List<Integer> allKills = new ArrayList<>();
            for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player)) {
                if (uuid != null) {
                    allKills.add(plugin.inGameKills.get(uuid));
                }
            }
            int oneKill = allKills.get(0);
            int sameKills = 0;
            for (int kill : allKills) {
                if (kill == oneKill) {
                    sameKills++;
                }
            }
            if (sameKills == allKills.size()) {
                // tie
                player.sendMessage(gameStats);
                player.sendMessage(green + "" + italic + "  ! Tie !");
                player.sendMessage("");
                player.sendMessage(playerStats);
                for (UUID uuid3 : plugin.getPlayersInGroupOfPlayer(player)) {
                    if (uuid3 != null) {
                        Player player2 = Bukkit.getPlayer(uuid3);
                        if (player2 != null) {
                            int deaths1 = plugin.inGameDeaths.get(uuid3);
                            int kills1 = plugin.inGameKills.get(uuid3);
                            float KD1 = (float) kills1 / deaths1;
                            String[] list1 = String.valueOf(KD1).split("");
                            String first1 = list1[0];
                            String middle1 = list1[1];
                            String last1 = list1[2];
                            String finalString1 = first1 + middle1 + last1;
                            player.sendMessage(gold + " " + player2.getName());
                            player.sendMessage(red + "" + italic + "  Kills: " + kills1);
                            player.sendMessage(red + "" + italic + "  Deaths: " + deaths1);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "    K/D: " + finalString1);
                            int win1 = plugin.mainConfig.getIntWin(String.valueOf(uuid3));
                            int lose1 = plugin.mainConfig.getIntLose(String.valueOf(uuid3));
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
                reset(player, plugin.mainConfig.getLocationMain("end_location"));
                return;
            }
            // the player with the most kills
            for (UUID uuid1 : plugin.inGameKills.keySet()) {
                if (uuid1 != null && plugin.inGameKills.get(uuid1) == mostKills) {
                    Player playerWithTheMostKills = Bukkit.getPlayer(uuid1);
                    // The K/D
                    if (playerWithTheMostKills != null) {
                        plugin.winners.add(uuid1);
                        UUID uuid2 = playerWithTheMostKills.getUniqueId();
                        Integer integer = plugin.inGameDeaths.get(uuid2);
                        float KD = (float) mostKills / integer;
                        String[] list = String.valueOf(KD).split("");
                        String first = list[0];
                        String middle = list[1];
                        String last = list[2];
                        String finalString = first + middle + last;
                        player.sendMessage(gameStats);
                        player.sendMessage(green + "" + italic + "  Winner: " + playerWithTheMostKills.getName());
                        player.sendMessage("");
                        player.sendMessage(red + "" + italic + "  Kills: " + mostKills);
                        player.sendMessage(red + "" + italic + "  Deaths: " + integer);
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "    K/D: " + finalString);
                        int win1 = plugin.mainConfig.getIntWin(String.valueOf(uuid2));
                        int lose1 = plugin.mainConfig.getIntLose(String.valueOf(uuid2));
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
                        player.sendMessage(playerStats);
                        if (!player.equals(playerWithTheMostKills)) {
                            plugin.losses.add(player.getUniqueId());
                        }
                        for (UUID uuid3 : plugin.getPlayersInGroupOfPlayer(player)) {
                            if (uuid3 != null) {
                                Player player2 = Bukkit.getPlayer(uuid3);
                                if (player2 != null) {
                                    int deaths = plugin.inGameDeaths.get(player2.getUniqueId());
                                    int kills = plugin.inGameKills.get(player2.getUniqueId());
                                    float KD1 = (float) kills / deaths;
                                    String[] list1 = String.valueOf(KD1).split("");
                                    String first1 = list1[0];
                                    String middle1 = list1[1];
                                    String last1 = list1[2];
                                    String finalString1 = first1 + middle1 + last1;
                                    if (!player2.equals(playerWithTheMostKills)) {
                                        player.sendMessage(gold + " " + player2.getName());
                                        player.sendMessage(red + "" + italic + "  Kills: " + kills);
                                        player.sendMessage(red + "" + italic + "  Deaths: " + deaths);
                                        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "    K/D: " + finalString1);
                                        int win = plugin.mainConfig.getIntWin(String.valueOf(uuid3));
                                        int lose = plugin.mainConfig.getIntLose(String.valueOf(uuid3));
                                        float WL1 = (float) win / lose;
                                        String[] listw1 = String.valueOf(WL1).split("");
                                        String one1 = listw1[0];
                                        String two1 = listw1[1];
                                        String three1 = listw1[2];
                                        String wl1 = one1 + two1 + three1;
                                        player.sendMessage(green + "" + italic + "  Wins: " + win);
                                        player.sendMessage(red + "" + italic + "  Losses: " + lose);
                                        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "   W/L: " + wl1);
                                        player.sendMessage("");
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    public synchronized void endThePvE(Player player) {
        if (!player.isOnline()) {
            return;
        }
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        String s = " " + plugin.mainConfig.getStrMain("name") + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + ChatColor.RED + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + ChatColor.RED + "" + italic + " ";
        Location endLoc = plugin.mainConfig.getLocationMain("end_location");
        if (plugin.isPlayerPlayingPvE(player)) {
            NamespacedKey name = new NamespacedKey(plugin, "boss");
            NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
            for (World world : Bukkit.getWorlds()) {
                for (LivingEntity entity : world.getLivingEntities()) {
                    PersistentDataContainer data = entity.getPersistentDataContainer();
                    if (data.has(name, PersistentDataType.STRING) && data.has(namePlayer, PersistentDataType.STRING)) {
                        if (plugin.isPlayerInGroup(player)) {
                            String playerName = data.get(namePlayer, PersistentDataType.STRING);
                            if (playerName != null) {
                                Player player1 = Bukkit.getPlayer(playerName);
                                if (player1 != null) {
                                    UUID uuid = player1.getUniqueId();
                                    if (plugin.getPlayersInGroupOfPlayer(player).contains(uuid)) {
                                        entity.setHealth(0);
                                    }
                                }
                            }
                        } else if (data.get(namePlayer, PersistentDataType.STRING).equals(player.getName())) {
                            entity.setHealth(0);
                        }
                    }
                }
            }
            String name1 = plugin.getGameName(player);
            plugin.stillAlive.remove(name1);
            if (plugin.isPlayerInGroup(player)) {
                for (UUID uuid1 : plugin.getPlayersInGroupOfPlayer(player)) {
                    if (uuid1 != null) {
                        Player player1 = Bukkit.getPlayer(uuid1);
                        if (player1 != null) {
                            if (plugin.playersPlayingPvEZombie.contains(uuid1)) {
                                plugin.playAgain.put(uuid1, "pve-zombie");
                            } else if (plugin.playersPlayingPvESkeleton.contains(uuid1)) {
                                plugin.playAgain.put(uuid1, "pve-skeleton");
                            } else if (plugin.playersPlayingPvESlime.contains(uuid1)) {
                                plugin.playAgain.put(uuid1, "pve-slime");
                            } else if (plugin.playersPlayingPvESpider.contains(uuid1)) {
                                plugin.playAgain.put(uuid1, "pve-spider");
                            } else if (plugin.playersPlayingPvEEnderman.contains(uuid1)) {
                                plugin.playAgain.put(uuid1, "pve-enderman");
                            }
                            plugin.playersPlayingPvESpider.remove(uuid1);
                            plugin.playersPlayingPvESkeleton.remove(uuid1);
                            plugin.playersPlayingPvEZombie.remove(uuid1);
                            plugin.playersPlayingPvESlime.remove(uuid1);
                            plugin.playersPlayingPvEEnderman.remove(uuid1);
                            plugin.playersChoicePvESpider.remove(uuid1);
                            plugin.playersChoicePvESkeleton.remove(uuid1);
                            plugin.playersChoicePvEZombie.remove(uuid1);
                            plugin.playersChoicePvEEnderman.remove(uuid1);
                            plugin.playersChoicePvESlime.remove(uuid1);
                            plugin.removePlayerFromGame(player1);
                            player1.getInventory().clear();
                            AttributeInstance attributeInstance = player1.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                            assert attributeInstance != null;
                            attributeInstance.setBaseValue(20);
                            player1.setHealth(20);
                            player1.removePotionEffect(PotionEffectType.SPEED);
                            player1.getInventory().clear();
                            player1.setGameMode(GameMode.SURVIVAL);
                            if (endLoc != null) {
                                player1.teleport(endLoc);
                            } else {
                                if (!player1.hasPermission("foodm.staff")) {
                                    player1.sendMessage(ERROR + "One of the locations is not set.");
                                } else {
                                    player1.sendMessage(ERROR + "The end location is not set.");
                                }
                            }
                        }
                    }
                }
            } else {
                // the player is solo
                UUID uuid2 = player.getUniqueId();
                if (plugin.playersPlayingPvEZombie.contains(uuid2)) {
                    plugin.playAgain.put(uuid2, "pve-zombie");
                } else if (plugin.playersPlayingPvESkeleton.contains(uuid2)) {
                    plugin.playAgain.put(uuid2, "pve-skeleton");
                } else if (plugin.playersPlayingPvESlime.contains(uuid2)) {
                    plugin.playAgain.put(uuid2, "pve-slime");
                } else if (plugin.playersPlayingPvESpider.contains(uuid2)) {
                    plugin.playAgain.put(uuid2, "pve-spider");
                } else if (plugin.playersPlayingPvEEnderman.contains(uuid2)) {
                    plugin.playAgain.put(uuid2, "pve-enderman");
                }
                plugin.removePlayerFromGame(player);
                plugin.playersPlayingPvESpider.remove(uuid2);
                plugin.playersPlayingPvESkeleton.remove(uuid2);
                plugin.playersPlayingPvEZombie.remove(uuid2);
                plugin.playersPlayingPvESlime.remove(uuid2);
                plugin.playersPlayingPvEEnderman.remove(uuid2);
                plugin.playersChoicePvESpider.remove(uuid2);
                plugin.playersChoicePvESkeleton.remove(uuid2);
                plugin.playersChoicePvEZombie.remove(uuid2);
                plugin.playersChoicePvEEnderman.remove(uuid2);
                plugin.playersChoicePvESlime.remove(uuid2);
                player.getInventory().clear();
                AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                assert attributeInstance != null;
                attributeInstance.setBaseValue(20);
                player.setHealth(20);
                player.removePotionEffect(PotionEffectType.SPEED);
                player.getInventory().clear();
                player.setGameMode(GameMode.SURVIVAL);
                if (endLoc != null) {
                    player.teleport(endLoc);
                } else {
                    if (!player.hasPermission("foodm.staff")) {
                        player.sendMessage(ERROR + "One of the locations is not set.");
                    } else {
                        player.sendMessage(ERROR + "The end location is not set.");
                    }
                }
            }
        }
    }

    private void FoodGameWinner(Player player, Player playerWinner) {
        UUID uuids = playerWinner.getUniqueId();
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        String gameStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Game stats" + aqua + "" + italic + " }==-->";
        int deaths1 = plugin.inGameDeaths.get(uuids);
        int kills1 = plugin.inGameKills.get(uuids);
        float KD1 = (float) kills1 / deaths1;
        String[] list1 = String.valueOf(KD1).split("");
        String first1 = list1[0];
        String middle1 = list1[1];
        String last1 = list1[2];
        String finalString1 = first1 + middle1 + last1;
        plugin.winners.add(uuids);
        player.sendMessage(gameStats);
        player.sendMessage(green + "" + italic + "  Winner: " + gold + "" + playerWinner.getName());
        player.sendMessage("");
        player.sendMessage(red + "" + italic + "  Kills: " + kills1);
        player.sendMessage(red + "" + italic + "  Deaths: " + deaths1);
        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "    K/D: " + finalString1);
        int win1 = plugin.mainConfig.getIntWin(String.valueOf(uuids));
        int lose1 = plugin.mainConfig.getIntLose(String.valueOf(uuids));
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
        player.sendMessage(playerStats);
        if (!player.equals(playerWinner)) {
            plugin.losses.add(player.getUniqueId());
        }
        for (UUID uuid3 : plugin.getPlayersInGroupOfPlayer(player)) {
            if (uuid3 != null) {
                Player player2 = Bukkit.getPlayer(uuid3);
                if (player2 != null) {
                    int deaths = plugin.inGameDeaths.get(uuid3);
                    int kills = plugin.inGameKills.get(uuid3);
                    float KD = (float) kills / deaths;
                    String[] list = String.valueOf(KD).split("");
                    String first = list[0];
                    String middle = list[1];
                    String last = list[2];
                    String finalString = first + middle + last;
                    if (!player2.equals(playerWinner)) {
                        player.sendMessage(gold + " " + player2.getName());
                        player.sendMessage(red + "" + italic + "  Kills: " + kills);
                        player.sendMessage(red + "" + italic + "  Deaths: " + deaths);
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "    K/D: " + finalString);
                        int win = plugin.mainConfig.getIntWin(String.valueOf(uuid3));
                        int lose = plugin.mainConfig.getIntLose(String.valueOf(uuid3));
                        float WL1 = (float) win / lose;
                        String[] listw1 = String.valueOf(WL1).split("");
                        String one2 = listw1[0];
                        String two2 = listw1[1];
                        String three2 = listw1[2];
                        String wl1 = one2 + two2 + three2;
                        player.sendMessage(green + "" + italic + "  Wins: " + win);
                        player.sendMessage(red + "" + italic + "  Losses: " + lose);
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "   W/L: " + wl1);
                        player.sendMessage("");
                    }
                }
            }
        }
    }

    public synchronized void endTheGameWithStatus(Player player) {
        if (!player.isOnline()) {
            return;
        }
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String ERROR;
        if (plugin.mainConfig.getStrMain("name") != null) {
            ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + " " + plugin.mainConfig.getStrMain("name") + " " + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        } else {
            ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + " FoodMaster " + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        }
        String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        String gameStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Game stats" + aqua + "" + italic + " }==-->";
        UUID uuid = player.getUniqueId();
        Location endLoc = plugin.mainConfig.getLocationMain("end_location");
        if (endLoc == null) {
            if (player.hasPermission("foodm.staff")) {
                player.sendMessage(ERROR + "The end location is not set. Use " + ChatColor.DARK_PURPLE + "" + italic + "/fm set end");
            } else {
                player.sendMessage(ERROR + "One of the locations isn't set.");
            }
        } else {
            // play again -- start
            if (!plugin.isPlayerInGroup(player)) {
                player.sendMessage(ERROR + "You need to be in a group.");
            } else {
                if (plugin.playersInFreeForAll.contains(uuid) || plugin.playersChoiceFreeForAll.contains(uuid)) {
                    plugin.playAgain.put(uuid, "free-for-all");
                }
                if (plugin.players5Teams.contains(uuid) || plugin.playersThatChoice5Teams.contains(uuid)) {
                    plugin.playAgain.put(uuid, "team-deathmatch5");
                } else if (plugin.players4Teams.contains(uuid) || plugin.playersThatChoice4Teams.contains(uuid)) {
                    plugin.playAgain.put(uuid, "team-deathmatch4");
                } else if (plugin.players3Teams.contains(uuid) || plugin.playersThatChoice3Teams.contains(uuid)) {
                    plugin.playAgain.put(uuid, "team-deathmatch3");
                } else if (plugin.players2Teams.contains(uuid) || plugin.playersThatChoice2Teams.contains(uuid)) {
                    plugin.playAgain.put(uuid, "team-deathmatch2");
                }
                if (plugin.playersInFoodGame.contains(uuid) || plugin.playersChoiceFoodGame.contains(uuid)) {
                    plugin.playAgain.put(uuid, "food-game");
                }
                // play again -- end
                if (plugin.isPlayerInGame(player)) {
                    String name = plugin.getGameName(player);
                    if (name != null) {
                        plugin.timer.remove(name);
                        if (plugin.timerTik.bossBar.getPlayers().contains(player)) {
                            plugin.timerTik.bossBar.removePlayer(player);
                        }
                        for (UUID uuid1 : plugin.getPlayersInGroupOfPlayer(player)) {
                            if (uuid1 != null) {
                                Player player1 = Bukkit.getPlayer(uuid1);
                                if (player1 != null) {
                                    giveGameStats(uuid1);
                                }
                            }
                        }
                        // MVP Board
                        if (plugin.isPlayerPlayingFoodGame(player)) {
                            for (UUID uuids : plugin.getPlayersInGroupOfPlayer(player)) {
                                if (plugin.FoodGameWinner.contains(uuids)) {
                                    Player playerWinner = Bukkit.getPlayer(uuids);
                                    if (playerWinner != null) {
                                        FoodGameWinner(player, playerWinner);
                                    }
                                    break;
                                } else {
                                    for (UUID uuid1 : plugin.getPlayersInGroupOfPlayer(player)) {
                                        if (plugin.playersInBeefKit.contains(uuid1)) {
                                            Player playerWinner1 = Bukkit.getPlayer(uuid1);
                                            if (playerWinner1 != null) {
                                                FoodGameWinner(player, playerWinner1);
                                            }
                                            break;
                                        } else if (plugin.playersInFishKit.contains(uuid1)) {
                                            Player playerWinner1 = Bukkit.getPlayer(uuid1);
                                            if (playerWinner1 != null) {
                                                FoodGameWinner(player, playerWinner1);
                                            }
                                            break;
                                        } else if (plugin.playersInPotatoKit.contains(uuid1)) {
                                            Player playerWinner1 = Bukkit.getPlayer(uuid1);
                                            if (playerWinner1 != null) {
                                                FoodGameWinner(player, playerWinner1);
                                            }
                                            break;
                                        } else if (plugin.playersInMelonKit.contains(uuid1)) {
                                            Player playerWinner1 = Bukkit.getPlayer(uuid1);
                                            if (playerWinner1 != null) {
                                                FoodGameWinner(player, playerWinner1);
                                            }
                                            break;
                                        } else if (plugin.playersInBreadKit.contains(uuid1)) {
                                            Player playerWinner1 = Bukkit.getPlayer(uuid1);
                                            if (playerWinner1 != null) {
                                                FoodGameWinner(player, playerWinner1);
                                            }
                                            break;
                                        } else if (plugin.playersInCookieKit.contains(uuid1)) {
                                            Player playerWinner1 = Bukkit.getPlayer(uuid1);
                                            if (playerWinner1 != null) {
                                                FoodGameWinner(player, playerWinner1);
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        } else if (plugin.isPlayerPlayingFoodWars(player)) {
                            if (plugin.mainConfig.getBooleanGame("enable_lives_team_deathmatch")) {
                                // the last team standing
                                List<String> aliveTeams = new ArrayList<>(plugin.getAliveTeams(player));
                                // 1 blue  3 red  0 cyan 0 yellow
                                if (aliveTeams.size() == 1) {
                                    String teamName = aliveTeams.get(0);
                                    player.sendMessage(gameStats);
                                    if (teamName.equalsIgnoreCase("red")) {
                                        player.sendMessage(green + "" + italic + "  Winner team: " + red + "" + italic + "Red");
                                        player.sendMessage("");
                                        theRedTeamWins(player);
                                    } else if (teamName.equalsIgnoreCase("blue")) {
                                        player.sendMessage(green + "" + italic + "  Winner team: " + ChatColor.BLUE + "" + italic + "Blue");
                                        player.sendMessage("");
                                        theBlueTeamWins(player);
                                    } else if (teamName.equalsIgnoreCase("yellow")) {
                                        player.sendMessage(green + "" + italic + "  Winner team: " + yellow + "" + italic + "Yellow");
                                        player.sendMessage("");
                                        theYellowTeamWins(player);
                                    } else if (teamName.equalsIgnoreCase("cyan")) {
                                        player.sendMessage(green + "" + italic + "  Winner team: " + aqua + "" + italic + "Cyan");
                                        player.sendMessage("");
                                        theCyanTeamWins(player);
                                    } else if (teamName.equalsIgnoreCase("green")) {
                                        player.sendMessage(green + "" + italic + "  Winner team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                                        player.sendMessage("");
                                        theGreenTeamWins(player);
                                    }
                                } else {
                                    theTeamWithTheMostKillsWins(player);
                                }
                            } else {
                                theTeamWithTheMostKillsWins(player);
                            }
                        } else if (plugin.isPlayerPlayingFreeForAll(player)) {
                            String name1 = plugin.getGameName(player);
                            if (name1 != null) {
                                List<UUID> alivePlayers = new ArrayList<>(plugin.stillAlive.get(name1));
                                if (plugin.getPlayersInGroupOfPlayer(player).size() == 2) {
                                    int one;
                                    int two;
                                    List<UUID> groupClone = new ArrayList<>(plugin.getPlayersInGroupOfPlayer(player));
                                    one = plugin.inGameKills.get(groupClone.get(0));
                                    two = plugin.inGameKills.get(groupClone.get(1));
                                    if (one == two) {
                                        player.sendMessage(gameStats);
                                        player.sendMessage(green + "" + italic + "   ! Tie !");
                                        player.sendMessage("");
                                        player.sendMessage(playerStats);
                                        for (UUID uuid3 : plugin.getPlayersInGroupOfPlayer(player)) {
                                            if (uuid3 != null) {
                                                Player player2 = Bukkit.getPlayer(uuid3);
                                                if (player2 != null) {
                                                    int deaths1 = plugin.inGameDeaths.get(uuid3);
                                                    int kills1 = plugin.inGameKills.get(uuid3);
                                                    float KD1 = (float) kills1 / deaths1;
                                                    String[] list1 = String.valueOf(KD1).split("");
                                                    String first1 = list1[0];
                                                    String middle1 = list1[1];
                                                    String last1 = list1[2];
                                                    String finalString1 = first1 + middle1 + last1;
                                                    player.sendMessage(gold + " " + player2.getName());
                                                    player.sendMessage(red + "" + italic + "  Kills: " + kills1);
                                                    player.sendMessage(red + "" + italic + "  Deaths: " + deaths1);
                                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "    K/D: " + finalString1);
                                                    player.sendMessage("");
                                                }
                                            }
                                        }
                                        reset(player, endLoc);
                                        return;
                                    }
                                }
                                if (plugin.mainConfig.getBooleanGame("enable_lives_free-for-all")) {
                                    // the last player standing is the winner
                                    if (alivePlayers.size() == 1) {
                                        // only ones
                                        UUID uuid1 = alivePlayers.get(0);
                                        if (uuid1 != null) {
                                            Player playerWinner = Bukkit.getPlayer(uuid1);
                                            if (playerWinner != null) {
                                                plugin.winners.add(uuid1);
                                                Integer kills = plugin.inGameKills.get(uuid1);
                                                Integer deaths = plugin.inGameDeaths.get(uuid1);
                                                float KD = (float) kills / deaths;
                                                String[] list = String.valueOf(KD).split("");
                                                String first = list[0];
                                                String middle = list[1];
                                                String last = list[2];
                                                String finalString = first + middle + last;
                                                player.sendMessage(gameStats);
                                                player.sendMessage(green + "" + italic + "  Winner: " + gold + "" + playerWinner.getName());
                                                player.sendMessage("");
                                                player.sendMessage(red + "" + italic + "  Kills: " + kills);
                                                player.sendMessage(red + "" + italic + "  Deaths: " + deaths);
                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "    K/D: " + finalString);
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
                                                player.sendMessage(playerStats);
                                                if (!player.equals(playerWinner)) {
                                                    plugin.losses.add(player.getUniqueId());
                                                }
                                                for (UUID uuid3 : plugin.getPlayersInGroupOfPlayer(player)) {
                                                    if (uuid3 != null) {
                                                        Player player2 = Bukkit.getPlayer(uuid3);
                                                        if (player2 != null) {
                                                            int deaths1 = plugin.inGameDeaths.get(uuid3);
                                                            int kills1 = plugin.inGameKills.get(uuid3);
                                                            float KD1 = (float) kills1 / deaths1;
                                                            String[] list1 = String.valueOf(KD1).split("");
                                                            String first1 = list1[0];
                                                            String middle1 = list1[1];
                                                            String last1 = list1[2];
                                                            String finalString1 = first1 + middle1 + last1;
                                                            if (!player2.equals(playerWinner)) {
                                                                player.sendMessage(gold + " " + player2.getName());
                                                                player.sendMessage(red + "" + italic + "  Kills: " + kills1);
                                                                player.sendMessage(red + "" + italic + "  Deaths: " + deaths1);
                                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "    K/D: " + finalString1);
                                                                int win = plugin.mainConfig.getIntWin(String.valueOf(uuid3));
                                                                int lose = plugin.mainConfig.getIntLose(String.valueOf(uuid3));
                                                                float WL1 = (float) win / lose;
                                                                String[] listw1 = String.valueOf(WL1).split("");
                                                                String one2 = listw1[0];
                                                                String two2 = listw1[1];
                                                                String three2 = listw1[2];
                                                                String wl1 = one2 + two2 + three2;
                                                                player.sendMessage(green + "" + italic + "  Wins: " + win);
                                                                player.sendMessage(red + "" + italic + "  Losses: " + lose);
                                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "   W/L: " + wl1);
                                                                player.sendMessage("");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (alivePlayers.size() > 1) {
                                    int mostKills = 0;
                                    for (UUID uuids : alivePlayers) {
                                        if (uuids != null) {
                                            int kills = plugin.inGameKills.get(uuids);
                                            if (kills > mostKills) {
                                                mostKills = kills;
                                            }
                                        }
                                    }
                                    // the player with the most kills
                                    for (UUID uuid1 : plugin.getPlayersInGroupOfPlayer(player)) {
                                        if (uuid1 != null && plugin.inGameKills.get(uuid1) == mostKills) {
                                            Player playerWithTheMostKills = Bukkit.getPlayer(uuid1);
                                            // The K/D
                                            if (playerWithTheMostKills != null) {
                                                plugin.winners.add(uuid1);
                                                int integer = plugin.inGameDeaths.get(uuid1);
                                                float KD = (float) mostKills / integer;
                                                String[] list = String.valueOf(KD).split("");
                                                String first = list[0];
                                                String middle = list[1];
                                                String last = list[2];
                                                String finalString = first + middle + last;
                                                player.sendMessage(gameStats);
                                                player.sendMessage(green + "" + italic + "  Winner: " + gold + "" + playerWithTheMostKills.getName());
                                                player.sendMessage("");
                                                player.sendMessage(red + "" + italic + "  Kills: " + mostKills);
                                                player.sendMessage(red + "" + italic + "  Deaths: " + integer);
                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "    K/D: " + finalString);
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
                                                player.sendMessage(playerStats);
                                                if (!player.equals(playerWithTheMostKills)) {
                                                    plugin.losses.add(player.getUniqueId());
                                                }
                                                for (UUID uuid3 : plugin.getPlayersInGroupOfPlayer(player)) {
                                                    if (uuid3 != null) {
                                                        Player player2 = Bukkit.getPlayer(uuid3);
                                                        if (player2 != null) {
                                                            //giveGameStats(uuid3);
                                                            int deaths = plugin.inGameDeaths.get(uuid3);
                                                            int kills = plugin.inGameKills.get(uuid3);
                                                            float KD1 = (float) kills / deaths;
                                                            String[] list1 = String.valueOf(KD1).split("");
                                                            String first1 = list1[0];
                                                            String middle1 = list1[1];
                                                            String last1 = list1[2];
                                                            String finalString1 = first1 + middle1 + last1;
                                                            if (!player2.equals(playerWithTheMostKills)) {
                                                                player.sendMessage(gold + " " + player2.getName());
                                                                player.sendMessage(red + "" + italic + "  Kills: " + kills);
                                                                player.sendMessage(red + "" + italic + "  Deaths: " + deaths);
                                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "    K/D: " + finalString1);
                                                                int win = plugin.mainConfig.getIntWin(String.valueOf(uuid3));
                                                                int lose = plugin.mainConfig.getIntLose(String.valueOf(uuid3));
                                                                float WL1 = (float) win / lose;
                                                                String[] listw1 = String.valueOf(WL1).split("");
                                                                String one1 = listw1[0];
                                                                String two1 = listw1[1];
                                                                String three1 = listw1[2];
                                                                String w1 = one1 + two1 + three1;
                                                                player.sendMessage(green + "" + italic + "  Wins: " + win);
                                                                player.sendMessage(red + "" + italic + "  Losses: " + lose);
                                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "   W/L: " + w1);
                                                                player.sendMessage("");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    }
                                } else {
                                    thePlayerWithTheMostKillsWins(player);
                                }
                            } else {
                                thePlayerWithTheMostKillsWins(player);
                            }
                        }
                    }
                }
            }
            if (plugin.isPlayerInGame(player)) {
                plugin.removePlayerFromGame(player);
            }
            if (plugin.isPlayerInWaitingLobby(player)) {
                plugin.removePlayerFromWaitLobby.removePlayerFromWaitedLobby(player);
            }
            if (plugin.isPlayerInGroup(player)) {
                Set<UUID> playerGroupClone = new HashSet<>(plugin.getPlayersInGroupOfPlayer(player));
                if (!playerGroupClone.isEmpty()) {
                    plugin.locOfPlayersInWaitingLobby.remove(playerGroupClone);
                }
            }
            PlayerInventory inventory = player.getInventory();
            if (inventory.getHelmet() != null) {
                inventory.setHelmet(null);
            }
            if (inventory.getChestplate() != null) {
                inventory.setChestplate(null);
            }
            if (inventory.getLeggings() != null) {
                inventory.setLeggings(null);
            }
            if (inventory.getBoots() != null) {
                inventory.setBoots(null);
            }
            // kits
            plugin.playersInPotatoKit.remove(uuid);
            plugin.playersInBreadKit.remove(uuid);
            plugin.playersInMelonKit.remove(uuid);
            plugin.playersInCookieKit.remove(uuid);
            plugin.playersInFishKit.remove(uuid);
            plugin.playersRandomKit.remove(uuid);
            // teams
            plugin.players2Teams.remove(uuid);
            plugin.players3Teams.remove(uuid);
            plugin.players4Teams.remove(uuid);
            plugin.players5Teams.remove(uuid);
            // others
            plugin.playersThatChoice5Teams.remove(uuid);
            plugin.playersThatChoice4Teams.remove(uuid);
            plugin.playersThatChoice2Teams.remove(uuid);
            plugin.playersPlayingPvESpider.remove(uuid);
            plugin.playersPlayingPvESkeleton.remove(uuid);
            plugin.playersPlayingPvEZombie.remove(uuid);
            plugin.playersPlayingPvESlime.remove(uuid);
            plugin.playersPlayingPvEEnderman.remove(uuid);
            plugin.playersChoicePvESpider.remove(uuid);
            plugin.playersChoicePvESkeleton.remove(uuid);
            plugin.playersChoicePvEZombie.remove(uuid);
            plugin.playersChoicePvEEnderman.remove(uuid);
            plugin.playersChoicePvESlime.remove(uuid);
            if (plugin.isPlayerInGroup(player)) {
                plugin.locOfPlayersInWaitingLobby.remove(new HashSet<>(plugin.getPlayersInGroupOfPlayer(player)));
            }
            plugin.kickedPlayers.remove(uuid);
            plugin.playersThatChoice3Teams.remove(uuid);
            String name1 = plugin.getGameName(player);
            plugin.stillAlive.remove(name1);
            plugin.ReadySystem.remove(uuid);
            plugin.ReadyPlayers.remove(uuid);
            plugin.playersInFreeForAll.remove(uuid);
            plugin.lives.remove(uuid);
            plugin.playersChoiceFreeForAll.remove(uuid);
            // Health
            AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert attributeInstance != null;
            attributeInstance.setBaseValue(20);
            player.setHealth(20);
            player.removePotionEffect(PotionEffectType.SPEED);
            player.getInventory().clear();
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(endLoc);
        }
    }
}
