package me.thegoldenmine.foodmaster.Commands.SubCmd.EndHelpers;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerWithTheMostKills {
    private final FoodMaster plugin;

    public PlayerWithTheMostKills(FoodMaster main) {
        plugin = main;
    }

    public void playerKillerWins(Player player) {
        ChatColor gold = ChatColor.GOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        String gameStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Game stats" + aqua + "" + italic + " }==-->";
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            // get the Most kills
            int mostKills = 0;
            for (int kill : plugin.inGameKills.values()) {
                if (mostKills < kill) {
                    mostKills = kill;
                }
            }
            List<Integer> allKills = new ArrayList<>();
            for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
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
                for (UUID uuid3 : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
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
                plugin.clearPlayer.clearThePlayer(player);
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
                        for (UUID uuid3 : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
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
}
