package fr.neyuux.tournament.phases.classes;

import fr.neyuux.tournament.Match;
import fr.neyuux.tournament.enums.PhaseType;
import fr.neyuux.tournament.phases.Phase;

import java.util.HashMap;
import java.util.List;

public class Playoffs extends Phase {
    public Playoffs(String name, int participants) {
        super(name, PhaseType.PLAYOFFS, participants);
    }

    public Playoffs(int participants) {
        super(PhaseType.PLAYOFFS.getName(), PhaseType.PLAYOFFS, participants);
    }

    @Override
    public PhaseType getType() {
        return PhaseType.PLAYOFFS;
    }

    @Override
    public HashMap<Integer, List<Match>> getMatches() {
        return null;
    }
}
