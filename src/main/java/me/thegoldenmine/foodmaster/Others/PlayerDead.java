package me.thegoldenmine.foodmaster.Others;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class PlayerDead {
    private final FoodMaster plugin;

    public PlayerDead(FoodMaster main) {
        plugin = main;
    }

    public synchronized void thePlayerIsDead(Player player) {
        String name = plugin.game.getGameName(player);
        UUID uuid = player.getUniqueId();
        plugin.stillAlive.get(name).remove(uuid);
        plugin.kitsCoolDown.playerCoolDownMap.remove(uuid);
        plugin.kitPowerCoolDown.playerCoolDownMap.remove(uuid);
        if (plugin.stillAlive.get(name).size() == 1) {
            List<UUID> alivePlayer = new ArrayList<>(plugin.stillAlive.get(name));
            UUID alive = alivePlayer.get(0);
            if (alive != null) {
                Player player1 = Bukkit.getPlayer(alive);
                if (player1 != null) {
                    Set<UUID> group = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
                    for (UUID uuid1 : group) {
                        if (uuid1 != null) {
                            Player player2 = Bukkit.getPlayer(uuid1);
                            if (player2 != null) {
                                plugin.endTheGame.endTheGameWithStatus(player2);
                            }
                        }
                    }
                    for (UUID uuid1 : group) {
                        if (uuid1 != null) {
                            plugin.inGameKills.remove(uuid1);
                            plugin.inGameDeaths.remove(uuid1);
                            plugin.playersInBlueTeams.remove(uuid1);
                            plugin.playersInCyanTeams.remove(uuid1);
                            plugin.playersInRedTeams.remove(uuid1);
                            plugin.playersInGreenTeams.remove(uuid1);
                            plugin.playersInYellowTeams.remove(uuid1);
                            plugin.playersRandomTeam.remove(uuid1);
                            plugin.playersChoiceFoodGame.remove(uuid);
                            plugin.playersInFoodGame.remove(uuid);
                            plugin.FoodGameWinner.remove(uuid);
                        }
                    }
                    for (UUID uuid1 : plugin.winners) {
                        if (uuid1 != null) {
                            Player player11 = Bukkit.getPlayer(uuid1);
                            if (player11 != null) {
                                plugin.giveOneWinToPlayer.givePlayerWin(player11);
                            }
                        }
                    }
                    for (UUID uuid1 : plugin.losses) {
                        if (uuid1 != null) {
                            Player player11 = Bukkit.getPlayer(uuid1);
                            if (player11 != null) {
                                plugin.giveOneLoseToPlayer.givePlayerLose(player11);
                            }
                        }
                    }
                    plugin.winners.clear();
                    plugin.losses.clear();
                }
            }
        } else {
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
            plugin.playersInFoodGame.remove(uuid);
            plugin.playersPlayingPvEZombie.remove(uuid);
            plugin.playersPlayingPvESpider.remove(uuid);
            plugin.playersPlayingPvEEnderman.remove(uuid);
            plugin.playersPlayingPvESkeleton.remove(uuid);
            plugin.playersPlayingPvESlime.remove(uuid);
            // others
            plugin.kickedPlayers.remove(uuid);
            plugin.ReadySystem.remove(uuid);
            plugin.ReadyPlayers.remove(uuid);
            plugin.lives.remove(uuid);
            AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert attributeInstance != null;
            attributeInstance.setBaseValue(20);
            player.setHealth(20);
            player.removePotionEffect(PotionEffectType.SPEED);
            player.getInventory().clear();
            Location loc = player.getLocation();
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                    if (uuids != null) {
                        Player playerInGroup = Bukkit.getPlayer(uuids);
                        if (playerInGroup != null) {
                            // FIREWORKS_SPARK
                            // END_ROD
                            playerInGroup.spawnParticle(Particle.END_ROD, loc.add(0, 1, 0), 10, 0.000001, 0.000001, 0.000001, 0.3);
                            playerInGroup.spawnParticle(Particle.FIREWORKS_SPARK, loc.add(0, 1, 0), 10, 0.000001, 0.000001, 0.000001, 0.3);
                        }
                    }
                }
            } else {
                for (Player playerInGroup : Bukkit.getOnlinePlayers()) {
                    if (playerInGroup != null) {
                        // FIREWORKS_SPARK
                        // END_ROD
                        playerInGroup.spawnParticle(Particle.END_ROD, loc.add(0, 1, 0), 10, 0.000001, 0.000001, 0.000001, 0.3);
                        playerInGroup.spawnParticle(Particle.FIREWORKS_SPARK, loc.add(0, 1, 0), 10, 0.000001, 0.000001, 0.000001, 0.3);
                    }
                }
            }
            player.setGameMode(GameMode.SPECTATOR);
            if (plugin.mainConfig.getStrGame("message_on_death") != null && !plugin.mainConfig.getStrGame("message_on_death").equalsIgnoreCase("") && !plugin.mainConfig.getStrGame("message_on_death").equalsIgnoreCase("empty")) {
                player.sendMessage(plugin.mainConfig.getStrGame("message_on_death"));
            }
        }
    }
}
