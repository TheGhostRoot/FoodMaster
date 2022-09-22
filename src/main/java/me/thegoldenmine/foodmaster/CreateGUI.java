package me.thegoldenmine.foodmaster;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CreateGUI {
    public final Inventory guiKit;
    public Inventory guiTeam2;
    public Inventory guiTeam3;
    public Inventory guiTeam5;
    public Inventory guiTeam4;
    private FoodMaster plugin;

    public CreateGUI(FoodMaster main) {
        guiKit = Bukkit.createInventory(null, 27, ChatColor.AQUA + "" + ChatColor.BOLD + "Choose your Food");
        guiKit.setItem(0, ItemManager.glassForGUI);
        guiKit.setItem(1, ItemManager.glassForGUI);
        guiKit.setItem(2, ItemManager.glassForGUI);
        guiKit.setItem(3, ItemManager.glassForGUI);
        guiKit.setItem(4, ItemManager.glassForGUI);
        guiKit.setItem(5, ItemManager.glassForGUI);
        guiKit.setItem(6, ItemManager.glassForGUI);
        guiKit.setItem(7, ItemManager.glassForGUI);
        guiKit.setItem(8, ItemManager.close);
        guiKit.setItem(9, ItemManager.glassForGUI);
        guiKit.setItem(10, ItemManager.glassForGUI);
        // kits start
        guiKit.setItem(11, ItemManager.fish);
        guiKit.setItem(12, ItemManager.bread);
        guiKit.setItem(13, ItemManager.potato);
        guiKit.setItem(14, ItemManager.melon);
        guiKit.setItem(15, ItemManager.cookie);
        guiKit.setItem(22, ItemManager.beef);
        // kits end
        guiKit.setItem(16, ItemManager.glassForGUI);
        guiKit.setItem(17, ItemManager.glassForGUI);
        guiKit.setItem(18, ItemManager.randomChoice);
        guiKit.setItem(19, ItemManager.glassForGUI);
        guiKit.setItem(20, ItemManager.glassForGUI);
        guiKit.setItem(21, ItemManager.glassForGUI);
        guiKit.setItem(23, ItemManager.glassForGUI);
        guiKit.setItem(24, ItemManager.glassForGUI);
        guiKit.setItem(25, ItemManager.glassForGUI);
        guiKit.setItem(26, ItemManager.glassForGUI);
        plugin = main;
    }

    public void waitGUIkitChoose(Player player) {
        player.openInventory(guiKit);
    }

    public Inventory KitGui() {
        return guiKit;
    }

    public void openTeamGUI(Player player, int team) {
        if (team == 2) {
            player.openInventory(guiTeam2);
        } else if (team == 3) {
            player.openInventory(guiTeam3);
        } else if (team == 4) {
            player.openInventory(guiTeam4);
        } else if (team == 5) {
            player.openInventory(guiTeam5);
        }
    }

    public Inventory getTeamGUI(int team) {
        if (team == 2) {
            return guiTeam2;
        } else if (team == 3) {
            return guiTeam3;
        } else if (team == 4) {
            return guiTeam4;
        } else if (team == 5) {
            return guiTeam5;
        }
        return null;
    }

    public Inventory createTeam5GUI(ItemStack red, ItemStack blue, ItemStack green, ItemStack aqua, ItemStack yellow) {
        //team5
        guiTeam5 = Bukkit.createInventory(null, 27, ChatColor.AQUA + "" + ChatColor.BOLD + "Choose your team");
        guiTeam5.setItem(0, ItemManager.glassForGUI);
        guiTeam5.setItem(1, ItemManager.glassForGUI);
        guiTeam5.setItem(2, ItemManager.glassForGUI);
        guiTeam5.setItem(3, ItemManager.glassForGUI);
        guiTeam5.setItem(4, ItemManager.glassForGUI);
        guiTeam5.setItem(5, ItemManager.glassForGUI);
        guiTeam5.setItem(6, ItemManager.glassForGUI);
        guiTeam5.setItem(7, ItemManager.glassForGUI);
        guiTeam5.setItem(8, ItemManager.close);
        guiTeam5.setItem(7, ItemManager.glassForGUI);
        guiTeam5.setItem(9, ItemManager.glassForGUI);
        guiTeam5.setItem(10, ItemManager.glassForGUI);
        // teams start
        guiTeam5.setItem(11, blue);
        guiTeam5.setItem(12, aqua);
        guiTeam5.setItem(13, green);
        guiTeam5.setItem(14, yellow);
        guiTeam5.setItem(15, red);
        // teams end
        guiTeam5.setItem(16, ItemManager.glassForGUI);
        guiTeam5.setItem(17, ItemManager.glassForGUI);
        guiTeam5.setItem(18, ItemManager.randomChoice);
        guiTeam5.setItem(19, ItemManager.glassForGUI);
        guiTeam5.setItem(20, ItemManager.glassForGUI);
        guiTeam5.setItem(21, ItemManager.glassForGUI);
        guiTeam5.setItem(22, ItemManager.glassForGUI);
        guiTeam5.setItem(23, ItemManager.glassForGUI);
        guiTeam5.setItem(24, ItemManager.glassForGUI);
        guiTeam5.setItem(25, ItemManager.glassForGUI);
        guiTeam5.setItem(26, ItemManager.glassForGUI);
        return guiTeam5;
    }

    public Inventory createTeam4GUI(ItemStack red, ItemStack blue, ItemStack green, ItemStack aqua) {
        // team 4
        guiTeam4 = Bukkit.createInventory(null, 27, ChatColor.AQUA + "" + ChatColor.BOLD + "Choose your team");
        guiTeam4.setItem(0, ItemManager.glassForGUI);
        guiTeam4.setItem(1, ItemManager.glassForGUI);
        guiTeam4.setItem(2, ItemManager.glassForGUI);
        guiTeam4.setItem(3, ItemManager.glassForGUI);
        guiTeam4.setItem(4, ItemManager.glassForGUI);
        guiTeam4.setItem(5, ItemManager.glassForGUI);
        guiTeam4.setItem(6, ItemManager.glassForGUI);
        guiTeam4.setItem(7, ItemManager.glassForGUI);
        guiTeam4.setItem(8, ItemManager.close);
        guiTeam4.setItem(7, ItemManager.glassForGUI);
        guiTeam4.setItem(9, ItemManager.glassForGUI);
        guiTeam4.setItem(10, ItemManager.glassForGUI);
        // teams start
        guiTeam4.setItem(11, blue);
        guiTeam4.setItem(12, aqua);
        guiTeam4.setItem(13, green);
        guiTeam4.setItem(14, red);
        // teams end
        guiTeam4.setItem(16, ItemManager.glassForGUI);
        guiTeam4.setItem(17, ItemManager.glassForGUI);
        guiTeam4.setItem(18, ItemManager.randomChoice);
        guiTeam4.setItem(19, ItemManager.glassForGUI);
        guiTeam4.setItem(20, ItemManager.glassForGUI);
        guiTeam4.setItem(21, ItemManager.glassForGUI);
        guiTeam4.setItem(22, ItemManager.glassForGUI);
        guiTeam4.setItem(23, ItemManager.glassForGUI);
        guiTeam4.setItem(24, ItemManager.glassForGUI);
        guiTeam4.setItem(25, ItemManager.glassForGUI);
        guiTeam4.setItem(26, ItemManager.glassForGUI);
        return guiTeam4;
    }

    public Inventory createTeam3GUI(ItemStack red, ItemStack blue, ItemStack green) {
        // team 3
        guiTeam3 = Bukkit.createInventory(null, 27, ChatColor.AQUA + "" + ChatColor.BOLD + "Choose your team");
        guiTeam3.setItem(0, ItemManager.glassForGUI);
        guiTeam3.setItem(1, ItemManager.glassForGUI);
        guiTeam3.setItem(2, ItemManager.glassForGUI);
        guiTeam3.setItem(3, ItemManager.glassForGUI);
        guiTeam3.setItem(4, ItemManager.glassForGUI);
        guiTeam3.setItem(5, ItemManager.glassForGUI);
        guiTeam3.setItem(6, ItemManager.glassForGUI);
        guiTeam3.setItem(7, ItemManager.glassForGUI);
        guiTeam3.setItem(8, ItemManager.close);
        guiTeam3.setItem(7, ItemManager.glassForGUI);
        guiTeam3.setItem(9, ItemManager.glassForGUI);
        guiTeam3.setItem(10, ItemManager.glassForGUI);
        // teams start
        guiTeam3.setItem(11, blue);
        guiTeam3.setItem(12, green);
        guiTeam3.setItem(13, red);
        // teams end
        guiTeam3.setItem(14, ItemManager.glassForGUI);
        guiTeam3.setItem(15, ItemManager.glassForGUI);
        guiTeam3.setItem(16, ItemManager.glassForGUI);
        guiTeam3.setItem(17, ItemManager.glassForGUI);
        guiTeam3.setItem(18, ItemManager.randomChoice);
        guiTeam3.setItem(19, ItemManager.glassForGUI);
        guiTeam3.setItem(20, ItemManager.glassForGUI);
        guiTeam3.setItem(21, ItemManager.glassForGUI);
        guiTeam3.setItem(22, ItemManager.glassForGUI);
        guiTeam3.setItem(23, ItemManager.glassForGUI);
        guiTeam3.setItem(24, ItemManager.glassForGUI);
        guiTeam3.setItem(25, ItemManager.glassForGUI);
        guiTeam3.setItem(26, ItemManager.glassForGUI);
        return guiTeam3;
    }

    public Inventory createTeam2GUI(ItemStack red, ItemStack blue) {
        guiTeam2 = Bukkit.createInventory(null, 27, ChatColor.AQUA + "" + ChatColor.BOLD + "Choose your team");
        guiTeam2.setItem(0, ItemManager.glassForGUI);
        guiTeam2.setItem(1, ItemManager.glassForGUI);
        guiTeam2.setItem(2, ItemManager.glassForGUI);
        guiTeam2.setItem(3, ItemManager.glassForGUI);
        guiTeam2.setItem(4, ItemManager.glassForGUI);
        guiTeam2.setItem(5, ItemManager.glassForGUI);
        guiTeam2.setItem(6, ItemManager.glassForGUI);
        guiTeam2.setItem(7, ItemManager.glassForGUI);
        guiTeam2.setItem(8, ItemManager.close);
        guiTeam2.setItem(7, ItemManager.glassForGUI);
        guiTeam2.setItem(9, ItemManager.glassForGUI);
        guiTeam2.setItem(10, ItemManager.glassForGUI);
        // teams start
        guiTeam2.setItem(11, blue);
        guiTeam2.setItem(12, red);
        // teams end
        guiTeam2.setItem(13, ItemManager.glassForGUI);
        guiTeam2.setItem(14, ItemManager.glassForGUI);
        guiTeam2.setItem(15, ItemManager.glassForGUI);
        guiTeam2.setItem(16, ItemManager.glassForGUI);
        guiTeam2.setItem(17, ItemManager.glassForGUI);
        guiTeam2.setItem(18, ItemManager.randomChoice);
        guiTeam2.setItem(19, ItemManager.glassForGUI);
        guiTeam2.setItem(20, ItemManager.glassForGUI);
        guiTeam2.setItem(21, ItemManager.glassForGUI);
        guiTeam2.setItem(22, ItemManager.glassForGUI);
        guiTeam2.setItem(23, ItemManager.glassForGUI);
        guiTeam2.setItem(24, ItemManager.glassForGUI);
        guiTeam2.setItem(25, ItemManager.glassForGUI);
        guiTeam2.setItem(26, ItemManager.glassForGUI);
        return guiTeam2;
    }

    public Inventory createMain(Player player) {
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        Inventory mainGUI = Bukkit.createInventory(null, 18, ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-" + ChatColor.GOLD + "" + ChatColor.BOLD + " "+s+" " + ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-" + ChatColor.GREEN + "" + ChatColor.ITALIC + " Main Menu");

        // 1 layer

        mainGUI.setItem(0, ItemManager.glassForGUI);
        mainGUI.setItem(1, ItemManager.glassForGUI);
        mainGUI.setItem(2, ItemManager.glassForGUI);
        if (player.hasPermission("foodm.pvp")) {
            mainGUI.setItem(3, ItemManager.pvpGUI);
        } else {
            mainGUI.setItem(3, ItemManager.glassForGUI);
        }
        mainGUI.setItem(4, ItemManager.glassForGUI);
        if (player.hasPermission("foodm.pve")) {
            mainGUI.setItem(5, ItemManager.pveGUI);
        } else {
            mainGUI.setItem(5, ItemManager.glassForGUI);
        }
        mainGUI.setItem(6, ItemManager.glassForGUI);
        mainGUI.setItem(7, ItemManager.glassForGUI);
        mainGUI.setItem(8, ItemManager.close);

        // 2 layer

        if (player.hasPermission("foodm.help")) {
            mainGUI.setItem(9, ItemManager.helpGUI);
        } else {
            mainGUI.setItem(9, ItemManager.glassForGUI);
        }
        mainGUI.setItem(10, ItemManager.glassForGUI);
        mainGUI.setItem(11, ItemManager.glassForGUI);
        mainGUI.setItem(12, ItemManager.glassForGUI);
        if (player.hasPermission("foodm.rejoin")) {
            mainGUI.setItem(13, ItemManager.rejoinGUI);
        } else {
            mainGUI.setItem(13, ItemManager.glassForGUI);
        }
        mainGUI.setItem(14, ItemManager.glassForGUI);
        mainGUI.setItem(15, ItemManager.glassForGUI);
        mainGUI.setItem(16, ItemManager.glassForGUI);
        mainGUI.setItem(17, ItemManager.glassForGUI);
        return mainGUI;
    }

    public Inventory createPvP(Player player) {
        // ---Free For All ---- Team Deathmatch --- close
        // ---King Of The Hill ---- Food Game -------
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        Inventory mainGUI = Bukkit.createInventory(null, 18, ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-" + ChatColor.GOLD + "" + ChatColor.BOLD + " "+s+" " + ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-" + ChatColor.RED + "" + ChatColor.ITALIC + " PvP");

        // 1 layer

        mainGUI.setItem(0, ItemManager.glassForGUI);
        mainGUI.setItem(1, ItemManager.glassForGUI);
        mainGUI.setItem(2, ItemManager.glassForGUI);
        if (player.hasPermission("foodm.pvp.freeforall")) {
            mainGUI.setItem(3, ItemManager.freeforall); // free for all
        } else {
            mainGUI.setItem(3, ItemManager.glassForGUI);
        }
        mainGUI.setItem(4, ItemManager.glassForGUI);
        if (player.hasPermission("foodm.pvp.teamdeathmatch")) {
            mainGUI.setItem(5, ItemManager.teamDeathmatch); // team deathmatch
        } else {
            mainGUI.setItem(5, ItemManager.glassForGUI);
        }
        mainGUI.setItem(6, ItemManager.glassForGUI);
        mainGUI.setItem(7, ItemManager.glassForGUI);
        mainGUI.setItem(8, ItemManager.close);

        // 2 layer

        mainGUI.setItem(9, ItemManager.back);
        mainGUI.setItem(10, ItemManager.glassForGUI);
        mainGUI.setItem(11, ItemManager.glassForGUI);
        mainGUI.setItem(12, ItemManager.glassForGUI);
        if (player.hasPermission("foodm.pvp.foodgame")) {
            mainGUI.setItem(13, ItemManager.foodGame); // food game
        } else {
            mainGUI.setItem(13, ItemManager.glassForGUI);
        }
        mainGUI.setItem(14, ItemManager.glassForGUI);
        mainGUI.setItem(15, ItemManager.glassForGUI);
        mainGUI.setItem(16, ItemManager.glassForGUI);
        mainGUI.setItem(17, ItemManager.glassForGUI);
        return mainGUI;
    }

    public Inventory createTeamGUI() {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.GREEN + "" + ChatColor.ITALIC + " Amount of teams");
        gui.setItem(0, ItemManager.glassForGUI);
        gui.setItem(1, ItemManager.glassForGUI);
        gui.setItem(2, ItemManager.glassForGUI);
        gui.setItem(3, ItemManager.glassForGUI);
        gui.setItem(4, ItemManager.glassForGUI);
        gui.setItem(5, ItemManager.glassForGUI);
        gui.setItem(6, ItemManager.glassForGUI);
        gui.setItem(7, ItemManager.glassForGUI);
        gui.setItem(8, ItemManager.close);
        // 2 layer
        gui.setItem(9, ItemManager.glassForGUI);
        gui.setItem(10, ItemManager.glassForGUI);

        gui.setItem(11, ItemManager.team2);
        gui.setItem(12, ItemManager.team3);
        gui.setItem(13, ItemManager.team4);
        gui.setItem(14, ItemManager.team5);

        gui.setItem(15, ItemManager.glassForGUI);
        gui.setItem(16, ItemManager.glassForGUI);
        gui.setItem(17, ItemManager.glassForGUI);

        // 3 layer
        gui.setItem(18, ItemManager.back);
        gui.setItem(19, ItemManager.glassForGUI);
        gui.setItem(20, ItemManager.glassForGUI);
        gui.setItem(21, ItemManager.glassForGUI);
        gui.setItem(22, ItemManager.glassForGUI);
        gui.setItem(23, ItemManager.glassForGUI);
        gui.setItem(24, ItemManager.glassForGUI);
        gui.setItem(25, ItemManager.glassForGUI);
        gui.setItem(26, ItemManager.glassForGUI);
        return gui;
    }

    public Inventory createPvE() {
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        Inventory mainGUI = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-" + ChatColor.GOLD + "" + ChatColor.BOLD + " "+s+" " + ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-" + ChatColor.RED + "" + ChatColor.ITALIC + " PvE");
        mainGUI.setItem(0, ItemManager.glassForGUI);
        mainGUI.setItem(1, ItemManager.glassForGUI);
        mainGUI.setItem(2, ItemManager.glassForGUI);
        mainGUI.setItem(3, ItemManager.glassForGUI);
        mainGUI.setItem(4, ItemManager.glassForGUI);
        mainGUI.setItem(5, ItemManager.glassForGUI);
        mainGUI.setItem(6, ItemManager.glassForGUI);
        mainGUI.setItem(7, ItemManager.glassForGUI);
        mainGUI.setItem(8, ItemManager.close);
        // second layer
        mainGUI.setItem(9, ItemManager.glassForGUI);
        mainGUI.setItem(10, ItemManager.glassForGUI);
        mainGUI.setItem(11, ItemManager.PvEZombie);
        mainGUI.setItem(12, ItemManager.PvEEnderman);
        mainGUI.setItem(13, ItemManager.PvESkeleton);
        mainGUI.setItem(14, ItemManager.PvESlime);
        mainGUI.setItem(15, ItemManager.PvESpider);
        mainGUI.setItem(16, ItemManager.glassForGUI);
        mainGUI.setItem(17, ItemManager.glassForGUI);
        // 3 layer
        mainGUI.setItem(18, ItemManager.back);
        mainGUI.setItem(19, ItemManager.glassForGUI);
        mainGUI.setItem(20, ItemManager.glassForGUI);
        mainGUI.setItem(21, ItemManager.glassForGUI);
        mainGUI.setItem(22, ItemManager.glassForGUI);
        mainGUI.setItem(23, ItemManager.glassForGUI);
        mainGUI.setItem(24, ItemManager.glassForGUI);
        mainGUI.setItem(25, ItemManager.glassForGUI);
        mainGUI.setItem(26, ItemManager.glassForGUI);
        return mainGUI;
    }
}
