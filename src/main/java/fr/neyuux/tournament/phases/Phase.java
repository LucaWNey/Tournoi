package fr.neyuux.tournament.phases;

import fr.neyuux.tournament.Match;
import fr.neyuux.tournament.Tournament;
import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.enums.PhaseType;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class Phase {

    private final String ID;
    protected String name;
    protected final PhaseType type;
    protected int participants;

    protected Phase(String name, PhaseType type, int participants) {
        this.name = name;
        this.type = type;
        this.participants = participants;
        this.ID = UUID.randomUUID().toString().substring(0, 4);

        for (Phase phase : TournamentPlugin.getInstance().getSelectedTournament().getPhases())
            if (phase.getName().equals(name))
                throw new IllegalArgumentException("phase with name \"" + name + "\" aleardy exists");

        Tournament tournament = TournamentPlugin.getInstance().getSelectedTournament();

        tournament.setInConfig("phases." + this.name + ".name", name);
        tournament.setInConfig("phases." + this.name + ".type", type);
        tournament.setInConfig("phases." + this.name + ".participants", participants);
        tournament.setInConfig("phases." + this.name + ".id", ID);
    }

    public abstract PhaseType getType();

    public abstract HashMap<Integer, List<Match>> getMatches();


    public int getParticipants() {
        return participants;
    }

    public String getName() {
        return name;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }
}
