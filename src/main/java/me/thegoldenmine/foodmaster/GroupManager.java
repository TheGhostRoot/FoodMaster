package me.thegoldenmine.foodmaster;

import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.Bukkit;
import static org.bukkit.ChatColor.*;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class GroupManager {

    FoodMaster plugin;
    private final Messenger messenger;
    private final Utils utils;

    public GroupManager(FoodMaster main) {
        plugin = main;
        messenger = new Messenger(plugin);
        utils = new Utils(plugin);
    }

    public void GroupMain(Player player, String[] args) {
        // The player is the sender of the command
        if (player != null) {
            if (args.length >= 2) {
                if (args[1].equalsIgnoreCase("invite")) {
                    inviteCommand(player, args);
                } else if (args[1].equalsIgnoreCase("accept")) {
                    acceptCommand(player, args);
                } else if (args[1].equalsIgnoreCase("leave")) {
                    leaveCommand(player);
                } else if (args[1].equalsIgnoreCase("list")) {
                    listCommand(player);
                } else if (args[1].equalsIgnoreCase("kick")) {
                    kickCommand(player, args);
                } else if (args[1].equalsIgnoreCase("chat")) {
                    chatCommand(player, args);
                } else if (args[1].equalsIgnoreCase("help")) {
                    helpGroup(player);
                }
            } else {
                helpGroup(player);
            }
        }
    }

    public void helpGroup(Player player) {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        BookMeta bookMeta = (BookMeta) meta;
        assert meta != null;
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bookMeta.addPage(Messenger.COMMAND_GENERAL+ "/fm group invite " + Messenger.MAIN_GENERAL + "[player name] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "Invites the player whose name you have entered. The player that you invite will have 5 minutes to accept the invite.");
        bookMeta.addPage(Messenger.COMMAND_GENERAL + "/fm group accept " + Messenger.MAIN_GENERAL + "[player name] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "You have to accept the invite of the player that you have specified.");
        bookMeta.addPage(Messenger.COMMAND_GENERAL + "/fm group leave " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "You will leave the group.");
        bookMeta.addPage(Messenger.COMMAND_GENERAL + "/fm group help " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "Shows this menu.");
        bookMeta.addPage(Messenger.COMMAND_GENERAL + "/fm group chat " + Messenger.MAIN_GENERAL + "[message] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "Chat with your group.");
        bookMeta.addPage(Messenger.COMMAND_GENERAL + "/fm group kick " + Messenger.MAIN_GENERAL + "[player] [optional: reason] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "Kicks specified player from your group.");
        bookMeta.addPage(Messenger.COMMAND_GENERAL + "/fm group list " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "You can see the group members.");
        bookMeta.setAuthor(player.getName());
        if (plugin.mainConfig.getStrMain("name") != null) {
            bookMeta.setTitle(plugin.mainConfig.getStrMain("name"));
        } else {
            bookMeta.setTitle("FoodMaster");
        }
        item.setItemMeta(meta);
        plugin.playerOpenBook(player, item);
    }

    public void acceptCommand(Player player, String[] args) {
        Player inviter = Bukkit.getPlayer(args[2]);
        if (!canJoin(player, inviter)) {
            return;
        }
        joinGroup(player, inviter);
    }

    public void chatCommand(Player player, String[] args) {
        StringBuilder message = new StringBuilder();
        if (args.length >= 3) {
            for (int i = 2; i < args.length; i++) {
                message.append(" ").append(args[i]);
            }
            if (!plugin.playerGroup.isPlayerInGroup(player)) {
                messenger.error(player, "You need to be in a group to send messages.");
                return;
            }
            if (message.length() == 0) {
                messenger.error(player, "You should write a message.");
                return;
            }
            for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                Player players = Bukkit.getPlayer(uuid);
                if (players != null) {
                    messenger.normal(players, Messenger.INFO_STYLE + "Group Chat "+ Messenger.DASH + "- " + Messenger.MAIN_GENERAL + player.getName() + Messenger.ERROR_STYLE + " -> " + Messenger.NORMAL_GENERAL + message);
                }
            }
        } else {
            messenger.info(player, Messenger.COMMAND_GENERAL + "/fm group chat " + Messenger.MAIN_GENERAL + "[message] " + Messenger.ERROR_STYLE + " >- " + Messenger.COMMAND_DIS + " Chat with your group.");
        }
    }

    public void leaveCommand(Player player) {
        if (plugin.game.isPlayerInGame(player)) {
            player.sendMessage("You cannot leave the group while in-game.");
            return;
        }

        if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
            Location endLoc = plugin.mainConfig.getLocationMain("end_location");
            if (endLoc == null) {
                player.sendMessage("End location is not set. Unable to leave group.");
            } else {
                player.teleport(endLoc);
                leaveGroup(player);
                utils.clearPlayerChose(player);
            }
        } else {
            leaveGroup(player);
        }
    }

    private boolean canKick(Player player, Player player_to_kick) {
        UUID player_to_kick_UUID = player_to_kick.getUniqueId();
        UUID playerUUID = player.getUniqueId();
        if (player_to_kick_UUID.equals(playerUUID)) {
            messenger.error(player, "You can't kick yourself.");
            return false;
        }
        if (!isPlayerInGroup(player)) {
            messenger.error(player, "You have to be in a group.");
            return false;
        }
        if (!isPlayerInGroup(player_to_kick)) {
            messenger.error(player, Messenger.MAIN_GENERAL + player_to_kick.getName() + Messenger.ERROR_GENERAL + " is not in group.");
            return false;
        }
        if (!getPlayersInGroupOfPlayer(player_to_kick).contains(playerUUID)) {
            messenger.error(player, Messenger.MAIN_GENERAL + player_to_kick.getName() + Messenger.ERROR_GENERAL + " is not in your group.");
            return false;
        }
        if (!player_to_kick.isOnline()) {
            messenger.error(player, Messenger.MAIN_GENERAL + player_to_kick.getName() + Messenger.ERROR_GENERAL + " is not Online!");
            return false;
        }
        if (plugin.game.isPlayerInGame(player) || plugin.game.isPlayerInGame(player_to_kick) || plugin.waitingLobby.isPlayerInWaitingLobby(player) || plugin.waitingLobby.isPlayerInWaitingLobby(player_to_kick)) {
            messenger.error(player, "You can't kick players from your group while you are in a game or waiting lobby");
            return false;
        }
        return true;
    }

    public synchronized void remove_small_groups() {
        List<Set<UUID>> removeListG = new ArrayList<>();
        for (Set<UUID> group : plugin.allGroups) {
            if (group.size() < 2) {
                UUID uuid = group.stream().findFirst().orElse(null);
                if (uuid != null) {
                    Player players = Bukkit.getPlayer(uuid);
                    if (players != null) {
                        group.remove(players.getUniqueId());
                        messenger.info(players, "You have been removed from the group, because there is no one else in the group.");
                    }
                }
                removeListG.add(group);
            }
        }
        removeListG.forEach(plugin.allGroups::remove);
        plugin.allGroups.removeIf(Set::isEmpty);
    }

    private void tell_players_that_a_player_was_kicked(Player player, String reason) {
        Set<UUID> playersInGroup = getPlayersInGroupOfPlayer(player);
        String message = reason != null ?
                Messenger.MAIN_GENERAL + player.getName() + Messenger.NORMAL_GENERAL + " has been kicked from the group. Reason: " + Messenger.WARN_GENERAL + reason :
                Messenger.MAIN_GENERAL + player.getName() + Messenger.NORMAL_GENERAL + " has been kicked from the group.";
        for (UUID uuid : playersInGroup) {
            Player players = Bukkit.getPlayer(uuid);
            if (players != null && !players.equals(player)) {
                messenger.normal(players, message);
            }
        }
    }

    private void tell_the_player_that_the_player_was_kick(String reason, Player player, Player playerKick) {
        String playersList = getPlayerNamesFromGroupString(playerKick);
        String messageToPlayer = "You have kicked " + Messenger.MAIN_GENERAL + playerKick.getName() + Messenger.NORMAL_GENERAL + " from your group";
        String messageToPlayerKick = "You have been kicked from the group of " + Messenger.MAIN_GENERAL + playersList + Messenger.NORMAL_GENERAL + " players by " + Messenger.MAIN_GENERAL + player.getName() + Messenger.NORMAL_GENERAL + " .";
        if (reason != null) {
            messageToPlayer += ". Reason: " + Messenger.WARN_GENERAL + reason;
            messageToPlayerKick += ". Reason: " + Messenger.WARN_GENERAL + reason;
        }
        messenger.normal(player, messageToPlayer);
        messenger.normal(playerKick, messageToPlayerKick);
        playerKick.sendTitle(Messenger.MAIN_GENERAL + "You were kicked from your group by", Messenger.ERROR_STYLE + player.getName(), 2, 80, 2);
        player.sendTitle(Messenger.MAIN_GENERAL + "You just kicked", Messenger.INFO_GENERAL + playerKick.getName(), 2, 80, 2);
    }

    public void kickCommand(Player player, String[] args) {
        if (args.length >= 3) {
            Player player_to_kick = Bukkit.getPlayer(args[2]);
            if (player_to_kick == null) {
                messenger.error(player, "This player is invalid.");
                return;
            }
            if (!canKick(player, player_to_kick)) { return; }
            getPlayersInGroupOfPlayer(player).remove(player_to_kick.getUniqueId());
            remove_small_groups();
            String reason = null;
            if (args.length >= 4) {
                reason = String.join(" ", Arrays.copyOfRange(args, 3, args.length));
            }
            tell_players_that_a_player_was_kicked(player, reason);
            tell_the_player_that_the_player_was_kick(reason, player, player_to_kick);
        }
    }

    public void inviteCommand(Player player, String[] args) {
        if (args.length >= 3) {
            // /fm group invite playerName
            //      0      1        2      index
            //      1      2        3       num
            // player - sender - inviter - value
            // invited - accept - invited - key
            Player invited = Bukkit.getPlayer(args[2]);
            if (invited == null) {
                messenger.error(player, "This player is invalid!");
                return;
            }
            if (!canInvite(invited, player)) { return; }
            invitePlayer(player, invited);
        } else {
            messenger.info(player, Messenger.COMMAND_GENERAL + "/fm group invite " + Messenger.MAIN_GENERAL + "[player name] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "Invites the player whose name you have entered. The player that you invite will have 5 minutes to accept the invite.");
        }
    }

    public void listCommand(Player player) {
        if (!isPlayerInGroup(player)) {
            messenger.warn(player, "You are not in a group, but you can join one.");
            return;
        }
        final Set<UUID> playerGroup = getPlayersInGroupOfPlayer(player);
        if (playerGroup.size() < 2) {
            plugin.allGroups.remove(playerGroup);
            messenger.info(player, "You have been removed from the group, because there is no one else in the group.");
        } else {
            String names = getPlayerNamesFromGroupString(player);
            messenger.normal(player, "The players in this group are " + Messenger.MAIN_GENERAL + names);
        }
    }

    public boolean canInvite(Player invited, Player inviter) {
        UUID invitedUUID = invited.getUniqueId();
        if (isPlayerInGroup(inviter) && getPlayersInGroupOfPlayer(inviter).size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
            messenger.warn(inviter, "Your group has reached the player limit.");
            return false;
        }
        if (isPlayerInGroup(inviter) && getPlayersInGroupOfPlayer(inviter).contains(invitedUUID)) {
            messenger.error(inviter, "You are in the same group.");
            return false;
        }
        if (plugin.commandCoolDown.isPlayerInCoolDown(inviter)) {
            messenger.warn(inviter, "Slow down. You have " + Messenger.MAIN_GENERAL + plugin.commandCoolDown.getTime(inviter) + Messenger.WARN_GENERAL + " seconds left.");
            return false;
        }
        if (plugin.groupInviteManager.isPlayerInvited(invited)) {
            messenger.warn(inviter, "You have already invited " + Messenger.MAIN_GENERAL + invited.getName() + Messenger.WARN_GENERAL + " .");
            return false;
        }
        if (invited.equals(inviter)) {
            messenger.error(inviter, "You can't invite yourself.");
            return false;
        }
        if (plugin.game.isPlayerInGame(inviter) || plugin.waitingLobby.isPlayerInWaitingLobby(inviter)) {
            messenger.error(inviter, "You can't send invites while in game or waiting in a lobby.");
            return false;
        }
        if (plugin.game.isPlayerInGame(invited) || plugin.waitingLobby.isPlayerInWaitingLobby(invited)) {
            messenger.error(inviter, Messenger.MAIN_GENERAL + ""+ invited.getName() + Messenger.ERROR_GENERAL + " is in-game or in a waiting lobby. Players that are in the game or waiting in a lobby can't be invited.");
            return false;
        }
        if (plugin.mainConfig.getIntMain("max-players_in_group") < 2) {
            if (inviter.hasPermission("foodm.staff")) {
                messenger.error(inviter, "You have to set the " + Messenger.MAIN_GENERAL + "max-players-in-group" + Messenger.ERROR_GENERAL + " to more than " + Messenger.MAIN_GENERAL + "1" + Messenger.ERROR_GENERAL + " to invite players.");
            } else {
                messenger.error(inviter, "The max amount of players allowed in one group isn't set correctly.");
            }
            return false;
        }
        return true;
    }

    public void invitePlayer(Player inviter, Player invited) {
        UUID invitedUUID = invited.getUniqueId();
        UUID inviterUUID = inviter.getUniqueId();
        Set<UUID> uuids;
        if (plugin.invites.get(invitedUUID) == null) {
            uuids = new HashSet<>();
        } else {
            uuids = plugin.invites.get(invitedUUID);
        }
        uuids.add(inviterUUID);
        plugin.invites.put(invitedUUID, uuids);
        plugin.groupInviteManager.addPlayerToCoolMap(invited, 5);
        plugin.commandCoolDown.addPlayerToCoolMap(inviter, 5);
        messenger.normal(inviter, "You have successfully invited " + Messenger.MAIN_GENERAL + invited.getName() + Messenger.NORMAL_GENERAL + " to your group. " + Messenger.MAIN_GENERAL + invited.getName() + Messenger.NORMAL_GENERAL + " has " + Messenger.MAIN_GENERAL + plugin.groupInviteManager.getTime(invited) + Messenger.NORMAL_GENERAL + " minutes to accept the invite.");
        messenger.normal(invited, "You have been invited by " + Messenger.MAIN_GENERAL + inviter.getName() + Messenger.NORMAL_GENERAL + " in their group to play FoodMaster. You have " + Messenger.MAIN_GENERAL + plugin.groupInviteManager.getTime(invited) + Messenger.NORMAL_GENERAL + " minutes to accept the invite." + " Use this command to accept: " + GOLD + "/fm group accept " + inviter.getName());
    }

    public boolean canJoin(Player invited, Player inviter) {
        if (invited.equals(inviter)) {
            messenger.error(inviter, "You can't invite yourself.");
            return false;
        }
        // Check if players are online and not in game or waiting lobby
        if (!inviter.isOnline() || plugin.game.isPlayerInGame(inviter) || plugin.waitingLobby.isPlayerInWaitingLobby(inviter) ||
                !invited.isOnline() || plugin.game.isPlayerInGame(invited) || plugin.waitingLobby.isPlayerInWaitingLobby(invited)) {
            messenger.error(inviter, "Players are not online or in the game or waiting lobby.");
            return false;
        }
        return plugin.invites.get(invited.getUniqueId()) != null && !plugin.invites.get(invited.getUniqueId()).isEmpty();
    }

    public synchronized void update_The_PlayAgain_For_The_Group(Player player) {
        Set<UUID> playersInGroupOfPlayer = getPlayersInGroupOfPlayer(player);
        playersInGroupOfPlayer.stream()
                .filter(uuid -> plugin.playAgain.containsKey(uuid))
                .map(plugin.playAgain::get)
                .findFirst()
                .ifPresent(game -> playersInGroupOfPlayer.forEach(uuid -> plugin.playAgain.put(uuid, game)));
    }

    public void player_Leave_Group_Send_Messages(Player playerLeaver) {
        getPlayersInGroupOfPlayer(playerLeaver)
                .stream()
                .filter(uuid -> !uuid.equals(playerLeaver.getUniqueId()))
                .map(Bukkit::getPlayer)
                .forEach(p -> messenger.info(p, playerLeaver.getName() + Messenger.NORMAL_GENERAL + " has left the group."));
    }

    public synchronized void leaveGroup(Player player) {
        player_Leave_Group_Send_Messages(player);
        getPlayersInGroupOfPlayer(player).remove(player.getUniqueId());
        messenger.normal(player, "You have left the group of " + Messenger.MAIN_GENERAL + getPlayerNamesFromGroupString(player) + Messenger.NORMAL_GENERAL+ " players");
    }

    private synchronized void join_Group_And_Remove_Invite(Player joiner, Player inviter) {
        leaveGroup(joiner);
        Set<UUID> inviterGroup = getPlayersInGroupOfPlayer(inviter);
        UUID joinerUUID = joiner.getUniqueId();
        remove_Invite(inviter.getUniqueId(), joinerUUID);
        inviterGroup.add(joinerUUID);
        update_The_PlayAgain_For_The_Group(inviter);
        Join_Group_Message(joiner, inviter);
        if (inviterGroup.size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
            messenger.warn(inviter, "You have reached the max player limit in your group");
            messenger.warn(joiner, inviter.getName() + Messenger.WARN_GENERAL + " has reached the max player limit in the group.");
        }
    }

    public String getPlayerNamesFromGroupString(Player player) {
        if (player == null || !isPlayerInGroup(player)) {
            return "";
        }

        Set<UUID> group = getPlayersInGroupOfPlayer(player);
        return group.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getName)
                .collect(Collectors.toSet())
                .toString()
                .replace("[", "")
                .replace("]", "")
                .replace("\"\"", "");
    }

    public synchronized void clear_Player_Invites(Player player) {
        if (player != null) {
            UUID uuid = player.getUniqueId();
            Set<UUID> inviters = plugin.invites.get(uuid);
            if (inviters != null) {
                inviters.clear();
            }
            plugin.invites.values().forEach(uuidList -> {
                if(uuidList!=null)
                    uuidList.removeIf(uuid::equals);
            });
        }
    }

    public void Join_Group_Message(Player playerJoiner, Player inviter) {
        Set<UUID> playersInGroup = getPlayersInGroupOfPlayer(inviter);
        if (playersInGroup != null) {
            playersInGroup.stream()
                    .map(Bukkit::getPlayer)
                    .filter(p -> p != playerJoiner)
                    .forEach(p -> messenger.info(p, playerJoiner.getName() + Messenger.INFO_GENERAL +" has joined the group."));
        }
    }

    private synchronized void remove_Invite(UUID inviterUUID, UUID joinerUUID) {
        plugin.groupInviteManager.playerCoolDownMap.remove(joinerUUID);
        plugin.invites.get(joinerUUID).remove(inviterUUID);
    }

    private synchronized void create_Group_And_Remove_Player_Invite(UUID inviterUUID, UUID joinerUUID) {
        Set<UUID> Group = new HashSet<>();
        Group.add(joinerUUID);
        Group.add(inviterUUID);
        plugin.allGroups.add(Group);
        remove_Invite(inviterUUID, joinerUUID);
    }

    public boolean isPlayerInGroup(Player player) {
        return plugin.allGroups.stream().anyMatch(group -> group.contains(player.getUniqueId()));
    }

    public Set<UUID> getPlayersInGroupOfPlayer(Player player) {
        return plugin.allGroups.stream()
                .filter(group -> group.contains(player.getUniqueId()))
                .findFirst()
                .orElse(null);
    }

    public void joinGroup(Player joiner, Player inviter) {
        UUID joinerUUID = joiner.getUniqueId();
        UUID inviterUUID = inviter.getUniqueId();
        if (plugin.invites.get(joinerUUID) == null || plugin.invites.get(joinerUUID).isEmpty()) {
            messenger.info(joiner, "You have no invites");
            return;
        }
        boolean isJoinerInGroup = isPlayerInGroup(joiner);
        boolean isInviterInGroup = isPlayerInGroup(inviter);
        if (!isJoinerInGroup && !isInviterInGroup) {
            // Just create a new group
            create_Group_And_Remove_Player_Invite(inviterUUID, joinerUUID);
            messenger.normal(joiner, "You are now in a group with " + Messenger.MAIN_GENERAL + inviter.getName() + Messenger.NORMAL_GENERAL + " .");
            messenger.normal(inviter, "You are now in a group with " + Messenger.MAIN_GENERAL + joiner.getName() + Messenger.NORMAL_GENERAL + " .");
            if (getPlayersInGroupOfPlayer(inviter).size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
                messenger.warn(inviter, "Your group has reached the player limit.");
            }

        } else if (isJoinerInGroup && isInviterInGroup) {
            // They are both in group
            // joiner must leave and join the inviter's group
            Set<UUID> inviterGroup = getPlayersInGroupOfPlayer(inviter);
            if (inviterGroup.contains(joinerUUID)) {
                messenger.info(inviter, "You are in the same group.");
                return;
            }
            if (inviterGroup.size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
                messenger.warn(inviter, "You have reached the max player limit in your group.");
                messenger.warn(joiner, inviter.getName() + Messenger.WARN_GENERAL + " has reached the max player limit in the group.");
                return;
            }
            join_Group_And_Remove_Invite(joiner, inviter);

        } else if (isJoinerInGroup) {
            // Joiner must leave the group. The inviter is alone
            leaveGroup(joiner);
            create_Group_And_Remove_Player_Invite(inviterUUID, joinerUUID);
            messenger.normal(inviter, "You're now in a group with " + Messenger.MAIN_GENERAL + joiner.getName() + Messenger.NORMAL_GENERAL + " .");
            update_The_PlayAgain_For_The_Group(inviter);

        } else {
            // Joiner is alone must join the inviter's group
            if (getPlayersInGroupOfPlayer(inviter).size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
                messenger.warn(inviter, "You have reached the max player limit in your group");
                messenger.warn(joiner, inviter.getName() + Messenger.WARN_GENERAL + " has reached the max player limit in the group.");
                return;
            }
            join_Group_And_Remove_Invite(joiner, inviter);
        }
        if (!inviter.getInventory().contains(ItemManager.groupLeave)) {
            inviter.getInventory().setItem(8, ItemManager.groupLeave);
        }
        if (!joiner.getInventory().contains(ItemManager.groupLeave)) {
            joiner.getInventory().setItem(8, ItemManager.groupLeave);
        }
        plugin.allGroups.removeIf(Set::isEmpty);
    }

    public void helpGroupMenu(Player player) {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        BookMeta bookMeta = (BookMeta) meta;
        assert meta != null;
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bookMeta.addPage(Messenger.COMMAND_GENERAL + "/fm group invite " + Messenger.MAIN_GENERAL + "[player name] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "Invites the player whose name you have entered. The player that you invite will have 5 minutes to accept the invite.");
        bookMeta.addPage(Messenger.COMMAND_GENERAL + "/fm group accept " + Messenger.MAIN_GENERAL + "[player name] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "You have to accept the invite of the player that you have specified.");
        bookMeta.addPage(Messenger.COMMAND_GENERAL + "/fm group leave " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "You will leave the group.");
        bookMeta.addPage(Messenger.COMMAND_GENERAL + "/fm group help " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "Shows this menu.");
        bookMeta.addPage(Messenger.COMMAND_GENERAL + "/fm group chat " + Messenger.MAIN_GENERAL + "[message] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "Chat with your group.");
        bookMeta.addPage(Messenger.COMMAND_GENERAL + "/fm group kick " + Messenger.MAIN_GENERAL + "[player] [optional: reason] " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "Kicks specified player from your group.");
        bookMeta.addPage(Messenger.COMMAND_GENERAL + "/fm group list " + Messenger.ERROR_STYLE + ">- " + Messenger.COMMAND_DIS + "You can see the group members.");
        bookMeta.setAuthor(player.getName());
        bookMeta.setTitle(messenger.pluginName);
        item.setItemMeta(meta);
        plugin.playerOpenBook(player, item);
    }
}
