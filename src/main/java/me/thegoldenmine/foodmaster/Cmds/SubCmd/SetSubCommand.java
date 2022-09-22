package me.thegoldenmine.foodmaster.Cmds.SubCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SetSubCommand {
    private final FoodMaster plugin;

    public SetSubCommand(FoodMaster plugin) {
        this.plugin = plugin;
    }

    public void setGameOptions(String[] args, Player player) {
        ChatColor darkGray = ChatColor.GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String su;
        if (plugin.mainConfig.getStrMain("name") != null) {
            su = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            su = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + su + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + su + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + su + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + su + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        String s = "You have to specify ";
        String message1 = ERROR + s + green + "" + italic + "ON " + red + "" + italic + "or " + ChatColor.DARK_RED + "" + italic + "OFF";
        String message = message1;
        if (player != null) {
            // /fm set idk
            //      1   2
            if (args[1].equalsIgnoreCase("game-time")) {
                // set the timer
                if (args.length == 3) {
                    try {
                        String timeStr = args[2];
                        int time;
                        time = Integer.parseInt(timeStr);
                        int oldTime = plugin.mainConfig.getIntGame("game_time_seconds");
                        plugin.mainConfig.setIntGame("game_time_seconds", time);
                        for (String name : plugin.timer.keySet()) {
                            if (name != null && plugin.timer.get(name) == oldTime) {
                                plugin.timer.put(name, time);
                            }
                        }
                        player.sendMessage(NORMAL + "You have set game time to " + gold + "" + italic + "" + time + "" + green + "" + italic + " seconds. " + gold + "" + "" + italic + "" + plugin.getTime(time));
                    } catch (NumberFormatException e) {
                        player.sendMessage(ERROR + "You can only enter numbers.");
                    }
                } else {
                    player.sendMessage(WARN + "" + ChatColor.DARK_PURPLE + "" + italic + "/fm set game-time " + gold + "" + italic + "<seconds> " + yellow + "" + italic + ".");
                }
            } else if (args[1].equalsIgnoreCase("fall-damage")) {
                // enable_fall_damage
                // /fm set fall-damage [game/waiting-lobby/group] [on/off]
                //      0       1             2                3        index
                //      1       2             3                4          num
                if (args.length == 4) {
                    String game = args[2];
                    String mode = args[3];
                    if (game.equalsIgnoreCase("game")) {
                        if (mode.equalsIgnoreCase("on")) {
                            plugin.mainConfig.setBooleanGame("enable_fall_damage", true);
                            player.sendMessage(NORMAL + "Players will take fall damage during a game.");
                        } else if (mode.equalsIgnoreCase("off")) {
                            plugin.mainConfig.setBooleanGame("enable_fall_damage", false);
                            player.sendMessage(NORMAL + "Players won't take fall damage during a game.");
                        }
                    } else if (game.equalsIgnoreCase("waiting-lobby")) {
                        if (mode.equalsIgnoreCase("on")) {
                            plugin.mainConfig.setBooleanWaitLobby("enable_fall_damage", true);
                            player.sendMessage(NORMAL + "Players will take fall damage in waiting lobby.");
                        } else if (mode.equalsIgnoreCase("off")) {
                            plugin.mainConfig.setBooleanWaitLobby("enable_fall_damage", false);
                            player.sendMessage(NORMAL + "Players won't take fall damage in waiting lobby.");
                        }
                    } else if (game.equalsIgnoreCase("group")) {
                        if (mode.equalsIgnoreCase("on")) {
                            plugin.mainConfig.setBooleanMain("enable_fall_damage", true);
                            player.sendMessage(NORMAL + "Players will take fall damage when they are in group.");
                        } else if (mode.equalsIgnoreCase("off")) {
                            plugin.mainConfig.setBooleanMain("enable_fall_damage", false);
                            player.sendMessage(NORMAL + "Players won't take fall damage when they are in group.");
                        }
                    }
                } else {
                    player.sendMessage(INFO + "Try with " + ChatColor.DARK_PURPLE + "" + italic + "/fm set fall-damage " + gold + "" + italic + "[game/waiting-lobby/group] [on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "Removes the fall game.");
                }
            } else if (args[1].equalsIgnoreCase("riding")) {
                // /fm set riding [pve-bosses/lobby-players] [on/off]
                //      0     1                2                 3     index
                //      1     2                3                 4        num
                if (args.length >= 4) {
                    String type = args[2];
                    String mode = args[3];
                    if (type != null && mode != null) {
                        if (type.equalsIgnoreCase("pve-bosses")) {
                            if (mode.equalsIgnoreCase("on")) {
                                plugin.mainConfig.setBooleanPvE("ride_pve_bosses", true);
                                player.sendMessage(NORMAL+"Players can ride the PvE bosses.");
                            } else if (mode.equalsIgnoreCase("off")) {
                                plugin.mainConfig.setBooleanPvE("ride_pve_bosses", false);
                                player.sendMessage(NORMAL+"Players can't ride the PvE bosses.");
                            }
                        } else if (type.equalsIgnoreCase("lobby-players")) {
                            if (mode.equalsIgnoreCase("on")) {
                                plugin.mainConfig.setBooleanWaitLobby("ride_players", true);
                                player.sendMessage(NORMAL+"Players can ride other players in the waiting lobby.");
                            } else if (mode.equalsIgnoreCase("off")) {
                                plugin.mainConfig.setBooleanWaitLobby("ride_players", false);
                                player.sendMessage(NORMAL+"Players can't ride other players in the waiting lobby.");
                            }
                        }
                    }
                } else {
                    player.sendMessage(INFO+"/fm set riding " + gold + "" + italic + "[pve-bosses/lobby-players] [on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can allow the player to ride the PvE bosses and players in the waiting lobby or not.");
                }
            } else if (args[1].equalsIgnoreCase("pve-respawn-player")) {
                // respawm_player
                if (args.length >= 3) {
                    String mode = args[2];
                    if (mode != null) {
                        if (mode.equalsIgnoreCase("on")) {
                            plugin.mainConfig.setBooleanPvE("respawm_player", true);
                            player.sendMessage(NORMAL + "Now the player will respawn.");
                        } else if (mode.equalsIgnoreCase("off")) {
                            plugin.mainConfig.setBooleanPvE("respawm_player", false);
                            player.sendMessage(NORMAL + "Now the player wont respawn.");
                        }
                    } else {
                        player.sendMessage(INFO+"/fm pve-respawn-player " + gold + "" + italic + "[on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "This sets whether the player will respawn or not.");
                    }
                } else {
                    player.sendMessage(INFO+"/fm set pve-respawn-player " + gold + "" + italic + "[on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "This sets whether the player will respawn or not.");
                }
            } else if (args[1].equalsIgnoreCase("pve-boss-spawn")) {
                // /fm set pve-boss-spawn [game name]
                //     0          1            2       index
                //     1         2             3          num
                if (args.length >= 3) {
                    String gameName = args[2];
                    if (gameName != null && !plugin.playersInGame.keySet().isEmpty() && plugin.playersInGame.containsKey(gameName)) {
                        plugin.mainConfig.setLocationPvE("pve_boss_spawn-"+gameName, player.getLocation());
                        player.sendMessage(NORMAL+"You have set your location as a PvE boss spawn point at "+gold+""+gameName+""+green+" game.");
                    } else {
                        player.sendMessage(INFO+"/fm set pve-boss-spawn " + gold + "" + italic + "[game name] " + red + "" + bold + ">- " + darkGray + "" + italic + "This sets the location of the boss to spawn for the game.");
                    }
                } else {
                    player.sendMessage(INFO+"/fm set pve-boss-spawn " + gold + "" + italic + "[game name] " + red + "" + bold + ">- " + darkGray + "" + italic + "This sets the location of the boss to spawn for the game.");
                }
            } else if (args[1].equalsIgnoreCase("hungry")) {
                // hungry_during_a_game
                // /fm set hungry while-waiting/during-game/in-group on/off
                //      0     1                      2               3   index
                //      1     2                      3               4     num
                if (args.length == 4) {
                    String mode = args[3];
                    String game = args[2];
                    if (game.equalsIgnoreCase("during-game")) {
                        if (mode.equalsIgnoreCase("on")) {
                            // be hungry
                            plugin.mainConfig.setBooleanGame("hungry_during_a_game", true);
                            player.sendMessage(NORMAL + "Now players will be hungry during a game.");
                        } else if (mode.equalsIgnoreCase("off")) {
                            plugin.mainConfig.setBooleanGame("hungry_during_a_game", false);
                            player.sendMessage(NORMAL + "Now players will have infinite food and they won't be hungry during a game.");
                        }
                    } else if (game.equalsIgnoreCase("while-waiting")) {
                        if (mode.equalsIgnoreCase("on")) {
                            // be hungry
                            plugin.mainConfig.setBooleanWaitLobby("hungry_while_waiting", true);
                            player.sendMessage(NORMAL + "Now players will be hungry while waiting in the waiting lobby.");
                        } else if (mode.equalsIgnoreCase("off")) {
                            plugin.mainConfig.setBooleanWaitLobby("hungry_while_waiting", false);
                            player.sendMessage(NORMAL + "Now players will have infinite food and they won't be hungry while waiting in the waiting lobby.");
                        }
                    } else if (game.equalsIgnoreCase("in-group")) {
                        if (mode.equalsIgnoreCase("on")) {
                            // be hungry
                            plugin.mainConfig.setBooleanMain("hungry_in_group", true);
                            player.sendMessage(NORMAL + "Now players will be hungry when they are in group.");
                        } else if (mode.equalsIgnoreCase("off")) {
                            plugin.mainConfig.setBooleanMain("hungry_in_group", false);
                            player.sendMessage(NORMAL + "Now players will have infinite food and they won't be hungry when they are in group.");
                        }
                    }
                } else {
                    player.sendMessage(INFO + "Try with " + ChatColor.DARK_PURPLE + "" + italic + "/fm set hungry " + gold + "" + italic + "[while-waiting/during-game/in-group] [on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can decide if the players will get hungry.");
                }
            } else if (args[1].equalsIgnoreCase("respawn")) {
                if (args.length == 2) {
                    player.sendMessage(ERROR + "You have to enter a game mode name: " + gold + "" + italic + "team-deathmatch" + darkGray + "" + italic + " / " + gold + "" + italic + "free-for-all" + red + "" + italic + ".");
                } else {
                    if (args.length == 3) {
                        player.sendMessage(ERROR + "You have to set respawn to " + green + "" + italic + "ON" + red + "" + italic + " or " + ChatColor.DARK_RED + "" + italic + "OFF" + red + "" + italic + ".");
                    } else {
                        String game = args[2];
                        String mode = args[3];
                        if (game.equalsIgnoreCase("team-deathmatch")) {
                            if (mode.equalsIgnoreCase("off")) {
                                plugin.mainConfig.setBooleanGame("respawn_team_deathmatch", false);
                                player.sendMessage(INFO + "You have removed the respawn from " + gold + "" + italic + "team-deathmatch" + aqua + "" + italic + ".");
                            } else if (mode.equalsIgnoreCase("on")) {
                                plugin.mainConfig.setBooleanGame("respawn_team_deathmatch", true);
                                player.sendMessage(INFO + "You have added the respawn from " + gold + "" + italic + "team-deathmatch" + aqua + "" + italic + ".");
                            }
                        } else if (game.equalsIgnoreCase("free-for-all")) {
                            if (mode.equalsIgnoreCase("off")) {
                                plugin.mainConfig.setBooleanGame("respawn_free-for-all", false);
                                player.sendMessage(INFO + "You have removed the respawn from " + gold + "" + italic + "free-for-all" + aqua + "" + italic + ".");
                            } else if (mode.equalsIgnoreCase("on")) {
                                plugin.mainConfig.setBooleanGame("respawn_free-for-all", true);
                                player.sendMessage(INFO + "You have added the respawn from " + gold + "" + italic + "free-for-all" + aqua + "" + italic + ".");
                            }
                        }
                    }
                }
            } else if (args[1].equalsIgnoreCase("friendly-fire")) {
                // /fw set friendly-fire on/off
                //      0        1          2        index
                //      1        2          3         num
                if (args.length == 3) {
                    String mode = args[2];
                    if (mode.equalsIgnoreCase("on")) {
                        plugin.mainConfig.setBooleanGame("friendly-fire", true);
                        player.sendMessage(INFO + "You have turned " + gold + "" + italic + "friendly-fire " + green + "" + italic + "ON" + aqua + "" + italic + ". Players will take damage from their team now.");
                    } else if (mode.equalsIgnoreCase("off")) {
                        plugin.mainConfig.setBooleanGame("friendly-fire", false);
                        player.sendMessage(INFO + "You have turned " + gold + "" + italic + "friendly-fire " + red + "" + italic + "OFF" + aqua + "" + italic + ". Players won't take damage from their team now.");
                    }
                } else {
                    player.sendMessage(ERROR + "Try to use: " + ChatColor.DARK_PURPLE + "" + italic + "" + "/fm set friendly-fire " + gold + "" + italic + "[on or off] " + red + "" + bold + ">- " + darkGray + "" + italic + "If you want to take damage from your team then set it to ON or set it to OFF if you don't want to take damage.");
                }
            } else if (args[1].equalsIgnoreCase("end")) {
                // when the game ends. All players will be teleported to this location
                Location playerLoc = player.getLocation();
                plugin.mainConfig.setLocationMain("end_location", playerLoc);
                player.sendMessage(NORMAL + "Your location is set as final location.");
            } else if (args[1].equalsIgnoreCase("max-players-in-waiting-lobby")) {
                if (args.length == 3) {
                    try {
                        int maxPlayer = Integer.parseInt(args[2]);
                        plugin.mainConfig.setIntWaitLobby("max-players_in_waiting_lobby", maxPlayer);
                        player.sendMessage(NORMAL + "The max amount of players in waiting lobby is " + gold + "" + italic + "" + maxPlayer + "" + green + "" + italic + " .");
                    } catch (NumberFormatException e) {
                        player.sendMessage(WARN + "You can only enter numbers!");
                    }
                } else {
                    player.sendMessage(INFO + "" + ChatColor.DARK_PURPLE + "" + italic + "/fm set max-players-in-waiting-lobby " + gold + "" + italic + "[amount] " + red + "" + bold + ">- " + darkGray + "" + italic + "Sets the max players in one group.");
                }
            } else if (args[1].equalsIgnoreCase("wait-spawn-point")) {
                if (args.length == 3) {
                    if (args[2] != null) {
                        String nameW = args[2];
                        Set<UUID> empty = new HashSet<>();
                        plugin.playersInWaitingLobby.put(nameW, empty);
                        Location loc = player.getLocation();
                        plugin.mainConfig.setLocationWaitLobby(nameW + "->wait-location", loc);
                        int x = plugin.mainConfig.getIntWaitLobby("number_of_wait-lobbies");
                        x += 1;
                        plugin.mainConfig.setIntWaitLobby("number_of_wait-lobbies", x);
                        plugin.mainConfig.setStrWaitLobby(x + "-one_of_the_names", nameW);
                        player.sendMessage(NORMAL + "You have set your location as a waiting lobby with the name " + gold + "" + italic + "" + nameW + "" + green + "" + italic + " .");
                    }
                } else {
                    player.sendMessage(INFO + "" + ChatColor.DARK_PURPLE + "" + italic + "/fm set wait-spawn-point " + gold + "" + italic + "[location name] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can set unlimited locations as waiting lobby locations and you can call them whatever you want.");
                }
            } else if (args[1].equalsIgnoreCase("max-players-in-group")) {
                //  path "max-players_in_group"
                // /fw set max-players-in-group [number]
                //     1          2                 3             number
                //     0            1               2             index
                if (args.length == 3) {
                    try {
                        int players = Integer.parseInt(args[2]);
                        plugin.mainConfig.setIntMain("max-players_in_group", players);
                        player.sendMessage(NORMAL + "You have set the max players allowed in one group to " + gold + "" + italic + "" + players + "" + green + "" + italic + "" + " .");
                    } catch (Exception e) {
                        player.sendMessage(WARN + "You must provide a number. No letters are allowed!");
                    }
                } else {
                    player.sendMessage(INFO + "" + ChatColor.DARK_PURPLE + "" + italic + "/fm set max-players-in-group " + gold + "" + italic + "[number] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can set the max number of players allowed in one group.");
                }
            } else if (args[1].equalsIgnoreCase("damage")) {
                if (args.length > 2) {
                    String damageStr = args[2];
                    try {
                        int damage = Integer.parseInt(damageStr);
                        plugin.mainConfig.setIntGame("damage", damage);
                        player.sendMessage(INFO + "You have set the damage to " + damageStr);
                    } catch (Exception e) {
                        player.sendMessage(ERROR + "You can only enter numbers");
                    }
                } else {
                    player.sendMessage(ERROR + "You have to specify the amount of damage dealt with a number.");
                }
            } else if (args[1].equalsIgnoreCase("friendly-damage")) {
                if (args.length > 2) {
                    String damageStr = args[2];
                    try {
                        int damage = Integer.parseInt(damageStr);
                        plugin.mainConfig.setIntGame("friendly_damage", damage);
                        player.sendMessage(INFO + "You have set the friendly fire damage to " + damageStr);
                    } catch (Exception e) {
                        player.sendMessage(ERROR + "You have to enter a number.");
                    }
                } else {
                    player.sendMessage(ERROR + "You have to specify the amount of friendly fire damage with a number.");
                }
            } else if (args[1].equalsIgnoreCase("game_timer_color")) {
                // /fm set game-timer-color color
                //     1           2         3   num
                //     0           1         2    index
                if (args.length >= 3) {
                    plugin.mainConfig.setStrGame("game_timer_color", args[2]);
                    player.sendMessage(NORMAL +""+ args[2] +""+ plugin.getTime(plugin.mainConfig.getIntGame("game_time_seconds")));
                } else {
                    player.sendMessage(ERROR+"/fm set game-timer-color "+gold+""+italic+"[color] "+red+""+bold+">- "+darkGray+""+italic+"Sets the color of the timer.");
                }
            } else if (args[1].equalsIgnoreCase("game-spawn-point")) {
                // subcommand "game-spawn-point"
                //  /fw set game-spawn-point [gameName] [name of location]  get player location
                //       0        1                2          3                    index
                //       1        2                 3         4
                if (args.length == 3) {
                    player.sendMessage(INFO + "" + ChatColor.DARK_PURPLE + "" + italic + "/fm set game-spawn-point " + gold + "" + italic + "[game name] [location name] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can set unlimited spawn points for players.");
                } else if (args.length == 4) {
                    String givenGameName = args[2];
                    String givenNameOfLocation = args[3];
                    if (givenGameName.equals(givenNameOfLocation)) {
                        player.sendMessage(ERROR+"The game name must not be the same as the location name.");
                        return;
                    }
                    Location playerLoc = player.getLocation();
                    int x = plugin.mainConfig.getIntGame("number_of_game_names");
                    int w = plugin.mainConfig.getIntGame("number_of_game_location_names");
                    if (plugin.GameSpawnPoints.isEmpty() || !plugin.GameSpawnPoints.containsKey(givenGameName)) {
                        x = x + 1;
                        plugin.mainConfig.setStrGame(x + "-one_of_the_game_names", givenGameName);
                        plugin.mainConfig.setIntGame("number_of_game_names", x);
                        w = w + 1;
                        plugin.mainConfig.setStrGame(w + "-one_of_the_game_location_names-" + givenGameName, givenNameOfLocation);
                        plugin.mainConfig.setIntGame("number_of_game_location_names", w);
                        Set<String> namesOfLoc = new HashSet<>();
                        namesOfLoc.add(givenNameOfLocation);
                        plugin.GameSpawnPoints.put(givenGameName, namesOfLoc);
                        if (!plugin.timer.containsKey(givenGameName)) {
                            int time = plugin.mainConfig.getIntGame("game_time_seconds");
                            plugin.timer.put(givenGameName, time);
                        }
                        if (!plugin.playersInGame.containsKey(givenGameName)) {
                            Set<UUID> just = new HashSet<>();
                            plugin.playersInGame.put(givenGameName, just);
                        }
                    } else {
                        if (!plugin.GameSpawnPoints.containsKey(givenGameName)) {
                            x = x + 1;
                            plugin.mainConfig.setStrGame(x + "-one_of_the_game_names", givenGameName);
                            plugin.mainConfig.setIntGame("number_of_game_names", x);
                        }
                        if (!plugin.GameSpawnPoints.get(givenGameName).contains(givenNameOfLocation)) {
                            w = w + 1;
                            plugin.mainConfig.setStrGame(w + "-one_of_the_game_location_names-" + givenGameName, givenNameOfLocation);
                            plugin.mainConfig.setIntGame("number_of_game_location_names", w);
                        }
                        if (plugin.GameSpawnPoints.get(givenGameName) == null) {
                            Set<String> namesOfLoc = new HashSet<>();
                            namesOfLoc.add(givenNameOfLocation);
                            plugin.GameSpawnPoints.put(givenGameName, namesOfLoc);
                            if (!plugin.timer.containsKey(givenGameName)) {
                                int time = plugin.mainConfig.getIntGame("game_time_seconds");
                                plugin.timer.put(givenGameName, time);
                            }
                        } else {
                            Set<String> names = plugin.GameSpawnPoints.get(givenGameName);
                            names.add(givenNameOfLocation);
                            plugin.GameSpawnPoints.put(givenGameName, names);
                            if (!plugin.timer.containsKey(givenGameName)) {
                                int time = plugin.mainConfig.getIntGame("game_time_seconds");
                                plugin.timer.put(givenGameName, time);
                            }
                        }
                        if (!plugin.playersInGame.containsKey(givenGameName)) {
                            Set<UUID> just = new HashSet<>();
                            plugin.playersInGame.put(givenGameName, just);
                        }
                    }
                    plugin.mainConfig.setLocationGame(givenGameName + "->" + givenNameOfLocation + "-spawn-point", playerLoc);
                    plugin.mainConfig.saveGame();
                    plugin.mainConfig.reloadGame();
                    player.sendMessage(NORMAL + "You have set " + gold + "" + italic + "" + givenNameOfLocation + "" + green + "" + italic + " as spawn point location. " + gold + "" + italic + "" + givenGameName + "" + green + "" + italic + "  has one more spawn point.");
                } else {
                    player.sendMessage(INFO + "You are adding unnecessary characters to this command. " + ChatColor.DARK_PURPLE + "" + italic + "/fm set game-spawn-point " + gold + "" + italic + "[game name] [location name] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can set unlimited spawn points for players to be teleported when the game starts or on respawn.");
                }
            } else if (args[1].equalsIgnoreCase("group-player-break-blocks")) {
                // group_player_break_blocks
                // /fm set group-player-break-blocks on/off
                //      0              1               2     index
                //      1              2               3      num
                if (args.length == 2) {
                    player.sendMessage(ERROR + s + green + "" + italic + "ON" + red + "" + italic + " or " + ChatColor.DARK_RED + "" + italic + "OFF");
                } else {
                    String mode = args[2];
                    if (mode.equalsIgnoreCase("on")) {
                        plugin.mainConfig.setBooleanMain("group_player_break_blocks", true);
                        player.sendMessage(NORMAL + "Players in groups can now break blocks before the game starts.");
                    } else if (mode.equalsIgnoreCase("off")) {
                        plugin.mainConfig.setBooleanMain("group_player_break_blocks", false);
                        player.sendMessage(NORMAL + "Players in groups cannot break blocks anymore.");
                    }
                }
            } else if (args[1].equalsIgnoreCase("group-player-place-blocks")) {
                // group_player_place_blocks
                if (args.length == 2) {
                    player.sendMessage(ERROR + s);
                } else {
                    String mode = args[2];
                    if (mode.equalsIgnoreCase("on")) {
                        plugin.mainConfig.setBooleanMain("group_player_place_blocks", true);
                        player.sendMessage(NORMAL + "Players in groups can now place blocks before the game starts.");
                    } else if (mode.equalsIgnoreCase("off")) {
                        plugin.mainConfig.setBooleanMain("group_player_place_blocks", false);
                        player.sendMessage(NORMAL + "Players in groups cannot place blocks anymore.");
                    }
                }
            } else if (args[1].equalsIgnoreCase("group-player-hit-teammate")) {
                // group_player_hit_teammate
                if (args.length == 2) {
                    player.sendMessage(message1);
                } else {
                    String mode = args[2];
                    if (mode.equalsIgnoreCase("on")) {
                        plugin.mainConfig.setBooleanMain("group_player_hit_teammate", true);
                        player.sendMessage(NORMAL + "Players in groups can now attack their teammates.");
                    } else if (mode.equalsIgnoreCase("off")) {
                        plugin.mainConfig.setBooleanMain("group_player_hit_teammate", false);
                        player.sendMessage(NORMAL + "Players in groups cannot attack their teammates anymore.");
                    }
                }
            } else if (args[1].equalsIgnoreCase("group-player-pickup")) {
                // group_player_pickup
                if (args.length == 2) {
                    player.sendMessage(message1);
                } else {
                    String mode = args[2];
                    if (mode.equalsIgnoreCase("on")) {
                        plugin.mainConfig.setBooleanMain("group_player_pickup", true);
                        player.sendMessage(NORMAL + "Players in groups can pickup items.");
                    } else if (mode.equalsIgnoreCase("off")) {
                        plugin.mainConfig.setBooleanMain("group_player_pickup", false);
                        player.sendMessage(NORMAL + "Players in groups cannot pickup items anymore.");
                    }
                }
            } else if (args[1].equalsIgnoreCase("enable-lives")) {
                // enable_lives
                // /fm set enable-lives gamemode mode
                //      0         1          2     3     index
                //      1         2          3     4       num
                if (args.length == 2) {
                    player.sendMessage(ERROR + "You have to enter a game mode.");
                } else {
                    if (args.length == 3) {
                        player.sendMessage(ERROR + "You have to specify ON or OFF");
                    } else {
                        String mode = args[3];
                        String game = args[2];
                        mode = mode.toLowerCase();
                        game = game.toLowerCase();
                        if (mode.equalsIgnoreCase("on")) {
                            if (game.equalsIgnoreCase("free-for-all")) {
                                plugin.mainConfig.setBooleanGame("enable_lives_free-for-all", true);
                                if (plugin.mainConfig.getBooleanGame("respawn_free-for-all")) {
                                    player.sendMessage(NORMAL + "Players now have limited lives. The amount of lives is " + plugin.mainConfig.getIntGame("lives"));
                                } else {
                                    plugin.mainConfig.setBooleanGame("respawn_free-for-all", true);
                                    player.sendMessage(NORMAL + "Respawn has been turned on automatically.");
                                }
                            } else if (game.equalsIgnoreCase("team-deathmatch")) {
                                plugin.mainConfig.setBooleanGame("enable_lives_team_deathmatch", true);
                                if (plugin.mainConfig.getBooleanGame("respawn_team_deathmatch")) {
                                    player.sendMessage(NORMAL + "Players now have limited lives. The amount of lives is " + gold + "" + italic + "" + plugin.mainConfig.getIntGame("lives"));
                                } else {
                                    plugin.mainConfig.setBooleanGame("respawn_team_deathmatch", true);
                                    player.sendMessage(NORMAL + "Respawn has been turned on automatically.");
                                }
                            }
                        } else if (mode.equalsIgnoreCase("off")) {
                            if (game.equalsIgnoreCase("free-for-all")) {
                                plugin.mainConfig.setBooleanGame("enable_lives_free-for-all", false);
                                if (plugin.mainConfig.getBooleanGame("respawn_free-for-all")) {
                                    player.sendMessage(NORMAL + "Players will now respawn until the game ends.");
                                } else {
                                    player.sendMessage(NORMAL + "Players will no longer respawn.");
                                }
                            } else if (game.equalsIgnoreCase("team-deathmatch")) {
                                plugin.mainConfig.setBooleanGame("enable_lives_team_deathmatch", false);
                                if (plugin.mainConfig.getBooleanGame("respawn_team_deathmatch")) {
                                    player.sendMessage(NORMAL + "Players will now respawn until the game ends.");
                                } else {
                                    player.sendMessage(NORMAL + "Players will no longer respawn.");
                                }
                            }
                        }
                    }
                }
            } else if (args[1].equalsIgnoreCase("lives")) {
                // lives
                if (args.length == 2) {
                    player.sendMessage(NORMAL + "You have to specify the amount of lives.");
                } else {
                    String amount = args[2];
                    int num = 0;
                    try {
                        num = Integer.parseInt(amount);
                    } catch (Exception e) {
                        player.sendMessage(ERROR + "You have to enter a number.");
                    }
                    if (num <= 0) {
                        plugin.mainConfig.setBooleanGame("enable_lives_free-for-all", false);
                        plugin.mainConfig.setBooleanGame("enable_lives_team_deathmatch", false);
                        player.sendMessage(WARN + "You haven't entered a high enough number, so lives have been disabled.");
                    } else {
                        plugin.mainConfig.setIntGame("lives", num);
                        player.sendMessage(NORMAL + "You have set the lives to " + gold + "" + italic + "" + num + " .");
                    }
                }
            } else if (args[1].equalsIgnoreCase("group-player-hit-mobs")) {
                // group_player_hit_mobs
                if (args.length == 2) {
                    player.sendMessage(message);
                } else {
                    String mode = args[2];
                    if (mode.equalsIgnoreCase("on")) {
                        plugin.mainConfig.setBooleanMain("group_player_hit_mobs", true);
                        player.sendMessage(NORMAL + "Players in groups can now attack mobs.");
                    } else if (mode.equalsIgnoreCase("off")) {
                        plugin.mainConfig.setBooleanMain("group_player_hit_mobs", false);
                        player.sendMessage(NORMAL + "Players in groups cannot attack mobs anymore.");
                    }
                }
            } else if (args[1].equalsIgnoreCase("show-timer-above-inventory")) {
                // show_timer_above_inventory
                if (args.length == 2) {
                    player.sendMessage(message);
                } else {
                    String mode = args[2];
                    if (mode.equalsIgnoreCase("on")) {
                        plugin.mainConfig.setBooleanGame("show_timer_above_inventory", true);
                        player.sendMessage(NORMAL + "The timer should appear above the inventory.");
                    } else if (mode.equalsIgnoreCase("off")) {
                        plugin.mainConfig.setBooleanGame("show_timer_above_inventory", false);
                        player.sendMessage(NORMAL + "The timer should not be above the inventory anymore.");
                    }
                }
            } else if (args[1].equalsIgnoreCase("show-timer-boss-bar")) {
                // show_timer_boss_bar
                if (args.length == 2) {
                    player.sendMessage(message);
                } else {
                    String mode = args[2];
                    if (mode.equalsIgnoreCase("on")) {
                        plugin.mainConfig.setBooleanGame("show_timer_boss_bar", true);
                        player.sendMessage(NORMAL + "The timer should appear as a boss bar.");
                    } else if (mode.equalsIgnoreCase("off")) {
                        plugin.mainConfig.setBooleanGame("show_timer_boss_bar", false);
                        player.sendMessage(NORMAL + "The timer should not appear as a boss bar anymore.");
                    }
                }
            } else {
                plugin.helpMenu.helpAll(player);
            }
        }
    }
}
