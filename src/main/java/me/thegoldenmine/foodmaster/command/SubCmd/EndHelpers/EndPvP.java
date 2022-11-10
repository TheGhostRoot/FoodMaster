package me.thegoldenmine.foodmaster.command.SubCmd.EndHelpers;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EndPvP {
    private final FoodMaster plugin;

    public EndPvP(FoodMaster main) {
        plugin = main;
    }

    public void freeForAll(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        //ChatColor yellow = ChatColor.YELLOW;
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
        //UUID uuid = player.getUniqueId();
        //Location endLoc = plugin.mainConfig.getLocationMain("end_location");
        String name1 = plugin.game.getGameName(player);
        if (name1 != null) {
            List<UUID> alivePlayers = new ArrayList<>(plugin.stillAlive.get(name1));
            if (plugin.playerGroup.getPlayersInGroupOfPlayer(player).size() == 2) {
                int one;
                int two;
                List<UUID> groupClone = new ArrayList<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
                one = plugin.inGameKills.get(groupClone.get(0));
                two = plugin.inGameKills.get(groupClone.get(1));
                if (one == two) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
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
                                player.sendMessage("");
                            }
                        }
                    }
                    plugin.clearPlayer.clearThePlayer(player);
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
                for (UUID uuid1 : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
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
                            for (UUID uuid3 : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                                if (uuid3 != null) {
                                    Player player2 = Bukkit.getPlayer(uuid3);
                                    if (player2 != null) {
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
                plugin.playerWithTheMostKills.playerKillerWins(player);
            }
        } else {
            plugin.playerWithTheMostKills.playerKillerWins(player);
        }
    }

    public void deathMatch(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        /*
        String ERROR;
        if (plugin.mainConfig.getStrMain("name") != null) {
            ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + " " + plugin.mainConfig.getStrMain("name") + " " + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        } else {
            ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + " FoodMaster " + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        }

         */
        String gameStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Game stats" + aqua + "" + italic + " }==-->";
        if (plugin.mainConfig.getBooleanGame("enable_lives_team_deathmatch")) {
            // the last team standing
            List<String> aliveTeams = new ArrayList<>(plugin.deathmatch.getAliveTeams(player));
            // 1 blue  3 red  0 cyan 0 yellow
            if (aliveTeams.size() == 1) {
                String teamName = aliveTeams.get(0);
                player.sendMessage(gameStats);
                if (teamName.equalsIgnoreCase("red")) {
                    player.sendMessage(green + "" + italic + "  Winner team: " + red + "" + italic + "Red");
                    player.sendMessage("");
                    plugin.redTeam.Wins(player);
                } else if (teamName.equalsIgnoreCase("blue")) {
                    player.sendMessage(green + "" + italic + "  Winner team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    plugin.blueTeam.Wins(player);
                } else if (teamName.equalsIgnoreCase("yellow")) {
                    player.sendMessage(green + "" + italic + "  Winner team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    plugin.yellowTeam.Wins(player);
                } else if (teamName.equalsIgnoreCase("cyan")) {
                    player.sendMessage(green + "" + italic + "  Winner team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    plugin.cyanTeam.Wins(player);
                } else if (teamName.equalsIgnoreCase("green")) {
                    player.sendMessage(green + "" + italic + "  Winner team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    plugin.greenTeam.Wins(player);
                }
            } else {
                plugin.teamWithTheMostKills.winnerTeam(player);
            }
        } else {
            plugin.teamWithTheMostKills.winnerTeam(player);
        }
    }

    public void foodGameWinner(Player player, Player playerWinner) {
        UUID uuids = playerWinner.getUniqueId();
        ChatColor gold = ChatColor.GOLD;
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
        for (UUID uuid3 : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
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

    public void foodGame(Player player) {
        for (UUID uuid1 : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
            if (plugin.playersInBeefKit.contains(uuid1)) {
                getWinner(player, uuid1);
                break;
            } else if (plugin.playersInFishKit.contains(uuid1)) {
                getWinner(player, uuid1);
                break;
            } else if (plugin.playersInPotatoKit.contains(uuid1)) {
                getWinner(player, uuid1);
                break;
            } else if (plugin.playersInMelonKit.contains(uuid1)) {
                getWinner(player, uuid1);
                break;
            } else if (plugin.playersInBreadKit.contains(uuid1)) {
                getWinner(player, uuid1);
                break;
            } else if (plugin.playersInCookieKit.contains(uuid1)) {
                getWinner(player, uuid1);
                break;
            }
        }
    }

    private void getWinner(Player player, UUID uuid1) {
        Player playerWinner1 = Bukkit.getPlayer(uuid1);
        if (playerWinner1 != null) {
            foodGameWinner(player, playerWinner1);
        }
    }
}
