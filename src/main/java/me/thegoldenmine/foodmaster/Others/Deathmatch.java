package me.thegoldenmine.foodmaster.Others;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Deathmatch {
    private final FoodMaster plugin;

    public Deathmatch(FoodMaster main) {
        plugin = main;
    }

    public boolean isPlayerChooseToPlayFoodWars(Player player) {
        UUID uuid = player.getUniqueId();
        boolean Teams2 = !plugin.playersThatChoice2Teams.isEmpty() && plugin.playersThatChoice2Teams.contains(uuid);
        boolean Teams3 = !plugin.playersThatChoice3Teams.isEmpty() && plugin.playersThatChoice3Teams.contains(uuid);
        boolean Teams4 = !plugin.playersThatChoice4Teams.isEmpty() && plugin.playersThatChoice4Teams.contains(uuid);
        boolean Teams5 = !plugin.playersThatChoice5Teams.isEmpty() && plugin.playersThatChoice5Teams.contains(uuid);
        return Teams2 || Teams3 || Teams4 || Teams5;
    }

    public boolean isPlayerHaveStartedTeams(Player player) {
        UUID uuid = player.getUniqueId();
        if (plugin.playersThatChoice2Teams.contains(uuid)) {
            return true;
        } else if (plugin.playersThatChoice3Teams.contains(uuid)) {
            return true;
        } else if (plugin.playersThatChoice4Teams.contains(uuid)) {
            return true;
        } else return plugin.playersThatChoice5Teams.contains(uuid);
    }

    public boolean isPlayerInTeams(Player player) {
        UUID uuid = player.getUniqueId();
        if (plugin.playersInBlueTeams.contains(uuid)) {
            return true;
        } else if (plugin.playersInRedTeams.contains(uuid)) {
            return true;
        } else if (plugin.playersInCyanTeams.contains(uuid)) {
            return true;
        } else if (plugin.playersInYellowTeams.contains(uuid)) {
            return true;
        } else return plugin.playersInGreenTeams.remove(uuid);
    }

    public boolean isPlayerPlayingFoodWars(Player player) {
        return plugin.players2Teams.contains(player.getUniqueId())
                || plugin.players3Teams.contains(player.getUniqueId())
                || plugin.players4Teams.contains(player.getUniqueId())
                || plugin.players5Teams.contains(player.getUniqueId());
    }

    public Set<String> getAliveTeams(Player player) {
        Set<String> aliveTeams = new HashSet<>();
        if (player != null && plugin.playerGroup.isPlayerInGroup(player) && isPlayerPlayingFoodWars(player)) {
            String gameName = plugin.game.getGameName(player);
            if (gameName != null && plugin.mainConfig.getBooleanGame("enable_lives_team_deathmatch")) {
                for (UUID uuid : plugin.stillAlive.get(gameName)) {
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
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        UUID uuid = player.getUniqueId();
        if (plugin.playersRandomTeam.contains(uuid)) {
            if (plugin.playersThatChoice2Teams.contains(uuid)) {
                Set<UUID> groupPlayers = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
                Set<UUID> onlyOtherPlayersInBlueTeam = new HashSet<>(plugin.playersInBlueTeams);
                Set<UUID> BlueTeamPlayersInGroup = new HashSet<>(plugin.playersInBlueTeams);

                Set<UUID> RedTeamPlayersInGroup = new HashSet<>(plugin.playersInRedTeams);
                Set<UUID> onlyOtherPlayersInRedTeam = new HashSet<>(plugin.playersInRedTeams);

                onlyOtherPlayersInRedTeam.removeAll(groupPlayers);
                RedTeamPlayersInGroup.removeAll(onlyOtherPlayersInRedTeam);

                onlyOtherPlayersInBlueTeam.removeAll(groupPlayers);
                BlueTeamPlayersInGroup.removeAll(onlyOtherPlayersInBlueTeam);
                if (BlueTeamPlayersInGroup.isEmpty()) {
                    plugin.playersInBlueTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.BLUE + "" + italic + "Blue Team " + green + "" + italic + "!");
                } else if (RedTeamPlayersInGroup.isEmpty()) {
                    plugin.playersInRedTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + red + "" + italic + "Red Team " + green + "" + italic + "!");
                }
            } else if (plugin.playersThatChoice3Teams.contains(uuid)) {
                Set<UUID> groupPlayers = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
                Set<UUID> onlyOtherPlayersInBlueTeam = new HashSet<>(plugin.playersInBlueTeams);
                Set<UUID> BlueTeamPlayersInGroup = new HashSet<>(plugin.playersInBlueTeams);

                Set<UUID> RedTeamPlayersInGroup = new HashSet<>(plugin.playersInRedTeams);
                Set<UUID> onlyOtherPlayersInRedTeam = new HashSet<>(plugin.playersInRedTeams);

                Set<UUID> GreenTeamPlayersInGroup = new HashSet<>(plugin.playersInGreenTeams);
                Set<UUID> onlyOtherPlayersInGreenTeam = new HashSet<>(plugin.playersInGreenTeams);

                onlyOtherPlayersInRedTeam.removeAll(groupPlayers);
                RedTeamPlayersInGroup.removeAll(onlyOtherPlayersInRedTeam);

                onlyOtherPlayersInGreenTeam.removeAll(groupPlayers);
                GreenTeamPlayersInGroup.removeAll(onlyOtherPlayersInGreenTeam);

                onlyOtherPlayersInBlueTeam.removeAll(groupPlayers);
                BlueTeamPlayersInGroup.removeAll(onlyOtherPlayersInBlueTeam);
                if (BlueTeamPlayersInGroup.isEmpty()) {
                    plugin.playersInBlueTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.BLUE + "" + italic + "Blue Team " + green + "" + italic + "!");
                } else if (RedTeamPlayersInGroup.isEmpty()) {
                    plugin.playersInRedTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + red + "" + italic + "Red Team " + green + "" + italic + "!");
                } else if (GreenTeamPlayersInGroup.isEmpty()) {
                    plugin.playersInGreenTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.DARK_GREEN + "" + italic + "Green Team " + green + "" + italic + "!");
                }
            } else if (plugin.playersThatChoice4Teams.contains(uuid)) {
                Set<UUID> groupPlayers = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
                Set<UUID> onlyOtherPlayersInBlueTeam = new HashSet<>(plugin.playersInBlueTeams);
                Set<UUID> BlueTeamPlayersInGroup = new HashSet<>(plugin.playersInBlueTeams);

                Set<UUID> RedTeamPlayersInGroup = new HashSet<>(plugin.playersInRedTeams);
                Set<UUID> onlyOtherPlayersInRedTeam = new HashSet<>(plugin.playersInRedTeams);

                Set<UUID> GreenTeamPlayersInGroup = new HashSet<>(plugin.playersInGreenTeams);
                Set<UUID> onlyOtherPlayersInGreenTeam = new HashSet<>(plugin.playersInGreenTeams);

                Set<UUID> CyanTeamPlayersInGroup = new HashSet<>(plugin.playersInCyanTeams);
                Set<UUID> onlyOtherPlayersInCyanTeam = new HashSet<>(plugin.playersInCyanTeams);

                onlyOtherPlayersInCyanTeam.removeAll(groupPlayers);
                CyanTeamPlayersInGroup.removeAll(onlyOtherPlayersInCyanTeam);

                onlyOtherPlayersInRedTeam.removeAll(groupPlayers);
                RedTeamPlayersInGroup.removeAll(onlyOtherPlayersInRedTeam);

                onlyOtherPlayersInGreenTeam.removeAll(groupPlayers);
                GreenTeamPlayersInGroup.removeAll(onlyOtherPlayersInGreenTeam);

                onlyOtherPlayersInBlueTeam.removeAll(groupPlayers);
                BlueTeamPlayersInGroup.removeAll(onlyOtherPlayersInBlueTeam);
                if (BlueTeamPlayersInGroup.isEmpty()) {
                    plugin.playersInBlueTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.BLUE + "" + italic + "Blue Team " + green + "" + italic + "!");
                } else if (RedTeamPlayersInGroup.isEmpty()) {
                    plugin.playersInRedTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + red + "" + italic + "Red Team " + green + "" + italic + "!");
                } else if (GreenTeamPlayersInGroup.isEmpty()) {
                    plugin.playersInGreenTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.DARK_GREEN + "" + italic + "Green Team " + green + "" + italic + "!");
                } else if (CyanTeamPlayersInGroup.isEmpty()) {
                    plugin.playersInCyanTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + aqua + "" + italic + "Cyan Team " + green + "" + italic + "!");
                }
            } else if (plugin.playersThatChoice5Teams.contains(uuid)) {
                Set<UUID> groupPlayers = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
                Set<UUID> onlyOtherPlayersInBlueTeam = new HashSet<>(plugin.playersInBlueTeams);
                Set<UUID> BlueTeamPlayersInGroup = new HashSet<>(plugin.playersInBlueTeams);

                Set<UUID> onlyOtherPlayersInYellowTeam = new HashSet<>(plugin.playersInYellowTeams);
                Set<UUID> YellowTeamPlayersInGroup = new HashSet<>(plugin.playersInYellowTeams);

                Set<UUID> RedTeamPlayersInGroup = new HashSet<>(plugin.playersInRedTeams);
                Set<UUID> onlyOtherPlayersInRedTeam = new HashSet<>(plugin.playersInRedTeams);

                Set<UUID> GreenTeamPlayersInGroup = new HashSet<>(plugin.playersInGreenTeams);
                Set<UUID> onlyOtherPlayersInGreenTeam = new HashSet<>(plugin.playersInGreenTeams);

                Set<UUID> CyanTeamPlayersInGroup = new HashSet<>(plugin.playersInCyanTeams);
                Set<UUID> onlyOtherPlayersInCyanTeam = new HashSet<>(plugin.playersInCyanTeams);

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
                    plugin.playersInBlueTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.BLUE + "" + italic + "Blue Team " + green + "" + italic + "!");
                } else if (RedTeamPlayersInGroup.isEmpty()) {
                    plugin.playersInRedTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + red + "" + italic + "Red Team " + green + "" + italic + "!");
                } else if (GreenTeamPlayersInGroup.isEmpty()) {
                    plugin.playersInGreenTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + ChatColor.DARK_GREEN + "" + italic + "Green Team " + green + "" + italic + "!");
                } else if (CyanTeamPlayersInGroup.isEmpty()) {
                    plugin.playersInCyanTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + aqua + "" + italic + "Cyan Team " + green + "" + italic + "!");
                } else if (YellowTeamPlayersInGroup.isEmpty()) {
                    plugin.playersInYellowTeams.add(uuid);
                    player.sendMessage(NORMAL + "You are on the " + yellow + "" + italic + "Yellow Team " + green + "" + italic + "!");
                }
            }
            plugin.playersRandomTeam.remove(uuid);
        }
    }

    public Set<UUID> getGroupPlayersInBlueTeam(Player player) {
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            Set<UUID> BlueTeam = new HashSet<>(plugin.playersInBlueTeams);
            Set<UUID> BlueTeam1 = new HashSet<>(plugin.playersInBlueTeams);
            Set<UUID> group = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
            BlueTeam.removeAll(group);
            BlueTeam1.removeAll(BlueTeam);
            return BlueTeam1;
        }
        return null;
    }

    public Set<UUID> getGroupPlayersInRedTeam(Player player) {
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            Set<UUID> BlueTeam = new HashSet<>(plugin.playersInRedTeams);
            Set<UUID> BlueTeam1 = new HashSet<>(plugin.playersInRedTeams);
            Set<UUID> group = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
            BlueTeam.removeAll(group);
            BlueTeam1.removeAll(BlueTeam);
            return BlueTeam1;
        }
        return null;
    }

    public Set<UUID> getGroupPlayersInYellowTeam(Player player) {
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            Set<UUID> BlueTeam = new HashSet<>(plugin.playersInYellowTeams);
            Set<UUID> BlueTeam1 = new HashSet<>(plugin.playersInYellowTeams);
            Set<UUID> group = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
            BlueTeam.removeAll(group);
            BlueTeam1.removeAll(BlueTeam);
            return BlueTeam1;
        }
        return null;
    }

    public Set<UUID> getGroupPlayersInCyanTeam(Player player) {
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            Set<UUID> BlueTeam = new HashSet<>(plugin.playersInCyanTeams);
            Set<UUID> BlueTeam1 = new HashSet<>(plugin.playersInCyanTeams);
            Set<UUID> group = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
            BlueTeam.removeAll(group);
            BlueTeam1.removeAll(BlueTeam);
            return BlueTeam1;
        }
        return null;
    }

    public Set<UUID> getGroupPlayersInGreenTeam(Player player) {
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            Set<UUID> BlueTeam = new HashSet<>(plugin.playersInGreenTeams);
            Set<UUID> BlueTeam1 = new HashSet<>(plugin.playersInGreenTeams);
            Set<UUID> group = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
            BlueTeam.removeAll(group);
            BlueTeam1.removeAll(BlueTeam);
            return BlueTeam1;
        }
        return null;
    }

    public boolean isThereEmptyTeam(Player player) {
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            UUID uuid = player.getUniqueId();
            if (plugin.playersThatChoice2Teams.contains(uuid)) {
                // Blue Red
                return getGroupPlayersInBlueTeam(player).isEmpty() || getGroupPlayersInRedTeam(player).isEmpty();
            } else if (plugin.playersThatChoice3Teams.contains(uuid)) {
                // Blue Red Green
                return getGroupPlayersInBlueTeam(player).isEmpty() || getGroupPlayersInRedTeam(player).isEmpty() || getGroupPlayersInGreenTeam(player).isEmpty();
            } else if (plugin.playersThatChoice4Teams.contains(uuid)) {
                // Blue Red Green Cyan
                return getGroupPlayersInBlueTeam(player).isEmpty() || getGroupPlayersInRedTeam(player).isEmpty() || getGroupPlayersInGreenTeam(player).isEmpty() || getGroupPlayersInCyanTeam(player).isEmpty();
            } else if (plugin.playersThatChoice5Teams.contains(uuid)) {
                // Blue Red Green Cyan Yellow
                return getGroupPlayersInBlueTeam(player).isEmpty() || getGroupPlayersInRedTeam(player).isEmpty() || getGroupPlayersInGreenTeam(player).isEmpty() || getGroupPlayersInCyanTeam(player).isEmpty() || getGroupPlayersInYellowTeam(player).isEmpty();
            }
        }
        return false;
    }

    public String getPlayerTeam(Player player) {
        if (plugin.deathmatch.isPlayerPlayingFoodWars(player)) {
            UUID uuid = player.getUniqueId();
            if (plugin.deathmatch.getGroupPlayersInRedTeam(player) != null && !plugin.deathmatch.getGroupPlayersInRedTeam(player).isEmpty() && plugin.deathmatch.getGroupPlayersInRedTeam(player).contains(uuid)) {
                return "red";
            } else if (plugin.deathmatch.getGroupPlayersInBlueTeam(player) != null && !plugin.deathmatch.getGroupPlayersInBlueTeam(player).isEmpty() && plugin.deathmatch.getGroupPlayersInBlueTeam(player).contains(uuid)) {
                return "blue";
            } else if (plugin.deathmatch.getGroupPlayersInCyanTeam(player) != null && !plugin.deathmatch.getGroupPlayersInCyanTeam(player).isEmpty() && plugin.deathmatch.getGroupPlayersInCyanTeam(player).contains(uuid)) {
                return "cyan";
            } else if (plugin.deathmatch.getGroupPlayersInGreenTeam(player) != null && !plugin.deathmatch.getGroupPlayersInGreenTeam(player).isEmpty() && plugin.deathmatch.getGroupPlayersInGreenTeam(player).contains(uuid)) {
                return "green";
            } else if (plugin.deathmatch.getGroupPlayersInYellowTeam(player) != null && !plugin.deathmatch.getGroupPlayersInYellowTeam(player).isEmpty() && plugin.deathmatch.getGroupPlayersInYellowTeam(player).contains(uuid)) {
                return "yellow";
            }
        }
        return "";
    }
}
