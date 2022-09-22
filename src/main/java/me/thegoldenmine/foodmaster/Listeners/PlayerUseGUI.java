package me.thegoldenmine.foodmaster.Listeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PlayerUseGUI implements Listener {
    private final FoodMaster plugin;

    public PlayerUseGUI(FoodMaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInv(InventoryClickEvent event) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String s4;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s4 = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s4 = " FoodMaster ";
        }
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        // pve
        boolean checkPvE = inv.contains(ItemManager.PvEZombie) && inv.contains(ItemManager.PvESkeleton) && inv.contains(ItemManager.PvESpider);
        // teams
        boolean checkTeams = inv.contains(ItemManager.team2) && inv.contains(ItemManager.team3) && inv.contains(ItemManager.team4) && inv.contains(ItemManager.team5);

        // pvp
        boolean freeforallCheck = player.hasPermission("foodm.pvp.freeforall") && inv.contains(ItemManager.freeforall);
        boolean teamdeathmatchCheck = player.hasPermission("foodm.pvp.teamdeathmatch") && inv.contains(ItemManager.teamDeathmatch);
        boolean foodGameCheck = player.hasPermission("foodm.pvp.foodgame") && inv.contains(ItemManager.foodGame);
        boolean allPvP = freeforallCheck || teamdeathmatchCheck || foodGameCheck;
        // main
        boolean rejoinCheck = player.hasPermission("foodm.rejoin") && inv.contains(ItemManager.rejoinGUI);
        boolean helpCheck = player.hasPermission("foodm.help") && inv.contains(ItemManager.helpGUI);
        boolean pvpCheck = player.hasPermission("foodm.pvp") && inv.contains(ItemManager.pvpGUI);
        boolean pveCheck = player.hasPermission("foodm.pve") && inv.contains(ItemManager.pveGUI);
        boolean all = rejoinCheck || helpCheck || pvpCheck || pveCheck;
        if (checkTeams) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                player.closeInventory();
                return;
            }
            switch (Objects.requireNonNull(event.getCurrentItem()).getType()) {
                case WOODEN_AXE:
                    player.closeInventory();
                    String[] command2 = {"start", "team-deathmatch", "2"};
                    plugin.startCommand.mainStart(player, command2);
                    break;
                case STONE_AXE:
                    player.closeInventory();
                    String[] command3 = {"start", "team-deathmatch", "3"};
                    plugin.startCommand.mainStart(player, command3);
                    break;
                case IRON_AXE:
                    player.closeInventory();
                    String[] command4 = {"start", "team-deathmatch", "4"};
                    plugin.startCommand.mainStart(player, command4);
                    break;
                case DIAMOND_AXE:
                    player.closeInventory();
                    String[] command5 = {"start", "team-deathmatch", "5"};
                    plugin.startCommand.mainStart(player, command5);
                    break;
                case ARROW:
                    player.openInventory(plugin.createGUI.createPvP(player));
                    break;
                case BARRIER:
                    player.closeInventory();
                    break;
            }
        } else if (checkPvE) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                player.closeInventory();
                return;
            }
            switch (Objects.requireNonNull(event.getCurrentItem()).getType()) {
                case BARRIER:
                    player.closeInventory();
                    break;
                case ARROW:
                    player.openInventory(plugin.createGUI.createMain(player));
                    break;
                case ROTTEN_FLESH:
                    player.closeInventory();
                    String[] command = {"start", "pve", "Zombie"};
                    plugin.startCommand.mainStart(player, command);
                    break;
                case BONE:
                    player.closeInventory();
                    String[] command1 = {"start", "pve", "Skeleton"};
                    plugin.startCommand.mainStart(player, command1);
                    break;
                case SPIDER_EYE:
                    player.closeInventory();
                    String[] command2 = {"start", "pve", "Spider"};
                    plugin.startCommand.mainStart(player, command2);
                    break;
                case SLIME_BALL:
                    player.closeInventory();
                    String[] command21 = {"start", "pve", "Slime"};
                    plugin.startCommand.mainStart(player, command21);
                    break;
                case ENDER_PEARL:
                    player.closeInventory();
                    String[] command23 = {"start", "pve", "Enderman"};
                    plugin.startCommand.mainStart(player, command23);
                    break;
            }
        } else if (allPvP) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                player.closeInventory();
                return;
            }
            switch (Objects.requireNonNull(event.getCurrentItem()).getType()) {
                case GOLDEN_APPLE:
                    if (plugin.isPlayerInGroup(player)) {
                        String[] command = {"start", "food-game"};
                        plugin.startCommand.mainStart(player, command);
                    } else {
                        player.sendMessage(ERROR+"You have to be in group in order to start a game.");
                    }
                    break;
                case STONE_SWORD:
                    if (plugin.isPlayerInGroup(player)) {
                        player.openInventory(plugin.createGUI.createTeamGUI());
                    } else {
                        player.sendMessage(ERROR+"You have to be in group in order to start a game.");
                    }
                    break;
                case GOLDEN_SWORD:
                    if (plugin.isPlayerInGroup(player)) {
                        String[] command = {"start", "free-for-all"};
                        plugin.startCommand.mainStart(player, command);
                    } else {
                        player.sendMessage(ERROR+"You have to be in group in order to start a game.");
                    }
                    break;
                case BARRIER:
                    player.closeInventory();
                    break;
                case ARROW:
                    player.openInventory(plugin.createGUI.createMain(player));
                    break;
            }
        } else if (all) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                player.closeInventory();
                return;
            }
            switch (Objects.requireNonNull(event.getCurrentItem()).getType()) {
                case SNOWBALL:
                    player.closeInventory();
                    plugin.rejoin.rejoinCommand(player);
                    break;
                case BOOK:
                    player.closeInventory();
                    plugin.helpMenu.helpAll(player);
                    break;
                case IRON_SWORD:
                    player.openInventory(plugin.createGUI.createPvP(player));
                    break;
                case WITHER_SKELETON_SKULL:
                    if (player.hasPermission("foodm.pve")) {
                        player.openInventory(plugin.createGUI.createPvE());
                    } else {
                        player.sendMessage(ERROR+"You don't have "+gold+""+italic+"foodm.pve"+red+""+italic+" permission to play PvE");
                    }
                    break;
                case BARRIER:
                    player.closeInventory();
                    break;
            }
        } else if (inv.equals(plugin.createGUI.KitGui())) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                player.closeInventory();
                player.sendMessage(INFO + "You have to choose a kit.");
                return;
            }
            switch (Objects.requireNonNull(event.getCurrentItem()).getType()) {
                case COOKED_BEEF:
                    player.closeInventory();
                    if (plugin.playersInBeefKit.contains(uuid)) {
                        player.sendMessage(INFO + "You have already chosen "+gold+""+italic+"Beef"+aqua+""+italic+" Kit.");
                    } else {
                        plugin.playersInFishKit.remove(uuid);
                        plugin.playersInMelonKit.remove(uuid);
                        plugin.playersInCookieKit.remove(uuid);
                        plugin.playersInBreadKit.remove(uuid);
                        plugin.playersRandomKit.remove(uuid);
                        plugin.playersInPotatoKit.remove(uuid);
                        plugin.playersInBeefKit.add(uuid);
                        player.sendMessage(NORMAL + "You chose "+gold+""+italic+"Beef Kit.");
                    }
                    break;
                case TROPICAL_FISH:
                    player.closeInventory();
                    if (plugin.playersInFishKit.contains(uuid)) {
                        player.sendMessage(INFO + "You have already chosen "+gold+""+italic+"Fish"+aqua+""+italic+" Kit.");
                    } else {
                        plugin.playersInMelonKit.remove(uuid);
                        plugin.playersInCookieKit.remove(uuid);
                        plugin.playersInBreadKit.remove(uuid);
                        plugin.playersRandomKit.remove(uuid);
                        plugin.playersInPotatoKit.remove(uuid);
                        plugin.playersInBeefKit.remove(uuid);
                        plugin.playersInFishKit.add(uuid);
                        player.sendMessage(NORMAL + "You chose "+gold+""+italic+"Fish Kit.");
                    }
                    break;
                case COOKIE:
                    player.closeInventory();
                    if (plugin.playersInCookieKit.contains(uuid)) {
                        player.sendMessage(INFO + "You have already chosen "+gold+""+italic+"Cookie"+aqua+""+italic+" Kit.");
                    } else {
                        plugin.playersInMelonKit.remove(uuid);
                        plugin.playersInFishKit.remove(uuid);
                        plugin.playersRandomKit.remove(uuid);
                        plugin.playersInBreadKit.remove(uuid);
                        plugin.playersInPotatoKit.remove(uuid);
                        plugin.playersInBeefKit.remove(uuid);
                        plugin.playersInCookieKit.add(uuid);
                        player.sendMessage(NORMAL + "You chose "+gold+""+italic+"Cookie"+green+""+italic+" Kit.");
                    }
                    break;
                case MELON_SLICE:
                    player.closeInventory();
                    if (plugin.playersInMelonKit.contains(uuid)) {
                        player.sendMessage(INFO + "You have already chosen "+gold+""+italic+"Melon"+aqua+""+italic+" Kit.");
                    } else {
                        plugin.playersInCookieKit.remove(uuid);
                        plugin.playersInFishKit.remove(uuid);
                        plugin.playersInBreadKit.remove(uuid);
                        plugin.playersInPotatoKit.remove(uuid);
                        plugin.playersRandomKit.remove(uuid);
                        plugin.playersInBeefKit.remove(uuid);
                        plugin.playersInMelonKit.add(uuid);
                        player.sendMessage(NORMAL + "You choose "+gold+""+italic+"Melon"+green+""+italic+" Kit.");
                    }
                    break;
                case BREAD:
                    player.closeInventory();
                    if (plugin.playersInBreadKit.contains(uuid)) {
                        player.sendMessage(INFO + " You have already chosen "+gold+""+italic+"Bread"+aqua+""+italic+" Kit.");
                    } else {
                        plugin.playersInCookieKit.remove(uuid);
                        plugin.playersInFishKit.remove(uuid);
                        plugin.playersInMelonKit.remove(uuid);
                        plugin.playersInPotatoKit.remove(uuid);
                        plugin.playersInBreadKit.add(uuid);
                        plugin.playersInBeefKit.remove(uuid);
                        plugin.playersRandomKit.remove(uuid);
                        player.sendMessage(NORMAL + "You choose "+gold+""+italic+"Bread"+green+""+italic+" Kit.");
                    }
                    break;
                case POTATO:
                    player.closeInventory();
                    if (plugin.playersInPotatoKit.contains(uuid)) {
                        player.sendMessage(INFO + "You have already chosen "+gold+""+italic+"Potato"+aqua+""+italic+" Kit.");
                    } else {
                        plugin.playersInCookieKit.remove(uuid);
                        plugin.playersInFishKit.remove(uuid);
                        plugin.playersInMelonKit.remove(uuid);
                        plugin.playersInBreadKit.remove(uuid);
                        plugin.playersInBeefKit.remove(uuid);
                        plugin.playersRandomKit.remove(uuid);
                        plugin.playersInPotatoKit.add(uuid);
                        player.sendMessage(NORMAL + "You chose "+gold+""+italic+"Potato"+green+""+italic+" Kit.");
                    }
                    break;
                case BARRIER:
                    player.closeInventory();
                    player.sendMessage(NORMAL + "The kit GUI is closed.");
                    break;
                case DIAMOND:
                    player.closeInventory();
                    if (plugin.playersRandomKit.contains(uuid)) {
                        player.sendMessage(NORMAL + "You have already chosen a random kit.");
                    } else {
                        plugin.playersInCookieKit.remove(uuid);
                        plugin.playersInFishKit.remove(uuid);
                        plugin.playersInMelonKit.remove(uuid);
                        plugin.playersInBreadKit.remove(uuid);
                        plugin.playersInPotatoKit.remove(uuid);
                        plugin.playersInBeefKit.remove(uuid);
                        plugin.playersRandomKit.add(uuid);
                        plugin.randomKitGiver(player);
                    }
                    break;
            }
        } else if (inv.contains(plugin.getItemByName(inv, ItemManager.blue.getItemMeta().getDisplayName())) && inv.contains(plugin.getItemByName(inv, ItemManager.red.getItemMeta().getDisplayName())) && inv.contains(plugin.getItemByName(inv, ItemManager.close.getItemMeta().getDisplayName())) && inv.contains(plugin.getItemByName(inv, ItemManager.glassForGUI.getItemMeta().getDisplayName()))) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                player.closeInventory();
                player.sendMessage(NORMAL + "You have to choose a team.");
                return;
            }
            String s = "You have joined the ";
            String s1 = "You are already on the ";
            switch (Objects.requireNonNull(event.getCurrentItem()).getType()) {
                case BLUE_WOOL:
                    player.closeInventory();
                    if (plugin.playersInBlueTeams.contains(uuid)) {
                        player.sendMessage(INFO + s1 + ChatColor.BLUE + "" + italic + "Blue" + aqua + "" + italic + " Team.");
                    } else {
                        // remove from other
                        plugin.playersInRedTeams.remove(uuid);
                        plugin.playersInYellowTeams.remove(uuid);
                        plugin.playersInGreenTeams.remove(uuid);
                        plugin.playersRandomTeam.remove(uuid);
                        plugin.playersInCyanTeams.remove(uuid);
                        // add
                        plugin.playersInBlueTeams.add(uuid);
                        if (plugin.playersThatChoice2Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam2.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam2.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam2.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam2.setItem(12, ItemManager.red);
                            }
                            if (plugin.createGUI.guiTeam2.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam2.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam2.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam2.setItem(11, ItemManager.blue);
                            }
                        } else if (plugin.playersThatChoice3Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam3.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam3.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam3.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam3.setItem(13, ItemManager.red);
                            }
                            if (plugin.createGUI.guiTeam3.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam3.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam3.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam3.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam3.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam3.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam3.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam3.setItem(12, ItemManager.green);
                            }
                        } else if (plugin.playersThatChoice4Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam4.getItem(14) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(14));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(14, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(14, ItemManager.red);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(12, ItemManager.aqua);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam2.setItem(13, ItemManager.green);
                            }
                        } else if (plugin.playersThatChoice5Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam5.getItem(15) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(15));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(15, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(15, ItemManager.red);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(12, ItemManager.aqua);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(13, ItemManager.green);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(14) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(14));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(14, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(14, ItemManager.yellow);
                            }
                        }
                        player.sendMessage(NORMAL + s + ChatColor.BLUE + "" + italic + "Blue" + green + "" + italic + " Team.");
                    }
                    break;
                case RED_WOOL:
                    player.closeInventory();
                    if (plugin.playersInRedTeams.contains(uuid)) {
                        player.sendMessage(INFO + s1 + red + "" + italic + "Red" + aqua + "" + italic + " Team.");
                    } else {
                        // remove from other
                        plugin.playersInBlueTeams.remove(uuid);
                        plugin.playersInYellowTeams.remove(uuid);
                        plugin.playersInGreenTeams.remove(uuid);
                        plugin.playersRandomTeam.remove(uuid);
                        plugin.playersInCyanTeams.remove(uuid);
                        // add
                        plugin.playersInRedTeams.add(uuid);
                        if (plugin.playersThatChoice2Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam2.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam2.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam2.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam2.setItem(12, ItemManager.red);
                            }
                            if (plugin.createGUI.guiTeam2.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam2.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam2.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam2.setItem(11, ItemManager.blue);
                            }
                        } else if (plugin.playersThatChoice3Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam3.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam3.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam3.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam3.setItem(13, ItemManager.red);
                            }
                            if (plugin.createGUI.guiTeam3.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam3.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam3.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam3.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam3.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam3.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam3.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam3.setItem(12, ItemManager.green);
                            }
                        } else if (plugin.playersThatChoice4Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam4.getItem(14) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(14));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(14, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(14, ItemManager.red);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(12, ItemManager.aqua);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam2.setItem(13, ItemManager.green);
                            }
                        } else if (plugin.playersThatChoice5Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam5.getItem(15) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(15));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(15, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(15, ItemManager.red);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(12, ItemManager.aqua);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(13, ItemManager.green);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(14) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(14));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(14, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(14, ItemManager.yellow);
                            }
                        }
                        player.sendMessage(NORMAL + s + red + "" + italic + "Red" + green + "" + italic + " Team.");
                    }
                    break;
                case CYAN_WOOL:
                    player.closeInventory();
                    if (plugin.playersInCyanTeams.contains(uuid)) {
                        player.sendMessage(INFO + "You are already on the Cyan Team.");
                    } else {
                        // remove from other
                        plugin.playersInBlueTeams.remove(uuid);
                        plugin.playersInYellowTeams.remove(uuid);
                        plugin.playersRandomTeam.remove(uuid);
                        plugin.playersInGreenTeams.remove(uuid);
                        plugin.playersInRedTeams.remove(uuid);
                        // add
                        plugin.playersInCyanTeams.add(uuid);
                        if (plugin.playersThatChoice4Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam4.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(12, ItemManager.aqua);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(13, ItemManager.green);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(14) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(14));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(14, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(14, ItemManager.red);
                            }
                        } else if (plugin.playersThatChoice5Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam5.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(12, ItemManager.aqua);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(13, ItemManager.green);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(14) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(14));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(14, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(14, ItemManager.yellow);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(15) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(15));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(15, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(15, ItemManager.red);
                            }
                        }
                        player.sendMessage(NORMAL + s + aqua + "" + italic + "Cyan" + green + "" + italic + " Team.");
                    }
                    break;
                case GREEN_WOOL:
                    player.closeInventory();
                    if (plugin.playersInGreenTeams.contains(uuid)) {
                        player.sendMessage(INFO + s1 + ChatColor.DARK_GREEN + "" + italic + "Green" + aqua + "" + italic + " Team.");
                    } else {
                        // remove from other
                        plugin.playersInBlueTeams.remove(uuid);
                        plugin.playersInYellowTeams.remove(uuid);
                        plugin.playersInCyanTeams.remove(uuid);
                        plugin.playersRandomTeam.remove(uuid);
                        plugin.playersInRedTeams.remove(uuid);
                        // add
                        plugin.playersInGreenTeams.add(uuid);
                        if (plugin.playersThatChoice3Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam3.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam3.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam3.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam3.setItem(12, ItemManager.green);
                            }
                            if (plugin.createGUI.guiTeam3.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam3.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam3.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam3.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam3.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam3.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam3.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam3.setItem(13, ItemManager.red);
                            }
                        } else if (plugin.playersThatChoice4Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam4.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(13, ItemManager.green);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(12, ItemManager.aqua);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(14) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(14));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(14, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(14, ItemManager.red);
                            }
                        } else if (plugin.playersThatChoice5Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam5.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(13, ItemManager.green);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(12, ItemManager.aqua);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(14) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(14));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(14, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(14, ItemManager.yellow);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(15) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(15));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(15, ItemManager.red);
                            }
                        }
                        player.sendMessage(NORMAL + s + ChatColor.DARK_GREEN + "" + italic + "Green" + green + "" + italic + " Team.");
                    }
                    break;
                case YELLOW_WOOL:
                    player.closeInventory();
                    if (plugin.playersInYellowTeams.contains(uuid)) {
                        player.sendMessage(INFO + s1 + yellow + "" + italic + "Yellow" + aqua + "" + italic + " Team.");
                    } else {
                        // remove from other
                        plugin.playersInBlueTeams.remove(uuid);
                        plugin.playersInGreenTeams.remove(uuid);
                        plugin.playersInCyanTeams.remove(uuid);
                        plugin.playersRandomTeam.remove(uuid);
                        plugin.playersInRedTeams.remove(uuid);
                        // add
                        plugin.playersInYellowTeams.add(uuid);
                        if (plugin.playersThatChoice5Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam5.getItem(14) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(14));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(14, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(14, ItemManager.yellow);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(12, ItemManager.aqua);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(13, ItemManager.green);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(15) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(15));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(15, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(15, ItemManager.red);
                            }
                        }
                        player.sendMessage(NORMAL + s + yellow + "" + italic + "Yellow" + green + "" + italic + " Team.");
                    }
                    break;
                case BARRIER:
                    player.closeInventory();
                    player.sendMessage(NORMAL + "The team GUI is closed.");
                    break;
                case DIAMOND:
                    player.closeInventory();
                    if (plugin.playersRandomTeam.contains(uuid)) {
                        player.sendMessage(INFO + "You have already chosen a random team.");
                    } else {
                        plugin.playersInBlueTeams.remove(uuid);
                        plugin.playersInGreenTeams.remove(uuid);
                        plugin.playersInCyanTeams.remove(uuid);
                        plugin.playersInRedTeams.remove(uuid);
                        plugin.playersInYellowTeams.remove(uuid);
                        plugin.playersRandomTeam.add(uuid);
                        if (plugin.playersThatChoice2Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam2.getItem(18) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam2.getItem(18));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam2.setItem(18, item);
                            } else {
                                plugin.createGUI.guiTeam2.setItem(18, ItemManager.randomChoice);
                            }
                            if (plugin.createGUI.guiTeam2.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam2.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam2.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam2.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam2.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam2.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam2.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam2.setItem(12, ItemManager.red);
                            }
                        } else if (plugin.playersThatChoice3Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam3.getItem(18) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam3.getItem(18));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam3.setItem(18, item);
                            } else {
                                plugin.createGUI.guiTeam3.setItem(18, ItemManager.randomChoice);
                            }
                            if (plugin.createGUI.guiTeam3.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam3.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam3.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam3.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam3.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam3.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam3.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam3.setItem(12, ItemManager.green);
                            }
                            if (plugin.createGUI.guiTeam3.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam3.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam3.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam3.setItem(13, ItemManager.red);
                            }
                        } else if (plugin.playersThatChoice4Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam4.getItem(18) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(18));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(18, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(18, ItemManager.randomChoice);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(12, ItemManager.aqua);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(14) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(14));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(14, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(14, ItemManager.red);
                            }
                            if (plugin.createGUI.guiTeam4.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam4.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam4.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam4.setItem(13, ItemManager.green);
                            }
                        } else if (plugin.playersThatChoice5Teams.contains(uuid)) {
                            if (plugin.createGUI.guiTeam5.getItem(18) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(18));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.add(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(18, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(18, ItemManager.randomChoice);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(11) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(11));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(11, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(11, ItemManager.blue);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(12) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(12));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(12, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(12, ItemManager.aqua);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(13) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(13));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(13, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(13, ItemManager.green);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(14) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(14));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(14, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(14, ItemManager.yellow);
                            }
                            if (plugin.createGUI.guiTeam5.getItem(15) != null) {
                                ItemStack item = new ItemStack(plugin.createGUI.guiTeam5.getItem(15));
                                ItemMeta meta = item.getItemMeta();
                                assert meta != null;
                                List<String> lore = item.getItemMeta().getLore();
                                if (lore != null) {
                                    lore.remove(gold + "" + player.getName());
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                plugin.createGUI.guiTeam5.setItem(15, item);
                            } else {
                                plugin.createGUI.guiTeam5.setItem(15, ItemManager.red);
                            }
                        }
                        plugin.setPlayersRandomTeam(player);
                    }
                    break;
            }
        }
    }
}
