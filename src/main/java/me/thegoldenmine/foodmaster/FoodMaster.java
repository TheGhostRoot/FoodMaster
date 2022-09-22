package me.thegoldenmine.foodmaster;

import me.thegoldenmine.foodmaster.Cmds.GroupCmd.*;
import me.thegoldenmine.foodmaster.Cmds.MainCommand;
import me.thegoldenmine.foodmaster.Cmds.MainTabCompletion;
import me.thegoldenmine.foodmaster.Cmds.SubCmd.*;
import me.thegoldenmine.foodmaster.CoolDown.*;
import me.thegoldenmine.foodmaster.Listeners.AntiGlitchListeners.*;
import me.thegoldenmine.foodmaster.Listeners.*;
import me.thegoldenmine.foodmaster.Listeners.KitPowerListeners.*;
import me.thegoldenmine.foodmaster.Others.RemovePlayerFromWaitLobby;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Cow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.util.*;

public class FoodMaster extends JavaPlugin {
    public ConfigFiles mainConfig;
    public CreateGUI createGUI;
    public OpenGUI openGUI;
    public PlayerUseGUI PlayerUseGUI;
    public GroupLeave groupLeave;
    public OnLeave onLeave;
    public EndermanTpListener endermanTpListener;
    public AntiGlitchSys antiGlitchSys;
    public PlacingListener placingListener;
    public ProjectileHitListener projectileHitListener;
    public DropListener dropListener;
    public BossPower bossPower;
    public List<ItemStack> items = new ArrayList<>();
    public ItemManager itemManager;
    public FallDamage fallDamage;
    public BreakListener breakListener;
    public DamageListener damageListener;
    public Set<UUID> kickedPlayers = Collections.synchronizedSet(new HashSet<>());
    public Rejoin rejoin;
    public PlayAgainListener playAgainListener;
    public HashMap<UUID, Integer> lives = new HashMap<>();
    public PickupListener pickupListener;
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

    // OTHERS
    public RemovePlayerFromWaitLobby removePlayerFromWaitLobby;

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

    // some health: harts: 15  points: 30
    // some shield: leather armor

    // Food Game
    // Cookie, bread, melon, potato, fish
    //  1        2      3      4      5

    // kits :
    // Fish - Left Click > BIG hit with knockback of target and the player | Right Click > Splash (jump and hit) | Player > some speed, some health | MELEE -- done
    // Bread - Left Click > You shoot ammo, and they get hit and get poison | Right Click > Heals the team with half | Player > normal health, some speed, some shield | Team Support -- done
    // Potato - Left Click > trows egg and explosion | Right Click > trows everywhere bombs | Player > some speed, some health, no shield | Explosions -- done
    // Melon - Left Click > Starts firing snowballs with a little damage | Right Click > A LOT OF DAMAGE a lot of eggs everywhere | Player > some speed, some health, no shield | Minigun -- done
    // Cookie - Left Click > Lighting where the player is looking at (GIVES  bad effects to the enemy) | Right Click > Gives help effects to  | Player > some speed, normal health, some shield | Team support -- done

    // return from 0 to max - 1

    // Free of all - death match - the most kills
    // Food War - death match - the most kills

    // ERROR - When something can't be done
    // WARN - something is wrong, but it is done
    // INFO - some extra information that will be helpful

    private FoodMaster foodMaster;

    //todo BUG FIX:

    //todo UPDATE:
    //TODO: Add teams in PvE
    //TODO: Food cooldown on action bar
    //TODO: Add Open/close Group: Others can join your group.
    //TODO: Add potions on the map and when the player gets the potion. It will give the player effects
    //TODO: Make cooldowns custom
    //TODO: Add custom food names
    //TODO: Add cooldown option for respawn
    //TODO: Add permission for the food
    //TODO: Add discord sync - last update

    //todo TASK:


    //todo Changes:
    // made enderman teleport to players in the config
    // made slime minions
    // added skeleton rapid fire in the pve config
    // made you can ride the bosses and players
    // added Beef
    // added riding to pve config and lobby config

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
            createGUI = new CreateGUI(this);
            rejoin = new Rejoin(this);
            openGUI = new OpenGUI(this);
            PlayerUseGUI = new PlayerUseGUI(this);
            groupLeave = new GroupLeave(this);
            onLeave = new OnLeave(this);
            placingListener = new PlacingListener(this);
            dropListener = new DropListener(this);
            itemManager = new ItemManager();
            breakListener = new BreakListener(this);
            damageListener = new DamageListener(this);
            playAgainListener = new PlayAgainListener(this);
            pickupListener = new PickupListener(this);
            timerTik = new TimerTik(this);
            readyListener = new ReadyListener(this);
            projectileHitListener = new ProjectileHitListener(this);
            ridingListener = new RidingListener(this);
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
            Objects.requireNonNull(getCommand("foodmaster")).setTabCompleter(new MainTabCompletion(this));
            System.out.println("  <-> Commands Registered <->");
            getServer().getPluginManager().registerEvents(openGUI, this);
            getServer().getPluginManager().registerEvents(groupLeave, this);
            getServer().getPluginManager().registerEvents(PlayerUseGUI, this);
            getServer().getPluginManager().registerEvents(onLeave, this);
            getServer().getPluginManager().registerEvents(readyListener, this);
            getServer().getPluginManager().registerEvents(ridingListener, this);
            getServer().getPluginManager().registerEvents(playAgainListener, this);
            getServer().getPluginManager().registerEvents(endermanTpListener, this);
            getServer().getPluginManager().registerEvents(skeletonShootListener, this);
            getServer().getPluginManager().registerEvents(beefPowerListener, this);
            fallDamage = new FallDamage(this);
            getServer().getPluginManager().registerEvents(fallDamage, this);
            // part of the anti-glitch system
            getServer().getPluginManager().registerEvents(breakListener, this);
            getServer().getPluginManager().registerEvents(dropListener, this);
            getServer().getPluginManager().registerEvents(placingListener, this);
            getServer().getPluginManager().registerEvents(damageListener, this);
            getServer().getPluginManager().registerEvents(pickupListener, this);
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
            removePlayerFromWaitLobby = new RemovePlayerFromWaitLobby(this);
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
                removePlayerFromWaitLobby.removePlayerFromWaitedLobby(player);
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
                                if (isPlayerPlayingPvE(player) || isPlayerChooseToPlayPvE(player)) {
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
                                                givePlayerWin(player2);
                                            }
                                        }
                                    }
                                    for (UUID uuid2 : losses) {
                                        if (uuid2 != null) {
                                            Player player2 = Bukkit.getPlayer(uuid2);
                                            if (player2 != null) {
                                                givePlayerLose(player2);
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

    public String getPlayerTeam(Player player) {
        if (isPlayerPlayingFoodWars(player)) {
            UUID uuid = player.getUniqueId();
            if (getGroupPlayersInRedTeam(player) != null && !getGroupPlayersInRedTeam(player).isEmpty() && getGroupPlayersInRedTeam(player).contains(uuid)) {
                return "red";
            }
            if (getGroupPlayersInBlueTeam(player) != null && !getGroupPlayersInBlueTeam(player).isEmpty() && getGroupPlayersInBlueTeam(player).contains(uuid)) {
                return "blue";
            }
            if (getGroupPlayersInCyanTeam(player) != null && !getGroupPlayersInCyanTeam(player).isEmpty() && getGroupPlayersInCyanTeam(player).contains(uuid)) {
                return "cyan";
            }
            if (getGroupPlayersInGreenTeam(player) != null && !getGroupPlayersInGreenTeam(player).isEmpty() && getGroupPlayersInGreenTeam(player).contains(uuid)) {
                return "green";
            }
            if (getGroupPlayersInYellowTeam(player) != null && !getGroupPlayersInYellowTeam(player).isEmpty() && getGroupPlayersInYellowTeam(player).contains(uuid)) {
                return "yellow";
            }
        }
        return "";
    }

    public synchronized void thePlayerIsDead(Player player) {
        String name = getGameName(player);
        UUID uuid = player.getUniqueId();
        stillAlive.get(name).remove(uuid);
        kitsCoolDown.playerCoolDownMap.remove(uuid);
        kitPowerCoolDown.playerCoolDownMap.remove(uuid);
        if (stillAlive.get(name).size() == 1) {
            List<UUID> alivePlayer = new ArrayList<>(stillAlive.get(name));
            UUID alive = alivePlayer.get(0);
            if (alive != null) {
                Player player1 = Bukkit.getPlayer(alive);
                if (player1 != null) {
                    Set<UUID> group = new HashSet<>(getPlayersInGroupOfPlayer(player));
                    for (UUID uuid1 : group) {
                        if (uuid1 != null) {
                            Player player2 = Bukkit.getPlayer(uuid1);
                            if (player2 != null) {
                                endTheGame.endTheGameWithStatus(player2);
                            }
                        }
                    }
                    for (UUID uuid1 : group) {
                        if (uuid1 != null) {
                            inGameKills.remove(uuid1);
                            inGameDeaths.remove(uuid1);
                            playersInBlueTeams.remove(uuid1);
                            playersInCyanTeams.remove(uuid1);
                            playersInRedTeams.remove(uuid1);
                            playersInGreenTeams.remove(uuid1);
                            playersInYellowTeams.remove(uuid1);
                            playersRandomTeam.remove(uuid1);
                            playersChoiceFoodGame.remove(uuid);
                            playersInFoodGame.remove(uuid);
                            FoodGameWinner.remove(uuid);
                        }
                    }
                    for (UUID uuid1 : winners) {
                        if (uuid1 != null) {
                            Player player11 = Bukkit.getPlayer(uuid1);
                            if (player11 != null) {
                                givePlayerWin(player11);
                            }
                        }
                    }
                    for (UUID uuid1 : losses) {
                        if (uuid1 != null) {
                            Player player11 = Bukkit.getPlayer(uuid1);
                            if (player11 != null) {
                                givePlayerLose(player11);
                            }
                        }
                    }
                    winners.clear();
                    losses.clear();
                }
            }
        } else {
            // kits
            playersInPotatoKit.remove(uuid);
            playersInBreadKit.remove(uuid);
            playersInMelonKit.remove(uuid);
            playersInCookieKit.remove(uuid);
            playersInFishKit.remove(uuid);
            playersRandomKit.remove(uuid);
            // teams
            playersInBlueTeams.remove(uuid);
            playersInCyanTeams.remove(uuid);
            playersInRedTeams.remove(uuid);
            playersInGreenTeams.remove(uuid);
            playersInYellowTeams.remove(uuid);
            playersRandomTeam.remove(uuid);
            playersInFoodGame.remove(uuid);
            playersPlayingPvEZombie.remove(uuid);
            playersPlayingPvESpider.remove(uuid);
            playersPlayingPvEEnderman.remove(uuid);
            playersPlayingPvESkeleton.remove(uuid);
            playersPlayingPvESlime.remove(uuid);
            // others
            kickedPlayers.remove(uuid);
            ReadySystem.remove(uuid);
            ReadyPlayers.remove(uuid);
            lives.remove(uuid);
            AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert attributeInstance != null;
            attributeInstance.setBaseValue(20);
            player.setHealth(20);
            player.removePotionEffect(PotionEffectType.SPEED);
            player.getInventory().clear();
            Location loc = player.getLocation();
            if (isPlayerInGroup(player)) {
                for (UUID uuids : getPlayersInGroupOfPlayer(player)) {
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
            if (mainConfig.getStrGame("message_on_death") != null && !mainConfig.getStrGame("message_on_death").equalsIgnoreCase("") && !mainConfig.getStrGame("message_on_death").equalsIgnoreCase("empty")) {
                player.sendMessage(mainConfig.getStrGame("message_on_death"));
            }
        }
    }

    public Set<String> getAliveTeams(Player player) {
        Set<String> aliveTeams = new HashSet<>();
        if (player != null && isPlayerInGroup(player) && isPlayerPlayingFoodWars(player)) {
            String gameName = getGameName(player);
            if (gameName != null && mainConfig.getBooleanGame("enable_lives_team_deathmatch")) {
                for (UUID uuid : stillAlive.get(gameName)) {
                    if (uuid != null) {
                        Player alivePlayer = Bukkit.getPlayer(uuid);
                        if (alivePlayer != null) {
                            if (!getGroupPlayersInCyanTeam(player).isEmpty() && getGroupPlayersInCyanTeam(player).contains(uuid)) {
                                aliveTeams.add("cyan");
                            } else if (!getGroupPlayersInBlueTeam(player).isEmpty() && getGroupPlayersInBlueTeam(player).contains(uuid)) {
                                aliveTeams.add("blue");
                            } else if (!getGroupPlayersInRedTeam(player).isEmpty() && getGroupPlayersInRedTeam(player).contains(uuid)) {
                                aliveTeams.add("red");
                            } else if (!getGroupPlayersInYellowTeam(player).isEmpty() && getGroupPlayersInYellowTeam(player).contains(uuid)) {
                                aliveTeams.add("yellow");
                            } else if (!getGroupPlayersInGreenTeam(player).isEmpty() && getGroupPlayersInGreenTeam(player).contains(uuid)) {
                                aliveTeams.add("green");
                            }
                        }
                    }
                }
                return aliveTeams;
            }
        }
        return null;
    }

    public synchronized void givePlayerWin(Player player) {
        String uuidOfDeadString = String.valueOf(player.getUniqueId());
        if (uuidOfDeadString != null) {
            String uuidOfDeadString1 = String.valueOf(player.getUniqueId());
            if (uuidOfDeadString1 != null) {
                try {
                    int killerCounter = mainConfig.getIntWin(uuidOfDeadString);
                    mainConfig.setIntWin(uuidOfDeadString1, killerCounter + 1);
                    mainConfig.saveWin();
                    mainConfig.reloadWin();
                } catch (Exception e) {
                    mainConfig.setIntWin(uuidOfDeadString1, 1);
                }
            }
        }
    }

    public synchronized void givePlayerLose(Player player) {
        String uuidOfDeadString = String.valueOf(player.getUniqueId());
        if (uuidOfDeadString != null) {
            try {
                int killerCounter = mainConfig.getIntLose(uuidOfDeadString);
                mainConfig.setIntLose(uuidOfDeadString, killerCounter + 1);
                mainConfig.saveLose();
                mainConfig.reloadLose();
            } catch (Exception e) {
                mainConfig.setIntLose(uuidOfDeadString, 1);
            }
        }
    }

    public synchronized void givePlayerKD(Player dead, Player killer) {
        String uuidOfDeadString = String.valueOf(dead.getUniqueId());
        String uuidOfKillerString = String.valueOf(killer.getUniqueId());
        if (uuidOfDeadString != null && uuidOfKillerString != null) {
            try {
                int deadCounter = mainConfig.getIntDeath(uuidOfDeadString);
                mainConfig.setIntDeath(uuidOfDeadString, deadCounter + 1);
                mainConfig.saveDeath();
                UUID uuidD = dead.getUniqueId();
                if (inGameDeaths.containsKey(uuidD)) {
                    int deaths = inGameDeaths.get(uuidD);
                    inGameDeaths.put(uuidD, deaths + 1);
                } else {
                    inGameDeaths.put(uuidD, 1);
                }
            } catch (Exception e) {
                mainConfig.setIntDeath(uuidOfDeadString, 1);
                UUID uuidD = dead.getUniqueId();
                if (inGameDeaths.containsKey(uuidD)) {
                    int deaths = inGameDeaths.get(uuidD);
                    inGameDeaths.put(uuidD, deaths + 1);
                } else {
                    inGameDeaths.put(uuidD, 1);
                }
            }
            try {
                int killerCounter = mainConfig.getIntKill(uuidOfKillerString);
                mainConfig.setIntKill(uuidOfKillerString, killerCounter + 1);
                mainConfig.saveKill();
                mainConfig.reloadKill();
                UUID uuid = killer.getUniqueId();
                if (inGameKills.containsKey(uuid)) {
                    int kills = inGameKills.get(uuid);
                    inGameKills.put(uuid, kills + 1);
                } else {
                    inGameKills.put(uuid, 1);
                }
            } catch (Exception e) {
                mainConfig.setIntKill(uuidOfKillerString, 1);
                UUID uuid = killer.getUniqueId();
                if (inGameKills.containsKey(uuid)) {
                    int kills = inGameKills.get(uuid);
                    inGameKills.put(uuid, kills + 1);
                } else {
                    inGameKills.put(uuid, 1);
                }
            }
        }
    }

    public boolean isPlayerPlayingPvE(Player player) {
        UUID uuid = player.getUniqueId();
        return playersPlayingPvEZombie.contains(uuid) || playersPlayingPvESkeleton.contains(uuid) || playersPlayingPvESpider.contains(uuid) || playersPlayingPvEEnderman.contains(uuid) || playersPlayingPvESlime.contains(uuid);
    }

    public boolean isPlayerChooseToPlayPvE(Player player) {
        UUID uuid = player.getUniqueId();
        boolean zombie = !playersChoicePvEZombie.isEmpty() && playersChoicePvEZombie.contains(uuid);
        boolean skeleton = !playersChoicePvESkeleton.isEmpty() && playersChoicePvESkeleton.contains(uuid);
        boolean spider = !playersChoicePvESpider.isEmpty() && playersChoicePvESpider.contains(uuid);
        boolean enderman = !playersChoicePvEEnderman.isEmpty() && playersChoicePvEEnderman.contains(uuid);
        boolean slime = !playersChoicePvESlime.isEmpty() && playersChoicePvESlime.contains(uuid);
        return zombie || skeleton || spider || enderman || slime;
    }

    public boolean isPlayerChooseToPlayFoodWars(Player player) {
        UUID uuid = player.getUniqueId();
        boolean Teams2 = !playersThatChoice2Teams.isEmpty() && playersThatChoice2Teams.contains(uuid);
        boolean Teams3 = !playersThatChoice3Teams.isEmpty() && playersThatChoice3Teams.contains(uuid);
        boolean Teams4 = !playersThatChoice4Teams.isEmpty() && playersThatChoice4Teams.contains(uuid);
        boolean Teams5 = !playersThatChoice5Teams.isEmpty() && playersThatChoice5Teams.contains(uuid);
        return Teams2 || Teams3 || Teams4 || Teams5;
    }

    public String getTime(int totalTime) {
        if ((totalTime % 60) == 0) {
            // just a full minute
            int justMinute = totalTime / 60;
            if (justMinute == 1) {
                return justMinute + " minute";
            } else if (justMinute > 1) {
                return justMinute + " minutes";
            }
        } else {
            // minute and seconds
            if (totalTime > 60) {
                int justMin = totalTime / 60;
                int minInSec = justMin * 60;
                int seconds = totalTime - minInSec;
                if (seconds >= 10) {
                    if (justMin > 1) {
                        return justMin + ":" + seconds + " minutes";
                    } else {
                        return justMin + ":" + seconds + " minute";
                    }
                } else {
                    if (justMin > 1) {
                        return justMin + ":0" + seconds + " minutes";
                    } else {
                        return justMin + ":0" + seconds + " minute";
                    }
                }
            } else {
                return String.valueOf(totalTime);
            }
        }
        return null;
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
        if (mainConfig.getStrMain("name") != null) {
            s = " " + mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        double hp = playerDead.getHealthScale();
        playerDead.setHealth(hp);
        String GameName = getGameName(playerDead);
        UUID uuid3 = playerDead.getUniqueId();
        kitsCoolDown.playerCoolDownMap.remove(uuid3);
        kitPowerCoolDown.playerCoolDownMap.remove(uuid3);
        if (isPlayerPlayingPvE(playerDead) && !isPlayerInGroup(playerDead)) {
            if (!GameSpawnPoints.containsKey(GameName)) {
                playerDead.sendMessage(ERROR + "There is no game with name " + gold + "" + GameName);
                return;
            }
            if (GameSpawnPoints.get(GameName) == null) {
                playerDead.sendMessage(ERROR + "There are no game spawn points set.");
                return;
            }
            Set<String> namesOfGameLoc = GameSpawnPoints.get(GameName);
            int theIndex = new Random().nextInt(namesOfGameLoc.size());
            List<String> list1 = new ArrayList<>(namesOfGameLoc);
            String theNameOfLoc = list1.get(theIndex);
            if (tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                while (tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                    int theIndex2 = new Random().nextInt(namesOfGameLoc.size());
                    theNameOfLoc = list1.get(theIndex2);
                }
            }
            if (kickedPlayers.contains(uuid3)) {
                if (isPlayerInGroup(playerDead)) {
                    for (UUID uuid2 : getPlayersInGroupOfPlayer(playerDead)) {
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
                endTheGame.endThePvE(playerDead);
            } else {
                if (gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uuid3) || tpPlayersInGameNameLoc.containsValue(null)) {
                    Location theLocation = mainConfig.getLocationGame(GameName + "->" + theNameOfLoc + "-spawn-point");
                    playerDead.teleport(theLocation);
                    if (playersInFishKit.contains(playerDead.getUniqueId()) || playersInMelonKit.contains(playerDead.getUniqueId())) {
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
                    if (mainConfig.getStrGame("message_on_death") != null && !mainConfig.getStrGame("message_on_death").equalsIgnoreCase("") && !mainConfig.getStrGame("message_on_death").equalsIgnoreCase("empty")) {
                        playerDead.sendMessage(INFO + mainConfig.getStrGame("message_on_death"));
                    }
                    playerDead.sendTitle(gold + "" + italic + "Respawned", null, 2, 30, 2);
                    tpPlayersInGameNameLoc.put(uuid3, theNameOfLoc);
                    gameSpawnCoolDown.addPlayerToCoolMap(uuid3, 5);
                    playerDead.sendMessage(NORMAL + "You are teleported to the " + gold + "" + italic + "" + GameName + "" + green + "" + italic + " game on the " + gold + "" + italic + "" + theNameOfLoc + "" + green + "" + italic + " location.");
                }
            }
        } else {
            Set<UUID> playersInGroupOfPlayer = getPlayersInGroupOfPlayer(playerDead);
            if (GameName == null) {
                playerDead.sendMessage(WARN + "The game you were playing does not exist any more.");
            } else {
                Set<String> namesOfGameLoc = GameSpawnPoints.get(GameName);
                if (namesOfGameLoc.isEmpty()) {
                    playerDead.sendMessage(ERROR + "" + gold + "" + italic + "" + GameName + "" + green + "" + italic + " game don't have any spawn points.");
                    if (playersInGroupOfPlayer != null && !playersInGroupOfPlayer.isEmpty()) {
                        for (UUID uuid : playersInGroupOfPlayer) {
                            if (uuid != null) {
                                Player playerGroup = Bukkit.getPlayer(uuid);
                                if (playerGroup != null) {
                                    endTheGame.endTheGameWithStatus(playerGroup);
                                }
                            }
                        }
                        for (UUID uuid : playersInGroupOfPlayer) {
                            if (uuid != null) {
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
                            }
                        }
                        for (UUID uuid : winners) {
                            if (uuid != null) {
                                Player player = Bukkit.getPlayer(uuid);
                                if (player != null) {
                                    givePlayerWin(player);
                                }
                            }
                        }
                        for (UUID uuid : losses) {
                            if (uuid != null) {
                                Player player = Bukkit.getPlayer(uuid);
                                if (player != null) {
                                    givePlayerLose(player);
                                }
                            }
                        }
                        winners.clear();
                        losses.clear();
                    }
                } else {
                    int theIndex = new Random().nextInt(namesOfGameLoc.size());
                    List<String> list1 = new ArrayList<>(namesOfGameLoc);
                    String theNameOfLoc = list1.get(theIndex);
                    if (tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                        while (tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                            int theIndex2 = new Random().nextInt(namesOfGameLoc.size());
                            theNameOfLoc = list1.get(theIndex2);
                        }
                    }
                    if (kickedPlayers.contains(uuid3)) {
                        playerDead.sendMessage(INFO + "You won't respawn because you are kicked from the game.");
                        if (getPlayersInGroupOfPlayer(playerDead).size() == 2 && playersInGroupOfPlayer != null && !playersInGroupOfPlayer.isEmpty()) {
                            for (UUID uuid1 : playersInGroupOfPlayer) {
                                if (uuid1 != null) {
                                    Player playerGroup = Bukkit.getPlayer(uuid1);
                                    if (playerGroup != null) {
                                        endTheGame.endTheGameWithStatus(playerGroup);
                                    }
                                }
                            }
                            for (UUID uuid1 : playersInGroupOfPlayer) {
                                if (uuid1 != null) {
                                    inGameKills.remove(uuid1);
                                    inGameDeaths.remove(uuid1);
                                    playersInBlueTeams.remove(uuid1);
                                    playersInCyanTeams.remove(uuid1);
                                    playersInRedTeams.remove(uuid1);
                                    playersInGreenTeams.remove(uuid1);
                                    playersInYellowTeams.remove(uuid1);
                                    playersRandomTeam.remove(uuid1);
                                    playersChoiceFoodGame.remove(uuid1);
                                    playersInFoodGame.remove(uuid1);
                                    FoodGameWinner.remove(uuid1);
                                }
                            }
                            for (UUID uuid1 : winners) {
                                if (uuid1 != null) {
                                    Player player = Bukkit.getPlayer(uuid1);
                                    if (player != null) {
                                        givePlayerWin(player);
                                    }
                                }
                            }
                            for (UUID uuid1 : losses) {
                                if (uuid1 != null) {
                                    Player player = Bukkit.getPlayer(uuid1);
                                    if (player != null) {
                                        givePlayerLose(player);
                                    }
                                }
                            }
                            winners.clear();
                            losses.clear();
                        }
                    } else {
                        // the player can be teleported
                        if (gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uuid3) || tpPlayersInGameNameLoc.containsValue(null)) {
                            Location theLocation = mainConfig.getLocationGame(GameName + "->" + theNameOfLoc + "-spawn-point");
                            //playerDead.getInventory().clear();
                            // enable_lives
                            if (mainConfig.getBooleanGame("enable_lives_free-for-all") && isPlayerPlayingFreeForAll(playerDead)) {
                                UUID uuid1 = playerDead.getUniqueId();
                                int live = mainConfig.getIntGame("lives");
                                if (lives.isEmpty() || !lives.containsKey(uuid1)) {
                                    lives.put(uuid1, live);
                                    playerDead.sendMessage(INFO + "You have " + live + " lives.");
                                } else {
                                    if (lives.containsKey(uuid1)) {
                                        int newLive = lives.get(uuid1) - 1;
                                        lives.put(uuid1, newLive);
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
                            } else if (mainConfig.getBooleanGame("enable_lives_team_deathmatch") && isPlayerPlayingFoodWars(playerDead)) {
                                UUID uuid1 = playerDead.getUniqueId();
                                int live = mainConfig.getIntGame("lives");
                                if (lives.isEmpty() || !lives.containsKey(uuid1)) {
                                    lives.put(uuid1, live);
                                    playerDead.sendMessage(INFO + "You have " + gold + "" + italic + "" + live + "" + aqua + "" + italic + " lives.");
                                } else {
                                    if (lives.containsKey(uuid1)) {
                                        int newLive = lives.get(uuid1) - 1;
                                        lives.put(uuid1, newLive);
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
                            if (isPlayerInGroup(playerDead)) {
                                for (UUID uuids : getPlayersInGroupOfPlayer(playerDead)) {
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
                            kitsCoolDown.playerCoolDownMap.remove(uuid3);
                            kitPowerCoolDown.playerCoolDownMap.remove(uuid3);
                            if (playersInFishKit.contains(playerDead.getUniqueId()) || playersInMelonKit.contains(playerDead.getUniqueId())) {
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
                            if (mainConfig.getStrGame("message_on_death") != null && !mainConfig.getStrGame("message_on_death").equalsIgnoreCase("") && !mainConfig.getStrGame("message_on_death").equalsIgnoreCase("empty")) {
                                playerDead.sendMessage(INFO + mainConfig.getStrGame("message_on_death"));
                            }
                            playerDead.sendTitle(gold + "" + italic + "Respawned", null, 2, 30, 2);
                            tpPlayersInGameNameLoc.put(uuid3, theNameOfLoc);
                            gameSpawnCoolDown.addPlayerToCoolMap(uuid3, 5);
                            playerDead.sendMessage(NORMAL + "You are teleported to the " + gold + "" + italic + "" + GameName + "" + green + "" + italic + " game on the " + gold + "" + italic + "" + theNameOfLoc + "" + green + "" + italic + " location.");
                        }
                    }
                }
            }
        }
    }

    public boolean isPlayerInGroup(Player player) {
        for (Set<UUID> group : allGroups) {
            if (group.contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerChosenKit(Player player) {
        UUID uuid = player.getUniqueId();
        if (playersInPotatoKit.contains(uuid)) {
            return true;
        } else if (playersInBreadKit.contains(uuid)) {
            return true;
        } else if (playersInCookieKit.contains(uuid)) {
            return true;
        } else if (playersInMelonKit.contains(uuid)) {
            return true;
        } else if (playersInFishKit.contains(uuid)) {
            return true;
        } else return playersInBeefKit.contains(uuid);
    }

    public void randomKitGiver(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        String s;
        if (mainConfig.getStrMain("name") != null) {
            s = " " + mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        UUID uuid = player.getUniqueId();
        if (playersRandomKit.contains(uuid)) {
            if (playersChoiceFoodGame.contains(uuid)) {
                // Cookie
                playersInCookieKit.add(uuid);
                player.sendMessage(NORMAL + "" + gold + "" + italic + "Cookie" + green + "" + italic + " kit was chosen.");
            } else {
                int randomInt = new Random().nextInt(6);
                if (randomInt == 0) {
                    // Fish
                    playersInFishKit.add(uuid);
                    player.sendMessage(NORMAL + "" + gold + "" + italic + "Fish" + green + "" + italic + " kit was chosen.");
                } else if (randomInt == 1) {
                    // Bread
                    playersInBreadKit.add(uuid);
                    player.sendMessage(NORMAL + "" + gold + "" + italic + "Bread" + green + "" + italic + " kit was chosen.");
                } else if (randomInt == 2) {
                    // Potato
                    playersInPotatoKit.add(uuid);
                    player.sendMessage(NORMAL + "" + gold + "" + italic + "Potato" + green + "" + italic + " kit was chosen.");
                } else if (randomInt == 3) {
                    // Melon
                    playersInMelonKit.add(uuid);
                    player.sendMessage(NORMAL + "" + gold + "" + italic + "Melon" + green + "" + italic + " kit was chosen.");
                } else if (randomInt == 4) {
                    // Beef
                    playersInBeefKit.add(uuid);
                    player.sendMessage(NORMAL + "" + gold + "" + italic + "Beef" + green + "" + italic + " kit was chosen.");
                } else {
                    // Cookie
                    playersInCookieKit.add(uuid);
                    player.sendMessage(NORMAL + "" + gold + "" + italic + "Cookie" + green + "" + italic + " kit was chosen.");
                }
            }
            playersRandomKit.remove(uuid);
        }
    }

    public boolean isPlayerPlayingFreeForAll(Player player) {
        return playersInFreeForAll.contains(player.getUniqueId());
    }

    public boolean isPlayerPlayingFoodGame(Player player) {
        return playersInFoodGame.contains(player.getUniqueId());
    }

    public boolean isPlayerPlayingFoodWars(Player player) {
        return players2Teams.contains(player.getUniqueId())
                || players3Teams.contains(player.getUniqueId())
                || players4Teams.contains(player.getUniqueId())
                || players5Teams.contains(player.getUniqueId());
    }

    public Set<UUID> getPlayersInGroupOfPlayer(Player player) {
        for (Set<UUID> group : allGroups) {
            if (group.contains(player.getUniqueId())) {
                return group;
            }
        }
        return null;
    }

    public String getPlayerNamesFromGroupString(Player player) {
        Set<String> names = new HashSet<>();
        if (player != null && isPlayerInGroup(player)) {
            for (UUID uuid : Objects.requireNonNull(getPlayersInGroupOfPlayer(player))) {
                Player players = Bukkit.getPlayer(uuid);
                if (players != null) {
                    names.add(players.getName());
                }
            }
        }
        return names.toString().replace("[", "").replace("]", "").replace("\"\"", "");
    }

    public synchronized void PlayerLeaveFromGroup(Player playerLeaver) {
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
        if (mainConfig.getStrMain("name") != null) {
            s = " " + mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        Location endLoc = mainConfig.getLocationMain("end_location");
        if (playerLeaver == null) {
            System.out.println(ERROR + "An unknown player wants to leave the group.");
            return;
        }
        if (!isPlayerInGroup(playerLeaver)) {
            playerLeaver.sendMessage(WARN + "You need to be in a group so you can leave.");
            return;
        }
        for (UUID uuid : getPlayersInGroupOfPlayer(playerLeaver)) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && !player.equals(playerLeaver)) {
                player.sendMessage(NORMAL + "" + gold + "" + italic + "" + playerLeaver.getName() + "" + green + "" + italic + " has left the group.");
            }
        }
        if (endLoc == null) {
            if (playerLeaver.hasPermission("foodm.staff")) {
                playerLeaver.sendMessage(WARN + "The end location is not set. Use " + ChatColor.DARK_PURPLE + "" + italic + "/fm set end");
            } else {
                playerLeaver.sendMessage(WARN + "One of the locations are not set.");
            }
        }
        playerLeaver.sendTitle(gold + "" + italic + "You left the group", aqua + "" + italic + "" + getPlayerNamesFromGroupString(playerLeaver).replaceAll("\"\"", "").replace("[", "").replace("]", ""), 2, 80, 2);
        if (isPlayerInGame(playerLeaver) && getPlayersInGroupOfPlayer(playerLeaver).size() == 2) {
            for (UUID uuid1 : getPlayersInGroupOfPlayer(playerLeaver)) {
                if (uuid1 != null) {
                    Player playerGroup = Bukkit.getPlayer(uuid1);
                    if (playerGroup != null) {
                        endTheGame.endTheGameWithStatus(playerGroup);
                    }
                }
            }
            for (UUID uuid1 : getPlayersInGroupOfPlayer(playerLeaver)) {
                if (uuid1 != null) {
                    inGameKills.remove(uuid1);
                    inGameDeaths.remove(uuid1);
                    playersInBlueTeams.remove(uuid1);
                    playersInCyanTeams.remove(uuid1);
                    playersInRedTeams.remove(uuid1);
                    playersInGreenTeams.remove(uuid1);
                    playersInYellowTeams.remove(uuid1);
                    playersRandomTeam.remove(uuid1);
                    playersChoiceFoodGame.remove(uuid1);
                    playersInFoodGame.remove(uuid1);
                    FoodGameWinner.remove(uuid1);
                }
            }
            for (UUID uuid : winners) {
                if (uuid != null) {
                    Player player = Bukkit.getPlayer(uuid);
                    if (player != null) {
                        givePlayerWin(player);
                    }
                }
            }
            for (UUID uuid : losses) {
                if (uuid != null) {
                    Player player = Bukkit.getPlayer(uuid);
                    if (player != null) {
                        givePlayerLose(player);
                    }
                }
            }
            winners.clear();
            losses.clear();
            getPlayersInGroupOfPlayer(playerLeaver).remove(playerLeaver.getUniqueId());
            if (playerLeaver.getInventory().contains(ItemManager.playAgain)) {
                playerLeaver.getInventory().remove(ItemManager.playAgain);
            }
            playerLeaver.sendMessage(NORMAL + "You left the group.");
            playAgain.remove(playerLeaver.getUniqueId());
            List<Set<UUID>> removeListG = new ArrayList<>();
            for (Set<UUID> group : allGroups) {
                if (group.size() <= 1) {
                    removeListG.add(group);
                }
                if (group.size() == 1) {
                    UUID uuid = group.stream().findFirst().get();
                    Player player = Bukkit.getPlayer(uuid);
                    if (player != null) {
                        if (isPlayerInWaitingLobby(player)) {
                            locOfPlayersInWaitingLobby.remove(new HashSet<>(getPlayersInGroupOfPlayer(player)));
                            removePlayerFromWaitLobby.removePlayerFromWaitedLobby(player);
                            if (endLoc != null) {
                                player.teleport(endLoc);
                            }
                        }
                        if (isPlayerInGame(player)) {
                            String name1 = getGameName(player);
                            stillAlive.remove(name1);
                            removePlayerFromGame(player);
                            if (endLoc != null) {
                                player.teleport(endLoc);
                            }
                        }
                        group.remove(player.getUniqueId());
                        playersInPotatoKit.remove(uuid);
                        playersInBreadKit.remove(uuid);
                        playersInMelonKit.remove(uuid);
                        playersInCookieKit.remove(uuid);
                        playersInFishKit.remove(uuid);
                        playersRandomKit.remove(uuid);
                        // teams
                        players2Teams.remove(uuid);
                        players3Teams.remove(uuid);
                        players4Teams.remove(uuid);
                        players5Teams.remove(uuid);
                        // others
                        playersThatChoice5Teams.remove(uuid);
                        playersThatChoice4Teams.remove(uuid);
                        playersThatChoice2Teams.remove(uuid);
                        playAgain.remove(uuid);
                        kickedPlayers.remove(uuid);
                        playersThatChoice3Teams.remove(uuid);
                        ReadySystem.remove(uuid);
                        ReadyPlayers.remove(uuid);
                        playersInFreeForAll.remove(uuid);
                        lives.remove(uuid);
                        playersChoiceFreeForAll.remove(uuid);
                        if (player.getInventory().contains(ItemManager.playAgain)) {
                            player.getInventory().remove(ItemManager.playAgain);
                        }
                        allGroups.removeIf(Set::isEmpty);
                        player.sendMessage(INFO + "It seems like you were alone in the group, so the group has been removed.");
                    }
                }
            }
            removeListG.forEach(allGroups::remove);
            return;
        }
        if (isPlayerInWaitingLobby(playerLeaver)) {
            locOfPlayersInWaitingLobby.remove(new HashSet<>(getPlayersInGroupOfPlayer(playerLeaver)));
            removePlayerFromWaitLobby.removePlayerFromWaitedLobby(playerLeaver);
            if (endLoc != null) {
                playerLeaver.teleport(endLoc);
            }
        }
        if (isPlayerInGame(playerLeaver)) {
            String name1 = getGameName(playerLeaver);
            stillAlive.remove(name1);
            removePlayerFromGame(playerLeaver);
            if (endLoc != null) {
                playerLeaver.teleport(endLoc);
            }
        }
        getPlayersInGroupOfPlayer(playerLeaver).remove(playerLeaver.getUniqueId());
        allGroups.removeIf(Set::isEmpty);
        playerLeaver.sendMessage(NORMAL + "You left the group.");
        // remove very small groups
        List<Set<UUID>> removeListG = new ArrayList<>();
        for (Set<UUID> group : allGroups) {
            if (group.size() <= 1) {
                removeListG.add(group);
            }
            if (group.size() == 1) {
                UUID uuid = group.stream().findFirst().get();
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    if (isPlayerInWaitingLobby(player)) {
                        locOfPlayersInWaitingLobby.remove(new HashSet<>(getPlayersInGroupOfPlayer(player)));
                        removePlayerFromWaitLobby.removePlayerFromWaitedLobby(player);
                        if (endLoc != null) {
                            player.teleport(endLoc);
                        }
                    }
                    if (isPlayerInGame(player)) {
                        String name1 = getGameName(player);
                        stillAlive.remove(name1);
                        removePlayerFromGame(player);
                        if (endLoc != null) {
                            player.teleport(endLoc);
                        }
                    }
                    group.remove(player.getUniqueId());
                    playersInPotatoKit.remove(uuid);
                    playersInBreadKit.remove(uuid);
                    playersInMelonKit.remove(uuid);
                    playersInCookieKit.remove(uuid);
                    playersInFishKit.remove(uuid);
                    playersRandomKit.remove(uuid);
                    // teams
                    players2Teams.remove(uuid);
                    players3Teams.remove(uuid);
                    players4Teams.remove(uuid);
                    players5Teams.remove(uuid);
                    // others
                    playersThatChoice5Teams.remove(uuid);
                    playersThatChoice4Teams.remove(uuid);
                    playersThatChoice2Teams.remove(uuid);
                    kickedPlayers.remove(uuid);
                    playersThatChoice3Teams.remove(uuid);
                    ReadySystem.remove(uuid);
                    ReadyPlayers.remove(uuid);
                    playersInFreeForAll.remove(uuid);
                    lives.remove(uuid);
                    playersChoiceFreeForAll.remove(uuid);
                    if (player.getInventory().contains(ItemManager.playAgain)) {
                        player.getInventory().remove(ItemManager.playAgain);
                    }
                    allGroups.removeIf(Set::isEmpty);
                    player.sendMessage(INFO + "It seems like you were alone in the group, so the group has been removed.");
                }
            }
        }
        removeListG.forEach(allGroups::remove);
        UUID uuid = playerLeaver.getUniqueId();
        playersInPotatoKit.remove(uuid);
        playersInBreadKit.remove(uuid);
        playersInMelonKit.remove(uuid);
        playersInCookieKit.remove(uuid);
        playersInFishKit.remove(uuid);
        playersRandomKit.remove(uuid);
        // teams
        players2Teams.remove(uuid);
        players3Teams.remove(uuid);
        players4Teams.remove(uuid);
        players5Teams.remove(uuid);
        // others
        playersThatChoice5Teams.remove(uuid);
        playersThatChoice4Teams.remove(uuid);
        playersThatChoice2Teams.remove(uuid);
        kickedPlayers.remove(uuid);
        playersThatChoice3Teams.remove(uuid);
        ReadySystem.remove(uuid);
        ReadyPlayers.remove(uuid);
        playersInFreeForAll.remove(uuid);
        lives.remove(uuid);
        playersChoiceFreeForAll.remove(uuid);
        if (playerLeaver.getInventory().contains(ItemManager.playAgain)) {
            playerLeaver.getInventory().remove(ItemManager.playAgain);
        }
    }

    public synchronized void PlayerJoinInGroup(Player playerJoiner, Player inviter) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (mainConfig.getStrMain("name") != null) {
            s = " " + mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        UUID uuid = inviter.getUniqueId();
        UUID uniqueId = playerJoiner.getUniqueId();
        // playerJoiner = invited
        // key - invited
        // value - inviters
        Set<UUID> list = invites.get(uniqueId);
        if (list != null && list.contains(uuid)) {
            if (isPlayerInGroup(inviter) && getPlayersInGroupOfPlayer(inviter).size() >= mainConfig.getIntMain("max-players_in_group")) {
                inviter.sendMessage(WARN + "You have reached the max player limit in your group");
                playerJoiner.sendMessage(WARN + "" + red + "" + italic + "" + inviter.getName() + "" + yellow + "" + italic + " has reached the max player limit in the group so you can't join.");
                return;
            }
            if (isPlayerInGroup(playerJoiner) && getPlayersInGroupOfPlayer(playerJoiner).contains(uniqueId)) {
                getPlayersInGroupOfPlayer(playerJoiner).remove(uniqueId);
                String ok = getPlayerNamesFromGroupString(inviter).replace("[", "").replace("]", "").replace("\"\"", "");
                String okJ = getPlayerNamesFromGroupString(playerJoiner).replace("[", "").replace("]", "").replace("\"\"", "");
                playerJoiner.sendMessage(NORMAL + "You have left the group of " + gold + "" + italic + "" + okJ + "" + green + "" + italic + " players and joined the group of " + gold + "" + italic + "" + ok + "" + green + "" + italic + " players.");
            }
            if (isPlayerInGroup(playerJoiner) && isPlayerInGroup(inviter)) {
                PlayerLeaveFromGroup(playerJoiner);
            }
            if (!isPlayerInGroup(playerJoiner) && isPlayerInGroup(inviter)) {
                getPlayersInGroupOfPlayer(inviter).add(uniqueId);
            }
            for (UUID players : Objects.requireNonNull(getPlayersInGroupOfPlayer(inviter))) {
                Player playersInGroup = Bukkit.getPlayer(players);
                if (playersInGroup != null && playersInGroup != playerJoiner) {
                    playersInGroup.sendMessage(NORMAL + "" + gold + "" + italic + "" + playerJoiner.getName() + "" + green + "" + italic + " has joined the group.");
                }
            }
            if (isPlayerInGroup(inviter) && getPlayersInGroupOfPlayer(inviter).size() >= mainConfig.getIntMain("max-players_in_group")) {
                inviter.sendMessage(NORMAL + "You have reached the max player limit in your group.");
                playerJoiner.sendMessage(NORMAL + "" + gold + "" + italic + "" + inviter.getName() + "" + green + "" + italic + " have reached the max player limit in the group.");
            }
            if (isPlayerInGroup(playerJoiner) && getPlayersInGroupOfPlayer(playerJoiner).contains(uuid)) {
                String okJ = getPlayerNamesFromGroupString(playerJoiner).replace("[", "").replace("]", "").replace("\"\"", "");
                playerJoiner.sendMessage(NORMAL + "You are now in a group with " + gold + "" + italic + "" + okJ + "" + green + "" + italic + " players.");
                playerJoiner.sendTitle(gold + "" + italic + "You have joined a group with", ChatColor.AQUA + "" + ChatColor.ITALIC + "" + getPlayerNamesFromGroupString(inviter).replaceAll("\"\"", "").replace("[", "").replace("]", ""), 2, 80, 2);
            }
        }
        for (UUID uuid1 : getPlayersInGroupOfPlayer(playerJoiner)) {
            if (uuid1 != null) {
                playAgain.remove(uuid1);
            }
        }
    }

    public String getWaitingLobbyNameByLoc(Location location) {
        for (String name : getWaitLobbyNames()) {
            try {
                Location loc = mainConfig.getLocationWaitLobby(name + "->wait-location");
                if (loc.equals(location)) {
                    return name;
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public boolean isPlayerInWaitingLobby(Player player) {
        for (String name : getWaitLobbyNames()) {
            if (name != null && playersInWaitingLobby.get(name).contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerHaveStartedTeams(Player player) {
        UUID uuid = player.getUniqueId();
        if (playersThatChoice2Teams.contains(uuid)) {
            return true;
        } else if (playersThatChoice3Teams.contains(uuid)) {
            return true;
        } else if (playersThatChoice4Teams.contains(uuid)) {
            return true;
        } else return playersThatChoice5Teams.contains(uuid);
    }

    public boolean isPlayerInTeams(Player player) {
        UUID uuid = player.getUniqueId();
        if (playersInBlueTeams.contains(uuid)) {
            return true;
        } else if (playersInRedTeams.contains(uuid)) {
            return true;
        } else if (playersInCyanTeams.contains(uuid)) {
            return true;
        } else if (playersInYellowTeams.contains(uuid)) {
            return true;
        } else return playersInGreenTeams.remove(uuid);
    }

    public ItemStack getItemByName(Inventory inventory, String name) {
        for (ItemStack item : inventory) {
            if (item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public void setReadyStatus(Player player) {
        String ReadyName = Objects.requireNonNull(ItemManager.isReady.getItemMeta()).getDisplayName();
        String NotReadyName = Objects.requireNonNull(ItemManager.notReady.getItemMeta()).getDisplayName();
        if (ReadyPlayers.contains(player.getUniqueId())) {
            List<String> lore3 = new ArrayList<>();
            lore3.add(ChatColor.GRAY + "" + ChatColor.ITALIC + " Your ready status >> " + ChatColor.GREEN + "" + ChatColor.BOLD + "Ready");
            ItemMeta meta = getItemByName(player.getInventory(), NotReadyName).getItemMeta();
            ItemStack item = getItemByName(player.getInventory(), NotReadyName);
            assert meta != null;
            meta.setLore(lore3);
            assert item != null;
            item.setItemMeta(meta);
        } else {
            List<String> lore4 = new ArrayList<>();
            lore4.add(ChatColor.GRAY + "" + ChatColor.ITALIC + " Your ready status >> " + ChatColor.RED + "" + ChatColor.BOLD + "UNReady");
            ItemMeta meta = getItemByName(player.getInventory(), ReadyName).getItemMeta();
            ItemStack item = getItemByName(player.getInventory(), ReadyName);
            assert meta != null;
            meta.setLore(lore4);
            assert item != null;
            item.setItemMeta(meta);
        }
    }

    public void setPlayersRandomTeam(Player player) {
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
        if (mainConfig.getStrMain("name") != null) {
            s = " " + mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        UUID uuid = player.getUniqueId();
        if (playersRandomTeam.contains(uuid)) {
            if (playersThatChoice2Teams.contains(uuid)) {
                Set<UUID> groupPlayers = new HashSet<>(getPlayersInGroupOfPlayer(player));
                Set<UUID> onlyOtherPlayersInBlueTeam = new HashSet<>(playersInBlueTeams);
                Set<UUID> BlueTeamPlayersInGroup = new HashSet<>(playersInBlueTeams);

                Set<UUID> RedTeamPlayersInGroup = new HashSet<>(playersInRedTeams);
                Set<UUID> onlyOtherPlayersInRedTeam = new HashSet<>(playersInRedTeams);

                onlyOtherPlayersInRedTeam.removeAll(groupPlayers);
                RedTeamPlayersInGroup.removeAll(onlyOtherPlayersInRedTeam);

                onlyOtherPlayersInBlueTeam.removeAll(groupPlayers);
                BlueTeamPlayersInGroup.removeAll(onlyOtherPlayersInBlueTeam);
                if (BlueTeamPlayersInGroup.isEmpty()) {
                    playersInBlueTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.BLUE + "" + italic + "Blue Team " + green + "" + italic + "!");
                } else if (RedTeamPlayersInGroup.isEmpty()) {
                    playersInRedTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + red + "" + italic + "Red Team " + green + "" + italic + "!");
                }
            } else if (playersThatChoice3Teams.contains(uuid)) {
                Set<UUID> groupPlayers = new HashSet<>(getPlayersInGroupOfPlayer(player));
                Set<UUID> onlyOtherPlayersInBlueTeam = new HashSet<>(playersInBlueTeams);
                Set<UUID> BlueTeamPlayersInGroup = new HashSet<>(playersInBlueTeams);

                Set<UUID> RedTeamPlayersInGroup = new HashSet<>(playersInRedTeams);
                Set<UUID> onlyOtherPlayersInRedTeam = new HashSet<>(playersInRedTeams);

                Set<UUID> GreenTeamPlayersInGroup = new HashSet<>(playersInGreenTeams);
                Set<UUID> onlyOtherPlayersInGreenTeam = new HashSet<>(playersInGreenTeams);

                onlyOtherPlayersInRedTeam.removeAll(groupPlayers);
                RedTeamPlayersInGroup.removeAll(onlyOtherPlayersInRedTeam);

                onlyOtherPlayersInGreenTeam.removeAll(groupPlayers);
                GreenTeamPlayersInGroup.removeAll(onlyOtherPlayersInGreenTeam);

                onlyOtherPlayersInBlueTeam.removeAll(groupPlayers);
                BlueTeamPlayersInGroup.removeAll(onlyOtherPlayersInBlueTeam);
                if (BlueTeamPlayersInGroup.isEmpty()) {
                    playersInBlueTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.BLUE + "" + italic + "Blue Team " + green + "" + italic + "!");
                } else if (RedTeamPlayersInGroup.isEmpty()) {
                    playersInRedTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + red + "" + italic + "Red Team " + green + "" + italic + "!");
                } else if (GreenTeamPlayersInGroup.isEmpty()) {
                    playersInGreenTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.DARK_GREEN + "" + italic + "Green Team " + green + "" + italic + "!");
                }
            } else if (playersThatChoice4Teams.contains(uuid)) {
                Set<UUID> groupPlayers = new HashSet<>(getPlayersInGroupOfPlayer(player));
                Set<UUID> onlyOtherPlayersInBlueTeam = new HashSet<>(playersInBlueTeams);
                Set<UUID> BlueTeamPlayersInGroup = new HashSet<>(playersInBlueTeams);

                Set<UUID> RedTeamPlayersInGroup = new HashSet<>(playersInRedTeams);
                Set<UUID> onlyOtherPlayersInRedTeam = new HashSet<>(playersInRedTeams);

                Set<UUID> GreenTeamPlayersInGroup = new HashSet<>(playersInGreenTeams);
                Set<UUID> onlyOtherPlayersInGreenTeam = new HashSet<>(playersInGreenTeams);

                Set<UUID> CyanTeamPlayersInGroup = new HashSet<>(playersInCyanTeams);
                Set<UUID> onlyOtherPlayersInCyanTeam = new HashSet<>(playersInCyanTeams);

                onlyOtherPlayersInCyanTeam.removeAll(groupPlayers);
                CyanTeamPlayersInGroup.removeAll(onlyOtherPlayersInCyanTeam);

                onlyOtherPlayersInRedTeam.removeAll(groupPlayers);
                RedTeamPlayersInGroup.removeAll(onlyOtherPlayersInRedTeam);

                onlyOtherPlayersInGreenTeam.removeAll(groupPlayers);
                GreenTeamPlayersInGroup.removeAll(onlyOtherPlayersInGreenTeam);

                onlyOtherPlayersInBlueTeam.removeAll(groupPlayers);
                BlueTeamPlayersInGroup.removeAll(onlyOtherPlayersInBlueTeam);
                if (BlueTeamPlayersInGroup.isEmpty()) {
                    playersInBlueTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.BLUE + "" + italic + "Blue Team " + green + "" + italic + "!");
                } else if (RedTeamPlayersInGroup.isEmpty()) {
                    playersInRedTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + red + "" + italic + "Red Team " + green + "" + italic + "!");
                } else if (GreenTeamPlayersInGroup.isEmpty()) {
                    playersInGreenTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.DARK_GREEN + "" + italic + "Green Team " + green + "" + italic + "!");
                } else if (CyanTeamPlayersInGroup.isEmpty()) {
                    playersInCyanTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + aqua + "" + italic + "Cyan Team " + green + "" + italic + "!");
                }
            } else if (playersThatChoice5Teams.contains(uuid)) {
                Set<UUID> groupPlayers = new HashSet<>(getPlayersInGroupOfPlayer(player));
                Set<UUID> onlyOtherPlayersInBlueTeam = new HashSet<>(playersInBlueTeams);
                Set<UUID> BlueTeamPlayersInGroup = new HashSet<>(playersInBlueTeams);

                Set<UUID> onlyOtherPlayersInYellowTeam = new HashSet<>(playersInYellowTeams);
                Set<UUID> YellowTeamPlayersInGroup = new HashSet<>(playersInYellowTeams);

                Set<UUID> RedTeamPlayersInGroup = new HashSet<>(playersInRedTeams);
                Set<UUID> onlyOtherPlayersInRedTeam = new HashSet<>(playersInRedTeams);

                Set<UUID> GreenTeamPlayersInGroup = new HashSet<>(playersInGreenTeams);
                Set<UUID> onlyOtherPlayersInGreenTeam = new HashSet<>(playersInGreenTeams);

                Set<UUID> CyanTeamPlayersInGroup = new HashSet<>(playersInCyanTeams);
                Set<UUID> onlyOtherPlayersInCyanTeam = new HashSet<>(playersInCyanTeams);

                onlyOtherPlayersInCyanTeam.removeAll(groupPlayers);
                CyanTeamPlayersInGroup.removeAll(onlyOtherPlayersInCyanTeam);

                onlyOtherPlayersInYellowTeam.removeAll(groupPlayers);
                YellowTeamPlayersInGroup.removeAll(onlyOtherPlayersInYellowTeam);

                onlyOtherPlayersInRedTeam.removeAll(groupPlayers);
                RedTeamPlayersInGroup.removeAll(onlyOtherPlayersInRedTeam);

                onlyOtherPlayersInGreenTeam.removeAll(groupPlayers);
                GreenTeamPlayersInGroup.removeAll(onlyOtherPlayersInGreenTeam);

                onlyOtherPlayersInBlueTeam.removeAll(groupPlayers);
                BlueTeamPlayersInGroup.removeAll(onlyOtherPlayersInBlueTeam);
                if (BlueTeamPlayersInGroup.isEmpty()) {
                    playersInBlueTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.BLUE + "" + italic + "Blue Team " + green + "" + italic + "!");
                } else if (RedTeamPlayersInGroup.isEmpty()) {
                    playersInRedTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + red + "" + italic + "Red Team " + green + "" + italic + "!");
                } else if (GreenTeamPlayersInGroup.isEmpty()) {
                    playersInGreenTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.DARK_GREEN + "" + italic + "Green Team " + green + "" + italic + "!");
                } else if (CyanTeamPlayersInGroup.isEmpty()) {
                    playersInCyanTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + aqua + "" + italic + "Cyan Team " + green + "" + italic + "!");
                } else if (YellowTeamPlayersInGroup.isEmpty()) {
                    playersInYellowTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + yellow + "" + italic + "Yellow Team " + green + "" + italic + "!");
                }
            }
            playersRandomTeam.remove(uuid);
        }
    }

    public Set<UUID> getGroupPlayersInBlueTeam(Player player) {
        if (isPlayerInGroup(player)) {
            Set<UUID> BlueTeam = new HashSet<>(playersInBlueTeams);
            Set<UUID> BlueTeam1 = new HashSet<>(playersInBlueTeams);
            Set<UUID> group = new HashSet<>(getPlayersInGroupOfPlayer(player));
            BlueTeam.removeAll(group);
            BlueTeam1.removeAll(BlueTeam);
            return BlueTeam1;
        }
        return null;
    }

    public Set<UUID> getGroupPlayersInRedTeam(Player player) {
        if (isPlayerInGroup(player)) {
            Set<UUID> BlueTeam = new HashSet<>(playersInRedTeams);
            Set<UUID> BlueTeam1 = new HashSet<>(playersInRedTeams);
            Set<UUID> group = new HashSet<>(getPlayersInGroupOfPlayer(player));
            BlueTeam.removeAll(group);
            BlueTeam1.removeAll(BlueTeam);
            return BlueTeam1;
        }
        return null;
    }

    public Set<UUID> getGroupPlayersInYellowTeam(Player player) {
        if (isPlayerInGroup(player)) {
            Set<UUID> BlueTeam = new HashSet<>(playersInYellowTeams);
            Set<UUID> BlueTeam1 = new HashSet<>(playersInYellowTeams);
            Set<UUID> group = new HashSet<>(getPlayersInGroupOfPlayer(player));
            BlueTeam.removeAll(group);
            BlueTeam1.removeAll(BlueTeam);
            return BlueTeam1;
        }
        return null;
    }

    public Set<UUID> getGroupPlayersInCyanTeam(Player player) {
        if (isPlayerInGroup(player)) {
            Set<UUID> BlueTeam = new HashSet<>(playersInCyanTeams);
            Set<UUID> BlueTeam1 = new HashSet<>(playersInCyanTeams);
            Set<UUID> group = new HashSet<>(getPlayersInGroupOfPlayer(player));
            BlueTeam.removeAll(group);
            BlueTeam1.removeAll(BlueTeam);
            return BlueTeam1;
        }
        return null;
    }

    public Set<UUID> getGroupPlayersInGreenTeam(Player player) {
        if (isPlayerInGroup(player)) {
            Set<UUID> BlueTeam = new HashSet<>(playersInGreenTeams);
            Set<UUID> BlueTeam1 = new HashSet<>(playersInGreenTeams);
            Set<UUID> group = new HashSet<>(getPlayersInGroupOfPlayer(player));
            BlueTeam.removeAll(group);
            BlueTeam1.removeAll(BlueTeam);
            return BlueTeam1;
        }
        return null;
    }

    public boolean isThereEmptyTeam(Player player) {
        if (isPlayerInGroup(player)) {
            UUID uuid = player.getUniqueId();
            if (playersThatChoice2Teams.contains(uuid)) {
                // Blue Red
                return getGroupPlayersInBlueTeam(player).isEmpty() || getGroupPlayersInRedTeam(player).isEmpty();
            } else if (playersThatChoice3Teams.contains(uuid)) {
                // Blue Red Green
                return getGroupPlayersInBlueTeam(player).isEmpty() || getGroupPlayersInRedTeam(player).isEmpty() || getGroupPlayersInGreenTeam(player).isEmpty();
            } else if (playersThatChoice4Teams.contains(uuid)) {
                // Blue Red Green Cyan
                return getGroupPlayersInBlueTeam(player).isEmpty() || getGroupPlayersInRedTeam(player).isEmpty() || getGroupPlayersInGreenTeam(player).isEmpty() || getGroupPlayersInCyanTeam(player).isEmpty();
            } else if (playersThatChoice5Teams.contains(uuid)) {
                // Blue Red Green Cyan Yellow
                return getGroupPlayersInBlueTeam(player).isEmpty() || getGroupPlayersInRedTeam(player).isEmpty() || getGroupPlayersInGreenTeam(player).isEmpty() || getGroupPlayersInCyanTeam(player).isEmpty() || getGroupPlayersInYellowTeam(player).isEmpty();
            }
        }
        return false;
    }

    public boolean checkIfTheGroupIsReady(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String s;
        if (mainConfig.getStrMain("name") != null) {
            s = " " + mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        if (player != null) {
            if (isPlayerChooseToPlayPvE(player) && !isPlayerInGroup(player)) {
                // playing alone
                return ReadyPlayers.contains(player.getUniqueId());
            } else if (isPlayerChooseToPlayPvE(player) && isPlayerInGroup(player)) {
                Set<UUID> ReadyUUID = new HashSet<>();
                for (UUID uuidOfGroup : getPlayersInGroupOfPlayer(player)) {
                    if (uuidOfGroup != null) {
                        Player playerInGroup = Bukkit.getPlayer(uuidOfGroup);
                        if (playerInGroup != null && ReadyPlayers.contains(playerInGroup.getUniqueId())) {
                            ReadyUUID.add(playerInGroup.getUniqueId());
                        }
                    }
                }
                return ReadyUUID.size() == getPlayersInGroupOfPlayer(player).size();
            } else if (isPlayerInGroup(player)) {
                UUID uuid = player.getUniqueId();
                Set<UUID> ReadyUUID = new HashSet<>();
                for (UUID uuidOfGroup : getPlayersInGroupOfPlayer(player)) {
                    if (uuidOfGroup != null) {
                        Player playerInGroup = Bukkit.getPlayer(uuidOfGroup);
                        if (playerInGroup != null && ReadyPlayers.contains(playerInGroup.getUniqueId())) {
                            ReadyUUID.add(playerInGroup.getUniqueId());
                        }
                    }
                }
                if (isThereEmptyTeam(player)) {
                    Set<UUID> blueTeam = getGroupPlayersInBlueTeam(player);
                    Set<UUID> redTeam = getGroupPlayersInRedTeam(player);
                    Set<UUID> greenTeam = getGroupPlayersInGreenTeam(player);
                    Set<UUID> yellowTeam = getGroupPlayersInYellowTeam(player);
                    Set<UUID> cyanTeam = getGroupPlayersInCyanTeam(player);
                    if (playersThatChoice2Teams.contains(uuid)) {
                        if (blueTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.BLUE + "" + italic + "Blue team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (redTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + red + "" + italic + "Red team" + yellow + "" + italic + ".");
                            return false;
                        }
                    } else if (playersThatChoice3Teams.contains(uuid)) {
                        if (blueTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.BLUE + "" + italic + "Blue team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (redTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + red + "" + italic + "Red team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (greenTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.DARK_GREEN + "" + italic + "Green team" + yellow + "" + italic + ".");
                            return false;
                        }
                    } else if (playersThatChoice4Teams.contains(uuid)) {
                        if (blueTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.BLUE + "" + italic + "Blue team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (redTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + red + "" + italic + "Red team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (greenTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.DARK_GREEN + "" + italic + "Green team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (cyanTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + aqua + "" + italic + "Cyan team" + yellow + "" + italic + ".");
                            return false;
                        }
                    } else if (playersThatChoice5Teams.contains(uuid)) {
                        if (yellowTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + gold + "" + italic + "Yellow team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (blueTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.BLUE + "" + italic + "Blue team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (redTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + red + "" + italic + "Red team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (greenTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.DARK_GREEN + "" + italic + "Green team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (cyanTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + aqua + "" + italic + "Cyan team" + yellow + "" + italic + ".");
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return ReadyUUID.size() == getPlayersInGroupOfPlayer(player).size();
                }
            }
        }
        return false;
    }

    public String getGameName(Player player) {
        UUID uuid = player.getUniqueId();
        String name = null;
        for (String GameName : playersInGame.keySet()) {
            if (GameName != null) {
                Set<UUID> players = playersInGame.get(GameName);
                if (players.contains(uuid)) {
                    name = GameName;
                }
            }
        }
        return name;
    }

    public boolean isPlayerInGame(Player player) {
        UUID uuid = player.getUniqueId();
        for (String GameName : playersInGame.keySet()) {
            if (GameName != null) {
                Set<UUID> players = playersInGame.get(GameName);
                if (playersInGame.get(GameName) != null) {
                    if (players.contains(uuid)) {
                        return true;
                    }
                } else {
                    Set<UUID> empty = new HashSet<>();
                    playersInGame.put(GameName, empty);
                }
            }
        }
        return false;
    }

    public synchronized void removePlayerFromGame(Player player) {
        String name = getGameName(player);
        if (name != null) {
            Set<UUID> players = playersInGame.get(name);
            if (players == null || players.isEmpty()) {
                playersInGame.remove(name);
            }
            if (players != null) {
                players.remove(player.getUniqueId());
                tpPlayersInGameNameLoc.remove(player.getUniqueId());
            }
        }
    }

    public Set<UUID> getAllPlayersInGame() {
        Set<UUID> all = new HashSet<>();
        for (String name : playersInGame.keySet()) {
            if (name != null && !playersInGame.get(name).isEmpty()) {
                all.addAll(new HashSet<>(playersInGame.get(name)));
            }
        }
        return all;
    }

    public synchronized void removeAllPlayerInvites(Player player) {
        if (player != null) {
            UUID uuid = player.getUniqueId();
            Set<UUID> inviters = invites.get(uuid);
            if (inviters != null && !inviters.isEmpty()) {
                inviters.clear();
                invites.put(uuid, inviters);
            }
            for (UUID uuidKey : invites.keySet()) {
                if (uuidKey != null) {
                    Set<UUID> uuidList = invites.get(uuidKey);
                    if (uuidList != null && !uuidList.isEmpty()) {
                        uuidList.remove(uuid);
                        invites.put(uuidKey, uuidList);
                    }
                }
            }
        }
    }

    public Set<String> getWaitLobbyNames() {
        return playersInWaitingLobby.keySet();
    }

    public void giveThePlayerArmor(Player player) {
        if (player == null) {
            return;
        }
        for (UUID uuid : getPlayersInGroupOfPlayer(player)) {
            if (uuid != null) {
                Player player1 = Bukkit.getPlayer(uuid);
                if (player1 != null) {
                    PlayerInventory inventory = player1.getInventory();
                    // Hat
                    if (!inventory.contains(ItemManager.BlueHat) || !inventory.contains(ItemManager.RedHat) || !inventory.contains(ItemManager.YellowHat) || !inventory.contains(ItemManager.CyanHat) || !inventory.contains(ItemManager.GreenHat)) {
                        if (playersInBlueTeams.contains(uuid)) {
                            inventory.setHelmet(ItemManager.BlueHat);
                        } else if (playersInRedTeams.contains(uuid)) {
                            inventory.setHelmet(ItemManager.RedHat);
                        } else if (playersInYellowTeams.contains(uuid)) {
                            inventory.setHelmet(ItemManager.YellowHat);
                        } else if (playersInCyanTeams.contains(uuid)) {
                            inventory.setHelmet(ItemManager.CyanHat);
                        } else if (playersInGreenTeams.contains(uuid)) {
                            inventory.setHelmet(ItemManager.GreenHat);
                        }
                    }
                    // Chestplate
                    if (!inventory.contains(ItemManager.BlueChest) || !inventory.contains(ItemManager.RedChest) || !inventory.contains(ItemManager.YellowChest) || !inventory.contains(ItemManager.CyanChest) || !inventory.contains(ItemManager.GreenChest)) {
                        if (playersInBlueTeams.contains(uuid)) {
                            inventory.setChestplate(ItemManager.BlueChest);
                        } else if (playersInRedTeams.contains(uuid)) {
                            inventory.setChestplate(ItemManager.RedChest);
                        } else if (playersInYellowTeams.contains(uuid)) {
                            inventory.setChestplate(ItemManager.YellowChest);
                        } else if (playersInCyanTeams.contains(uuid)) {
                            inventory.setChestplate(ItemManager.CyanChest);
                        } else if (playersInGreenTeams.contains(uuid)) {
                            inventory.setChestplate(ItemManager.GreenChest);
                        }
                    }
                    // Legs
                    if (!inventory.contains(ItemManager.BlueLeg) || !inventory.contains(ItemManager.RedLeg) || !inventory.contains(ItemManager.YellowLeg) || !inventory.contains(ItemManager.CyanLeg) || !inventory.contains(ItemManager.GreenLeg)) {
                        if (playersInBlueTeams.contains(uuid)) {
                            inventory.setLeggings(ItemManager.BlueLeg);
                        } else if (playersInRedTeams.contains(uuid)) {
                            inventory.setLeggings(ItemManager.RedLeg);
                        } else if (playersInYellowTeams.contains(uuid)) {
                            inventory.setLeggings(ItemManager.YellowLeg);
                        } else if (playersInCyanTeams.contains(uuid)) {
                            inventory.setLeggings(ItemManager.CyanLeg);
                        } else if (playersInGreenTeams.contains(uuid)) {
                            inventory.setLeggings(ItemManager.GreenLeg);
                        }
                    }
                    // Boots
                    if (!inventory.contains(ItemManager.BlueBoots) || !inventory.contains(ItemManager.RedBoots) || !inventory.contains(ItemManager.YellowBoots) || !inventory.contains(ItemManager.CyanBoots) || !inventory.contains(ItemManager.GreenBoots)) {
                        if (playersInBlueTeams.contains(uuid)) {
                            inventory.setBoots(ItemManager.BlueBoots);
                        } else if (playersInRedTeams.contains(uuid)) {
                            inventory.setBoots(ItemManager.RedBoots);
                        } else if (playersInYellowTeams.contains(uuid)) {
                            inventory.setBoots(ItemManager.YellowBoots);
                        } else if (playersInCyanTeams.contains(uuid)) {
                            inventory.setBoots(ItemManager.CyanBoots);
                        } else if (playersInGreenTeams.contains(uuid)) {
                            inventory.setBoots(ItemManager.GreenBoots);
                        }
                    }
                }
            }
        }
    }
}
