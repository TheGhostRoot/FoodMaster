package me.thegoldenmine.foodmaster.Items;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static ItemStack glassForGUI;
    public static ItemStack close;
    public static ItemStack kitChooser;
    public static ItemStack teams;
    public static ItemStack randomChoice;
    public static ItemStack groupLeave;
    public static ItemStack playAgain;

    // Main GUI
    public static ItemStack pveGUI;
    public static ItemStack pvpGUI;
    public static ItemStack back;
    public static ItemStack helpGUI;
    public static ItemStack rejoinGUI;

    //Ready system
    public static ItemStack isReady;
    public static ItemStack notReady;

    //team items for PlayerUseGUI
    public static ItemStack blue;
    public static ItemStack red;
    public static ItemStack green;
    public static ItemStack yellow;
    public static ItemStack aqua;

    // items for kits in PlayerUseGUI
    public static ItemStack fish;
    public static ItemStack cookie;
    public static ItemStack melon;
    public static ItemStack bread;
    public static ItemStack potato;
    public static ItemStack beef;

    // Items with power
    public static ItemStack FishKit;
    public static ItemStack CookieKit;
    public static ItemStack MelonKit;
    public static ItemStack BreadKit;
    public static ItemStack PotatoKit;
    public static ItemStack BeefKit;

    // Armor
    // Blue
    public static ItemStack BlueHat;
    public static ItemStack BlueChest;
    public static ItemStack BlueLeg;
    public static ItemStack BlueBoots;

    // Red
    public static ItemStack RedHat;
    public static ItemStack RedChest;
    public static ItemStack RedLeg;
    public static ItemStack RedBoots;

    // Yellow
    public static ItemStack YellowHat;
    public static ItemStack YellowChest;
    public static ItemStack YellowLeg;
    public static ItemStack YellowBoots;

    // Cyan
    public static ItemStack CyanHat;
    public static ItemStack CyanChest;
    public static ItemStack CyanLeg;
    public static ItemStack CyanBoots;

    // Green
    public static ItemStack GreenHat;
    public static ItemStack GreenChest;
    public static ItemStack GreenLeg;
    public static ItemStack GreenBoots;

    // pvp gui
    public static ItemStack freeforall;
    public static ItemStack teamDeathmatch;
    public static ItemStack foodGame;

    // teams
    public static ItemStack team2;
    public static ItemStack team3;
    public static ItemStack team4;
    public static ItemStack team5;

    // PvE
    public static ItemStack Helmet;
    public static ItemStack Chestplate;
    public static ItemStack Leggins;
    public static ItemStack Boots;

    public static ItemStack PvEZombie;
    public static ItemStack PvESkeleton;
    public static ItemStack PvESpider;
    public static ItemStack PvESlime;
    public static ItemStack PvEEnderman;

    public static void init() {
        setPlayAgain();
        glassGUI();
        kitsChoose();
        bannerClose();
        teamChoose();
        // Ready system
        setIsReady();
        setNotReady();
        // team items for PlayerUseGUI
        blueTeam();
        redTeam();
        aquaTeam();
        yellowTeam();
        greenTeam();
        // food items for kit PlayerUseGUI
        fish = Foods.fishGUI();
        cookie = Foods.cookieGUI();
        bread = Foods.breadGUI();
        melon = Foods.melonGUI();
        beef = Foods.beefGUI();
        potato = Foods.potatoGUI();
        // others
        groupLeaveItem();
        setRandomChoice();
        // kit Items
        PotatoKit = Foods.setPotatoKit();
        CookieKit = Foods.setCookieKit();
        MelonKit = Foods.setMelonKit();
        FishKit = Foods.setFishKit();
        BeefKit = Foods.setBeefKit();
        BreadKit = Foods.setBreadKit();

        // blue
        BlueHat = TeamArmor.setBlueHat();
        BlueChest = TeamArmor.setBlueChestplate();
        BlueLeg = TeamArmor.setBlueLeggings();
        BlueBoots = TeamArmor.setBlueBoots();

        // red
        RedHat = TeamArmor.setRedHat();
        RedChest = TeamArmor.setRedChest();
        RedLeg = TeamArmor.setRedLegs();
        RedBoots = TeamArmor.setRedBoots();

        // yellow
        YellowHat = TeamArmor.setYellowHat();
        YellowChest = TeamArmor.setYellowChest();
        YellowLeg = TeamArmor.setYellowLegs();
        YellowBoots = TeamArmor.setYellowBoots();

        // cyan
        CyanHat = TeamArmor.setCyanHat();
        CyanChest = TeamArmor.setCyanChest();
        CyanLeg = TeamArmor.setCyanLegs();
        CyanBoots = TeamArmor.setCyanBoots();

        // green
        GreenHat = TeamArmor.setGreenHat();
        GreenChest = TeamArmor.setGreenChest();
        GreenLeg = TeamArmor.setGreenLegs();
        GreenBoots = TeamArmor.setGreenBoots();

        // main gui
        setRejoinGUI();
        setHelpGUI();
        setBack();
        setPvPGUI();
        setPvEGUI();

        // games
        setFreeForAll();
        setFoodGame();
        setTeamDeathmatch();

        // Teams
        setTeam2();
        setTeam3();
        setTeam4();
        setTeam5();

        // PvE
        Helmet = ArmorPvE.setHelmet();
        Chestplate = ArmorPvE.setChestplate();
        Leggins = ArmorPvE.setLeggins();
        Boots = ArmorPvE.setBoots();

        setPvEZombie();
        setPvESkeleton();
        setPvESpider();
        setPvEEnderman();
        setPvESlime();
    }

    // PvE
    private static void setPvESlime() {
        ItemStack item = new ItemStack(Material.SLIME_BALL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Slime");
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        PvESlime = item;
    }
    private static void setPvEEnderman() {
        ItemStack item = new ItemStack(Material.ENDER_PEARL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Enderman");
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        PvEEnderman = item;
    }
    private static void setPvEZombie() {
        ItemStack item = new ItemStack(Material.ROTTEN_FLESH, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Zombie");
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        PvEZombie = item;
    }
    private static void setPvESkeleton() {
        ItemStack item = new ItemStack(Material.BONE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Skeleton");
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        PvESkeleton = item;
    }
    private static void setPvESpider() {
        ItemStack item = new ItemStack(Material.SPIDER_EYE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Spider");
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        PvESpider = item;
    }

    // Teams
    private static void setTeam5() {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD+""+ChatColor.ITALIC+"5 Teams");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Team Deathmatch§7§o with 5 teams.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        team5 = item;
    }

    private static void setTeam4() {
        ItemStack item = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD+""+ChatColor.ITALIC+"4 Teams");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Team Deathmatch§7§o with 4 teams.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        team4 = item;
    }

    private static void setTeam3() {
        ItemStack item = new ItemStack(Material.STONE_AXE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD+""+ChatColor.ITALIC+"3 Teams");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Team Deathmatch§7§o with 3 teams.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        team3 = item;
    }

    private static void setTeam2() {
        ItemStack item = new ItemStack(Material.WOODEN_AXE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD+""+ChatColor.ITALIC+"2 Teams");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Team Deathmatch§7§o with 2 teams.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        team2 = item;
    }

    // pvp GUI
    private static void setFoodGame() {
        ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Food Game" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Food Game");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        foodGame = item;
    }

    private static void setTeamDeathmatch() {
        ItemStack item = new ItemStack(Material.STONE_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Team DeathMatch" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Team Deathmatch");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        teamDeathmatch = item;
    }

    private static void setFreeForAll() {
        ItemStack item = new ItemStack(Material.GOLDEN_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Free For All" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Free For All");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        freeforall = item;
    }

    // main GUI
    private static void setRejoinGUI() {
        ItemStack item = new ItemStack(Material.SNOWBALL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Rejoin" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to §6Rejoin");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        rejoinGUI = item;
    }
    private static void setHelpGUI() {
        ItemStack item = new ItemStack(Material.BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Help Menu" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to open §6Help Menu");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        helpGUI = item;
    }
    private static void setBack() {
        ItemStack item = new ItemStack(Material.ARROW, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GREEN + "" + ChatColor.BOLD + "Back" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§oClick this to go to the previous menu");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        back = item;
    }
    private static void setPvPGUI() {
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.RED + "" + ChatColor.BOLD + "PvP" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §cPvP");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        pvpGUI = item;
    }
    private static void setPvEGUI() {
        ItemStack item = new ItemStack(Material.WITHER_SKELETON_SKULL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.RED + "" + ChatColor.BOLD + "PvE" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §cPvE");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        pveGUI = item;
    }

    // Play Again
    private static void setPlayAgain() {
        ItemStack item = new ItemStack(Material.CREEPER_HEAD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        //  bold I - blue, magic Play Again - aqua, ital
        meta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.MAGIC + "I" + ChatColor.GREEN + "" + ChatColor.ITALIC + "Play Again" + ChatColor.DARK_GREEN + "" + ChatColor.MAGIC + "I");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§b§oAll group members must hold");
        lore.add("§b§othis item in order to start a game.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        playAgain = item;
    }

    private static void glassGUI() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GRAY + "");
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        glassForGUI = item;
    }

    private static void setIsReady() {
        ItemStack item = new ItemStack(Material.LIME_DYE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Ready");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        isReady = item;
    }

    private static void setNotReady() {
        ItemStack item = new ItemStack(Material.GRAY_DYE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Unready");
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        notReady = item;
    }

    private static void setRandomChoice() {
        ItemStack item = new ItemStack(Material.DIAMOND, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Random");
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lClick§7§o to pick random");
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        randomChoice = item;
    }

    private static void groupLeaveItem() {
        ItemStack item = new ItemStack(Material.BEETROOT_SOUP, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Group Leave");
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lRight Click§7§o to leave the group");
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        groupLeave = item;
    }

    private static void bannerClose() {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Close");
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        close = item;
    }

    private static void kitsChoose() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Kits");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lRight Click§7§o to open the kit GUI");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        kitChooser = item;
    }

    private static void teamChoose() {
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Teams");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lRight Click§7§o to open the teams GUI");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        teams = item;
    }

    // Team items for PlayerUseGUI
    private static void blueTeam() {
        ItemStack item = new ItemStack(Material.BLUE_WOOL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Blue team");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lJoin§7§o the §9§lBlue§7§o team");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        blue = item;
    }

    private static void redTeam() {
        ItemStack item = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Red team");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lJoin§7§o the §c§lRed§7§o team");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        red = item;
    }

    private static void aquaTeam() {
        ItemStack item = new ItemStack(Material.CYAN_WOOL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Cyan team");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lJoin§7§o the §b§lCyan§7§o team");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        aqua = item;
    }

    private static void greenTeam() {
        ItemStack item = new ItemStack(Material.GREEN_WOOL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Green team");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lJoin§7§o the §a§lGreen§7§o team");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        green = item;
    }

    private static void yellowTeam() {
        ItemStack item = new ItemStack(Material.YELLOW_WOOL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Yellow team");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lJoin§7§o the §e§lYellow§7§o team");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        yellow = item;
    }
}
