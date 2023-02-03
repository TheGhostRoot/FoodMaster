package me.thegoldenmine.foodmaster.command.SubCmd.refactored;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.GroupManager;
import me.thegoldenmine.foodmaster.Messenger;
import me.thegoldenmine.foodmaster.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public class KickPlayerCommand {
    private final FoodMaster plugin;
    private final Utils utils;
    private final GroupManager groupManager;
    private final Messenger messenger;
    private final EndGameCommand endGameCommand;

    public KickPlayerCommand(FoodMaster main) {
        plugin = main;
        utils = new Utils(plugin);
        groupManager = new GroupManager(plugin);
        messenger = new Messenger(plugin);
        endGameCommand = new EndGameCommand(plugin);
    }

    private boolean can_be_kicked(Player player, Player player_to_kick) {
        if (!player_to_kick.isOnline()) {
            messenger.error(player, Messenger.MAIN_GENERAL + player_to_kick.getName() + Messenger.ERROR_GENERAL + " is not Online!");
            return false;
        }
        if (player.equals(player_to_kick)) {
            messenger.error(player, "You can't kick yourself.");
            return false;
        }
        if (plugin.kickedPlayers.contains(player_to_kick.getUniqueId())) {
            messenger.error(player, Messenger.MAIN_GENERAL + player_to_kick.getName() + Messenger.ERROR_GENERAL + " is already kicked.");
            return false;
        }
        return true;
    }

    public void kickPlayer(Player player, String[] args) {
        String reason = null;
        if (args.length > 2) {
            reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
        }
        Player player_to_kick = Bukkit.getPlayer(args[2]);
        if (player_to_kick == null) {
            messenger.error(player, "This player is invalid.");
            return;
        }
        if (!can_be_kicked(player, player_to_kick)) { return; }
        endGameCommand.end_the_game_for_one_player(player);
        player_to_kick.sendTitle(Messenger.INFO_GENERAL + "You were kicked by", Messenger.MAIN_GENERAL + player.getName(), 2, 80, 2);
        String reasonText = ".";
        if (reason != null) {
            reasonText = " due to " + Messenger.MAIN_GENERAL + reason;
        }
        String message = "You were kicked by " + Messenger.MAIN_GENERAL + player.getName() + Messenger.INFO_GENERAL + reasonText;
        if (groupManager.isPlayerInGroup(player_to_kick)) {
            for (UUID uuid : groupManager.getPlayersInGroupOfPlayer(player_to_kick)) {
                if (uuid != null) {
                    Player playerInGroup = Bukkit.getPlayer(uuid);
                    if (playerInGroup != null) {
                        messenger.info(playerInGroup, Messenger.MAIN_GENERAL + player_to_kick.getName() + Messenger.INFO_GENERAL + " was kicked by " + Messenger.MAIN_GENERAL + player.getName() + Messenger.INFO_GENERAL + reasonText);
                        messenger.info(player_to_kick, message);
                    }
                }
            }
        } else {
            messenger.info(player_to_kick, message);
        }
    }
}
