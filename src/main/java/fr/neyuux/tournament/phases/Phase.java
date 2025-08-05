package fr.neyuux.tournament.phases;

import fr.neyuux.tournament.Match;
import fr.neyuux.tournament.Participant;
import fr.neyuux.tournament.Status;
import fr.neyuux.tournament.TournamentPlugin;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
public abstract class Phase {
    private int index;
    private Status status;
    private String tournamentId;
    private PhaseType type;
    private List<Comparator<Participant>> tieBreakers;
    private int numAdvancing;
    private int numStarting;
    private int matchRounds;
    private List<Participant> entrants;
    private List<Participant> winners;
    private List<Match> matches;

    protected Phase(int index, Status status, String tournamentId, PhaseType type, List<Comparator<Participant>> tieBreakers,
                    int numAdvancing, int numStarting, int matchRounds,
                    List<Participant> entrants, List<Participant> winners) {
        this.index = index;
        this.status = status;
        this.tournamentId = tournamentId;
        this.type = type;
        this.tieBreakers = tieBreakers != null ? tieBreakers : new ArrayList<>();
        this.numAdvancing = numAdvancing;
        this.numStarting = numStarting;
        this.matchRounds = matchRounds;
        this.entrants = entrants != null ? entrants : new ArrayList<>();
        this.winners = winners != null ? winners : new ArrayList<>();
        this.matches = new ArrayList<>();
    }

    protected void addMatch(Match match) {
        this.matches.add(match);
    }


    protected void end() {
        TournamentPlugin.INSTANCE.getTournament(this.tournamentId).onEndPhase(this);
        this.setStatus(Status.FINISHED);
    }


    public abstract void generateMatchs();

    public abstract void onEndMatch(String matchId);
}