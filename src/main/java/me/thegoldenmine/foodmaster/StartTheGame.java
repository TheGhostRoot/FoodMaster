package me.thegoldenmine.foodmaster;

import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class StartTheGame {
    private final FoodMaster plugin;

    public StartTheGame(FoodMaster plugin) {
        this.plugin = plugin;
    }

    private void removeThePlayerFromWaiting(Player player) {
        UUID uuid = player.getUniqueId();
        if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
            plugin.waitingLobby.removePlayerFromWaitedLobby(player);
        }
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
        plugin.players5Teams.remove(uuid);
        // others
        plugin.playersThatChoice5Teams.remove(uuid);
        plugin.playersThatChoice4Teams.remove(uuid);
        plugin.playersThatChoice2Teams.remove(uuid);
        plugin.kickedPlayers.remove(uuid);
        plugin.playersThatChoice3Teams.remove(uuid);
        plugin.ReadySystem.remove(uuid);
        plugin.ReadyPlayers.remove(uuid);
        plugin.playersInFreeForAll.remove(uuid);
        plugin.playersChoiceFreeForAll.remove(uuid);
        plugin.playersChoicePvESpider.remove(uuid);
        plugin.playersChoicePvESkeleton.remove(uuid);
        plugin.playersChoicePvEZombie.remove(uuid);
        plugin.playersChoicePvEEnderman.remove(uuid);
        plugin.playersChoicePvESlime.remove(uuid);
        plugin.playersPlayingPvESkeleton.remove(uuid);
        plugin.playersPlayingPvESpider.remove(uuid);
        plugin.playersPlayingPvEEnderman.remove(uuid);
        plugin.playersPlayingPvEZombie.remove(uuid);
        plugin.playersPlayingPvESlime.remove(uuid);
    }

    public String findFreeGame() {
        String GameName = null;
        for (String name : plugin.playersInGame.keySet()) {
            if (name != null) {
                if (plugin.playersInGame.get(name) == null) {
                    Set<UUID> emptySet = new HashSet<>();
                    plugin.playersInGame.put(name, emptySet);
                }
                if (plugin.playersInGame.get(name) != null && plugin.playersInGame.get(name).isEmpty() && !plugin.playersInGame.containsKey(GameName)) {
                    GameName = name;
                }
            }
        }
        return GameName;
    }

    public void tpTheGroup(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        String GameName = findFreeGame();
        UUID playerUUID = player.getUniqueId();
        if (GameName == null) {
            if (plugin.playerPvE.isPlayerPlayingPvE(player)) {
                if (plugin.playerGroup.isPlayerInGroup(player)) {
                    Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
                    for (UUID uuid : playersInGroupOfPlayer) {
                        if (uuid != null) {
                            Player players = Bukkit.getPlayer(uuid);
                            if (players != null) {
                                players.sendMessage(NORMAL + "There are no available games right now.");
                                plugin.ReadySystem.remove(uuid);
                                plugin.ReadyPlayers.remove(uuid);
                                players.getInventory().remove(ItemManager.notReady);
                            }
                        }
                    }
                } else {
                    player.sendMessage(NORMAL + "There are no available games right now.");
                    plugin.ReadySystem.remove(playerUUID);
                    plugin.ReadyPlayers.remove(playerUUID);
                    player.getInventory().remove(ItemManager.notReady);
                }
                return;
            }
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
                for (UUID uuid : playersInGroupOfPlayer) {
                    if (uuid != null) {
                        Player players = Bukkit.getPlayer(uuid);
                        if (players != null) {
                            players.sendMessage(NORMAL + "There are no available games right now.");
                            plugin.ReadySystem.remove(uuid);
                            plugin.ReadyPlayers.remove(uuid);
                            players.getInventory().remove(ItemManager.notReady);
                            return;
                        }
                    }
                }
            }
        } else {
            Set<String> namesOfGameLoc = plugin.GameSpawnPoints.get(GameName);
            if (namesOfGameLoc.isEmpty() || namesOfGameLoc.contains(null)) {
                player.sendMessage(ERROR + "The " + gold + "" + italic + "" + GameName + "" + red + "" + italic + " game doesn't have any spawn points.");
            } else {
                if (plugin.playerGroup.isPlayerInGroup(player)) {
                    Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
                    for (UUID uuid : playersInGroupOfPlayer) {
                        if (uuid != null) {
                            plugin.tpPlayersInGameNameLoc.put(uuid, null);
                        }
                    }
                } else {
                    plugin.tpPlayersInGameNameLoc.put(player.getUniqueId(), null);
                }
                if (plugin.playerPvE.isPlayerPlayingPvE(player)) {
                    if (plugin.playerGroup.isPlayerInGroup(player)) {
                        Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
                        for (UUID uuid : playersInGroupOfPlayer) {
                            if (uuid != null) {
                                Player groupPlayer = Bukkit.getPlayer(uuid);
                                if (groupPlayer != null) {
                                    UUID uniqueId = groupPlayer.getUniqueId();
                                    if (!groupPlayer.getPassengers().isEmpty()) {
                                        for (Entity player1 : groupPlayer.getPassengers()) {
                                            if (player1 instanceof Player) {
                                                Player player2 = (Player) player1;
                                                if (plugin.tpPlayersInGameNameLoc.containsValue(null)) {
                                                    int max = namesOfGameLoc.size() - 1;
                                                    int theIndex = new Random().nextInt(max + 1);
                                                    List<String> list1 = new ArrayList<>(namesOfGameLoc);
                                                    String theNameOfLoc = list1.get(theIndex);
                                                    if (plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                                                        while (plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                                                            int theIndex2 = new Random().nextInt(max + 1);
                                                            theNameOfLoc = list1.get(theIndex2);
                                                        }
                                                    }
                                                    if (plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId) && !plugin.kickedPlayers.contains(groupPlayer.getUniqueId())) {
                                                        Location theLocation = plugin.mainConfig.getLocationGame(GameName + "->" + theNameOfLoc + "-spawn-point");
                                                        player2.getInventory().clear();
                                                        player2.setGameMode(GameMode.SURVIVAL);
                                                        player2.teleport(theLocation);
                                                        plugin.tpPlayersInGameNameLoc.put(uniqueId, theNameOfLoc);
                                                        plugin.gameSpawnCoolDown.addPlayerToCoolMap(uniqueId, 5);
                                                        player2.sendMessage(NORMAL + "You are teleported to the " + gold + "" + italic + "" + GameName + "" + green + "" + italic + " game on the " + gold + "" + italic + "" + theNameOfLoc + "" + green + "" + italic + " location.");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (plugin.tpPlayersInGameNameLoc.containsValue(null)) {
                                        int max = namesOfGameLoc.size() - 1;
                                        int theIndex = new Random().nextInt(max + 1);
                                        List<String> list1 = new ArrayList<>(namesOfGameLoc);
                                        String theNameOfLoc = list1.get(theIndex);
                                        if (plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                                            while (plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                                                int theIndex2 = new Random().nextInt(max + 1);
                                                theNameOfLoc = list1.get(theIndex2);
                                            }
                                        }
                                        if (plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId) && !plugin.kickedPlayers.contains(groupPlayer.getUniqueId())) {
                                            Location theLocation = plugin.mainConfig.getLocationGame(GameName + "->" + theNameOfLoc + "-spawn-point");
                                            groupPlayer.getInventory().clear();
                                            groupPlayer.setGameMode(GameMode.SURVIVAL);
                                            groupPlayer.teleport(theLocation);
                                            plugin.tpPlayersInGameNameLoc.put(uniqueId, theNameOfLoc);
                                            plugin.gameSpawnCoolDown.addPlayerToCoolMap(uniqueId, 5);
                                            groupPlayer.sendMessage(NORMAL + "You are teleported to the " + gold + "" + italic + "" + GameName + "" + green + "" + italic + " game on the " + gold + "" + italic + "" + theNameOfLoc + "" + green + "" + italic + " location.");
                                        }
                                    }
                                }
                            }
                        }
                        plugin.playersInGame.put(GameName, new HashSet<>(playersInGroupOfPlayer));
                        plugin.stillAlive.put(GameName, new HashSet<>(playersInGroupOfPlayer));
                    } else {
                        // the player is alone
                        if (plugin.tpPlayersInGameNameLoc.containsValue(null)) {
                            int max = namesOfGameLoc.size() - 1;
                            int theIndex = new Random().nextInt(max + 1);
                            List<String> list1 = new ArrayList<>(namesOfGameLoc);
                            String theNameOfLoc = list1.get(theIndex);
                            if (plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                                while (plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                                    int theIndex2 = new Random().nextInt(max + 1);
                                    theNameOfLoc = list1.get(theIndex2);
                                }
                            }
                            //  && !plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)
                            if (plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(playerUUID) && !plugin.kickedPlayers.contains(playerUUID)) {
                                Location theLocation = plugin.mainConfig.getLocationGame(GameName + "->" + theNameOfLoc + "-spawn-point");
                                player.getInventory().clear();
                                player.setGameMode(GameMode.SURVIVAL);
                                player.teleport(theLocation);
                                plugin.tpPlayersInGameNameLoc.put(playerUUID, theNameOfLoc);
                                plugin.gameSpawnCoolDown.addPlayerToCoolMap(playerUUID, 5);
                                player.sendMessage(NORMAL + "You are teleported to the " + gold + "" + italic + "" + GameName + "" + green + "" + italic + " game on the " + gold + "" + italic + "" + theNameOfLoc + "" + green + "" + italic + " location.");
                            }
                        } else {
                            player.sendMessage(ERROR + "There are insufficient spawn points.");
                        }
                        Set<UUID> idk = new HashSet<>();
                        idk.add(playerUUID);
                        plugin.playersInGame.put(GameName, idk);
                        plugin.stillAlive.put(GameName, idk);
                    }
                } else {
                    if (plugin.playerGroup.isPlayerInGroup(player)) {
                        Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
                        for (UUID uuid : playersInGroupOfPlayer) {
                            if (uuid != null) {
                                Player groupPlayer = Bukkit.getPlayer(uuid);
                                if (groupPlayer != null) {
                                    UUID uniqueId = groupPlayer.getUniqueId();
                                    if (plugin.tpPlayersInGameNameLoc.containsValue(null)) {
                                        int max = namesOfGameLoc.size() - 1;
                                        int theIndex = new Random().nextInt(max + 1);
                                        List<String> list1 = new ArrayList<>(namesOfGameLoc);
                                        String theNameOfLoc = list1.get(theIndex);
                                        if (plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                                            while (plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                                                int theIndex2 = new Random().nextInt(max + 1);
                                                theNameOfLoc = list1.get(theIndex2);
                                            }
                                        }
                                        //  && !plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)
                                        if (plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId) && !plugin.kickedPlayers.contains(groupPlayer.getUniqueId())) {
                                            Location theLocation = plugin.mainConfig.getLocationGame(GameName + "->" + theNameOfLoc + "-spawn-point");
                                            groupPlayer.getInventory().clear();
                                            groupPlayer.setGameMode(GameMode.SURVIVAL);
                                            groupPlayer.teleport(theLocation);
                                            plugin.tpPlayersInGameNameLoc.put(uniqueId, theNameOfLoc);
                                            plugin.gameSpawnCoolDown.addPlayerToCoolMap(uniqueId, 5);
                                            groupPlayer.sendMessage(NORMAL + "You are teleported to the " + gold + "" + italic + "" + GameName + "" + green + "" + italic + " game on the " + gold + "" + italic + "" + theNameOfLoc + "" + green + "" + italic + " location.");
                                        }
                                    }
                                }
                            }
                        }
                        plugin.playersInGame.put(GameName, new HashSet<>(playersInGroupOfPlayer));
                        plugin.stillAlive.put(GameName, new HashSet<>(playersInGroupOfPlayer));
                    } else {
                        player.sendMessage(WARN + "You are not in a group so you won't start this game.");
                        removeThePlayerFromWaiting(player);
                    }
                }
            }
        }
    }

    public synchronized void StartFoodGame(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s7;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s7 = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s7 = " FoodMaster ";
        }
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s7 + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (player != null) {
            Set<UUID> playersInGroupOfPlayer = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
            if (plugin.playerGroup.isPlayerInGroup(player) && !playersInGroupOfPlayer.isEmpty()) {
                for (UUID uuids : playersInGroupOfPlayer) {
                    if (uuids != null) {
                        Player playerInGroup = Bukkit.getPlayer(uuids);
                        if (playerInGroup != null) {
                            plugin.waitingLobby.removePlayerFromWaitedLobby(playerInGroup);
                        }
                    }
                }
            } else {
                removeThePlayerFromWaiting(player);
                player.sendMessage(ERROR + "You have to be in a group to play this game!");
                return;
            }
            plugin.ReadyPlayers.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.ReadySystem.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.playersChoiceFoodGame.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.playersInFoodGame.addAll(new HashSet<>(playersInGroupOfPlayer));
            tpTheGroup(player);
            plugin.playersInCookieKit.addAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.playersInBreadKit.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.playersInFishKit.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.playersInPotatoKit.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.playersInMelonKit.removeAll(new HashSet<>(playersInGroupOfPlayer));
        }
    }

    public synchronized void StartFreeForAll(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s4;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s4 = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s4 = " FoodMaster ";
        }
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (player != null) {
            Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
            assert playersInGroupOfPlayer != null;
            plugin.ReadyPlayers.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.ReadySystem.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.playersChoiceFreeForAll.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.playersInFreeForAll.addAll(new HashSet<>(playersInGroupOfPlayer));
            if (plugin.playerGroup.isPlayerInGroup(player) && !playersInGroupOfPlayer.isEmpty()) {
                for (UUID uuids : playersInGroupOfPlayer) {
                    if (uuids != null) {
                        Player playerInGroup = Bukkit.getPlayer(uuids);
                        if (playerInGroup != null) {
                            plugin.waitingLobby.removePlayerFromWaitedLobby(playerInGroup);
                        }
                    }
                }
            } else {
                removeThePlayerFromWaiting(player);
                player.sendMessage(ERROR + "You have to be in a group to play this game!");
                return;
            }
            tpTheGroup(player);
        }
    }

    public synchronized void StartTeams2(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s6;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s6 = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s6 = " FoodMaster ";
        }
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s6 + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (player != null) {
            Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
            assert playersInGroupOfPlayer != null;
            plugin.ReadyPlayers.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.ReadySystem.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.playersThatChoice2Teams.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.players2Teams.addAll(new HashSet<>(playersInGroupOfPlayer));
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                for (UUID uuid : playersInGroupOfPlayer) {
                    if (uuid != null) {
                        Player players = Bukkit.getPlayer(uuid);
                        if (players != null) {
                            plugin.waitingLobby.removePlayerFromWaitedLobby(players);
                        }
                    }
                }
                tpTheGroup(player);
                plugin.playerGroup.giveThePlayerArmor(player);
            } else {
                removeThePlayerFromWaiting(player);
                player.sendMessage(ERROR + "You have to be in a group to play this game!");
            }
        }
    }

    public synchronized void StartTeams3(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (player != null) {
            Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
            assert playersInGroupOfPlayer != null;
            plugin.ReadyPlayers.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.ReadySystem.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.playersThatChoice3Teams.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.players3Teams.addAll(new HashSet<>(playersInGroupOfPlayer));
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                for (UUID uuid : playersInGroupOfPlayer) {
                    if (uuid != null) {
                        Player players = Bukkit.getPlayer(uuid);
                        if (players != null) {
                            plugin.waitingLobby.removePlayerFromWaitedLobby(players);
                        }
                    }
                }
                tpTheGroup(player);
                plugin.playerGroup.giveThePlayerArmor(player);
            } else {
                removeThePlayerFromWaiting(player);
                player.sendMessage(ERROR + "You have to be in a group to play this game!");
            }
        }
    }

    public synchronized void StartTeams4(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (player != null) {
            Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
            assert playersInGroupOfPlayer != null;
            plugin.ReadyPlayers.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.ReadySystem.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.playersThatChoice4Teams.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.players4Teams.addAll(new HashSet<>(playersInGroupOfPlayer));
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                for (UUID uuid : playersInGroupOfPlayer) {
                    if (uuid != null) {
                        Player players = Bukkit.getPlayer(uuid);
                        if (players != null) {
                            plugin.waitingLobby.removePlayerFromWaitedLobby(players);
                        }
                    }
                }
                tpTheGroup(player);
                plugin.playerGroup.giveThePlayerArmor(player);
            } else {
                removeThePlayerFromWaiting(player);
                player.sendMessage(ERROR + "You have to be in a group to play this game!");
            }
        }
    }

    public synchronized void StartTeams5(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (player != null) {
            Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
            assert playersInGroupOfPlayer != null;
            plugin.ReadyPlayers.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.ReadySystem.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.playersThatChoice5Teams.removeAll(new HashSet<>(playersInGroupOfPlayer));
            plugin.players5Teams.addAll(new HashSet<>(playersInGroupOfPlayer));
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                for (UUID uuid : playersInGroupOfPlayer) {
                    if (uuid != null) {
                        Player players = Bukkit.getPlayer(uuid);
                        if (players != null) {
                            plugin.waitingLobby.removePlayerFromWaitedLobby(players);
                        }
                    }
                }
                tpTheGroup(player);
                plugin.playerGroup.giveThePlayerArmor(player);
            } else {
                removeThePlayerFromWaiting(player);
                player.sendMessage(ERROR + "You have to be in a group to play this game!");
            }
        }
    }

    public synchronized void StartPvEZombie(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (player != null) {
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
                assert playersInGroupOfPlayer != null;
                plugin.ReadyPlayers.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.ReadySystem.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.playersChoicePvEZombie.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.playersPlayingPvEZombie.addAll(new HashSet<>(playersInGroupOfPlayer));
                if (!playersInGroupOfPlayer.isEmpty()) {
                    for (UUID uuids : playersInGroupOfPlayer) {
                        if (uuids != null) {
                            Player playerInGroup = Bukkit.getPlayer(uuids);
                            if (playerInGroup != null) {
                                plugin.waitingLobby.removePlayerFromWaitedLobby(playerInGroup);
                            }
                        }
                    }
                }
            } else {
                // the player is alone
                UUID uuid = player.getUniqueId();
                plugin.waitingLobby.removePlayerFromWaitedLobby(player);
                plugin.ReadyPlayers.remove(uuid);
                plugin.ReadySystem.remove(uuid);
                plugin.playersChoicePvEZombie.remove(uuid);
                plugin.playersPlayingPvEZombie.add(uuid);
            }
            tpTheGroup(player);
            String gameName = plugin.game.getGameName(player);
            String strLocation = plugin.mainConfig.getStrPvE("pve_boss_spawn-" + gameName);
            Location loc = plugin.StringToLocation(strLocation);
            if (loc != null) {
                plugin.spawnBoss.ZombieBoss(loc, player.getName());
            } else {
                if (plugin.playerGroup.isPlayerInGroup(player)) {
                    for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                        if (uuid != null) {
                            Player groupPlayer = Bukkit.getPlayer(uuid);
                            if (groupPlayer != null) {
                                groupPlayer.sendMessage(ERROR+"The PvE boss spawn location is not set so the boss will spawn at "+gold +""+italic+""+player.getName()+""+red+""+italic+"  location.");
                            }
                        }
                    }
                } else {
                    player.sendMessage(ERROR+"The PvE boss spawn location is not set so the boss will spawn at your location.");
                }
                plugin.spawnBoss.ZombieBoss(player.getLocation(), player.getName());
            }
        }
    }

    public synchronized void StartPvESkeleton(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (player != null) {
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
                assert playersInGroupOfPlayer != null;
                plugin.ReadyPlayers.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.ReadySystem.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.playersChoicePvESkeleton.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.playersPlayingPvESkeleton.addAll(new HashSet<>(playersInGroupOfPlayer));
                if (!playersInGroupOfPlayer.isEmpty()) {
                    for (UUID uuids : playersInGroupOfPlayer) {
                        if (uuids != null) {
                            Player playerInGroup = Bukkit.getPlayer(uuids);
                            if (playerInGroup != null) {
                                plugin.waitingLobby.removePlayerFromWaitedLobby(playerInGroup);
                            }
                        }
                    }
                }
            } else {
                // the player is alone
                UUID uuid = player.getUniqueId();
                plugin.waitingLobby.removePlayerFromWaitedLobby(player);
                plugin.ReadyPlayers.remove(uuid);
                plugin.ReadySystem.remove(uuid);
                plugin.playersChoicePvESkeleton.remove(uuid);
                plugin.playersPlayingPvESkeleton.add(uuid);
            }
            tpTheGroup(player);
            String gameName = plugin.game.getGameName(player);
            String strLocation = plugin.mainConfig.getStrPvE("pve_boss_spawn-" + gameName);
            Location loc = plugin.StringToLocation(strLocation);
            if (loc != null) {
                plugin.spawnBoss.SkeletonBoss(loc, player.getName());
            } else {
                if (plugin.playerGroup.isPlayerInGroup(player)) {
                    for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                        if (uuid != null) {
                            Player groupPlayer = Bukkit.getPlayer(uuid);
                            if (groupPlayer != null) {
                                groupPlayer.sendMessage(ERROR+"The PvE boss spawn location is not set so the boss will spawn at "+gold +""+italic+""+player.getName()+""+red+""+italic+"  location.");
                            }
                        }
                    }
                } else {
                    player.sendMessage(ERROR+"The PvE boss spawn location is not set so the boss will spawn at your location.");
                }
                plugin.spawnBoss.SkeletonBoss(player.getLocation(), player.getName());
            }
        }
    }

    public synchronized void StartPvESpider(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (player != null) {
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
                assert playersInGroupOfPlayer != null;
                plugin.ReadyPlayers.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.ReadySystem.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.playersChoicePvESpider.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.playersPlayingPvESpider.addAll(new HashSet<>(playersInGroupOfPlayer));
                if (!playersInGroupOfPlayer.isEmpty()) {
                    for (UUID uuids : playersInGroupOfPlayer) {
                        if (uuids != null) {
                            Player playerInGroup = Bukkit.getPlayer(uuids);
                            if (playerInGroup != null) {
                                plugin.waitingLobby.removePlayerFromWaitedLobby(playerInGroup);
                            }
                        }
                    }
                }
            } else {
                // the player is alone
                UUID uuid = player.getUniqueId();
                plugin.waitingLobby.removePlayerFromWaitedLobby(player);
                plugin.ReadyPlayers.remove(uuid);
                plugin.ReadySystem.remove(uuid);
                plugin.playersChoicePvESpider.remove(uuid);
                plugin.playersPlayingPvESpider.add(uuid);
            }
            tpTheGroup(player);
            String gameName = plugin.game.getGameName(player);
            String strLocation = plugin.mainConfig.getStrPvE("pve_boss_spawn-" + gameName);
            Location loc = plugin.StringToLocation(strLocation);
            if (loc != null) {
                plugin.spawnBoss.SpiderBoss(loc, player.getName());
            } else {
                if (plugin.playerGroup.isPlayerInGroup(player)) {
                    for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                        if (uuid != null) {
                            Player groupPlayer = Bukkit.getPlayer(uuid);
                            if (groupPlayer != null) {
                                groupPlayer.sendMessage(ERROR+"The PvE boss spawn location is not set so the boss will spawn at "+gold +""+italic+""+player.getName()+""+red+""+italic+"  location.");
                            }
                        }
                    }
                } else {
                    player.sendMessage(ERROR+"The PvE boss spawn location is not set so the boss will spawn at your location.");
                }
                plugin.spawnBoss.SpiderBoss(player.getLocation(), player.getName());
            }
        }
    }

    public synchronized void StartPvEEnderman(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (player != null) {
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
                assert playersInGroupOfPlayer != null;
                plugin.ReadyPlayers.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.ReadySystem.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.playersChoicePvEEnderman.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.playersPlayingPvEEnderman.addAll(new HashSet<>(playersInGroupOfPlayer));
                if (!playersInGroupOfPlayer.isEmpty()) {
                    for (UUID uuids : playersInGroupOfPlayer) {
                        if (uuids != null) {
                            Player playerInGroup = Bukkit.getPlayer(uuids);
                            if (playerInGroup != null) {
                                plugin.waitingLobby.removePlayerFromWaitedLobby(playerInGroup);
                            }
                        }
                    }
                }
            } else {
                // the player is alone
                UUID uuid = player.getUniqueId();
                plugin.waitingLobby.removePlayerFromWaitedLobby(player);
                plugin.ReadyPlayers.remove(uuid);
                plugin.ReadySystem.remove(uuid);
                plugin.playersChoicePvEEnderman.remove(uuid);
                plugin.playersPlayingPvEEnderman.add(uuid);
            }
            tpTheGroup(player);
            String gameName = plugin.game.getGameName(player);
            String strLocation = plugin.mainConfig.getStrPvE("pve_boss_spawn-" + gameName);
            Location loc = plugin.StringToLocation(strLocation);
            if (loc != null) {
                plugin.spawnBoss.EndermanBoss(loc, player.getName());
            } else {
                if (plugin.playerGroup.isPlayerInGroup(player)) {
                    for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                        if (uuid != null) {
                            Player groupPlayer = Bukkit.getPlayer(uuid);
                            if (groupPlayer != null) {
                                groupPlayer.sendMessage(ERROR+"The PvE boss spawn location is not set so the boss will spawn at "+gold +""+italic+""+player.getName()+""+red+""+italic+"  location.");
                            }
                        }
                    }
                } else {
                    player.sendMessage(ERROR+"The PvE boss spawn location is not set so the boss will spawn at your location.");
                }
                plugin.spawnBoss.EndermanBoss(player.getLocation(), player.getName());
            }
        }
    }

    public synchronized void StartPvESlime(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (player != null) {
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
                assert playersInGroupOfPlayer != null;
                plugin.ReadyPlayers.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.ReadySystem.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.playersChoicePvESlime.removeAll(new HashSet<>(playersInGroupOfPlayer));
                plugin.playersPlayingPvESlime.addAll(new HashSet<>(playersInGroupOfPlayer));
                if (!playersInGroupOfPlayer.isEmpty()) {
                    for (UUID uuids : playersInGroupOfPlayer) {
                        if (uuids != null) {
                            Player playerInGroup = Bukkit.getPlayer(uuids);
                            if (playerInGroup != null) {
                                plugin.waitingLobby.removePlayerFromWaitedLobby(playerInGroup);
                            }
                        }
                    }
                }
            } else {
                // the player is alone
                UUID uuid = player.getUniqueId();
                plugin.waitingLobby.removePlayerFromWaitedLobby(player);
                plugin.ReadyPlayers.remove(uuid);
                plugin.ReadySystem.remove(uuid);
                plugin.playersChoicePvESlime.remove(uuid);
                plugin.playersPlayingPvESlime.add(uuid);
            }
            tpTheGroup(player);
            String gameName = plugin.game.getGameName(player);
            String strLocation = plugin.mainConfig.getStrPvE("pve_boss_spawn-" + gameName);
            Location loc = plugin.StringToLocation(strLocation);
            if (loc != null) {
                plugin.spawnBoss.SlimeBoss(loc, player.getName());
            } else {
                if (plugin.playerGroup.isPlayerInGroup(player)) {
                    for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                        if (uuid != null) {
                            Player groupPlayer = Bukkit.getPlayer(uuid);
                            if (groupPlayer != null) {
                                groupPlayer.sendMessage(ERROR+"The PvE boss spawn location is not set so the boss will spawn at "+gold +""+italic+""+player.getName()+""+red+""+italic+"  location.");
                            }
                        }
                    }
                } else {
                    player.sendMessage(ERROR+"The PvE boss spawn location is not set so the boss will spawn at your location.");
                }
                plugin.spawnBoss.SlimeBoss(player.getLocation(), player.getName());
            }
        }
    }
}
