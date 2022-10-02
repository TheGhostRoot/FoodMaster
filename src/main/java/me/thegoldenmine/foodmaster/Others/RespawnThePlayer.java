package me.thegoldenmine.foodmaster.Others;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.*;

public class RespawnThePlayer {
    private final FoodMaster plugin;

    public RespawnThePlayer(FoodMaster main) {
        plugin = main;
    }

    public synchronized void respawnThePlayer(Player playerDead) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
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
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        double hp = playerDead.getHealthScale();
        playerDead.setHealth(hp);
        String GameName = plugin.game.getGameName(playerDead);
        UUID uuid3 = playerDead.getUniqueId();
        plugin.kitsCoolDown.playerCoolDownMap.remove(uuid3);
        plugin.kitPowerCoolDown.playerCoolDownMap.remove(uuid3);
        if (plugin.playerPvE.isPlayerPlayingPvE(playerDead) && !plugin.playerGroup.isPlayerInGroup(playerDead)) {
            if (!plugin.GameSpawnPoints.containsKey(GameName)) {
                playerDead.sendMessage(ERROR + "There is no game with name " + gold + "" + GameName);
                return;
            }
            if (plugin.GameSpawnPoints.get(GameName) == null) {
                playerDead.sendMessage(ERROR + "There are no game spawn points set.");
                return;
            }
            Set<String> namesOfGameLoc = plugin.GameSpawnPoints.get(GameName);
            int theIndex = new Random().nextInt(namesOfGameLoc.size());
            List<String> list1 = new ArrayList<>(namesOfGameLoc);
            String theNameOfLoc = list1.get(theIndex);
            if (plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                while (plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                    int theIndex2 = new Random().nextInt(namesOfGameLoc.size());
                    theNameOfLoc = list1.get(theIndex2);
                }
            }
            if (plugin.kickedPlayers.contains(uuid3)) {
                if (plugin.playerGroup.isPlayerInGroup(playerDead)) {
                    for (UUID uuid2 : plugin.playerGroup.getPlayersInGroupOfPlayer(playerDead)) {
                        if (uuid2 != null) {
                            Player player1 = Bukkit.getPlayer(uuid2);
                            if (player1 != null && !player1.equals(playerDead)) {
                                player1.sendMessage(INFO + "" + gold + "" + italic + "" + playerDead.getName() + "" + aqua + "" + italic + " can't play right now.");
                            }
                        }
                    }
                } else {
                    playerDead.sendMessage(INFO + "You can't play.");
                }
                plugin.endTheGame.endThePvE(playerDead);
            } else {
                if (plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uuid3) || plugin.tpPlayersInGameNameLoc.containsValue(null)) {
                    Location theLocation = plugin.mainConfig.getLocationGame(GameName + "->" + theNameOfLoc + "-spawn-point");
                    playerDead.teleport(theLocation);
                    if (plugin.playersInFishKit.contains(playerDead.getUniqueId()) || plugin.playersInMelonKit.contains(playerDead.getUniqueId())) {
                        AttributeInstance attributeInstance = playerDead.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                        assert attributeInstance != null;
                        attributeInstance.setBaseValue(35);
                        playerDead.setHealth(35);
                    } else {
                        AttributeInstance attributeInstance = playerDead.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                        assert attributeInstance != null;
                        attributeInstance.setBaseValue(20);
                        playerDead.setHealth(20);
                    }
                    if (plugin.mainConfig.getStrGame("message_on_death") != null && !plugin.mainConfig.getStrGame("message_on_death").equalsIgnoreCase("") && !plugin.mainConfig.getStrGame("message_on_death").equalsIgnoreCase("empty")) {
                        playerDead.sendMessage(INFO + plugin.mainConfig.getStrGame("message_on_death"));
                    }
                    playerDead.sendTitle(gold + "" + italic + "Respawned", null, 2, 30, 2);
                    plugin.tpPlayersInGameNameLoc.put(uuid3, theNameOfLoc);
                    plugin.gameSpawnCoolDown.addPlayerToCoolMap(uuid3, 5);
                    playerDead.sendMessage(NORMAL + "You are teleported to the " + gold + "" + italic + "" + GameName + "" + green + "" + italic + " game on the " + gold + "" + italic + "" + theNameOfLoc + "" + green + "" + italic + " location.");
                }
            }
        } else {
            Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(playerDead);
            if (GameName == null) {
                playerDead.sendMessage(WARN + "The game you were playing does not exist any more.");
            } else {
                Set<String> namesOfGameLoc = plugin.GameSpawnPoints.get(GameName);
                if (namesOfGameLoc.isEmpty()) {
                    playerDead.sendMessage(ERROR + "" + gold + "" + italic + "" + GameName + "" + green + "" + italic + " game don't have any spawn points.");
                    if (playersInGroupOfPlayer != null && !playersInGroupOfPlayer.isEmpty()) {
                        for (UUID uuid : playersInGroupOfPlayer) {
                            if (uuid != null) {
                                Player playerGroup = Bukkit.getPlayer(uuid);
                                if (playerGroup != null) {
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
                                Player player = Bukkit.getPlayer(uuid);
                                if (player != null) {
                                    plugin.giveOneWinToPlayer.givePlayerWin(player);
                                }
                            }
                        }
                        for (UUID uuid : plugin.losses) {
                            if (uuid != null) {
                                Player player = Bukkit.getPlayer(uuid);
                                if (player != null) {
                                    plugin.giveOneLoseToPlayer.givePlayerLose(player);
                                }
                            }
                        }
                        plugin.winners.clear();
                        plugin.losses.clear();
                    }
                } else {
                    int theIndex = new Random().nextInt(namesOfGameLoc.size());
                    List<String> list1 = new ArrayList<>(namesOfGameLoc);
                    String theNameOfLoc = list1.get(theIndex);
                    if (plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                        while (plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                            int theIndex2 = new Random().nextInt(namesOfGameLoc.size());
                            theNameOfLoc = list1.get(theIndex2);
                        }
                    }
                    if (plugin.kickedPlayers.contains(uuid3)) {
                        playerDead.sendMessage(INFO + "You won't respawn because you are kicked from the game.");
                        if (plugin.playerGroup.getPlayersInGroupOfPlayer(playerDead).size() == 2 && playersInGroupOfPlayer != null && !playersInGroupOfPlayer.isEmpty()) {
                            for (UUID uuid1 : playersInGroupOfPlayer) {
                                if (uuid1 != null) {
                                    Player playerGroup = Bukkit.getPlayer(uuid1);
                                    if (playerGroup != null) {
                                        plugin.endTheGame.endTheGameWithStatus(playerGroup);
                                    }
                                }
                            }
                            for (UUID uuid1 : playersInGroupOfPlayer) {
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
                            for (UUID uuid1 : plugin.winners) {
                                if (uuid1 != null) {
                                    Player player = Bukkit.getPlayer(uuid1);
                                    if (player != null) {
                                        plugin.giveOneWinToPlayer.givePlayerWin(player);
                                    }
                                }
                            }
                            for (UUID uuid1 : plugin.losses) {
                                if (uuid1 != null) {
                                    Player player = Bukkit.getPlayer(uuid1);
                                    if (player != null) {
                                        plugin.giveOneLoseToPlayer.givePlayerLose(player);
                                    }
                                }
                            }
                            plugin.winners.clear();
                            plugin.losses.clear();
                        }
                    } else {
                        // the player can be teleported
                        if (plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uuid3) || plugin.tpPlayersInGameNameLoc.containsValue(null)) {
                            Location theLocation = plugin.mainConfig.getLocationGame(GameName + "->" + theNameOfLoc + "-spawn-point");
                            //playerDead.getInventory().clear();
                            // enable_lives
                            if (plugin.mainConfig.getBooleanGame("enable_lives_free-for-all") && plugin.isPlayerPlayingFreeForAll(playerDead)) {
                                UUID uuid1 = playerDead.getUniqueId();
                                int live = plugin.mainConfig.getIntGame("lives");
                                if (plugin.lives.isEmpty() || !plugin.lives.containsKey(uuid1)) {
                                    plugin.lives.put(uuid1, live);
                                    playerDead.sendMessage(INFO + "You have " + live + " lives.");
                                } else {
                                    if (plugin.lives.containsKey(uuid1)) {
                                        int newLive = plugin.lives.get(uuid1) - 1;
                                        plugin.lives.put(uuid1, newLive);
                                        if (newLive == 0) {
                                            playerDead.sendMessage(INFO + "This is your last live. Try not to die.");
                                        } else {
                                            if (newLive > 1) {
                                                playerDead.sendMessage(INFO + "You have " + gold + "" + italic + "" + newLive + "" + aqua + "" + italic + " lives.");
                                            } else if (newLive == 1) {
                                                playerDead.sendMessage(INFO + "You have " + newLive + "" + aqua + "" + italic + " life left.");
                                            }
                                        }
                                    }
                                }
                            } else if (plugin.mainConfig.getBooleanGame("enable_lives_team_deathmatch") && plugin.deathmatch.isPlayerPlayingFoodWars(playerDead)) {
                                UUID uuid1 = playerDead.getUniqueId();
                                int live = plugin.mainConfig.getIntGame("lives");
                                if (plugin.lives.isEmpty() || !plugin.lives.containsKey(uuid1)) {
                                    plugin.lives.put(uuid1, live);
                                    playerDead.sendMessage(INFO + "You have " + gold + "" + italic + "" + live + "" + aqua + "" + italic + " lives.");
                                } else {
                                    if (plugin.lives.containsKey(uuid1)) {
                                        int newLive = plugin.lives.get(uuid1) - 1;
                                        plugin.lives.put(uuid1, newLive);
                                        if (newLive == 0) {
                                            playerDead.sendMessage(INFO + "This is your last life. Try not to die.");
                                        } else {
                                            if (newLive > 1) {
                                                playerDead.sendMessage(INFO + "You have " + gold + "" + italic + "" + newLive + "" + aqua + "" + italic + " lives.");
                                            } else if (newLive == 1) {
                                                playerDead.sendMessage(INFO + "You have " + gold + "" + italic + "" + newLive + "" + aqua + "" + italic + " life left.");
                                            }
                                        }
                                    }
                                }
                            }
                            Location loc = playerDead.getLocation();
                            if (plugin.playerGroup.isPlayerInGroup(playerDead)) {
                                for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(playerDead)) {
                                    if (uuids != null) {
                                        Player playerInGroup = Bukkit.getPlayer(uuids);
                                        if (playerInGroup != null) {
                                            // FIREWORKS_SPARK
                                            // END_ROD
                                            playerInGroup.spawnParticle(Particle.END_ROD, loc.add(0, 1, 0), 10, 0.000001, 0.000001, 0.000001, 0.2);
                                            playerInGroup.spawnParticle(Particle.FIREWORKS_SPARK, loc.add(0, 1, 0), 10, 0.000001, 0.000001, 0.000001, 0.2);
                                        }
                                    }
                                }
                            } else {
                                for (Player playerInGroup : Bukkit.getOnlinePlayers()) {
                                    if (playerInGroup != null) {
                                        // FIREWORKS_SPARK
                                        // END_ROD
                                        playerInGroup.spawnParticle(Particle.END_ROD, loc.add(0, 1, 0), 10, 0.000001, 0.000001, 0.000001, 0.2);
                                        playerInGroup.spawnParticle(Particle.FIREWORKS_SPARK, loc.add(0, 1, 0), 10, 0.000001, 0.000001, 0.000001, 0.2);
                                    }
                                }
                            }
                            playerDead.teleport(theLocation);
                            plugin.kitsCoolDown.playerCoolDownMap.remove(uuid3);
                            plugin.kitPowerCoolDown.playerCoolDownMap.remove(uuid3);
                            if (plugin.playersInFishKit.contains(playerDead.getUniqueId()) || plugin.playersInMelonKit.contains(playerDead.getUniqueId())) {
                                AttributeInstance attributeInstance = playerDead.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                                assert attributeInstance != null;
                                attributeInstance.setBaseValue(35);
                                playerDead.setHealth(35);
                            } else {
                                AttributeInstance attributeInstance = playerDead.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                                assert attributeInstance != null;
                                attributeInstance.setBaseValue(20);
                                playerDead.setHealth(20);
                            }
                            if (plugin.mainConfig.getStrGame("message_on_death") != null && !plugin.mainConfig.getStrGame("message_on_death").equalsIgnoreCase("") && !plugin.mainConfig.getStrGame("message_on_death").equalsIgnoreCase("empty")) {
                                playerDead.sendMessage(INFO + plugin.mainConfig.getStrGame("message_on_death"));
                            }
                            playerDead.sendTitle(gold + "" + italic + "Respawned", null, 2, 30, 2);
                            plugin.tpPlayersInGameNameLoc.put(uuid3, theNameOfLoc);
                            plugin.gameSpawnCoolDown.addPlayerToCoolMap(uuid3, 5);
                            playerDead.sendMessage(NORMAL + "You are teleported to the " + gold + "" + italic + "" + GameName + "" + green + "" + italic + " game on the " + gold + "" + italic + "" + theNameOfLoc + "" + green + "" + italic + " location.");
                        }
                    }
                }
            }
        }
    }
}
