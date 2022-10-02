package me.thegoldenmine.foodmaster;

import me.thegoldenmine.foodmaster.Commands.GroupCmd.*;
import me.thegoldenmine.foodmaster.Commands.MainCommand;
import me.thegoldenmine.foodmaster.Commands.MainTabComplete;
import me.thegoldenmine.foodmaster.Commands.SubCmd.*;
import me.thegoldenmine.foodmaster.Commands.SubCmd.EndHelpers.*;
import me.thegoldenmine.foodmaster.Commands.SubCmd.EndHelpers.Teams.*;
import me.thegoldenmine.foodmaster.Commands.SubCmd.Start.*;
import me.thegoldenmine.foodmaster.CoolDown.*;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import me.thegoldenmine.foodmaster.Listeners.AntiGlitchListeners.*;
import me.thegoldenmine.foodmaster.Listeners.*;
import me.thegoldenmine.foodmaster.Listeners.KitPowerListeners.*;
import me.thegoldenmine.foodmaster.Others.*;
import org.bukkit.*;
import org.bukkit.entity.Cow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.*;

public class FoodMaster extends JavaPlugin {
    public ConfigFiles mainConfig;
    public CreateGUI createGUI;
    public OpenGUI openGUI;
    public PlayerUseGUI playerUseGUI;
    public GroupLeave groupLeave;
    public OnLeave onLeave;
    public EndermanTpListener endermanTpListener;
    public AntiGlitchSys antiGlitchSys;
    public ItemPickupAndDropListener itemPickupAndDropListener;
    public GreenTeam greenTeam;
    public ProjectileHitListener projectileHitListener;
    public StartFreeForAll startFreeForAll;
    public UseTeamGUI useTeamGUI;
    public BossPower bossPower;
    public List<ItemStack> items = new ArrayList<>();
    public ItemManager itemManager;
    public StartPvEZombie startPvEZombie;
    public StartPvESlime startPvESlime;
    public StartPvESkeleton startPvESkeleton;
    public StartPvESpider startPvESpider;
    public FallDamage fallDamage;
    public TeamWithTheMostKills teamWithTheMostKills;
    public PlayerWithTheMostKills playerWithTheMostKills;
    public BlockAndExplosionListener blockAndExplosionListener;
    public DamageListener damageListener;
    public Set<UUID> kickedPlayers = Collections.synchronizedSet(new HashSet<>());
    public Rejoin rejoin;
    public StartFoodGame startFoodGame;
    public RedTeam redTeam;
    public BlueTeam blueTeam;
    public YellowTeam yellowTeam;
    public CyanTeam cyanTeam;
    public StartPvEEnderman startPvEEnderman;
    public ClearPlayer clearPlayer;
    public EndPvP endPvP;
    public PlayAgainListener playAgainListener;
    public HashMap<UUID, Integer> lives = new HashMap<>();
    public TimerTik timerTik;
    // win/lose
    public Set<UUID> winners = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> losses = Collections.synchronizedSet(new HashSet<>());

    //    Name of the game   /   time in integer
    public HashMap<String, Integer> timer = new HashMap<>();

    // Name of the game  /  alive players in that game
    public HashMap<String, Set<UUID>> stillAlive = new HashMap<>();

    // UUID / kills
    public HashMap<UUID, Integer> inGameKills = new HashMap<>();

    // UUID / deaths
    public HashMap<UUID, Integer> inGameDeaths = new HashMap<>();

    // kit listeners
    public BreadPowerListener breadPowerListener;
    public CookiePowerListener cookiePowerListener;
    public FishPowerListener fishPowerListener;
    public MelonPowerListener melonPowerListener;
    public PotatoPowerListener potatoPowerListener;
    public BeefPowerListener beefPowerListener;

    // teams
    public Set<UUID> playersThatChoice2Teams = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersThatChoice3Teams = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersThatChoice4Teams = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersThatChoice5Teams = Collections.synchronizedSet(new HashSet<>());

    public Set<UUID> players2Teams = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> players3Teams = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> players4Teams = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> players5Teams = Collections.synchronizedSet(new HashSet<>());

    public Set<UUID> playersInGreenTeams = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersInCyanTeams = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersInRedTeams = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersInBlueTeams = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersInYellowTeams = Collections.synchronizedSet(new HashSet<>());

    public Set<UUID> playersRandomKit = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersRandomTeam = Collections.synchronizedSet(new HashSet<>());

    // kits
    public Set<UUID> playersInFishKit = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersInCookieKit = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersInMelonKit = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersInBreadKit = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersInPotatoKit = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersInBeefKit = Collections.synchronizedSet(new HashSet<>());

    // CoolDowns
    public CommandCoolDown commandCoolDown;
    public GroupInviteManager groupInviteManager;
    public GameSpawnCoolDown gameSpawnCoolDown;
    public KitsCoolDown kitsCoolDown;
    public KitPowerCoolDown kitPowerCoolDown;

    // Ready system
    public ReadyListener readyListener;
    public Set<UUID> ReadyPlayers = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> ReadySystem = Collections.synchronizedSet(new HashSet<>());

    // Sub commands
    public EndTheGame endTheGame;
    public HelpMenu helpMenu;
    public SetSubCommand setSubCommand;
    public StartCommand startCommand;
    public KickPlayerFromGame kickPlayerFromGame;
    public ResetPlayer resetPlayer;

    // GROUP SUB COMMANDS
    public GroupMain groupMain;
    public GroupInvite groupInvite;
    public GroupAccept groupAccept;
    public GroupList groupList;
    public GroupChat groupChat;
    public GroupKick groupKick;

    // The name of the waiting lobby / All the players in the waiting lobby
    public HashMap<String, Set<UUID>> playersInWaitingLobby = new HashMap<>();

    // players uuid | location of waiting lobby
    public HashMap<Set<UUID>, Location> locOfPlayersInWaitingLobby = new HashMap<>();

    // ALL THE GROUPS!
    public Set<Set<UUID>> allGroups = Collections.synchronizedSet(new HashSet<>());

    // INVITES
    // key - invited
    // value - all players that sends invites
    public HashMap<UUID, Set<UUID>> invites = new HashMap<>();

    // GAME
    // location | Name of the location
    //public HashMap<Location, String> gameLocations = new HashMap<>();
    // Name of the game | players in the game
    public HashMap<String, Set<UUID>> playersInGame = new HashMap<>();
    // Name of the game | Names of the location
    public HashMap<String, Set<String>> GameSpawnPoints = new HashMap<>();
    // player in the location | Name of the location - null if the player can be teleported.
    public HashMap<UUID, String> tpPlayersInGameNameLoc = new HashMap<>();
    // Play Again
    // uuid of the player / the gamemode
    public HashMap<UUID, String> playAgain = new HashMap<>();

    // FREE FOR ALL
    public Set<UUID> playersChoiceFreeForAll = Collections.synchronizedSet(new HashSet<>()); // the players that want to play Free For All
    public Set<UUID> playersInFreeForAll = Collections.synchronizedSet(new HashSet<>()); // the players that are IN GAME free for all

    // MINIGAME
    public StartTheGame startTheGame;

    // Food Game
    public Set<UUID> playersChoiceFoodGame = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> playersInFoodGame = Collections.synchronizedSet(new HashSet<>());
    public Set<UUID> FoodGameWinner = Collections.synchronizedSet(new HashSet<>());

    // PvE
    public SpawnBoss spawnBoss;
    public Set<UUID> playersChoicePvEZombie = new HashSet<>();
    public Set<UUID> playersChoicePvESkeleton = new HashSet<>();
    public Set<UUID> playersChoicePvESpider = new HashSet<>();
    public Set<UUID> playersChoicePvEEnderman = new HashSet<>();
    public Set<UUID> playersChoicePvESlime = new HashSet<>();

    public Set<UUID> playersPlayingPvEZombie = new HashSet<>();
    public Set<UUID> playersPlayingPvESkeleton = new HashSet<>();
    public Set<UUID> playersPlayingPvESpider = new HashSet<>();
    public Set<UUID> playersPlayingPvEEnderman = new HashSet<>();
    public Set<UUID> playersPlayingPvESlime = new HashSet<>();

    // cows
    public CowPower cowPower;
    public List<Cow> allCows = new ArrayList<>();

    // riding

    public RidingListener ridingListener;

    // pve skeleton

    public SkeletonShootListener skeletonShootListener;

    // others
    public PlayerDead playerDead;
    public GiveOneWinToPlayer giveOneWinToPlayer;
    public GiveOneLoseToPlayer giveOneLoseToPlayer;
    public GiveOneKillAndDeathToPlayer giveOneKillAndDeathToPlayer;
    public PlayerPvE playerPvE;
    public Deathmatch deathmatch;
    public Time time;
    public RespawnThePlayer respawnPlayer;
    public PlayerGroup playerGroup;
    public WaitingLobby waitingLobby;
    public Game game;

    // Food Game
    // Cookie, bread, melon, potato, fish, beef
    //  1        2      3      4      5     6

    // return from 0 to max - 1

    // ERROR - When something can't be done
    // WARN - something is wrong, but it is done
    // INFO - some extra information that will be helpful

    private FoodMaster foodMaster;

    //todo BUG FIX:

    //todo UPDATE:
    //TODO: Add Open/close Group: Others can join your group.
    //TODO: Add potions on the map and when the player gets the potion. It will give the player effects
    //TODO: Make cooldowns custom
    //TODO: Add custom food names
    //TODO: Add cooldown option for respawn

    //todo TASK:

    //todo Changes:
    // Refactored


    @Override
    public void onEnable() {
        // Try to create the config
        // 1.14.2-R0.1-SNAPSHOT
        // 1.14-R0.1-SNAPSHOT
        String version = "N/A";

        try {

            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            getLogger().severe("Failed to setup FoodMaster!");
            getLogger().severe("Your server version is not compatible with this plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (!version.equals("N/A")) {
            getLogger().info("Your server is running version " + version);
            /*
            boolean versionOK = "v1_18_R1".equals(version) || ..
            if (versionOK) {
                foodMaster = this;
            }

             */
            switch (version) {
                case "v1_18_R1":
                case "v1_18_R2":
                case "v1_19_R1":
                case "v1_16_R3":
                case "v1_16_R2":
                case "v1_16_R1":
                case "v1_15_R1":
                case "v1_14_R1":
                case "v1_17_R1":
                    foodMaster = this;
                    break;
            }
        }
        // This will return true if the server version was compatible with one of our NMS classes
        // because if it is, our actionbar would not be null
        if (foodMaster != null) {
            try {
                System.out.println("<---{ Loading Config Files }--->");
                System.out.println(" ");
                System.out.println("  <-> Registering Configs <->");
                System.out.println(" ");
                mainConfig = new ConfigFiles(this);
            } catch (IOException e) {
                System.out.println("<---{ Couldn't load config files }--->");
                System.out.println(" ");
                throw new RuntimeException("- FoodMaster ERROR - Cannot prepare config files", e);
            }
            // playersInWaitingLobby key - String name of the lobby
            // name of the lobby + "->wait-location" , location
            // num + "-one_of_the_names"
            System.out.println("<---{ Reading Config Data }--->");
            System.out.println(" ");
            int num = mainConfig.getIntWaitLobby("number_of_wait-lobbies");
            for (int i = 1; i <= num; i++) {
                String nameOfWaitlobby = mainConfig.getStrWaitLobby(i + "-one_of_the_names");
                Set<UUID> theSet = new HashSet<>();
                if (nameOfWaitlobby != null) {
                    playersInWaitingLobby.put(nameOfWaitlobby, theSet);
                }
            }
            // locOfPlayersInWaitingLobby - value - Locations of the waiting lobby
            for (String name : playersInWaitingLobby.keySet()) {
                if (name != null) {
                    Location loc = mainConfig.getLocationWaitLobby(name + "->wait-location");
                    Set<UUID> theSet = new HashSet<>();
                    locOfPlayersInWaitingLobby.put(theSet, loc);
                }
            }
            System.out.println("  <-> Read Waiting Lobby Data <->");
            System.out.println(" ");
            int n = mainConfig.getIntGame("number_of_game_names"); // 0 - at the start
            int t = mainConfig.getIntGame("number_of_game_location_names"); // 0 - at the start
            String Game = null;
            for (int i = 1; i <= n; i++) {
                String nameOfTheGame = mainConfig.getStrGame(i + "-one_of_the_game_names");
                Game = nameOfTheGame;
                if (nameOfTheGame != null) {
                    for (int b = 1; b <= t; b++) {
                        String nameOfLoc = mainConfig.getStrGame(b + "-one_of_the_game_location_names-" + nameOfTheGame);
                        if (nameOfLoc != null) {
                            if (GameSpawnPoints.get(nameOfTheGame) == null) {
                                Set<String> names = new HashSet<>();
                                names.add(nameOfLoc);
                                GameSpawnPoints.put(nameOfTheGame, names);
                                int time = mainConfig.getIntGame("game_time_seconds");
                                timer.put(nameOfTheGame, time);
                            } else {
                                int time = mainConfig.getIntGame("game_time_seconds");
                                timer.put(nameOfTheGame, time);
                                GameSpawnPoints.get(nameOfTheGame).add(nameOfLoc);
                            }
                        } else if (!nameOfTheGame.equals(Game)) {
                            Game = mainConfig.getStrGame(i + "-one_of_the_game_names");
                            break;
                        }
                    }
                }
            }
            System.out.println("  <-> Read Game Data <->");
            System.out.println(" ");

            // playersInGame - key - String name of the game
            for (String name : GameSpawnPoints.keySet()) {
                if (name != null) {
                    Set<UUID> players = new HashSet<>();
                    playersInGame.put(name, players);
                }
            }
            System.out.println("  <-> Read Spawn Points Data <->");
            System.out.println(" ");

            ItemManager.init();
            itemManager = new ItemManager();
            items.add(ItemManager.groupLeave);
            items.add(ItemManager.pvpGUI);
            items.add(ItemManager.pveGUI);
            items.add(ItemManager.back);
            items.add(ItemManager.helpGUI);
            items.add(ItemManager.rejoinGUI);
            items.add(ItemManager.freeforall);
            items.add(ItemManager.foodGame);
            items.add(ItemManager.teamDeathmatch);
            items.add(ItemManager.team2);
            items.add(ItemManager.team3);
            items.add(ItemManager.team4);
            items.add(ItemManager.team5);
            items.add(ItemManager.close);
            items.add(ItemManager.randomChoice);
            items.add(ItemManager.blue);
            items.add(ItemManager.Helmet);
            items.add(ItemManager.Chestplate);
            items.add(ItemManager.Leggins);
            items.add(ItemManager.Boots);
            items.add(ItemManager.PvESpider);
            items.add(ItemManager.PvESkeleton);
            items.add(ItemManager.PvEZombie);
            items.add(ItemManager.PvESlime);
            items.add(ItemManager.PvEEnderman);
            items.add(ItemManager.isReady);
            items.add(ItemManager.cookie);
            items.add(ItemManager.glassForGUI);
            items.add(ItemManager.kitChooser);
            items.add(ItemManager.teams);
            items.add(ItemManager.notReady);
            items.add(ItemManager.red);
            items.add(ItemManager.green);
            items.add(ItemManager.aqua);
            items.add(ItemManager.bread);
            items.add(ItemManager.fish);
            items.add(ItemManager.melon);
            items.add(ItemManager.potato);
            items.add(ItemManager.yellow);
            items.add(ItemManager.BreadKit);
            items.add(ItemManager.CookieKit);
            items.add(ItemManager.FishKit);
            items.add(ItemManager.PotatoKit);
            items.add(ItemManager.MelonKit);
            items.add(ItemManager.playAgain);
            // blue
            items.add(ItemManager.BlueHat);
            items.add(ItemManager.BlueChest);
            items.add(ItemManager.BlueLeg);
            items.add(ItemManager.BlueBoots);
            // red
            items.add(ItemManager.RedHat);
            items.add(ItemManager.RedChest);
            items.add(ItemManager.RedLeg);
            items.add(ItemManager.RedBoots);
            // yellow
            items.add(ItemManager.YellowHat);
            items.add(ItemManager.YellowChest);
            items.add(ItemManager.YellowLeg);
            items.add(ItemManager.YellowBoots);
            // cyan
            items.add(ItemManager.CyanHat);
            items.add(ItemManager.CyanChest);
            items.add(ItemManager.CyanLeg);
            items.add(ItemManager.CyanBoots);
            // green
            items.add(ItemManager.GreenHat);
            items.add(ItemManager.GreenChest);
            items.add(ItemManager.GreenLeg);
            items.add(ItemManager.GreenBoots);
            items.add(ItemManager.beef);
            items.add(ItemManager.BeefKit);
            System.out.println("  <-> Read Items Data <->");
            System.out.println(" ");

            System.out.println("<---{ Registering Plugin }--->");
            System.out.println(" ");

            // others
            playerDead = new PlayerDead(this);
            giveOneWinToPlayer = new GiveOneWinToPlayer(this);
            giveOneLoseToPlayer = new GiveOneLoseToPlayer(this);
            giveOneKillAndDeathToPlayer = new GiveOneKillAndDeathToPlayer(this);
            playerPvE = new PlayerPvE(this);
            deathmatch = new Deathmatch(this);
            time = new Time(this);
            respawnPlayer = new RespawnThePlayer(this);
            playerGroup = new PlayerGroup(this);
            waitingLobby = new WaitingLobby(this);
            game = new Game(this);

            // idk
            createGUI = new CreateGUI(this);
            rejoin = new Rejoin(this);
            openGUI = new OpenGUI(this);
            playerUseGUI = new PlayerUseGUI(this);
            groupLeave = new GroupLeave(this);
            onLeave = new OnLeave(this);
            itemPickupAndDropListener = new ItemPickupAndDropListener(this);
            blockAndExplosionListener = new BlockAndExplosionListener(this);
            damageListener = new DamageListener(this);
            startFreeForAll = new StartFreeForAll(this);
            playAgainListener = new PlayAgainListener(this);
            timerTik = new TimerTik(this);
            startFoodGame = new StartFoodGame(this);
            startPvEEnderman = new StartPvEEnderman(this);
            startPvESpider = new StartPvESpider(this);
            startPvESlime = new StartPvESlime(this);
            greenTeam = new GreenTeam(this);
            cyanTeam = new CyanTeam(this);
            blueTeam = new BlueTeam(this);
            yellowTeam = new YellowTeam(this);
            redTeam = new RedTeam(this);
            startPvEZombie = new StartPvEZombie(this);
            clearPlayer = new ClearPlayer(this);
            endPvP = new EndPvP(this);
            startPvESkeleton = new StartPvESkeleton(this);
            useTeamGUI = new UseTeamGUI(this);
            readyListener = new ReadyListener(this);
            projectileHitListener = new ProjectileHitListener(this);
            ridingListener = new RidingListener(this);
            teamWithTheMostKills = new TeamWithTheMostKills(this);
            playerWithTheMostKills = new PlayerWithTheMostKills(this);
            spawnBoss = new SpawnBoss(this);
            bossPower = new BossPower(this);
            beefPowerListener = new BeefPowerListener(this);
            skeletonShootListener = new SkeletonShootListener(this);
            endermanTpListener = new EndermanTpListener(this);
            cowPower = new CowPower(this);

            // kits power
            breadPowerListener = new BreadPowerListener(this);
            cookiePowerListener = new CookiePowerListener(this);
            fishPowerListener = new FishPowerListener(this);
            melonPowerListener = new MelonPowerListener(this);
            potatoPowerListener = new PotatoPowerListener(this);

            Objects.requireNonNull(getCommand("foodmaster")).setExecutor(new MainCommand(this));
            Objects.requireNonNull(getCommand("foodmaster")).setTabCompleter(new MainTabComplete(this));
            System.out.println("  <-> Commands Registered <->");
            getServer().getPluginManager().registerEvents(openGUI, this);
            getServer().getPluginManager().registerEvents(groupLeave, this);
            getServer().getPluginManager().registerEvents(playerUseGUI, this);
            getServer().getPluginManager().registerEvents(onLeave, this);
            getServer().getPluginManager().registerEvents(readyListener, this);
            getServer().getPluginManager().registerEvents(ridingListener, this);
            getServer().getPluginManager().registerEvents(playAgainListener, this);
            getServer().getPluginManager().registerEvents(endermanTpListener, this);
            getServer().getPluginManager().registerEvents(skeletonShootListener, this);
            getServer().getPluginManager().registerEvents(beefPowerListener, this);
            getServer().getPluginManager().registerEvents(fallDamage, this);
            // part of the anti-glitch system
            getServer().getPluginManager().registerEvents(blockAndExplosionListener, this);
            getServer().getPluginManager().registerEvents(itemPickupAndDropListener, this);
            getServer().getPluginManager().registerEvents(damageListener, this);
            getServer().getPluginManager().registerEvents(itemPickupAndDropListener, this);
            getServer().getPluginManager().registerEvents(projectileHitListener, this);

            // register kits power
            getServer().getPluginManager().registerEvents(breadPowerListener, this);
            getServer().getPluginManager().registerEvents(cookiePowerListener, this);
            getServer().getPluginManager().registerEvents(fishPowerListener, this);
            getServer().getPluginManager().registerEvents(melonPowerListener, this);
            getServer().getPluginManager().registerEvents(potatoPowerListener, this);
            System.out.println(" ");
            System.out.println("  <-> Events Registered <->");
            System.out.println(" ");

            helpMenu = new HelpMenu(this);

            // CoolDowns
            commandCoolDown = new CommandCoolDown(this);
            groupInviteManager = new GroupInviteManager(this);
            antiGlitchSys = new AntiGlitchSys(this);
            gameSpawnCoolDown = new GameSpawnCoolDown(this);
            kitsCoolDown = new KitsCoolDown(this);
            kitPowerCoolDown = new KitPowerCoolDown(this);
            // Groups
            groupMain = new GroupMain(this);
            groupInvite = new GroupInvite(this);
            groupAccept = new GroupAccept(this);
            groupList = new GroupList(this);
            groupChat = new GroupChat(this);
            groupKick = new GroupKick(this);

            // MINIGAME
            startTheGame = new StartTheGame(this);

            // SubCommands
            endTheGame = new EndTheGame(this);
            setSubCommand = new SetSubCommand(this);
            startCommand = new StartCommand(this);
            kickPlayerFromGame = new KickPlayerFromGame(this);
            resetPlayer = new ResetPlayer(this);
            // others
            fallDamage = new FallDamage(this);
            System.out.println("  <-> Others Registered <->");
            System.out.println(" ");

            // Print the Logo and how the plugin version - 0.1 / spigot 1.17.1 java 11+ / spigot api 1.17
            System.out.println("<---{ Logo And Plugin Info }--->");
            System.out.println(" ");
            System.out.println("\n" +
                    "-----#########---###----------###\n" +
                    "----###---------######----######\n" +
                    "---########---###--###-###--###\n" +
                    "--###--------###----###----###\n" +
                    "-###-------###------------###\n" +
                    "###-------###------------###");
            System.out.println(" ");
            System.out.println(" Plugin version: 0.6");
            System.out.println(" Spigot versions: 1.14.2 - 1.19.2");
            System.out.println(" Spigot API version: 1.14");
            System.out.println(" Dev message: This is compiled in JAVA 11.\n Every version above JAVA 11 can run this plugin.");
        } else {
            getLogger().severe("Failed to setup FoodMaster!");
            getLogger().severe("Your server version is not compatible with this plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        System.out.println("<---{ Clearing }--->");
        System.out.println(" ");
        if (!playersInWaitingLobby.values().isEmpty() && !playersInWaitingLobby.isEmpty() && !playersInWaitingLobby.keySet().isEmpty()) {
            Location endLoc = mainConfig.getLocationMain("end_location");
            Set<Player> playerList = new HashSet<>();
            for (Set<UUID> players : playersInWaitingLobby.values()) {
                if (!players.isEmpty()) {
                    for (UUID uuid : players) {
                        Player player = Bukkit.getPlayer(uuid);
                        if (player != null) {
                            playerList.add(player);
                            if (endLoc != null) {
                                player.teleport(endLoc);
                            }
                        }
                    }
                }
            }
            for (Player player : playerList) {
                waitingLobby.removePlayerFromWaitedLobby(player);
            }
        }
        if (allGroups != null) {
            for (Set<UUID> group : allGroups) {
                if (group != null && !group.isEmpty()) {
                    for (UUID uuid : group) {
                        if (uuid != null) {
                            Player player = Bukkit.getPlayer(uuid);
                            Location endLoc = mainConfig.getLocationMain("end_location");
                            if (player != null) {
                                if (playerPvE.isPlayerPlayingPvE(player) || playerPvE.isPlayerChooseToPlayPvE(player)) {
                                    if (endLoc != null) {
                                        player.teleport(endLoc);
                                    }
                                    endTheGame.endThePvE(player);
                                } else {
                                    if (endLoc != null) {
                                        player.teleport(endLoc);
                                    }
                                    endTheGame.endTheGameWithStatus(player);
                                    inGameKills.remove(uuid);
                                    inGameDeaths.remove(uuid);
                                    playersInBlueTeams.remove(uuid);
                                    playersInCyanTeams.remove(uuid);
                                    playersInRedTeams.remove(uuid);
                                    playersInGreenTeams.remove(uuid);
                                    playersInYellowTeams.remove(uuid);
                                    playersRandomTeam.remove(uuid);
                                    playersChoiceFoodGame.remove(uuid);
                                    playersInFoodGame.remove(uuid);
                                    FoodGameWinner.remove(uuid);
                                    for (UUID uuid2 : winners) {
                                        if (uuid2 != null) {
                                            Player player2 = Bukkit.getPlayer(uuid2);
                                            if (player2 != null) {
                                                giveOneWinToPlayer.givePlayerWin(player2);
                                            }
                                        }
                                    }
                                    for (UUID uuid2 : losses) {
                                        if (uuid2 != null) {
                                            Player player2 = Bukkit.getPlayer(uuid2);
                                            if (player2 != null) {
                                                giveOneLoseToPlayer.givePlayerLose(player2);
                                            }
                                        }
                                    }
                                    winners.clear();
                                    losses.clear();
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("  <-> Games Are Cleared <->");
        System.out.println(" ");
        // Plugin shutdown logic
        if (mainConfig != null) {
            mainConfig.saveAll();
            mainConfig.reloadPvE();
            mainConfig.reloadGame();
            mainConfig.reloadMain();
            mainConfig.reloadWaitLobby();
            mainConfig.reloadKill();
            mainConfig.reloadLose();
            mainConfig.reloadDeath();
            mainConfig.reloadWin();
        }
        System.out.println("  <-> Config Reloaded <->");
        System.out.println(" ");
        NamespacedKey name = new NamespacedKey(this, "boss");
        NamespacedKey namePlayer = new NamespacedKey(this, "player");
        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                PersistentDataContainer data = entity.getPersistentDataContainer();
                if (data.has(name, PersistentDataType.STRING) && data.has(namePlayer, PersistentDataType.STRING)) {
                    entity.setHealth(0);
                }
            }
        }
        System.out.println("  <-> Plugin Cleared <->");
        System.out.println(" ");
    }

    public String LocationToString(final Location loc) {
        if (loc == null) {
            return "null";
        }
        return loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
    }

    public Location StringToLocation(final String str) {
        if (str == null || str.trim().equals("")) {
            return null;
        }
        final String[] parts = str.split(":");
        if (parts.length == 6) {
            final World w = Bukkit.getServer().getWorld(parts[0]);
            final int x = Integer.parseInt(parts[1]);
            final int y = Integer.parseInt(parts[2]);
            final int z = Integer.parseInt(parts[3]);
            final float yaw = Float.parseFloat(parts[4]);
            final float pit = Float.parseFloat(parts[5]);
            return new Location(w, x, y, z, yaw, pit);
        }
        return null;
    }

    public boolean isPlayerPlayingFreeForAll(Player player) {
        return playersInFreeForAll.contains(player.getUniqueId());
    }

    public boolean isPlayerPlayingFoodGame(Player player) {
        return playersInFoodGame.contains(player.getUniqueId());
    }

    public Set<String> getWaitLobbyNames() {
        return playersInWaitingLobby.keySet();
    }
}
