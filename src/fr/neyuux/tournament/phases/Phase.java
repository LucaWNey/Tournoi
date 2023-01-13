package fr.neyuux.tournament.phases;

import fr.neyuux.tournament.enums.PhaseType;

public abstract class Phase {

    private final String name;
    private final PhaseType type;
    private final int participants;

    protected Phase(String name, PhaseType type, int participants) {
        this.name = name;
        this.type = type;
        this.participants = participants;
    }

    public PhaseType getType() {
        return type;
    }

    public int getParticipants() {
        return participants;
    }

    public String getName() {
        return name;
    }
}
