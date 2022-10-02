package me.thegoldenmine.foodmaster.Listeners.AntiGlitchListeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OnLeave implements Listener {
    private final FoodMaster plugin;

    public OnLeave(FoodMaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public synchronized void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            Set<UUID> players = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
            boolean isPlayerStartedAgame = plugin.game.isPlayerInGame(player) || plugin.waitingLobby.isPlayerInWaitingLobby(player);
            boolean b = players.size() - 1 == 1 || players.size() - 1 == 0 ;
            if (!players.isEmpty() && b && isPlayerStartedAgame) {
                if (plugin.playerPvE.isPlayerPlayingPvE(player)) {
                    plugin.endTheGame.endThePvE(player);
                } else {
                    for (UUID uuid : players) {
                        if (uuid != null) {
                            Player playerInG = Bukkit.getPlayer(uuid);
                            if (playerInG != null) {
                                plugin.endTheGame.endTheGameWithStatus(playerInG);
                            }
                        }
                    }
                    for (UUID uuid : players) {
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
                plugin.playerGroup.PlayerLeaveFromGroup(player);
            }
        } else if (plugin.game.isPlayerInGame(player)) {
            plugin.game.removePlayerFromGame(player);
            Set<String> strings = new HashSet<>(plugin.playersInGame.keySet());
            for (String name : strings) {
                if (plugin.playersInGame.get(name).size() == 1) {
                    plugin.playersInGame.remove(name);
                    return;
                }
            }
            if (player.hasPotionEffect(PotionEffectType.SPEED)) {
                player.removePotionEffect(PotionEffectType.SPEED);
            }
            plugin.endTheGame.endThePvE(player);
        }
        if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
            plugin.waitingLobby.removePlayerFromWaitedLobby(player);
        }
        UUID uuid = player.getUniqueId();
        // kits
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
        plugin.players2Teams.remove(uuid);
        plugin.players3Teams.remove(uuid);
        plugin.players4Teams.remove(uuid);
        plugin.winners.remove(uuid);
        plugin.playersPlayingPvESpider.remove(uuid);
        plugin.playersPlayingPvESkeleton.remove(uuid);
        plugin.playersPlayingPvEZombie.remove(uuid);
        plugin.playersChoicePvESpider.remove(uuid);
        plugin.playersChoicePvESkeleton.remove(uuid);
        plugin.playersChoicePvEZombie.remove(uuid);
        plugin.FoodGameWinner.remove(uuid);
        plugin.losses.remove(uuid);
        plugin.players5Teams.remove(uuid);
        // others
        plugin.playersThatChoice5Teams.remove(uuid);
        plugin.playersThatChoice4Teams.remove(uuid);
        plugin.playersThatChoice2Teams.remove(uuid);
        plugin.kickedPlayers.remove(uuid);
        plugin.playersThatChoice3Teams.remove(uuid);
        plugin.playAgain.remove(uuid);
        plugin.ReadySystem.remove(uuid);
        plugin.ReadyPlayers.remove(uuid);
        plugin.playersInFreeForAll.remove(uuid);
        plugin.playersChoiceFreeForAll.remove(uuid);
        plugin.inGameKills.remove(uuid);
        plugin.inGameDeaths.remove(uuid);
    }
}
