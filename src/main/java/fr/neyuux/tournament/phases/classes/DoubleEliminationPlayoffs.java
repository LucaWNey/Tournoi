package fr.neyuux.tournament.phases.classes;

import fr.neyuux.tournament.Match;
import fr.neyuux.tournament.enums.PhaseType;
import fr.neyuux.tournament.phases.Phase;

import java.util.HashMap;
import java.util.List;

public class DoubleEliminationPlayoffs extends Phase {
    public DoubleEliminationPlayoffs(String name, int participants) {
        super(name, PhaseType.DOUBLE_ELIMINATION_PLAYOFFS, participants);
    }

    public DoubleEliminationPlayoffs(int participants) {
        super(PhaseType.PLAYOFFS.getName(), PhaseType.DOUBLE_ELIMINATION_PLAYOFFS, participants);
    }

    @Override
    public PhaseType getType() {
        return PhaseType.DOUBLE_ELIMINATION_PLAYOFFS;
    }

    @Override
    public HashMap<Integer, List<Match>> getMatches() {
        return null;
    }
}
