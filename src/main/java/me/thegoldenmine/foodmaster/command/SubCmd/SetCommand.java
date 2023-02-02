package me.thegoldenmine.foodmaster.command.SubCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Messenger;
import me.thegoldenmine.foodmaster.command.CommandManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class SetCommand {
    private final FoodMaster plugin;
    private final Messenger messenger;
    private final CommandManager commandManager;

    public SetCommand(FoodMaster main) {
        plugin = main;
        messenger = new Messenger(plugin);
        commandManager = new CommandManager(plugin);
    }

    private void set_timer_settings(Player player, String[] args) {
        switch (args[1].toLowerCase()) {
            case "game-time":
                if (args.length < 3) {
                    messenger.error(player, Messenger.COMMAND_GENERAL + "/fm set game-time " + Messenger.MAIN_GENERAL
                            + "<seconds> " + Messenger.WARN_GENERAL + ".");
                    return;
                }
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
                    messenger.normal(player, "You have set game time to " + Messenger.MAIN_GENERAL + time +
                            Messenger.NORMAL_GENERAL + " seconds. " + Messenger.MAIN_GENERAL + plugin.time.getTime(time));
                } catch (NumberFormatException e) {
                    messenger.error(player, "You can only enter numbers.");
                }
                break;
            case "show-timer-above-inventory":
                if (args.length < 3) {
                    messenger.error(player, "You have to specify " + Messenger.NORMAL_STYLE + "ON" +
                            Messenger.ERROR_GENERAL + " or " + Messenger.ERROR_STYLE + "OFF");
                    return;
                }
                boolean mode = args[2].equalsIgnoreCase("on");
                plugin.mainConfig.setBooleanGame("show_timer_above_inventory", mode);
                if (mode) {
                    messenger.normal(player, "The timer should appear above the inventory.");
                } else {
                    messenger.normal(player, "The timer should not be above the inventory anymore.");
                }
                break;
            case "show-timer-boss-bar":
                if (args.length < 3) {
                    messenger.error(player, "You have to specify " + Messenger.NORMAL_STYLE + "ON" +
                            Messenger.ERROR_GENERAL + " or " + Messenger.ERROR_STYLE + "OFF");
                    return;
                }
                boolean mode1 = args[2].equalsIgnoreCase("on");
                plugin.mainConfig.setBooleanGame("show_timer_boss_bar", mode1);
                if (mode1) {
                    messenger.normal(player, "The timer should appear as a boss bar.");
                } else {
                    messenger.normal(player, "The timer should not appear as a boss bar anymore.");
                }
                break;
            case "game_timer_color":
                if (args.length >= 3) {
                    plugin.mainConfig.setStrGame("game_timer_color", args[2]);
                    messenger.normal(player, args[2] + "" +
                            plugin.time.getTime(plugin.mainConfig.getIntGame("game_time_seconds")));
                } else {
                    messenger.error(player, "/fm set game-timer-color " + Messenger.MAIN_GENERAL + "[color] "
                            + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "Sets the color of the timer.");
                }
                break;
            default:
                return;
        }
    }

    private void set_fall_damage(Player player, String[] args) {
        if (args.length < 4) {
            messenger.error(player, Messenger.COMMAND_GENERAL + "/fm set fall-damage " + Messenger.MAIN_GENERAL
                    + "[game/waiting-lobby/group] [on/off] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS
                    + "Removes the fall game.");
            return;
        }
        String game = args[2].toLowerCase();
        boolean mode = args[3].equalsIgnoreCase("on");
        String message = "Players won't take fall damage during " + game;
        if (mode) {
            message = "Players will take fall damage during " + game;
        }
        switch (game) {
            case "game":
                plugin.mainConfig.setBooleanGame("enable_fall_damage", mode);
                break;
            case "waiting-lobby":
                plugin.mainConfig.setBooleanWaitLobby("enable_fall_damage", mode);
                break;
            case "group":
                plugin.mainConfig.setBooleanMain("enable_fall_damage", mode);
                break;
            default:
                return;
        }
        messenger.normal(player, message);
    }

    private void set_riding(Player player, String[] args) {
        if (args.length < 4) {
            messenger.error(player, Messenger.COMMAND_GENERAL+"/fm set riding " + Messenger.MAIN_GENERAL
                    + "[pve-bosses/lobby-players] [on/off] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS
                    + "You can allow the player to ride the PvE bosses and players in the waiting lobby or not.");
            return;
        }
        String type = args[2];
        boolean mode = args[3].equalsIgnoreCase("on");
        if (type.equalsIgnoreCase("pve-bosses")) {
            plugin.mainConfig.setBooleanPvE("ride_pve_bosses", mode);
            if (mode) {
                messenger.normal(player, "Players can ride the PvE bosses.");
            } else {
                messenger.normal(player, "Players can't ride the PvE bosses.");
            }
        } else if (type.equalsIgnoreCase("lobby-players")) {
            plugin.mainConfig.setBooleanWaitLobby("ride_players", mode);
            if (mode) {
                messenger.normal(player, "Players can ride other players in the waiting lobby.");
            } else {
                messenger.normal(player, "Players can't ride other players in the waiting lobby.");
            }
        }
    }

    private void set_pve_settings(Player player, String[] args) {
        switch (args[1].toLowerCase()) {
            case "pve-respawn-player":
                if (args.length < 3) {
                    messenger.error(player, Messenger.COMMAND_GENERAL+"/fm set pve-respawn-player " +
                            Messenger.MAIN_GENERAL + "[on/off] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS
                            + "This sets whether the player will respawn or not.");
                    return;
                }
                boolean mode = args[2].equalsIgnoreCase("on");
                plugin.mainConfig.setBooleanPvE("respawm_player", mode);
                if (mode) {
                    messenger.normal(player, "Now the player will respawn.");
                } else {
                    messenger.normal(player, "Now the player won't respawn.");
                }
                break;
            case "pve-boss-spawn":
                if (args.length < 3) {
                    messenger.error(player, Messenger.COMMAND_GENERAL+"/fm set pve-boss-spawn " + Messenger.MAIN_GENERAL
                            + "[game name] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS +
                            "This sets the location of the boss to spawn for the game.");
                    return;
                }
                String gameName = args[2];
                if (plugin.playersInGame.containsKey(gameName)) {
                    plugin.mainConfig.setLocationPvE("pve_boss_spawn-" + gameName, player.getLocation());
                    messenger.normal(player, "You have set your location as a PvE boss spawn point at " +
                            Messenger.MAIN_GENERAL + gameName + Messenger.NORMAL_GENERAL + " game.");
                } else {
                    messenger.error(player, Messenger.MAIN_GENERAL+gameName+Messenger.ERROR_GENERAL+" doesn't exits.");
                }
                break;
            default:
                return;
        }
    }

    private void set_hunger(Player player, String[] args) {
        if (args.length < 4) {
            messenger.error(player,  Messenger.COMMAND_GENERAL + "/fm set hungry " + Messenger.MAIN_GENERAL
                    + "[while-waiting/during-game/in-group] [on/off] " + Messenger.ERROR_STYLE + ">- " +
                    Messenger.COMMAND_DIS + "You can decide if the players will get hungry.");
            return;
        }
        boolean mode = args[3].equalsIgnoreCase("on");
        String game = args[2];
        switch (game) {
            case "during-game":
                plugin.mainConfig.setBooleanGame("hungry_during_a_game", mode);
                if (mode) {
                    messenger.normal(player, "Now players will be hungry during a game.");
                } else {
                    messenger.normal(player,
                            "Now players will have infinite food and they won't be hungry during a game.");
                }
                break;
            case "while-waiting":
                plugin.mainConfig.setBooleanWaitLobby("hungry_while_waiting", mode);
                if (mode) {
                    messenger.normal(player,"Now players will be hungry while waiting in the waiting lobby.");
                } else {
                    messenger.normal(player,
                            "Now players will have infinite food and they won't be hungry while waiting in the waiting lobby.");
                }
                break;
            case "in-group":
                plugin.mainConfig.setBooleanMain("hungry_in_group", mode);
                if (mode) {
                    messenger.normal(player, "Now players will be hungry when they are in group.");
                } else {
                    messenger.normal(player,
                            "Now players will have infinite food and they won't be hungry when they are in group.");
                }
                break;
            default:
                return;
        }
    }

    private void set_respawn(Player player, String[] args) {
        if (args.length < 3) {
            if (args.length == 2) {
                messenger.error(player, "You have to enter a game mode name: " + Messenger.MAIN_GENERAL +
                        "team-deathmatch" + Messenger.COMMAND_GENERAL + " / " + Messenger.MAIN_GENERAL +
                        "free-for-all" + Messenger.ERROR_GENERAL + ".");
            } else {
                messenger.error(player, "You have to set respawn to " + Messenger.NORMAL_GENERAL + "ON" +
                        Messenger.ERROR_GENERAL + " or " + Messenger.ERROR_STYLE + "OFF" + Messenger.ERROR_GENERAL
                        + ".");
            }
        } else {
            String game = args[2];
            boolean mode = args[3].equalsIgnoreCase("on");
            switch (game) {
                case "team-deathmatch":
                    plugin.mainConfig.setBooleanGame("respawn_team_deathmatch", mode);
                    if (mode) {
                        messenger.normal(player, "You have added the respawn from " + Messenger.MAIN_GENERAL
                                + "team-deathmatch" + Messenger.NORMAL_GENERAL + ".");
                    } else {
                        messenger.normal(player, "You have removed the respawn from " + Messenger.MAIN_GENERAL
                                + "team-deathmatch" + Messenger.NORMAL_GENERAL + ".");
                    }
                    break;
                case "free-for-all":
                    plugin.mainConfig.setBooleanGame("respawn_free-for-all", mode);
                    if (mode) {
                        messenger.normal(player, "You have added the respawn from " +Messenger.MAIN_GENERAL
                                + "free-for-all" + Messenger.NORMAL_GENERAL + ".");
                    } else {
                        messenger.normal(player, "You have removed the respawn from " + Messenger.MAIN_GENERAL
                                + "free-for-all" + Messenger.NORMAL_GENERAL + ".");
                    }
                    break;
                default:
                    return;
            }
        }
    }

    private void set_friendly_fire(Player player, String[] args) {
        if (args.length < 3) {
            messenger.error(player,Messenger.COMMAND_GENERAL +"/fm set friendly-fire " + Messenger.MAIN_GENERAL
                    + "[on or off] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS
                    + "If you want to take damage from your team then set it to ON or set it to OFF if you don't want to take damage.");
            return;
        }
        boolean mode = args[2].equalsIgnoreCase("on");
        plugin.mainConfig.setBooleanGame("friendly-fire", mode);
        if (mode) {
            messenger.normal(player, "You have turned " + Messenger.MAIN_GENERAL + "friendly-fire "
                    + Messenger.NORMAL_STYLE + "ON" + Messenger.NORMAL_GENERAL +
                    ". Players will take damage from their team now.");
        } else {
            messenger.normal(player, "You have turned " + Messenger.MAIN_GENERAL + "friendly-fire "
                    + Messenger.ERROR_STYLE + "OFF" + Messenger.NORMAL_GENERAL +
                    ". Players won't take damage from their team now.");
        }
    }

    private void set_end(Player player) {
        Location playerLoc = player.getLocation();
        plugin.mainConfig.setLocationMain("end_location", playerLoc);
        messenger.normal(player, "Your location is set as final location.");
    }

    private void set_waiting_lobby_settings(Player player, String[] args) {
        switch (args[1].toLowerCase()) {
            case "max-players-in-waiting-lobby":
                if (args.length < 3) {
                    messenger.error(player, Messenger.COMMAND_GENERAL+"/fm set max-players-in-waiting-lobby "
                            + Messenger.MAIN_GENERAL + "[amount] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS
                            + "Sets the max players in one group.");
                    return;
                }
                try {
                    int maxPlayer = Integer.parseInt(args[2]);
                    plugin.mainConfig.setIntWaitLobby("max-players_in_waiting_lobby", maxPlayer);
                    messenger.normal(player, "The max amount of players in waiting lobby is " + Messenger.MAIN_GENERAL
                            + "" + maxPlayer + Messenger.NORMAL_GENERAL + " .");
                } catch (NumberFormatException e) {
                    messenger.error(player, "You can only enter numbers!");
                }
                break;
            case "wait-spawn-point":
                if (args.length < 3) {
                    messenger.error(player, Messenger.COMMAND_GENERAL+"/fm set wait-spawn-point " +
                            Messenger.MAIN_GENERAL + "[location name] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS
                            + "You can set unlimited locations as waiting lobby locations and you can call them whatever you want.");
                    return;
                }
                String nameW = args[2];
                plugin.playersInWaitingLobby.put(nameW, new HashSet<>());
                plugin.mainConfig.setLocationWaitLobby(nameW + "->wait-location", player.getLocation());
                int numberOfLobbies = plugin.mainConfig.getIntWaitLobby("number_of_wait-lobbies") + 1;
                plugin.mainConfig.setIntWaitLobby("number_of_wait-lobbies", numberOfLobbies);
                plugin.mainConfig.setStrWaitLobby(numberOfLobbies + "-one_of_the_names", nameW);
                messenger.normal(player, "You have set your location as a waiting lobby with the name "
                        + Messenger.MAIN_GENERAL + nameW + Messenger.NORMAL_GENERAL + " .");
                break;
            default:
                return;
        }
    }

    private void set_game_spawn_point(Player player, String[] args) {
        if (args.length < 4) {
            messenger.error(player, Messenger.COMMAND_GENERAL+"/fm set game-spawn-point "
                    + Messenger.MAIN_GENERAL + "[game name] [location name] " + Messenger.ERROR_STYLE
                    + ">- " + Messenger.COMMAND_DIS + "You can set unlimited spawn points for players.");
            return;
        }
        String givenGameName = args[2];
        String givenNameOfLocation = args[3];
        if (givenGameName.equals(givenNameOfLocation)) {
            messenger.error(player, "The game name must not be the same as the location name.");
            return;
        }
        Location playerLoc = player.getLocation();
        int x = plugin.mainConfig.getIntGame("number_of_game_names");
        int w = plugin.mainConfig.getIntGame("number_of_game_location_names");
        if (plugin.GameSpawnPoints.isEmpty() || !plugin.GameSpawnPoints.containsKey(givenGameName)) {
            x++;
            plugin.mainConfig.setStrGame(x + "-one_of_the_game_names", givenGameName);
            plugin.mainConfig.setIntGame("number_of_game_names", x);
            w++;
            plugin.mainConfig.setStrGame(w + "-one_of_the_game_location_names-" + givenGameName,
                    givenNameOfLocation);
            plugin.mainConfig.setIntGame("number_of_game_location_names", w);
            Set<String> namesOfLoc = new HashSet<>();
            namesOfLoc.add(givenNameOfLocation);
            plugin.GameSpawnPoints.put(givenGameName, namesOfLoc);
        } else if (!plugin.GameSpawnPoints.get(givenGameName).contains(givenNameOfLocation)) {
            w++;
            plugin.mainConfig.setStrGame(w + "-one_of_the_game_location_names-" +
                    givenGameName, givenNameOfLocation);
            plugin.mainConfig.setIntGame("number_of_game_location_names", w);
            plugin.GameSpawnPoints.get(givenGameName).add(givenNameOfLocation);
        }
        if (!plugin.timer.containsKey(givenGameName)) {
            int time = plugin.mainConfig.getIntGame("game_time_seconds");
            plugin.timer.put(givenGameName, time);
        }
        if (!plugin.playersInGame.containsKey(givenGameName)) {
            Set<UUID> just = new HashSet<>();
            plugin.playersInGame.put(givenGameName, just);
        }
        plugin.mainConfig.setLocationGame(givenGameName + "->" + givenNameOfLocation + "-spawn-point", playerLoc);
        plugin.mainConfig.saveGame();
        plugin.mainConfig.reloadGame();
        messenger.normal(player, "You have set " + Messenger.MAIN_GENERAL
                + givenNameOfLocation + Messenger.NORMAL_GENERAL + " as spawn point location. "
                + Messenger.MAIN_GENERAL + givenGameName + Messenger.NORMAL_GENERAL + "  has one more spawn point.");
    }

    private void set_group_settings(Player player, String[] args) {
        switch (args[1].toLowerCase()) {
            case "max-players-in-group":
                if (args.length < 3) {
                    messenger.error(player, "/fm set max-players-in-group " + Messenger.MAIN_GENERAL
                            + "[number] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS
                            + "You can set the max number of players allowed in one group.");
                    return;
                }
                try {
                    int players = Integer.parseInt(args[2]);
                    plugin.mainConfig.setIntMain("max-players_in_group", players);
                    messenger.normal(player, "You have set the max players allowed in one group to " +
                            Messenger.MAIN_GENERAL + players + Messenger.NORMAL_GENERAL + " .");
                } catch (NumberFormatException e) {
                    messenger.error(player, "You must provide a number. No letters are allowed!");
                }
                break;
            case "group-player-break-blocks":
                if (args.length == 2) {
                    messenger.error(player, "You have to specify " + Messenger.NORMAL_STYLE + "ON" +
                            Messenger.ERROR_GENERAL + " or " + Messenger.ERROR_STYLE + "OFF");
                    return;
                }
                boolean mode = args[2].equalsIgnoreCase("on");
                plugin.mainConfig.setBooleanMain("group_player_break_blocks", mode);
                if (mode) {
                    messenger.normal(player, "Players in groups can now break blocks before the game starts.");
                } else {
                    messenger.normal(player, "Players in groups cannot break blocks anymore.");
                }
                break;
            case "group-player-place-blocks":
                if (args.length == 2) {
                    messenger.error(player, "You have to specify " + Messenger.NORMAL_STYLE + "ON" +
                            Messenger.ERROR_GENERAL + " or " + Messenger.ERROR_STYLE + "OFF");
                    return;
                }
                boolean mode1 = args[2].equalsIgnoreCase("on");
                plugin.mainConfig.setBooleanMain("group_player_place_blocks", mode1);
                if (mode1) {
                    messenger.normal(player, "Players in groups can now place blocks before the game starts.");
                } else {
                    messenger.normal(player, "Players in groups cannot place blocks anymore.");
                }
                break;
            case "group-player-hit-teammate":
                if (args.length == 2) {
                    messenger.error(player, "You have to specify " + Messenger.NORMAL_STYLE + "ON" +
                            Messenger.ERROR_GENERAL + " or " + Messenger.ERROR_STYLE + "OFF");
                    return;
                }
                boolean mode2 = args[2].equalsIgnoreCase("on");
                plugin.mainConfig.setBooleanMain("group_player_hit_teammate", mode2);
                if (mode2) {
                    messenger.normal(player, "Players in groups can now attack their teammates.");
                } else {
                    messenger.normal(player, "Players in groups cannot attack their teammates anymore.");
                }
                break;
            case "group-player-pickup":
                if (args.length == 2) {
                    messenger.error(player, "You have to specify " + Messenger.NORMAL_STYLE + "ON" +
                            Messenger.ERROR_GENERAL + " or " + Messenger.ERROR_STYLE + "OFF");
                    return;
                }
                boolean mode3 = args[2].equalsIgnoreCase("on");
                plugin.mainConfig.setBooleanMain("group_player_pickup", mode3);
                if (mode3) {
                    messenger.normal(player, "Players in groups can pickup items.");
                } else  {
                    messenger.normal(player, "Players in groups cannot pickup items anymore.");
                }
                break;
            case "group-player-hit-mobs":
                if (args.length < 3) {
                    messenger.error(player, "You have to specify " + Messenger.NORMAL_STYLE + "ON" +
                            Messenger.ERROR_GENERAL + " or " + Messenger.ERROR_STYLE + "OFF");
                    return;
                }
                boolean mode4 = args[2].equalsIgnoreCase("on");
                plugin.mainConfig.setBooleanMain("group_player_hit_mobs", mode4);
                if (mode4) {
                    messenger.normal(player, "Players in groups can now attack mobs.");
                } else {
                    messenger.normal(player, "Players in groups cannot attack mobs anymore.");
                }
                break;
            default:
                return;
        }
    }

    private void set_friendly_damage(Player player, String[] args) {
        if (args.length < 3) {
            messenger.error(player, "You have to specify the amount of friendly fire damage with a number.");
            return;
        }
        String damageStr = args[2];
        if (!damageStr.matches("\\d+")) {
            messenger.error(player, "You have to enter a number.");
        } else {
            int damage = Integer.parseInt(damageStr);
            plugin.mainConfig.setIntGame("friendly_damage", damage);
            messenger.normal(player, "You have set the friendly fire damage to " + damageStr);
        }
    }

    private void set_enable_lives(Player player, String[] args) {
        if (args.length == 2) {
            messenger.error(player, "You have to enter a game mode.");
            return;
        }
        if (args.length == 3) {
            messenger.error(player, "You have to specify " + Messenger.NORMAL_STYLE + "ON" +
                        Messenger.ERROR_GENERAL + " or " + Messenger.ERROR_STYLE + "OFF");
            return;
        }
        boolean mode = args[3].equalsIgnoreCase("on");
        String game = args[2].toLowerCase();
        if (mode) {
            if (game.equals("free-for-all")) {
                plugin.mainConfig.setBooleanGame("enable_lives_free-for-all", true);
                if (plugin.mainConfig.getBooleanGame("respawn_free-for-all")) {
                    messenger.normal(player, "Players now have limited lives. The amount of lives is " +Messenger.MAIN_GENERAL +plugin.mainConfig.getIntGame("lives"));
                } else {
                    plugin.mainConfig.setBooleanGame("respawn_free-for-all", true);
                    messenger.normal(player, "Respawn has been turned on automatically.");
                }
            } else {
                plugin.mainConfig.setBooleanGame("enable_lives_team_deathmatch", true);
                if (plugin.mainConfig.getBooleanGame("respawn_team_deathmatch")) {
                    messenger.normal(player, "Players now have limited lives. The amount of lives is " + Messenger.MAIN_GENERAL + plugin.mainConfig.getIntGame("lives"));
                } else {
                    plugin.mainConfig.setBooleanGame("respawn_team_deathmatch", true);
                    messenger.normal(player, "Respawn has been turned on automatically.");
                }
            }
        } else {
            if (game.equals("free-for-all")) {
                plugin.mainConfig.setBooleanGame("enable_lives_free-for-all", false);
                if (plugin.mainConfig.getBooleanGame("respawn_free-for-all")) {
                    messenger.normal(player, "Players will now respawn until the game ends.");
                } else {
                    messenger.normal(player, "Players will no longer respawn.");
                }
            } else  {
                plugin.mainConfig.setBooleanGame("enable_lives_team_deathmatch", false);
                if (plugin.mainConfig.getBooleanGame("respawn_team_deathmatch")) {
                    messenger.normal(player, "Players will now respawn until the game ends.");
                } else {
                    messenger.normal(player, "Players will no longer respawn.");
                }
            }
        }
    }

    private void set_lives(Player player, String[] args) {
        if (args.length < 3) {
            messenger.error(player, "You have to specify the amount of lives.");
            return;
        }
        String amount = args[2];
        if (!amount.matches("\\d+")) {
            messenger.error(player, "You have to enter a number.");
            return;
        }
        int num = Integer.parseInt(amount);
        if (num <= 0) {
            plugin.mainConfig.setBooleanGame("enable_lives_free-for-all", false);
            plugin.mainConfig.setBooleanGame("enable_lives_team_deathmatch", false);
            messenger.warn(player, "You haven't entered a high enough number, so lives have been disabled.");
        } else {
            plugin.mainConfig.setIntGame("lives", num);
            messenger.normal(player, "You have set the lives to " + Messenger.MAIN_GENERAL + num + " .");
        }
    }

    private void set_damage(Player player, String[] args) {
        if (args.length < 3) {
            messenger.error(player, "You have to specify the amount of damage dealt with a number.");
            return;
        }
        String damageStr = args[2];
        if (damageStr.matches("\\d+")) {
            int damage = Integer.parseInt(damageStr);
            plugin.mainConfig.setIntGame("damage", damage);
            messenger.normal(player, "You have set the damage to " + damageStr);
        } else {
            messenger.error(player, "You can only enter numbers");
        }
    }

    public void setCommand(Player player, String[] args) {
        String option = args[1].toLowerCase();
        if (option.contains("time")) {
            set_timer_settings(player, args);
        } else if (option.contains("pve")) {
            set_pve_settings(player, args);
        } else if (option.contains("wait")) {
            set_waiting_lobby_settings(player, args);
        } else if (option.contains("group")) {
            set_group_settings(player, args);
        } else {
            switch (option) {
                case "fall-damage":
                    set_fall_damage(player, args);
                    break;
                case "riding":
                    set_riding(player, args);
                    break;
                case "hungry":
                    set_hunger(player, args);
                    break;
                case "respawn":
                    set_respawn(player, args);
                    break;
                case "friendly-fire":
                    set_friendly_fire(player, args);
                    break;
                case "end":
                    set_end(player);
                    break;
                case "damage":
                    set_damage(player, args);
                    break;
                case "friendly-damage":
                    set_friendly_damage(player, args);
                    break;
                case "game-spawn-point":
                    set_game_spawn_point(player, args);
                    break;
                case "enable-lives":
                    set_enable_lives(player, args);
                    break;
                case "lives":
                    set_lives(player, args);
                    break;
                default:
                    if (player.hasPermission("foodm.help")) {
                        commandManager.helpMenu(player);
                    }
            }
        }
    }
}
