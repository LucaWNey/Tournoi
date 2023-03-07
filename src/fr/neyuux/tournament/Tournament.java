package fr.neyuux.tournament;

import fr.neyuux.tournament.enums.Countries;
import fr.neyuux.tournament.enums.PhaseType;
import fr.neyuux.tournament.phases.Phase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class Tournament {

    private final String ID;
    private String displayName;
    private File file;
    private YamlConfiguration yconfig;
    private int participants = 8;
    private PhaseType phaseType = PhaseType.NONE;
    private String creator;
    private final List<Phase> phases = new ArrayList<>();
    private final HashMap<PhaseType, HashMap<Integer, List<Match>>> matches = new HashMap<>();
    private boolean inscriptionsOpen;

    public Tournament(String displayName, String creator) {
        this.displayName = displayName;
        this.creator = creator;

        this.file = new File(TournamentPlugin.getInstance().getDataFolder(), ChatColor.stripColor(displayName) + ".yml");
        this.yconfig = YamlConfiguration.loadConfiguration(this.file);

        this.ID = UUID.randomUUID().toString().substring(0, 4);

        this.setInConfig("tournament", displayName.replace('§', '&'));
        this.setInConfig("creator", creator);
        this.setInConfig("id", ID);
        this.addParticipants(0);

        TournamentPlugin main = TournamentPlugin.getInstance();
        YamlConfiguration baseConfig = main.getBaseConfig();

        List<String> tournaments = baseConfig.getStringList("tournaments");
        tournaments.add(this.yconfig.getString("tournament") + ".yml");
        baseConfig.set("tournaments", tournaments);
        try {
            baseConfig.save(main.getBaseFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        TournamentPlugin.addTournament(this);

        Bukkit.broadcastMessage(TournamentPlugin.getPrefix() + "§b§l" + creator + " §aa créé le Tournoi " + displayName + "§a.");
    }

    public Tournament(File file, YamlConfiguration yamlConfiguration) {
        this.file = file;
        this.yconfig = yamlConfiguration;
        
        this.displayName = yconfig.getString("tournament").replace('&', '§');
        this.creator = yamlConfiguration.getString("creator");
        this.participants = yamlConfiguration.getInt("config.participants");
        this.inscriptionsOpen = yamlConfiguration.getBoolean("config.inscriptionsopen");
        this.ID = yamlConfiguration.getString("id");

        /*ConfigurationSection phaseSection = yamlConfiguration.getConfigurationSection("phases");

        for (Map<?, ?> map : phaseSection.getMapList()) {

        }*/
    }

    public void delete() {
        File mainfile = TournamentPlugin.getInstance().getBaseFile();
        YamlConfiguration mainyconfig = TournamentPlugin.getInstance().getBaseConfig();

        List<String> tournaments = mainyconfig.getStringList("tournaments");
        tournaments.remove(this.file.getName());
        mainyconfig.set("tournaments", tournaments);
        try {
            mainyconfig.save(mainfile);
        } catch (IOException e) {
            Bukkit.broadcastMessage(TournamentPlugin.getPrefix() + "§4[§cErreur§4] Impossible de supprimer le tournoi correctement.");
            e.printStackTrace();
        }

        if (this.getFile().delete()) {
            if (TournamentPlugin.getInstance().getSelectedTournament().getID().equals(this.getID()))
                TournamentPlugin.getInstance().setSelectedTournament(null);
            TournamentPlugin.getLoadedTournaments().remove(this);
            
            this.file = null;
            this.yconfig = null;
            
        } else {
            Bukkit.broadcastMessage(TournamentPlugin.getPrefix() + "§4[§cErreur§4] §cImpossible de supprimer le tournoi.");
            Bukkit.getLogger().log(Level.SEVERE, "Impossible de supprimer le Tournoi", new IOException());
        }
        Bukkit.broadcastMessage(TournamentPlugin.getInstance().getSelectedTournament().getDisplayName());
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

    public PhaseType getPhaseType() {
        return phaseType;
    }

    public void setPhaseType(PhaseType phase) {
        this.phaseType = phase;
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

    public List<Phase> getPhases() {
        return phases;
    }

    public HashMap<PhaseType, HashMap<Integer, List<Match>>> getMatches() {
        return matches;
    }

    public String getID() {
        return ID;
    }


    public Phase getPhaseByID(String ID) {
        for (Phase phase : this.phases) {
            if (phase.getID().equals(ID))
                return phase;
        }
        return null;
    }
}
