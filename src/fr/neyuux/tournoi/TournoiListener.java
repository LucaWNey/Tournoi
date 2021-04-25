package fr.neyuux.tournoi;

import fr.neyuux.tournoi.enums.FightState;
import fr.neyuux.tournoi.enums.Stuff;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class TournoiListener implements Listener {

	private final Index main;
	public static File file;
	public static YamlConfiguration yconfig;
	public TournoiListener(Index main) {
		this.main = main;
		file = main.getFile();
		yconfig = main.getYConfig();
	}
	
	
	@EventHandler
	public void onInvCommand(InventoryClickEvent ev) {
		Player player = (Player)ev.getWhoClicked();
		Inventory inv = ev.getInventory();
		ItemStack current = ev.getCurrentItem();
		
		if (current == null) return;
		if (current.getType() == Material.AIR) return;

		if (inv.getName().equals("§6Liste des pays disponibles"))
			ev.setCancelled(true);
		else if (inv.getName().equalsIgnoreCase("§a§lCréer un tournoi")) {
			ev.setCancelled(true);
			if (current.getType().equals(Material.STAINED_CLAY)) {
				try {
					yconfig.set("created", true);
					yconfig.save(file);
					player.sendMessage(main.getPrefix() + "§a§lLe §6§lTournoi §a§la bien été créé !");
					player.closeInventory();
				} catch (IOException e) {
					player.sendMessage(main.getPrefix() + "§c§lErreur lors de la création du tournoi, veuillez prévenir Neyuux_ ou réessayer plus tard.");
					e.printStackTrace();
				}
			}
		} else if (inv.getName().equals("§c§lSupprimer le Tournoi")) {
			ev.setCancelled(true);
			if (current.getType().equals(Material.STAINED_CLAY)) {

				try {
					file.delete();
					file.createNewFile();
					main.updateConfig();
					yconfig.set("created", false);
					yconfig.save(file);
					player.sendMessage(main.getPrefix() + "§a§lLe §6§lTournoi §a§la bien été supprimé !");
					player.closeInventory();
				} catch (IOException e) {
					player.sendMessage(main.getPrefix() + "§c§lErreur lors de la suppression du tournoi, veuillez prévenir Neyuux_ ou réessayer plus tard.");
					e.printStackTrace();
				}

			}
		}
	}
	
	
	@EventHandler
	public void onDamage(EntityDamageEvent ev) {
		Entity e = ev.getEntity();
		DamageCause dc = ev.getCause();
		double fdamage = ev.getFinalDamage();
		
		if (main.fightmanager.getFightState() != null) {
			if (!main.fightmanager.isFightState(FightState.FIGHTING)) {
				ev.setCancelled(true);
				return;
			}
		} else {
			ev.setCancelled(true);
			return;
		}
		if (!(e instanceof Player)) return;
		Player player = (Player)e;
		
		switch (dc) {
			case DROWNING:
				if (player.getHealth() <= fdamage)
					main.fightmanager.eliminate(player, player.getDisplayName() + " §cs'est noyé miskine.", ev);
				break;
			case ENTITY_ATTACK:
			case PROJECTILE:
				break;
			case FALL:
				if (player.getHealth() <= fdamage)
					main.fightmanager.eliminate(player, player.getDisplayName() + " §cs'est éclaté au sol.", ev);
				break;
			case FIRE:
			case FIRE_TICK:
			case LAVA:
				if (player.getHealth() <= fdamage)
					main.fightmanager.eliminate(player, player.getDisplayName() + " §ca échoué au \"Le Sol c'est de la Lave\".", ev);
				break;
			case STARVATION:
				if (player.getHealth() <= fdamage)
					main.fightmanager.eliminate(player, player.getDisplayName() + " §aest devenu un africain.", ev);
				break;
			case SUFFOCATION:
				if (player.getHealth() <= fdamage)
					main.fightmanager.eliminate(player, player.getDisplayName() + " §cs'est fait obsitrap.", ev);
				break;
			case SUICIDE:
				if (player.getHealth() <= fdamage)
					main.fightmanager.eliminate(player, player.getDisplayName() + " §cs'est fait /kill (ez).", ev);
				break;
			case VOID:
				if (player.getHealth() <= fdamage)
					main.fightmanager.eliminate(player, player.getDisplayName() + " §ca tourné dans le vide.", ev);
				break;
			default:
				ev.setCancelled(true);
				break;
		}
	}

	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent e){
		ItemStack item = e.getItem();
		Player p = e.getPlayer();
		if(item == null) return;

		// GOLDEN HEAD
		if (item.getType() == Material.GOLDEN_APPLE && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(Stuff.getGoldenHeads().getItemMeta().getDisplayName()))
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1, true, true));
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent ev) {
		Entity e = ev.getEntity();
		Entity d = ev.getDamager();
		DamageCause dc = ev.getCause();
		double fdamage = ev.getFinalDamage();
		
		if (!(d instanceof Player) && !(d instanceof Arrow)) return;
		if (main.fightmanager.getFightState() != null) {
			if (!main.fightmanager.isFightState(FightState.FIGHTING)) {
				ev.setCancelled(true);
				return;
			}
		} else {
			ev.setCancelled(true);
			return;
		}
		if (!(e instanceof Player)) return;
		Player player = (Player)e;
		
		switch (dc) {
			case DROWNING:
			case STARVATION:
			case SUFFOCATION:
			case SUICIDE:
			case VOID:
			case LAVA:
			case FIRE_TICK:
			case FIRE:
			case FALL:
				break;
			case ENTITY_ATTACK:
				Player damager = (Player)d;
				if (player.getHealth() <= fdamage)
					main.fightmanager.eliminate(player, player.getDisplayName() + " §cwas rekt by " + damager.getDisplayName() + "§c.", ev);
				break;
			case PROJECTILE:
				Arrow a = (Arrow)d;
				if (a.getShooter() instanceof Player) {
					Player damager1 = (Player)a.getShooter();
					NumberFormat format = NumberFormat.getInstance();
					format.setRoundingMode(RoundingMode.DOWN);
					format.setMaximumFractionDigits(1);
					damager1.sendMessage(main.getPrefix() + player.getDisplayName() + " §cest désormais à §4§l" + format.format(player.getHealth() - fdamage) + " \u2764");
					if (player.getHealth() <= fdamage)
						main.fightmanager.eliminate(player, player.getDisplayName() + " §ca été tué par la flèche de " + damager1.getDisplayName() + "§c.", ev);
				}
				break;
			default:
				ev.setCancelled(true);
				break;
		}
	}
	
	
	@EventHandler
	public void onMoove(PlayerMoveEvent ev) {
		if (main.fightmanager.getFightState() != null)
			if (main.fightmanager.isFightState(FightState.STARTING))
				if (main.fightmanager.p1.equals(ev.getPlayer()) || main.fightmanager.p2.equals(ev.getPlayer()))
					if (ev.getFrom().distanceSquared(ev.getTo()) > 0.001D)
						ev.setTo(ev.getFrom());
	}
	
	@EventHandler
	public void onArrowThrow(EntityShootBowEvent ev) {
		if (main.fightmanager.getFightState() != null)
			if (main.fightmanager.isFightState(FightState.STARTING))
				if (ev.getEntity() instanceof Player) {
					Player p = (Player)ev.getEntity();
					if (main.fightmanager.p1.equals(p) || main.fightmanager.p2.equals(p)) {
						ev.setCancelled(true);
					}
				}
	}
	
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent ev) {
		Block b = ev.getBlock();
		if (!ev.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			if (!b.getType().equals(Material.COBBLESTONE) && !b.getType().equals(Material.WOOD) && !b.getType().equals(Material.OBSIDIAN) && !b.getType().equals(Material.STONE)) {
				Index.sendActionBar(ev.getPlayer(), main.getPrefix() + "§cVous ne pouvez casser que les blocks qui ont été posés par des joueurs !");
				ev.setCancelled(true);
			}
	}
	
	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent ev) {
		if (ev.getChangedType().equals(Material.DIRT))
			ev.setCancelled(true);
	}
	
}
