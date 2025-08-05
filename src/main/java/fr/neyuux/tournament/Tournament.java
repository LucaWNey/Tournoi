package fr.neyuux.tournament;

import fr.neyuux.tournament.phases.Phase;
import fr.neyuux.tournament.phases.PhaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {
    private String id;
    private String name;
    private UUID organizer;
    private List<Participant> participants;
    private TournamentStatus status;
    private Date creationDate;
    private int maxParticipants;
    private int teamSize;
    private List<Phase> phases;


    public void createPhase(PhaseType type, int numAdvancing, int numStarting, int matchRounds) {
        int index = this.phases.size();
        Status phaseStatus = Status.NOT_STARTED;
        String tournamentId = this.getId();
        List<Participant> entrants = new ArrayList<>();

        for (int i = 0; i < this.maxParticipants; i++) {
            Participant participant = new Participant(UUID.randomUUID());
            participant.setSeed(i + 1);
            entrants.add(participant);
        }

        try {
            Phase phase = type.getClazz()
                .getConstructor(int.class, Status.class, String.class, PhaseType.class, List.class, int.class, int.class, int.class, List.class, List.class, List.class)
                .newInstance(index, phaseStatus, tournamentId, type, new ArrayList<>(), numAdvancing, numStarting, matchRounds, entrants, new ArrayList<>(), new ArrayList<>());

            this.phases.add(phase);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            Bukkit.broadcastMessage(TournamentPlugin.PREFIX + "§4§lErreur §8§l» §cImpossible de créer la phase.");
            Bukkit.getLogger().severe("couldn't create phase : " + e.getMessage());
        }
    }

    public void startPhase(Phase phase, @Nullable Phase previousPhase) {
        List<Participant> entrants = new ArrayList<>();
        if (previousPhase == null)
            entrants.addAll(this.getParticipants());
        else
            entrants.addAll(previousPhase.getWinners());

        phase.getEntrants().clear();
        phase.getEntrants().addAll(entrants);

        if (phase.getNumStarting() == entrants.size() && !phase.getMatches().isEmpty()) {

            for (Match match : phase.getMatches()) {
                for (Participant participant : entrants) {
                    if (match.getPlayer1().getSeed() == participant.getSeed())
                        match.setPlayer1(participant);
                    else if (match.getPlayer2().getSeed() == participant.getSeed())
                        match.setPlayer2(participant);
                }
            }
        } else {
            phase.getMatches().clear();
            phase.generateMatchs();
        }

        phase.setStatus(Status.IN_PROGRESS);
    }

    public void onEndPhase(Phase phase) {

        for (Participant participant : phase.getWinners()) {
            participant.setPoints(0);
            participant.setBuchholz(0);
            participant.setWins(0);
            participant.setLoses(0);
            participant.setTies(0);
            participant.setTimeToWin(0);
            participant.setDiff(0);
        }

        this.startPhase(this.phases.get(this.phases.indexOf(phase) + 1), phase);
    }
}