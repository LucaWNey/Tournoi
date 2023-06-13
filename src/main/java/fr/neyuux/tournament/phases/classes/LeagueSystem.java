package fr.neyuux.tournament.phases.classes;

import fr.neyuux.tournament.Match;
import fr.neyuux.tournament.enums.PhaseType;
import fr.neyuux.tournament.phases.Phase;

import java.util.HashMap;
import java.util.List;

public class LeagueSystem extends Phase {
    public LeagueSystem(String name, int participants) {
        super(name, PhaseType.LEAGUE_SYSTEM, participants);
    }

    public LeagueSystem(int participants) {
        super(PhaseType.LEAGUE_SYSTEM.getName(), PhaseType.LEAGUE_SYSTEM, participants);
    }

    @Override
    public PhaseType getType() {
        return PhaseType.LEAGUE_SYSTEM;
    }

    @Override
    public HashMap<Integer, List<Match>> getMatches() {
        return null;
    }
}
