package fr.neyuux.oldtournament;

import fr.neyuux.oldtournament.commands.CommandTournoi;
import fr.neyuux.oldtournament.enums.FightState;
import fr.neyuux.oldtournament.enums.Country;
import fr.neyuux.oldtournament.enums.State;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class Tournament extends JavaPlugin {
	
	private static final String prefix = "§6§lTournoi§8§l» §r";
	public final HashMap<UUID, ScoreboardSign> boards = new HashMap<>();
	private static int timer = 30;
	private static int timerID;
	
	private File file = new File(getDataFolder(), "config.yml");
	private YamlConfiguration yconfig = YamlConfiguration.loadConfiguration(file);
	
	private State state;
	private FightManager fightmanager;
	private StuffManager stuffManager;
	
	public String getPrefix() {
		return prefix;
	}

	public FightManager getFightManager() {
		return this.fightmanager;
	}

	public StuffManager getStuffManager() {
		return this.stuffManager;
	}
	
	
	@Override
	public void onEnable() {
		if (System.getProperties().containsKey("RELOAD")) {
			if (System.getProperty("RELOAD").equals("TRUE"))
				return;
		} else {
			Properties prop = new Properties(System.getProperties());
			prop.put("RELOAD", "FALSE");
		}

		Scoreboard s = Bukkit.getScoreboardManager().getMainScoreboard();
		for (Country p : Country.values()) {
			Team t = s.registerNewTeam(p.getName());
			t.setPrefix(p.getScoreboardPrefixName());
			t.setSuffix("§r");
		}
		
		fightmanager = new FightManager(this);
		
		if (getDataFolder().listFiles() != null) {
			for (File f : Objects.requireNonNull(getDataFolder().listFiles()))
				if (f.getName().equals("config.yml")) file = f;
			if (!file.exists()) {
				file = new File(getDataFolder(), "config.yml");
				yconfig.set("created", false);
				try {
					yconfig.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			yconfig.set("created", false);
			try {
				yconfig.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		getCommand("tournoi").setExecutor(new CommandTournoi(this));
		
		getServer().getPluginManager().registerEvents(new TournamentListener(this), this);
		
		System.out.println("Tournoi enabling");
		super.onEnable();
		
		if (!System.getProperties().containsKey("TOURNAMENTCONNEXION")) {
			Properties prop = new Properties(System.getProperties());
			prop.put("TOURNAMENTCONNEXION", "FALSE");
		} else
			if (System.getProperty("TOURNAMENTCONNEXION").equals("TRUE")) {
				runInscriptionTime();
			} else {
				for (Team t : s.getTeams())
					if (!isTournoiTeam(t.getName()))
						t.unregister();
				for (Objective ob : s.getObjectives())
					ob.unregister();
			}
	}
	
	private boolean isTournoiTeam(String tname) {
		for (Country p : Country.values())
			if (tname.equalsIgnoreCase(p.getName()))
				return true;
		return false;
	}
	
	@Override
	public void onDisable() {
		System.out.println("Tournoi disabling");
		
		try {
			for (Team t : Bukkit.getScoreboardManager().getMainScoreboard().getTeams())
				if (isTournoiTeam(t.getName())) t.unregister();
		} catch (IllegalStateException ignored) {}
		
		super.onDisable();
	}
	
	
	
	private void runInscriptionTime() {
		timer = 30;
		setState(State.INSCRIPTIONTIME);
		
		BukkitRunnable br = new BukkitRunnable() {
			
			@Override
			public void run() {
				if (!isState(State.INSCRIPTIONTIME)) {
					cancel();
					return;
				}
				
				if (timer == 0) {
					cancel();
					getServer().getPluginManager().disablePlugin(getServer().getPluginManager().getPlugin("Tournoi"));
					return;
				}
				
				if (timer == 15)
					Bukkit.broadcastMessage(getPrefix() + "§fDurée restante pour les inscriptions : §e§l" + timer + " §esecondes§f !");
				
				if (timer == 10)
					Bukkit.broadcastMessage(getPrefix() + "§fDurée restante pour les inscriptions : §e§l" + timer + " §esecondes§f !");
				
				if (timer <= 5 && timer > 1)
					Bukkit.broadcastMessage(getPrefix() + "§fDurée restante pour les inscriptions : §e§l" + timer + " §esecondes§f !");
				
				if (timer == 1)
					Bukkit.broadcastMessage(getPrefix() + "§fDurée restante pour les inscriptions : §e§l" + timer + " §eseconde§f !");
				
				timer--;
			}
		};
		br.runTaskTimer(this, 0, 20);
		timerID = br.getTaskId();
	}
	
	public void removeInscriptionTime() {
		Bukkit.getScheduler().cancelTask(timerID);
	}


	public static int getPing(Player player) {
		try {
			Method getHandleMethod = player.getClass().getDeclaredMethod("getHandle");
			Object craftPlayer = getHandleMethod.invoke(player);
			Field pingField = craftPlayer.getClass().getDeclaredField("ping");
			return pingField.getInt(craftPlayer);
		} catch (Exception e) {
			return -1;
		}
	}

	public void pingUpdater(ScoreboardSign ss, Player player) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if (!fightmanager.isFightState(FightState.FIGHTING)) {
					cancel();
					return;
				}
				
				int ping = getPing(player);
				String color = "§f";
				if (ping > 5)
					color = "§a";
				if (ping > 90)
					color = "§2";
				if (ping > 180)
					color = "§e";
				if (ping > 250)
					color = "§c";
				if (ping > 400)
					color = "§4";
				
				ss.setLine(8, " §6Ping : "+color+ping+" ms");
			}
		}.runTaskTimer(this, 0, 5L);
	}
	
	
	public File getFile() {
		return file;
	}
	
	public YamlConfiguration getYConfig() {
		return yconfig;
	}
	
	public void updateConfig() {
		file = new File(getDataFolder(), "config.yml");
		yconfig = YamlConfiguration.loadConfiguration(file);
		TournamentListener.file = file;
		TournamentListener.yconfig = yconfig;
		CommandTournoi.file = file;
		CommandTournoi.yconfig = yconfig;
		FightManager.file = file;
		FightManager.yconfig = yconfig;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
	public boolean isState(State state) {
		return state.equals(this.state);
	}
	
	
	public ItemStack getItem(Material type, int amount, List<String> lore, String name, short durability) {
		ItemStack it = new ItemStack(type, amount, durability);
		ItemMeta itm = it.getItemMeta();
		itm.setDisplayName(name);
		itm.setLore(lore);
		it.setItemMeta(itm);
		
		return it;
	}


	public static void setPlayerTabList(Player player,String header, String footer) {
		IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
		try {
			Field field = packet.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(packet, tabFoot);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sendPacket(player, packet);
		}
	}



	private static void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Class<?> getNMSClass(String name) {
		try {
			return Class.forName("net.minecraft.server."
					+ Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void sendTitle(Player player, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
		try {
			Object chatTitle = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\": \"" + title + "\"}");
			Constructor<?> titleConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getConstructor(
					Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
					int.class, int.class, int.class);
			Object packet = titleConstructor.newInstance(
					Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
					fadeInTime, showTime, fadeOutTime);

			Object chatsTitle = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\": \"" + subtitle + "\"}");
			Constructor<?> timingTitleConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getConstructor(
					Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
					int.class, int.class, int.class);
			Object timingPacket = timingTitleConstructor.newInstance(
					Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
					fadeInTime, showTime, fadeOutTime);

			sendPacket(player, packet);
			sendPacket(player, timingPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendActionBar(Player p, String message) {
		IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		sendPacket(p, ppoc);
	}

	
}
