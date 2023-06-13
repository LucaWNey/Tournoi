package fr.neyuux.tournament.enums;

import fr.neyuux.tournament.phases.Phase;
import fr.neyuux.tournament.phases.classes.*;
import fr.neyuux.tournament.phases.classes.groups.Groups;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public enum PhaseType {

    PLAYOFFS("Playoffs"),
    DOUBLE_ELIMINATION_PLAYOFFS("Playoffs avec Double Elimination"),
    KING_OF_THE_HILL("King of the Hill"),
    MULTIPLE_PLAYOFFS("Groupes d'Arbres"),
    LEAGUE_SYSTEM("Système de Ligue"),
    SWISS_ROUNDS("Système suisse"),
    GROUPS("Groupes"),
    NONE("Aucun");

    PhaseType(String name) {
        this.name = name;
    }

    private final String name;
    
    private static final HashMap<String, Constructor<? extends Phase>> phaseConstructors = new HashMap<>();

    public String getName() {
        return name;
    }


    public static HashMap<String, Constructor<? extends Phase>> getPhaseConstructors() {
        return phaseConstructors;
    }

    public static void initialiseConstructors() {
        phaseConstructors.clear();
        try {
            phaseConstructors.put(PLAYOFFS.getName(), Playoffs.class.getConstructor(String.class, int.class));
            phaseConstructors.put(DOUBLE_ELIMINATION_PLAYOFFS.getName(), DoubleEliminationPlayoffs.class.getConstructor(String.class, int.class));
            phaseConstructors.put(KING_OF_THE_HILL.getName(), KingOfTheHill.class.getConstructor(String.class, int.class));
            phaseConstructors.put(MULTIPLE_PLAYOFFS.getName(), MultiplePlayoffs.class.getConstructor(String.class, int.class));
            phaseConstructors.put(LEAGUE_SYSTEM.getName(), LeagueSystem.class.getConstructor(String.class, int.class));
            phaseConstructors.put(SWISS_ROUNDS.getName(), SwissRounds.class.getConstructor(String.class, int.class));
            phaseConstructors.put(GROUPS.getName(), Groups.class.getConstructor(String.class, int.class));

        } catch (NoSuchMethodException|SecurityException e) {
            e.printStackTrace();
        }
    }
}
