package fr.neyuux.tournament;

import fr.neyuux.tournament.enums.*;
import org.bukkit.*;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class FightManager {
	
	private final Tournament main;
	public static File file;
	public static YamlConfiguration yconfig;
	public FightManager(Tournament main) {
		this.main = main;
		file = main.getFile();
		yconfig = main.getYConfig();
	}
	
	private FightState fs;
	
	Player p1;
	Country country1;
	Player p2;
	Country country2;
	List<Player> players = new ArrayList<>();
	int bo;
	FightType ft;
	int groupNum;
	HashMap<Player, Integer> rounds = new HashMap<>();
	Player fwinner;
	
	
	public void createMatch(Player p1, Player p2, int BestOf, FightType fighttype, int groupNum) {
		players.clear();
		this.p1 = p1;
		players.add(p1);
		country1 = Country.values()[yconfig.getInt("players." + p1.getUniqueId().toString() + ".pays")];
		this.p2 = p2;
		players.add(p2);
		country2 = Country.values()[yconfig.getInt("players." + p2.getUniqueId().toString() + ".pays")];
		this.bo = BestOf;
		this.ft = fighttype;
		this.groupNum = groupNum;
		main.setState(State.FIGHTING);
		fs = FightState.WAIT;
		rounds.clear();
		rounds.put(p1, 0);
		rounds.put(p2, 0);
		fwinner = null;
		
		Bukkit.getServer().createWorld(new WorldCreator("Tournoi"));
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.teleport(new Location(Bukkit.getWorld("Tournoi"), -579, 84, 336, 90f, 0f));
			p.setGameMode(GameMode.SPECTATOR);
			if (Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(p.getName()) != null)
				Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(p.getName()).unregister();
			p.setDisplayName("§8[§7Spectateur§8] §7" + p.getName() + "§r");
			p.setPlayerListName(p.getDisplayName());
			
			ScoreboardSign ss = new ScoreboardSign(p, main.getPrefix());
			ss.create();
			ss.setLine(0, "§eType de match : §l" + ft);
			ss.setLine(1, "§0");
			ss.setLine(2, p1.getDisplayName() + "§8(§70 points§8)");
			ss.setLine(3, "   §a§lVS");
			ss.setLine(4, p2.getDisplayName() + "§8(§70points§8)");
			ss.setLine(12, "§8-------");
			ss.setLine(13, "§e§lMap by §c§l§oNeyuux_");
			main.boards.put(p.getUniqueId(), ss);
		}
		Bukkit.getScoreboardManager().getMainScoreboard().getTeam(country1.getName()).addEntry(p1.getName());
		p1.setDisplayName(country1.getDisplayName() + " §f" + p1.getName() + "§r");
		p1.setPlayerListName(p1.getDisplayName());
		Bukkit.getScoreboardManager().getMainScoreboard().getTeam(country2.getName()).addEntry(p2.getName());
		p2.setDisplayName(country2.getDisplayName() + " §f" + p2.getName() + "§r");
		p2.setPlayerListName(p2.getDisplayName());
		Bukkit.broadcastMessage(main.getPrefix() + "§eLe combat entre les pays §r" + country1.getDisplayName() + "§6§o(" + p1.getName() + ") §eet §r" + country2.getDisplayName() + "§6§o(" + p2.getName() + ") §eest sur le point de commencer !§r\n§eLe match se déroulera en §7Bo" + bo + "§e.");
		if (ft.equals(FightType.GROUPES)) {
			Bukkit.broadcastMessage("§7Avant cela, regardons les points du groupe " + groupNum + " :");
			for (String cs : yconfig.getConfigurationSection("groups.group" + groupNum).getKeys(false))
				if (yconfig.getInt("groups.group" + groupNum + "." + cs + ".points") != 1)
						Bukkit.broadcastMessage(Country.values()[Integer.parseInt(cs)].getDisplayName() + " §7: " + yconfig.getInt("groups.group" + groupNum + "." + cs + ".points") + " §8points");
				else
					Bukkit.broadcastMessage(Country.values()[Integer.parseInt(cs)].getDisplayName() + " §7: " + yconfig.getInt("groups.group" + groupNum + "." + cs + ".points") + " §8point");
		}
		
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(main.getPrefix() + "§eLe match commencera dans 5 secondes. Chaque joueur aura 10 secondes avant chaque combat pour modifier son inventaire et se préparer. Bonne chance.");
		new BukkitRunnable() {
			int s = 0;
			@Override
			public void run() {
				if (s == 5) {
					createFight();
					cancel();
					return;
				}
				
				s++;
			}
		}.runTaskTimer(main, 0, 20);
	}
	
	public void createFight() {
		int roundNumber = getRoundNumber();
		Stuff stuff = Stuff.getByRoundNumber(roundNumber);
		Bukkit.broadcastMessage(main.getPrefix() + "§e§lRound " + roundNumber  + ", §7Stuff : " + stuff.getName());
		for (Player p : players) {
			p.resetMaxHealth();
			p.setHealth(p.getMaxHealth());
			p.setFoodLevel(20);
			for (PotionEffect pe : p.getActivePotionEffects())
				p.removePotionEffect(pe.getType());
			p.setGameMode(GameMode.SURVIVAL);
			p.getInventory().clear();
			main.getStuffManager().giveStuff(p, stuff);
			p.updateInventory();
			
			ScoreboardSign ss = main.boards.get(p.getUniqueId());
			ss.setLine(5, "§9");
			ss.setLine(6, "§cAdversaire :");
			if (p.equals(p1))
				ss.setLine(7, " §6" + p2.getName());
			else
				ss.setLine(7, " §6" + p1.getName());
			ss.setLine(8, " §6Ping : §f? ms");
		}
		for (Entry<UUID, ScoreboardSign> en : main.boards.entrySet()) {
			en.getValue().setLine(2, p1.getDisplayName() + "§8(§7"+rounds.get(p1)+" points§8)");
			en.getValue().setLine(4, p2.getDisplayName() + "§8(§7"+rounds.get(p2)+" points§8)");
		}
		p1.teleport(new Location(Bukkit.getWorld("Tournoi"), -639.5, 64.0001, 381.5, 180f, 0f));
		p2.teleport(new Location(Bukkit.getWorld("Tournoi"), -638.5, 64.0001, 291.5, 0f, 0f));
		Bukkit.broadcastMessage(main.getPrefix() + "§aLe match commencera dans 10 secondes.");
		setFightState(FightState.STARTING);
		new BukkitRunnable() {
			int s = 10;
			@Override
			public void run() {
				if (s == 0) {
					Bukkit.broadcastMessage(main.getPrefix() + "§a§lDébut du combat !");

					for (Player p : Bukkit.getOnlinePlayers())
						p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 8, 1);

					for (Player p : players) {
						p.updateInventory();
						main.getStuffManager().saveStuff(p, stuff);
					}

					setFightState(FightState.FIGHTING);
					main.pingUpdater(main.boards.get(p1.getUniqueId()), p2);
					main.pingUpdater(main.boards.get(p2.getUniqueId()), p1);

					cancel();
					return;
				}
				
				if (s != 1 && s != 10)Bukkit.broadcastMessage(main.getPrefix() + "§aDébut du combat dans §l" + s + " §asecondes.");
				else if (s == 1) Bukkit.broadcastMessage(main.getPrefix() + "§aDébut du combat dans §l" + s + " §aseconde.");
				s--;
			}
		}.runTaskTimer(main, 0, 20);
	}
	
	
	private int getRoundNumber() {
		int i = 1;
		for (Entry<Player, Integer> en : rounds.entrySet())
			i = i + en.getValue();
		
		return i;
	}
	
	public FightState getFightState() {
		return fs;
	}
	
	public void setFightState(FightState fs) {
		this.fs = fs;
	}
	
	public boolean isFightState(FightState fs) {
		return this.fs.equals(fs);
	}
	
	
	public void eliminate(Player player, String deathMessage, EntityDamageEvent ev) {
		Player winner;
		if (player.equals(p1))
			winner = p2;
		else
			winner = p1;
		Country wp = Country.values()[yconfig.getInt("players." + winner.getUniqueId().toString() + ".pays")];
		
		Bukkit.broadcastMessage("§c================================================");
		Bukkit.broadcastMessage(deathMessage);
		Bukkit.broadcastMessage("§c================================================");
		
		ev.setDamage(0);
		
		for (Player p : players) {
			main.getStuffManager().clearStuff(player);
			p.setFireTicks(0);
		}
		for (Player p : Bukkit.getOnlinePlayers())
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 8, 1);
		for (Arrow arrow : Bukkit.getWorld("Tournoi").getEntitiesByClass(Arrow.class)) arrow.remove();
		clearBlocks();
		
		Bukkit.broadcastMessage(main.getPrefix() + "§eRound terminé ! Victoire de " + wp.getDisplayName() + "§6§o(" + winner.getName() + ")");
		rounds.put(winner, rounds.get(winner) + 1);
		setFightState(FightState.WAIT);
		if (rounds.get(p1) == 3 || rounds.get(p2) == 3) {
			if (rounds.get(p1) == 3)
				fwinner = p1;
			else
				fwinner = p2;
			Country fwp = Country.values()[yconfig.getInt("players." + fwinner.getUniqueId().toString() + ".pays")];
			if (ft.equals(FightType.GROUPES)) {
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage(main.getPrefix() + "§e§lVictoire du match par " + fwp.getDisplayName() + "§6§o(" + fwinner.getName() + ") §e§l!§r\n§eIl remporte §61 point§e et arrive à un score de §6§l" + (yconfig.getInt("groups.group" + groupNum + "." + fwp.ordinal() + ".points") + 1) + "§e!");
				try {
					yconfig.set("groups.group" + groupNum + "." + fwp.ordinal() + ".points", yconfig.getInt("groups.group" + groupNum + "." + fwp.ordinal() + ".points") + 1);
					yconfig.save(file);
				} catch (IOException e) {
					Bukkit.broadcastMessage(main.getPrefix() + "§cErreur lors de la modification des points, veuillez en prévenir Neyuux_.");
					e.printStackTrace();
				}
			} else {
				Player loser;
				if (fwinner == p1)
					loser = p2;
				else loser = p1;
				Country lp = Country.values()[yconfig.getInt("players." + loser.getUniqueId().toString() + ".pays")];
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage(main.getPrefix() + "§e§lVictoire du match par " + fwp.getDisplayName() + "§6§o(" + fwinner.getName() + ") §e§l! Il élimine " + lp.getDisplayName() + "§6§o(" + loser.getName() + ") §e§l!");
			}
			for (Player p : Bukkit.getOnlinePlayers())
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_DEATH, 6, 1);
			new BukkitRunnable() {
				int s = 7;
				@Override
				public void run() {
					if (s == 0) {
						cancel();
						return;
					}
					
					Firework fw = (Firework) Bukkit.getWorld("Tournoi").spawnEntity(fwinner.getLocation(), EntityType.FIREWORK);
					FireworkMeta fwm = fw.getFireworkMeta();
					Builder fwe = FireworkEffect.builder();
					fwe.trail(true);
					fwe.withColor(Color.GREEN);
					fwe.with(Type.STAR);
					fwm.addEffect(fwe.build());
					fw.setFireworkMeta(fwm);
					s--;
				}
			}.runTaskTimer(main, 0, 20);
		} else {
			Bukkit.broadcastMessage(main.getPrefix() + "§eLe prochain round démarrera dans 5 secondes.");
			new BukkitRunnable() {
				int s = 0;
				@Override
				public void run() {
					if (s == 5) {
						createFight();
						cancel();
						return;
					}
					
					s++;
				}
			}.runTaskTimer(main, 0, 20);
		}
	}

	private void clearBlocks() {
		int i = 0;
		for (int x = -611; x >= -668; x--)
			for (int y = 64; y <= 71; y++)
				for (int z = 288; z <= 384; z++) {
					Block b = Bukkit.getWorld("Tournoi").getBlockAt(x, y, z);
					if (!b.getType().equals(Material.IRON_BLOCK) && !b.getType().equals(Material.BARRIER)) {
						if (!b.getType().equals(Material.AIR)) i++;
						b.setType(Material.AIR);
					}
				}
		System.out.println("cleared " + i + " blocks");
		
		for (int x = -611; x >= -668; x--)
			for (int z = 288; z <= 384; z++)
				if (Bukkit.getWorld("Tournoi").getBlockAt(x, 63, z).getType().equals(Material.DIRT))
					Bukkit.getWorld("Tournoi").getBlockAt(x, 63, z).setType(Material.GRASS);
	}

}
