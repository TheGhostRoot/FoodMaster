package me.thegoldenmine.foodmaster.group.commands;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.group.GroupManager;
import org.bukkit.entity.Player;


public class GroupAccept {
    private FoodMaster plugin;

    public GroupAccept(FoodMaster plugin) {
        this.plugin = plugin;
    }

    public void acceptGroupInvite(Player invited, Player inviter) {
        // the invited player is the sender of the command/invitation
        GroupManager groupManager = new GroupManager(plugin);
        if (!groupManager.canJoin(invited, inviter)) {
            return;
        }
        groupManager.joinGroup(invited, inviter);
    }
}
