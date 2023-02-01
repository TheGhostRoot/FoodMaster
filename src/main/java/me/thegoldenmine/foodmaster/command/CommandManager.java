package me.thegoldenmine.foodmaster.command;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.GroupManager;
import me.thegoldenmine.foodmaster.Messenger;
import me.thegoldenmine.foodmaster.Utils;
import me.thegoldenmine.foodmaster.command.SubCmd.StatsCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import static me.thegoldenmine.foodmaster.Messenger.ERROR_GENERAL;
import static me.thegoldenmine.foodmaster.Messenger.WARN_GENERAL;

public class CommandManager {
    private final FoodMaster plugin;
    private final GroupManager groupManager;
    private final Messenger messenger;
    private final Utils utils;
    private final StatsCommand statsCommand;

    public CommandManager(FoodMaster main) {
        // helpers
        plugin = main;
        groupManager = new GroupManager(plugin);
        messenger = new Messenger(plugin);
        utils = new Utils(plugin);

        // commands
        statsCommand = new StatsCommand(plugin);
    }


    public void helpMenu(Player player) {
        if (!player.isOnline()) {
            return;
        }
        ChatColor darkGray = ChatColor.GRAY;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        ChatColor blue = ChatColor.BLUE;
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        BookMeta bookMeta = (BookMeta) meta;
        assert meta != null;
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bookMeta.addPage(blue + "" + bold + "\n SET >" + ChatColor.DARK_PURPLE + "" + italic + "\n /fm set wait-spawn-point " + gold + "" + italic + "[waiting lobby name] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can set unlimited locations as waiting lobby locations and you can call them whatever you want.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set game-time " + gold + "" + italic + "[time in seconds] " + red + "" + bold + ">- " + darkGray + "" + italic + "This will set the timer for the game.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set hungry " + gold + "" + italic + "[while-waiting/during-game/in-group] [on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can decide if the players will be hungry.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set game-timer-color " + gold + "" + italic + "[color]" + red + "" + bold + " >- " + darkGray + "" + italic + "Sets the color of the timer.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set game-spawn-point " + gold + "" + italic + "[game name] [location name] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can set unlimited spawn points for players to be teleported when the game starts or on respawn.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set max-players-in-group " + gold + "" + italic + "[number] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can set the max number of players allowed in one group.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set fall-damage " + gold + "" + italic + "[game/waiting-lobby/group] [on/off]" + red + "" + bold + ">- " + darkGray + "" + italic + "Removes the fall game.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm pve-respawn-player " + gold + "" + italic + "[on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "This sets whether the player will respawn or not.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set max-players-in-waiting-lobby " + gold + "" + italic + "[amount] " + red + "" + bold + ">- " + darkGray + "" + italic + "Sets the max players in a waiting lobby.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set end " + red + "" + bold + ">- " + darkGray + "" + italic + "This will set the location where players will be teleported when the game is ended or when players are kicked.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set friendly-fire " + gold + "" + italic + "[on or off] " + red + "" + bold + ">- " + darkGray + "" + italic + "If you want to take damage from your team then set it to ON or set it to OFF if you don't want to take damage.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set respawn " + gold + "" + italic + "[team-deathmatch/free-for-all] [on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can turn a game's respawn ON or OFF.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set pve-boss-spawn " + gold + "" + italic + "[game name] " + red + "" + bold + ">- " + darkGray + "" + italic + "This sets the location of the boss to spawn for the game.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set damage " + gold + "" + italic + "[amount] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can change the food's damage.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set friendly-damage " + gold + "" + italic + "[amount] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can change the food's friendly fire damage.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set group-player-break-blocks " + gold + "" + italic + "[on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can set if the players in a group can break blocks before the game starts.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set group-player-place-blocks " + gold + "" + italic + "[on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can set if the players in a group can place blocks before the game starts.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set group-player-pickup " + gold + "" + italic + "[on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can set if the players in a group can pickup before the game starts.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set group-player-hit-teammate " + gold + "" + italic + "[on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can set if the players in a group can attack their teammates before the game starts.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set group-player-hit-mobs " + gold + "" + italic + "[on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can set if the players in a group can attack mobs before the game starts.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set lives " + gold + "" + italic + "[amount] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can change the amount of lives for any player.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set riding " + gold + "" + italic + "[pve-bosses/lobby-players] [on/off] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can allow the player to ride the PvE bosses and players in the waiting lobby or not.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm set enable-lives " + gold + "" + italic + "[on/off] [team-deathmatch/free-for-all] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can turn a game's lives ON or OFF");
        bookMeta.addPage(blue + "" + bold + "\n OTHERS >" + ChatColor.DARK_PURPLE + "" + italic + "\n /fm help " + red + "" + bold + ">- " + darkGray + "" + italic + "Shows this menu.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm group help " + red + "" + bold + ">- " + darkGray + "" + italic + "This will show the help menu for the group system.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm end " + red + "" + bold + ">- " + darkGray + "" + italic + "This will end the game no matter what.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm default confirm" + red + "" + bold + ">- " + darkGray + "" + italic + "This will reset all config files to default values.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm kick " + gold + "" + italic + "[player name] [optional: reason] " + red + "" + bold + ">- " + darkGray + "" + italic + "This will kick the player that you have entered.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm rejoin " + red + "" + bold + ">- " + darkGray + "" + italic + "If you are kicked from a game and are still in your group then you can rejoin the game.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm reset " + gold + "" + italic + "[optional: player name] " + red + "" + bold + ">- " + darkGray + "" + italic + "This is for Server Staff Only. This command will reset the player's stats like kills, deaths, wins and losses. It will also remove the player from every game.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm stats " + gold + "" + italic + "[optional: player name] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can see your stats or someone's stats.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm start " + gold + "" + italic + "[team-deathmatch/free-for-all/food-game/pve] [teams/boss] " + red + "" + bold + ">- " + darkGray + "" + italic + "You can start a game with your group.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm gui " + red + "" + bold + ">-" + darkGray + "" + italic + " Opens the FoodMaster GUI.");
        bookMeta.addPage(blue + "" + bold + "\n GROUP >");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm group invite " + gold + "" + italic + "[player name] " + red + "" + bold + ">- " + darkGray + "" + italic + "Invites the player whose name you have entered. The player that you invite will have 5 minutes to accept the invite.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm group accept " + gold + "" + italic + "[player name] " + red + "" + bold + ">- " + darkGray + "" + italic + "You have to accept the invite of the player that you have specified.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm group leave " + red + "" + bold + ">- " + darkGray + "" + italic + "You will leave the group.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm group help " + red + "" + bold + ">- " + darkGray + "" + italic + "Shows this menu.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm group chat " + gold + "" + italic + "[message] " + red + "" + bold + ">- " + darkGray + "" + italic + "Chat with your group.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm group kick " + gold + "" + italic + "[player] [optional: reason] " + red + "" + bold + ">- " + darkGray + "" + italic + "Kicks specified player from your group.");
        bookMeta.addPage(ChatColor.DARK_PURPLE + "" + italic + "/fm group list " + red + "" + bold + ">- " + darkGray + "" + italic + "You can see the group members.");
        bookMeta.setAuthor(player.getName());
        if (plugin.mainConfig.getStrMain("name") != null) {
            bookMeta.setTitle(plugin.mainConfig.getStrMain("name"));
        } else {
            bookMeta.setTitle("FoodMaster");
        }
        item.setItemMeta(meta);
        plugin.playerOpenBook(player, item);
    }

    public void Command(Player player, String[] args) {
        // TODO make every command in a different class
        // TODO refactor it as much as possible
        if (args.length < 1 && player.hasPermission("foodm.help")) {
            // Add appropriate error message
            helpMenu(player);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "help":
                if (player.hasPermission("foodm.help")) {
                    helpMenu(player);
                } else {
                    messenger.warnPermission(player, "foodm.help");
                }
                break;
            case "group":
                if (player.hasPermission("foodm.group")) {
                    if (args.length > 1) {
                        groupManager.GroupMain(player, args);
                    } else {
                        groupManager.helpGroupMenu(player);
                    }
                } else {
                    messenger.warnPermission(player, "foodm.group");
                }
                break;
            case "gui":
                // TODO create refactor the gui after the command
                if (player.hasPermission("foodm.gui")) {
                    player.openInventory(plugin.createGUI.createMain(player));
                } else {
                    messenger.warnPermission(player, "foodm.gui");
                }
                break;
            case "stats":
                if (player.hasPermission("foodm.stats")) {
                    statsCommand.statsCommand(player, args);
                } else {
                    messenger.warnPermission(player, "foodm.stats");
                }
                break;
            case "set":
                if (player.hasPermission("foodm.set")) {
                    if (args.length > 1) {
                        plugin.setSubCommand.setCommand(player, args);
                    } else {
                        helpMenu(player);
                    }
                } else {
                    messenger.warnPermission(player, "foodm.set");
                }
                break;
            case "default":
                if (args[1].equalsIgnoreCase("confirm")) {
                    if (player.hasPermission("foodm.default")) {
                        plugin.mainConfig.setDefaultValues();
                        messenger.normal(player, "The config values have been set to default.");
                    } else {
                        messenger.warn(player, "You need " + ERROR_GENERAL + "foodm.default " + WARN_GENERAL
                                + "permission to do this.");
                    }
                } else {
                    messenger.error(player, "You have to 'confirm' you action!");
                }
                break;
            case "start":
                if (player.hasPermission("foodm.start")) {
                    if (args.length > 1) {
                        plugin.startCommand.mainStart(player, args);
                    } else {
                        helpMenu(player);
                    }
                } else {
                    messenger.warnPermission(player, "foodm.start");
                }
                break;
            case "end":
                // TODO make this command when the game class is done
                break;
            case "kick":
                if (player.hasPermission("foodm.kick")) {
                    if (args.length > 1) {
                        plugin.kickPlayerFromGame.kickPlayerFromGame(args, player);
                    } else {
                        plugin.helpMenu.helpAll(player);
                    }
                } else {
                    messenger.warnPermission(player, "foodm.kick");
                }
                break;
            case "rejoin":
                if (player.hasPermission("foodm.rejoin")) {
                    plugin.rejoin.rejoinCommand(player);
                } else {
                    messenger.warnPermission(player, "foodm.rejoin");
                }
                break;
            case "reset":
                if (player.hasPermission("foodm.reset")) {
                    plugin.resetPlayer.reset_player_command(player, args);
                } else {
                    messenger.warnPermission(player, "foodm.reset");
                }
                break;
            default:
                if (player.hasPermission("foodm.help")) {
                    helpMenu(player);
                }
        }
    }
}
