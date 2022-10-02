package me.thegoldenmine.foodmaster.Listeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class UseTeamGUI {
    private final FoodMaster plugin;

    public UseTeamGUI(FoodMaster main) {
        plugin = main;
    }

    public void BlueWool(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String s4;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s4 = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s4 = " FoodMaster ";
        }
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        UUID uuid = player.getUniqueId();
        player.closeInventory();
        if (plugin.playersInBlueTeams.contains(uuid)) {
            player.sendMessage(INFO + "You are already on the " + ChatColor.BLUE + "" + italic + "Blue" + aqua + "" + italic + " Team.");
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
            player.sendMessage(NORMAL + "You have joined the " + ChatColor.BLUE + "" + italic + "Blue" + green + "" + italic + " Team.");
        }
    }

    public void RedWool(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String s4;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s4 = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s4 = " FoodMaster ";
        }
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        UUID uuid = player.getUniqueId();
        player.closeInventory();
        if (plugin.playersInRedTeams.contains(uuid)) {
            player.sendMessage(INFO + "You are already on the " + red + "" + italic + "Red" + aqua + "" + italic + " Team.");
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
            player.sendMessage(NORMAL + "You have joined the " + red + "" + italic + "Red" + green + "" + italic + " Team.");
        }
    }

    public void YellowWool(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor yellow = ChatColor.YELLOW;
        String s4;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s4 = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s4 = " FoodMaster ";
        }
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        UUID uuid = player.getUniqueId();
        player.closeInventory();
        if (plugin.playersInYellowTeams.contains(uuid)) {
            player.sendMessage(INFO + "You are already on the " + yellow + "" + italic + "Yellow" + aqua + "" + italic + " Team.");
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
            player.sendMessage(NORMAL + "You have joined the " + yellow + "" + italic + "Yellow" + green + "" + italic + " Team.");
        }
    }

    public void GreenWool(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String s4;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s4 = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s4 = " FoodMaster ";
        }
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        UUID uuid = player.getUniqueId();
        player.closeInventory();
        if (plugin.playersInGreenTeams.contains(uuid)) {
            player.sendMessage(INFO + "You are already on the " + ChatColor.DARK_GREEN + "" + italic + "Green" + aqua + "" + italic + " Team.");
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
            player.sendMessage(NORMAL + "You have joined the " + ChatColor.DARK_GREEN + "" + italic + "Green" + green + "" + italic + " Team.");
        }
    }

    public void CyanWool(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String s4;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s4 = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s4 = " FoodMaster ";
        }
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s4 + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        UUID uuid = player.getUniqueId();
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
            player.sendMessage(NORMAL + "You have joined the " + aqua + "" + italic + "Cyan" + green + "" + italic + " Team.");
        }
    }

}
