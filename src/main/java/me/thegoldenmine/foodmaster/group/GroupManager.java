package me.thegoldenmine.foodmaster.group;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import me.thegoldenmine.foodmaster.Messenger;
import static org.bukkit.ChatColor.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GroupManager {

    FoodMaster plugin;
    private final Messenger messenger;

    public GroupManager(FoodMaster main) {
        plugin = main;
        messenger = new Messenger(plugin);
    }

    public boolean canJoin(Player invited, Player inviter) {
        // Inviter checkers
        if (!inviter.isOnline()) {
            messenger.error(invited, inviter.getName() + "is not online.");
            return false;
        } else if (plugin.game.isPlayerInGame(inviter)) {
            messenger.error(invited, inviter.getName() + " is in-game so you can't join the group.");
            return false;
        } else if (plugin.waitingLobby.isPlayerInWaitingLobby(inviter)) {
            messenger.error(invited, inviter.getName() + " is in the waiting lobby so you can't join the group.");
            return false;
        }

        // invited checkers
         else if (!invited.isOnline()) {
             messenger.error(inviter, invited.getName() + "is not online.");
             return false;
        } else if (plugin.game.isPlayerInGame(invited)) {
            messenger.error(inviter, "You are in-game so you can't join "+ Messenger.MAIN_GENERAL + inviter.getName() + Messenger.ERROR_GENERAL +"'s group.");
            return false;
        } else if (plugin.waitingLobby.isPlayerInWaitingLobby(invited)) {
            messenger.error(inviter, "You are in the waiting lobby so you can't join " + Messenger.MAIN_GENERAL + inviter.getName() + Messenger.ERROR_GENERAL + "'s group.");
            return false;
        } else return plugin.invites.get(invited.getUniqueId()) != null;

    }

    public void update_The_PlayAgain_For_The_Group(Player player) {
        Set<UUID> playersInGroupOfPlayer = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
        for (UUID uuid1 : playersInGroupOfPlayer) {
            if (uuid1 != null && plugin.playAgain.containsKey(uuid1)) {
                String game = plugin.playAgain.get(uuid1);
                for (UUID uuid3 : playersInGroupOfPlayer) {
                    if (uuid3 != null) {
                        plugin.playAgain.put(uuid3, game);
                    }
                }
                break;
            }
        }
    }

    public void player_Leave_Group_Send_Messages(Player playerLeaver) {
        for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(playerLeaver)) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && !player.equals(playerLeaver)) {
                messenger.info(player, playerLeaver.getName() + Messenger.NORMAL_GENERAL + " has left the group.");
            }
        }
    }

    private void leaveFromGroup(Player player) {
        player_Leave_Group_Send_Messages(player);
        plugin.playerGroup.getPlayersInGroupOfPlayer(player).remove(player.getUniqueId());
    }

    private void join_Group_And_Remove_Invite(Player joiner, Player inviter) {
        leaveFromGroup(joiner);
        UUID joinerUUID = joiner.getUniqueId();
        plugin.playerGroup.getPlayersInGroupOfPlayer(inviter).add(joinerUUID);
        remove_Invite(inviter.getUniqueId(), joinerUUID);
        update_The_PlayAgain_For_The_Group(inviter);
        String inviterPlayers = plugin.playerGroup.getPlayerNamesFromGroupString(inviter).replace("[", "").replace("]", "").replace("\"\"", "");
        String joinerPlayers = plugin.playerGroup.getPlayerNamesFromGroupString(joiner).replace("[", "").replace("]", "").replace("\"\"", "");
        Join_Group_Message(joiner, inviter);
        messenger.normal(joiner, "You have left the group of " + Messenger.MAIN_GENERAL + joinerPlayers + Messenger.NORMAL_GENERAL+ " players and joined the group of " + Messenger.MAIN_GENERAL + inviterPlayers + Messenger.NORMAL_GENERAL + " players.");
        if (plugin.playerGroup.getPlayersInGroupOfPlayer(inviter).size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
            messenger.warn(inviter, "You have reached the max player limit in your group");
            messenger.warn(joiner, inviter.getName() + Messenger.WARN_GENERAL + " has reached the max player limit in the group.");
        }
    }

    public void Join_Group_Message(Player playerJoiner, Player inviter) {
        for (UUID players : plugin.playerGroup.getPlayersInGroupOfPlayer(inviter)) {
            Player playersInGroup = Bukkit.getPlayer(players);
            if (playersInGroup != null && playersInGroup != playerJoiner) {
                messenger.info(playersInGroup, playerJoiner.getName() + Messenger.INFO_GENERAL +" has joined the group.");
            }
        }
    }

    private void remove_Invite(UUID inviterUUID, UUID joinerUUID) {
        plugin.groupInviteManager.playerCoolDownMap.remove(joinerUUID);
        plugin.invites.get(joinerUUID).remove(inviterUUID);
    }

    private void create_Group_And_Remove_Player_Invite(UUID inviterUUID, UUID joinerUUID) {
        Set<UUID> Group = new HashSet<>();
        Group.add(joinerUUID);
        Group.add(inviterUUID);
        plugin.allGroups.add(Group);
        remove_Invite(inviterUUID, joinerUUID);
    }

    public void joinGroup(Player joiner, Player inviter) {
        UUID joinerUUID = joiner.getUniqueId();
        UUID inviterUUID = inviter.getUniqueId();
        if (plugin.invites.get(joinerUUID) == null || plugin.invites.get(joinerUUID).isEmpty()) {
            messenger.info(joiner, "You have no invites");
            return;
        }
        boolean isJoinerInGroup = plugin.playerGroup.isPlayerInGroup(joiner);
        boolean isInviterInGroup = plugin.playerGroup.isPlayerInGroup(inviter);
        if (!isJoinerInGroup && !isInviterInGroup) {
            // Just create a new group
            create_Group_And_Remove_Player_Invite(inviterUUID, joinerUUID);
            messenger.normal(joiner, "You are now in a group with " + Messenger.MAIN_GENERAL + inviter.getName() + Messenger.NORMAL_GENERAL + " .");
            messenger.normal(inviter, "You are now in a group with " + Messenger.MAIN_GENERAL + joiner.getName() + Messenger.NORMAL_GENERAL + " .");
            if (plugin.playerGroup.getPlayersInGroupOfPlayer(inviter).size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
                messenger.warn(inviter, "Your group has reached the player limit.");
            }

        } else if (isJoinerInGroup && isInviterInGroup) {
            // They are both in group
            // joiner must leave and join the inviter's group
            Set<UUID> inviterGroup = plugin.playerGroup.getPlayersInGroupOfPlayer(inviter);
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
            leaveFromGroup(joiner);
            create_Group_And_Remove_Player_Invite(inviterUUID, joinerUUID);
            messenger.normal(inviter, "You're now in a group with " + Messenger.MAIN_GENERAL + joiner.getName() + Messenger.NORMAL_GENERAL + " .");
            update_The_PlayAgain_For_The_Group(inviter);

        } else {
            // Joiner is alone must join the inviter's group
            if (plugin.playerGroup.getPlayersInGroupOfPlayer(inviter).size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
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
