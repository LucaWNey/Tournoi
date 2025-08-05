package fr.neyuux.tournament;

import fr.neyuux.tournament.listeners.ConfigListener;
import fr.neyuux.tournament.phases.Phase;
import fr.neyuux.tournament.phases.PhaseType;
import fr.neyuux.tournament.phases.classes.SingleElimination;
import fr.neyuux.tournament.phases.classes.Swiss;
import fr.neyuux.tournament.utils.versions.VersionUtils;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class TournamentPlugin extends JavaPlugin {

    public static TournamentPlugin INSTANCE;
    public static final String LOG_PREFIX = "[Tournament] ";
    public static final String PREFIX = "§a§lTournoi §8§l» §r";

    private File tournamentsFile;
    private FileConfiguration tournamentsConfig;
    private final List<Tournament> tournaments = new ArrayList<>();

    @Getter
    @Nullable
    private Tournament selectedTournament;

    @Override
    public void onEnable() {

        this.getLogger().info(LOG_PREFIX + "enabling...");

        INSTANCE = this;

        this.tournamentsFile = new File(getDataFolder(), "tournaments.yml");
        if (!this.tournamentsFile.exists()) {
            this.tournamentsFile.getParentFile().mkdirs();
            this.saveResource("tournaments.yml", false);
        }
        this.tournamentsConfig = YamlConfiguration.loadConfiguration(this.tournamentsFile);

        this.loadTournaments();

        this.getServer().getPluginManager().registerEvents(new ConfigListener(), this);

        ItemsManager.clearAll();
        ItemsManager.updateSpawnAll();

        this.getLogger().info( LOG_PREFIX + "enabled, " + this.tournaments.size() + " tournaments retrieved ");

        super.onEnable();
    }

    @Override
    public void onDisable() {

        this.saveTournaments();

        this.getLogger().info(LOG_PREFIX + "disabled");
    }


    public Tournament createTournament(String name, UUID organizerID) {
        String id = UUID.randomUUID().toString().substring(0, 5);

        Tournament t = new Tournament(id, name,  organizerID, new ArrayList<>(), TournamentStatus.PENDING, new Date(), 50, 1, new ArrayList<>());

        this.tournaments.add(t);

        return t;
    }

    /**
     * load tournaments from "tournaments.yml"
     */
    private void loadTournaments() {

        this.getLogger().info(LOG_PREFIX + "loading tournaments.yml");

        if (this.tournamentsConfig.contains("tournaments")) {
            for (String key : this.tournamentsConfig.getConfigurationSection("tournaments").getKeys(false)) {

                this.getLogger().info(LOG_PREFIX + "loading tournament \"" + key + "\"...");

                String path = "tournaments." + key + ".";
                String id = key;
                String name = this.tournamentsConfig.getString(path + "name");

                UUID organizer = null;
                if (this.tournamentsConfig.contains(path + "organizer")) {
                    organizer = UUID.fromString(this.tournamentsConfig.getString(path + "organizer"));
                }

                List<Participant> participants = new ArrayList<>();
                if (this.tournamentsConfig.contains(path + "participants")) {
                    for (String participantKey : this.tournamentsConfig.getConfigurationSection(path + "participants").getKeys(false)) {
                        String participantPath = path + "participants." + participantKey + ".";
                        UUID uuid = UUID.fromString(participantKey);
                        String playerName = this.tournamentsConfig.getString(participantPath + "name");
                        int seed = this.tournamentsConfig.getInt(participantPath + "seed", 0);
                        int points = this.tournamentsConfig.getInt(participantPath + "points", 0);
                        int buchholz = this.tournamentsConfig.getInt(participantPath + "buchholz", 0);
                        int wins = this.tournamentsConfig.getInt(participantPath + "wins", 0);
                        int loses = this.tournamentsConfig.getInt(participantPath + "loses", 0);
                        int ties = this.tournamentsConfig.getInt(participantPath + "ties", 0);
                        int diff = this.tournamentsConfig.getInt(participantPath + "diff", 0);
                        long timeToWin = this.tournamentsConfig.getInt(participantPath + "timeToWin", 0);

                        List<UUID> teammates = new ArrayList<>();
                        if (this.tournamentsConfig.contains(participantPath + "teammates")) {
                            for (String uuidStr : this.tournamentsConfig.getStringList(participantPath + "teammates")) {
                                teammates.add(UUID.fromString(uuidStr));
                            }
                        }

                        Participant participant = new Participant(uuid, playerName, teammates, seed, points, buchholz, wins, loses, ties, diff, timeToWin);
                        participants.add(participant);
                    }
                }

                TournamentStatus status = TournamentStatus.PENDING;
                if (this.tournamentsConfig.contains(path + "status")) {
                    status = TournamentStatus.valueOf(this.tournamentsConfig.getString(path + "status"));
                }

                Date creationDate = new Date();
                if (this.tournamentsConfig.contains(path + "creationDate")) {
                    creationDate = new Date(this.tournamentsConfig.getLong(path + "creationDate"));
                }

                int maxParticipants = this.tournamentsConfig.getInt(path + "maxParticipants", 0);

                int teamSize = this.tournamentsConfig.getInt(path + "teamSize", 1);

                List<Phase> phases = new ArrayList<>();
                if (tournamentsConfig.contains(path + "phases")) {
                    for (String phaseKey : tournamentsConfig.getConfigurationSection(path + "phases").getKeys(false)) {
                        String phasePath = path + "phases." + phaseKey + ".";
                        int index = tournamentsConfig.getInt(phasePath + "index", 0);
                        Status phaseStatus = Status.valueOf(this.tournamentsConfig.getString(phasePath + "status"));
                        String tournamentId = this.tournamentsConfig.getString(phasePath + "tournamentId");
                        PhaseType type = PhaseType.valueOf(tournamentsConfig.getString(phasePath + "type"));
                        int numStarting = tournamentsConfig.getInt(phasePath + "numStarting", 0);
                        int numAdvancing = tournamentsConfig.getInt(phasePath + "numAdvancing", 0);
                        int matchRounds = tournamentsConfig.getInt(phasePath + "matchRounds", 1);

                        // Charger entrants en recherchant toutes les infos depuis participants
                        List<Participant> entrants = new ArrayList<>();
                        if (tournamentsConfig.contains(phasePath + "entrants")) {
                            for (String uuidStr : tournamentsConfig.getStringList(phasePath + "entrants")) {
                                UUID entrantUUID = UUID.fromString(uuidStr);
                                Optional<Participant> opt = participants.stream()
                                        .filter(p -> p.getUuid().equals(entrantUUID))
                                        .findFirst();
                                if (opt.isPresent()) {
                                    Participant found = opt.get();
                                    entrants.add(new Participant(found.getUuid(), found.getName(), found.getTeammates(), found.getSeed(), found.getPoints(), found.getBuchholz(), found.getWins(), found.getLoses(), found.getTies(), found.getDiff(), found.getTimeToWin()));
                                } else {
                                    entrants.add(new Participant(entrantUUID));
                                }
                            }
                        }

                        // Charger winners en recherchant toutes les infos depuis participants
                        List<Participant> winners = new ArrayList<>();
                        if (tournamentsConfig.contains(phasePath + "winners")) {
                            for (String uuidStr : tournamentsConfig.getStringList(phasePath + "winners")) {
                                UUID winnerUUID = UUID.fromString(uuidStr);
                                Optional<Participant> opt = participants.stream()
                                        .filter(p -> p.getUuid().equals(winnerUUID))
                                        .findFirst();
                                if (opt.isPresent()) {
                                    Participant found = opt.get();
                                    winners.add(new Participant(found.getUuid(), found.getName(), found.getTeammates(), found.getSeed(), found.getPoints(), found.getBuchholz(), found.getWins(), found.getLoses(), found.getTies(), found.getDiff(), found.getTimeToWin()));
                                } else {
                                    this.getLogger().warning(LOG_PREFIX + "couldn't load participant with id " + uuidStr);
                                    winners.add(new Participant(winnerUUID));
                                }
                            }
                        }

                        // Charger matchs si présents
                        List<Match> loadedMatches = new ArrayList<>();
                        if (tournamentsConfig.contains(phasePath + "matches")) {
                            for (String matchKey : tournamentsConfig.getConfigurationSection(phasePath + "matches").getKeys(false)) {
                                String matchPath = phasePath + "matches." + matchKey + ".";
                                Participant p1 = null;
                                Participant p2 = null;
                                int roundIndex = tournamentsConfig.getInt(matchPath + "roundIndex", 1);
                                if (tournamentsConfig.contains(matchPath + "player1")) {
                                    UUID p1UUID = UUID.fromString(tournamentsConfig.getString(matchPath + "player1"));
                                    p1 = participants.stream().filter(p -> p.getUuid().equals(p1UUID)).findFirst().orElse(new Participant(p1UUID));
                                }
                                if (tournamentsConfig.contains(matchPath + "player2")) {
                                    UUID p2UUID = UUID.fromString(tournamentsConfig.getString(matchPath + "player2"));
                                    p2 = participants.stream().filter(p -> p.getUuid().equals(p2UUID)).findFirst().orElse(new Participant(p2UUID));
                                }
                                int rounds = tournamentsConfig.getInt(matchPath + "rounds", matchRounds);
                                Match match = new Match(matchKey, p1, p2, rounds, roundIndex);
                                if (tournamentsConfig.contains(matchPath + "winner")) {
                                    UUID wUUID = UUID.fromString(tournamentsConfig.getString(matchPath + "winner"));
                                    Participant w = participants.stream().filter(p -> p.getUuid().equals(wUUID)).findFirst().orElse(new Participant(wUUID));
                                    match.setWinner(w);
                                }
                                if (tournamentsConfig.contains(matchPath + "status")) {
                                    match.setStatus(Status.valueOf(tournamentsConfig.getString(matchPath + "status")));
                                }
                                if (tournamentsConfig.contains(matchPath + "winnerNextMatchId")) {
                                    match.setWinnerNextMatchId(tournamentsConfig.getString(matchPath + "winnerNextMatchId"));
                                }
                                if (tournamentsConfig.contains(matchPath + "loserNextMatchId")) {
                                    match.setLoserNextMatchId(tournamentsConfig.getString(matchPath + "loserNextMatchId"));
                                }
                                loadedMatches.add(match);
                            }
                        }

                        // Tie-breakers vide pour l'instant
                        List<Comparator<Participant>> tieBreakers = new ArrayList<>();

                        Phase phase = null;
                        switch (type) {
                            case GROUP_STAGE:
                                break;
                            case SWISS_SYSTEM:
                                int pointsPerWin = tournamentsConfig.getInt(phasePath + "pointsPerWin", 2);
                                int pointsPerTie = tournamentsConfig.getInt(phasePath + "pointsPerTie", 1);
                                int pointsPerLoss = tournamentsConfig.getInt(phasePath + "pointsPerLoss", 0);
                                int numberOfWinsToAdvance = tournamentsConfig.getInt(phasePath + "numberOfWinsToAdvance", 0);
                                int totalRounds = tournamentsConfig.getInt(phasePath + "totalRounds", 5);
                                int round = tournamentsConfig.getInt(phasePath + "round", 1);

                                phase = new Swiss(index, phaseStatus, tournamentId, tieBreakers, numAdvancing, numStarting, matchRounds, entrants, pointsPerWin, pointsPerTie, pointsPerLoss, numberOfWinsToAdvance, totalRounds, round);

                                break;
                            case SINGLE_ELIMINATION:
                                boolean thirdPlaceMatch = this.tournamentsConfig.getBoolean(phasePath + "thirdPlaceMatch", false);

                                phase = new SingleElimination(index, phaseStatus, tournamentId, numStarting, numAdvancing, matchRounds, entrants, thirdPlaceMatch);

                                break;
                            case DOUBLE_ELIMINATION:
                                break;
                            case ROUND_ROBIN:
                                break;
                        }
                        phase.getMatches().addAll(loadedMatches);
                        phases.add(phase);
                    }
                }

                Tournament tournament = new Tournament(id, name, organizer, participants, status, creationDate, maxParticipants, teamSize, phases);
                this.tournaments.add(tournament);
            }
        }
    }

    /**
     * save all tournaments in tournaments.yml
     */
    private void saveTournaments() {
        this.tournamentsConfig.set("tournaments", null);

        this.getLogger().info(LOG_PREFIX + "saving tournaments...");

        for (Tournament t : this.tournaments) {
            String path = "tournaments." + t.getId() + ".";

            this.getLogger().info(LOG_PREFIX + "saving tournament with id \"" + t.getId() + "\"...");

            this.tournamentsConfig.set(path + "name", t.getName());

            if (t.getOrganizer() != null) {
                this.tournamentsConfig.set(path + "organizer", t.getOrganizer().toString());
            }

            if (t.getParticipants() != null && !t.getParticipants().isEmpty()) {
                for (Participant p : t.getParticipants()) {
                    String participantPath = path + "participants." + p.getUuid() + ".";
                    this.tournamentsConfig.set(participantPath + "name", p.getName());
                    this.tournamentsConfig.set(participantPath + "seed", p.getSeed());

                    List<String> teammatesStr = new ArrayList<>();
                    if (p.getTeammates() != null) {
                        for (UUID mate : p.getTeammates()) {
                            teammatesStr.add(mate.toString());
                        }
                    }
                    this.tournamentsConfig.set(participantPath + "teammates", teammatesStr);

                    this.tournamentsConfig.set(participantPath + "points", p.getPoints());
                    this.tournamentsConfig.set(participantPath + "buchholz", p.getBuchholz());
                    this.tournamentsConfig.set(participantPath + "wins", p.getWins());
                    this.tournamentsConfig.set(participantPath + "loses", p.getLoses());
                    this.tournamentsConfig.set(participantPath + "ties", p.getTies());
                    this.tournamentsConfig.set(participantPath + "diff", p.getDiff());
                    this.tournamentsConfig.set(participantPath + "timeToWin", p.getTimeToWin());
                }
            }

            if (t.getStatus() != null) {
                this.tournamentsConfig.set(path + "status", t.getStatus().name());
            }

            if (t.getCreationDate() != null) {
                this.tournamentsConfig.set(path + "creationDate", t.getCreationDate().getTime());
            }

            this.tournamentsConfig.set(path + "maxParticipants", t.getMaxParticipants());

            this.tournamentsConfig.set(path + "teamSize", t.getTeamSize());

            if (t.getPhases() != null && !t.getPhases().isEmpty()) {
                for (Phase p : t.getPhases()) {
                    String phasePath = path + "phases." + p.getIndex() + ".";
                    tournamentsConfig.set(phasePath + "index", p.getIndex());
                    this.tournamentsConfig.set(phasePath + "status", p.getStatus().name());
                    tournamentsConfig.set(phasePath + "tournamentId", p.getTournamentId());
                    tournamentsConfig.set(phasePath + "type", p.getType().name());
                    tournamentsConfig.set(phasePath + "numStarting", p.getNumStarting());
                    tournamentsConfig.set(phasePath + "numAdvancing", p.getNumAdvancing());
                    tournamentsConfig.set(phasePath + "matchRounds", p.getMatchRounds());

                    switch (p.getType()) {
                        case GROUP_STAGE:
                            break;
                        case SWISS_SYSTEM:
                            Swiss swiss = (Swiss) p;
                            this.tournamentsConfig.set(phasePath + "pointsPerWin", swiss.getPointsPerWin());
                            this.tournamentsConfig.set(phasePath + "pointsPerTie", swiss.getPointsPerTie());
                            this.tournamentsConfig.set(phasePath + "pointsPerLoss", swiss.getPointsPerLoss());
                            this.tournamentsConfig.set(phasePath + "numberOfWinsToAdvance", swiss.getNumberOfWinsToAdvance());
                            this.tournamentsConfig.set(phasePath + "totalRounds", swiss.getTotalRounds());
                            this.tournamentsConfig.set(phasePath + "round", swiss.getRound());
                            break;
                        case SINGLE_ELIMINATION:
                            this.tournamentsConfig.set(phasePath + "thirdPlaceMatch", ((SingleElimination)p).hasThirdPlaceMatch());
                            break;
                        case DOUBLE_ELIMINATION:
                            break;
                        case ROUND_ROBIN:
                            break;
                    }

                    if (p.getEntrants() != null) {
                        List<String> entrantUUIDs = new ArrayList<>();
                        for (Participant entrant : p.getEntrants()) {
                            entrantUUIDs.add(entrant.getUuid().toString());
                        }
                        tournamentsConfig.set(phasePath + "entrants", entrantUUIDs);
                    }

                    if (p.getWinners() != null) {
                        List<String> winnerUUIDs = new ArrayList<>();
                        for (Participant winner : p.getWinners()) {
                            winnerUUIDs.add(winner.getUuid().toString());
                        }
                        tournamentsConfig.set(phasePath + "winners", winnerUUIDs);
                    }

                    if (p.getMatches() != null) {
                        for (Match m : p.getMatches()) {
                            String matchPath = phasePath + "matches." + m.getMatchId() + ".";
                            if (m.getPlayer1() != null) {
                                tournamentsConfig.set(matchPath + "player1", m.getPlayer1().getUuid().toString());
                            }
                            if (m.getPlayer2() != null) {
                                tournamentsConfig.set(matchPath + "player2", m.getPlayer2().getUuid().toString());
                            }
                            tournamentsConfig.set(matchPath + "rounds", m.getRounds());
                            tournamentsConfig.set(matchPath + "roundIndex", m.getRoundIndex());
                            if (m.getWinnerNextMatchId() != null) {
                                tournamentsConfig.set(matchPath + "winnerNextMatchId", m.getWinnerNextMatchId());
                            }
                            if (m.getLoserNextMatchId() != null) {
                                tournamentsConfig.set(matchPath + "loserNextMatchId", m.getLoserNextMatchId());
                            }
                            if (m.getWinner() != null) {
                                tournamentsConfig.set(matchPath + "winner", m.getWinner().getUuid().toString());
                            }
                            tournamentsConfig.set(matchPath + "status", m.getStatus().name());
                        }
                    }
                }
            }
        }

        try {
            this.tournamentsConfig.save(this.tournamentsFile);
        } catch (IOException e) {
            this.getLogger().severe(LOG_PREFIX + "couldn't save tournaments.yml : " + e.getMessage());
        }
    }

    public void removeTournament(String id) {
        this.tournaments.removeIf(t -> t.getId().equals(id));
    }

    public Tournament getTournament(String id) {
        return this.tournaments.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Tournament> getAllTournaments() {
        return this.tournaments;
    }

    public void setSelectedTournament(@Nullable Tournament selectedTournament, String playerName) {
        this.selectedTournament = selectedTournament;

        if (selectedTournament != null) {

            VersionUtils.getVersionUtils().sendTitleToALl(selectedTournament.getName(), "§b§l" + playerName + " §a démarre le Tournoi !", 10, 50, 10);

        }
    }
}