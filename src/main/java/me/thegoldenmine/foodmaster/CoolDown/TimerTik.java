package me.thegoldenmine.foodmaster.CoolDown;

import me.thegoldenmine.foodmaster.FoodMaster;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TimerTik {
    public BossBar bossBar;

    public TimerTik(FoodMaster plugin) {
        bossBar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SEGMENTED_12, BarFlag.DARKEN_SKY);
        bossBar.setVisible(true);

        new BukkitRunnable() {
            @Override
            public void run() {
                ChatColor darkGray = ChatColor.DARK_GRAY;
                ChatColor strikethrough = ChatColor.STRIKETHROUGH;
                ChatColor gold = ChatColor.GOLD;
                ChatColor bold = ChatColor.BOLD;
                ChatColor green = ChatColor.GREEN;
                ChatColor italic = ChatColor.ITALIC;
                String s;
                if (plugin.mainConfig.getStrMain("name") != null) {
                    s = " " + plugin.mainConfig.getStrMain("name") + " ";
                } else {
                    s = " FoodMaster ";
                }
                String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
                String gameName = null;
                List<Player> removePlayersFromBossBar = new ArrayList<>();
                for (Player players : bossBar.getPlayers()) {
                    if (players != null && players.isOnline() && !plugin.game.isPlayerInGame(players) && plugin.mainConfig.getBooleanGame("show_timer_boss_bar")) {
                        removePlayersFromBossBar.add(players);
                    }
                }
                for (Player player : removePlayersFromBossBar) {
                    bossBar.removePlayer(player);
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player != null && plugin.playerGroup.isPlayerInGroup(player) && plugin.game.isPlayerInGame(player) && !plugin.playerPvE.isPlayerPlayingPvE(player)) {
                        gameName = plugin.game.getGameName(player);
                        if (gameName != null) {
                            if (plugin.timer.containsKey(gameName)) {
                                int timeLeft = plugin.timer.get(gameName);   // get is null (int)
                                String conTime = plugin.time.getTime(timeLeft);
                                if (conTime != null) {
                                    Set<UUID> playersInGroupOfPlayer = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
                                    if (timeLeft <= 0 || conTime.equals("0") || conTime.equals("-1")) {
                                        for (UUID uuid : playersInGroupOfPlayer) {
                                            if (uuid != null) {
                                                Player playerGroup = Bukkit.getPlayer(uuid);
                                                if (playerGroup != null) {
                                                    if (bossBar.getPlayers().contains(playerGroup)) {
                                                        bossBar.removePlayer(playerGroup);
                                                    }
                                                    plugin.endTheGame.endTheGameWithStatus(playerGroup);
                                                }
                                            }
                                        }
                                        for (UUID uuid : playersInGroupOfPlayer) {
                                            if (uuid != null) {
                                                plugin.inGameKills.remove(uuid);
                                                plugin.inGameDeaths.remove(uuid);
                                                plugin.playersInBlueTeams.remove(uuid);
                                                plugin.playersInCyanTeams.remove(uuid);
                                                plugin.playersInRedTeams.remove(uuid);
                                                plugin.playersInGreenTeams.remove(uuid);
                                                plugin.playersInYellowTeams.remove(uuid);
                                                plugin.playersRandomTeam.remove(uuid);
                                                plugin.playersChoiceFoodGame.remove(uuid);
                                                plugin.playersInFoodGame.remove(uuid);
                                                plugin.FoodGameWinner.remove(uuid);
                                            }
                                        }
                                        for (UUID uuid : plugin.winners) {
                                            if (uuid != null) {
                                                Player player11 = Bukkit.getPlayer(uuid);
                                                if (player11 != null) {
                                                    plugin.giveOneWinToPlayer.givePlayerWin(player11);
                                                }
                                            }
                                        }
                                        for (UUID uuid : plugin.losses) {
                                            if (uuid != null) {
                                                Player player11 = Bukkit.getPlayer(uuid);
                                                if (player11 != null) {
                                                    plugin.giveOneLoseToPlayer.givePlayerLose(player11);
                                                }
                                            }
                                        }
                                        plugin.winners.clear();
                                        plugin.losses.clear();
                                    }
                                    if (plugin.mainConfig.getBooleanGame("show_timer_boss_bar")) {
                                        switch (conTime) {
                                            case "1":
                                                bossBar.setTitle(ChatColor.DARK_RED + "" + conTime);
                                                player.sendMessage(NORMAL + "" + ChatColor.DARK_RED + "1" + green + "" + italic + " second left.");
                                                break;
                                            case "2":
                                                bossBar.setTitle(ChatColor.RED + "" + conTime);
                                                player.sendMessage(NORMAL + "" + ChatColor.RED + "2" + green + "" + italic + " seconds left.");
                                                break;
                                            case "3":
                                                bossBar.setTitle(ChatColor.GOLD + "" + conTime);
                                                player.sendMessage(NORMAL + "" + ChatColor.GOLD + "3" + green + "" + italic + " seconds left.");
                                                break;
                                            default:
                                                bossBar.setTitle(plugin.mainConfig.getStrGame("game_timer_color") + "" + conTime);
                                                break;
                                        }
                                        if (bossBar.getPlayers().isEmpty() || !bossBar.getPlayers().contains(player)) {
                                            bossBar.addPlayer(player);
                                        }
                                    } else if (plugin.mainConfig.getBooleanGame("show_timer_above_inventory")) {
                                        switch (conTime) {
                                            case "1":
                                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED + "" + conTime));
                                                player.sendMessage(NORMAL + "" + ChatColor.DARK_RED + "1" + green + "" + italic + " second left.");
                                                break;
                                            case "2":
                                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "" + conTime));
                                                player.sendMessage(NORMAL + "" + ChatColor.RED + "2" + green + "" + italic + " seconds left.");
                                                break;
                                            case "3":
                                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + "" + conTime));
                                                player.sendMessage(NORMAL + "" + ChatColor.GOLD + "3" + green + "" + italic + " seconds left.");
                                                break;
                                            default:
                                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(plugin.mainConfig.getStrGame("game_timer_color") + "" + conTime));
                                                break;
                                        }
                                    }
                                }
                            } else {
                                plugin.timer.put(gameName, plugin.mainConfig.getIntGame("game_time_seconds"));
                            }
                        }
                    }
                }
                if (gameName != null) {
                    if (plugin.timer.containsKey(gameName)) {
                        int time = plugin.timer.get(gameName);
                        plugin.timer.put(gameName, time - 1);
                    } else {
                        int total = plugin.mainConfig.getIntGame("game_time_seconds");
                        plugin.timer.put(gameName, total);
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 21);
    }
}
