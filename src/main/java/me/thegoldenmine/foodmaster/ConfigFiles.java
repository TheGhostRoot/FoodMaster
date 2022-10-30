package me.thegoldenmine.foodmaster;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigFiles {
    public final FoodMaster plugin;
    private final File GameFile;
    private final File WaitLobbyFile;
    private final File MainFile;
    private final File KillFile;
    private final File DeathFile;
    private final File WinFile;
    private final File LoseFile;
    private final File PvEFile;
    private FileConfiguration dataPvE;
    private FileConfiguration dataGame;
    private FileConfiguration dataWaitLobby;
    private FileConfiguration dataMain;
    private FileConfiguration dataKill;
    private FileConfiguration dataDeath;
    private FileConfiguration dataWin;
    private FileConfiguration dataLose;

    public ConfigFiles(FoodMaster plugin) throws IOException {
        this.plugin = plugin;

        File dataFolder = plugin.getDataFolder();
        GameFile = new File(dataFolder, "game.yml");
        WaitLobbyFile = new File(dataFolder, "lobby.yml");
        MainFile = new File(dataFolder, "main.yml");
        KillFile = new File(dataFolder, "kills.yml");
        DeathFile = new File(dataFolder, "deaths.yml");
        WinFile = new File(dataFolder, "wins.yml");
        LoseFile = new File(dataFolder, "losses.yml");
        PvEFile = new File(dataFolder, "pve.yml");
        if (!dataFolder.exists() && !MainFile.exists() && !GameFile.exists() && !WaitLobbyFile.exists() && !KillFile.exists() && !LoseFile.exists() && !WinFile.exists() && !PvEFile.exists()) {
            plugin.getLogger().info("<---{ Creating New Config Files }--->");
            plugin.getLogger().info(" ");
            if (dataFolder.mkdir()) {
                plugin.getLogger().info("  <-> Folder Created <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create The Folder <->");
            }
            plugin.getLogger().info(" ");
            if (PvEFile.createNewFile()) {
                plugin.getLogger().info("  <-> PvE File Created <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create PvE File <->");
            }
            plugin.getLogger().info(" ");
            if (MainFile.createNewFile()) {
                plugin.getLogger().info("  <-> Main File Created <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create Main File <->");
            }
            plugin.getLogger().info(" ");
            if (GameFile.createNewFile()) {
                plugin.getLogger().info("  <-> Game File Created <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create Game File <->");
            }
            plugin.getLogger().info(" ");
            if (WaitLobbyFile.createNewFile()) {
                plugin.getLogger().info("  <-> Lobby File Created <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create Lobby File <->");
            }
            plugin.getLogger().info(" ");
            if (KillFile.createNewFile()) {
                plugin.getLogger().info("  <-> Kill File Created <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create Kill File <->");
            }
            plugin.getLogger().info(" ");
            if (DeathFile.createNewFile()) {
                plugin.getLogger().info("  <-> Death File Created <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create Death File <->");
            }
            plugin.getLogger().info(" ");
            if (WinFile.createNewFile()) {
                plugin.getLogger().info("  <-> Win File Created <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create Win File <->");
            }
            plugin.getLogger().info(" ");
            if (LoseFile.createNewFile()) {
                plugin.getLogger().info("  <-> Lose File Created <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create Lose File <->");
            }
            plugin.getLogger().info(" ");
            dataMain = YamlConfiguration.loadConfiguration(MainFile);
            dataGame = YamlConfiguration.loadConfiguration(GameFile);
            dataWaitLobby = YamlConfiguration.loadConfiguration(WaitLobbyFile);

            dataKill = YamlConfiguration.loadConfiguration(KillFile);
            dataDeath = YamlConfiguration.loadConfiguration(DeathFile);
            dataWin = YamlConfiguration.loadConfiguration(WinFile);
            dataLose = YamlConfiguration.loadConfiguration(LoseFile);
            plugin.getLogger().info("  <-> Loaded Data <->");
            plugin.getLogger().info(" ");
            setDefaultValues();

            plugin.getLogger().info("  <-> Set Data <->");
            plugin.getLogger().info(" ");
        }
        if (!dataFolder.exists()) {
            if (dataFolder.mkdir()) {
                plugin.getLogger().info("  <-> Created A Folder <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create The Folder <->");
            }
            plugin.getLogger().info(" ");
        }
        if (!KillFile.exists()) {
            if (KillFile.createNewFile()) {
                plugin.getLogger().info("  <-> Created kills.yml <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create Kill File <->");
            }
            plugin.getLogger().info(" ");
            try {
                dataKill.load(KillFile);
                plugin.getLogger().info("  <-> Loaded kills.yml <->");
            } catch (Exception e) {
                plugin.getLogger().warning("  <-> Couldn't Load kills.yml <->");
            }
            plugin.getLogger().info(" ");
        }
        if (!PvEFile.exists()) {
            if (PvEFile.createNewFile()) {
                plugin.getLogger().info("  <-> Created pve.yml <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create PvE File <->");
            }
            plugin.getLogger().info(" ");
            try {
                dataPvE.load(PvEFile);
                plugin.getLogger().info("  <-> Loaded pve.yml <->");
            } catch (Exception e) {
                plugin.getLogger().warning("  <-> Couldn't Load pve.yml <->");
            }
            plugin.getLogger().info(" ");
        }
        if (!DeathFile.exists()) {
            if (DeathFile.createNewFile()) {
                plugin.getLogger().info("  <-> Created deaths.yml <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create Death File <->");
            }
            plugin.getLogger().info(" ");
            try {
                dataDeath.load(DeathFile);
                plugin.getLogger().info("  <-> Loaded deaths.yml <->");
            } catch (Exception e) {
                plugin.getLogger().warning("  <-> Couldn't Load deaths.yml <->");
            }
            plugin.getLogger().info(" ");
        }
        if (!WinFile.exists()) {
            if (WinFile.createNewFile()) {
                plugin.getLogger().info("  <-> Created wins.yml <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create Win File <->");
            }
            plugin.getLogger().info(" ");
            try {
                dataWin.load(WinFile);
                plugin.getLogger().info("  <-> Loaded wins.yml <->");
            } catch (Exception e) {
                plugin.getLogger().warning("  <-> Couldn't Load wins.yml <->");
            }
            plugin.getLogger().info(" ");
        }
        if (!LoseFile.exists()) {
            if (LoseFile.createNewFile()) {
                plugin.getLogger().info("  <-> Created losses.yml <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create Lose File <->");
            }
            plugin.getLogger().info(" ");
            try {
                dataLose.load(LoseFile);
                plugin.getLogger().info("  <-> Loaded losses.yml <->");
            } catch (Exception e) {
                plugin.getLogger().warning("  <-> Couldn't Load losses.yml <->");
            }
            plugin.getLogger().info(" ");
        }
        if (!MainFile.exists()) {
            if (MainFile.createNewFile()) {
                plugin.getLogger().info("  <-> Created main.yml <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create Main File <->");
            }
            plugin.getLogger().info(" ");
            try {
                dataMain.load(MainFile);
                plugin.getLogger().info("  <-> Loaded main.yml <->");
            } catch (Exception e) {
                plugin.getLogger().warning("  <-> Couldn't Load main.yml <->");
            }
            plugin.getLogger().info(" ");
        }
        if (!GameFile.exists()) {
            if (GameFile.createNewFile()) {
                plugin.getLogger().info("  <-> Created game.yml <->");
            } else {
                plugin.getLogger().warning("  <-> Couldn't Create Game File <->");
            }
            plugin.getLogger().info(" ");
            try {
                dataGame.load(GameFile);
                plugin.getLogger().info("  <-> Loaded game.yml <->");
            } catch (Exception e) {
                plugin.getLogger().warning("  <-> Couldn't Load game.yml <->");
            }
            plugin.getLogger().info(" ");
        }
        if (!WaitLobbyFile.exists()) {
            if (WaitLobbyFile.createNewFile()) {
                plugin.getLogger().info("  <-> Created lobby.yml <->");
            } else {
                plugin.getLogger().info("  <-> Couldn't Create Lobby File <->");
            }
            plugin.getLogger().info(" ");
            try {
                dataWaitLobby.load(WaitLobbyFile);
                plugin.getLogger().info("  <-> Loaded lobby.yml <->");
            } catch (Exception e) {
                plugin.getLogger().warning("  <-> Couldn't Load lobby.yml <->");
            }
            plugin.getLogger().info(" ");
        }
        reloadPvE();
        reloadGame();
        reloadMain();
        reloadWaitLobby();
        reloadKill();
        reloadLose();
        reloadDeath();
        reloadWin();
    }

    public void setDefaultValues() {
        reloadPvE();
        reloadGame();
        reloadMain();
        reloadWaitLobby();
        reloadKill();
        reloadLose();
        reloadDeath();
        reloadWin();
        setIntGame("game_time_seconds", 300);
        setIntMain("max-players_in_group", 20);
        setIntWaitLobby("max-players_in_waiting_lobby", 50);
        setBooleanGame("friendly-fire", false);
        setBooleanGame("respawn_free-for-all", true);
        setBooleanGame("respawn_team_deathmatch", true);
        setIntMain("number_of_wait-lobbies", 0);
        setIntGame("number_of_game_names", 0);
        setIntGame("number_of_game_location_names", 0);
        setBooleanMain("group_player_break_blocks", false);
        setBooleanMain("group_player_place_blocks", false);
        setBooleanMain("group_player_hit_teammate", true);
        setBooleanMain("group_player_hit_mobs", false);
        setBooleanGame("enable_lives_free-for-all", false);
        setBooleanGame("enable_lives_team_deathmatch", false);
        setBooleanGame("show_timer_above_inventory", false);
        setBooleanGame("show_timer_boss_bar", true);
        setIntGame("lives", 5);
        setBooleanWaitLobby("ride_players", false);
        setBooleanPvE("ride_pve_bosses", false);
        setStrGame("game_timer_color", "§b");
        setStrGame("message_on_death", "write the word 'empty' to not send custom message on respawn/death");
        setIntGame("damage", 6);
        setBooleanGame("enable_pvp_outside_the_plugin", false);
        setIntGame("friendly_damage", 2);
        setBooleanGame("hungry_during_a_game", false);
        setBooleanWaitLobby("hungry_while_waiting", false);
        setBooleanGame("enable_fall_damage", false);
        setBooleanWaitLobby("enable_fall_damage", false);
        setBooleanMain("enable_fall_damage", false);
        setBooleanMain("hungry_in_group", false);
        setBooleanMain("group_player_pickup", false);
        setStrMain("name", "FoodMaster");
        // PvE
        setBooleanPvE("respawm_player", true);
        // Zombie
        setIntPvE("Zombie_Health", 1000);
        setIntPvE("Zombie_Damage", 4);
        setIntPvE("Zombie_Minion_Damage", 6);
        setIntPvE("Zombie_Minions", 10);
        setIntPvE("Zombie_Minion_Health", 100);
        setBooleanPvE("Zombie_Jump", true);
        setBooleanPvE("Zombie_Spawn_Minions", true);
        setStrPvE("Zombie_Name", "§6§lBob");
        // Skeleton
        setStrPvE("Skeleton_Name", "§6§lJohn");
        setIntPvE("Skeleton_Health", 1000);
        setIntPvE("Skeleton_Damage", 4);
        setIntPvE("Skeleton_Thunder_Damage", 3);
        setBooleanPvE("Skeleton_Arrow_Rapid_Fire", true);
        setBooleanPvE("Skeleton_Shoot_Fireballs", true);
        setBooleanPvE("Skeleton_Spawn_Thunder", true);
        // Spider
        setBooleanPvE("Spider_Jump", true);
        setStrPvE("Spider_Name", "§d§lSpidy");
        setIntPvE("Spider_Damage", 5);
        setIntPvE("Spider_Health", 1000);
        setBooleanPvE("Spider_Spawn_Minions", true);
        setIntPvE("Spider_Minions", 5);
        setIntPvE("Spider_Minion_Health", 100);
        setIntPvE("Spider_Minion_Damage", 3);
        // Slime
        setBooleanPvE("Slime_Jump", true);
        setStrPvE("Slime_Name", "§a§lMagi");
        setIntPvE("Slime_Health", 1000);
        setIntPvE("Slime_Damage", 3);
        setBooleanPvE("Slime_Spawn_Explosion", true);
        setIntPvE("Slime_Explosion_Damage", 2);
        setBooleanPvE("Slime_Spawn_Minions", true);
        setIntPvE("Slime_Minions", 10);
        setIntPvE("Slime_Minion_Health", 100);
        setIntPvE("Slime_Minion_Damage", 3);
        // Enderman
        setBooleanPvE("Enderman_Jump", true);
        setStrPvE("Enderman_Name", "§5§lEndy");
        setIntPvE("Enderman_Damage", 5);
        setBooleanPvE("Enderman_Teleport_To_Player", true);
        setBooleanPvE("Enderman_Minion_Teleport_To_Player", true);
        setIntPvE("Enderman_Health", 1000);
        setBooleanPvE("Enderman_Spawn_Minions", true);
        setIntPvE("Enderman_Minions", 5);
        setIntPvE("Enderman_Minion_Health", 100);
        setIntPvE("Enderman_Minion_Damage", 3);
    }

    public void setIntKill(String path, int intiger) {
        dataKill.set(path, intiger);
        saveKill();
        reloadKill();
    }

    public void setIntDeath(String path, int intiger) {
        dataDeath.set(path, intiger);
        saveDeath();
        reloadDeath();
    }

    public void setIntWin(String path, int intiger) {
        dataWin.set(path, intiger);
        saveWin();
        reloadWin();
    }

    public void setIntLose(String path, int intiger) {
        dataLose.set(path, intiger);
        saveLose();
        reloadLose();
    }

    public void setStrGame(String path, String str) {
        dataGame.set(path, str);
        saveGame();
        reloadGame();
    }

    public void setStrWaitLobby(String path, String str) {
        dataWaitLobby.set(path, str);
        saveWaitLobby();
        reloadWaitLobby();
    }

    public void setStrMain(String path, String str) {
        dataMain.set(path, str);
        saveMain();
        reloadMain();
    }

    public void setBooleanMain(String path, boolean bolean) {
        dataMain.set(path, bolean);
        saveMain();
        reloadMain();
    }

    public void setBooleanGame(String path, boolean bolean) {
        dataGame.set(path, bolean);
        saveGame();
        reloadGame();
    }

    public void setBooleanWaitLobby(String path, boolean bolean) {
        dataWaitLobby.set(path, bolean);
        saveWaitLobby();
        reloadWaitLobby();
    }

    public void setIntMain(String path, int intiger) {
        dataMain.set(path, intiger);
        saveMain();
        reloadMain();
    }

    public void setIntGame(String path, int intiger) {
        dataGame.set(path, intiger);
        saveGame();
        reloadGame();
    }

    public void setIntWaitLobby(String path, int intiger) {
        dataWaitLobby.set(path, intiger);
        saveWaitLobby();
        reloadWaitLobby();
    }

    public void setLocationMain(String path, Location loc) {
        dataMain.set(path, plugin.LocationToString(loc));
        saveMain();
        reloadMain();
    }

    public void setLocationGame(String path, Location loc) {
        dataGame.set(path, plugin.LocationToString(loc));
        saveGame();
        reloadGame();
    }

    public void setLocationWaitLobby(String path, Location loc) {
        dataWaitLobby.set(path, plugin.LocationToString(loc));
        saveWaitLobby();
        reloadWaitLobby();
    }

    public String getStrMain(String path) {
        return dataMain.getString(path);
    }

    public String getStrGame(String path) {
        return dataGame.getString(path);
    }

    public String getStrWaitLobby(String path) {
        return dataWaitLobby.getString(path);
    }

    public boolean getBooleanMain(String path) {
        return dataMain.getBoolean(path);
    }

    public boolean getBooleanGame(String path) {
        return dataGame.getBoolean(path);
    }

    public boolean getBooleanWaitLobby(String path) {
        return dataWaitLobby.getBoolean(path);
    }

    public int getIntMain(String path) {
        return dataMain.getInt(path);
    }

    public int getIntGame(String path) {
        return dataGame.getInt(path);
    }

    public int getIntWaitLobby(String path) {
        return dataWaitLobby.getInt(path);
    }

    public int getIntKill(String path) {
        return dataKill.getInt(path);
    }

    public int getIntDeath(String path) {
        return dataDeath.getInt(path);
    }

    public int getIntWin(String path) {
        return dataWin.getInt(path);
    }

    public int getIntLose(String path) {
        return dataLose.getInt(path);
    }

    public Location getLocationMain(String path) {
        String strLoc = dataMain.getString(path);
        return plugin.StringToLocation(strLoc);
        //return dataMain.getLocation(path);
    }

    public Location getLocationGame(String path) {
        String strLoc = dataGame.getString(path);
        return plugin.StringToLocation(strLoc);
        //return dataGame.getLocation(path);
    }

    public Location getLocationWaitLobby(String path) {
        String strLoc = dataWaitLobby.getString(path);
        return plugin.StringToLocation(strLoc);
        //return dataWaitLobby.getLocation(path);
    }

    public Location getLocationPvE(String path) {
        String strLoc = dataPvE.getString(path);
        return plugin.StringToLocation(strLoc);
    }

    public boolean getBooleanPvE(String path) {
        return dataPvE.getBoolean(path);
    }

    public int getIntPvE(String path) {
        return dataPvE.getInt(path);
    }

    public String getStrPvE(String path) {
        return dataPvE.getString(path);
    }

    public void setLocationPvE(String path, Location loc) {
        dataPvE.set(path, plugin.LocationToString(loc));
        savePvE();
        reloadPvE();
    }

    public void setBooleanPvE(String path, boolean bolean) {
        dataPvE.set(path, bolean);
        savePvE();
        reloadPvE();
    }

    public void setIntPvE(String path, int i) {
        dataPvE.set(path, i);
        savePvE();
        reloadPvE();
    }

    public void setStrPvE(String path, String str) {
        dataPvE.set(path, str);
        savePvE();
        reloadPvE();
    }

    public synchronized void saveAll() {
        try {
            dataGame.save(GameFile);
            dataWaitLobby.save(WaitLobbyFile);
            dataMain.save(MainFile);
            dataPvE.save(PvEFile);

            dataKill.save(KillFile);
            dataDeath.save(DeathFile);
            dataWin.save(WinFile);
            dataLose.save(LoseFile);
        } catch (IOException e) {
            throw new RuntimeException("- FoodMaster ERROR - Cannot save all the files", e);
        }
    }

    public synchronized void saveGame() {
        try {
            dataGame.save(GameFile);
        } catch (IOException e) {
            throw new RuntimeException("- FoodMaster ERROR - Cannot save game.yml", e);
        }
    }

    public synchronized void saveWaitLobby() {
        try {
            dataWaitLobby.save(WaitLobbyFile);
        } catch (IOException e) {
            throw new RuntimeException("- FoodMaster ERROR - Cannot save lobby.yml", e);
        }
    }

    public synchronized void saveMain() {
        try {
            dataMain.save(MainFile);
        } catch (IOException e) {
            throw new RuntimeException("- FoodMaster ERROR - Cannot save main.yml", e);
        }
    }

    public synchronized void saveKill() {
        try {
            dataKill.save(KillFile);
        } catch (IOException e) {
            throw new RuntimeException("- FoodMaster ERROR - Cannot save kills.yml", e);
        }
    }

    public synchronized void saveDeath() {
        try {
            dataDeath.save(DeathFile);
        } catch (IOException e) {
            throw new RuntimeException("- FoodMaster ERROR - Cannot save deaths.yml", e);
        }
    }

    synchronized public void saveWin() {
        try {
            dataWin.save(WinFile);
        } catch (IOException e) {
            throw new RuntimeException("- FoodMaster ERROR - Cannot save wins.yml", e);
        }
    }

    public synchronized void saveLose() {
        try {
            dataLose.save(LoseFile);
        } catch (IOException e) {
            throw new RuntimeException("- FoodMaster ERROR - Cannot save losses.yml", e);
        }
    }

    public synchronized void savePvE() {
        try {
            dataPvE.save(PvEFile);
        } catch (IOException e) {
            throw new RuntimeException("- FoodMaster ERROR - Cannot save pve.yml", e);
        }
    }


    public synchronized void reloadMain() {
        dataMain = YamlConfiguration.loadConfiguration(MainFile);
    }

    public synchronized void reloadGame() {
        dataGame = YamlConfiguration.loadConfiguration(GameFile);
    }

    public synchronized void reloadWaitLobby() {
        dataWaitLobby = YamlConfiguration.loadConfiguration(WaitLobbyFile);
    }

    public synchronized void reloadKill() {
        dataKill = YamlConfiguration.loadConfiguration(KillFile);
    }

    public synchronized void reloadDeath() {
        dataDeath = YamlConfiguration.loadConfiguration(DeathFile);
    }

    public synchronized void reloadWin() {
        dataWin = YamlConfiguration.loadConfiguration(WinFile);
    }

    public synchronized void reloadLose() {
        dataLose = YamlConfiguration.loadConfiguration(LoseFile);
    }

    public synchronized void reloadPvE() {
        dataPvE = YamlConfiguration.loadConfiguration(PvEFile);
    }

    public String getPluginName() {
    	String pluginName = getStrMain("name");

		if (pluginName == null || pluginName.isBlank()) {
			pluginName = "FoodMaster";
		}

		return pluginName;
    }
}
