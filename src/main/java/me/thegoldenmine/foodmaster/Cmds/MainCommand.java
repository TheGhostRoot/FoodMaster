package me.thegoldenmine.foodmaster.Cmds;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MainCommand implements CommandExecutor {
    private final FoodMaster plugin;

    public MainCommand(FoodMaster plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String s42;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s42 = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s42 = " FoodMaster ";
        }
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s42 + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s42 + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s42 + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s42 + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (sender instanceof Player) {
            // when the sender of the command is player
            Player player = (Player) sender;
            String s = "You don't have ";
            if (player.hasPermission("foodm.commands")) {
                // when the player have permission to play the game
                String s1 = "permission.";
                String message1 = WARN + s + red + "" + italic + "foodm.help " + yellow + "" + italic + s1;
                if (args.length >= 1 && args[0].equalsIgnoreCase("help")) {
                    // Help menu
                    if (player.hasPermission("foodm.help")) {
                        plugin.helpMenu.helpAll(player);
                    } else {
                        player.sendMessage(message1);
                    }
                } else if (args.length >= 2 && args[0].equalsIgnoreCase("group") && args[1].equalsIgnoreCase("help")) {
                    if (player.hasPermission("foodm.group.help")) {
                        plugin.helpMenu.helpGroupMenu(player);
                    } else {
                        player.sendMessage(WARN + s + red + "" + italic + "foodm.group.help " + yellow + "" + italic + s1);
                    }
                } else if (args.length >= 1 && args[0].equalsIgnoreCase("gui")) {
                    if (player.hasPermission("foodm.gui")) {
                        player.openInventory(plugin.createGUI.createMain(player));
                    } else {
                        player.sendMessage(WARN + s + red + "" + italic + "foodm.gui " + yellow + "" + italic + s1);
                    }
                } else if (args.length >= 1 && args[0].equalsIgnoreCase("stats")) {
                    if (player.hasPermission("foodm.stats")) {
                        // /fm stats <player name>
                        //       0      1      index
                        //       1      2
                        UUID uuid = player.getUniqueId();
                        String s2 = "  Deaths: ";
                        String s3 = "  Kills: ";
                        String s4 = "   K/D: ";
                        String message = aqua + "" + bold + "<--=={ " + gold + "" + italic + "Game Stats " + aqua + "" + bold + "}==-->";
                        if (args.length > 1) {
                            String playerName = args[1];
                            if (playerName != null) {
                                Player givenPlayer = Bukkit.getPlayer(playerName);
                                if (givenPlayer != null) {
                                    UUID uuid1 = givenPlayer.getUniqueId();
                                    String theUUID = String.valueOf(uuid1);
                                    String kills12 = String.valueOf(plugin.mainConfig.getIntKill(theUUID));
                                    String death12 = String.valueOf(plugin.mainConfig.getIntDeath(theUUID));
                                    int deaths11 = plugin.mainConfig.getIntDeath(theUUID);
                                    int kills11 = plugin.mainConfig.getIntKill(theUUID);
                                    float KD11 = (float) kills11 / deaths11;
                                    String[] list11 = String.valueOf(KD11).split("");
                                    String first11 = list11[0];
                                    String middle11 = list11[1];
                                    String last11 = list11[2];
                                    String finalString11 = first11 + middle11 + last11;
                                    player.sendMessage(aqua + "" + bold + "<--=={ " + gold + "" + italic + "" + givenPlayer.getName() + "'s" + gold + "" + bold + " Stats " + aqua + "" + bold + "}==-->");
                                    player.sendMessage(red + "" + italic + s3 + kills12);
                                    player.sendMessage(red + "" + italic + s2 + death12);
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + s4 + finalString11);
                                    String win = String.valueOf(plugin.mainConfig.getIntWin(theUUID));
                                    String lose = String.valueOf(plugin.mainConfig.getIntLose(theUUID));
                                    int win1 = plugin.mainConfig.getIntWin(theUUID);
                                    int lose1 = plugin.mainConfig.getIntLose(theUUID);
                                    float WL = (float) win1 / lose1;
                                    String[] list = String.valueOf(WL).split("");
                                    String one = list[0];
                                    String two = list[1];
                                    String three = list[2];
                                    String wl = one + two + three;
                                    player.sendMessage("");
                                    player.sendMessage(green + "" + italic + "  Wins: " + win);
                                    player.sendMessage(red + "" + italic + "  Losses: " + lose);
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "   W/L: " + wl);
                                    if (plugin.isPlayerInGame(givenPlayer)) {
                                        if (!plugin.inGameKills.containsKey(uuid1)) {
                                            plugin.inGameKills.put(uuid1, 0);
                                        }
                                        if (!plugin.inGameDeaths.containsKey(uuid1)) {
                                            plugin.inGameDeaths.put(uuid1, 0);
                                        }
                                        String kills = String.valueOf(plugin.inGameKills.get(uuid1));
                                        String death = String.valueOf(plugin.inGameDeaths.get(uuid1));
                                        int deaths1 = plugin.inGameDeaths.get(uuid1);
                                        int kills1 = plugin.inGameKills.get(uuid1);
                                        float KD1 = (float) kills1 / deaths1;
                                        String[] list1 = String.valueOf(KD1).split("");
                                        String first1 = list1[0];
                                        String middle1 = list1[1];
                                        String last1 = list1[2];
                                        String finalString1 = first1 + middle1 + last1;
                                        player.sendMessage(message);
                                        player.sendMessage(red + "" + italic + s3 + kills);
                                        player.sendMessage(red + "" + italic + s2 + death);
                                        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + s4 + finalString1);
                                    }
                                    player.sendMessage(aqua + "" + bold + "<--====-----====-->");
                                }
                            } else {
                                player.sendMessage(ERROR + "This player is not found.");
                            }
                        } else {
                            String theUUID = String.valueOf(uuid);
                            String kills12 = String.valueOf(plugin.mainConfig.getIntKill(theUUID));
                            String death12 = String.valueOf(plugin.mainConfig.getIntDeath(theUUID));
                            int deaths11 = plugin.mainConfig.getIntDeath(theUUID);
                            int kills11 = plugin.mainConfig.getIntKill(theUUID);
                            float KD11 = (float) kills11 / deaths11;
                            String[] list11 = String.valueOf(KD11).split("");
                            String first11 = list11[0];
                            String middle11 = list11[1];
                            String last11 = list11[2];
                            String finalString11 = first11 + middle11 + last11;
                            player.sendMessage(aqua + "" + bold + "<--=={ " + gold + "" + bold + "Your Stats " + aqua + "" + bold + "}==-->");
                            player.sendMessage(red + "" + italic + s3 + kills12);
                            player.sendMessage(red + "" + italic + s2 + death12);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + s4 + finalString11);
                            String win = String.valueOf(plugin.mainConfig.getIntWin(theUUID));
                            String lose = String.valueOf(plugin.mainConfig.getIntLose(theUUID));
                            int win1 = plugin.mainConfig.getIntWin(theUUID);
                            int lose1 = plugin.mainConfig.getIntLose(theUUID);
                            float WL = (float) win1 / lose1;
                            String[] listw = String.valueOf(WL).split("");
                            String one = listw[0];
                            String two = listw[1];
                            String three = listw[2];
                            String wl = one + two + three;
                            player.sendMessage("");
                            player.sendMessage(green + "" + italic + "  Wins: " + win);
                            player.sendMessage(red + "" + italic + "  Losses: " + lose);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + "   W/L: " + wl);
                            if (plugin.isPlayerInGame(player)) {
                                if (!plugin.inGameKills.containsKey(uuid)) {
                                    plugin.inGameKills.put(uuid, 0);
                                }
                                if (!plugin.inGameDeaths.containsKey(uuid)) {
                                    plugin.inGameDeaths.put(uuid, 0);
                                }
                                String kills = String.valueOf(plugin.inGameKills.get(uuid));
                                String death = String.valueOf(plugin.inGameDeaths.get(uuid));
                                int deaths1 = plugin.inGameDeaths.get(uuid);
                                int kills1 = plugin.inGameKills.get(uuid);
                                float KD1 = (float) kills1 / deaths1;
                                String[] list1 = String.valueOf(KD1).split("");
                                String first1 = list1[0];
                                String middle1 = list1[1];
                                String last1 = list1[2];
                                String finalString1 = first1 + middle1 + last1;
                                player.sendMessage(message);
                                player.sendMessage(red + "" + italic + s3 + kills);
                                player.sendMessage(red + "" + italic + s2 + death);
                                player.sendMessage(ChatColor.LIGHT_PURPLE + "" + italic + s4 + finalString1);
                            }
                            player.sendMessage(aqua + "" + bold + "<--====-----====-->");
                        }
                    } else {
                        player.sendMessage(WARN + s + red + "" + italic + "foodm.stats " + yellow + "" + italic + s1);
                    }
                } else if (args.length >= 1 && args[0].equalsIgnoreCase("set")) {
                    // Sets the values of the minigame
                    if (player.hasPermission("foodm.set")) {
                        // /fm set idk
                        //      1   2
                        if (args.length > 1) {
                            plugin.setSubCommand.setGameOptions(args, player);
                        } else {
                            plugin.helpMenu.helpAll(player);
                        }
                    } else {
                        player.sendMessage(WARN + s + red + "" + italic + "foodm.set " + yellow + "" + italic + s1);
                    }
                } else if (args.length >= 2 && args[0].equalsIgnoreCase("default") && args[1].equalsIgnoreCase("confirm")) {
                    // Sets the config to default
                    if (player.hasPermission("foodm.default")) {
                        plugin.mainConfig.setDefaultValues();
                        player.sendMessage(NORMAL + "The config values have been set to default.");
                    } else {
                        player.sendMessage(WARN + "You need " + red + "" + italic + "foodm.default " + yellow + "" + italic + "permission to do this.");
                    }
                } else if (args.length >= 1 && args[0].equalsIgnoreCase("start")) {
                    // It starts the FIGHT!
                    if (player.hasPermission("foodm.start")) {
                        if (args.length > 1) {
                            plugin.startCommand.mainStart(player, args);
                        } else {
                            plugin.helpMenu.helpAll(player);
                        }
                    } else {
                        player.sendMessage(WARN + "You are not the chosen one. You don't have " + red + "" + italic + "foodm.start " + yellow + "" + italic + s1);
                    }
                } else if (args.length >= 1 && args[0].equalsIgnoreCase("end")) {
                    // It ends the fight
                    if (player.hasPermission("foodm.end")) {
                        // /fm end optional: player
                        //      1             2        num
                        //      0             1        index
                        Location endLoc = plugin.mainConfig.getLocationMain("end_location");
                        if (args.length > 1) {
                            Player player1 = Bukkit.getPlayer(args[1]);
                            if (player1 != null) {
                                if (player1.isOnline()) {
                                    if (plugin.isPlayerPlayingPvE(player1)) {
                                        if (plugin.isPlayerInGroup(player1)) {
                                            for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player1)) {
                                                if (uuid != null) {
                                                    Player player2 = Bukkit.getPlayer(uuid);
                                                    if (player2 != null) {
                                                        player2.sendMessage(INFO + "The game was ended by "+ gold+""+italic+""+player.getName());
                                                    }
                                                }
                                            }
                                        } else {
                                            player1.sendMessage(INFO + "The game was ended by "+ gold+""+italic+""+player.getName());
                                        }
                                        plugin.endTheGame.endThePvE(player1);
                                    } else if (plugin.isPlayerChooseToPlayPvE(player1)) {
                                        if (plugin.isPlayerInGroup(player)) {
                                            for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player)) {
                                                if (uuid != null) {
                                                    Player players = Bukkit.getPlayer(uuid);
                                                    if (players != null) {
                                                        plugin.removePlayerFromWaitLobby.removePlayerFromWaitedLobby(players);
                                                        if (endLoc != null) {
                                                            players.teleport(endLoc);
                                                            players.sendMessage(INFO+"You are removed from the waiting lobby");
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            plugin.removePlayerFromWaitLobby.removePlayerFromWaitedLobby(player);
                                            if (endLoc != null) {
                                                player.teleport(endLoc);
                                                player.sendMessage(INFO+"You are removed from the waiting lobby");
                                            }
                                        }
                                    } else {
                                        if (plugin.isPlayerInGroup(player1)) {
                                            if (plugin.isPlayerInGame(player1)) {
                                                for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player1)) {
                                                    if (uuid != null) {
                                                        Player players = Bukkit.getPlayer(uuid);
                                                        if (players != null) {
                                                            plugin.endTheGame.endTheGameWithStatus(players);
                                                        }
                                                    }
                                                }
                                                for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player1)) {
                                                    if (uuid != null) {
                                                        plugin.inGameKills.remove(uuid);
                                                        plugin.inGameDeaths.remove(uuid);
                                                        plugin.playersInBlueTeams.remove(uuid);
                                                        plugin.playersInCyanTeams.remove(uuid);
                                                        plugin.playersInRedTeams.remove(uuid);
                                                        plugin.playersInGreenTeams.remove(uuid);
                                                        plugin.playersInYellowTeams.remove(uuid);
                                                        plugin.playersRandomTeam.remove(uuid);
                                                        plugin.playersChoiceFoodGame.remove(uuid);
                                                        plugin.playersInFoodGame.remove(uuid);
                                                        plugin.FoodGameWinner.remove(uuid);
                                                    }
                                                }
                                                for (UUID uuid : plugin.winners) {
                                                    if (uuid != null) {
                                                        Player player11 = Bukkit.getPlayer(uuid);
                                                        if (player11 != null) {
                                                            plugin.givePlayerWin(player11);
                                                        }
                                                    }
                                                }
                                                for (UUID uuid : plugin.losses) {
                                                    if (uuid != null) {
                                                        Player player11 = Bukkit.getPlayer(uuid);
                                                        if (player11 != null) {
                                                            plugin.givePlayerLose(player11);
                                                        }
                                                    }
                                                }
                                                plugin.winners.clear();
                                                plugin.losses.clear();
                                                player.sendMessage(NORMAL + "" + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " just ended your game.");
                                            } else {
                                                player.sendMessage(WARN + "" + red + "" + italic + "" + player1.getName() + "" + yellow + "" + italic + " is not in game.");
                                            }
                                        } else {
                                            player.sendMessage(WARN + "" + red + "" + italic + "" + player1.getName() + "" + yellow + "" + italic + " is not in a group.");
                                        }
                                    }
                                } else {
                                    player.sendMessage(WARN + "" + red + "" + italic + "" + player1.getName() + "" + yellow + "" + italic + " is not Online.");
                                }
                            } else {
                                player.sendMessage(ERROR + "This player can't be found.");
                            }
                        } else {
                            if (plugin.isPlayerPlayingPvE(player)) {
                                if (plugin.isPlayerInGroup(player)) {
                                    for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player)) {
                                        if (uuid != null) {
                                            Player player2 = Bukkit.getPlayer(uuid);
                                            if (player2 != null) {
                                                player2.sendMessage(INFO + "The game was ended by " + gold + "" + italic + "" + player.getName());
                                            }
                                        }
                                    }
                                } else {
                                    player.sendMessage(INFO + "The game was ended by " + gold + "" + italic + "" + player.getName());
                                }
                                plugin.endTheGame.endThePvE(player);
                            } else if (plugin.isPlayerChooseToPlayPvE(player)) {
                                if (plugin.isPlayerInGroup(player)) {
                                    for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player)) {
                                        if (uuid != null) {
                                            Player players = Bukkit.getPlayer(uuid);
                                            if (players != null) {
                                                plugin.removePlayerFromWaitLobby.removePlayerFromWaitedLobby(players);
                                                if (endLoc != null) {
                                                    players.teleport(endLoc);
                                                    players.sendMessage(INFO+"You are removed from the waiting lobby");
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    plugin.removePlayerFromWaitLobby.removePlayerFromWaitedLobby(player);
                                    if (endLoc != null) {
                                        player.teleport(endLoc);
                                        player.sendMessage(INFO+"You are removed from the waiting lobby");
                                    }
                                }
                            } else {
                                if (plugin.isPlayerInGroup(player)) {
                                    for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player)) {
                                        if (uuid != null) {
                                            Player players = Bukkit.getPlayer(uuid);
                                            if (players != null) {
                                                plugin.endTheGame.endTheGameWithStatus(players);
                                            }
                                        }
                                    }
                                    for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player)) {
                                        if (uuid != null) {
                                            plugin.inGameKills.remove(uuid);
                                            plugin.inGameDeaths.remove(uuid);
                                            plugin.playersInBlueTeams.remove(uuid);
                                            plugin.playersInCyanTeams.remove(uuid);
                                            plugin.playersInRedTeams.remove(uuid);
                                            plugin.playersInGreenTeams.remove(uuid);
                                            plugin.playersInYellowTeams.remove(uuid);
                                            plugin.playersRandomTeam.remove(uuid);
                                            plugin.playersChoiceFoodGame.remove(uuid);
                                            plugin.playersInFoodGame.remove(uuid);
                                            plugin.FoodGameWinner.remove(uuid);
                                        }
                                    }
                                    for (UUID uuid : plugin.winners) {
                                        if (uuid != null) {
                                            Player player11 = Bukkit.getPlayer(uuid);
                                            if (player11 != null) {
                                                plugin.givePlayerWin(player11);
                                            }
                                        }
                                    }
                                    for (UUID uuid : plugin.losses) {
                                        if (uuid != null) {
                                            Player player11 = Bukkit.getPlayer(uuid);
                                            if (player11 != null) {
                                                plugin.givePlayerLose(player11);
                                            }
                                        }
                                    }
                                    plugin.winners.clear();
                                    plugin.losses.clear();
                                }
                            }
                        }
                    } else {
                        player.sendMessage(WARN + "You can't end the game. You need " + red + "" + italic + "foodm.end " + yellow + "" + italic + "permission to do this.");
                    }
                } else if (args.length >= 1 && args[0].equalsIgnoreCase("kick")) {
                    // it kicks players from the game/waiting lobby
                    if (player.hasPermission("foodm.kick")) {
                        if (args.length > 1) {
                            plugin.kickPlayerFromGame.kickPlayerFromGame(args, player);
                        } else {
                            plugin.helpMenu.helpAll(player);
                        }
                    } else {
                        player.sendMessage(WARN + "You can't kick players without " + red + "" + italic + "foodm.kick " + yellow + "" + italic + s1);
                    }
                } else if (args.length >= 1 && args[0].equalsIgnoreCase("group")) {
                    if (player.hasPermission("foodm.group")) {
                        if (args.length > 1) {
                            plugin.groupMain.GroupMain(player, args);
                        } else {
                            plugin.helpMenu.helpGroupMenu(player);
                        }
                    } else {
                        player.sendMessage(WARN + "You can't use groups without " + red + "" + italic + "foodm.group " + yellow + "" + italic + s1);
                    }
                } else if (args.length >= 1 && args[0].equalsIgnoreCase("rejoin")) {
                    if (player.hasPermission("foodm.rejoin")) {
                        plugin.rejoin.rejoinCommand(player);
                    } else {
                        player.sendMessage(WARN + s + red + "" + italic + "foodm.rejoin " + yellow + "" + italic + s1);
                    }
                } else if (args.length >= 1 && args[0].equalsIgnoreCase("reset")) {
                    if (player.hasPermission("foodm.reset")) {
                        // fm reset [player]
                        //      1     2      size
                        //       0     1     index
                        if (args.length > 1) {
                            if (player.hasPermission("foodm.staff")) {
                                plugin.resetPlayer.resetPlayer(player, args);
                            } else {
                                player.sendMessage(WARN + "You are not a staff member and you are not allowed to use this command!");
                            }
                        } else {
                            plugin.resetPlayer.resetPlayer(player);
                        }
                    } else {
                        player.sendMessage(WARN + s + red + "" + italic + "foodm.reset " + yellow + "" + italic + "permission");
                    }
                } else {
                    // help menu
                    if (player.hasPermission("foodm.help")) {
                        plugin.helpMenu.helpAll(player);
                    } else {
                        player.sendMessage(message1);
                    }
                }
            } else {
                player.sendMessage(WARN + s + red + "" + italic + "foodm.commands " + yellow + "" + italic + "permission to play this game.");
            }
        }
        return true;
    }
}