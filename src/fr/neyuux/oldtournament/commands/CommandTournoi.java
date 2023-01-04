package fr.neyuux.oldtournament.commands;

import fr.neyuux.oldtournament.Tournament;
import fr.neyuux.oldtournament.enums.FightType;
import fr.neyuux.oldtournament.enums.Country;
import fr.neyuux.oldtournament.enums.State;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CommandTournoi implements CommandExecutor {
	
	private final Tournament main;
	public static File file;
	public static YamlConfiguration yconfig;
	public CommandTournoi(Tournament main) {
		this.main = main;
		file = main.getFile();
		yconfig = main.getYConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

		if (args.length > 0) {
			
			if (args[0].equalsIgnoreCase("sign")) {
				if (main.isState(State.INSCRIPTIONTIME)) {
					if (sender instanceof Player && yconfig.getBoolean("created")) {
						final Player player = (Player)sender;
						if (args.length > 1) {
							Country p = Country.getByCode(args[1]);
							if (p != null) {
								if (yconfig.getConfigurationSection("players") != null)
									for (String cs : yconfig.getConfigurationSection("players").getKeys(false))
										if (yconfig.getConfigurationSection("players").getInt(cs + ".pays") == p.ordinal()) {
											player.sendMessage(main.getPrefix() + "§cLe pays §4\"§r" + p.getDisplayName() + "§4\" §cest déjà prit !");
											return true;
										} else if (cs.equals(player.getUniqueId().toString())) {
											player.sendMessage(main.getPrefix() + "§cVous avez déjà choisi un pays ! Pour le changer, contacter Neyuux_");
											return true;
										}
									final String key = "players." + player.getUniqueId().toString();
									
									try {
										yconfig.set(key + ".pays", p.ordinal());
										yconfig.save(file);
										player.sendMessage(main.getPrefix() + "§aVous incarnez désormais le pays : §2\"§r" + p.getDisplayName() + "§2\"§a.");
									} catch (IOException e) {
										e.printStackTrace();
										player.sendMessage(main.getPrefix() + "§cLa sélection du pays à échoué. Veuillez en informer Neyuux_ ou réessayer plus tard.");
									}
									
									return true;
							} else player.sendMessage(main.getPrefix() + "§cLe pays avec le code §4\"§e" + args[1] + "§4\" §cn'existe pas. §e§o(La liste des pays se trouve à l'aide de la commande §6/tournoi payslist§e§o)");
						} else player.sendMessage(main.getPrefix() + "§fUtilisation de la commande §atournoi sign§f : \n§e/tournoi sign §6<Code>");
					}
				} else sender.sendMessage(main.getPrefix() + "§cVous devez allumer le plugin à l'aide de la commande §6/tournamentconnexion §cpour utiliser cette commande.");
			}
			
			else if (args[0].equalsIgnoreCase("payslist") && yconfig.getBoolean("created")) {
				if (sender instanceof Player) {
					final Player player = (Player)sender;
					final Inventory inv = Bukkit.createInventory(null, 36, "§6Liste des pays disponibles");
					
					for (Country p : Country.values()) {
						Boolean isUsed = false;
						try {
							for (String cs : yconfig.getConfigurationSection("players").getKeys(false))
								if (yconfig.getConfigurationSection("players").getInt(cs + ".pays") == p.ordinal())
									isUsed = true;
						} catch (NullPointerException ignored) {}
						if (isUsed.equals(true)) continue;
						ItemStack it = p.getHead();
						SkullMeta itm = (SkullMeta) it.getItemMeta();
						itm.setDisplayName("§f" + p.getDisplayName());
						itm.setLore(Arrays.asList("§7Choisir le pays §f" + p.getDisplayName() + "§7.", "", "§fCode : §6" + p.getCode(), "§fVotre pseudo : " + Bukkit.getScoreboardManager().getMainScoreboard().getTeam(p.getName()).getPrefix() + player.getName()));
						it.setItemMeta(itm);
						inv.addItem(it);
					}
					player.openInventory(inv);
				}
			}
			
			else if (args[0].equalsIgnoreCase("create")) {
				if (!yconfig.getBoolean("created"))
					if (sender instanceof Player) {
						final Player player = (Player)sender;
						if (player.isOp()) {
							final Inventory inv = Bukkit.createInventory(null, 27, "§a§lCréer un tournoi");
							ItemStack verre = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)1);
							int slot1 = 0;
							int slot2 = 8;
							int slot3 = inv.getSize() - 9;
							int slot4 = inv.getSize() - 1;
							inv.setItem((slot1), verre);
							inv.setItem((slot1 + 9), verre);
							inv.setItem((slot1 + 1), verre);
							inv.setItem((slot2), verre);
							inv.setItem((slot2 + 9), verre);
							inv.setItem((slot2 - 1), verre);
							inv.setItem((slot3), verre);
							inv.setItem((slot3 - 9), verre);
							inv.setItem((slot3 + 1), verre);
							inv.setItem((slot4), verre);
							inv.setItem((slot4 - 9), verre);
							inv.setItem((slot4 - 1), verre);
							
							
							inv.setItem(13, main.getItem(Material.STAINED_CLAY, 1, Collections.singletonList("§7Créé un tournoi."), "§a§lCréer un tournoi", (short)5));
							player.openInventory(inv);
						}
					}
			}
			
			else if (args[0].equalsIgnoreCase("delete")) {
				if (yconfig.getBoolean("created"))
					if (sender instanceof Player) {
						final Player player = (Player)sender;
						if (player.isOp()) {
							final Inventory inv = Bukkit.createInventory(null, 27, "§c§lSupprimer le Tournoi");
							ItemStack verre = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)1);
							int slot1 = 0;
							int slot2 = 8;
							int slot3 = inv.getSize() - 9;
							int slot4 = inv.getSize() - 1;
							inv.setItem((slot1), verre);
							inv.setItem((slot1 + 9), verre);
							inv.setItem((slot1 + 1), verre);
							inv.setItem((slot2), verre);
							inv.setItem((slot2 + 9), verre);
							inv.setItem((slot2 - 1), verre);
							inv.setItem((slot3), verre);
							inv.setItem((slot3 - 9), verre);
							inv.setItem((slot3 + 1), verre);
							inv.setItem((slot4), verre);
							inv.setItem((slot4 - 9), verre);
							inv.setItem((slot4 - 1), verre);
							
							
							inv.setItem(13, main.getItem(Material.STAINED_CLAY, 1, Collections.singletonList("§7Supprime un tournoi."), "§a§lSupprimer le tournoi", (short)14));
							player.openInventory(inv);
						}
					}
			}
			
			else if (args[0].equalsIgnoreCase("setgroup")) {
				if (yconfig.getBoolean("created")) {
					if (sender instanceof Player) {
						final Player player = (Player)sender;
						if (player.isOp()) {
							
							if (args.length > 1) {
								if (StringUtils.isNumeric(args[1])) {
									if (args.length > 2) {
										int g = Integer.parseInt(args[1]);
										String key = "groups.group" + g;
										List<Integer> pays = new ArrayList<>();
										for (String a : args)
											if (!a.equals(args[0]) && !a.equals(args[1]))
												if (Country.getByCode(a) != null)
													pays.add(Country.getByCode(a).ordinal());
										
										try {
											yconfig.set(key, pays);
											for (Integer i : pays)
												yconfig.set(key + "." + i + ".points", 0);
											yconfig.save(file);
											player.sendMessage(main.getPrefix() + "§a§lLe Groupe " + g + " contient bien désormais les pays :");
											for (Integer i : pays)
												player.sendMessage(Country.values()[i].getDisplayName());
										} catch (IOException e) {
											player.sendMessage(main.getPrefix() + "§c§lErreur lors de la création du groupe, veuillez en informer Neyuux_ ou réessayer plus tard.");
											e.printStackTrace();
										}
									} else player.sendMessage(main.getPrefix() + "§cVeuillez inscrire les codes des pays que vous voulez ajouter au groupe à la suite de cette commande.");
								} else player.sendMessage(main.getPrefix() + "§4\"§e" + args[1] + "§4\" §cn'a pas pu être détecté comme un nombre.");
							} else player.sendMessage(main.getPrefix() + "§cVeuillez incrire un numéro de groupe.");
							
						}
					}
				}
			}
			
			else if (args[0].equalsIgnoreCase("fight")) {
				if (yconfig.getBoolean("created")) {
					if (sender instanceof Player) {
						final Player player = (Player)sender;
						if (player.isOp()) {
							
							if (args.length > 1)
								if (args[1].equalsIgnoreCase("groups")) {
									if (args.length > 2)
										if (StringUtils.isNumeric(args[2])) {
											if (args.length >= 5) {
												if (Country.getByCode(args[4]) != null && Country.getByCode(args[5]) != null) {
													Country country1 = Country.getByCode(args[4]);
													Player p1 = null;
													Country country2 = Country.getByCode(args[5]);
													Player p2 = null;
													int bo;
													try {
														bo = Integer.parseInt(args[3]);
													} catch (NumberFormatException e) {
														player.sendMessage(main.getPrefix() + "§cVeuillez donner un nombre valide pour le bo.");
														return true;
													}
													for (String cs : yconfig.getConfigurationSection("players").getKeys(false))
														if (yconfig.getConfigurationSection("players").getInt(cs + ".pays") == country1.ordinal())
															p1 = Bukkit.getPlayer(UUID.fromString(cs));
														else if (yconfig.getConfigurationSection("players").getInt(cs + ".pays") == country2.ordinal())
															p2 = Bukkit.getPlayer(UUID.fromString(cs));
													
													if (p1 == null || p2 == null) {
														Bukkit.broadcastMessage(main.getPrefix() + "§cImpossible de lancer le combat §r" + country1.getDisplayName() + " §cvs §r" + country2.getDisplayName() + " §ccar les deux joueurs ne sont pas connectés");
														return true;
													}
													
													main.getFightManager().createMatch(p1, p2, bo, FightType.GROUPES, Integer.parseInt(args[2]));
												}
											}
										}
								} else if (args[1].equalsIgnoreCase("elim")) {
									if (args.length > 3) {
										if (Country.getByCode(args[3]) != null && Country.getByCode(args[4]) != null) {
											Country country1 = Country.getByCode(args[3]);
											Player p1 = null;
											Country country2 = Country.getByCode(args[4]);
											Player p2 = null;
											int bo;
											try {
												bo = Integer.parseInt(args[2]);
											} catch (NumberFormatException e) {
												player.sendMessage(main.getPrefix() + "§cVeuillez donner un nombre valide pour le bo.");
												return true;
											}
											for (String cs : yconfig.getConfigurationSection("players").getKeys(false))
												if (yconfig.getConfigurationSection("players").getInt(cs + ".pays") == country1.ordinal())
													p1 = Bukkit.getPlayer(UUID.fromString(cs));
												else if (yconfig.getConfigurationSection("players").getInt(cs + ".pays") == country2.ordinal())
													p2 = Bukkit.getPlayer(UUID.fromString(cs));
											
											if (p1 == null || p2 == null) {
												Bukkit.broadcastMessage(main.getPrefix() + "§cImpossible de lancer le combat §r" + country1.getDisplayName() + " §cvs §r" + country2.getDisplayName() + " §ccar les deux joueurs ne sont pas connectés");
												return true;
											}
											
											main.getFightManager().createMatch(p1, p2, bo, FightType.ELIMINATIONS, 0);
										}
									}
								}
							
						}
					}
				}
			}
			
			else if (args[0].equalsIgnoreCase("teleport")) {
				if (sender instanceof Player) {
					final Player player = (Player)sender;
					if (player.isOp()) {
						Bukkit.getServer().createWorld(new WorldCreator("Tournoi"));
						player.teleport(new Location(Bukkit.getWorld("Tournoi"), -579, 84, 336, 90f, 0f));
					}
				}
			}
		}
		
		return false;
	}

}
