package me.thegoldenmine.foodmaster.CoolDown;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AntiGlitchSys {
    private final FoodMaster plugin;

    public AntiGlitchSys(FoodMaster main) {
        plugin = main;

        new BukkitRunnable() {
            @Override
            public void run() {
                ChatColor darkGray = ChatColor.DARK_GRAY;
                ChatColor strikethrough = ChatColor.STRIKETHROUGH;
                ChatColor gold = ChatColor.GOLD;
                ChatColor bold = ChatColor.BOLD;
                ChatColor green = ChatColor.GREEN;
                ChatColor italic = ChatColor.ITALIC;
                ChatColor red = ChatColor.RED;
                String s;
                if (plugin.mainConfig.getStrMain("name") != null) {
                    s = " " + plugin.mainConfig.getStrMain("name") + " ";
                } else {
                    s = " FoodMaster ";
                }
                String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
                String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
                // 20 is normal health
                int someHealth = 35;
                String ReadyName = Objects.requireNonNull(ItemManager.isReady.getItemMeta()).getDisplayName();
                String NotReadyName = Objects.requireNonNull(ItemManager.notReady.getItemMeta()).getDisplayName();
                plugin.allGroups.removeIf(Set::isEmpty);
                List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
                if (players.isEmpty()) {
                    return;
                }
                for (Player player : players) {
                    if (player != null) {
                        // group leave
                        PlayerInventory inventory = player.getInventory();
                        UUID uuid = player.getUniqueId();
                        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                        assert attributeInstance != null;
                        // kits
                        ItemStack fish = plugin.game.getItemByName(player.getInventory(), ChatColor.GREEN + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Fish" + ChatColor.GREEN + "" + ChatColor.MAGIC + "|");
                        ItemStack bread = plugin.game.getItemByName(player.getInventory(), ChatColor.GREEN + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Bread" + ChatColor.GREEN + "" + ChatColor.MAGIC + "|");
                        ItemStack cookie = plugin.game.getItemByName(player.getInventory(), ChatColor.GREEN + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Cookie" + ChatColor.GREEN + "" + ChatColor.MAGIC + "|");
                        ItemStack melon = plugin.game.getItemByName(player.getInventory(), ChatColor.GREEN + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Melon" + ChatColor.GREEN + "" + ChatColor.MAGIC + "|");
                        ItemStack potato = plugin.game.getItemByName(player.getInventory(), ChatColor.GREEN + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Potato" + ChatColor.GREEN + "" + ChatColor.MAGIC + "|");

                        // others
                        ItemStack playAgainItem = plugin.game.getItemByName(player.getInventory(), ItemManager.playAgain.getItemMeta().getDisplayName());
                        ItemStack groupLeaveItem = plugin.game.getItemByName(player.getInventory(), ItemManager.groupLeave.getItemMeta().getDisplayName());
                        if (playAgainItem != null && playAgainItem.getAmount() > 1) {
                            playAgainItem.setAmount(1);
                        }
                        if (groupLeaveItem != null && groupLeaveItem.getAmount() > 1) {
                            groupLeaveItem.setAmount(1);
                        }
                        if (fish != null && fish.getAmount() > 1) {
                            fish.setAmount(1);
                        }
                        if (bread != null && bread.getAmount() > 1) {
                            bread.setAmount(1);
                        }
                        if (cookie != null && cookie.getAmount() > 1) {
                            cookie.setAmount(1);
                        }
                        if (melon != null && melon.getAmount() > 1) {
                            melon.setAmount(1);
                        }
                        if (potato != null && potato.getAmount() > 1) {
                            potato.setAmount(1);
                        }
                        List<String> names = new ArrayList<>();
                        if (!plugin.stillAlive.keySet().isEmpty()) {
                            for (String name : plugin.stillAlive.keySet()) {
                                if (plugin.stillAlive.get(name).isEmpty()) {
                                    names.add(name);
                                }
                            }
                        }
                        if (!names.isEmpty()) {
                            for (String Ename : names) {
                                plugin.stillAlive.remove(Ename);
                            }
                        }
                        if (plugin.isPlayerPlayingFoodGame(player) && plugin.playersInFishKit.contains(uuid) && attributeInstance.getBaseValue() < someHealth) {
                            attributeInstance.setBaseValue(someHealth);
                            player.setHealth(someHealth);
                        }
                        if (plugin.isPlayerPlayingFoodGame(player) && plugin.playersInMelonKit.contains(uuid) && attributeInstance.getBaseValue() < someHealth) {
                            attributeInstance.setBaseValue(someHealth);
                            player.setHealth(someHealth);
                        }
                        if (plugin.waitingLobby.isPlayerInWaitingLobby(player) && !plugin.game.isPlayerInGame(player)) {
                            if (!plugin.mainConfig.getBooleanWaitLobby("hungry_while_waiting")) {
                                player.setFoodLevel(20);
                            }
                            if (!inventory.contains(ItemManager.kitChooser) && !plugin.playersChoiceFoodGame.contains(uuid)) {
                                inventory.addItem(ItemManager.kitChooser);
                            }
                            if (plugin.deathmatch.isPlayerHaveStartedTeams(player) && !inventory.contains(ItemManager.teams) && !plugin.playersChoiceFoodGame.contains(uuid)) {
                                inventory.addItem(ItemManager.teams);
                            }
                            if (plugin.game.getItemByName(player.getInventory(), ReadyName) != null && inventory.contains(plugin.game.getItemByName(player.getInventory(), ReadyName)) && plugin.ReadyPlayers.contains(uuid)) {
                                inventory.remove(ItemManager.isReady);
                            }
                            if (plugin.ReadyPlayers.contains(uuid) && !inventory.contains(plugin.game.getItemByName(player.getInventory(), NotReadyName))) {
                                inventory.addItem(ItemManager.notReady);
                            }
                            if (!plugin.ReadyPlayers.contains(uuid) && !inventory.contains(plugin.game.getItemByName(player.getInventory(), ReadyName))) {
                                inventory.addItem(ItemManager.isReady);
                            }
                        }
                        if (plugin.items.contains(player.getInventory().getItemInOffHand())) {
                            player.getInventory().setItemInOffHand(null);
                            player.sendMessage(ERROR + "Nope.");
                        }
                        if (plugin.game.isPlayerInGame(player) && !player.getGameMode().equals(GameMode.SURVIVAL) && player.isOnline() && !player.isDead()) {
                            String name = plugin.game.getGameName(player);
                            if (!plugin.stillAlive.isEmpty() && plugin.stillAlive.get(name) != null && plugin.stillAlive.get(name).contains(uuid)) {
                                // the player is alive
                                player.setGameMode(GameMode.SURVIVAL);
                            }
                        }
                        if (plugin.game.isPlayerInGame(player) && !player.getGameMode().equals(GameMode.SPECTATOR) && player.isOnline() && !player.isDead()) {
                            String name = plugin.game.getGameName(player);
                            if (!plugin.stillAlive.isEmpty() && plugin.stillAlive.get(name) != null && !plugin.stillAlive.get(name).contains(uuid)) {
                                // the player is alive
                                player.setGameMode(GameMode.SPECTATOR);
                            }
                        }
                        if (plugin.playerGroup.isPlayerInGroup(player) && !plugin.mainConfig.getBooleanMain("hungry_in_group")) {
                            player.setFoodLevel(20);
                        }
                        if (plugin.playerGroup.isPlayerInGroup(player) && !inventory.contains(ItemManager.groupLeave) && !plugin.game.isPlayerInGame(player) && !plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                            inventory.setItem(8, ItemManager.groupLeave);
                        }
                        if (!plugin.playerGroup.isPlayerInGroup(player) && inventory.contains(ItemManager.groupLeave)) {
                            inventory.remove(ItemManager.groupLeave);
                        }
                        if (!plugin.waitingLobby.isPlayerInWaitingLobby(player) && !plugin.game.isPlayerInGame(player) && plugin.playerGroup.isPlayerInGroup(player) && plugin.playAgain.containsKey(uuid) && !player.getInventory().contains(ItemManager.playAgain)) {
                            player.getInventory().addItem(ItemManager.playAgain);
                        }
                        if (!plugin.playAgain.containsKey(uuid) && player.getInventory().contains(ItemManager.playAgain)) {
                            player.getInventory().remove(ItemManager.playAgain);
                        }
                        // Ready system
                        if (plugin.ReadySystem.contains(uuid)) {
                            // From Ready to UNREADY
                            if (inventory.contains(ItemManager.isReady) || inventory.contains(plugin.game.getItemByName(player.getInventory(), ReadyName))) {
                                if (!plugin.game.isPlayerInGame(player) && plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                                    if (!plugin.playerGroup.isPlayerInGroup(player) && !plugin.playerPvE.isPlayerChooseToPlayPvE(player)) {
                                        plugin.ReadySystem.remove(uuid);
                                        player.sendMessage(ERROR + "You are not in a group.");
                                    }
                                    ItemStack item = plugin.game.getItemByName(player.getInventory(), ReadyName);
                                    assert item != null;
                                    inventory.remove(item);
                                    inventory.addItem(ItemManager.notReady);
                                    plugin.ReadyPlayers.add(uuid);
                                    player.sendMessage(NORMAL + "Ready");
                                    ItemStack itemNotReady = plugin.game.getItemByName(player.getInventory(), NotReadyName);
                                    if (inventory.contains(ItemManager.notReady) || inventory.contains(itemNotReady)) {
                                        assert itemNotReady != null;
                                        if (itemNotReady.getItemMeta() != null && itemNotReady.getItemMeta().getLore() != null) {
                                            itemNotReady.getItemMeta().getLore().clear();
                                        }
                                        plugin.game.setReadyStatus(player);
                                    }
                                    if (plugin.playerGroup.checkIfTheGroupIsReady(player)) {
                                        if (plugin.playerPvE.isPlayerChooseToPlayPvE(player)) {
                                            if (plugin.playerGroup.isPlayerInGroup(player)) {
                                                for (UUID uuid1 : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                                                    if (uuid1 != null) {
                                                        Player player1 = Bukkit.getPlayer(uuid1);
                                                        if (player1 != null && !plugin.playerGroup.isPlayerChosenKit(player1)) {
                                                            plugin.playersRandomKit.add(uuid1);
                                                            plugin.game.randomKitGiver(player1);
                                                        }
                                                    }
                                                }
                                            } else {
                                                if (!plugin.playerGroup.isPlayerChosenKit(player)) {
                                                    plugin.playersRandomKit.add(uuid);
                                                    plugin.game.randomKitGiver(player);
                                                }
                                            }
                                            if (plugin.playersChoicePvEZombie.contains(uuid)) {
                                                plugin.startTheGame.StartPvEZombie(player);
                                            } else if (plugin.playersChoicePvESkeleton.contains(uuid)) {
                                                plugin.startTheGame.StartPvESkeleton(player);
                                            } else if (plugin.playersChoicePvESpider.contains(uuid)) {
                                                plugin.startTheGame.StartPvESpider(player);
                                            } else if (plugin.playersChoicePvESlime.contains(uuid)) {
                                                plugin.startTheGame.StartPvESlime(player);
                                            } else if (plugin.playersChoicePvEEnderman.contains(uuid)) {
                                                plugin.startTheGame.StartPvEEnderman(player);
                                            }
                                        } else {
                                            if (plugin.playerGroup.isPlayerInGroup(player)) {
                                                for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                                                    if (uuids != null) {
                                                        Player groupPlayer = Bukkit.getPlayer(uuids);
                                                        if (groupPlayer != null && !plugin.isPlayerPlayingFoodGame(player)) {
                                                            if (!plugin.deathmatch.isPlayerInTeams(groupPlayer) && !plugin.playerGroup.isPlayerChosenKit(groupPlayer)) {
                                                                plugin.playersRandomTeam.add(groupPlayer.getUniqueId());
                                                                plugin.playersRandomKit.add(groupPlayer.getUniqueId());
                                                                plugin.game.randomKitGiver(groupPlayer);
                                                                plugin.deathmatch.setPlayersRandomTeam(groupPlayer);
                                                            } else if (!plugin.deathmatch.isPlayerInTeams(groupPlayer) && plugin.playerGroup.isPlayerChosenKit(groupPlayer)) {
                                                                plugin.playersRandomTeam.add(groupPlayer.getUniqueId());
                                                                plugin.deathmatch.setPlayersRandomTeam(groupPlayer);
                                                            } else if (plugin.deathmatch.isPlayerInTeams(groupPlayer) && !plugin.playerGroup.isPlayerChosenKit(groupPlayer)) {
                                                                plugin.playersRandomKit.add(groupPlayer.getUniqueId());
                                                                plugin.game.randomKitGiver(groupPlayer);
                                                            } else if (!plugin.deathmatch.isPlayerInTeams(groupPlayer)) {
                                                                plugin.playersRandomTeam.add(groupPlayer.getUniqueId());
                                                                plugin.deathmatch.setPlayersRandomTeam(groupPlayer);
                                                            } else if (!plugin.playerGroup.isPlayerChosenKit(groupPlayer)) {
                                                                plugin.playersRandomKit.add(groupPlayer.getUniqueId());
                                                                plugin.game.randomKitGiver(groupPlayer);
                                                            }
                                                        }
                                                    }
                                                }
                                                if (plugin.playersChoiceFoodGame.contains(uuid)) {
                                                    plugin.startTheGame.StartFoodGame(player);
                                                } else if (plugin.playersThatChoice2Teams.contains(uuid)) {
                                                    plugin.startTheGame.StartTeams2(player);
                                                } else if (plugin.playersThatChoice3Teams.contains(uuid)) {
                                                    plugin.startTheGame.StartTeams3(player);
                                                } else if (plugin.playersThatChoice4Teams.contains(uuid)) {
                                                    plugin.startTheGame.StartTeams4(player);
                                                } else if (plugin.playersThatChoice5Teams.contains(uuid)) {
                                                    plugin.startTheGame.StartTeams5(player);
                                                } else if (plugin.playersChoiceFreeForAll.contains(uuid)) {
                                                    plugin.startTheGame.StartFreeForAll(player);
                                                } else {
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
                                                    player.sendMessage(ERROR + "Something went wrong while checking the player's status.");
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    ItemStack item = plugin.game.getItemByName(player.getInventory(), ReadyName);
                                    ItemStack item2 = plugin.game.getItemByName(player.getInventory(), NotReadyName);
                                    assert item != null;
                                    inventory.remove(item);
                                    assert item2 != null;
                                    inventory.remove(item2);
                                    player.sendMessage(ERROR + "You are not allowed to use this item.");
                                }
                                plugin.ReadySystem.remove(uuid);
                            } else if (inventory.contains(ItemManager.notReady) || inventory.contains(plugin.game.getItemByName(player.getInventory(), NotReadyName))) {
                                // From UNREADY To Ready
                                if (!plugin.game.isPlayerInGame(player) && plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                                    if (!plugin.playerGroup.isPlayerInGroup(player) && !plugin.playerPvE.isPlayerChooseToPlayPvE(player)) {
                                        plugin.ReadySystem.remove(uuid);
                                        player.sendMessage(ERROR + "You are not in a group.");
                                        return;
                                    }
                                    ItemStack item = plugin.game.getItemByName(player.getInventory(), NotReadyName);
                                    assert item != null;
                                    inventory.remove(item);
                                    inventory.addItem(ItemManager.isReady);
                                    plugin.ReadyPlayers.remove(uuid);
                                    player.sendMessage(NORMAL + "Unready");
                                    ItemStack itemByName = plugin.game.getItemByName(player.getInventory(), ReadyName);
                                    if (inventory.contains(ItemManager.isReady) || inventory.contains(itemByName)) {
                                        if (itemByName.getItemMeta() != null && itemByName.getItemMeta().getLore() != null) {
                                            itemByName.getItemMeta().getLore().clear();
                                        }
                                        plugin.game.setReadyStatus(player);
                                    }
                                    if (plugin.playerGroup.checkIfTheGroupIsReady(player)) {
                                        if (plugin.playerPvE.isPlayerChooseToPlayPvE(player)) {
                                            if (!plugin.playerGroup.isPlayerChosenKit(player)) {
                                                plugin.playersRandomKit.add(uuid);
                                                plugin.game.randomKitGiver(player);
                                            }
                                            if (plugin.playersChoicePvEZombie.contains(uuid)) {
                                                plugin.startTheGame.StartPvEZombie(player);
                                            } else if (plugin.playersChoicePvESkeleton.contains(uuid)) {
                                                plugin.startTheGame.StartPvESkeleton(player);
                                            } else if (plugin.playersChoicePvESpider.contains(uuid)) {
                                                plugin.startTheGame.StartPvESpider(player);
                                            } else if (plugin.playersChoicePvESlime.contains(uuid)) {
                                                plugin.startTheGame.StartPvESlime(player);
                                            } else if (plugin.playersChoicePvEEnderman.contains(uuid)) {
                                                plugin.startTheGame.StartPvEEnderman(player);
                                            }
                                        } else {
                                            if (plugin.playerGroup.isPlayerInGroup(player)) {
                                                for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                                                    if (uuids != null) {
                                                        Player groupPlayer = Bukkit.getPlayer(uuids);
                                                        if (groupPlayer != null && !plugin.isPlayerPlayingFoodGame(player)) {
                                                            if (!plugin.deathmatch.isPlayerInTeams(groupPlayer) && !plugin.playerGroup.isPlayerChosenKit(groupPlayer)) {
                                                                plugin.playersRandomTeam.add(groupPlayer.getUniqueId());
                                                                plugin.playersRandomKit.add(groupPlayer.getUniqueId());
                                                                plugin.game.randomKitGiver(groupPlayer);
                                                                plugin.deathmatch.setPlayersRandomTeam(groupPlayer);
                                                            } else if (!plugin.deathmatch.isPlayerInTeams(groupPlayer) && plugin.playerGroup.isPlayerChosenKit(groupPlayer)) {
                                                                plugin.playersRandomTeam.add(groupPlayer.getUniqueId());
                                                                plugin.deathmatch.setPlayersRandomTeam(groupPlayer);
                                                            } else if (plugin.deathmatch.isPlayerInTeams(groupPlayer) && !plugin.playerGroup.isPlayerChosenKit(groupPlayer)) {
                                                                plugin.playersRandomKit.add(groupPlayer.getUniqueId());
                                                                plugin.game.randomKitGiver(groupPlayer);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if (plugin.playersChoiceFoodGame.contains(uuid)) {
                                                plugin.startTheGame.StartFoodGame(player);
                                            } else if (plugin.playersThatChoice2Teams.contains(uuid)) {
                                                plugin.startTheGame.StartTeams2(player);
                                            } else if (plugin.playersThatChoice3Teams.contains(uuid)) {
                                                plugin.startTheGame.StartTeams3(player);
                                            } else if (plugin.playersThatChoice4Teams.contains(uuid)) {
                                                plugin.startTheGame.StartTeams4(player);
                                            } else if (plugin.playersThatChoice5Teams.contains(uuid)) {
                                                plugin.startTheGame.StartTeams5(player);
                                            } else if (plugin.playersChoiceFreeForAll.contains(uuid)) {
                                                plugin.startTheGame.StartFreeForAll(player);
                                            } else {
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
                                                player.sendMessage(ERROR + "Something went wrong while checking the player's status.");
                                            }
                                        }
                                    }
                                }
                            } else {
                                ItemStack item = plugin.game.getItemByName(player.getInventory(), NotReadyName);
                                ItemStack item2 = plugin.game.getItemByName(player.getInventory(), ReadyName);
                                assert item != null;
                                inventory.remove(item);
                                assert item2 != null;
                                inventory.remove(item2);
                            }
                            plugin.ReadySystem.remove(uuid);
                        }
                        if (!plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                            if (inventory.contains(ItemManager.isReady) || inventory.contains(plugin.game.getItemByName(player.getInventory(), ReadyName))) {
                                inventory.remove(ItemManager.isReady);
                                ItemStack itemByName = plugin.game.getItemByName(player.getInventory(), ReadyName);
                                if (itemByName != null) {
                                    inventory.remove(itemByName);
                                }
                            } else if (inventory.contains(ItemManager.notReady) || inventory.contains(plugin.game.getItemByName(player.getInventory(), NotReadyName))) {
                                inventory.remove(ItemManager.notReady);
                                if (plugin.game.getItemByName(player.getInventory(), NotReadyName) != null) {
                                    inventory.remove(plugin.game.getItemByName(player.getInventory(), NotReadyName));
                                }
                            }
                            plugin.ReadySystem.remove(uuid);
                        }
                        if (plugin.game.isPlayerInGame(player)) {
                            if (!plugin.mainConfig.getBooleanGame("hungry_during_a_game")) {
                                player.setFoodLevel(20);
                            }
                            if (inventory.contains(ItemManager.isReady) || inventory.contains(plugin.game.getItemByName(player.getInventory(), ReadyName))) {
                                inventory.remove(ItemManager.isReady);
                                inventory.remove(Objects.requireNonNull(plugin.game.getItemByName(player.getInventory(), ReadyName)));
                            } else if (inventory.contains(ItemManager.notReady) || inventory.contains(plugin.game.getItemByName(player.getInventory(), NotReadyName))) {
                                inventory.remove(ItemManager.notReady);
                                inventory.remove(Objects.requireNonNull(plugin.game.getItemByName(player.getInventory(), NotReadyName)));
                            }
                            plugin.ReadySystem.remove(uuid);
                        }
                        plugin.ReadySystem.remove(uuid);
                        if (inventory.contains(ItemManager.kitChooser) && !plugin.waitingLobby.isPlayerInWaitingLobby(player) || plugin.game.isPlayerInGame(player)) {
                            inventory.remove(ItemManager.kitChooser);
                        } else if (inventory.contains(ItemManager.teams) && !(plugin.waitingLobby.isPlayerInWaitingLobby(player) || plugin.game.isPlayerInGame(player))) {
                            inventory.remove(ItemManager.teams);
                        }
                        if (!plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                            if (inventory.contains(ItemManager.kitChooser)) {
                                inventory.remove(ItemManager.kitChooser);
                            }
                            if (!plugin.deathmatch.isPlayerHaveStartedTeams(player) && inventory.contains(ItemManager.teams)) {
                                inventory.remove(ItemManager.teams);
                            }
                        }
                        if (!plugin.game.isPlayerInGame(player) && !plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                            if (inventory.contains(ItemManager.kitChooser)) {
                                inventory.remove(ItemManager.kitChooser);
                            }
                            if (!plugin.deathmatch.isPlayerHaveStartedTeams(player) && inventory.contains(ItemManager.teams)) {
                                inventory.remove(ItemManager.teams);
                            }
                            // Bread
                            if (inventory.contains(ItemManager.BreadKit) && !(plugin.playersInBreadKit.contains(uuid))) {
                                inventory.remove(ItemManager.BreadKit);
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                            }
                            // Melon
                            if (inventory.contains(ItemManager.MelonKit) && !(plugin.playersInMelonKit.contains(uuid))) {
                                inventory.remove(ItemManager.MelonKit);
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                //player.removePotionEffect(PotionEffectType.SPEED);
                            }
                            // Beef
                            if (inventory.contains(ItemManager.BeefKit) && !(plugin.playersInBeefKit.contains(uuid))) {
                                inventory.remove(ItemManager.BeefKit);
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                //player.removePotionEffect(PotionEffectType.SPEED);
                            }
                            // Potato
                            if (inventory.contains(ItemManager.PotatoKit) && !(plugin.playersInPotatoKit.contains(uuid))) {
                                inventory.remove(ItemManager.PotatoKit);
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                //player.removePotionEffect(PotionEffectType.SPEED);
                            }
                            // Cookie
                            if (inventory.contains(ItemManager.CookieKit) && !(plugin.playersInCookieKit.contains(uuid))) {
                                inventory.remove(ItemManager.CookieKit);
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                //player.removePotionEffect(PotionEffectType.SPEED);
                            }
                            // Fish
                            if (inventory.contains(ItemManager.FishKit) && !plugin.playersInFishKit.contains(uuid)) {
                                inventory.remove(ItemManager.FishKit);
                                // Health
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                //player.removePotionEffect(PotionEffectType.SPEED);
                            }
                            // WITHOUT a list
                            plugin.playersInBreadKit.remove(uuid);
                            plugin.playersInMelonKit.remove(uuid);
                            plugin.playersInFishKit.remove(uuid);
                            plugin.playersInPotatoKit.remove(uuid);
                            plugin.playersInCookieKit.remove(uuid);
                            plugin.playersInBeefKit.remove(uuid);
                        }
                        if (!plugin.waitingLobby.isPlayerInWaitingLobby(player) && plugin.game.isPlayerInGame(player)) {
                            if (inventory.contains(ItemManager.BreadKit) && plugin.playersInBreadKit.contains(uuid) && !player.hasPotionEffect(PotionEffectType.SPEED)) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 2));
                            }
                            // Bread
                            if (!inventory.contains(ItemManager.BreadKit) && plugin.playersInBreadKit.contains(uuid)) {
                                inventory.addItem(ItemManager.BreadKit);
                                // Health
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 2));
                            }
                            if (inventory.contains(ItemManager.BreadKit) && !plugin.playersInBreadKit.contains(uuid)) {
                                inventory.remove(ItemManager.BreadKit);
                                // Health
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                //player.removePotionEffect(PotionEffectType.SPEED);
                            }
                            // Beef
                            if (!inventory.contains(ItemManager.BeefKit) && plugin.playersInBeefKit.contains(uuid)) {
                                inventory.addItem(ItemManager.BeefKit);
                                // Health
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 2));
                            }
                            if (inventory.contains(ItemManager.BeefKit) && !plugin.playersInBeefKit.contains(uuid)) {
                                inventory.remove(ItemManager.BeefKit);
                                // Health
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                //player.removePotionEffect(PotionEffectType.SPEED);
                            }
                            // Cookie
                            if (!inventory.contains(ItemManager.CookieKit) && plugin.playersInCookieKit.contains(uuid)) {
                                inventory.addItem(ItemManager.CookieKit);
                                // Health
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 2));
                            }
                            if (inventory.contains(ItemManager.CookieKit) && !plugin.playersInCookieKit.contains(uuid)) {
                                inventory.remove(ItemManager.CookieKit);
                                // Health
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                //player.removePotionEffect(PotionEffectType.SPEED);
                            }
                            // Melon
                            if (inventory.contains(ItemManager.MelonKit) && !plugin.playersInMelonKit.contains(uuid)) {
                                inventory.remove(ItemManager.MelonKit);
                                // Health
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                //player.removePotionEffect(PotionEffectType.SPEED);
                            }
                            if (!inventory.contains(ItemManager.MelonKit) && plugin.playersInMelonKit.contains(uuid)) {
                                inventory.addItem(ItemManager.MelonKit);
                                // Health
                                attributeInstance.setBaseValue(someHealth);
                                player.setHealth(someHealth);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 2));
                            }
                            // Potato
                            if (inventory.contains(ItemManager.PotatoKit) && !plugin.playersInPotatoKit.contains(uuid)) {
                                inventory.remove(ItemManager.PotatoKit);
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                //player.removePotionEffect(PotionEffectType.SPEED);
                            }
                            if (!(inventory.contains(ItemManager.PotatoKit)) && plugin.playersInPotatoKit.contains(uuid)) {
                                inventory.addItem(ItemManager.PotatoKit);
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 2));
                            }
                            // Fish
                            if (inventory.contains(ItemManager.FishKit) && !plugin.playersInFishKit.contains(uuid)) {
                                inventory.remove(ItemManager.FishKit);
                                // Health
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                //player.removePotionEffect(PotionEffectType.SPEED);
                            }
                            if (!inventory.contains(ItemManager.FishKit) && plugin.playersInFishKit.contains(uuid)) {
                                inventory.addItem(ItemManager.FishKit);
                                // Health
                                attributeInstance.setBaseValue(someHealth);
                                player.setHealth(someHealth);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 2));
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 25);
    }
}
