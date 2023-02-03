package me.thegoldenmine.foodmaster.command.SubCmd.refactored;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Messenger;
import me.thegoldenmine.foodmaster.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.thegoldenmine.foodmaster.Messenger.STATS_ARROW_CLOSE;
import static me.thegoldenmine.foodmaster.Messenger.STATS_ARROW_OPEN;

public class StatsCommand {
    private final FoodMaster plugin;
    private final Messenger messenger;
    private final Utils utils;

    public StatsCommand(FoodMaster main) {
        plugin = main;
        messenger = new Messenger(plugin);
        utils = new Utils(plugin);
    }

    private void stats(Player player) {
        UUID uuid = player.getUniqueId();
        String uuid_string = uuid.toString();
        int deaths = plugin.mainConfig.getIntDeath(uuid_string);
        int kills = plugin.mainConfig.getIntKill(uuid_string);
        int wins = plugin.mainConfig.getIntWin(uuid_string);
        int losses = plugin.mainConfig.getIntLose(uuid_string);
        messenger.no_prefix(player, "  Kills: "+ kills);
        messenger.no_prefix(player, "  Deaths: "+ deaths);
        messenger.no_prefix(player, "  K/D: "+ utils.getKD(player));
        messenger.no_prefix(player, " ");
        messenger.no_prefix(player, "  Wins: "+ wins);
        messenger.no_prefix(player, "  Losses : "+ losses);
        messenger.no_prefix(player, "  W/L: "+ utils.getWL(player));
        if (plugin.game.isPlayerInGame(player)) {
            if (!plugin.inGameKills.containsKey(uuid)) {
                plugin.inGameKills.put(uuid, 0);
            }
            if (!plugin.inGameDeaths.containsKey(uuid)) {
                plugin.inGameDeaths.put(uuid, 0);
            }
            int deaths_in_game = plugin.inGameDeaths.get(uuid);
            int kills_in_game = plugin.inGameKills.get(uuid);
            float KD_in_game = 0;
            if (deaths_in_game != 0) {
                KD_in_game = (float) kills_in_game / deaths_in_game;
            }
            messenger.no_prefix(player, Messenger.STATS_ARROW_OPEN +
                    Messenger.MAIN_GENERAL + "Game Stats " + STATS_ARROW_CLOSE);
            messenger.no_prefix(player, "  Kills: "+ kills_in_game);
            messenger.no_prefix(player, "  Deaths: "+ deaths_in_game);
            messenger.no_prefix(player, "  K/D: "+ KD_in_game);
        }
        messenger.no_prefix(player, Messenger.INFO_STYLE + "<--====-----====-->");
    }

    public void statsCommand(Player player, String[] args) {
        String player_stats_title = STATS_ARROW_OPEN +
                Messenger.MAIN_GENERAL + "" + player.getName()
                + "'s" + Messenger.MAIN_STYLE + " Stats " + STATS_ARROW_CLOSE;
        if (args.length > 1) {
            Player givenPlayer = Bukkit.getPlayer(args[1]);
            if (givenPlayer == null) {
                messenger.error(player, "This player is invalid.");
                return;
            }
            messenger.no_prefix(givenPlayer, player_stats_title);
            messenger.no_prefix(givenPlayer, " ");
            stats(givenPlayer);
        } else {
            messenger.no_prefix(player, Messenger.STATS_ARROW_OPEN +
                    Messenger.MAIN_STYLE + "Your Stats " + STATS_ARROW_CLOSE);
            stats(player);
        }
    }
}
