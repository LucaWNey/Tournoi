package fr.neyuux.tournament;

import fr.neyuux.tournament.enums.PhaseType;
import fr.neyuux.tournament.items.hotbar.OpComparatorItemStack;
import fr.neyuux.tournament.listeners.TournamentListener;
import fr.neyuux.tournament.phases.Phase;
import fr.neyuux.tournament.utils.CustomItemStack;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;

public class TournamentPlugin extends JavaPlugin {

    private static TournamentPlugin INSTANCE;
    private static final String PREFIX = "§a§lTournoi §8§l» §r";
    private static List<Tournament> loadedTournaments;

    private File baseFile;

    private YamlConfiguration baseConfig;

    private Tournament selectedTournament = null;

    public static String getPrefix() {
        return PREFIX;
    }

    public static TournamentPlugin getInstance() {
        return INSTANCE;
    }

    public static List<Tournament> getLoadedTournaments() {
        INSTANCE.loadTournaments();
        return loadedTournaments;
    }

    public static void addTournament(Tournament t) {
        loadedTournaments.add(t);
    }

    public static void openRenameTournamentInventory(Player player, Tournament tournament) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.ANVIL, "§a§lNommez votre Tournoi");
        inv.setItem(0, new CustomItemStack(Material.PAPER, 1, "Nouveau Tournoi " + (getLoadedTournaments().size() + 1)).addLore("§0" + getLoadedTournaments().indexOf(tournament)));
        player.openInventory(inv);
        player.setLevel(10);
    }

    public static void openRenamePhaseInventory(Player player, Phase phase) {
        AnvilInventory inv = (AnvilInventory) Bukkit.createInventory(null, InventoryType.ANVIL, "§c§lNommez votre Phase");
        inv.setItem(0, new CustomItemStack(Material.PAPER, 1, "Nouveau Phase " + (getInstance().getSelectedTournament().getPhases().size() + 1)).addLore("§0" + getInstance().getSelectedTournament().getPhases().indexOf(phase)));
        player.openInventory(inv);
        player.setLevel(10);
    }

    public void sendActionBar(Player player, String msg) {
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        try {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
        } catch (NullPointerException e) {e.printStackTrace();}
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

    private static void sendPacket(Player player, Object packet) {
        try {
            Object handle = (player).getClass().getMethod("getHandle").invoke(player);
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

    public void loadTournaments() {
        List<Tournament> tournaments = new ArrayList<>();

        this.baseFile = new File(this.getDataFolder(), "tournaments.yml");
        this.baseConfig = YamlConfiguration.loadConfiguration(this.baseFile);

        List<String> configFiles = this.baseConfig.getStringList("tournaments");

        for (String filename : configFiles) {
            File file = new File(this.getDataFolder(), filename);
            YamlConfiguration yconfig = YamlConfiguration.loadConfiguration(file);
            tournaments.add(new Tournament(file, yconfig));
        }
        loadedTournaments = tournaments;
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.FINE, "Tournament disabling");

        super.onDisable();
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

        INSTANCE = this;

        if (!this.getDataFolder().exists() || this.getDataFolder().listFiles((dir, name) -> name.equals("tournaments.yml")) == null || this.getDataFolder().listFiles((dir, name) -> name.equals("tournaments.yml")).length == 0) {
            this.baseFile = new File(this.getDataFolder(), "tournaments.yml");
            this.baseConfig = YamlConfiguration.loadConfiguration(this.baseFile);
            baseConfig.set("tournaments", new ArrayList<>());
            try {
                baseConfig.save(baseFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.loadTournaments();
        PhaseType.initialiseConstructors();

        Bukkit.getServer().getPluginManager().registerEvents(new TournamentListener(), this);

        Bukkit.getLogger().log(Level.FINE, "Tournament enabling");

        Bukkit.getOnlinePlayers().iterator().next().getInventory().addItem(new OpComparatorItemStack());

        super.onEnable();
    }

    public Tournament getSelectedTournament() {
        return selectedTournament;
    }

    public void setSelectedTournament(Tournament tournament) {
        this.selectedTournament = tournament;
        if (tournament != null) {
            Bukkit.broadcastMessage(getPrefix() + tournament.getDisplayName() + " §aa été sélectionné.");
        }
    }

    public YamlConfiguration getBaseConfig() {
        return baseConfig;
    }

    public File getBaseFile() {
        return baseFile;
    }
}
