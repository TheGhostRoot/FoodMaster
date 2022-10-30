package me.thegoldenmine.foodmaster.command;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import static me.thegoldenmine.foodmaster.command.Messenger.*;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MainCommand implements CommandExecutor {
	private final FoodMaster plugin;

	public MainCommand(FoodMaster plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}

		Messenger messenger = plugin.getMessenger();

		// the sender is Player
		Player player = (Player) sender;
		String s = "You don't have ";

		if (!player.hasPermission("foodm.commands")) {
			messenger.warn(player,
					s + ERROR_GENERAL + "foodm.commands " + WARN_GENERAL + "permission to play this game.");
			return true;
		}

		// the player have permission to play the game
		String s1 = "permission.";
		String message1 = s + ERROR_GENERAL + "foodm.help " + WARN_GENERAL + s1;
		if (args.length >= 1 && args[0].equalsIgnoreCase("help")) {
			// Help menu
			if (player.hasPermission("foodm.help")) {
				plugin.helpMenu.helpAll(player);
			} else {
				messenger.warn(player, message1);
			}
		} else if (args.length >= 2 && args[0].equalsIgnoreCase("group") && args[1].equalsIgnoreCase("help")) {
			if (player.hasPermission("foodm.group.help")) {
				plugin.helpMenu.helpGroupMenu(player);
			} else {
				messenger.warn(player, s + ERROR_GENERAL + "foodm.group.help " + WARN_GENERAL + s1);
			}
		} else if (args.length >= 1 && args[0].equalsIgnoreCase("gui")) {
			if (player.hasPermission("foodm.gui")) {
				player.openInventory(plugin.createGUI.createMain(player));
			} else {
				messenger.warn(player, s + ERROR_GENERAL + "foodm.gui " + WARN_GENERAL + s1);
			}
		} else if (args.length >= 1 && args[0].equalsIgnoreCase("stats")) {
			if (player.hasPermission("foodm.stats")) {
				// /fm stats <player name>
				// 0 1 index
				// 1 2
				UUID uuid = player.getUniqueId();
				String s2 = "  Deaths: ";
				String s3 = "  Kills: ";
				String s4 = "   K/D: ";
				String message = INFO_STYLE + "<--=={ " + MAIN_GENERAL + "Game Stats " + INFO_STYLE
						+ "}==-->";
				if (args.length > 1) {
					String playerName = args[1];
					if (playerName != null) {
						Player givenPlayer = Bukkit.getPlayer(playerName);
						if (givenPlayer != null) {
							UUID uuid1 = givenPlayer.getUniqueId();
							String theUUID = String.valueOf(uuid1);
							String kills12 = String.valueOf(plugin.mainConfig.getIntKill(theUUID));
							String death12 = String.valueOf(plugin.mainConfig.getIntDeath(theUUID));
							int deaths11 = plugin.mainConfig.getIntDeath(theUUID);
							int kills11 = plugin.mainConfig.getIntKill(theUUID);
							float KD11 = (float) kills11 / deaths11;
							String[] list11 = String.valueOf(KD11).split("");
							String first11 = list11[0];
							String middle11 = list11[1];
							String last11 = list11[2];
							String finalString11 = first11 + middle11 + last11;
							messenger.normal(player,
									INFO_STYLE + "<--=={ " + MAIN_GENERAL + "" + givenPlayer.getName()
											+ "'s" + MAIN_STYLE + " Stats " + INFO_STYLE + "}==-->");
							messenger.normal(player, ERROR_GENERAL + s3 + kills12);
							messenger.normal(player, ERROR_GENERAL + s2 + death12);
							messenger.normal(player, NORMAL_GENERAL_2 + s4 + finalString11);
							String win = String.valueOf(plugin.mainConfig.getIntWin(theUUID));
							String lose = String.valueOf(plugin.mainConfig.getIntLose(theUUID));
							int win1 = plugin.mainConfig.getIntWin(theUUID);
							int lose1 = plugin.mainConfig.getIntLose(theUUID);
							float WL = (float) win1 / lose1;
							String[] list = String.valueOf(WL).split("");
							String one = list[0];
							String two = list[1];
							String three = list[2];
							String wl = one + two + three;
							messenger.normal(player, "");
							messenger.normal(player, NORMAL_GENERAL + "  Wins: " + win);
							messenger.normal(player, ERROR_GENERAL + "  Losses: " + lose);
							messenger.normal(player, NORMAL_GENERAL_2 + "   W/L: " + wl);
							if (plugin.game.isPlayerInGame(givenPlayer)) {
								if (!plugin.inGameKills.containsKey(uuid1)) {
									plugin.inGameKills.put(uuid1, 0);
								}
								if (!plugin.inGameDeaths.containsKey(uuid1)) {
									plugin.inGameDeaths.put(uuid1, 0);
								}
								String kills = String.valueOf(plugin.inGameKills.get(uuid1));
								String death = String.valueOf(plugin.inGameDeaths.get(uuid1));
								int deaths1 = plugin.inGameDeaths.get(uuid1);
								int kills1 = plugin.inGameKills.get(uuid1);
								float KD1 = (float) kills1 / deaths1;
								String[] list1 = String.valueOf(KD1).split("");
								String first1 = list1[0];
								String middle1 = list1[1];
								String last1 = list1[2];
								String finalString1 = first1 + middle1 + last1;
								messenger.normal(player, message);
								messenger.normal(player, ERROR_GENERAL + s3 + kills);
								messenger.normal(player, ERROR_GENERAL + s2 + death);
								messenger.normal(player, NORMAL_GENERAL_2 + s4 + finalString1);
							}
							messenger.normal(player, INFO_STYLE + "<--====-----====-->");
						}
					} else {
						messenger.error(player, "This player is not found.");
					}
				} else {
					String theUUID = String.valueOf(uuid);
					String kills12 = String.valueOf(plugin.mainConfig.getIntKill(theUUID));
					String death12 = String.valueOf(plugin.mainConfig.getIntDeath(theUUID));
					int deaths11 = plugin.mainConfig.getIntDeath(theUUID);
					int kills11 = plugin.mainConfig.getIntKill(theUUID);
					float KD11 = (float) kills11 / deaths11;
					String[] list11 = String.valueOf(KD11).split("");
					String first11 = list11[0];
					String middle11 = list11[1];
					String last11 = list11[2];
					String finalString11 = first11 + middle11 + last11;
					messenger.normal(player, INFO_STYLE + "<--=={ " + MAIN_STYLE + "Your Stats " + INFO_STYLE + "}==-->");
					messenger.normal(player, ERROR_GENERAL + s3 + kills12);
					messenger.normal(player, ERROR_GENERAL + s2 + death12);
					messenger.normal(player, NORMAL_GENERAL_2 + s4 + finalString11);
					String win = String.valueOf(plugin.mainConfig.getIntWin(theUUID));
					String lose = String.valueOf(plugin.mainConfig.getIntLose(theUUID));
					int win1 = plugin.mainConfig.getIntWin(theUUID);
					int lose1 = plugin.mainConfig.getIntLose(theUUID);
					float WL = (float) win1 / lose1;
					String[] listw = String.valueOf(WL).split("");
					String one = listw[0];
					String two = listw[1];
					String three = listw[2];
					String wl = one + two + three;
					messenger.normal(player, "");
					messenger.normal(player, NORMAL_GENERAL + "  Wins: " + win);
					messenger.normal(player, ERROR_GENERAL + "  Losses: " + lose);
					messenger.normal(player, NORMAL_GENERAL_2 + "   W/L: " + wl);
					if (plugin.game.isPlayerInGame(player)) {
						if (!plugin.inGameKills.containsKey(uuid)) {
							plugin.inGameKills.put(uuid, 0);
						}
						if (!plugin.inGameDeaths.containsKey(uuid)) {
							plugin.inGameDeaths.put(uuid, 0);
						}
						String kills = String.valueOf(plugin.inGameKills.get(uuid));
						String death = String.valueOf(plugin.inGameDeaths.get(uuid));
						int deaths1 = plugin.inGameDeaths.get(uuid);
						int kills1 = plugin.inGameKills.get(uuid);
						float KD1 = (float) kills1 / deaths1;
						String[] list1 = String.valueOf(KD1).split("");
						String first1 = list1[0];
						String middle1 = list1[1];
						String last1 = list1[2];
						String finalString1 = first1 + middle1 + last1;
						messenger.normal(player, message);
						messenger.normal(player, ERROR_GENERAL + s3 + kills);
						messenger.normal(player, ERROR_GENERAL + s2 + death);
						messenger.normal(player, NORMAL_GENERAL_2 + s4 + finalString1);
					}
					messenger.normal(player, INFO_STYLE + "<--====-----====-->");
				}
			} else {
				messenger.warn(player, s + ERROR_GENERAL + "foodm.stats " + WARN_GENERAL + s1);
			}
		} else if (args.length >= 1 && args[0].equalsIgnoreCase("set")) {
			// Sets the values of the minigame
			if (player.hasPermission("foodm.set")) {
				// /fm set idk
				// 1 2
				if (args.length > 1) {
					plugin.setSubCommand.setGameOptions(args, player);
				} else {
					plugin.helpMenu.helpAll(player);
				}
			} else {
				messenger.warn(player, s + ERROR_GENERAL + "foodm.set " + WARN_GENERAL + s1);
			}
		} else if (args.length >= 2 && args[0].equalsIgnoreCase("default") && args[1].equalsIgnoreCase("confirm")) {
			// Sets the config to default
			if (player.hasPermission("foodm.default")) {
				plugin.mainConfig.setDefaultValues();
				messenger.normal(player, "The config values have been set to default.");
			} else {
				messenger.warn(player, "You need " + ERROR_GENERAL + "foodm.default " + WARN_GENERAL
						+ "permission to do this.");
			}
		} else if (args.length >= 1 && args[0].equalsIgnoreCase("start")) {
			// It starts the FIGHT!
			if (player.hasPermission("foodm.start")) {
				if (args.length > 1) {
					plugin.startCommand.mainStart(player, args);
				} else {
					plugin.helpMenu.helpAll(player);
				}
			} else {
				messenger.warn(player, "You are not the chosen one. You don't have " + ERROR_GENERAL
						+ "foodm.start " + WARN_GENERAL + s1);
			}
		} else if (args.length >= 1 && args[0].equalsIgnoreCase("end")) {
			// It ends the fight
			if (player.hasPermission("foodm.end")) {
				// /fm end optional: player
				// 1 2 num
				// 0 1 index
				Location endLoc = plugin.mainConfig.getLocationMain("end_location");
				if (args.length > 1) {
					Player player1 = Bukkit.getPlayer(args[1]);
					if (player1 != null) {
						if (player1.isOnline()) {
							if (plugin.playerPvE.isPlayerPlayingPvE(player1)) {
								if (plugin.playerGroup.isPlayerInGroup(player1)) {
									for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player1)) {
										if (uuid != null) {
											Player player2 = Bukkit.getPlayer(uuid);
											if (player2 != null) {
												messenger.info(player2, "The game was ended by " + MAIN_GENERAL
														+ "" + player.getName());
											}
										}
									}
								} else {
									messenger.info(player1,
											"The game was ended by " + MAIN_GENERAL + "" + player.getName());
								}
								plugin.endTheGame.endThePvE(player1);
							} else if (plugin.playerPvE.isPlayerChooseToPlayPvE(player1)) {
								if (plugin.playerGroup.isPlayerInGroup(player)) {
									for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
										if (uuid != null) {
											Player players = Bukkit.getPlayer(uuid);
											if (players != null) {
												plugin.waitingLobby.removePlayerFromWaitedLobby(players);
												if (endLoc != null) {
													players.teleport(endLoc);
													messenger.info(players, "You are removed from the waiting lobby");
												}
											}
										}
									}
								} else {
									plugin.waitingLobby.removePlayerFromWaitedLobby(player);
									if (endLoc != null) {
										player.teleport(endLoc);
										messenger.info(player, "You are removed from the waiting lobby");
									}
								}
							} else {
								if (plugin.playerGroup.isPlayerInGroup(player1)) {
									if (plugin.game.isPlayerInGame(player1)) {
										for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player1)) {
											if (uuid != null) {
												Player players = Bukkit.getPlayer(uuid);
												if (players != null) {
													plugin.endTheGame.endTheGameWithStatus(players);
												}
											}
										}
										for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player1)) {
											if (uuid != null) {
												plugin.inGameKills.remove(uuid);
												plugin.inGameDeaths.remove(uuid);
												plugin.playersInBlueTeams.remove(uuid);
												plugin.playersInCyanTeams.remove(uuid);
												plugin.playersInRedTeams.remove(uuid);
												plugin.playersInGreenTeams.remove(uuid);
												plugin.playersInYellowTeams.remove(uuid);
												plugin.playersRandomTeam.remove(uuid);
												plugin.playersChoiceFoodGame.remove(uuid);
												plugin.playersInFoodGame.remove(uuid);
												plugin.FoodGameWinner.remove(uuid);
											}
										}
										for (UUID uuid : plugin.winners) {
											if (uuid != null) {
												Player player11 = Bukkit.getPlayer(uuid);
												if (player11 != null) {
													plugin.giveOneWinToPlayer.givePlayerWin(player11);
												}
											}
										}
										for (UUID uuid : plugin.losses) {
											if (uuid != null) {
												Player player11 = Bukkit.getPlayer(uuid);
												if (player11 != null) {
													plugin.giveOneLoseToPlayer.givePlayerLose(player11);
												}
											}
										}
										plugin.winners.clear();
										plugin.losses.clear();
										messenger.normal(player, "" + MAIN_GENERAL + "" + player.getName() + ""
												+ NORMAL_GENERAL + " just ended your game.");
									} else {
										messenger.warn(player, "" + ERROR_GENERAL + "" + player1.getName() + ""
												+ WARN_GENERAL + " is not in game.");
									}
								} else {
									messenger.warn(player, "" + ERROR_GENERAL + "" + player1.getName() + "" + WARN_GENERAL + " is not in a group.");
								}
							}
						} else {
							messenger.warn(player, "" + ERROR_GENERAL + "" + player1.getName() + "" + WARN_GENERAL + " is not Online.");
						}
					} else {
						messenger.error(player, "This player can't be found.");
					}
				} else {
					if (plugin.playerPvE.isPlayerPlayingPvE(player)) {
						if (plugin.playerGroup.isPlayerInGroup(player)) {
							for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
								if (uuid != null) {
									Player player2 = Bukkit.getPlayer(uuid);
									if (player2 != null) {
										messenger.info(player2,
												"The game was ended by " + MAIN_GENERAL + "" + player.getName());
									}
								}
							}
						} else {
							messenger.info(player,
									"The game was ended by " + MAIN_GENERAL + "" + player.getName());
						}
						plugin.endTheGame.endThePvE(player);
					} else if (plugin.playerPvE.isPlayerChooseToPlayPvE(player)) {
						if (plugin.playerGroup.isPlayerInGroup(player)) {
							for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
								if (uuid != null) {
									Player players = Bukkit.getPlayer(uuid);
									if (players != null) {
										plugin.waitingLobby.removePlayerFromWaitedLobby(players);
										if (endLoc != null) {
											players.teleport(endLoc);
											messenger.info(players, "You are removed from the waiting lobby");
										}
									}
								}
							}
						} else {
							plugin.waitingLobby.removePlayerFromWaitedLobby(player);
							if (endLoc != null) {
								player.teleport(endLoc);
								messenger.info(player, "You are removed from the waiting lobby");
							}
						}
					} else {
						if (plugin.playerGroup.isPlayerInGroup(player)) {
							for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
								if (uuid != null) {
									Player players = Bukkit.getPlayer(uuid);
									if (players != null) {
										plugin.endTheGame.endTheGameWithStatus(players);
									}
								}
							}
							for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
								if (uuid != null) {
									plugin.inGameKills.remove(uuid);
									plugin.inGameDeaths.remove(uuid);
									plugin.playersInBlueTeams.remove(uuid);
									plugin.playersInCyanTeams.remove(uuid);
									plugin.playersInRedTeams.remove(uuid);
									plugin.playersInGreenTeams.remove(uuid);
									plugin.playersInYellowTeams.remove(uuid);
									plugin.playersRandomTeam.remove(uuid);
									plugin.playersChoiceFoodGame.remove(uuid);
									plugin.playersInFoodGame.remove(uuid);
									plugin.FoodGameWinner.remove(uuid);
								}
							}
							for (UUID uuid : plugin.winners) {
								if (uuid != null) {
									Player player11 = Bukkit.getPlayer(uuid);
									if (player11 != null) {
										plugin.giveOneWinToPlayer.givePlayerWin(player11);
									}
								}
							}
							for (UUID uuid : plugin.losses) {
								if (uuid != null) {
									Player player11 = Bukkit.getPlayer(uuid);
									if (player11 != null) {
										plugin.giveOneLoseToPlayer.givePlayerLose(player11);
									}
								}
							}
							plugin.winners.clear();
							plugin.losses.clear();
						}
					}
				}
			} else {
				messenger.warn(player, "You can't end the game. You need " + ERROR_GENERAL + "foodm.end " + WARN_GENERAL + "permission to do this.");
			}
		} else if (args.length >= 1 && args[0].equalsIgnoreCase("kick")) {
			// it kicks players from the game/waiting lobby
			if (player.hasPermission("foodm.kick")) {
				if (args.length > 1) {
					plugin.kickPlayerFromGame.kickPlayerFromGame(args, player);
				} else {
					plugin.helpMenu.helpAll(player);
				}
			} else {
				messenger.warn(player, "You can't kick players without " + ERROR_GENERAL + "foodm.kick " + WARN_GENERAL + s1);
			}
		} else if (args.length >= 1 && args[0].equalsIgnoreCase("group")) {
			if (player.hasPermission("foodm.group")) {
				if (args.length > 1) {
					plugin.groupMain.GroupMain(player, args);
				} else {
					plugin.helpMenu.helpGroupMenu(player);
				}
			} else {
				messenger.warn(player, "You can't use groups without " + ERROR_GENERAL + "foodm.group " + WARN_GENERAL + s1);
			}
		} else if (args.length >= 1 && args[0].equalsIgnoreCase("rejoin")) {
			if (player.hasPermission("foodm.rejoin")) {
				plugin.rejoin.rejoinCommand(player);
			} else {
				messenger.warn(player, s + ERROR_GENERAL + "foodm.rejoin " + WARN_GENERAL + s1);
			}
		} else if (args.length >= 1 && args[0].equalsIgnoreCase("reset")) {
			if (player.hasPermission("foodm.reset")) {
				// fm reset [player]
				// 1 2 size
				// 0 1 index
				if (args.length > 1) {
					if (player.hasPermission("foodm.staff")) {
						plugin.resetPlayer.resetPlayer(player, args);
					} else {
						messenger.warn(player,
								"You are not a staff member and you are not allowed to use this command!");
					}
				} else {
					plugin.resetPlayer.resetPlayer(player);
				}
			} else {
				messenger.warn(player, s + ERROR_GENERAL + "foodm.reset " + WARN_GENERAL + "permission");
			}
		} else {
			// help menu
			if (player.hasPermission("foodm.help")) {
				plugin.helpMenu.helpAll(player);
			} else {
				messenger.warn(player, message1);
			}
		}

		return true;
	}
}