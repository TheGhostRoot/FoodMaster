package me.thegoldenmine.foodmaster.group;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import me.thegoldenmine.foodmaster.Messenger;
import me.thegoldenmine.foodmaster.Others.PlayerGroup;
import me.thegoldenmine.foodmaster.group.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class GroupManager {

    FoodMaster plugin;
    private final Messenger messenger;

    private final GroupAccept groupAccept;
    private final GroupLeave groupLeave;
    private final GroupKick groupKick;
    private final GroupInvite groupInvite;
    private final GroupList groupList;
    private final GroupChat groupChat;

    public GroupManager(FoodMaster main) {
        plugin = main;
        messenger = new Messenger(plugin);

        groupAccept = new GroupAccept(plugin);
        groupLeave = new GroupLeave(plugin);
        groupKick = new GroupKick(plugin);
        groupInvite = new GroupInvite(plugin);
        groupList = new GroupList(plugin);
        groupChat = new GroupChat(plugin);
    }

    public void acceptCommand(Player player, String[] args) {
        Player inviter = Bukkit.getPlayer(args[2]);
        groupAccept.acceptGroupInvite(player, inviter);
    }

    public void leaveCommand(Player player) {
        groupLeave.leaveFromGroup(player);
    }

    public void kickCommand(Player player) {

    }

    public void inviteCommand(Player player) {

    }

    public void listCommand(Player player) {

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
        String inviterPlayers = getPlayerNamesFromGroupString(inviter);
        String joinerPlayers = getPlayerNamesFromGroupString(joiner);
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
}
