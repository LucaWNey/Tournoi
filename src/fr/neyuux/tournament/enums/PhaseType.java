package fr.neyuux.tournament.enums;

public enum PhaseType {

    PLAYOFFS("Playoffs"),
    DOUBLE_ELIMINATION_PLAYOFFS("Playoffs avec Double Elimination"),
    KING_OF_THE_HILL("King of the Hill"),
    MULTIPLE_PLAYOFFS("Groupes d'Arbres"),
    LEAGUE_SYSTEM("Système de Ligue"),
    GROUPS("Groupes"),
    NONE("Aucun");

    PhaseType(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return name;
    }
}
