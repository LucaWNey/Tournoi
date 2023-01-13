package fr.neyuux.tournament;

import fr.neyuux.tournament.enums.Countries;
import fr.neyuux.tournament.enums.PhaseType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Tournament {

    private String displayName;
    private File file;
    private YamlConfiguration yconfig;
    private int participants = 8;
    private PhaseType phase = PhaseType.NONE;
    private final String creator;
    private boolean inscriptionsOpen;

    public Tournament(String displayName, String creator) {
        this.displayName = displayName;
        this.creator = creator;

        this.file = new File(TournamentPlugin.getInstance().getDataFolder(), ChatColor.stripColor(displayName));
        this.yconfig = YamlConfiguration.loadConfiguration(this.file);

        this.setInConfig("tournament", displayName.replace('§', '&'));
        this.setInConfig("creator", creator);

        Bukkit.broadcastMessage(TournamentPlugin.getPrefix() + "§b§l" + creator + " §aa créé le Tournoi " + displayName + "§a.");
    }

    public Tournament(File file, YamlConfiguration yamlConfiguration) {
        this.file = file;
        this.yconfig = yamlConfiguration;

        this.displayName = ChatColor.translateAlternateColorCodes('&', (String) yamlConfiguration.get("tournament"));
        this.creator = yamlConfiguration.getString("creator");
        this.participants = yamlConfiguration.getInt("config.participants");
        this.inscriptionsOpen = yamlConfiguration.getBoolean("config.inscriptionsopen");
    }

    public void delete() {
        if (this.getFile().delete()) {
            this.file = null;
            this.yconfig = null;
        } else {
            Bukkit.broadcastMessage(TournamentPlugin.getPrefix() + "§4[§cErreur§4] §cImpossible de supprimer le tournoi.");
            Bukkit.getLogger().log(Level.SEVERE, "Impossible de supprimer le Tournoi", new IOException());
        }
    }

    public void setPlayerCountry(Player player, Countries country) {
        Scoreboard s = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = s.getTeam(country.getName());

        if (team == null)
            team = s.registerNewTeam(country.getName());

        team.setPrefix(country.getPrefix());
        team.setSuffix("§r");

        team.addEntry(player.getName());
    }

    public YamlConfiguration getYamlConfiguration() {
        return yconfig;
    }

    public void setInConfig(String path, Object value) {
        yconfig.set(path, value);
        try {
            yconfig.save(this.getFile());
        } catch (IOException e) {
            Bukkit.broadcastMessage(this.getPrefix() + "§4[§cErreur§4] §cImpossible de sauvegarder une modif de la config du Tournoi");
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        this.setInConfig("tournament", displayName.replace('§', '&'));
    }

    public String getPrefix() {
        return displayName + " §8§l» §r";
    }

    public int getParticipants() {
        return participants;
    }

    public void addParticipants(int participants) {
        this.participants += participants;
        this.setInConfig("config.participants", this.participants);
    }

    public PhaseType getPhase() {
        return phase;
    }

    public void setPhase(PhaseType phase) {
        this.phase = phase;
    }

    public String getCreator() {
        return creator;
    }

    public boolean isInscriptionsOpen() {
        return inscriptionsOpen;
    }

    public void setInscriptionsOpen(boolean inscriptionsOpen) {
        this.inscriptionsOpen = inscriptionsOpen;
        this.setInConfig("config.inscriptionsopen", this.inscriptionsOpen);
    }
}
