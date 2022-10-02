package me.thegoldenmine.foodmaster.Listeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GroupLeave implements Listener {
    private final FoodMaster plugin;

    public GroupLeave(FoodMaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public synchronized void onLeave(PlayerInteractEvent event) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        Action action = event.getAction();
        Player player = event.getPlayer();
        Location endLoc = plugin.mainConfig.getLocationMain("end_location");
        boolean b = action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK);
        if (b && player.getInventory().getItemInMainHand().equals(ItemManager.groupLeave)) {
            if (endLoc == null) {
                if (player.hasPermission("foodm.staff")) {
                    player.sendMessage(ERROR + "The end location is not set. Use " + ChatColor.DARK_PURPLE + "" + italic + "/fm set end");
                } else {
                    player.sendMessage(ERROR + "One of the locations isn't set.");
                }
            } else {
                if (plugin.playerGroup.isPlayerInGroup(player)) {
                    if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                        Set<UUID> group = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
                        // kits
                        UUID uuid = player.getUniqueId();
                        // tell everyone that you left the group
                        for (UUID uuidss : group) {
                            Player playerss = Bukkit.getPlayer(uuidss);
                            if (playerss != null && !playerss.getUniqueId().equals(player.getUniqueId())) {
                                playerss.sendMessage(NORMAL + "" + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " just left the group.");
                            }
                        }
                        // if group sire is 2 then tp the players to end loc and then remove the group from everywhere
                        if (group.size() == 2) {
                            for (UUID uuids : group) {
                                if (uuids != null) {
                                    Player playersInGroup = Bukkit.getPlayer(uuids);
                                    if (playersInGroup != null && plugin.waitingLobby.isPlayerInWaitingLobby(playersInGroup)) {
                                        playersInGroup.teleport(endLoc);
                                    }
                                }
                            }
                            for (UUID uuids : group) {
                                if (uuids != null) {
                                    Player playerInPlayer = Bukkit.getPlayer(uuids);
                                    if (playerInPlayer != null && plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                                        plugin.waitingLobby.removePlayerFromWaitedLobby(playerInPlayer);
                                    }
                                }
                            }
                            plugin.waitingLobby.removePlayerFromWaitedLobby(player);
                            plugin.playersInPotatoKit.removeAll(group);
                            plugin.playersInBreadKit.removeAll(group);
                            plugin.playersInMelonKit.removeAll(group);
                            plugin.playersInCookieKit.removeAll(group);
                            plugin.playersInFishKit.removeAll(group);
                            plugin.playersRandomKit.removeAll(group);
                            // teams
                            plugin.playersInBlueTeams.removeAll(group);
                            plugin.playersInCyanTeams.removeAll(group);
                            plugin.playersInRedTeams.removeAll(group);
                            plugin.playersInGreenTeams.removeAll(group);
                            plugin.playersInYellowTeams.removeAll(group);
                            plugin.playersRandomTeam.removeAll(group);
                            // others
                            plugin.players2Teams.removeAll(group);
                            plugin.players3Teams.removeAll(group);
                            plugin.players4Teams.removeAll(group);
                            plugin.players5Teams.removeAll(group);
                            plugin.playersThatChoice5Teams.removeAll(group);
                            plugin.playersThatChoice4Teams.removeAll(group);
                            plugin.playersThatChoice2Teams.removeAll(group);
                            plugin.playersThatChoice3Teams.removeAll(group);
                            plugin.locOfPlayersInWaitingLobby.remove(group);
                            // choice
                            plugin.playersChoiceFreeForAll.removeAll(group);
                            plugin.kickedPlayers.removeAll(group);
                            plugin.playersChoiceFoodGame.removeAll(group);
                            plugin.playersInFoodGame.removeAll(group);
                            plugin.playersPlayingPvESpider.removeAll(group);
                            plugin.playersPlayingPvESkeleton.removeAll(group);
                            plugin.playersPlayingPvEZombie.removeAll(group);
                            plugin.playersPlayingPvESlime.removeAll(group);
                            plugin.playersPlayingPvEEnderman.removeAll(group);
                            plugin.playersChoicePvESpider.removeAll(group);
                            plugin.playersChoicePvESkeleton.removeAll(group);
                            plugin.playersChoicePvEZombie.removeAll(group);
                            plugin.playersChoicePvEEnderman.removeAll(group);
                            plugin.playersChoicePvESlime.removeAll(group);
                        } else {
                            for (UUID uuidss : group) {
                                Player playerss = Bukkit.getPlayer(uuidss);
                                if (playerss != null && !playerss.getUniqueId().equals(player.getUniqueId())) {
                                    playerss.sendMessage(NORMAL + "" + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " just left the group.");
                                }
                            }
                            plugin.playersInPotatoKit.remove(uuid);
                            plugin.playersInBreadKit.remove(uuid);
                            plugin.playersInMelonKit.remove(uuid);
                            plugin.playersInCookieKit.remove(uuid);
                            plugin.playersInFishKit.remove(uuid);
                            plugin.playersRandomKit.remove(uuid);
                            // teams
                            plugin.playersInBlueTeams.remove(uuid);
                            plugin.playersInCyanTeams.remove(uuid);
                            plugin.playersInRedTeams.remove(uuid);
                            plugin.playersInGreenTeams.remove(uuid);
                            plugin.playersInYellowTeams.remove(uuid);
                            plugin.playersRandomTeam.remove(uuid);
                            // others
                            plugin.players2Teams.remove(uuid);
                            plugin.players3Teams.remove(uuid);
                            plugin.players4Teams.remove(uuid);
                            plugin.players5Teams.remove(uuid);
                            plugin.playersThatChoice5Teams.remove(uuid);
                            plugin.playersThatChoice4Teams.remove(uuid);
                            plugin.playersThatChoice2Teams.remove(uuid);
                            plugin.playersThatChoice3Teams.remove(uuid);
                            plugin.playersPlayingPvESpider.remove(uuid);
                            plugin.playersPlayingPvESkeleton.remove(uuid);
                            plugin.playersPlayingPvEZombie.remove(uuid);
                            plugin.playersPlayingPvESlime.remove(uuid);
                            plugin.playersPlayingPvEEnderman.remove(uuid);
                            plugin.playersChoicePvESpider.remove(uuid);
                            plugin.playersChoicePvESkeleton.remove(uuid);
                            plugin.playersChoicePvEZombie.remove(uuid);
                            plugin.playersChoicePvEEnderman.remove(uuid);
                            plugin.playersChoicePvESlime.remove(uuid);
                            plugin.playersChoiceFoodGame.remove(uuid);
                            plugin.playersInFoodGame.remove(uuid);
                            plugin.playersPlayingPvESpider.remove(uuid);
                            plugin.playersPlayingPvESkeleton.remove(uuid);
                            plugin.playersPlayingPvEZombie.remove(uuid);
                            plugin.playersPlayingPvESlime.remove(uuid);
                            plugin.playersPlayingPvEEnderman.remove(uuid);
                            plugin.playersChoicePvESpider.remove(uuid);
                            plugin.playersChoicePvESkeleton.remove(uuid);
                            plugin.playersChoicePvEZombie.remove(uuid);
                            plugin.playersChoicePvEEnderman.remove(uuid);
                            plugin.playersChoicePvESlime.remove(uuid);
                            Location loc = plugin.locOfPlayersInWaitingLobby.get(group);
                            group.remove(uuid);
                            plugin.locOfPlayersInWaitingLobby.put(group, loc);
                            // choice
                            plugin.playersChoiceFreeForAll.remove(uuid);
                            plugin.kickedPlayers.remove(uuid);
                            player.teleport(endLoc);
                        }
                        plugin.allGroups.removeIf(Set::isEmpty);
                    } else if (plugin.game.isPlayerInGame(player)) {
                        for (UUID uuidss : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                            Player playerss = Bukkit.getPlayer(uuidss);
                            if (playerss != null && !playerss.getUniqueId().equals(player.getUniqueId())) {
                                playerss.sendMessage(NORMAL + "" + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " just left the group.");
                            }
                        }
                        if (plugin.playerGroup.getPlayersInGroupOfPlayer(player).size() == 2) {
                            if (plugin.playerPvE.isPlayerPlayingPvE(player)) {
                                plugin.endTheGame.endThePvE(player);
                            } else {
                                for (UUID uuid1 : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                                    if (uuid1 != null) {
                                        Player playerGroup = Bukkit.getPlayer(uuid1);
                                        if (playerGroup != null) {
                                            plugin.endTheGame.endTheGameWithStatus(playerGroup);
                                        }
                                    }
                                }
                                for (UUID uuid1 : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                                    if (uuid1 != null) {
                                        plugin.inGameKills.remove(uuid1);
                                        plugin.inGameDeaths.remove(uuid1);
                                        plugin.playersInBlueTeams.remove(uuid1);
                                        plugin.playersInCyanTeams.remove(uuid1);
                                        plugin.playersInRedTeams.remove(uuid1);
                                        plugin.playersInGreenTeams.remove(uuid1);
                                        plugin.playersInYellowTeams.remove(uuid1);
                                        plugin.playersRandomTeam.remove(uuid1);
                                        plugin.playersChoiceFoodGame.remove(uuid1);
                                        plugin.playersInFoodGame.remove(uuid1);
                                        plugin.FoodGameWinner.remove(uuid1);
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
                        } else {
                            plugin.game.removePlayerFromGame(player);
                            UUID uuid = player.getUniqueId();
                            plugin.playersInPotatoKit.remove(uuid);
                            plugin.playersInBreadKit.remove(uuid);
                            plugin.playersInMelonKit.remove(uuid);
                            plugin.playersInCookieKit.remove(uuid);
                            plugin.playersInFishKit.remove(uuid);
                            plugin.playersRandomKit.remove(uuid);
                            // teams
                            plugin.players2Teams.remove(uuid);
                            plugin.players3Teams.remove(uuid);
                            plugin.players4Teams.remove(uuid);
                            plugin.players5Teams.remove(uuid);
                            // others
                            plugin.playersThatChoice5Teams.remove(uuid);
                            plugin.playersThatChoice4Teams.remove(uuid);
                            plugin.playersThatChoice2Teams.remove(uuid);
                            if (plugin.playerGroup.isPlayerInGroup(player)) {
                                plugin.locOfPlayersInWaitingLobby.remove(new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player)));
                            }
                            plugin.kickedPlayers.remove(uuid);
                            plugin.playersThatChoice3Teams.remove(uuid);
                            String name1 = plugin.game.getGameName(player);
                            plugin.stillAlive.remove(name1);
                            plugin.ReadySystem.remove(uuid);
                            plugin.ReadyPlayers.remove(uuid);
                            plugin.playersInFreeForAll.remove(uuid);
                            plugin.lives.remove(uuid);
                            plugin.playersChoiceFreeForAll.remove(uuid);
                            plugin.playersPlayingPvESpider.remove(uuid);
                            plugin.playersPlayingPvESkeleton.remove(uuid);
                            plugin.playersPlayingPvEZombie.remove(uuid);
                            plugin.playersPlayingPvESlime.remove(uuid);
                            plugin.playersPlayingPvEEnderman.remove(uuid);
                            plugin.playersChoicePvESpider.remove(uuid);
                            plugin.playersChoicePvESkeleton.remove(uuid);
                            plugin.playersChoicePvEZombie.remove(uuid);
                            plugin.playersChoicePvEEnderman.remove(uuid);
                            plugin.playersChoicePvESlime.remove(uuid);
                            plugin.playersChoiceFoodGame.remove(uuid);
                            plugin.playersInFoodGame.remove(uuid);
                            // Health
                            AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                            assert attributeInstance != null;
                            attributeInstance.setBaseValue(20);
                            player.setHealth(20);
                            player.removePotionEffect(PotionEffectType.SPEED);
                            player.getInventory().clear();
                            player.setGameMode(GameMode.SURVIVAL);
                            player.teleport(endLoc);
                        }
                    }
                    player.sendTitle(gold + "" + italic + "You left your group", aqua + "" + italic + "" + plugin.playerGroup.getPlayerNamesFromGroupString(player).replaceAll("\"\"", "").replace("[", "").replace("]", ""), 2, 80, 2);
                    plugin.playerGroup.PlayerLeaveFromGroup(player);
                }
            }
        }
    }
}
