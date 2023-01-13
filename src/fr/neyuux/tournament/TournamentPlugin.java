package fr.neyuux.tournament;

import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

public class TournamentPlugin extends JavaPlugin {

    private static TournamentPlugin INSTANCE;
    private static final String PREFIX = "§a§lTournoi §8§l» §r";
    private static List<Tournament> loadedTournaments;

    private Tournament selectedTournament;

    public static String getPrefix() {
        return PREFIX;
    }

    public static TournamentPlugin getInstance() {
        return INSTANCE;
    }

    public static List<Tournament> getLoadedTournaments() {
        return loadedTournaments;
    }

    public static void openRenameTournamentInventory(Player player) {
        AnvilInventory inv = (AnvilInventory) Bukkit.createInventory(null, InventoryType.ANVIL, "§a§lNommez votre Tournoi");
        inv.setItem(0, new CustomItemStack(Material.PAPER, 1, "Nouveau Tournoi"));
        player.openInventory(inv);
        player.setLevel(10);
    }

    public void loadTournaments() {
        List<Tournament> tournaments = new ArrayList<>();
        File[] configFiles = this.getDataFolder().listFiles(pathname -> pathname.getName().endsWith(".yml"));

        if (configFiles == null) {
            Bukkit.broadcastMessage(getPrefix() + "§4[Erreur§4] Impossible de récupérer la liste des Tournois");
            Bukkit.getLogger().log(Level.SEVERE, "Impossible de recuperer la liste des Tournois", new NullPointerException());
            return;
        }

        for (File file : configFiles) {
            YamlConfiguration yconfig = YamlConfiguration.loadConfiguration(file);
            if (yconfig.contains("tournament"))
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
        this.loadTournaments();

        Bukkit.getServer().getPluginManager().registerEvents(new TournamentListener(), this);

        Bukkit.getLogger().log(Level.FINE, "Tournament enabling");

        super.onEnable();
    }

    public Tournament getSelectedTournament() {
        return selectedTournament;
    }

    public void setSelectedTournament(Tournament tournament) {
        this.selectedTournament = tournament;
        Bukkit.broadcastMessage(getPrefix() + tournament.getDisplayName() + " §aa été sélectionné.");
    }
}
