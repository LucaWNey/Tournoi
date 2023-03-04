package fr.neyuux.tournament.phases.classes;

import fr.neyuux.tournament.Match;
import fr.neyuux.tournament.enums.PhaseType;
import fr.neyuux.tournament.phases.Phase;

import java.util.HashMap;
import java.util.List;

public class SwissRounds extends Phase {
    public SwissRounds(String name, int participants) {
        super(name, PhaseType.SWISS_ROUNDS, participants);
    }

    public SwissRounds(int participants) {
        super(PhaseType.SWISS_ROUNDS.getName(), PhaseType.SWISS_ROUNDS, participants);
    }

    @Override
    public PhaseType getType() {
        return PhaseType.SWISS_ROUNDS;
    }

    @Override
    public HashMap<Integer, List<Match>> getMatches() {
        return null;
    }
}
