package me.thegoldenmine.foodmaster.Commands;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class MainTabComplete implements TabCompleter {
    private final FoodMaster plugin;

    public MainTabComplete(FoodMaster plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (args.length == 1) {
            List<String> SubCommands = new ArrayList<>();
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("foodm.commands")) {
                    if (player.hasPermission("foodm.gui")) {
                        SubCommands.add("gui");
                    }
                    if (player.hasPermission("foodm.help")) {
                        SubCommands.add("help");
                    }
                    if (player.hasPermission("foodm.kick")) {
                        SubCommands.add("kick");
                    }
                    if (player.hasPermission("foodm.end")) {
                        SubCommands.add("end");
                    }
                    if (player.hasPermission("foodm.reset")) {
                        SubCommands.add("reset");
                    }
                    if (player.hasPermission("foodm.start")) {
                        SubCommands.add("start");
                    }
                    if (player.hasPermission("foodm.default")) {
                        SubCommands.add("default");
                    }
                    if (player.hasPermission("foodm.set")) {
                        SubCommands.add("set");
                    }
                    if (player.hasPermission("foodm.group")) {
                        SubCommands.add("group");
                    }
                    if (player.hasPermission("foodm.rejoin")) {
                        SubCommands.add("rejoin");
                    }
                    if (player.hasPermission("foodm.stats")) {
                        SubCommands.add("stats");
                    }
                }
            }
            return SubCommands;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("set")) {
                List<String> UltraSubCommands = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player1 = (Player) sender;
                    if (player1.hasPermission("foodm.commands") && player1.hasPermission("foodm.set")) {
                        UltraSubCommands.add("friendly-fire");
                        UltraSubCommands.add("damage");
                        UltraSubCommands.add("friendly-damage");
                        UltraSubCommands.add("pve-respawn-player");
                        UltraSubCommands.add("game-time");
                        UltraSubCommands.add("end");
                        UltraSubCommands.add("max-players-in-group");
                        UltraSubCommands.add("wait-spawn-point");
                        UltraSubCommands.add("game-spawn-point");
                        UltraSubCommands.add("max-players-in-waiting-lobby");
                        UltraSubCommands.add("respawn");
                        UltraSubCommands.add("group-player-break-blocks");
                        UltraSubCommands.add("group-player-place-blocks");
                        UltraSubCommands.add("riding");
                        UltraSubCommands.add("group-player-hit-teammate");
                        UltraSubCommands.add("pve-boss-spawn");
                        UltraSubCommands.add("group-player-hit-mobs");
                        UltraSubCommands.add("group-player-pickup");
                        UltraSubCommands.add("game-timer-color");
                        UltraSubCommands.add("enable-lives");
                        UltraSubCommands.add("lives");
                        UltraSubCommands.add("show-timer-above-inventory");
                        UltraSubCommands.add("show-timer-boss-bar");
                        UltraSubCommands.add("hungry");
                        UltraSubCommands.add("fall-damage");
                    }
                }
                return UltraSubCommands;
            } else if (args[0].equalsIgnoreCase("stats")) {
                List<String> UltraSubCommands = new ArrayList<>();
                if (sender instanceof Player) {
                    Player players = (Player) sender;
                    if (players.hasPermission("foodm.commands") && players.hasPermission("foodm.stats")) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (player != null && !player.equals(players) && player.hasPermission("foodm.commands")) {
                                UltraSubCommands.add(player.getName());
                            }
                        }
                    }
                }
                return UltraSubCommands;
            } else if (args[0].equalsIgnoreCase("start")) {
                List<String> idk = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player1 = (Player) sender;
                    if (player1.hasPermission("foodm.commands") && player1.hasPermission("foodm.start")) {
                        idk.add("team-deathmatch");
                        idk.add("free-for-all");
                        idk.add("food-game");
                        if (player1.hasPermission("foodm.pve")) {
                            idk.add("pve");
                        }
                    }
                }
                return idk;
            } else if (args[0].equalsIgnoreCase("group")) {
                List<String> groupArgs = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player1 = (Player) sender;
                    if (player1.hasPermission("foodm.commands") && player1.hasPermission("foodm.group")) {
                        groupArgs.add("invite");
                        groupArgs.add("accept");
                        groupArgs.add("leave");
                        groupArgs.add("list");
                        groupArgs.add("kick");
                        groupArgs.add("help");
                        groupArgs.add("chat");
                    }
                }
                return groupArgs;
            } else if (args[0].equalsIgnoreCase("kick")) {
                List<String> players = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player1 = (Player) sender;
                    if (player1.hasPermission("foodm.commands") && player1.hasPermission("foodm.kick")) {
                        for (String name : plugin.getWaitLobbyNames()) {
                            if (name != null) {
                                for (UUID uuid : plugin.playersInWaitingLobby.get(name)) {
                                    Player player = Bukkit.getPlayer(uuid);
                                    if (player != null) {
                                        Player playerSender = (Player) sender;
                                        if (!playerSender.getUniqueId().equals(player.getUniqueId())) {
                                            players.add(player.getName());
                                        }
                                    }
                                }
                            }
                        }
                        for (UUID uuid : plugin.game.getAllPlayersInGame()) {
                            Player player = Bukkit.getPlayer(uuid);
                            if (player != null) {
                                Player playerSender = (Player) sender;
                                if (!playerSender.getUniqueId().equals(player.getUniqueId())) {
                                    players.add(player.getName());
                                }
                            }
                        }
                        if (players.isEmpty()) {
                            Player player = (Player) sender;
                            player.sendMessage(WARN + "There are no players playing.");
                            players.add("FM - There are no players playing.");
                        }
                    }
                }
                return players;
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("group") && args[1].equalsIgnoreCase("kick")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    List<String> listOfPlayers = new ArrayList<>();
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.group")) {
                        if (plugin.playerGroup.isPlayerInGroup(player)) {
                            if (plugin.playerGroup.getPlayersInGroupOfPlayer(player).isEmpty()) {
                                plugin.allGroups.removeIf(Set::isEmpty);
                            } else {
                                for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                                    Player players = Bukkit.getPlayer(uuid);
                                    if (players != null) {
                                        if (!players.getName().equals(player.getName())) {
                                            listOfPlayers.add(players.getName());
                                        }
                                    }
                                }
                            }
                        } else {
                            player.sendMessage(WARN + "You are not in a group, but you can join one.");
                        }
                    }
                    return listOfPlayers;
                }
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("riding")) {
                // pve-bosses/lobby-players
                List<String> list = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        list.add("pve-bosses");
                        list.add("lobby-players");
                    }
                }
                return list;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("pve-respawn-player")) {
                return onOff(sender);
                // pve-respawn-player
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("fall-damage")) {
                List<String> list = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        list.add("waiting-lobby");
                        list.add("game");
                        list.add("group");
                    }
                }
                return list;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("hungry")) {
                List<String> list = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        list.add("while-waiting");
                        list.add("during-game");
                        list.add("in-group");
                    }
                }
                return list;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("show-timer-boss-bar")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("pve-boss-spawn")) {
                List<String> list21 = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        if (!plugin.playersInGame.keySet().isEmpty()) {
                            list21.addAll(plugin.playersInGame.keySet());
                        } else {
                            list21.add("FM - There are no games.");
                        }
                    }
                }
                return list21;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("show-timer-above-inventory")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("enable-lives")) {
                List<String> list = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        list.add("free-for-all");
                        list.add("team-deathmatch");
                    }
                }
                return list;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("lives")) {
                List<String> list = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        list.add(String.valueOf(plugin.mainConfig.getIntGame("lives")));
                    }
                }
                return list;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("group-player-break-blocks")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("group-player-hit-mobs")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("group-player-place-blocks")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("group-player-hit-teammate")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("damage")) {
                List<String> list = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        int d = plugin.mainConfig.getIntGame("damage");
                        String dStr = String.valueOf(d);
                        list.add(dStr);
                    }
                }
                return list;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("friendly-damage")) {
                List<String> list = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        int d = plugin.mainConfig.getIntGame("friendly_damage");
                        String dStr = String.valueOf(d);
                        list.add(dStr);
                    }
                }
                return list;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("respawn")) {
                List<String> list2 = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        list2.add("free-for-all");
                        list2.add("team-deathmatch");
                    }
                }
                return list2;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("friendly-fire")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("game-time")) {
                List<String> theList = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        int sec = plugin.mainConfig.getIntGame("game_time_seconds");
                        theList.add(String.valueOf(sec));
                    }
                }
                return theList;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("max-players-in-waiting-lobby")) {
                List<String> theList = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        int maxPlayers = plugin.mainConfig.getIntWaitLobby("max-players_in_waiting_lobby");
                        theList.add(String.valueOf(maxPlayers));
                    }
                }
                return theList;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("max-players-in-group")) {
                List<String> theList = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        int maxPlayers = plugin.mainConfig.getIntMain("max-players_in_group");
                        theList.add(String.valueOf(maxPlayers));
                    }
                }
                return theList;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("game-spawn-point")) {
                List<String> example = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        if (plugin.playersInGame.keySet().isEmpty()) {
                            example.add("ExampleGame");
                            return example;
                        } else {
                            example.addAll(new ArrayList<>(plugin.playersInGame.keySet()));
                        }
                    }
                }
                return example;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("wait-spawn-point")) {
                List<String> list1 = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        if (plugin.getWaitLobbyNames().isEmpty()) {
                            list1.add("exampleLobby");
                        } else {
                            list1.addAll(new ArrayList(plugin.getWaitLobbyNames()));
                        }
                    }
                }
                return list1;
            } else if (args[0].equalsIgnoreCase("group") && args[1].equalsIgnoreCase("accept")) {
                List<String> playersThatInvitedThePlayer = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.group")) {
                        // key - invited = command sender
                        // value - list of senders of the invite
                        UUID uniqueId = player.getUniqueId();
                        if (plugin.invites.get(uniqueId) == null) {
                            player.sendMessage(INFO + "No one invited you.");
                        } else {
                            if (plugin.invites.get(uniqueId).isEmpty()) {
                                player.sendMessage(INFO + "There are no invites.");
                            } else {
                                Set<UUID> uuids1 = plugin.invites.get(uniqueId);
                                if (uuids1.isEmpty()) {
                                    playersThatInvitedThePlayer.add("FM - No one invited you.");
                                    player.sendMessage(INFO + "No one invited you.");
                                }
                                for (UUID uuid : uuids1) {
                                    if (uuid != null) {
                                        Player playerInList = Bukkit.getPlayer(uuid);
                                        if (playerInList != null) {
                                            playersThatInvitedThePlayer.add(playerInList.getName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return playersThatInvitedThePlayer;
            } else if (args[0].equalsIgnoreCase("group") && args[1].equalsIgnoreCase("invite")) {
                List<String> playerNamesCanPlay = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.group")) {
                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                            if (player1.hasPermission("foodm.commands") && !player.getUniqueId().equals(player1.getUniqueId())) {
                                boolean b = plugin.playerGroup.isPlayerInGroup(player1) && !plugin.playerGroup.getPlayersInGroupOfPlayer(player1).contains(player.getUniqueId());
                                if (b || !plugin.playerGroup.isPlayerInGroup(player1)) {
                                    playerNamesCanPlay.add(player1.getName());
                                }
                            }
                        }
                    }
                }
                return playerNamesCanPlay;
            } else if (args[0].equalsIgnoreCase("start") && args[1].equalsIgnoreCase("pve")) {
                List<String> publicOrPrivate = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.start") && player.hasPermission("foodm.pve")) {
                        publicOrPrivate.add("Zombie");
                        publicOrPrivate.add("Skeleton");
                        publicOrPrivate.add("Spider");
                        publicOrPrivate.add("Enderman");
                        publicOrPrivate.add("Slime");
                    }
                }
                return publicOrPrivate;
            } else if (args[0].equalsIgnoreCase("start") && args[1].equalsIgnoreCase("team-deathmatch")) {
                List<String> publicOrPrivate = new ArrayList<>();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.start")) {
                        publicOrPrivate.add("2");
                        publicOrPrivate.add("3");
                        publicOrPrivate.add("4");
                        publicOrPrivate.add("5");
                    }
                }
                return publicOrPrivate;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("group-player-pickup")) {
                return onOff(sender);
            }
        } else if (args.length == 4) {
            if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("game-spawn-point")) {
                List<String> list = new ArrayList<>();
                if (args[2] != null && args[3] != null && sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                        String GameName = args[2];
                        if (plugin.GameSpawnPoints.get(GameName) == null) {
                            list.add("exampleSpawn1");
                        } else {
                            list.addAll(new ArrayList<>(plugin.GameSpawnPoints.get(GameName)));
                        }
                    }
                }
                return list;
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("riding") && args[2].equalsIgnoreCase("pve-bosses")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("riding") && args[2].equalsIgnoreCase("lobby-players")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("fall-damage") && args[2].equalsIgnoreCase("game")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("fall-damage") && args[2].equalsIgnoreCase("waiting-lobby")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("fall-damage") && args[2].equalsIgnoreCase("group")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("hungry") && args[2].equalsIgnoreCase("while-waiting")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("hungry") && args[2].equalsIgnoreCase("during-game")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("hungry") && args[2].equalsIgnoreCase("in-group")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("enable-lives") && args[2].equalsIgnoreCase("free-for-all")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("enable-lives") && args[2].equalsIgnoreCase("team-deathmatch")) {
                return onOff(sender);
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("respawn") && args[2].equalsIgnoreCase("free-for-all")) {
                if (args[3] != null) {
                    return onOff(sender);
                }
            } else if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("respawn") && args[2].equalsIgnoreCase("team-deathmatch")) {
                return onOff(sender);
            }
        }
        return Collections.emptyList();
    }

    private List<String> onOff(CommandSender sender) {
        List<String> list3 = new ArrayList<>();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("foodm.commands") && player.hasPermission("foodm.set")) {
                list3.add("on");
                list3.add("off");
            }
        }
        return list3;
    }
}

