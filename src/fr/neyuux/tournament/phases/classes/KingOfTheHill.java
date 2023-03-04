package fr.neyuux.tournament.phases.classes;

import fr.neyuux.tournament.Match;
import fr.neyuux.tournament.enums.PhaseType;
import fr.neyuux.tournament.phases.Phase;

import java.util.HashMap;
import java.util.List;

public class KingOfTheHill extends Phase {
    public KingOfTheHill(String name, int participants) {
        super(name, PhaseType.KING_OF_THE_HILL, participants);
    }

    public KingOfTheHill(int participants) {
        super(PhaseType.KING_OF_THE_HILL.getName(), PhaseType.KING_OF_THE_HILL, participants);
    }

    @Override
    public PhaseType getType() {
        return PhaseType.KING_OF_THE_HILL;
    }

    @Override
    public HashMap<Integer, List<Match>> getMatches() {
        return null;
    }
}
