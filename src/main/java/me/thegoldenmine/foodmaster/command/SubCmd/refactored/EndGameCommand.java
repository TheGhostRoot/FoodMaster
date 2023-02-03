package me.thegoldenmine.foodmaster.command.SubCmd.refactored;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.GroupManager;
import me.thegoldenmine.foodmaster.Messenger;
import me.thegoldenmine.foodmaster.Utils;
import org.bukkit.entity.Player;

public class EndGameCommand {
    private final FoodMaster plugin;
    private final Utils utils;
    private final GroupManager groupManager;
    private final Messenger messenger;

    public EndGameCommand(FoodMaster main) {
        plugin = main;
        utils = new Utils(plugin);
        groupManager = new GroupManager(plugin);
        messenger = new Messenger(plugin);
    }
    // TODO refactor this command and don't make tha player leave the group


    public void end_the_game_for_one_player(Player player) {}

    public void end_the_game(Player player) {}
}
