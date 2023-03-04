package fr.neyuux.tournament.phases.classes;

import fr.neyuux.tournament.Match;
import fr.neyuux.tournament.enums.PhaseType;
import fr.neyuux.tournament.phases.Phase;

import java.util.HashMap;
import java.util.List;

public class MultiplePlayoffs extends Phase {
    public MultiplePlayoffs(String name, int participants) {
        super(name, PhaseType.MULTIPLE_PLAYOFFS, participants);
    }

    public MultiplePlayoffs(int participants) {
        super(PhaseType.MULTIPLE_PLAYOFFS.getName(), PhaseType.MULTIPLE_PLAYOFFS, participants);
    }

    @Override
    public PhaseType getType() {
        return PhaseType.MULTIPLE_PLAYOFFS;
    }

    @Override
    public HashMap<Integer, List<Match>> getMatches() {
        return null;
    }
}
