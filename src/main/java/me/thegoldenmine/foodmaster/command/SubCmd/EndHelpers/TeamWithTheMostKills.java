package me.thegoldenmine.foodmaster.command.SubCmd.EndHelpers;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TeamWithTheMostKills {
    private final FoodMaster plugin;

    public TeamWithTheMostKills(FoodMaster main) {
        plugin = main;
    }

    public void winnerTeam(Player player) {
        ChatColor gold = ChatColor.GOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        String gameStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Game stats" + aqua + "" + italic + " }==-->";
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            // get the total kills of the teams
            // greenNotEmpty, yellowNotEmpty, blue, cyanNotEmpty, redNotEmpty
            List<Integer> greenKills = new ArrayList<>();
            int greenMaxKills = 0;
            Set<UUID> groupPlayersInGreenTeam = plugin.deathmatch.getGroupPlayersInGreenTeam(player);
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
            Set<UUID> groupPlayersInRedTeam = plugin.deathmatch.getGroupPlayersInRedTeam(player);
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
            Set<UUID> groupPlayersInBlueTeam = plugin.deathmatch.getGroupPlayersInBlueTeam(player);
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
            Set<UUID> groupPlayersInCyanTeam = plugin.deathmatch.getGroupPlayersInCyanTeam(player);
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
            Set<UUID> groupPlayersInYellowTeam = plugin.deathmatch.getGroupPlayersInYellowTeam(player);
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
            // yellowNotEmpty
            UUID uuid = player.getUniqueId();
            boolean yellowEqRed = yellowMaxKills == redMaxKills;
            boolean yellowEqCyan = yellowMaxKills == cyanMaxKills;
            boolean yellowEqGreen = yellowMaxKills == greenMaxKills;
            boolean blueEqRed = blueMaxKills == redMaxKills;
            boolean blueEqYellow = blueMaxKills == yellowMaxKills;
            boolean blueEqCyan = blueMaxKills == cyanMaxKills;
            boolean blueEqGreen = blueMaxKills == greenMaxKills;
            boolean cyanEqRed = cyanMaxKills == redMaxKills;
            boolean greenEqRed = greenMaxKills == redMaxKills;
            boolean greenEqCyan = greenMaxKills == cyanMaxKills;

            boolean blueNotEmpty = !plugin.deathmatch.getGroupPlayersInBlueTeam(player).isEmpty();
            boolean redNotEmpty = !plugin.deathmatch.getGroupPlayersInRedTeam(player).isEmpty();
            boolean yellowNotEmpty = !plugin.deathmatch.getGroupPlayersInYellowTeam(player).isEmpty();
            boolean cyanNotEmpty = !plugin.deathmatch.getGroupPlayersInCyanTeam(player).isEmpty();
            boolean greenNotEmpty = !plugin.deathmatch.getGroupPlayersInGreenTeam(player).isEmpty();

            boolean tieCyanAndRed = cyanEqRed && cyanNotEmpty && redNotEmpty;
            boolean tieGreenAndCyan = greenEqCyan && greenNotEmpty && cyanNotEmpty;
            boolean tieGreenAndRed = greenEqRed && greenNotEmpty && redNotEmpty;
            boolean tieYellowAndRed = yellowEqRed && yellowNotEmpty && redNotEmpty;
            boolean tieYellowAndCyan = yellowEqCyan && yellowNotEmpty && cyanNotEmpty;
            boolean tieYellowAndGreen = yellowEqGreen && yellowNotEmpty && greenNotEmpty;
            boolean tieBlueAndRed = blueEqRed && blueNotEmpty && redNotEmpty;
            boolean tieBlueAndYellow = blueEqYellow && blueNotEmpty && yellowNotEmpty;
            boolean tieBlueAndCyan = blueEqCyan && blueNotEmpty && cyanNotEmpty;
            boolean tieBlueAndGreen = blueEqGreen && blueNotEmpty && greenNotEmpty;

            if (tieYellowAndRed) {
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + red + "" + italic + "Red");
                    player.sendMessage("");
                    plugin.redTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                } else if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty() && groupPlayersInYellowTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    plugin.yellowTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                }
            }
            String s = "  ! Tie !";
            if (tieYellowAndCyan) {
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    plugin.cyanTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                } else if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty() && groupPlayersInYellowTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    plugin.yellowTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                }
            }
            if (tieYellowAndGreen) {
                if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty() && groupPlayersInGreenTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    plugin.greenTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                } else if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty() && groupPlayersInYellowTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    plugin.yellowTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                }
            }
            if (tieBlueAndRed) {
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + red + "" + italic + "Red");
                    player.sendMessage("");
                    plugin.redTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                } else if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    plugin.blueTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                }
            }
            if (tieBlueAndYellow) {
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    plugin.blueTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                } else if (groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty() && groupPlayersInYellowTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "  Your Team: " + yellow + "" + italic + "Yellow");
                    player.sendMessage("");
                    plugin.yellowTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                }
            }
            if (tieBlueAndCyan) {
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    plugin.blueTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                } else if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    plugin.cyanTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                }
            }
            if (tieBlueAndGreen) {
                if (groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty() && groupPlayersInBlueTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.BLUE + "" + italic + "Blue");
                    player.sendMessage("");
                    plugin.blueTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                } else if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty() && groupPlayersInGreenTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + s);
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    plugin.greenTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                }
            }
            // cyanNotEmpty
            if (tieCyanAndRed) {
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    plugin.cyanTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                } else if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + red + "" + italic + "Red");
                    player.sendMessage("");
                    plugin.redTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                }
            }
            // greenNotEmpty
            if (tieGreenAndRed) {
                if (groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty() && groupPlayersInRedTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + red + "" + italic + "Red");
                    player.sendMessage("");
                    plugin.redTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                } else if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty() && groupPlayersInGreenTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    plugin.greenTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                }
            }
            if (tieGreenAndCyan) {
                // greenMaxKills == cyanMaxKills
                if (groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty() && groupPlayersInCyanTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + aqua + "" + italic + "Cyan");
                    player.sendMessage("");
                    plugin.cyanTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                } else if (groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty() && groupPlayersInGreenTeam.contains(uuid)) {
                    player.sendMessage(gameStats);
                    player.sendMessage(green + "" + italic + "   ! Tie !");
                    player.sendMessage("");
                    player.sendMessage(playerStats);
                    player.sendMessage(green + "" + italic + "Your Team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                    player.sendMessage("");
                    plugin.greenTeam.Players(player);
                    plugin.clearPlayer.clearThePlayer(player);
                    return;
                }
            }
            if (winner == yellowMaxKills && groupPlayersInYellowTeam != null && !groupPlayersInYellowTeam.isEmpty()) {
                // yellowNotEmpty team is the winner
                player.sendMessage(gameStats);
                player.sendMessage(green + "" + italic + "  Winner team: " + yellow + "" + italic + "Yellow");
                player.sendMessage("");
                // give them the win
                plugin.yellowTeam.Wins(player);
            } else if (winner == blueMaxKills && groupPlayersInBlueTeam != null && !groupPlayersInBlueTeam.isEmpty()) {
                // blue team is the winner
                player.sendMessage(gameStats);
                player.sendMessage(green + "" + italic + "  Winner team: " + ChatColor.BLUE + "" + italic + "Blue");
                player.sendMessage("");
                plugin.blueTeam.Wins(player);
            } else if (winner == cyanMaxKills && groupPlayersInCyanTeam != null && !groupPlayersInCyanTeam.isEmpty()) {
                // cyanNotEmpty team is the winner
                player.sendMessage(gameStats);
                player.sendMessage(green + "" + italic + "  Winner team: " + aqua + "" + italic + "Cyan");
                player.sendMessage("");
                plugin.cyanTeam.Wins(player);
            } else if (winner == redMaxKills && groupPlayersInRedTeam != null && !groupPlayersInRedTeam.isEmpty()) {
                // redNotEmpty team is the winner
                player.sendMessage(gameStats);
                player.sendMessage(green + "" + italic + "  Winner team: " + red + "" + italic + "Red");
                player.sendMessage("");
                plugin.redTeam.Wins(player);
            } else if (winner == greenMaxKills && groupPlayersInGreenTeam != null && !groupPlayersInGreenTeam.isEmpty()) {
                // greenNotEmpty team is the winner
                player.sendMessage(gameStats);
                player.sendMessage(green + "" + italic + "  Winner team: " + ChatColor.DARK_GREEN + "" + italic + "Green");
                player.sendMessage("");
                plugin.greenTeam.Wins(player);
            }
        }
    }
}
